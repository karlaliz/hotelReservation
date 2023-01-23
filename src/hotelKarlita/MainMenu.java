package hotelKarlita;

import hotelKarlita.api.HotelResource;
import hotelKarlita.model.Customer;
import hotelKarlita.model.IRoom;
import hotelKarlita.model.Reservation;
import hotelKarlita.model.Room;

import java.util.Collection;
import java.util.Date;

import static hotelKarlita.UIHelpers.*;

public class MainMenu {

    private static final MainMenu instance = new MainMenu();

    private final HotelResource hotelResource;

    private final AdminMenu adminMenu;

    private MainMenu() {
        this.hotelResource = HotelResource.getInstance();
        this.adminMenu = AdminMenu.getInstance();
    }

    public static MainMenu getInstance() {
        return instance;
    }
    public void mainMenu() {
        while (true) {
            println("Welcome to the Hotel Reservation Application");
            println("-----------------------------------------------------------------");
            println("1. Find and reserve a room");
            println("2. See my reservations");
            println("3. Create an Account");
            println("4. Admin");
            println("5. Exit");
            println("-----------------------------------------------------------------");

            int selection = enterNumber("Please select a number for the menu option", 1, 5);

            switch (selection) {
                case 1 -> makeReservation();
                case 2 -> viewMyReservations();
                case 3 -> createAnAccount();
                case 4 -> adminMenu.adminMenu();
                case 5 -> {
                    return;
                }
            }
        }
    }

    private void makeReservation() {
        Date checkInDate = null;
        Date checkOutDate = null;
        while (true) {
            checkInDate = enterDate("Enter Check In Date mm/dd/yyyy example 02/15/2023");
            checkOutDate = enterDate("Enter Check Out Date mm/dd/yyyy example 02/20/2023");
            if (checkOutDate.after(checkInDate)) {
                break;
            }
            println("Check out date should be posterior to Check in date. Please try again.");
        }

        Collection<IRoom> available = hotelResource.findARoom(checkInDate, checkOutDate);
        if (available.isEmpty()) {
            println("There are no available rooms on " + checkInDate + ". Trying the following week.");
            checkInDate = addDays(checkInDate, 7);
            checkOutDate = addDays(checkOutDate, 7);
            available = hotelResource.findARoom(checkInDate, checkOutDate);
            if (available.isEmpty()) {
                println("There are no available rooms on " + checkInDate + ". Sorry.");
                return;
            }
        }

        println("Found these rooms available from:" + checkInDate + " to: " + checkOutDate);
        printRoomList(available);
        int roomPick = enterNumber("What room number would you like to reserve. Enter room number", 1, 500);
        if (!available.contains(new Room(roomPick, 0.0, null))) {
            println("That room is not available. Sorry.");
            return;
        }

        Customer customer = findOrCreateAccount();
        IRoom aRoom = hotelResource.getRoom(roomPick + "");

        Reservation res = hotelResource.bookARoom(customer.getEmail(), aRoom, checkInDate, checkOutDate);

        println("-----------------------------------------------------------------");
        printReservation(res);
        println("-----------------------------------------------------------------");

    }

    private void viewMyReservations() {
        String email = enterEmail("Enter Email format: name@domain.com");
        for (Reservation res : hotelResource.getCustomersReservation(email)) {
            println("-.-.-.-.-.-.-.-.-.-.");
            printReservation(res);
        }
        println("-.-.-.-.-.-.-.-.-.-.");
    }

    private Customer findOrCreateAccount() {
        String selection = enterYN("Do you have an account with us? (y/n)");
        if (selection.equalsIgnoreCase("y")) {

            do {
                String emailKey = enterEmail("Enter Email format: name@domain.com");
                Customer person = hotelResource.getCustomer(emailKey);
                if (person != null) {
                    return person;
                }
                println("Can't find account. Please try again.");
            } while (true);
        } else {
            println("Then we will create an account.");
            return createAnAccount();
        }

    }

    private Customer createAnAccount() {
        String email = enterEmail("Enter Email format: name@domain.com");
        String name = enterName("First Name");
        String lastName = enterName("Last name");
        hotelResource.createACustomer(email, name, lastName);
        Customer customer = hotelResource.getCustomer(email);
        println("Your account has been successfully created");
        return customer;
    }

    private Date addDays(Date date, long days) {
        return new Date(date.getTime() + days * HotelApplication.MILLS_IN_A_DAY);
    }

}
