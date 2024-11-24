package project.demo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import project.demo.models.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServicePageController {

    @FXML
    private GridPane serviceGrid;

    @FXML
    private VBox serviceInfoVBox;

    private final List<Service> services = new ArrayList<>();

    @FXML
    public void initialize() {
        loadServices();
        displayServices();
    }

    private void loadServices() {
        services.add(new Service(
                "Carpentry Services",
                "Transform your spaces with precision-crafted carpentry. From custom furniture and cabinetry " +
                        "to structural repairs and renovations, our skilled carpenters deliver quality craftsmanship tailored to your needs.",
                "Precision-crafted furniture and repairs.",
                "/project/demo/imageservices/CarpentryServices.png"
        ));
        services.add(new Service(
                "Electrical Services",
                "\"Keep your home or office powered safely and efficiently with our professional electrical " +
                        "services. We specialize in wiring, lighting installation, electrical repairs, and troubleshooting " +
                        "to ensure a reliable and secure electrical system.",
                "Safe and efficient power solutions.",
                "/project/demo/imageservices/ElectricalServices.png"
        ));
        services.add(new Service(
                "Plumbing Services",
                "Ensure the proper flow of water in your home or business with our expert plumbing services. " +
                        "From fixing leaks and unclogging drains to installing and repairing pipes, our skilled plumbers " +
                        "handle all your water system needs with efficiency and care.",
                "Expert water flow and pipe repairs.",
                "/project/demo/imageservices/PlumbingServices.png"
        ));
        services.add(new Service(
                "Painting Services",
                "Revitalize your walls and spaces with our professional painting services. Whether it's interior" +
                        " or exterior, we provide clean, precise, and durable painting solutions that enhance the beauty " +
                        "and value of your property.",
                "Revitalize spaces with clean painting.",
                "/project/demo/imageservices/PaintingServices.png"
        ));
        services.add(new Service(
                "Roofing Services",
                "Protect your property with reliable roofing solutions. We offer roof installation, repair, " +
                        "and maintenance services, ensuring your home or business stays secure and weatherproof all year " +
                        "round.",
                "Reliable roof installation and repairs.",
                "/project/demo/imageservices/RoofingServices.png"
        ));
        services.add(new Service(
                "Masonry Services",
                "Strengthen and beautify your property with our skilled masonry services. From brick and stone " +
                        "work to concrete repairs and installations, we deliver durable and aesthetically pleasing results.",
                "Durable brick, stone, and concrete work.",
                "/project/demo/imageservices/MasonryServices.png"
        ));
        services.add(new Service(
                "Cleaning Services",
                "Enjoy a spotless and sanitized space with our comprehensive cleaning services. From residential " +
                        "deep cleaning to commercial janitorial services, we ensure your environment is healthy, fresh, " +
                        "and welcoming.",
                "Spotless spaces, residential or commercial.",
                "/project/demo/imageservices/CleaningServices.png"
        ));
        services.add(new Service(
                "Appliance Repair Services",
                "Extend the life of your appliances with our expert repair services. Whether it’s your " +
                        "refrigerator, washing machine, oven, or other appliances, our technicians provide quick and " +
                        "reliable fixes to get them running like new.",
                "Quick fixes for home appliances.",
                "/project/demo/imageservices/ApplianceRepairServices.png"
        ));
    }

    private void displayServices() {
        displayServices(services); // Use the overloaded method
    }

    private void displayServices(List<Service> servicesToDisplay) {
        serviceGrid.getChildren().clear(); // Clear previous content
        int column = 0;
        int row = 0;

        for (Service service : servicesToDisplay) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/ServiceCard.fxml"));
                VBox serviceCard = loader.load();
                ServiceCardController controller = loader.getController();
                controller.setService(service, selectedService -> {
                    // Show service details when "View Details" is clicked
                    showServiceDetails(selectedService);
                });

                serviceGrid.add(serviceCard, column, row);
                column++;
                if (column == 4) { // Adjust column count based on your layout
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void showServiceDetails(Service service) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/ServiceInfoCard.fxml"));
            VBox serviceInfoCard = loader.load();

            // Set service info in the card
            ServiceInfoCardController controller = loader.getController();
            controller.setServiceInfo(service);

            // Update the serviceInfoVBox with the loaded card
            serviceInfoVBox.getChildren().clear();
            serviceInfoVBox.getChildren().add(serviceInfoCard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
