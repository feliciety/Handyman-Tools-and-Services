package project.demo.controllers.Shop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import project.demo.DataBase.DatabaseConfig;
import project.demo.models.Product;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShopPageController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> categoriesList;

    @FXML
    private Slider priceSlider;

    @FXML
    private GridPane productGrid;

    private final ObservableList<String> categories = FXCollections.observableArrayList();
    private final List<Product> products = new ArrayList<>(); // Holds all products
    private List<Product> displayedProducts = new ArrayList<>(); // Keeps track of products currently displayed

    private final DatabaseConfig db = new DatabaseConfig();

    @FXML
    public void initialize() {
        // Load categories and products from the database
        loadCategories(); // Load categories from the database
        loadProducts();   // Load products from the database

        // Populate categories list view
        categoriesList.setItems(categories);

        // Display all products initially
        displayedProducts = new ArrayList<>(products);
        displayProducts(displayedProducts);

        // Add listener for category selection
        categoriesList.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                filterByCategory(newValue);
            }
        });

        // Add listener for search
        searchField.setOnKeyReleased(event -> searchProducts());

        // Add listener for price slider
        priceSlider.valueProperty().addListener((obs, oldValue, newValue) -> filterByPrice(newValue.doubleValue()));
    }

    /**
     * Loads categories from the database.
     */
    private void loadCategories() {
        categories.add("All Tools"); // Default category
        String query = "SELECT name FROM productcategories";

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                categories.add(resultSet.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load categories: " + e.getMessage());
        }
    }

    /**
     * Loads products from the database.
     */
    private void loadProducts() {
        String query = "SELECT p.name, p.price, p.image_path, c.name AS category FROM products p " +
                "JOIN productcategories c ON p.category_id = c.id";

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            products.clear(); // Clear the product list
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String imagePath = resultSet.getString("image_path");
                String category = resultSet.getString("category");

                products.add(new Product(name, price, imagePath, category));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load products: " + e.getMessage());
        }
    }

    /**
     * Displays products in the product grid.
     */
    private void displayProducts(List<Product> productsToDisplay) {
        productGrid.getChildren().clear(); // Clear the grid

        int column = 0;
        int row = 0;

        for (Product product : productsToDisplay) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLShopPage/ProductCard.fxml"));
                Pane productCard = loader.load();

                // Get the controller and set the product
                ProductCardController controller = loader.getController();
                controller.setProduct(product);

                productGrid.add(productCard, column, row);
                column++;
                if (column == 3) { // 3 columns per row
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("[ERROR] Failed to load product card: " + e.getMessage());
            }
        }
    }

    /**
     * Filters products by category.
     */
    private void filterByCategory(String category) {
        if (category.equalsIgnoreCase("All Tools")) {
            displayedProducts = new ArrayList<>(products); // Reset to all products
        } else {
            displayedProducts = products.stream()
                    .filter(product -> product.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }

        displayProducts(displayedProducts);
    }

    /**
     * Searches products by name.
     */
    @FXML
    private void searchProducts() {
        String searchQuery = searchField.getText().toLowerCase().trim();

        if (searchQuery.isEmpty()) {
            displayProducts(displayedProducts);
            return;
        }

        List<Product> filteredProducts = displayedProducts.stream()
                .filter(product -> product.getName().toLowerCase().contains(searchQuery))
                .collect(Collectors.toList());

        displayProducts(filteredProducts);
    }

    /**
     * Filters products by price range.
     */
    private void filterByPrice(double maxPrice) {
        List<Product> filteredProducts = displayedProducts.stream()
                .filter(product -> product.getPrice() <= maxPrice) // Compare raw double
                .collect(Collectors.toList());

        displayProducts(filteredProducts);
    }
}
