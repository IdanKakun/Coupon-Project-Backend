package com.idan.test;


import com.idan.coupons.beans.Coupons;
import com.idan.coupons.enums.CategoryType;
import com.idan.coupons.facade.CompanyFacade;
import com.idan.coupons.facade.CustomerFacade;

import java.util.ArrayList;

import static com.idan.coupons.constants.Constants.MAX_PRICE;
import static com.idan.test.CouponSystem.SCANNER;

//A class that implements the business logic operations of customer in the test.
public class CustomerTests {
    private final CustomerFacade customerFacade;

    //Constructor- creates an instance of customer facade.
    public CustomerTests() {
        this.customerFacade = CustomerFacade.instance;
    }

    //                                      --Purchase--

    // Purchase a new coupon for customer.
    public boolean purchaseCoupon() throws Exception {
        System.out.print("Please enter a customer id: ");
        Long customerId = SCANNER.nextLong();
        System.out.print("Please enter a coupon id: ");
        Long couponId = SCANNER.nextLong();
        Coupons coupon = CompanyFacade.instance.read(couponId);
        if (coupon == null) {
            System.err.println("Test failed | failed to purchase coupon.");
            return false;
        }
        customerFacade.purchaseCoupon(customerId, coupon);
        return true;
    }

    //                                      --Getters--


    // Gets all purchased coupons of specific customer by category type.
    public boolean getPurchasedCouponsByCategory() throws Exception {
        System.out.print("Please enter a customer id: ");
        Long customerId = SCANNER.nextLong();
        System.out.print("Please enter a category: ");
        CategoryType category = CategoryType.valueOf(SCANNER.next().toUpperCase());
        ArrayList<Coupons> couponsByCategory = customerFacade.getPurchaseCouponsByCategory(customerId, category);
        if (couponsByCategory.isEmpty()) {
            System.err.println("Test failed | No coupons were found in " + category + " category");
            return false;
        }
        System.out.println(couponsByCategory);
        return true;
    }

    // Gets all purchased coupons by max price.
    public boolean getPurchasedCouponsByMaxPrice() throws Exception {
        Long customerId = SCANNER.nextLong();
        ArrayList<Coupons> coupons = customerFacade.getPurchasedCouponsByMaxPrice(customerId, MAX_PRICE);
        if (coupons.isEmpty()) {
            System.err.println("Test failed | No coupons were found by max price " + MAX_PRICE);
            return false;
        }
        System.out.println(coupons);
        return true;
    }
}
