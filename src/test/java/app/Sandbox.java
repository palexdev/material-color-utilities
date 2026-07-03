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

import io.github.palexdev.mcu.Colors;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

public class Sandbox {
    static void main(String[] args) {
        Application.launch(App.class, args);
    }

    public static class App extends Application {

        @Override
        public void start(Stage stage) throws Exception {
            HBox root = new HBox(24.0);
            root.setAlignment(Pos.CENTER);
            root.setPadding(new Insets(12.0));

            List<String> colors = Colors.extractFromPixels(getPixels());
            colors.forEach(c -> {
                Circle circle = new Circle(32, Color.web(c));
                root.getChildren().add(circle);
            });

            Scene scene = new Scene(root, 600, 300);
            stage.setScene(scene);
            stage.show();
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
}
