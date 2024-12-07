module iscteiul.ista.es20241semletigrupof {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.opencsv;
    requires java.sql;
    requires java.desktop;


    opens iscteiul.ista.es20241semletigrupof to javafx.fxml;
    exports iscteiul.ista.es20241semletigrupof;
}