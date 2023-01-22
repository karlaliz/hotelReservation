package hotelKarlita.api;

import hotelKarlita.model.Customer;
import hotelKarlita.model.IRoom;
import hotelKarlita.services.CustomerService;
import hotelKarlita.services.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static final AdminResource instance = new AdminResource();

    public static AdminResource getInstance() {
        return instance;
    }

    private final CustomerService customerService;

    private final ReservationService reservationService;

    public AdminResource() {
        this.customerService = CustomerService.getInstance();
        this.reservationService = ReservationService.getInstance();
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservations();
    }
}
