package project.demo.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
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
    private TableColumn<CartItem, Void> deleteButtonCol;

    private final ObservableList<CartItem> cartItems = CartManager.getInstance().getCartItems();

    @FXML
    public void initialize() {
        // Set up table columns
        productImageCol.setCellValueFactory(new PropertyValueFactory<>("productImage"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityControl"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        // Center-align all columns
        centerColumnContent(productImageCol);
        centerColumnContent(productNameCol);
        centerColumnContent(productQuantityCol);
        centerColumnContent(productPriceCol);

        // Add Delete Button Column
        addDeleteButtonToTable();
        centerColumnContent(deleteButtonCol);

        // Bind the cartItems from CartManager to the table
        cartTable.setItems(cartItems);
    }

    private void addDeleteButtonToTable() {
        deleteButtonCol.setCellFactory(tc -> new TableCell<>() {
            private final Button deleteButton = new Button("X");

            {
                // Style the delete button
                deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
                deleteButton.setOnAction(event -> {
                    CartItem cartItem = getTableView().getItems().get(getIndex());
                    removeCartItem(cartItem);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
                setAlignment(Pos.CENTER);
            }
        });
    }

    public void removeCartItem(CartItem cartItem) {
        System.out.println("Removing: " + cartItem.getProductName());
        cartItems.remove(cartItem);
        updateTable();
    }

    public void updateTable() {
        cartTable.refresh();
    }

    public void addToCart(Product product) {
        CartManager.getInstance().addProductToCart(product);
        updateTable();
    }

    private <T> void centerColumnContent(TableColumn<CartItem, T> column) {
        column.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else if (item instanceof Node) {
                    setGraphic((Node) item);
                    setText(null);
                } else {
                    setText(item.toString());
                    setGraphic(null);
                }
                setAlignment(Pos.CENTER);
            }
        });
    }
}
