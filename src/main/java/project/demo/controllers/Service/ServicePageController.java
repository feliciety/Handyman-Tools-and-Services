package project.demo.controllers.Service;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.demo.models.Service;

import java.io.IOException;
import java.security.cert.PolicyNode;
import java.util.*;

public class ServicePageController {

    @FXML
    private ListView<String> categoriesList;

    @FXML
    private HBox subcategoriesBox;

    @FXML
    private GridPane serviceGrid;

    @FXML
    private TextField searchField;

    private final Map<String, List<String>> subcategories = new HashMap<>();
    private final Map<String, List<Service>> servicesBySubcategory = new HashMap<>();


    @FXML
    public void initialize() {
        try {
            // Setup categories, subcategories, and services
            setupCategories();
            setupSubcategories();

            // Populate categories in the ListView
            categoriesList.setItems(javafx.collections.FXCollections.observableArrayList(subcategories.keySet()));
            categoriesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    populateSubcategories(newValue);
                }
            });

            // Setup search functionality
            searchField.textProperty().addListener((observable, oldValue, newValue) -> filterServices(newValue));
        } catch (Exception e) {
            System.err.println("[ERROR] Initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupCategories() {
        subcategories.put("Carpentry Services", Arrays.asList(
                "Made-to-Order Furniture",
                "Furniture Repair",
                "Installation Services",
                "Custom Woodwork",
                "Outdoor Carpentry"
        ));
    }

    private void setupSubcategories() {
        servicesBySubcategory.put("Made-to-Order Furniture", generateServices(List.of(
                new Service("Custom Dining Table Set", "Tailor-made dining tables and chairs", "$360 – $900", "/project/demo/imagesservices/CustomDiningTableSet.png"),
                new Service("Custom Office Desk", "Personalized office desks with ergonomic design", "$180 – $540", "/project/demo/imagesservices/CustomOfficeDesk.png"),
                new Service("Custom Bed Frames", "Wooden bed frames in various sizes and designs", "$270 – $720", "/project/demo/imagesservices/CustomBedFrames.png"),
                new Service("Custom Bookshelves", "Tailored bookshelves to fit any space", "$144 – $450", "/project/demo/imagesservices/CustomBookshelves.png"),
                new Service("Custom Kitchen Cabinets", "Space-efficient and modern cabinet designs", "$540 – $1,800", "/project/demo/imagesservices/CustomKitchenCabinets.png")
        )));

        servicesBySubcategory.put("Furniture Repair", generateServices(List.of(
                new Service("Table & Chair Repair", "Fix scratches, loose joints, or broken parts", "$27 – $90", "/project/demo/imagesservices/TableAndChairRepair.png"),
                new Service("Wood Refinishing", "Restore shine and color to wooden furniture", "$54 – $180", "/project/demo/imagesservices/WoodRefinishing.png"),
                new Service("Cabinet Door Repairs", "Repair hinges, knobs, or structural issues", "$18 – $72", "/project/demo/imagesservices/CabinetDoorRepairs.png"),
                new Service("Upholstered Furniture Repair", "Repair frames or replace fabric for chairs", "$90 – $270", "/project/demo/imagesservices/UpholsteredFurnitureRepair.png"),
                new Service("Antique Furniture Restoration", "Preserve or restore vintage pieces", "$180 – $540", "/project/demo/imagesservices/AntiqueFurnitureRestoration.png")
        )));

        // Add more subcategories here...
    }

    private List<Service> generateServices(List<Service> services) {
        return new ArrayList<>(services);
    }

    private void populateSubcategories(String category) {
        subcategoriesBox.getChildren().clear(); // Clear existing buttons

        List<String> subcategoriesForCategory = subcategories.get(category);
        if (subcategoriesForCategory != null) {
            for (String subcategory : subcategoriesForCategory) {
                Button subcategoryButton = new Button(subcategory);
                subcategoryButton.setPrefWidth(200); // Set button width for uniformity
                subcategoryButton.setStyle("-fx-background-color: #3E4546; -fx-text-fill: white; -fx-font-weight: bold;");
                subcategoryButton.setOnAction(event -> populateServices(subcategory)); // Populate services when clicked
                subcategoriesBox.getChildren().add(subcategoryButton);
            }
        }
    }

    private void populateServices(String subcategory) {
        serviceGrid.getChildren().clear(); // Clear the grid

        List<Service> services = servicesBySubcategory.get(subcategory);
        if (services != null) {
            for (int i = 0; i < services.size(); i++) {
                Service service = services.get(i);

                VBox serviceCard = createServiceCard(service); // Create a service card for each service
                serviceGrid.add(serviceCard, i % 3, i / 3); // Arrange in a grid (3 columns)
            }
        }
    }


    private VBox createServiceCard(Service service) {
        // Create a simple service card layout (you can replace this with an FXML-based card if required)
        VBox card = new VBox();
        card.setSpacing(10);
        card.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-background-color: #f9f9f9; -fx-border-radius: 5;");

        // Add service details to the card (e.g., name, description)
        Button serviceButton = new Button(service.getName());
        serviceButton.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
        card.getChildren().add(serviceButton);

        return card;
    }


    private void filterServices(String query) {
        String selectedCategory = categoriesList.getSelectionModel().getSelectedItem();

        if (selectedCategory != null) {
            List<String> subcategoriesForCategory = subcategories.get(selectedCategory);

            if (subcategoriesForCategory != null) {
                serviceGrid.getChildren().clear();
                for (String subcategory : subcategoriesForCategory) {
                    List<Service> services = servicesBySubcategory.get(subcategory);

                    if (services != null) {
                        for (Service service : services) {
                            if (service.getName().toLowerCase().contains(query.toLowerCase())) {
                                Node serviceCard = createServiceCard(service);
                                serviceGrid.add(serviceCard, serviceGrid.getChildren().size() % 3, serviceGrid.getChildren().size() / 3);
                            }
                        }
                    }
                }
            }
        } else {
            System.err.println("[ERROR] No category selected for filtering.");
        }
    }
}
