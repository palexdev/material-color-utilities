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
import io.github.palexdev.mcu.vendor.palettes.TonalPalette;

/// The four roles that make up a Material color group. All values are in the **ARGB** format.
public record ColorGroup(int color, int onColor, int colorContainer, int onColorContainer) {

    //================================================================================
    // Methods
    //================================================================================

    /// Reads the four roles off a palette at the given tones, in component order.
    public static ColorGroup of(TonalPalette palette, int color, int onColor, int container, int onContainer) {
        return new ColorGroup(
            palette.tone(color),
            palette.tone(onColor),
            palette.tone(container),
            palette.tone(onContainer)
        );
    }

    public static ColorGroup light(TonalPalette palette) {
        return of(palette, 40, 100, 90, 30);
    }

    public static ColorGroup dark(TonalPalette palette) {
        return of(palette, 80, 20, 30, 90);
    }

    public static ColorGroup of(TonalPalette palette, boolean isDark) {
        return isDark ? dark(palette) : light(palette);
    }

    public static ColorGroup fromPrimary(DynamicScheme scheme) {
        return new ColorGroup(
            scheme.getPrimary(),
            scheme.getOnPrimary(),
            scheme.getPrimaryContainer(),
            scheme.getOnPrimaryContainer()
        );
    }
}
