package com.idan.test;

import com.idan.coupons.Threads.CouponExpirationDailyJob;
import com.idan.coupons.enums.ClientType;
import com.idan.coupons.exceptions.RunTestError;
import com.idan.coupons.facade.LoginManager;
import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

//A class that defines the system operating interface.
public class CouponSystem {

    public final static Scanner SCANNER = new Scanner(System.in);
    private static int cmd;
    @Getter
    @Setter
    private static boolean isRunningTests = true;

    //Initial display when the system is booted.
    public static void runTest() throws Throwable {
        while (isRunningTests) {

//            //Activates the daily job in the system boot.
//            final Thread dailyJob = new Thread(new CouponExpirationDailyJob());
//            dailyJob.start();

            //Menu that directs actions without login.
            System.out.println("Hello dear user!\n" +
                    "Please choose an action: \n" +
                    "1 - create tables\n" +
                    "2 - drop tables\n" +
                    "3- log in to the system\n" +
                    "4 - exit");
            int action = SCANNER.nextInt();
            switch (action) {
                case 1:
                    DataBaseInitializer.createTables();
                    break;
                case 2:
                    DataBaseInitializer.dropAllTables();
                    break;
                case 3:
                    loginToTheSystem();
                    break;
                case 4:
                    CouponSystem.setRunningTests(false);
            }
        }
    }

    //Menu that receives input of the login details from the user,the details lead to a menu adapted to the user type.
    private static void loginToTheSystem() throws Throwable {
        LoginManager loginManager = LoginManager.instance;

        System.out.println("Please log in to the system: ");
        System.out.print("User name: ");
        final String name = SCANNER.next();
        System.out.print("Email: ");
        final String email = SCANNER.next();
        System.out.print("Password: ");
        final String password = SCANNER.next();
        System.out.print("User type (ADMIN/COMPANY/CUSTOMER): ");
        final ClientType clientType = ClientType.valueOf(SCANNER.next().toUpperCase());

        switch (clientType) {
            case ADMIN:
                loginManager.login(email, password, ClientType.ADMIN);
                greeting(name);
                adminTest();
                break;
            case COMPANY:
                loginManager.login(email, password, ClientType.COMPANY);
                greeting(name);
                companyTest();
                break;
            case CUSTOMER:
                loginManager.login(email, password, ClientType.CUSTOMER);
                greeting(name);
                customerTest();
                break;
        }
    }

//     ------------------------------------AdminTest-----------------------------------------

    //Implements the admin test menu and returns according to the case if the test was successful.
    private static void adminTest() throws Throwable {

        final AdminTests adminTest;
        try {
            adminTest = new AdminTests();
        } catch (RunTestError e) {
            System.err.println("Failed to create a adminTest instance.");
            throw new RunTestError();
        }
        boolean isRunning = true;
        while (isRunning) {
            printAdminMenu();
            cmd = SCANNER.nextInt();
            switch (cmd) {
                case 1:
                    final boolean createCompanyResult = adminTest.createCompanies();
                    System.out.println("\nTEST createCompany: " + createCompanyResult);
                    break;

                case 2:
                    final boolean updateCompanyResult = adminTest.updateCompany();
                    System.out.println("\nTEST updateCompany: " + updateCompanyResult);
                    break;

                case 3:
                    final boolean deleteCompanyResult = adminTest.deleteCompany();
                    System.out.println("\nTEST deleteCompany: " + deleteCompanyResult);
                    break;

                case 4:
                    final boolean getCompanyResult = adminTest.getCompany();
                    System.out.println("\nTEST getCompany: " + getCompanyResult);
                    break;

                case 5:
                    final boolean getAllCompaniesResult = adminTest.getAllCompanies();
                    System.out.println("\nTEST getAllCompanies: " + getAllCompaniesResult);
                    break;

                case 6:
                    final boolean createCustomerResult = adminTest.createCustomers();
                    System.out.println("\nTEST createCustomer: " + createCustomerResult);
                    break;

                case 7:
                    final boolean updateCustomerResult = adminTest.updateCustomer();
                    System.out.println("\nTEST updateCustomer: " + updateCustomerResult);
                    break;

                case 8:
                    final boolean deleteCustomerResult = adminTest.deleteCustomer();
                    System.out.println("\nTEST deleteCustomer: " + deleteCustomerResult);
                    break;

                case 9:
                    final boolean getCustomerResult = adminTest.getCustomer();
                    System.out.println("\nTEST getCustomer: " + getCustomerResult);
                    break;

                case 10:
                    final boolean getAllCustomersResult = adminTest.getAllCustomers();
                    System.out.println("\nTEST getAllCustomers: " + getAllCustomersResult);
                    break;

                case 0:
                    isRunning = false;
                    break;
            }
        }

    }


//        -----------------------------------CompanyTest-----------------------------------------

    //Implements the company test menu and returns according to the case if the test was successful.
    private static void companyTest() throws Throwable {
        final CompanyTests companyTests;
        try {
            companyTests = new CompanyTests();
        } catch (RunTestError e) {
            System.err.println("Failed to create a companyTest instance");
            throw new RunTestError();
        }
        boolean isRunning = true;
        while (isRunning) {
            printCompanyMenu();
            cmd = SCANNER.nextInt();
            switch (cmd) {
                case 1:
                    final boolean createCouponResult = companyTests.createAllCoupons();
                    System.out.println("\nTEST createCoupon: " + createCouponResult);
                    break;
                case 2:
                    final boolean updateCouponResult = companyTests.updateCoupon();
                    System.out.println("\nTEST updateCoupon: " + updateCouponResult);
                    break;
                case 3:
                    final boolean deleteCouponResult = companyTests.deleteCoupon();
                    System.out.println("\nTEST deleteCoupon: " + deleteCouponResult);
                    break;
                case 4:
                    final boolean getCouponResult = companyTests.getCoupon();
                    System.out.println("\nTEST getCoupon: " + getCouponResult);
                    break;
                case 5:
                    final boolean getCompanyCouponsByMaxPriceResult = companyTests.getCompanyCouponsByMaxPrice();
                    System.out.println("\nTEST getCompanyCouponsByMaxPrice: " + getCompanyCouponsByMaxPriceResult);
                    break;
                case 6:
                    final boolean getCompanyCouponByCategoryResult = companyTests.getCompanyCouponsByCategory();
                    System.out.println("\nTEST getCompanyCouponByCategory: " + getCompanyCouponByCategoryResult);
                    break;
                case 7:
                    final boolean getAllCouponsResult = companyTests.getAllCoupons();
                    System.out.println("\nTEST getAllCoupons: " + getAllCouponsResult);
                    break;
                case 8:
                    final boolean getAllCouponsByCompanyResult = companyTests.getAllCouponsByCompany();
                    System.out.println("\nTEST getAllCouponsByCompany: " + getAllCouponsByCompanyResult);
                    break;
                case 0:
                    isRunning = false;
                    break;
            }
        }
    }

//        -----------------------------------CustomerTest-----------------------------------------

    //Implements the customer test menu and returns according to the case if the test was successful.
    private static void customerTest() throws Throwable {
        final CustomerTests customerTests;
        try {
            customerTests = new CustomerTests();
        } catch (RunTestError e) {
            System.err.println("Failed to create a customerTest instance");
            throw new RunTestError();
        }
        boolean isRunning = true;
        while (isRunning) {
            printCustomerMenu();
            cmd = SCANNER.nextInt();
            switch (cmd) {
                case 1:
                    final boolean purchaseCouponResult = customerTests.purchaseCoupon();
                    System.out.println("\nTEST purchaseCoupon: " + purchaseCouponResult);
                    break;
                case 2:
                    final boolean getPurchasedCouponsByCategoryResult = customerTests.getPurchasedCouponsByCategory();
                    System.out.println("\nTEST getPurchasedCouponsByCategory: " + getPurchasedCouponsByCategoryResult);
                    break;
                case 3:
                    final boolean getPurchasedCouponsByMaxPriceResult = customerTests.getPurchasedCouponsByMaxPrice();
                    System.out.println("\nTEST getPurchasedCouponsByMaxPrice: " + getPurchasedCouponsByMaxPriceResult);
                    break;

                case 0:
                    isRunning = false;
                    break;

            }
        }
    }

    //Admin Test Menu - Displays the actions that an admin can perform on the database.
    private static void printAdminMenu() {
        System.out.println("\nPlease choose an action: " +
                "\nCompany actions: \n" +
                "1 - create companies\n" +
                "2 - update company\n" +
                "3 - delete company\n" +
                "4 - get a specific company\n" +
                "5 - get all companies\n" +
                "\nCustomer actions: \n" +
                "6 - create customers\n" +
                "7 - update customer\n" +
                "8 - delete customer\n" +
                "9 - get a specific customer\n" +
                "10 - get all customers\n" +
                "0 - exit");
    }

    //Company Test Menu - Displays the actions that a company can perform on the database.
    private static void printCompanyMenu() {
        System.out.println("\nPlease choose an action: \n" +
                "1 - create coupons\n" +
                "2 - update coupon\n" +
                "3 - delete coupon\n" +
                "4 - get a specific coupon\n" +
                "5 - get company coupons by max price\n" +
                "6 - get company coupon by category\n" +
                "7 - get all coupons\n" +
                "8 - get all coupons by company\n" +
                "0 - exit");
    }

    //Customer Test Menu - Displays the actions that a customer can perform on the database.
    private static void printCustomerMenu() {
        System.out.println("\nPlease choose an action: \n" +
                "1 - purchase coupon\n" +
                "2 - get purchased coupons by category\n" +
                "3 - get purchased coupons by max price\n" +
                "0 - exit");
    }

    //A greeting to a user who has logged in,it appears before printing the relevant menu.
    private static void greeting(String name) {
        System.out.println();
        System.out.println("Hello " + name.substring(0, 1).toUpperCase() + name.substring(1) + "!");
    }
}
