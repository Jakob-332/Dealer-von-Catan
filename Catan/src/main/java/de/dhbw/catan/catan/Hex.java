package de.dhbw.catan.catan;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class Hex extends Group {
    private final Polygon hexShape;
    private final Text hexText;

    public Hex(double centerX, double centerY, double radius, String type, int id) {
        hexShape = new Polygon();

        // Erstellung der Eckpunkte
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i - 30);
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            hexShape.getPoints().addAll(x, y);
        }

        // Farbe nach Landschaftstyp
        hexShape.setFill(switch (type) {
            case "Wald" -> Color.FORESTGREEN;
            case "Hügel" -> Color.DARKORANGE;
            case "Weide" -> Color.LIGHTGREEN;
            case "Ackerland" -> Color.GOLD;
            case "Gebirge" -> Color.GREY;
            case "Wüste" -> Color.BEIGE;
            default -> Color.BLACK;
        });
        hexShape.setStroke(Color.BLACK);

        // Text in der Mitte platzieren
        hexText = new Text(type + id);
        hexText.setFill(Color.BLACK);
        hexText.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        hexText.setX(centerX - 20); // Feinjustierung der Position
        hexText.setY(centerY + 5);

        this.getChildren().addAll(hexShape, hexText);
    }
}
