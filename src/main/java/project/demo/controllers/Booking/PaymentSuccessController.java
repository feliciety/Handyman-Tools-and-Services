package project.demo.controllers.Booking;

import javafx.application.Application;
import javafx.stage.Stage;
import project.demo.controllers.Cart.CartPageController;

public class PaymentSuccessController extends Application {

    private BookingPageController mainController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    public void setMainController(BookingPageController bookingPageController) {
        this.mainController = mainController;
    }
}
