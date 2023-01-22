package hotelKarlita;

import hotelKarlita.model.Customer;

public class Tester {
    public static void main(String[] args) {
        Customer customer = new Customer("j@domain.com", "first", "second");
        System.out.println(customer);
        Customer customer2 = new Customer("bademail", "first", "second");

    }
}
