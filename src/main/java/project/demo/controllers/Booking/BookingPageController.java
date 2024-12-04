package project.demo.controllers.Booking;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class BookingPageController {

    @FXML
    public AnchorPane contentPane;

    @FXML
    public GridPane bookedServiceRow;

    @FXML
    public GridPane employeeImagesgrid;


    public Label totalservicefeeLabel;
    public Label couponDiscountLabel;
    public TextField promoCodeField;
    public Button removeCouponButton;
    public Label appliedCouponLabel;
    public Label totalLabel;

    public void applyPromoCode(ActionEvent actionEvent) {
    }

    public void removeCoupon(ActionEvent actionEvent) {
    }
}
