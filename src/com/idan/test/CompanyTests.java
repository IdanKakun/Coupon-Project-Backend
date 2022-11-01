package com.idan.test;

import com.idan.coupons.beans.Coupons;
import com.idan.coupons.enums.CategoryType;
import com.idan.coupons.facade.CompanyFacade;

import java.util.ArrayList;

import static com.idan.coupons.constants.Constants.MAX_PRICE;
import static com.idan.coupons.constants.testsConstants.CouponConstants.*;
import static com.idan.test.CouponSystem.SCANNER;

//A class that implements the business logic operations of company in the test.
public class CompanyTests {

    private final CompanyFacade companyFacade;


    //Constructor- creates an instance of company facade.
    public CompanyTests() {
        this.companyFacade = CompanyFacade.instance;
    }

    //                                      --Create--

    //Creates all coupons for the test.
    public boolean createAllCoupons() throws Exception {
        for (Coupons coupon : couponsForTest()) {
            companyFacade.create(coupon);
        }
        ArrayList<Coupons> coupons = companyFacade.readAll();
        for (Coupons coupon : coupons) {
            if (coupon == null) {
                System.err.println("Test failed | failed to create customers ");
                return false;
            }
        }
        return true;
    }

    //                                      --Update--

    //Updates a coupon details by coupon ID.
    public boolean updateCoupon() throws Exception {
        System.out.print("Please enter a coupon id: ");
        Long couponId = SCANNER.nextLong();
        Coupons coupon = companyFacade.read(couponId);
        if (coupon == null) {
            System.err.println("Test failed | failed to update coupon number " + couponId);
            return false;
        }
        coupon.setTitle(couponForUpdate.getTitle());
        coupon.setPrice(couponForUpdate.getPrice());
        coupon.setStartDate(couponForUpdate.getStartDate());
        companyFacade.update(coupon);
        return true;
    }

    //                                      --Delete--

    //Removes a coupon by coupon ID.
    public boolean deleteCoupon() throws Exception {
        System.out.print("Please enter a coupon id: ");
        Long couponId = SCANNER.nextLong();
        Coupons coupon = companyFacade.read(couponId);
        if (coupon == null) {
            System.err.println("Test failed | failed to delete coupon number " + couponId);
            return false;
        }
        companyFacade.delete(couponId);
        return true;
    }

    //                                      --Getters--

    // Gets a specific coupon by coupon ID.
    public boolean getCoupon() throws Exception {
        System.out.print("Please enter a coupon id: ");
        Long couponId = SCANNER.nextLong();
        Coupons coupon = companyFacade.read(couponId);
        if (coupon == null) {
            System.err.println("Test failed | failed to read coupon number " + couponId);
            return false;
        }
        System.out.println(coupon);
        return true;
    }

    //Gets all purchased coupons by category.
    public boolean getCompanyCouponsByCategory() throws Exception {
        System.out.print("Please enter a company id: ");
        Long companyId = SCANNER.nextLong();
        System.out.print("Please enter a category: ");
        CategoryType category = CategoryType.valueOf(SCANNER.next().toUpperCase());
        ArrayList<Coupons> coupons = companyFacade.getCompanyCouponsByCategory(companyId, category);
        if (coupons.isEmpty()) {
            System.err.println("Test failed | failed to read coupons of company number " + companyId + " by category " + category);
            return false;
        }
        System.out.println("These are all the coupons of company number " + companyId + ":\n" + coupons);
        return true;
    }

    // Gets all company coupons by max price.
    public boolean getCompanyCouponsByMaxPrice() throws Exception {
        System.out.print("Please enter a company id: ");
        Long companyId = SCANNER.nextLong();
        ArrayList<Coupons> maxPriceCoupons = companyFacade.getCompanyCouponsByMaxPrice(companyId, MAX_PRICE);
        if (maxPriceCoupons.isEmpty()) {
            System.err.println("Test failed | failed to read coupons of company number " + companyId + " by max price " + MAX_PRICE);
            return false;
        }
        System.out.println(maxPriceCoupons);
        return true;
    }

    // Gets all coupons that exist in the database.
    public boolean getAllCoupons() throws Exception {
        ArrayList<Coupons> coupons = companyFacade.readAll();
        if (coupons == null) {
            System.err.println("Test failed | failed to read coupons.");
            return false;
        }
        System.out.println("These are all the coupons we could find : \n" + coupons);
        return true;
    }

    // Gets all company coupons that exist in the database.
    public boolean getAllCouponsByCompany() throws Exception {
        System.out.print("Please enter a company id: ");
        Long companyId = SCANNER.nextLong();
        ArrayList<Coupons> coupons = companyFacade.readAllByCompany(companyId);
        if (coupons == null) {
            System.err.println("Test failed | failed to read coupons of company number " + companyId);
            return false;
        }
        System.out.println("These are all the coupons of company number " + companyId + ":\n" + coupons);
        return true;
    }

}
