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
        setupCategories();
        setupSubcategories();
        setupServices();

        // Populate categories in the ListView
        categoriesList.setItems(javafx.collections.FXCollections.observableArrayList(subcategories.keySet()));
        categoriesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateSubcategories(newValue);
            }
        });

        // Setup search functionality
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterServices(newValue));
    }

    private void setupCategories() {
        subcategories.put("Carpentry Services", Arrays.asList(
                "Made-to-Order Furniture",
                "Furniture Repair",
                "Installation Services",
                "Custom Woodwork",
                "Outdoor Carpentry"
        ));
        // Add other categories here if needed
    }

    private void setupSubcategories() {
        servicesBySubcategory.put("Made-to-Order Furniture", generateServices(List.of(
                new Service("Custom Dining Table Set", "Tailor-made dining tables and chairs", "₱20,000 – ₱50,000", "CustomDiningTableSet.png"),
                new Service("Custom Office Desk", "Personalized office desks with ergonomic design", "₱10,000 – ₱30,000", "CustomOfficeDesk.png"),
                new Service("Custom Bed Frames", "Wooden bed frames in various sizes and designs", "₱15,000 – ₱40,000", "CustomBedFrames.png"),
                new Service("Custom Bookshelves", "Tailored bookshelves to fit any space", "₱8,000 – ₱25,000", "CustomBookshelves.png"),
                new Service("Custom Kitchen Cabinets", "Space-efficient and modern cabinet designs", "₱30,000 – ₱100,000", "CustomKitchenCabinets.png")
        )));

        servicesBySubcategory.put("Furniture Repair", generateServices(List.of(
                new Service("Table & Chair Repair", "Fix scratches, loose joints, or broken parts", "₱1,500 – ₱5,000", "TableAndChairRepair.png"),
                new Service("Wood Refinishing", "Restore shine and color to wooden furniture", "₱3,000 – ₱10,000", "WoodRefinishing.png"),
                new Service("Cabinet Door Repairs", "Repair hinges, knobs, or structural issues", "₱1,000 – ₱4,000", "CabinetDoorRepairs.png"),
                new Service("Upholstered Furniture Repair", "Repair frames or replace fabric for chairs", "₱5,000 – ₱15,000", "UpholsteredFurnitureRepair.png"),
                new Service("Antique Furniture Restoration", "Preserve or restore vintage pieces", "₱10,000 – ₱30,000", "AntiqueFurnitureRestoration.png")
        )));
        // Add other subcategories similarly
    }

    private void setupServices() {
        // Additional logic to handle services by subcategories
    }

    private List<Service> generateServices(List<Service> services) {
        return new ArrayList<>(services);
    }

    private void populateSubcategories(String category) {
        subcategoriesBox.getChildren().clear();
        List<String> subcategoriesForCategory = subcategories.get(category);
        if (subcategoriesForCategory != null) {
            for (String subcategory : subcategoriesForCategory) {
                Button subcategoryButton = new Button(subcategory);
                subcategoryButton.setOnAction(event -> populateServices(subcategory));
                subcategoryButton.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: #ccc; -fx-padding: 5;");
                subcategoriesBox.getChildren().add(subcategoryButton);
            }
        }
    }

    private void populateServices(String subcategory) {
        serviceGrid.getChildren().clear();
        List<Service> services = servicesBySubcategory.get(subcategory);
        if (services != null) {
            for (int i = 0; i < services.size(); i++) {
                Service service = services.get(i);
                try {
                    Node serviceCard = createServiceCard(service);
                    serviceGrid.add(serviceCard, i % 3, i / 3); // Populate in grid (3 columns)
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Node createServiceCard(Service service) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/fxml/ServiceCard.fxml"));
        VBox card = loader.load();

        // Set service details in the card
        project.demo.controllers.ServiceCardController controller = loader.getController();
        controller.setServiceDetails(service);

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
                                try {
                                    Node serviceCard = createServiceCard(service);
                                    serviceGrid.add(serviceCard, serviceGrid.getChildren().size() % 3, serviceGrid.getChildren().size() / 3);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
