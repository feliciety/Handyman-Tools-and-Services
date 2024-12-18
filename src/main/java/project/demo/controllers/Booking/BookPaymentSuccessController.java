package project.demo.controllers.Booking;

import javafx.event.ActionEvent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


public class BookPaymentSuccessController {
    public Button backToServiceBTN;
    public Button downloadReceiptBTN;
    public Label AdressNoteLabel;
    public Label totalPriceLabel;
    public Label couponDiscountLabel;
    public Label serviceFeeLabel;
    public GridPane bookedServicesGridPane;
    public Label shippingAddressLabel;
    public Label bookingDateLabel;
    public Label bookingIdLabel;
    public Label phoneNumberLabel;
    public Label nameLabel;
    public Label paymentMethodLabel;
    public VBox ReceiptPane;
    private BookingPageController mainController;



    public void handleBackToServices(ActionEvent actionEvent) {
    }



    public void setMainController(BookingPageController mainController) {
        this.mainController = mainController;
    }

    public void handleDownloadReceipt(ActionEvent actionEvent) {
        try {
            WritableImage snapshot = ReceiptPane.snapshot(new SnapshotParameters(), null);
            BufferedImage bufferedImage = new BufferedImage((int) snapshot.getWidth(), (int) snapshot.getHeight(), BufferedImage.TYPE_INT_ARGB);

            for (int y = 0; y < snapshot.getHeight(); y++) {
                for (int x = 0; x < snapshot.getWidth(); x++) {
                    bufferedImage.setRGB(x, y, snapshot.getPixelReader().getArgb(x, y));
                }
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Receipt");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
            fileChooser.setInitialFileName("HandymanReceipt" + bookingIdLabel + ".png");

            File outputFile = fileChooser.showSaveDialog(new Stage());
            if (outputFile != null) {
                ImageIO.write(bufferedImage, "png", outputFile);
                System.out.println("[INFO] Receipt saved at: " + outputFile.getAbsolutePath());
            }

        } catch (Exception e) {
            System.err.println("[ERROR] Failed to save receipt: " + e.getMessage());
        }
    }
}
