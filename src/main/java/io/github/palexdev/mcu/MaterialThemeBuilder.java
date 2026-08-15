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

import io.github.palexdev.mcu.enums.SchemeVariant;
import io.github.palexdev.mcu.vendor.dislike.DislikeAnalyzer;
import io.github.palexdev.mcu.vendor.dynamiccolor.DynamicScheme;
import io.github.palexdev.mcu.vendor.hct.Hct;

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
        // Custom colors
        Map<String, CustomColor> custom = new LinkedHashMap<>();
        customColors.forEach((name, spec) -> {
            Hct resolved = spec.harmonized()
                ? Hct.fromInt(harmonize(spec.seed().toInt(), seed.toInt()))
                : spec.seed();
            if (!colorMatch) resolved = DislikeAnalyzer.fixIfDisliked(resolved);
            custom.put(name, new CustomColor(
                name, spec.seed(), resolved, spec.harmonized(),
                variant.generate(resolved, false),
                variant.generate(resolved, true)
            ));
        });

        return new MaterialTheme(variant, colors, ls, ds, custom);
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

    /// Registers an extra color to expand into its own schemes.
    public MaterialThemeBuilder customColor(String name, Hct color, boolean harmonize) {
        customColors.put(name, new Spec(color, harmonize));
        return this;
    }

    public MaterialThemeBuilder customColor(String name, int argbColor, boolean harmonize) {
        return customColor(name, Hct.fromInt(argbColor), harmonize);
    }

    public MaterialThemeBuilder customColor(String name, String webColor, boolean harmonize) {
        return customColor(name, webToHct(webColor), harmonize);
    }

    //================================================================================
    // Internal Classes
    //================================================================================

    /// A custom color as registered, before it is resolved against the theme's seed.
    private record Spec(Hct seed, boolean harmonized) {}
}
