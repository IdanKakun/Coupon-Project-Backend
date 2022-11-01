package com.idan.coupons.exceptions;

import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.enums.InputType;


//A class that defines the application exceptions.
public class ApplicationException extends Exception {


    //A constructor that defines application exceptions that are thrown by input.
    public ApplicationException(final ErrorType errorType, final InputType inputType, String message) {
        super(errorType + " | " + inputType + " | " + message);
    }

    //General constructor of Application Exceptions.
    public ApplicationException(final ErrorType errorType, String message) {
        super(errorType + " | " + message);
    }

}
