package io.github.palexdev.mcu;

import io.github.palexdev.mcu.vendor.hct.Hct;
import io.github.palexdev.mcu.vendor.quantize.QuantizerCelebi;
import io.github.palexdev.mcu.vendor.score.Score;
import io.github.palexdev.mcu.vendor.utils.ColorUtils;

import java.util.List;
import java.util.Map;

public record Colors(
    Hct primary,
    Hct secondary,
    Hct tertiary,
    Hct error,
    Hct neutral,
    Hct neutralVariant
) {

    //================================================================================
    // Methods
    //================================================================================

    /// Extracts a list of colors (in web format) from an array of pixels in the **ARGB** format.<br >
    /// The colors are sorted by score, see [Score].
    ///
    /// To get the pixels from a `BufferedImage` you can use the following code:
    /// ```
    /// BufferedImage bimg = ...;
    ///  int w = bimg.getWidth();
    ///  int h = bimg.getHeight();
    ///  if (bimg.getType() == BufferedImage.TYPE_INT_ARGB)
    ///      return bimg.getRGB(0, 0, w, h, null, 0, w);
    ///
    ///  BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    ///  out.getGraphics().drawImage(bimg, 0, 0, null);
    ///  return out.getRGB(0, 0, w, h, null, 0, w);
    ///```
    public static List<String> extractFromPixels(int[] pixels) {
        Map<Integer, Integer> colors = QuantizerCelebi.quantize(pixels, 128);
        return Score.score(colors, 128, MaterialThemeBuilder.BASELINE_SEED.toInt()).stream()
            .map(Colors::argbToWeb)
            .toList();
    }

    /// Converts a color from hex to its argb representation.
    public static int webToArgb(String hex) {
        try {
            if (hex.charAt(0) == '#') {
                // Use a long to avoid rollovers on #ffXXXXXX
                long color = Long.parseLong(hex.substring(1), 16);
                if (hex.length() == 7) {
                    // Set the alpha value
                    color |= 0x00000000ff000000L;
                } else if (hex.length() != 9) {
                    throw new IllegalArgumentException("Unknown color");
                }
                return (int) color;
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Failed converting color", ex);
        }
        throw new IllegalArgumentException("Unknown color");
    }

    /// Hex string representing color, ex. #ff0000 for red.
    ///
    /// @param argb ARGB representation of a color.
    public static String argbToWeb(int argb) {
        int alpha = ColorUtils.alphaFromArgb(argb);
        int red = ColorUtils.redFromArgb(argb);
        int green = ColorUtils.greenFromArgb(argb);
        int blue = ColorUtils.blueFromArgb(argb);
        return alpha == 0xFF
            ? String.format("#%02X%02X%02X", red, green, blue)
            : String.format("#%02X%02X%02X%02X", red, green, blue, alpha);
    }

    /// Delegates to [#argbToWeb] using [Hct#toInt()].
    public static String hctToWeb(Hct hct) {
        return argbToWeb(hct.toInt());
    }

    /// Uses [Hct#fromInt(int)] using [#webToArgb].
    public static Hct webToHct(String hex) {
        return Hct.fromInt(webToArgb(hex));
    }

    // "Withers"

    public Colors primary(Hct primary) {
        return new Colors(primary, secondary, tertiary, error, neutral, neutralVariant);
    }

    public Colors secondary(Hct secondary) {
        return new Colors(primary, secondary, tertiary, error, neutral, neutralVariant);
    }

    public Colors tertiary(Hct tertiary) {
        return new Colors(primary, secondary, tertiary, error, neutral, neutralVariant);
    }

    public Colors error(Hct error) {
        return new Colors(primary, secondary, tertiary, error, neutral, neutralVariant);
    }

    public Colors neutral(Hct neutral) {
        return new Colors(primary, secondary, tertiary, error, neutral, neutralVariant);
    }

    public Colors neutralVariant(Hct neutralVariant) {
        return new Colors(primary, secondary, tertiary, error, neutral, neutralVariant);
    }
}
