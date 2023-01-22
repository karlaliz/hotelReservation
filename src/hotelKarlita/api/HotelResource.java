package hotelKarlita.api;

import hotelKarlita.model.Customer;
import hotelKarlita.model.IRoom;
import hotelKarlita.model.Reservation;
import hotelKarlita.services.CustomerService;
import hotelKarlita.services.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    private static final HotelResource instance = new HotelResource();

    public static HotelResource getInstance() {
        return instance;
    }

    private final CustomerService customerService;

    private final ReservationService reservationService;

    public HotelResource() {
        this.customerService = CustomerService.getInstance();
        this.reservationService = ReservationService.getInstance();
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(Integer.parseInt(roomNumber));
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = customerService.getCustomer(customerEmail);
        return reservationService.reserveARoom(checkInDate, checkOutDate, customer, room);
    }

    public Collection<Reservation> getCustomersReservation(String customerEmail) {
        return reservationService.getCustomersReservation(customerEmail);
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        return reservationService.findRooms(checkInDate, checkOutDate);
    }

}
