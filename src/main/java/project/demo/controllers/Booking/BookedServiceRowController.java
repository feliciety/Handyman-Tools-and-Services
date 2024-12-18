package project.demo.controllers.Booking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BookedServiceRowController {

    @FXML public Label ServiceFeelabel;
    @FXML public Label jobcomplexityLabel;
    @FXML
    private Label nameLabel;


    /**
     * Sets the values for the booked service row.
     *
     * @param serviceName      the name of the service
     * @param serviceFee       the fee for the service
     * @param jobComplexity    the complexity of the job
     */
    public void setServiceData(String serviceName, double serviceFee, String jobComplexity) {
        System.out.println("[DEBUG] setServiceData called.");
        System.out.println("[DEBUG] ServiceName: " + serviceName + ", ServiceFee: " + serviceFee + ", JobComplexity: " + jobComplexity);

        if (nameLabel == null || ServiceFeelabel == null || jobcomplexityLabel == null) {
            System.err.println("[ERROR] Labels in BookedServiceRowController are NULL. Check fx:id in FXML.");
            return;
        }

        nameLabel.setText(serviceName);
        ServiceFeelabel.setText(String.format("₱%.2f", serviceFee));
        jobcomplexityLabel.setText(jobComplexity);
        System.out.println("[DEBUG] Labels updated successfully.");
    }
}
