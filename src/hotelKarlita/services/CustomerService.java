package hotelKarlita.services;

import hotelKarlita.model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private static final CustomerService instance = new CustomerService();

    public static CustomerService getInstance() {
        return instance;
    }

    private final Map<String, Customer> customerAccounts = new HashMap<String, Customer>();

    public Customer addCustomer(String email, String name, String lastName) {
        Customer customer = new Customer(email, name, lastName);
        customerAccounts.put(customer.getEmail(), customer);
        return customer;
    }

    public Customer getCustomer(String customerEmail) {
        return customerAccounts.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return customerAccounts.values();
    }
}
