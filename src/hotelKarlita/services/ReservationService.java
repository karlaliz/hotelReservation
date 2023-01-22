package hotelKarlita.services;

import hotelKarlita.UIHelpers;
import hotelKarlita.model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static hotelKarlita.UIHelpers.println;

public class ReservationService {

    private static final ReservationService instance = new ReservationService();

    public static ReservationService getInstance() {
        return instance;
    }

    private final List<Reservation> reservations = new ArrayList<>();

    private final Map<Integer, IRoom> rooms = new HashMap<Integer, IRoom>();

    public IRoom addRoom(int roomNumber, double price, int roomType) {
        Room newRoom = new Room(roomNumber, price, roomType == 1 ? RoomType.SINGLE : RoomType.DOUBLE);
        addRoom(newRoom);
        return newRoom;
    }

    public void addRoom(IRoom newRoom) {
        rooms.put(newRoom.getRoomNumber(), newRoom);
    }

    public Collection<IRoom> getAllRooms() {
        return sortByRoomNumber(new ArrayList<>(rooms.values()));
    }

    public IRoom getARoom(int roomPick) {
        return rooms.get(roomPick);
    }

    public Reservation reserveARoom(Date checkInDate, Date checkOutDate, Customer customer, IRoom aRoom) {
        Reservation res = new Reservation(customer, checkInDate, checkOutDate, aRoom);
        reservations.add(res);
        return res;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> available = new ArrayList<>();
        for (IRoom room : rooms.values()) {
            if (roomAvailable(room, checkInDate, checkOutDate)) {
                available.add(room);
            }
        }
        return sortByRoomNumber(available);
    }

    public boolean roomAvailable(IRoom room, Date checkInDate, Date checkOutDate) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().getRoomNumber() == room.getRoomNumber()) {
                if (dateOverlap(reservation, checkInDate, checkOutDate)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Collection<Reservation> getCustomersReservation(String email) {

        List<Reservation> list = new ArrayList<>();
        for (Reservation res : reservations) {
            if (res.getCustomer().getEmail().equals(email)) {
                list.add(res);
            }
        }
        return list;
    }

    public void printAllReservations() {
        for (Reservation res : reservations) {
            println("-.-.-.-.-.-.-.-.-.-.");
            UIHelpers.printReservation(res);
        }
        println("-.-.-.-.-.-.-.-.-.-.");
    }

    private List<IRoom> sortByRoomNumber(List<IRoom> roomList) {
        List<IRoom> newList = new ArrayList<>(roomList);
        Collections.sort(newList, new Comparator<IRoom>() {
            @Override
            public int compare(IRoom r1, IRoom r2) {
                return r1.getRoomNumber() - r2.getRoomNumber();
            }
        });
        return newList;
    }

    private static boolean dateOverlap(Reservation reservation, Date checkInDate, Date checkOutDate) {


        if (dateInBetween(reservation.getCheckInDate(), reservation.getCheckOutDate(), checkInDate)) {
            return true;
        }

        if (dateInBetween(reservation.getCheckInDate(), reservation.getCheckOutDate(), checkOutDate)) {
            return true;
        }

        return dateInBetween(checkInDate, checkOutDate, reservation.getCheckInDate());
    }

    private static boolean dateInBetween(Date checkInDate, Date checkOutDate, Date date) {
        return checkInDate.compareTo(date) <= 0 && checkOutDate.compareTo(date) >= 0;
    }

    private static void checkDates(String start, String end, String date, boolean expected) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            if (dateInBetween(df.parse(start), df.parse(end), df.parse(date)) != expected) {
                throw new RuntimeException("failed test");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void checkOverlap(Reservation r, String start, String end, boolean expected) {
        if (dateOverlap(r, makeDate(start), makeDate(end)) != expected)
            throw new RuntimeException("Failed overlap test");
    }


    public static void testDates() {

        checkDates("2022-12-24", "2023-01-05", "2022-12-24", true);
        checkDates("2022-12-24", "2023-01-05", "2022-12-26", true);
        checkDates("2022-12-24", "2023-01-05", "2023-01-04", true);
        checkDates("2022-12-24", "2023-01-05", "2023-01-05", true);

        checkDates("2022-12-24", "2023-01-05", "2022-12-23", false);
        checkDates("2022-12-24", "2023-01-05", "2023-01-06", false);
        println("testDates successful");

        Reservation r = new Reservation(new Customer(), makeDate("2022-12-24"), makeDate("2023-01-05"), new Room());
        checkOverlap(r, "2022-12-26", "2023-01-04", true);
        checkOverlap(r, "2022-12-26", "2023-01-05", true);
        checkOverlap(r, "2022-12-26", "2023-01-06", true);
        checkOverlap(r, "2022-12-23", "2023-01-04", true);
        checkOverlap(r, "2022-12-24", "2023-01-04", true);
        checkOverlap(r, "2022-12-23", "2023-01-06", true);

        checkOverlap(r, "2022-12-01", "2022-12-10", false);
        checkOverlap(r, "2022-12-01", "2022-12-23", false);
        checkOverlap(r, "2023-01-06", "2023-01-07", false);
    }

    public static void loadInitialData() {

        CustomerService customerService = CustomerService.getInstance();
        ReservationService reservationService = ReservationService.getInstance();

        Customer c1 = customerService.addCustomer("karla@hotmail.com", "Karla", "Parada");
        Customer c2 = customerService.addCustomer("luisa@hotmail.com", "Luisa", "Madrigal");
        Customer c3 = customerService.addCustomer("mirabel@hotmail.com", "Mirabel", "Madrigal");

        IRoom room1 = reservationService.addRoom(101, 150.0, 2);
        IRoom room2 = reservationService.addRoom(102, 110.0, 1);
        IRoom room3 = reservationService.addRoom(103, 150.0, 2);
        IRoom room4 = reservationService.addRoom(104, 150.0, 2);

        Reservation res1 = new Reservation(c1, makeDate("2022-12-24"), makeDate("2023-01-05"), room1);
        Reservation res2 = new Reservation(c1, makeDate("2023-01-10"), makeDate("2023-01-12"), room1);
        reservationService.reservations.add(res1);
        reservationService.reservations.add(res2);

    }

    private static Date makeDate(String str) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
