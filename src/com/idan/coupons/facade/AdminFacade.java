package com.idan.coupons.facade;

import com.idan.coupons.beans.Company;
import com.idan.coupons.beans.Coupons;
import com.idan.coupons.beans.Customer;
import com.idan.coupons.dao.beansDao.CompanyDao;
import com.idan.coupons.dao.beansDao.CouponDao;
import com.idan.coupons.dao.beansDao.CustomerDao;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.exceptions.ApplicationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

import static com.idan.coupons.constants.testsConstants.AdminConstants.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

//A class that expresses the business logic of an admin-type user
public class AdminFacade implements ClientFacade {

    public static final AdminFacade instance = new AdminFacade();

    private final CompanyDao companyDao = CompanyDao.instance;
    private final CouponDao couponDao = CouponDao.instance;
    private final CustomerDao customerDao = CustomerDao.instance;

    // Login method that checks if the inputs email and password are valid.
    @Override
    public boolean login(final String email, final String password) {
        return ADMIN_EMAIL.equals(email) && PASSWORD.equals(password);
    }

    // -----------------------------------Company-----------------------------------------


    //                                      --Create--

    //Creates a new company by checking in the database if it already exists.
    public Long createCompany(final Company company) throws Exception {
        if (!companyDao.isExistsByEmail(company.getEmail()) &&
                !companyDao.isExistsByName(company.getName())) {
            Long companyId = companyDao.create(company);
            System.out.println("A new company by the name " + company.getName() + " was created successfully!");
            return companyId;
        } else {
            throw new ApplicationException(ErrorType.ILLEGAL_USER_INPUT, "This input already has an account.");
        }
    }

    //                                      --Update--

    //Updates a company details.
    public void updateCompany(final Company company) throws Exception {
        if (companyDao.isExistsById(company.getId())) {
            companyDao.update(company);
            System.out.println("Company number " + company.getId() + " was updated successfully!");
        } else {
            throw new ApplicationException(ErrorType.DATA_NOT_FOUND, company.getName() + " is not found.");
        }
    }

    //                                      --Delete--

    //Removes a company by company ID.
    public void deleteCompany(final Long companyId) throws Exception {
        if (companyDao.isExistsById(companyId)) {
            ArrayList<Coupons> coupons = couponDao.getAllCouponsByCompany(companyId);
            if (coupons != null) {
                for (Coupons coupon : coupons) {
                    CompanyFacade.instance.delete(coupon.getId());
                }
            }
            companyDao.delete(companyId);
            System.out.println("The company number " + companyId + " was removed.");
        } else {
            throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "Company number " + companyId + " is not found.");
        }
    }

    //                                      --Getters--


    // Gets a specific company by company ID,adds coupons to the company if they exist.
    public Company readCompany(final Long companyId, Boolean withCoupons) throws Exception {
        if (companyDao.isExistsById(companyId)) {
            final Company company = companyDao.read(companyId);
            if (withCoupons) {
                addCouponsToCompany(company);
            }
            return company;
        } else {
            throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "Company number " + companyId + " is not found.");
        }
    }

    // Gets all the companies that exist in the database.
    public ArrayList<Company> readAllCompanies() throws Exception {
        final ArrayList<Company> companies = companyDao.readAll();
        if (companies == null) {
            throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "No companies could be found.");
        } else {
            return companies;
        }
    }

    // supporting method that adds coupons to the company
    public void addCouponsToCompany(final Company company) throws Exception {
        final ArrayList<Coupons> coupons = couponDao.getAllCouponsByCompany(company.getId());
        company.setCoupons(coupons);
    }


    // -----------------------------------Customer-----------------------------------------


    //                                      --Create--

    //Creates a new customer by checking in the database if it already exists.
    public Long createCustomer(final Customer customer) throws Exception {
        if (!customerDao.isExistsByEmail(customer.getEmail())) {
            Long customerId = customerDao.create(customer);
            System.out.println("Customer " + customer.getFirstName() + " " + customer.getLastName() + " was created successfully!");
            return customerId;
        } else {
            throw new ApplicationException(ErrorType.ILLEGAL_USER_INPUT,
                    "Customer with email " + customer.getEmail() + " This input already has an account.");
        }
    }

    //                                      --Update--

    //Updates a customer's details.
    public void updateCustomer(final Customer customer) throws Exception {
        if (customerDao.isExistsById(customer.getId())) {
            customerDao.update(customer);
            System.out.println("Customer number " + customer.getId() + " was updated successfully!");
        } else {
            throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "Customer with email " + customer.getEmail() + " is not found.");
        }
    }

    //                                      --Delete--

    //Removes a customer by customer ID.
    public void deleteCustomer(final Long customerId) throws Exception {
        if (customerDao.isExistsById(customerId)) {
            couponDao.deletePurchaseCouponByCustomerId(customerId);
            customerDao.delete(customerId);
            System.out.println("The customer by the id " + customerId + " was removed.");
        } else {
            throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "Customer number " + customerId + " is not found.");
        }
    }


    //                                      --Getters--


    // Gets a specific customer by customer ID.
    public Customer readCustomer(final Long customerId) throws Exception {
        if (customerDao.isExistsById(customerId)) {
            Customer customer = customerDao.read(customerId);
            customer.setCoupons(couponDao.getPurchasedCoupons(customerId));
            return customer;

        } else {
            throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "Customer number " + customerId + " is not found.");
        }
    }

    // Gets all the customers that exist in the database.
    public ArrayList<Customer> readAllCustomers() throws Exception {
        final ArrayList<Customer> allCustomers = customerDao.readAll();
        if (allCustomers != null) {
            for (Customer customer : allCustomers) {
                customer.setCoupons(couponDao.getPurchasedCoupons(customer.getId()));
            }
            return allCustomers;
        } else {
            throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "No customers could be found.");
        }
    }
}
