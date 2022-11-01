package com.idan.coupons.Threads;


import com.idan.coupons.beans.Coupons;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.facade.CompanyFacade;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Date;

import static com.idan.coupons.constants.Constants.SINGLE_DAY_IN_MILLI_SECONDS;

//A class that implements a thread that runs in parallel with the program.
public class CouponExpirationDailyJob implements Runnable {

    private final CompanyFacade companyFacade = CompanyFacade.instance;

    // 86,400 is the amount of seconds in a single day, multiplied by 1000 because the system works in milliseconds
    long day = SINGLE_DAY_IN_MILLI_SECONDS;

    //The task is daily job that removes expired coupons from the database.
    @SneakyThrows
    @Override
    public void run() {
        try {
            while (true) {
                Date today = new Date();
                ArrayList<Coupons> coupons = companyFacade.readAll();
                for (Coupons coupon : coupons) {
                    if (today.getTime() >= coupon.getEndDate().getTime()) {
                        companyFacade.delete(coupon.getId());
                        System.out.println("Coupon number " + coupon.getId() + " has expired.");
                    }
                }
                Thread.sleep(day);
            }
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.CANNOT_DELETE_EXPIRED_COUPONS, "Failed to remove expired coupons");
        }
    }
}