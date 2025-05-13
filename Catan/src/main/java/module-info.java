module de.dhbw.catan.catan {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens de.dhbw.catan.catan to javafx.fxml;
    exports de.dhbw.catan.catan;
}