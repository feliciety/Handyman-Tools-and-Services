package project.demo.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import project.demo.models.Product;

import java.io.IOException;
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

    private final List<Product> products = new ArrayList<>(); // Holds all products
    private List<Product> displayedProducts = new ArrayList<>(); // Keeps track of products currently displayed

    @FXML
    public void initialize() {
        // Populate categories
        categoriesList.setItems(FXCollections.observableArrayList(
                    "All Tools",
                "Safety Gear",
                "Measuring and Marking Tools",
                "Cutting Tools",
                "Shaping and Smoothing Tools",
                "Drilling and Fastening Tools",
                "Joining Tools and Supplies",
                "Support Tools",
                "Storage and Organization",
                "Wood and Materials",
                "Cleanup Supplies"
        ));

        // Load products
        loadProducts();

        // Display all products initially
        displayedProducts = new ArrayList<>(products); // Initially, all products are displayed
        displayProducts(displayedProducts);

        // Add listener for category selection
        categoriesList.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                filterByCategory(newValue);
            }
        });

        // Add listener for search
        searchField.setOnKeyReleased(event -> searchProducts());
        priceSlider.valueProperty().addListener((obs, oldValue, newValue) -> filterByPrice(newValue.doubleValue()));
    }

    private void loadProducts() {
        // Safety Gear Products
        products.add(new Product("Dust Mask", "$8.00", "/project/demo/imageproducts/dust_masks1.png", "Safety Gear"));
        products.add(new Product("Filter Mask", "$9.00", "/project/demo/imageproducts/dust_masks2.png", "Safety Gear"));
        products.add(new Product("Foam Plugs", "$10.00", "/project/demo/imageproducts/ear_protection1.png", "Safety Gear"));
        products.add(new Product("Ear Muffs", "$12.00", "/project/demo/imageproducts/ear_protection2.png", "Safety Gear"));
        products.add(new Product("Reusable Plugs", "$11.00", "/project/demo/imageproducts/ear_protection3.png", "Safety Gear"));
        products.add(new Product("Noise Muffs", "$15.00", "/project/demo/imageproducts/ear_protection4.png", "Safety Gear"));
        products.add(new Product("Work Gloves", "$7.00", "/project/demo/imageproducts/gloves1.png", "Safety Gear"));
        products.add(new Product("Heat Gloves", "$8.00", "/project/demo/imageproducts/gloves2.png", "Safety Gear"));
        products.add(new Product("Cut Gloves", "$9.00", "/project/demo/imageproducts/gloves3.png", "Safety Gear"));
        products.add(new Product("Safety Goggles", "$12.00", "/project/demo/imageproducts/safety_goggles1.png", "Safety Gear"));
        products.add(new Product("UV Goggles", "$13.00", "/project/demo/imageproducts/safety_goggles2.png", "Safety Gear"));
        products.add(new Product("Waterproof Boots", "$55.00", "/project/demo/imageproducts/steel_toed_boots1.png", "Safety Gear"));
        products.add(new Product("Insulated Boots", "$60.00", "/project/demo/imageproducts/steel_toed_boots2.png", "Safety Gear"));

        products.add(new Product("Carpenter's Square", "$12.00", "/project/demo/imageproducts/Carpentersquare1.png", "Measuring and Marking Tools"));
        products.add(new Product("Framing Square", "$15.00", "/project/demo/imageproducts/Carpentersquare2.png", "Measuring and Marking Tools"));
        products.add(new Product("Blue Chalk Line", "$8.00", "/project/demo/imageproducts/chalkline1.png", "Measuring and Marking Tools"));
        products.add(new Product("Heavy-Duty Chalk Line", "$10.00", "/project/demo/imageproducts/chalkline2.png", "Measuring and Marking Tools"));
        products.add(new Product("Combination Square", "$14.00", "/project/demo/imageproducts/Combinationsquare1.png", "Measuring and Marking Tools"));
        products.add(new Product("Adjustable Combination Square", "$18.00", "/project/demo/imageproducts/Combinationsquare2.png", "Measuring and Marking Tools"));
        products.add(new Product("Steel Measuring Tape", "$6.00", "/project/demo/imageproducts/measuringtape1.png", "Measuring and Marking Tools"));
        products.add(new Product("Retractable Measuring Tape", "$7.00", "/project/demo/imageproducts/measuringtape2.png", "Measuring and Marking Tools"));
        products.add(new Product("Double-Sided Measuring Tape", "$9.00", "/project/demo/imageproducts/measuringtape3.png", "Measuring and Marking Tools"));
        products.add(new Product("Spirit Level", "$11.00", "/project/demo/imageproducts/Spiritlevel1.png", "Measuring and Marking Tools"));
        products.add(new Product("Bubble Spirit Level", "$12.00", "/project/demo/imageproducts/Spiritlevel2.png", "Measuring and Marking Tools"));
        products.add(new Product("Magnetic Spirit Level", "$14.00", "/project/demo/imageproducts/Spiritlevel3.png", "Measuring and Marking Tools"));
    }

    private void displayProducts(List<Product> productsToDisplay) {
        productGrid.getChildren().clear(); // Clear the grid

        int column = 0;
        int row = 0;

        for (Product product : productsToDisplay) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/productCard.fxml"));
                Pane productCard = loader.load();

                // Get the controller and set the product
                ProductCardController controller = loader.getController();
                if (controller != null) {
                    controller.setProduct(product);
                } else {
                    System.err.println("Controller is null for productCard.fxml");
                }

                // Add product card to the grid
                productGrid.add(productCard, column, row);
                column++;
                if (column == 4) { // 4 columns per row
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                System.err.println("Failed to load product card: " + e.getMessage());
            }
        }
    }

    private void filterByCategory(String category) {
        if (category.equalsIgnoreCase("All Tools")) {
            // If "All Tools" is selected, display all products
            displayedProducts = new ArrayList<>(products);
        } else {
            // Filter products by the selected category
            displayedProducts = products.stream()
                    .filter(product -> product.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }

        // Display the filtered products
        displayProducts(displayedProducts);

        // Apply the price filter to the filtered products
        filterByPrice(priceSlider.getValue());
    }

    @FXML
    private void searchProducts() {
        String searchQuery = searchField.getText().toLowerCase().trim();

        if (searchQuery.isEmpty()) {
            // If search field is empty, reset to displayedProducts
            displayProducts(displayedProducts);
            return;
        }

        List<Product> filteredProducts = displayedProducts.stream()
                .filter(product -> product.getName().toLowerCase().contains(searchQuery))
                .collect(Collectors.toList());

        displayProducts(filteredProducts);
    }

    @FXML
    private void filterProducts() {
        double maxPrice = priceSlider.getValue();

        List<Product> filteredProducts = displayedProducts.stream()
                .filter(product -> {
                    String priceText = product.getPrice().replace("$", "");
                    double price = Double.parseDouble(priceText);
                    return price <= maxPrice;
                })
                .collect(Collectors.toList());

        displayProducts(filteredProducts);
    }

    private void filterByPrice(double maxPrice) {
        List<Product> filteredProducts = displayedProducts.stream()
                .filter(product -> {
                    double price = Double.parseDouble(product.getPrice().replace("$", ""));
                    return price <= maxPrice;
                })
                .collect(Collectors.toList());
        displayProducts(filteredProducts);
    }

    @FXML
    private void addToCart(Product product) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/CartPage.fxml"));
        CartPageController cartPageController = loader.getController();
        cartPageController.addToCart(product);
    }

}
