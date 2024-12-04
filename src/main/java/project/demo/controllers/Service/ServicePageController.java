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
import project.demo.models.ServiceCardModel;

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
                if (newValue.equals("All Services")) {
                    populateAllServices();
                    subcategoriesBox.getChildren().clear(); // Clear subcategories when "All Services" is selected
                } else {
                    loadSubcategories(newValue); // Load subcategories and services for the selected category
                    populateCategoryServices(newValue); // Display services for the selected category
                }
            }
        });
    }

    private void loadSubcategories(String category) {
        subcategoriesBox.getChildren().clear();

        List<String> subcategories = fetchSubcategoriesFromDatabase(category);

        for (String subcategory : subcategories) {
            Button subcategoryButton = new Button(subcategory);
            subcategoryButton.setOnAction(event -> populateServices(subcategory)); // Display services for the selected subcategory
            subcategoryButton.setStyle(
                    "-fx-background-color: #3E4546; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 14px; " +  // Increase font size
                            "-fx-padding: 10 20 10 20; " +  // Add padding (top-right-bottom-left)
                            "-fx-pref-width: 200px; " +  // Set a preferred width
                            "-fx-pref-height: 40px;"     // Set a preferred height
            );
            subcategoriesBox.getChildren().add(subcategoryButton);
        }
    }

    private void populateAllServices() {
        serviceGrid.getChildren().clear();
        List<ServiceCardModel> serviceCardModels = fetchAllServicesFromDatabase();

        if (serviceCardModels != null) {
            populateServiceGrid(serviceCardModels);
        }
    }

    private void populateCategoryServices(String category) {
        serviceGrid.getChildren().clear();
        List<ServiceCardModel> serviceCardModels = fetchServicesByCategoryFromDatabase(category);

        if (serviceCardModels != null) {
            populateServiceGrid(serviceCardModels);
        }
    }

    private void populateServices(String subcategory) {
        serviceGrid.getChildren().clear();
        List<ServiceCardModel> serviceCardModels = fetchServicesFromDatabase(subcategory);

        if (serviceCardModels != null) {
            populateServiceGrid(serviceCardModels);
        }
    }

    private void filterServices(String query) {
        serviceGrid.getChildren().clear();

        int maxPrice = (int) servicePriceSlider.getValue();
        List<ServiceCardModel> filteredServiceCardModels = filterServicesByQueryAndPrice(query, maxPrice);

        if (filteredServiceCardModels != null) {
            populateServiceGrid(filteredServiceCardModels);
        }
    }

    private void populateServiceGrid(List<ServiceCardModel> serviceCardModels) {
        for (int i = 0; i < serviceCardModels.size(); i++) {
            ServiceCardModel serviceCardModel = serviceCardModels.get(i);
            Node serviceCard = createServiceCard(serviceCardModel);
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

    private List<ServiceCardModel> fetchAllServicesFromDatabase() {
        List<ServiceCardModel> serviceCardModels = new ArrayList<>();
        String query = "SELECT service_name, service_description, service_price, service_image_path FROM Service";

        try (Connection connection = new DatabaseConfig().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                serviceCardModels.add(extractServiceFromResultSet(resultSet));
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to fetch all serviceCardModels: " + e.getMessage());
            e.printStackTrace();
        }
        return serviceCardModels;
    }

    private List<ServiceCardModel> fetchServicesByCategoryFromDatabase(String category) {
        List<ServiceCardModel> serviceCardModels = new ArrayList<>();
        String query = "SELECT service_name, service_description, service_price, service_image_path FROM Service WHERE subcategory_id IN (SELECT subcategory_id FROM Subcategory WHERE category_id = (SELECT category_id FROM ServiceCategory WHERE category_name = ?))";

        try (Connection connection = new DatabaseConfig().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, category);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                serviceCardModels.add(extractServiceFromResultSet(resultSet));
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to fetch serviceCardModels by category: " + e.getMessage());
            e.printStackTrace();
        }
        return serviceCardModels;
    }

    private List<ServiceCardModel> fetchServicesFromDatabase(String subcategory) {
        List<ServiceCardModel> serviceCardModels = new ArrayList<>();
        String query = "SELECT service_name, service_description, service_price, service_image_path FROM Service WHERE subcategory_id = (SELECT subcategory_id FROM Subcategory WHERE subcategory_name = ?)";

        try (Connection connection = new DatabaseConfig().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, subcategory);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                serviceCardModels.add(extractServiceFromResultSet(resultSet));
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to fetch serviceCardModels: " + e.getMessage());
            e.printStackTrace();
        }
        return serviceCardModels;
    }

    private List<ServiceCardModel> filterServicesByQueryAndPrice(String query, int maxPrice) {
        List<ServiceCardModel> serviceCardModels = new ArrayList<>();
        String sqlQuery = "SELECT service_name, service_description, service_price, service_image_path FROM Service WHERE (service_name LIKE ? OR service_description LIKE ?) AND CAST(service_price AS UNSIGNED) <= ?";

        try (Connection connection = new DatabaseConfig().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            String searchTerm = "%" + (query != null ? query : "") + "%";
            preparedStatement.setString(1, searchTerm);
            preparedStatement.setString(2, searchTerm);
            preparedStatement.setInt(3, maxPrice);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                serviceCardModels.add(extractServiceFromResultSet(resultSet));
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to filter serviceCardModels: " + e.getMessage());
            e.printStackTrace();
        }
        return serviceCardModels;
    }

    private ServiceCardModel extractServiceFromResultSet(ResultSet resultSet) throws Exception {
        String name = resultSet.getString("service_name");
        String description = resultSet.getString("service_description");
        String price = resultSet.getString("service_price");
        String imagePath = resultSet.getString("service_image_path");

        return new ServiceCardModel(name, description, price, imagePath);
    }

    private Node createServiceCard(ServiceCardModel serviceCardModel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLServicePage/ServiceCard.fxml"));
            VBox card = loader.load();

            ServiceCardController controller = loader.getController();
            if (controller != null) {
                controller.setServiceDetails(serviceCardModel);
            }

            return card;
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to load ServiceCard.fxml: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
