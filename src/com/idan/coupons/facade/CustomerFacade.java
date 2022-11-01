package com.idan.coupons.facade;


import com.idan.coupons.beans.Coupons;
import com.idan.coupons.dao.beansDao.CouponDao;
import com.idan.coupons.enums.CategoryType;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.enums.InputType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.InputValidationUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

//A class that expresses the business logic of a customer-type user
public class CustomerFacade implements ClientFacade {
    public static final CustomerFacade instance = new CustomerFacade();

    private final CouponDao couponDao = CouponDao.instance;

    // Login method that checks if the inputs email and password are valid.
    @Override
    public boolean login(final String email, final String password) throws ApplicationException {

        if (InputValidationUtils.isEmailValid(email)) {
            throw new ApplicationException(ErrorType.INVALID_USER, InputType.EMAIL,
                    "The email you're trying to use doesn't fit the required format.");
        }

        if (InputValidationUtils.isPasswordValid(password)) {
            throw new ApplicationException(ErrorType.INVALID_USER, InputType.PASSWORD,
                    "The password you're trying to use doesn't fit the required format.");
        }
        return true;
    }

    //                                      --Purchase--

    // Purchase a new coupon for customer,update the coupon in the DB.
    public void purchaseCoupon(final Long customerId, final Coupons coupon) throws Exception {
        if (couponDao.isExistsById(coupon.getId())) {
            ArrayList<Coupons> purchaseCoupon = getAllPurchaseCoupons(customerId);
            if (!purchaseCoupon.contains(coupon)) {
                if (coupon.getAmount() > 0 && coupon.getEndDate().after(new Date())) {
                    couponDao.addCouponPurchase(customerId, coupon.getId());
                    coupon.setAmount(coupon.getAmount() - 1);
                    couponDao.update(coupon);
                    System.out.println("A new coupon was purchased successfully");
                } else {
                    throw new ApplicationException(ErrorType.OUT_OF_STOCK_OR_EXPIRED, "Failed to purchase a new coupon");
                }
            } else {
                throw new ApplicationException(ErrorType.CANNOT_PURCHASE_COUPON, "You are already purchased this coupon!");
            }
        } else {
            System.err.println("Coupon number " + coupon.getId() + " is not found.");
        }
    }

    //                                      --Getters--

    // Gets all purchased coupons by customer id.
    public ArrayList<Coupons> getAllPurchaseCoupons(final Long customerId) throws Exception {
        return couponDao.getPurchasedCoupons(customerId);
    }

    // Gets all purchased coupons by category type.
    public ArrayList<Coupons> getPurchaseCouponsByCategory(final Long customerId, final CategoryType category) throws Exception {
        ArrayList<Coupons> purchaseCoupons = getAllPurchaseCoupons(customerId);
        purchaseCoupons.removeIf(coupon -> coupon.getCategory() != category);
        return purchaseCoupons;
    }

    // Gets all purchased coupons by max price of the customer and customer id.
    public ArrayList<Coupons> getPurchasedCouponsByMaxPrice(final Long customerId, final double maxPrice) throws Exception {
        ArrayList<Coupons> maxPriceCoupons = getAllPurchaseCoupons(customerId);
        maxPriceCoupons.removeIf(coupon -> coupon.getPrice() <= maxPrice);
        return maxPriceCoupons;
    }
}
