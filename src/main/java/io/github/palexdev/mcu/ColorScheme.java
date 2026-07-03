package io.github.palexdev.mcu;

import io.github.palexdev.mcu.vendor.dynamiccolor.DynamicScheme;
import io.github.palexdev.mcu.vendor.palettes.TonalPalette;

import java.util.HashMap;
import java.util.Map;

public class ColorScheme {

    //================================================================================
    // Properties
    //================================================================================

    private final DynamicScheme scheme;
    private final Map<String, Integer> cache = new HashMap<>();

    //================================================================================
    // Constructors
    //================================================================================

    public ColorScheme(DynamicScheme scheme) {
        this.scheme = scheme;
    }

    //================================================================================
    // Methods
    //================================================================================

    public int getPrimary() {
        return cache.computeIfAbsent("primary", _ -> scheme.getPrimary());
    }

    public int getSurfaceTint() {
        return cache.computeIfAbsent("surface_tint", _ -> scheme.getSurfaceTint());
    }

    public int getOnPrimary() {
        return cache.computeIfAbsent("on_primary", _ -> scheme.getOnPrimary());
    }

    public int getPrimaryContainer() {
        return cache.computeIfAbsent("primary_container", _ -> scheme.getPrimaryContainer());
    }

    public int getOnPrimaryContainer() {
        return cache.computeIfAbsent("on_primary_container", _ -> scheme.getOnPrimaryContainer());
    }

    public int getPrimaryPaletteKeyColor() {
        return cache.computeIfAbsent("primary_palette_key_color", _ -> scheme.getPrimaryPaletteKeyColor());
    }

    public int getSecondary() {
        return cache.computeIfAbsent("secondary", _ -> scheme.getSecondary());
    }

    public int getOnSecondary() {
        return cache.computeIfAbsent("on_secondary", _ -> scheme.getOnSecondary());
    }

    public int getSecondaryContainer() {
        return cache.computeIfAbsent("secondary_container", _ -> scheme.getSecondaryContainer());
    }

    public int getOnSecondaryContainer() {
        return cache.computeIfAbsent("on_secondary_container", _ -> scheme.getOnSecondaryContainer());
    }

    public int getSecondaryPaletteKeyColor() {
        return cache.computeIfAbsent("secondary_palette_key_color", _ -> scheme.getSecondaryPaletteKeyColor());
    }

    public int getTertiary() {
        return cache.computeIfAbsent("tertiary", _ -> scheme.getTertiary());
    }

    public int getOnTertiary() {
        return cache.computeIfAbsent("on_tertiary", _ -> scheme.getOnTertiary());
    }

    public int getTertiaryContainer() {
        return cache.computeIfAbsent("tertiary_container", _ -> scheme.getTertiaryContainer());
    }

    public int getOnTertiaryContainer() {
        return cache.computeIfAbsent("on_tertiary_container", _ -> scheme.getOnTertiaryContainer());
    }

    public int getTertiaryPaletteKeyColor() {
        return cache.computeIfAbsent("tertiary_palette_key_color", _ -> scheme.getTertiaryPaletteKeyColor());
    }

    public int getError() {
        return cache.computeIfAbsent("error", _ -> scheme.getError());
    }

    public int getOnError() {
        return cache.computeIfAbsent("on_error", _ -> scheme.getOnError());
    }

    public int getErrorContainer() {
        return cache.computeIfAbsent("error_container", _ -> scheme.getErrorContainer());
    }

    public int getOnErrorContainer() {
        return cache.computeIfAbsent("on_error_container", _ -> scheme.getOnErrorContainer());
    }

    public int getErrorPaletteKeyColor() {
        return cache.computeIfAbsent("error_palette_key_color", _ -> scheme.errorPalette.getKeyColor().toInt());
    }

    public int getBackground() {
        return cache.computeIfAbsent("background", _ -> scheme.getBackground());
    }

    public int getOnBackground() {
        return cache.computeIfAbsent("on_background", _ -> scheme.getOnBackground());
    }

    public int getSurface() {
        return cache.computeIfAbsent("surface", _ -> scheme.getSurface());
    }

    public int getOnSurface() {
        return cache.computeIfAbsent("on_surface", _ -> scheme.getOnSurface());
    }

    public int getSurfaceVariant() {
        return cache.computeIfAbsent("surface_variant", _ -> scheme.getSurfaceVariant());
    }

    public int getOnSurfaceVariant() {
        return cache.computeIfAbsent("on_surface_variant", _ -> scheme.getOnSurfaceVariant());
    }

    public int getOutline() {
        return cache.computeIfAbsent("outline", _ -> scheme.getOutline());
    }

    public int getOutlineVariant() {
        return cache.computeIfAbsent("outline_variant", _ -> scheme.getOutlineVariant());
    }

    public int getShadow() {
        return cache.computeIfAbsent("shadow", _ -> scheme.getShadow());
    }

    public int getScrim() {
        return cache.computeIfAbsent("scrim", _ -> scheme.getScrim());
    }

    public int getInverseSurface() {
        return cache.computeIfAbsent("inverse_surface", _ -> scheme.getInverseSurface());
    }

    public int getInverseOnSurface() {
        return cache.computeIfAbsent("inverse_on_surface", _ -> scheme.getInverseOnSurface());
    }

    public int getInversePrimary() {
        return cache.computeIfAbsent("inverse_primary", _ -> scheme.getInversePrimary());
    }

    public int getPrimaryFixed() {
        return cache.computeIfAbsent("primary_fixed", _ -> scheme.getPrimaryFixed());
    }

    public int getOnPrimaryFixed() {
        return cache.computeIfAbsent("on_primary_fixed", _ -> scheme.getOnPrimaryFixed());
    }

    public int getPrimaryFixedDim() {
        return cache.computeIfAbsent("primary_fixed_dim", _ -> scheme.getPrimaryFixedDim());
    }

    public int getOnPrimaryFixedVariant() {
        return cache.computeIfAbsent("on_primary_fixed_variant", _ -> scheme.getOnPrimaryFixedVariant());
    }

    public int getSecondaryFixed() {
        return cache.computeIfAbsent("secondary_fixed", _ -> scheme.getSecondaryFixed());
    }

    public int getOnSecondaryFixed() {
        return cache.computeIfAbsent("on_secondary_fixed", _ -> scheme.getOnSecondaryFixed());
    }

    public int getSecondaryFixedDim() {
        return cache.computeIfAbsent("secondary_fixed_dim", _ -> scheme.getSecondaryFixedDim());
    }

    public int getOnSecondaryFixedVariant() {
        return cache.computeIfAbsent("on_secondary_fixed_variant", _ -> scheme.getOnSecondaryFixedVariant());
    }

    public int getTertiaryFixed() {
        return cache.computeIfAbsent("tertiary_fixed", _ -> scheme.getTertiaryFixed());
    }

    public int getOnTertiaryFixed() {
        return cache.computeIfAbsent("on_tertiary_fixed", _ -> scheme.getOnTertiaryFixed());
    }

    public int getTertiaryFixedDim() {
        return cache.computeIfAbsent("tertiary_fixed_dim", _ -> scheme.getTertiaryFixedDim());
    }

    public int getOnTertiaryFixedVariant() {
        return cache.computeIfAbsent("on_tertiary_fixed_variant", _ -> scheme.getOnTertiaryFixedVariant());
    }

    public int getSurfaceDim() {
        return cache.computeIfAbsent("surface_dim", _ -> scheme.getSurfaceDim());
    }

    public int getSurfaceBright() {
        return cache.computeIfAbsent("surface_bright", _ -> scheme.getSurfaceBright());
    }

    public int getSurfaceContainerLowest() {
        return cache.computeIfAbsent("surface_container_lowest", _ -> scheme.getSurfaceContainerLowest());
    }

    public int getSurfaceContainerLow() {
        return cache.computeIfAbsent("surface_container_low", _ -> scheme.getSurfaceContainerLow());
    }

    public int getSurfaceContainer() {
        return cache.computeIfAbsent("surface_container", _ -> scheme.getSurfaceContainer());
    }

    public int getSurfaceContainerHigh() {
        return cache.computeIfAbsent("surface_container_high", _ -> scheme.getSurfaceContainerHigh());
    }

    public int getSurfaceContainerHighest() {
        return cache.computeIfAbsent("surface_container_highest", _ -> scheme.getSurfaceContainerHighest());
    }

    public int getNeutralPaletteKeyColor() {
        return cache.computeIfAbsent("neutral_palette_key_color", _ -> scheme.getNeutralPaletteKeyColor());
    }

    public int getNeutralVariantPaletteKeyColor() {
        return cache.computeIfAbsent("neutral_variant_palette_key_color", _ -> scheme.getNeutralVariantPaletteKeyColor());
    }

    public DynamicScheme getScheme() {
        return scheme;
    }

    /* Palettes */

    public TonalPalette primaryPalette() {
        return scheme.primaryPalette;
    }

    public TonalPalette secondaryPalette() {
        return scheme.secondaryPalette;
    }

    public TonalPalette tertiaryPalette() {
        return scheme.tertiaryPalette;
    }

    public TonalPalette errorPalette() {
        return scheme.errorPalette;
    }

    public TonalPalette neutralPalette() {
        return scheme.neutralPalette;
    }

    public TonalPalette neutralVariantPalette() {
        return scheme.neutralVariantPalette;
    }
}
