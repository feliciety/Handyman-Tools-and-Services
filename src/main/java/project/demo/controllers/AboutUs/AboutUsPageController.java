package project.demo.controllers.AboutUs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import project.demo.controllers.Main.MainStructureController;

public class AboutUsPageController {

    private MainStructureController mainController;

    public void setMainStructureController(MainStructureController mainController) {
        this.mainController = mainController;
        System.out.println("[INFO] MainStructureController injected into HomepageController.");
    }

    @FXML
    private void onServiceButtonClick(ActionEvent actionEvent) {
        if (mainController != null) {
            mainController.handleBookingClick(actionEvent);
        } else {
            System.err.println("[ERROR] MainStructureController not injected.");
        }
    }
}