module com.irritablebabayaga {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.irritablebabayaga to javafx.fxml;
    exports com.irritablebabayaga;
    opens com.irritablebabayaga.modelo to javafx.fxml;
    exports com.irritablebabayaga.modelo;


}