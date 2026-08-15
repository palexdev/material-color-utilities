package io.github.palexdev.mcu;

import io.github.palexdev.mcu.enums.ExportFormat;
import io.github.palexdev.mcu.enums.Palettes;
import io.github.palexdev.mcu.enums.SchemeVariant;
import io.github.palexdev.mcu.vendor.dynamiccolor.DynamicScheme;
import io.github.palexdev.mcu.vendor.hct.Hct;
import io.github.palexdev.mcu.vendor.palettes.TonalPalette;

import java.util.Collections;
import java.util.Map;

public record MaterialTheme(
    SchemeVariant variant,
    Colors colors,
    ColorScheme lightScheme,
    ColorScheme darkScheme,
    Map<String, CustomColor> customColors
) {

    //================================================================================
    // Constructors
    //================================================================================

    public MaterialTheme(SchemeVariant variant, Colors colors,
                         DynamicScheme lightScheme, DynamicScheme darkScheme,
                         Map<String, CustomColor> customColors) {
        this(variant, colors, new ColorScheme(lightScheme), new ColorScheme(darkScheme), customColors);
    }

    //================================================================================
    // Methods
    //================================================================================

    public Hct seed() {
        return lightScheme.getScheme().sourceColorHct;
    }

    public ColorScheme colorScheme(boolean isDark) {
        return isDark ? darkScheme : lightScheme;
    }

    public TonalPalette palette(Palettes palette, boolean isDark) {
        return switch (palette) {
            case PRIMARY -> colorScheme(isDark).primaryPalette();
            case SECONDARY -> colorScheme(isDark).secondaryPalette();
            case TERTIARY -> colorScheme(isDark).tertiaryPalette();
            case ERROR -> colorScheme(isDark).errorPalette();
            case NEUTRAL -> colorScheme(isDark).neutralPalette();
            case NEUTRAL_VARIANT -> colorScheme(isDark).neutralVariantPalette();
        };
    }

    public int keyColor(Palettes palette, boolean isDark) {
        return switch (palette) {
            case PRIMARY -> colorScheme(isDark).getPrimaryPaletteKeyColor();
            case SECONDARY -> colorScheme(isDark).getSecondaryPaletteKeyColor();
            case TERTIARY -> colorScheme(isDark).getTertiaryPaletteKeyColor();
            case ERROR -> colorScheme(isDark).getErrorPaletteKeyColor();
            case NEUTRAL -> colorScheme(isDark).getNeutralPaletteKeyColor();
            case NEUTRAL_VARIANT -> colorScheme(isDark).getNeutralVariantPaletteKeyColor();
        };
    }

    public CustomColor customColor(String name) {
        return customColors.get(name);
    }

    public String export(ExportFormat format, boolean includePalettes) {
        return format.export(this, includePalettes);
    }

    @Override
    public Map<String, CustomColor> customColors() {
        return Collections.unmodifiableMap(customColors);
    }
}
