package io.github.palexdev.mcu.enums;

import io.github.palexdev.mcu.ColorScheme;
import io.github.palexdev.mcu.MaterialTheme;
import io.github.palexdev.mcu.vendor.palettes.TonalPalette;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.github.palexdev.mcu.Colors.argbToWeb;

public enum ExportFormat {
    WEB {
        @Override
        public String export(MaterialTheme theme, boolean includePalettes) {
            StringBuilder sb = new StringBuilder();
            // Light Scheme
            sb.append(".light {").append("\n");
            sb.append("  ").append("--md-seed: ").append(argbToWeb(theme.seed().toInt())).append(";\n");
            getSchemeColors(theme, false).forEach((k, v) ->
                sb.append("  ").append(k).append(": ").append(v).append(";\n"));
            if (!theme.customColors().isEmpty()) {
                sb.append("  ").append(comment("Custom"));
                theme.customColors().forEach((n, c) -> {
                    sb.append("  -").append(customSeed(n, c.keyColor())).append("\n");
                    sb.append("  -").append(customColor(n, c.color(false))).append("\n");
                    sb.append("  -").append(customColor("on-" + n, c.onColor(false))).append("\n");
                    sb.append("  -").append(customColor(n + "-container", c.colorContainer(false))).append("\n");
                    sb.append("  -").append(customColor("on-" + n + "-container", c.onColorContainer(false))).append("\n");
                });
            }
            sb.append("}\n\n");

            // Dark Scheme
            sb.append(".dark {").append("\n");
            sb.append("  ").append("--md-seed: ").append(argbToWeb(theme.seed().toInt())).append(";\n");
            getSchemeColors(theme, true).forEach((k, v) ->
                sb.append("  ").append(k).append(": ").append(v).append(";\n"));
            if (!theme.customColors().isEmpty()) {
                sb.append("  ").append(comment("Custom"));
                theme.customColors().forEach((n, c) -> {
                    sb.append("  -").append(customSeed(n, c.keyColor())).append("\n");
                    sb.append("  -").append(customColor(n, c.color(true))).append("\n");
                    sb.append("  -").append(customColor("on-" + n, c.onColor(true))).append("\n");
                    sb.append("  -").append(customColor(n + "-container", c.colorContainer(true))).append("\n");
                    sb.append("  -").append(customColor("on-" + n + "-container", c.onColorContainer(true))).append("\n");
                });
            }
            sb.append("}\n");
            return sb.toString();
        }
    },
    JAVAFX {
        @Override
        public String export(MaterialTheme theme, boolean includePalettes) {
            StringBuilder sb = new StringBuilder(".root {\n");
            // Light
            sb.append("  ").append(comment("Light"));
            sb.append("  ").append("-md-seed: ").append(argbToWeb(theme.seed().toInt())).append(";\n");
            if (includePalettes) {
                getPaletteColors(theme, false).forEach((k, v) ->
                    sb.append("  ").append(k.substring(1)).append(": ").append(v).append(";\n"));
                sb.append("\n");
            }
            getSchemeColors(theme, false).forEach((k, v) ->
                sb.append("  ").append(k.substring(1)).append(": ").append(v).append(";\n"));

            // Light - Custom
            if (!theme.customColors().isEmpty()) {
                sb.append("  ").append(comment("Custom"));
                theme.customColors().forEach((n, c) -> {
                    sb.append("  ").append(customSeed(n, c.keyColor())).append("\n");
                    sb.append("  ").append(customColor(n, c.color(false))).append("\n");
                    sb.append("  ").append(customColor("on-" + n, c.onColor(false))).append("\n");
                    sb.append("  ").append(customColor(n + "-container", c.colorContainer(false))).append("\n");
                    sb.append("  ").append(customColor("on-" + n + "-container", c.onColorContainer(false))).append("\n");
                });
            }
            sb.append("}\n\n");

            // Dark
            sb.append(".root:dark {\n");
            sb.append("  ").append(comment("Dark"));
            sb.append("  ").append("-md-seed: ").append(argbToWeb(theme.seed().toInt())).append(";\n");
            if (includePalettes) {
                getPaletteColors(theme, true).forEach((k, v) ->
                    sb.append("  ").append(k.substring(1)).append(": ").append(v).append(";\n"));
                sb.append("\n");
            }
            getSchemeColors(theme, true).forEach((k, v) ->
                sb.append("  ").append(k.substring(1)).append(": ").append(v).append(";\n"));

            // Dark - Custom
            if (!theme.customColors().isEmpty()) {
                sb.append("  ").append(comment("Custom"));
                theme.customColors().forEach((n, c) -> {
                    sb.append("  ").append(customSeed(n, c.keyColor())).append("\n");
                    sb.append("  ").append(customColor(n, c.color(true))).append("\n");
                    sb.append("  ").append(customColor("on-" + n, c.onColor(true))).append("\n");
                    sb.append("  ").append(customColor(n + "-container", c.colorContainer(true))).append("\n");
                    sb.append("  ").append(customColor("on-" + n + "-container", c.onColorContainer(true))).append("\n");
                });
            }
            sb.append("}\n");
            return sb.toString();
        }
    };

    public abstract String export(MaterialTheme theme, boolean includePalettes);

    private static Map<String, String> getSchemeColors(MaterialTheme theme, boolean isDark) {
        Map<String, String> colors = new LinkedHashMap<>();
        ColorScheme scheme = theme.colorScheme(isDark);
        // Primary
        colors.put(colorKey("primary"), argbToWeb(scheme.getPrimary()));
        colors.put(colorKey("surface-tint"), argbToWeb(scheme.getSurfaceTint()));
        colors.put(colorKey("on-primary"), argbToWeb(scheme.getOnPrimary()));
        colors.put(colorKey("primary-container"), argbToWeb(scheme.getPrimaryContainer()));
        colors.put(colorKey("on-primary-container"), argbToWeb(scheme.getOnPrimaryContainer()));
        colors.put(colorKey("primary-fixed"), argbToWeb(scheme.getPrimaryFixed()));
        colors.put(colorKey("on-primary-fixed"), argbToWeb(scheme.getOnPrimaryFixed()));
        colors.put(colorKey("primary-fixed-dim"), argbToWeb(scheme.getPrimaryFixedDim()));
        colors.put(colorKey("on-primary-fixed-variant"), argbToWeb(scheme.getOnPrimaryFixedVariant()));
        colors.put(colorKey("inverse-primary"), argbToWeb(scheme.getInversePrimary()));

        // Secondary
        colors.put(colorKey("secondary"), argbToWeb(scheme.getSecondary()));
        colors.put(colorKey("on-secondary"), argbToWeb(scheme.getOnSecondary()));
        colors.put(colorKey("secondary-container"), argbToWeb(scheme.getSecondaryContainer()));
        colors.put(colorKey("on-secondary-container"), argbToWeb(scheme.getOnSecondaryContainer()));
        colors.put(colorKey("secondary-fixed"), argbToWeb(scheme.getSecondaryFixed()));
        colors.put(colorKey("on-secondary-fixed"), argbToWeb(scheme.getOnSecondaryFixed()));
        colors.put(colorKey("secondary-fixed-dim"), argbToWeb(scheme.getSecondaryFixedDim()));
        colors.put(colorKey("on-secondary-fixed-variant"), argbToWeb(scheme.getOnSecondaryFixedVariant()));

        // Tertiary
        colors.put(colorKey("tertiary"), argbToWeb(scheme.getTertiary()));
        colors.put(colorKey("on-tertiary"), argbToWeb(scheme.getOnTertiary()));
        colors.put(colorKey("tertiary-container"), argbToWeb(scheme.getTertiaryContainer()));
        colors.put(colorKey("on-tertiary-container"), argbToWeb(scheme.getOnTertiaryContainer()));
        colors.put(colorKey("tertiary-fixed"), argbToWeb(scheme.getTertiaryFixed()));
        colors.put(colorKey("on-tertiary-fixed"), argbToWeb(scheme.getOnTertiaryFixed()));
        colors.put(colorKey("tertiary-fixed-dim"), argbToWeb(scheme.getTertiaryFixedDim()));
        colors.put(colorKey("on-tertiary-fixed-variant"), argbToWeb(scheme.getOnTertiaryFixedVariant()));

        // Error
        colors.put(colorKey("error"), argbToWeb(scheme.getError()));
        colors.put(colorKey("on-error"), argbToWeb(scheme.getOnError()));
        colors.put(colorKey("error-container"), argbToWeb(scheme.getErrorContainer()));
        colors.put(colorKey("on-error-container"), argbToWeb(scheme.getOnErrorContainer()));

        // Surfaces
        colors.put(colorKey("background"), argbToWeb(scheme.getBackground()));
        colors.put(colorKey("on-background"), argbToWeb(scheme.getOnBackground()));

        colors.put(colorKey("surface"), argbToWeb(scheme.getSurface()));
        colors.put(colorKey("on-surface"), argbToWeb(scheme.getOnSurface()));
        colors.put(colorKey("surface-variant"), argbToWeb(scheme.getSurfaceVariant()));
        colors.put(colorKey("on-surface-variant"), argbToWeb(scheme.getOnSurfaceVariant()));

        colors.put(colorKey("inverse-surface"), argbToWeb(scheme.getInverseSurface()));
        colors.put(colorKey("inverse-on-surface"), argbToWeb(scheme.getInverseOnSurface()));

        colors.put(colorKey("surface-dim"), argbToWeb(scheme.getSurfaceDim()));
        colors.put(colorKey("surface-bright"), argbToWeb(scheme.getSurfaceBright()));
        colors.put(colorKey("surface-container-lowest"), argbToWeb(scheme.getSurfaceContainerLowest()));
        colors.put(colorKey("surface-container-low"), argbToWeb(scheme.getSurfaceContainerLow()));
        colors.put(colorKey("surface-container"), argbToWeb(scheme.getSurfaceContainer()));
        colors.put(colorKey("surface-container-high"), argbToWeb(scheme.getSurfaceContainerHigh()));
        colors.put(colorKey("surface-container-highest"), argbToWeb(scheme.getSurfaceContainerHighest()));

        // Misc
        colors.put(colorKey("outline"), argbToWeb(scheme.getOutline()));
        colors.put(colorKey("outline-variant"), argbToWeb(scheme.getOutlineVariant()));

        colors.put(colorKey("shadow"), argbToWeb(scheme.getShadow()));
        colors.put(colorKey("scrim"), argbToWeb(scheme.getScrim()));

        return colors;
    }

    private static Map<String, String> getPaletteColors(MaterialTheme theme, boolean isDark) {
        Map<String, String> colors = new LinkedHashMap<>();
        for (Palettes palette : Palettes.values()) {
            String name = palette.toCssString();
            TonalPalette tp = theme.palette(palette, isDark);
            for (int tone : Palettes.TONES) {
                String key = "--md-ref-palette-" + name + tone;
                String val = argbToWeb(tp.tone(tone));
                colors.put(key, val);
            }
        }
        return colors;
    }

    private static String customColor(String name, int color) {
        return "-md-sys-color-" + name + ": " + argbToWeb(color) + ";";
    }

    private static String customSeed(String name, int seed) {
        return "-md-seed-" + name + ": " + argbToWeb(seed) + ";";
    }

    private static String colorKey(String color) {
        return "--md-sys-color-" + color;
    }

    private static String comment(String text) {
        return "/* %s */\n".formatted(text);
    }
}
