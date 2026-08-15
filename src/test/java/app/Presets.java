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

package app;

import io.github.palexdev.mcu.MaterialTheme;
import io.github.palexdev.mcu.MaterialThemeBuilder;
import io.github.palexdev.mcu.enums.ExportFormat;
import io.github.palexdev.mcu.enums.SchemeVariant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Presets {

    private static final String[][] PRESETS = {
        {"blue", "#2196F3"},
        {"deep-orange", "#FF5722"},
        {"green", "#4CAF50"},
        {"indigo", "#3F51B5"},
        {"orange", "#FF9800"},
        {"pink", "#E91E63"},
        {"purple", "#6750A4"},
        {"teal", "#009688"},
        {"yellow", "#FFEB3B"},
    };

    private static final SchemeVariant VARIANT = MaterialThemeBuilder.DEFAULT_VARIANT;
    private static final boolean EXTRA_SEMANTIC_COLORS = true;
    private static final boolean INCLUDE_PALETTES = true;

    static void main(String[] args) throws IOException {
        Path out = Path.of(args.length > 0 ? args[0] : "build/presets").toAbsolutePath().normalize();
        Files.createDirectories(out);
        System.out.println("Writing " + PRESETS.length + " presets to " + out);

        for (String[] preset : PRESETS) {
            String name = preset[0];
            String seed = preset[1];
            MaterialTheme theme = MaterialThemeBuilder.theme(seed)
                .variant(VARIANT)
                .extraSemanticColors(EXTRA_SEMANTIC_COLORS)
                .generate();

            Path file = out.resolve("md-preset-" + name + ".css");
            Files.writeString(file, theme.export(ExportFormat.JAVAFX, INCLUDE_PALETTES));
            System.out.printf("  %-14s %s%n", name, seed);
        }
    }
}
