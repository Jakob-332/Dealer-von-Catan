// Klasse für Spielfeld
package de.dhbw.catan.catan;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Board {
    public Group createBoard() {
        Group board = new Group();


        // 1. Liste mit Landschaftstypen
        List<String> landschaften = new ArrayList<>(Arrays.asList(
                "Hügel", "Hügel", "Hügel",
                "Wald", "Wald", "Wald", "Wald",
                "Weide", "Weide", "Weide", "Weide",
                "Ackerland", "Ackerland", "Ackerland", "Ackerland",
                "Gebirge", "Gebirge", "Gebirge",
                "Wüste"
        ));
        Collections.shuffle(landschaften);


        // 2. Hex-Felder erzeugen & Typen zuweisen
        int index = 0;
        for (int row = 0; row < 5; row++) {
            for (int columns = Math.abs(row-2); columns < 5; columns++) { // columns = 2, 1, 0, 1, 2
                int col = 5 - Math.abs(row - 2); // 3, 4, 5, 4, 3 Anzahl der Hexagone nach Zeilen
                double offsetX = 0;//Versatz für jede Zeilen 1, 2, 4 und 5
                switch (Math.abs(row-2)) {
                    case 2:
                        offsetX = -140;
                        break;
                    case 1:
                        offsetX = -70;
                        break;
                    default:
                        break;
                }

                double x = 430 + columns * 140 + offsetX;
                double y = 120 + row * 120;
                String type = landschaften.get(index++);

                Hex hexBoard = new Hex(x, y, 80, type, index);
                board.getChildren().add(hexBoard);
            }
        }
    return board;
    }
}

//Liste mit Hex
