module project.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens project.demo to javafx.fxml;
    exports project.demo;
    exports project.demo.controllers;
    opens project.demo.controllers to javafx.fxml;
    exports project.demo.models;
    opens project.demo.models to javafx.fxml;
}