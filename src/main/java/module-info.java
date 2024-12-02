module project.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.desktop;
    requires annotations;

    // Exporting and opening all necessary packages
    exports project.demo.models;
    opens project.demo.models to javafx.fxml;

    exports project.demo.controllers.Home;
    opens project.demo.controllers.Home to javafx.fxml;

    exports project.demo.controllers.Cart;
    opens project.demo.controllers.Cart to javafx.fxml;

    exports project.demo.controllers.Shop;
    opens project.demo.controllers.Shop to javafx.fxml;

    exports project.demo.controllers.LoginSignup;
    opens project.demo.controllers.LoginSignup to javafx.fxml;

    exports project.demo.controllers.Service;
    opens project.demo.controllers.Service to javafx.fxml;

    exports project.demo.controllers.Booking;
    opens project.demo.controllers.Booking to javafx.fxml;

    exports project.demo.controllers.Main;
    opens project.demo.controllers.Main to javafx.fxml;

    exports project.demo.controllers.Profile;
    opens project.demo.controllers.Profile to javafx.fxml;
}
