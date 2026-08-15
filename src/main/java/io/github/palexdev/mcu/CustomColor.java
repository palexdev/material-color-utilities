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
import io.github.palexdev.mcu.vendor.hct.Hct;

import java.util.Objects;

/// A named extra color, resolved into its light/dark [ColorGroups][ColorGroup].
///
/// Instances are produced by [MaterialThemeBuilder#generate()] and are immutable: every call to `generate()`
/// creates fresh ones, so themes never share state.
///
/// There are two derivation modes. Colors registered through [MaterialThemeBuilder#customColor(String, Hct, boolean)]
/// are expanded through the theme's variant, so they follow it. The defaults in [ExtraColors] instead take their tones
/// straight from a palette, exactly like Material's `error` role, so the variant has no effect on them.
///
/// The full schemes of a variant-derived color are not retained. To get one back:
/// ```java
/// DynamicScheme scheme = theme.variant().generate(customColor.resolvedSeed(), isDark);
/// ```
///
/// @param seed         the color exactly as it was registered
/// @param resolvedSeed the color that actually drove the derivation, after harmonization against the theme seed
///                     and after the "universally disliked" fix. Equal to [#seed()] when neither applied
public record CustomColor(
    String name,
    Hct seed,
    Hct resolvedSeed,
    boolean harmonized,
    ColorGroup light,
    ColorGroup dark
) {

    //================================================================================
    // Methods
    //================================================================================

    public ColorGroup group(boolean isDark) {
        return isDark ? dark : light;
    }

    public int keyColor() {
        return seed.toInt();
    }

    public int resolvedKeyColor() {
        return resolvedSeed.toInt();
    }

    public int color(boolean isDark) {
        return group(isDark).color();
    }

    public int onColor(boolean isDark) {
        return group(isDark).onColor();
    }

    public int colorContainer(boolean isDark) {
        return group(isDark).colorContainer();
    }

    public int onColorContainer(boolean isDark) {
        return group(isDark).onColorContainer();
    }

    //================================================================================
    // Overridden Methods
    //================================================================================

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CustomColor that)) return false;
        return harmonized == that.harmonized
            && Objects.equals(name, that.name)
            && keyColor() == that.keyColor()
            && resolvedKeyColor() == that.resolvedKeyColor()
            && Objects.equals(light, that.light)
            && Objects.equals(dark, that.dark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, keyColor(), resolvedKeyColor(), harmonized, light, dark);
    }
}
