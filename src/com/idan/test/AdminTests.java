package com.idan.test;

import com.idan.coupons.beans.Company;
import com.idan.coupons.beans.Customer;
import com.idan.coupons.dao.beansDao.CouponDao;
import com.idan.coupons.enums.ClientType;
import com.idan.coupons.facade.AdminFacade;
import com.idan.coupons.facade.LoginManager;

import java.util.ArrayList;

import static com.idan.coupons.constants.testsConstants.CompanyConstants.*;
import static com.idan.coupons.constants.testsConstants.CustomerConstants.*;
import static com.idan.coupons.constants.testsConstants.UserConstants.USER_PASSWORD;
import static com.idan.test.CouponSystem.SCANNER;

//A class that implements the business logic operations of admin in the test.
public class AdminTests {

    private final AdminFacade adminFacade;

    //Constructor- creates an instance of admin facade.
    public AdminTests() {
        this.adminFacade = AdminFacade.instance;
    }


    // -----------------------------------Company-----------------------------------------

    //                                      --Create--

    //Creates all companies for the test.
    public boolean createCompanies() throws Exception {
        for (Company company : companiesForTest()) {
            adminFacade.createCompany(company);
            LoginManager.instance.login(company.getEmail(), USER_PASSWORD, ClientType.COMPANY);
        }
        ArrayList<Company> companies = adminFacade.readAllCompanies();
        for (Company company : companies) {
            if (company == null) {
                System.err.println("Test failed | failed to create companies");
                return false;
            }
        }
        return true;
    }

    //                                      --Update--

    //Updates a company details.
    public boolean updateCompany() throws Exception {
        System.out.print("Please enter a company id: ");
        Long companyId = SCANNER.nextLong();
        Company company = adminFacade.readCompany(companyId, false);
        if (company == null) {
            System.err.println("Test failed | failed to update company number " + companyId);
            return false;
        }
        company.setEmail(companyForUpdate.getEmail());
        adminFacade.updateCompany(company);
        return true;
    }

    //                                      --Delete--

    //Removes a company by company ID.
    public boolean deleteCompany() throws Exception {
        System.out.print("Please enter a company id: ");
        Long companyId = SCANNER.nextLong();
        if (!CouponDao.instance.isExistsById(companyId)) {
            System.err.println("Test failed | failed to delete company number " + companyId);
            return false;
        }
        adminFacade.deleteCompany(companyId);
        return true;
    }

    //                                      --Getters--

    // Gets a specific company by company ID.
    public boolean getCompany() throws Exception {
        System.out.print("Please enter a company id: ");
        Long companyId = SCANNER.nextLong();
        Company company = adminFacade.readCompany(companyId, false);
        if (company == null) {
            System.err.println("Test failed | failed to read company number " + companyId);
            return false;
        }
        System.out.println(company);
        return true;
    }

    // Gets all companies.
    public boolean getAllCompanies() throws Exception {
        ArrayList<Company> companies = adminFacade.readAllCompanies();
        if (companies != null) {
            System.out.println("These are all the companies we could find : \n" + companies);
            return true;
        }
        System.err.println("Test failed | failed to read all companies");
        return false;
    }


    // -----------------------------------Customer-----------------------------------------


    //                                      --Create--

    //Creates all customers for the test.
    public boolean createCustomers() throws Exception {
        for (Customer customer : customersForTest()) {
            adminFacade.createCustomer(customer);
            LoginManager.instance.login(customer.getEmail(), USER_PASSWORD, ClientType.CUSTOMER);
        }
        ArrayList<Customer> customers = adminFacade.readAllCustomers();
        for (Customer customer : customers) {
            if (customer == null) {
                System.err.println("Test failed | failed to create customers ");
                return false;
            }
        }
        return true;
    }

    //                                      --Update--

    //Updates a customer details.
    public boolean updateCustomer() throws Exception {
        System.out.print("Please enter a customer id: ");
        Long customerId = SCANNER.nextLong();
        Customer customer = adminFacade.readCustomer(customerId);
        if (customer == null) {
            System.err.println("Test failed | failed to update customer number " + customerId);
            return false;
        }
        customer.setEmail(customerForUpdate.getEmail());
        customer.setLastName(customerForUpdate.getLastName());
        adminFacade.updateCustomer(customer);
        return true;
    }

    //                                      --Delete--

    //Removes a customer by customer ID.
    public boolean deleteCustomer() throws Exception {
        System.out.print("Please enter a customer id: ");
        Long customerId = SCANNER.nextLong();
        Customer customer = adminFacade.readCustomer(customerId);
        if (customer == null) {
            System.err.println("Test failed | failed to delete customer number " + customerId);
            return false;
        }
        adminFacade.deleteCustomer(customerId);
        return true;
    }

    //                                      --Getters--

    // Gets a specific customer by customer ID.
    public boolean getCustomer() throws Exception {
        System.out.print("Please enter a customer id: ");
        Long customerId = SCANNER.nextLong();
        Customer customer = adminFacade.readCustomer(customerId);
        if (customer == null) {
            System.err.println("Test failed | failed to read customer number " + customerId);
            return false;
        }
        System.out.println(customer);
        return true;
    }

    // Gets all customers that exist in the database.
    public boolean getAllCustomers() throws Exception {
        ArrayList<Customer> customers = adminFacade.readAllCustomers();
        if (customers == null) {
            System.err.println("Test failed | failed to read all customers");
            return false;
        }
        System.out.println("These are all the customers we could find : " + customers);
        return true;
    }
}
