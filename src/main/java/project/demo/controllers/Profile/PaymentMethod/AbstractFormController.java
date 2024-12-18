package project.demo.controllers.Profile.PaymentMethod;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public abstract class AbstractFormController<T> {

    @FXML
    public Button saveButton;

    @FXML
    public Button cancelButton;

    /**
     * Populates form fields with data from the given object.
     */
    public abstract void setFields(T object);

    /**
     * Save or update the data.
     */
    public abstract void onSave(javafx.event.ActionEvent actionEvent);

    /**
     * Close the current window/dialog.
     */
    public void onCancel(javafx.event.ActionEvent actionEvent) {
        closeWindow();
    }

    /**
     * Closes the current stage/window.
     */
    protected void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Hides the Save and Cancel buttons within the form.
     */
    public void hideButtons() {
        if (saveButton != null) {
            saveButton.setVisible(false);
            saveButton.setManaged(false); // To prevent it from occupying space in the layout
        }
        if (cancelButton != null) {
            cancelButton.setVisible(false);
            cancelButton.setManaged(false);
        }
    }
}