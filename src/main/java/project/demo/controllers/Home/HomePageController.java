package project.demo.controllers.Home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import project.demo.controllers.Main.MainStructureController;

public class HomePageController {

    private MainStructureController mainController;

    public void setMainStructureController(MainStructureController mainController) {
        this.mainController = mainController;
        System.out.println("[INFO] MainStructureController injected into HomepageController.");
    }

    @FXML
    private void onServiceButtonClick(ActionEvent actionEvent) {
        if (mainController != null) {
            mainController.handleServiceClick(actionEvent);
        } else {
            System.err.println("[ERROR] MainStructureController not injected.");
        }
    }

    @FXML
    private void onShopButtonClick(ActionEvent actionEvent) {
        if (mainController != null) {
            mainController.handleShopClick(actionEvent);
        } else {
            System.err.println("[ERROR] MainStructureController not injected.");
        }
    }
}
