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
            bookedServices.add(newService);
            System.out.println("[DEBUG] Added new service: " + newService.getServiceName());
        } else {
            System.out.println("[INFO] Service already exists: " + newService.getServiceName());
        }
    }

    public void removeService(BookServiceItem service) {
        bookedServices.remove(service);
        System.out.println("[DEBUG] Removed service: " + service.getServiceName());
    }
}
