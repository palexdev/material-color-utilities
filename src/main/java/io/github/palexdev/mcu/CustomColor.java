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

import io.github.palexdev.mcu.vendor.dynamiccolor.DynamicScheme;
import io.github.palexdev.mcu.vendor.hct.Hct;

/// A named extra color, resolved against a theme's seed and expanded into its own light/dark schemes.
///
/// Instances are produced by [MaterialThemeBuilder#generate()] and are immutable: every call to
/// `generate()` creates fresh ones, so themes never share state.
///
/// @param seed         the color exactly as it was registered
/// @param resolvedSeed the color that actually drove the schemes, after harmonization against the
///                     theme seed and after the "universally disliked" fix. Equal to [#seed()] when
///                     neither applied
public record CustomColor(
    String name,
    Hct seed,
    Hct resolvedSeed,
    boolean harmonized,
    DynamicScheme lightScheme,
    DynamicScheme darkScheme
) {

    //================================================================================
    // Methods
    //================================================================================

    public DynamicScheme scheme(boolean isDark) {
        return isDark ? darkScheme : lightScheme;
    }

    public int keyColor() {
        return seed.toInt();
    }

    public int resolvedKeyColor() {
        return resolvedSeed.toInt();
    }

    public int color(boolean isDark) {
        return scheme(isDark).getPrimary();
    }

    public int onColor(boolean isDark) {
        return scheme(isDark).getOnPrimary();
    }

    public int colorContainer(boolean isDark) {
        return scheme(isDark).getPrimaryContainer();
    }

    public int onColorContainer(boolean isDark) {
        return scheme(isDark).getOnPrimaryContainer();
    }
}
