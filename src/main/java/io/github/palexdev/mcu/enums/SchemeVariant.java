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

import io.github.palexdev.mcu.vendor.dynamiccolor.DynamicScheme;
import io.github.palexdev.mcu.vendor.dynamiccolor.Variant;
import io.github.palexdev.mcu.vendor.hct.Hct;
import io.github.palexdev.mcu.vendor.scheme.*;

public enum SchemeVariant {
    MONOCHROME {
        @Override
        public DynamicScheme generate(Hct seed, boolean isDark) {
            return new SchemeMonochrome(seed, isDark, 0.0);
        }
    },
    NEUTRAL {
        @Override
        public DynamicScheme generate(Hct seed, boolean isDark) {
            return new SchemeNeutral(seed, isDark, 0.0);
        }
    },
    TONAL_SPOT {
        @Override
        public DynamicScheme generate(Hct seed, boolean isDark) {
            return new SchemeTonalSpot(seed, isDark, 0.0);
        }
    },
    VIBRANT {
        @Override
        public DynamicScheme generate(Hct seed, boolean isDark) {
            return new SchemeVibrant(seed, isDark, 0.0);
        }
    },
    EXPRESSIVE {
        @Override
        public DynamicScheme generate(Hct seed, boolean isDark) {
            return new SchemeExpressive(seed, isDark, 0.0);
        }
    },
    FIDELITY {
        @Override
        public DynamicScheme generate(Hct seed, boolean isDark) {
            return new SchemeFidelity(seed, isDark, 0.0);
        }
    },
    CONTENT {
        @Override
        public DynamicScheme generate(Hct seed, boolean isDark) {
            return new SchemeContent(seed, isDark, 0.0);
        }
    },
    RAINBOW {
        @Override
        public DynamicScheme generate(Hct seed, boolean isDark) {
            return new SchemeRainbow(seed, isDark, 0.0);
        }
    },
    FRUIT_SALAD {
        @Override
        public DynamicScheme generate(Hct seed, boolean isDark) {
            return new SchemeFruitSalad(seed, isDark, 0.0);
        }
    },
    CMF {
        @Override
        public DynamicScheme generate(Hct seed, boolean isDark) {
            return new SchemeCmf(seed, isDark, 0.0);
        }
    };

    public abstract DynamicScheme generate(Hct seed, boolean isDark);

    public DynamicScheme generateLight(Hct seed) {
        return generate(seed, false);
    }

    public DynamicScheme generateDark(Hct seed) {
        return generate(seed, true);
    }

    public Variant toVariant() {
        return Variant.valueOf(name());
    }
}
