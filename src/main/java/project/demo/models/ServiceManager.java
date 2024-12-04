package project.demo.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ServiceManager {

    private static ServiceManager instance;
    private final ObservableList<ServiceItem> serviceItems;

    private ServiceManager() {
        this.serviceItems = FXCollections.observableArrayList();
    }

    public static ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager();
        }
        return instance;
    }

    public ObservableList<ServiceItem> getServiceItems() {
        return serviceItems;
    }

    // Add a service to the list
    public void addService(ServiceItem serviceItem) {
        serviceItems.add(serviceItem);
    }

    // Remove a service from the list
    public void removeService(ServiceItem serviceItem) {
        serviceItems.remove(serviceItem);
    }

    // Clear all services
    public void clearServices() {
        serviceItems.clear();
    }
}
