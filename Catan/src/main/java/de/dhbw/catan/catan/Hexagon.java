package de.dhbw.catan.catan;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.input.MouseEvent;


public class Hexagon extends Polygon {
    public Hexagon(double centerX, double centerY, double radius) {
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i - 30);
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            getPoints().addAll(x, y);
        }
        setFill(Color.BEIGE);
        setStroke(Color.BLACK);

        //Beispiel Ereignis
        setOnMouseClicked(e -> setFill(Color.ORANGE));
    }
}
