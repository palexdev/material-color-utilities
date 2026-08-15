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
import io.github.palexdev.mcu.vendor.contrast.Contrast;
import io.github.palexdev.mcu.vendor.hct.Hct;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.net.URL;

import static io.github.palexdev.mcu.Colors.argbToWeb;

public class Sandbox {
    static void main(String[] args) {
        Application.launch(App.class, args);
    }

    static final String reset = "\u001B[0m";
    static final String block = "\u2b1b";


    public static class App extends Application {

        @Override
        public void start(Stage stage) {
            ComboBox<SchemeVariant> variants = new ComboBox<>();
            variants.getItems().setAll(SchemeVariant.values());
            variants.getSelectionModel().select(MaterialThemeBuilder.DEFAULT_VARIANT);

            CheckBox dark = new CheckBox("Dark");

            VBox swatches = new VBox(12.0);
            swatches.setAlignment(Pos.CENTER_LEFT);
            swatches.setPadding(new Insets(16.0));

            Runnable update = () -> {
                SchemeVariant variant = variants.getSelectionModel().getSelectedItem();
                boolean isDark = dark.isSelected();
                MaterialTheme theme = MaterialThemeBuilder.theme()
                    .extraSemanticColors(true)
                    .variant(variant)
                    .generate();

                // The surface the cards sit on, so the preview matches how these would really be seen
                int surface = theme.colorScheme(isDark).getSurface();
                int onSurface = theme.colorScheme(isDark).getOnSurface();
                swatches.setBackground(fill(surface, 0.0));

                swatches.getChildren().setAll(
                    header(onSurface),
                    row("error", onSurface,
                        theme.colorScheme(isDark).getError(),
                        theme.colorScheme(isDark).getOnError(),
                        theme.colorScheme(isDark).getErrorContainer(),
                        theme.colorScheme(isDark).getOnErrorContainer())
                );
                theme.customColors().forEach((n, c) -> swatches.getChildren().add(
                    row(n, onSurface, c.color(isDark), c.onColor(isDark),
                        c.colorContainer(isDark), c.onColorContainer(isDark))
                ));

                System.out.println("========== " + variant + (isDark ? " (dark)" : " (light)") + " ==========");
                System.out.println(theme.export(ExportFormat.JAVAFX, false));
            };

            variants.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> update.run());
            dark.selectedProperty().addListener((o, ov, nv) -> update.run());
            update.run();

            VBox root = new VBox(16.0, new HBox(12.0, variants, dark), swatches);
            root.setAlignment(Pos.CENTER);
            root.setPadding(new Insets(16.0));

            Scene scene = new Scene(root, 780, 560);
            stage.setScene(scene);
            stage.show();
        }

        private HBox header(int onSurface) {
            HBox box = new HBox(8.0, spacer(), title("on-color", onSurface),
                title("forced white", onSurface), title("container", onSurface));
            box.setAlignment(Pos.CENTER_LEFT);
            return box;
        }

        /// One row per color. Three cards: the role's own on-color, white forced on the same background for
        /// comparison, and the container pair.
        private HBox row(String name, int onSurface, int color, int onColor, int container, int onContainer) {
            Label label = new Label(name);
            label.setMinWidth(80.0);
            label.setTextFill(Color.web(argbToWeb(onSurface)));
            HBox box = new HBox(8.0, label,
                card(name, color, onColor),
                card(name, color, 0xFFFFFFFF),
                card(name, container, onContainer));
            box.setAlignment(Pos.CENTER_LEFT);
            return box;
        }

        /// A block of the background color with real text on it, plus the contrast ratio of the pair.
        private VBox card(String text, int background, int foreground) {
            Label label = new Label(text);
            label.setTextFill(Color.web(argbToWeb(foreground)));
            label.setFont(Font.font("System", 15.0));

            double ratio = Contrast.ratioOfTones(
                Hct.fromInt(background).getTone(), Hct.fromInt(foreground).getTone());
            Label sub = new Label("%.2f:1 %s".formatted(ratio, ratio >= 4.5 ? "AA" : "fail"));
            sub.setTextFill(Color.web(argbToWeb(foreground)));
            sub.setFont(Font.font("System", 11.0));

            VBox box = new VBox(2.0, label, sub);
            box.setAlignment(Pos.CENTER_LEFT);
            box.setPadding(new Insets(10.0));
            box.setPrefSize(150.0, 62.0);
            box.setBackground(fill(background, 8.0));
            return box;
        }

        private Label title(String text, int onSurface) {
            Label label = new Label(text);
            label.setPrefWidth(150.0);
            label.setFont(Font.font("System", 11.0));
            label.setTextFill(Color.web(argbToWeb(onSurface)));
            return label;
        }

        private Region spacer() {
            Region region = new Region();
            region.setMinWidth(80.0);
            return region;
        }

        private Background fill(int argb, double radius) {
            return new Background(new BackgroundFill(
                Color.web(argbToWeb(argb)), new CornerRadii(radius), Insets.EMPTY));
        }
    }

    static int[] getPixels() throws Exception {
        URL res = Sandbox.class.getResource("img.jpg");
        BufferedImage bimg = ImageIO.read(res);
        int w = bimg.getWidth();
        int h = bimg.getHeight();
        if (bimg.getType() == BufferedImage.TYPE_INT_ARGB) {
            return bimg.getRGB(0, 0, w, h, null, 0, w);
        }

        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        out.getGraphics().drawImage(bimg, 0, 0, null);
        return out.getRGB(0, 0, w, h, null, 0, w);
    }

    public static String getAnsiColor(String hexColor) {
        // Remove the '#' sign if present
        if (hexColor.startsWith("#")) {
            hexColor = hexColor.substring(1);
        }

        // Parse hex values into integer RGB channels
        int r = Integer.parseInt(hexColor.substring(0, 2), 16);
        int g = Integer.parseInt(hexColor.substring(2, 4), 16);
        int b = Integer.parseInt(hexColor.substring(4, 6), 16);

        // Return the 24-bit foreground color code
        return "\u001B[38;2;" + r + ";" + g + ";" + b + "m";
    }
}
