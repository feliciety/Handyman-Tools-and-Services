package project.demo.controllers.Service;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.demo.DataBase.DatabaseConfig;
import project.demo.models.Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServicePageController {

    @FXML
    private ListView<String> categoriesList;

    @FXML
    private HBox subcategoriesBox;

    @FXML
    private GridPane serviceGrid;

    @FXML
    private TextField searchField;

    @FXML
    private Slider servicePriceSlider;

    @FXML
    private Label priceLabel;

    @FXML
    public void initialize() {
        loadCategories();
        populateAllServices();

        // Add a listener for the search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterServices(newValue);
        });

        // Add a listener for the price slider
        servicePriceSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            priceLabel.setText("Max Price: $" + newValue.intValue());
            filterServices(searchField.getText());
        });
    }

    private void loadCategories() {
        List<String> categories = fetchCategoriesFromDatabase();
        categories.add(0, "All Services"); // Add "All Services" option at the top
        categoriesList.setItems(FXCollections.observableArrayList(categories));

        // Add listener for category selection
        categoriesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("[DEBUG] Selected category: " + newValue);

                if (newValue.equals("All Services")) {
                    populateAllServices(); // Show all services
                } else {
                    // Populate services under the selected category
                    populateServicesByCategory(newValue);

                    // Load its subcategories for further filtering
                    loadSubcategories(newValue);
                }
            }
        });
    }

    private void populateServicesByCategory(String category) {
        serviceGrid.getChildren().clear();
        System.out.println("[DEBUG] Populating services for category: " + category);

        List<Service> services = fetchServicesByCategoryFromDatabase(category);

        if (services != null) {
            populateServiceGrid(services);
        }
    }

    private List<Service> fetchServicesByCategoryFromDatabase(String category) {
        List<Service> services = new ArrayList<>();
        String query = "SELECT service_name, service_description, service_price, service_image_path " +
                "FROM Service WHERE category_id = (SELECT category_id FROM ServiceCategory WHERE category_name = ?)";

        try (Connection connection = new DatabaseConfig().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, category);
            System.out.println("[DEBUG] Executing query for category: " + category);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                services.add(extractServiceFromResultSet(resultSet));
            }

            System.out.println("[DEBUG] Services fetched for category: " + category + ", Count: " + services.size());
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to fetch services for category: " + category);
            e.printStackTrace();
        }
        return services;
    }

    private void loadSubcategories(String category) {
        subcategoriesBox.getChildren().clear();

        List<String> subcategories = fetchSubcategoriesFromDatabase(category);

        for (String subcategory : subcategories) {
            Button subcategoryButton = new Button(subcategory);

            subcategoryButton.setPrefWidth(200);
            subcategoryButton.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #555555, #444444);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 12px;" +
                            "-fx-border-radius: 12px;" +
                            "-fx-padding: 10px 20px;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(three-pass-box, #222222, 0, 1, 0, 3);"
            );

            // Add Action Logic to Filter Services
            subcategoryButton.setOnAction(event -> {
                System.out.println("[DEBUG] Subcategory Button Clicked: " + subcategory);
                populateServices(subcategory); // Filter services for the selected subcategory
            });

            // Hover Effect
            subcategoryButton.setOnMouseEntered(event -> {
                subcategoryButton.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #666666, #555555);" +
                                "-fx-text-fill: white;" +
                                "-fx-font-weight: bold;" +
                                "-fx-background-radius: 12px;" +
                                "-fx-border-radius: 12px;" +
                                "-fx-padding: 10px 20px;" +
                                "-fx-cursor: hand;" +
                                "-fx-effect: dropshadow(three-pass-box, #222222, 0, 1, 0, 3);"
                );
            });

            // Reset to Default
            subcategoryButton.setOnMouseExited(event -> {
                subcategoryButton.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #555555, #444444);" +
                                "-fx-text-fill: white;" +
                                "-fx-font-weight: bold;" +
                                "-fx-background-radius: 12px;" +
                                "-fx-border-radius: 12px;" +
                                "-fx-padding: 10px 20px;" +
                                "-fx-cursor: hand;" +
                                "-fx-effect: dropshadow(three-pass-box, #222222, 0, 1, 0, 3);"
                );
            });

            // Pressed Effect
            subcategoryButton.setOnMousePressed(event -> {
                subcategoryButton.setStyle(
                        "-fx-background-color: #444444;" +
                                "-fx-text-fill: white;" +
                                "-fx-font-weight: bold;" +
                                "-fx-background-radius: 12px;" +
                                "-fx-border-radius: 12px;" +
                                "-fx-padding: 10px 20px;" +
                                "-fx-translate-y: 3px;" +
                                "-fx-effect: none;"
                );
            });

            subcategoryButton.setOnMouseReleased(event -> {
                subcategoryButton.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #666666, #555555);" +
                                "-fx-text-fill: white;" +
                                "-fx-font-weight: bold;" +
                                "-fx-background-radius: 12px;" +
                                "-fx-border-radius: 12px;" +
                                "-fx-padding: 10px 20px;" +
                                "-fx-cursor: hand;" +
                                "-fx-effect: dropshadow(three-pass-box, #222222, 0, 1, 0, 3);"
                );
            });

            subcategoriesBox.getChildren().add(subcategoryButton);
        }
    }


    private void populateAllServices() {
        serviceGrid.getChildren().clear();
        List<Service> services = fetchAllServicesFromDatabase();

        if (services != null) {
            populateServiceGrid(services);
        }
    }

    private void populateServices(String subcategory) {
        serviceGrid.getChildren().clear();
        System.out.println("[DEBUG] Populating services for subcategory: " + subcategory);

        List<Service> services = fetchServicesFromDatabase(subcategory);

        if (services != null) {
            populateServiceGrid(services);
        }
    }


    private void filterServices(String query) {
        serviceGrid.getChildren().clear();

        int maxPrice = (int) servicePriceSlider.getValue();
        List<Service> filteredServices = filterServicesByQueryAndPrice(query, maxPrice);

        if (filteredServices != null) {
            populateServiceGrid(filteredServices);
        }
    }

    private void populateServiceGrid(List<Service> services) {
        for (int i = 0; i < services.size(); i++) {
            Service service = services.get(i);
            Node serviceCard = createServiceCard(service);
            if (serviceCard != null) {
                serviceGrid.add(serviceCard, i % 3, i / 3); // Arrange in 3 columns
            }
        }
    }

    private List<String> fetchCategoriesFromDatabase() {
        List<String> categories = new ArrayList<>();
        String query = "SELECT DISTINCT category_name FROM ServiceCategory";

        try (Connection connection = new DatabaseConfig().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                categories.add(resultSet.getString("category_name"));
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to fetch categories: " + e.getMessage());
            e.printStackTrace();
        }
        return categories;
    }

    private List<String> fetchSubcategoriesFromDatabase(String category) {
        List<String> subcategories = new ArrayList<>();
        String query = "SELECT subcategory_name FROM Subcategory WHERE category_id = (SELECT category_id FROM ServiceCategory WHERE category_name = ?)";

        try (Connection connection = new DatabaseConfig().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, category);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                subcategories.add(resultSet.getString("subcategory_name"));
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to fetch subcategories: " + e.getMessage());
            e.printStackTrace();
        }
        return subcategories;
    }

    private List<Service> fetchAllServicesFromDatabase() {
        List<Service> services = new ArrayList<>();
        String query = "SELECT service_name, service_description, service_price, service_image_path FROM Service";

        try (Connection connection = new DatabaseConfig().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                services.add(extractServiceFromResultSet(resultSet));
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to fetch all services: " + e.getMessage());
            e.printStackTrace();
        }
        return services;
    }

    private List<Service> fetchServicesFromDatabase(String subcategory) {
        List<Service> services = new ArrayList<>();
        String query = "SELECT service_name, service_description, service_price, service_image_path " +
                "FROM Service WHERE subcategory_id = (SELECT subcategory_id FROM Subcategory WHERE subcategory_name = ?)";

        try (Connection connection = new DatabaseConfig().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, subcategory);
            System.out.println("[DEBUG] Executing query for subcategory: " + subcategory);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                services.add(extractServiceFromResultSet(resultSet));
            }

            System.out.println("[DEBUG] Services fetched for subcategory: " + subcategory + ", Count: " + services.size());
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to fetch services for subcategory: " + subcategory);
            e.printStackTrace();
        }
        return services;
    }


    private List<Service> filterServicesByQueryAndPrice(String query, int maxPrice) {
        List<Service> services = new ArrayList<>();
        String sqlQuery = "SELECT service_name, service_description, service_price, service_image_path " +
                "FROM Service WHERE (service_name LIKE ? OR service_description LIKE ?) " +
                "AND CAST(service_price AS UNSIGNED) <= ?";

        try (Connection connection = new DatabaseConfig().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            String searchTerm = "%" + (query != null ? query : "") + "%";
            preparedStatement.setString(1, searchTerm);
            preparedStatement.setString(2, searchTerm);
            preparedStatement.setInt(3, maxPrice);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                services.add(extractServiceFromResultSet(resultSet));
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to filter services: " + e.getMessage());
            e.printStackTrace();
        }
        return services;
    }

    private Service extractServiceFromResultSet(ResultSet resultSet) throws Exception {
        String name = resultSet.getString("service_name");
        String description = resultSet.getString("service_description");
        String price = resultSet.getString("service_price");
        String imagePath = resultSet.getString("service_image_path");

        return new Service(name, description, price, imagePath);
    }

    private Node createServiceCard(Service service) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLServicePage/ServiceCard.fxml"));
            VBox card = loader.load();

            ServiceCardController controller = loader.getController();
            if (controller != null) {
                controller.setServiceDetails(service);
            }

            return card;
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to load ServiceCard.fxml: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
