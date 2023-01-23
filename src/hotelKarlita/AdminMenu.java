package hotelKarlita;

import hotelKarlita.api.AdminResource;
import hotelKarlita.model.Customer;
import hotelKarlita.model.IRoom;
import hotelKarlita.model.Room;
import hotelKarlita.model.RoomType;

import java.util.ArrayList;
import java.util.List;

import static hotelKarlita.UIHelpers.*;

public class AdminMenu {

    private static final AdminMenu instance = new AdminMenu();

    public static AdminMenu getInstance() {
        return instance;
    }

    private final AdminResource adminResource;

    private AdminMenu() {
        this.adminResource = AdminResource.getInstance();
    }

    public void adminMenu() {

        while (true) {
            println(" Admin Menu");
            println("-----------------------------------------------------------------");
            println("1. See all Customers");
            println("2. See all Rooms");
            println("3. See all Reservations");
            println("4. Add a Room");
            println("5. Back to the Main Menu");
            println("-----------------------------------------------------------------");

            int selection = enterNumber("Please select a number for the menu option", 1, 5);
            switch (selection) {
                case 1 -> printAllCustomers();
                case 2 -> printAllRooms();
                case 3 -> adminResource.displayAllReservations();
                case 4 -> createRooms();
                case 5 -> {
                    return;
                }
            }
        }
    }

    private void printAllCustomers() {
        for (Customer customer : adminResource.getAllCustomers()) {
            customer.printPerson();
        }
    }

    private void printAllRooms() {
        printRoomList(adminResource.getAllRooms());
    }

    private void createRooms() {

        List<IRoom> roomsToAdd = new ArrayList<>();
        while (true) {
            int roomNumber = enterNumber("Enter a room number", 1, 500);
            double price = enterDouble("Enter a price per night");
            int roomTypeInt = enterNumber("Enter room type: 1 for single bed, 2 for double bed", 1, 2);
            RoomType roomType = roomTypeInt == 1 ? RoomType.SINGLE : RoomType.DOUBLE;
            roomsToAdd.add(new Room(roomNumber, price, roomType));
            String anotherRoom = enterYN("Would you like to add another room? (y/n)");
            if (!anotherRoom.equalsIgnoreCase("y")) {
                adminResource.addRoom(roomsToAdd);
                return;
            }
        }

    }

}
