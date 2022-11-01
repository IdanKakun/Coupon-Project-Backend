package com.idan;

import com.idan.coupons.exceptions.RunTestError;
import com.idan.test.CouponSystem;

public class Main {


    public static void main(String[] args) {
        try {
            CouponSystem.runTest();
        } catch (Throwable e) {
            throw new RunTestError();
        }
    }
}


