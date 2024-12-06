package project.demo.controllers.AboutUs;

import javafx.fxml.FXML;
import project.demo.controllers.Main.MainStructureController;

public class AboutUsPageController{

    private MainStructureController mainController;

    public void setMainStructureController(MainStructureController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        System.out.println("[DEBUG] AboutUsPageController initialized.");
    }

    // Add additional methods to handle interactions on the About Us page
}
