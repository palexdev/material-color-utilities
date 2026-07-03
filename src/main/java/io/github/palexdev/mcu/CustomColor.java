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

import java.util.Objects;

public class CustomColor {

    //================================================================================
    // Properties
    //================================================================================

    private final String name;
    private final Hct seed;
    private final boolean harmonized;

    private DynamicScheme lightScheme;
    private DynamicScheme darkScheme;

    //================================================================================
    // Constructors
    //================================================================================

    public CustomColor(String name, Hct seed, boolean harmonized) {
        this.name = name;
        this.seed = seed;
        this.harmonized = harmonized;
    }

    //================================================================================
    // Overridden Methods
    //================================================================================

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CustomColor that = (CustomColor) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    //================================================================================
    // Getters/Setters
    //================================================================================

    public int keyColor() {
        return seed.toInt();
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

    public String name() {
        return name;
    }

    public Hct seed() {
        return seed;
    }

    public boolean harmonized() {
        return harmonized;
    }

    public DynamicScheme scheme(boolean isDark) {
        return isDark ? darkScheme : lightScheme;
    }

    void setSchemes(DynamicScheme lightScheme, DynamicScheme darkScheme) {
        this.lightScheme = lightScheme;
        this.darkScheme = darkScheme;
    }
}
