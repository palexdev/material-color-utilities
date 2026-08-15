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

package io.github.palexdev.mcu.enums;

import io.github.palexdev.mcu.ColorGroup;
import io.github.palexdev.mcu.Colors;
import io.github.palexdev.mcu.CustomColor;
import io.github.palexdev.mcu.MaterialThemeBuilder;
import io.github.palexdev.mcu.vendor.hct.Hct;
import io.github.palexdev.mcu.vendor.palettes.TonalPalette;

/// The semantic colors Material does not ship a role for.
///
/// Material defines `error` but nothing for success, warning or information, which are ubiquitous in real UIs.
/// These three can be added to every theme by using [MaterialThemeBuilder#extraSemanticColors(boolean)],
/// and are reachable as `theme.customColor("success")`.
///
/// Exactly like `error`, they are fixed: their tones come straight from a [TonalPalette] built on the seed's hue
/// and chroma, so they neither follow the theme's [SchemeVariant] nor get harmonized toward the theme's seed.
/// Success stays green under [SchemeVariant#MONOCHROME] and is not hue rotated under [SchemeVariant#EXPRESSIVE].
public enum ExtraColors {
    SUCCESS("success", "#4CAF50"),
    /// Light tone 55 instead of 40: warm hues have no vivid form that dark, orange at tone 40 is `#964900`, brown.
    ///
    /// Tone 55 is `#CE6700`, and against white that is 3.77:1. Below the 4.5:1 needed for small body text, but
    /// above the 3:1 that WCAG asks of large text, icons and UI components, which is what a warning color is
    /// normally used for. Deliberate: keeping `onColor` white makes it consistent with the other roles, and the
    /// tones that clear 4.5:1 against white (45 and below) are all visibly brown. Use the container pair for
    /// anything that has to carry small text.
    WARN("warn", "#F57C00", 55, 100, 90, 30),
    INFO("info", "#0288D1");

    //================================================================================
    // Properties
    //================================================================================

    private final String key;
    private final Hct seed;
    private final ColorGroup light;
    private final ColorGroup dark;

    //================================================================================
    // Constructors
    //================================================================================

    ExtraColors(String key, String webSeed) {
        this(key, webSeed, 40, 100, 90, 30);
    }

    /// Only the light tones are ever overridden: dark mode reads the base role at tone 80, which is light enough
    /// that no hue goes muddy there.
    ExtraColors(String key, String webSeed, int color, int onColor, int container, int onContainer) {
        this.key = key;
        this.seed = Colors.webToHct(webSeed);
        TonalPalette palette = TonalPalette.fromHct(seed);
        this.light = ColorGroup.of(palette, color, onColor, container, onContainer);
        this.dark = ColorGroup.dark(palette);
    }

    //================================================================================
    // Methods
    //================================================================================

    /// @return the name this color is registered under, and the stem of its CSS variables.
    public String key() {
        return key;
    }

    public Hct seed() {
        return seed;
    }

    public ColorGroup group(boolean isDark) {
        return isDark ? dark : light;
    }

    /// @return a fresh palette for this color. Callers own the instance, see the threading note on [TonalPalette].
    public TonalPalette palette() {
        return TonalPalette.fromHct(seed);
    }

    public CustomColor toCustomColor() {
        return new CustomColor(key, seed, seed, false, light, dark);
    }
}
