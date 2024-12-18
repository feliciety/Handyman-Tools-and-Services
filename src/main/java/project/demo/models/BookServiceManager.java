package project.demo.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookServiceManager {

    private static final BookServiceManager instance = new BookServiceManager();

    private final ObservableList<BookServiceItem> bookedServices = FXCollections.observableArrayList();

    private BookServiceManager() {}

    public static BookServiceManager getInstance() {
        return instance;
    }

    public ObservableList<BookServiceItem> getBookedServices() {
        return bookedServices;
    }

    public void addService(BookServiceItem newService) {
        // Check for duplicates based on service name
        boolean exists = bookedServices.stream()
                .anyMatch(service -> service.getServiceName().equals(newService.getServiceName()));

        if (!exists) {
            // Set the remove logic before adding
            newService.setOnRemoveAction(() -> {
                System.out.println("[DEBUG] Removing service via callback: " + newService.getServiceName());
                removeService();
            });

            bookedServices.add(newService);
            System.out.println("[DEBUG] Added new service: " + newService.getServiceName());
        } else {
            System.out.println("[INFO] Service already exists: " + newService.getServiceName());
        }
    }

    public void removeService() {
        if (bookedServices.isEmpty()) {
            bookedServices.clear();
        }

    }
}
