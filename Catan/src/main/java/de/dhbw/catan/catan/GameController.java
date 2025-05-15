// JavaFX Controller Klasse mit FXML-Annotations
package de.dhbw.catan.catan;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class GameController {
    @FXML
    private AnchorPane rootPane;

    @FXML
    public void initialize() {
        Board board = new Board();
        rootPane.getChildren().addAll(board.createBoard());
    }
}
