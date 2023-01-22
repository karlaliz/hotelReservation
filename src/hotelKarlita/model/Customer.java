package hotelKarlita.model;

import hotelKarlita.HotelApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static hotelKarlita.UIHelpers.println;

public class Customer {
    private String email;
    private String name;
    private String lastName;

    public Customer() {
    }

    public Customer(String email, String name, String lastName) {
        this.email = email;
        Pattern pattern = Pattern.compile(HotelApplication.EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Bad email argument");
        }

        this.name = name;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public void printPerson() {
        println("Costumer : " +
                "name= " + name +
                ", lastName= " + lastName +
                ", email= " + email);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
