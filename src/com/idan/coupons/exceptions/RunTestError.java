package com.idan.coupons.exceptions;

//A class that defines the run tests errors.
public class RunTestError extends Error {

    //A constructor that defines run tests errors.
    public RunTestError() {
        super("Failed to run test");
    }
}
