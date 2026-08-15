/*
 * Copyright (C) 2026 Parisi Alessandro - alessandro.parisi406@gmail.com
 * This file is part of material-color-utilities (https://github.com/palexdev/material-color-utilities)
 *
 * material-color-utilities is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * material-color-utilities is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with material-color-utilities. If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.palexdev.mcu;

import io.github.palexdev.mcu.enums.ExtraColors;
import io.github.palexdev.mcu.enums.SchemeVariant;
import io.github.palexdev.mcu.vendor.dislike.DislikeAnalyzer;
import io.github.palexdev.mcu.vendor.dynamiccolor.DynamicScheme;
import io.github.palexdev.mcu.vendor.hct.Hct;
import io.github.palexdev.mcu.vendor.palettes.TonalPalette;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.github.palexdev.mcu.Colors.webToHct;
import static io.github.palexdev.mcu.vendor.blend.Blend.harmonize;

public class MaterialThemeBuilder {

    //================================================================================
    // Properties
    //================================================================================

    public static final Hct BASELINE_SEED = Hct.fromInt(Colors.webToArgb("#6750A4"));
    public static final SchemeVariant DEFAULT_VARIANT = SchemeVariant.TONAL_SPOT;

    private final Hct seed;
    private SchemeVariant variant = DEFAULT_VARIANT;
    private boolean colorMatch = false;
    private boolean extraSemanticColors = false;
    private final Map<String, Spec> customColors = new LinkedHashMap<>();

    //================================================================================
    // Constructors
    //================================================================================

    MaterialThemeBuilder(Hct seed) {
        this.seed = seed;
    }

    public static MaterialThemeBuilder theme() {
        return theme(BASELINE_SEED);
    }

    public static MaterialThemeBuilder theme(Hct seed) {
        return new MaterialThemeBuilder(seed);
    }

    public static MaterialThemeBuilder theme(String webSeed) {
        return new MaterialThemeBuilder(webToHct(webSeed));
    }

    public static MaterialThemeBuilder theme(int argbSeed) {
        return new MaterialThemeBuilder(Hct.fromInt(argbSeed));
    }

    //================================================================================
    // Methods
    //================================================================================

    public MaterialTheme generate() {
        // Main schemes
        DynamicScheme ls = variant.generateLight(seed);
        DynamicScheme ds = variant.generateDark(seed);
        Colors colors = new Colors(
            Hct.fromInt(ls.getPrimaryPaletteKeyColor()),
            Hct.fromInt(ls.getSecondaryPaletteKeyColor()),
            Hct.fromInt(ls.getTertiaryPaletteKeyColor()),
            Hct.fromInt(ls.errorPalette.getKeyColor().toInt()),
            Hct.fromInt(ls.getNeutralPaletteKeyColor()),
            Hct.fromInt(ls.getNeutralVariantPaletteKeyColor())
        );
        // Custom colors: lay down the extra semantic defaults, then let the user's registrations overwrite them.
        // Re-putting a key in a LinkedHashMap replaces the value but keeps the original position, so a user's own
        // "success" wins the color while the default keeps its slot, and the exports stay position stable
        Map<String, CustomColor> custom = new LinkedHashMap<>();
        if (extraSemanticColors) {
            for (ExtraColors extra : ExtraColors.values()) {
                custom.put(extra.key(), extra.toCustomColor());
            }
        }
        customColors.forEach((name, spec) -> custom.put(name, resolve(name, spec)));

        return new MaterialTheme(variant, colors, ls, ds, custom);
    }

    private CustomColor resolve(String name, Spec spec) {
        Hct resolved = spec.harmonized()
            ? Hct.fromInt(harmonize(spec.seed().toInt(), seed.toInt()))
            : spec.seed();
        if (!colorMatch) resolved = DislikeAnalyzer.fixIfDisliked(resolved);
        if (spec.fixed()) {
            TonalPalette palette = TonalPalette.fromHct(resolved);
            return new CustomColor(
                name, spec.seed(), resolved, spec.harmonized(),
                ColorGroup.light(palette),
                ColorGroup.dark(palette)
            );
        }
        return new CustomColor(
            name, spec.seed(), resolved, spec.harmonized(),
            ColorGroup.fromPrimary(variant.generate(resolved, false)),
            ColorGroup.fromPrimary(variant.generate(resolved, true))
        );
    }

    public MaterialThemeBuilder variant(SchemeVariant variant) {
        this.variant = variant;
        return this;
    }

    /// Whether to fix custom colors that are "universally disliked" or stay true to input.
    ///
    /// (Fix by default, so `false`)
    public MaterialThemeBuilder colorMatch(boolean colorMatch) {
        this.colorMatch = colorMatch;
        return this;
    }

    /// Whether to add `success`, `warn` and `info` to every generated theme, see [ExtraColors].
    public MaterialThemeBuilder extraSemanticColors(boolean extraSemanticColors) {
        this.extraSemanticColors = extraSemanticColors;
        return this;
    }

    /// Registers an extra color to expand into its own schemes.
    public MaterialThemeBuilder customColor(String name, Hct color, boolean harmonize) {
        customColors.put(name, new Spec(color, harmonize, false));
        return this;
    }

    public MaterialThemeBuilder customColor(String name, int argbColor, boolean harmonize) {
        return customColor(name, Hct.fromInt(argbColor), harmonize);
    }

    public MaterialThemeBuilder customColor(String name, String webColor, boolean harmonize) {
        return customColor(name, webToHct(webColor), harmonize);
    }

    /// Registers a color whose tones come straight from its own palette, exactly like Material's `error` role and
    /// like the [ExtraColors] defaults.
    ///
    /// Unlike [#customColor(String, Hct, boolean)] the result does not follow the theme's [SchemeVariant]: it keeps
    /// its hue under every variant, including [SchemeVariant#MONOCHROME]. There is deliberately no `harmonize`
    /// option, since harmonizing rotates the hue toward the theme's seed, which is the opposite of fixed.
    ///
    /// Note: warm hues (oranges, ambers, yellows) go muddy at the light base tone of 40, see [ExtraColors#WARN].
    public MaterialThemeBuilder fixedColor(String name, Hct color) {
        customColors.put(name, new Spec(color, false, true));
        return this;
    }

    public MaterialThemeBuilder fixedColor(String name, int argbColor) {
        return fixedColor(name, Hct.fromInt(argbColor));
    }

    public MaterialThemeBuilder fixedColor(String name, String webColor) {
        return fixedColor(name, webToHct(webColor));
    }

    //================================================================================
    // Internal Classes
    //================================================================================

    /// A custom color as registered, before it is resolved against the theme's seed.
    /// `fixed` colors take their tones from their own palette instead of following the theme's variant.
    private record Spec(Hct seed, boolean harmonized, boolean fixed) {}
}
