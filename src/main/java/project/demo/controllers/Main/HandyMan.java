package project.demo.controllers.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HandyMan extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
     FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project/demo/FXMLLoginSignup/MainPane.fxml"));
        //    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project/demo/MainStructure.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("HandyMan");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
