package hotelKarlita.model;

import hotelKarlita.model.Customer;
import hotelKarlita.model.IRoom;

import java.util.Date;

public class Reservation {

    public Reservation(Customer person, Date checkInDate, Date checkOutDate, IRoom room) {
        this.customer = person;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
    }

    private final Customer customer;
    private final Date checkInDate;
    private final Date checkOutDate;
    private final IRoom room;

    public Customer getCustomer() {
        return customer;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public IRoom getRoom() {
        return room;
    }
}
