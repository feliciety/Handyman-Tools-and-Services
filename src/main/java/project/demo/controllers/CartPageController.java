package project.demo.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import project.demo.models.CartItem;
import project.demo.models.CartManager;
import project.demo.models.Product;

public class CartPageController {

    @FXML
    private TableView<CartItem> cartTable;

    @FXML
    private TableColumn<CartItem, ImageView> productImageCol;

    @FXML
    private TableColumn<CartItem, String> productNameCol;

    @FXML
    private TableColumn<CartItem, HBox> productQuantityCol;

    @FXML
    private TableColumn<CartItem, String> productPriceCol;

    @FXML
    private TableColumn<CartItem, Button> deleteButtonCol;

    private final ObservableList<CartItem> cartItems = CartManager.getInstance().getCartItems();

    @FXML
    public void initialize() {
        // Set up table columns
        productImageCol.setCellValueFactory(new PropertyValueFactory<>("productImage"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityControl"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        deleteButtonCol.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));


        // Bind the cartItems from CartManager to the table
        cartTable.setItems(cartItems);
    }

    public void addToCart(Product product) {
        // Add to CartManager instead of local list
        CartManager.getInstance().addProductToCart(product);
        updateTable();
    }

    public void removeCartItem(CartItem cartItem) {
        System.out.println("Removing: " + cartItem.getProductName());
        cartItems.remove(cartItem);
        updateTable();
    }

    public void updateTable() {
        // Refresh the table to reflect changes
        cartTable.refresh();
    };
}

