package io.github.palexdev.mcu.enums;

import io.github.palexdev.mcu.Colors;
import io.github.palexdev.mcu.vendor.palettes.TonalPalette;

import java.util.List;

public enum Palettes {
    PRIMARY,
    SECONDARY,
    TERTIARY,
    ERROR,
    NEUTRAL,
    NEUTRAL_VARIANT;

    //================================================================================
    // Properties
    //================================================================================

    public static final TonalPalette DEFAULT_ERROR_PALETTE = TonalPalette.fromHueAndChroma(25.0, 84.0);
    public static final List<Integer> TONES = List.of(0, 4, 5, 6, 10, 12, 15, 17, 20, 24, 25, 30, 35, 40, 50, 60, 70, 80, 87, 90, 92, 94, 95, 96, 98, 99, 100);

    //================================================================================
    // Methods
    //================================================================================

    public static List<Integer> getColors(TonalPalette palette) {
        return TONES.stream().map(palette::tone).toList();
    }

    public static List<String> getWebColor(TonalPalette palette) {
        return TONES.stream().map(palette::tone).map(Colors::argbToWeb).toList();
    }

    public String toCssString() {
        return name().replace("_", "-").toLowerCase();
    }

    @Override
    public String toString() {
        return (this == NEUTRAL_VARIANT) ?
            "Neutral Variant" :
            name().charAt(0) + name().substring(1).toLowerCase();
    }
}
