package project.demo.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.demo.models.BookServiceItem;

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

    public void addService(BookServiceItem service) {
        bookedServices.add(service);
    }

    public void removeService(BookServiceItem service) {
        bookedServices.remove(service);
    }
}
