package hotelKarlita;

import hotelKarlita.model.IRoom;
import hotelKarlita.model.Reservation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UIHelpers {

    public static void println(String s) {
        System.out.println(s);
    }

    static int enterNumber(String prompt, int start, int end) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            println(prompt);
            String line = scanner.nextLine();
            Pattern pattern = Pattern.compile("^\\s*(\\d+)\\s*$");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String str = matcher.group(1);
                int num = Integer.parseInt(str);
                if (num >= start && num <= end) {
                    return num;
                }
            }
            println("Please enter a number between " + start + " and " + end);
        }
    }

    static Double enterDouble(String prompt) {
        while (true) {
            String line = enterString(prompt, "^\\s*(\\d+.\\d+)\\s*$");
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                println("Invalid number. Please try again.");
            }
        }
    }

    static String enterString(String prompt, String regex) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            println(prompt);
            String line = scanner.nextLine();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                return matcher.group(1);
            }
            println("Invalid input. Please try again");
        }
    }

    static String enterYN(String prompt) {
        return enterString(prompt, "^\\s*([yYnN])\\s*$");
    }

    static String enterName(String prompt) {
        return enterString(prompt, "^\\s*([A-Za-z\\s]+)\\s*$");
    }

    static String enterEmail(String prompt) {
        return enterString(prompt, HotelApplication.EMAIL_REGEX);
    }

    static Date enterDate(String prompt) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        while (true) {
            String line = enterString(prompt, "^\\s*(\\d{2}/\\d{2}/\\d{4})\\s*$");
            try {
                return df.parse(line);
            } catch (ParseException e) {
                println("Invalid date. Please try again.");
            }
        }
    }

    public static void printRoom(IRoom room) {
        println("Room Number= " + room.getRoomNumber() +
                ", Price= $" + room.getRoomPrice() +
                ", Room Type= " + room.getRoomType());
    }

    public static void printRoomList(Collection<IRoom> rooms) {

        for (IRoom room : rooms) {
            printRoom(room);
        }

    }

    public static void printReservation(Reservation res) {
        println("Your Reservation: ");
        println("Reservation Name: " + res.getCustomer().getName() + " " + res.getCustomer().getLastName());
        println("Email: " + res.getCustomer().getEmail());
        println("Check In date: " + res.getCheckInDate());
        println("Check Out date: " + res.getCheckOutDate());
        println("Room Number= " + res.getRoom().getRoomNumber() + ", Price= $" + res.getRoom().getRoomPrice() +
                ", Room Type= " + res.getRoom().getRoomType());
    }
}
