// Klasse für Spielfeld
package de.dhbw.catan.catan;

import javafx.scene.Group;

public class Board {
    public Group createBoard() {
        Group board = new Group();

        for (int row = 0; row < 5; row++) {
            for (int colums = Math.abs(row-2); colums < 5; colums++) { // colums = 2, 1, 0, 1, 2
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
                Hexagon hexBoard = new Hexagon(430 + colums * 140 + offsetX, 120 + row * 120, 80);
                board.getChildren().add(hexBoard);
            }
        }
    return board;
    }
}
