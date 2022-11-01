package com.idan.coupons.constants.testsConstants;

import com.idan.coupons.beans.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

import static com.idan.coupons.constants.testsConstants.UserConstants.USER_PASSWORD;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
//Constants of login to the system using a customer-type user.
public class CustomerConstants {

    //A company created to run the update customer function.
    public static final Customer customerForUpdate = new Customer("Adi", "Cohen", "Adi@gmail.com", USER_PASSWORD);

    //List of customers that created for system testing
    public static ArrayList<Customer> customersForTest() {
        final ArrayList<Customer> customers = new ArrayList<>();
        final Customer customer1 = new Customer("Idan", "Kakun", "idankakun@gmail.com", USER_PASSWORD);
        final Customer customer2 = new Customer("Eden", "Raz", "eden@gmail.com", USER_PASSWORD);
        final Customer customer3 = new Customer("Dor", "Eden", "dor@gmail.com", USER_PASSWORD);
        final Customer customer4 = new Customer("Daniel", "Perez", "daniel@gmail.com", USER_PASSWORD);
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);
        return customers;
    }
}
