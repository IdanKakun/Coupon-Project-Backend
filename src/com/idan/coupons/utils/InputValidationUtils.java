package com.idan.coupons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InputValidationUtils {


    private static final String PASSWORD_REGEX = "[a-zA-Z0-9]{4,12}";
    private static final String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    //Password validation.
    public static boolean isPasswordValid(String password) {
        return !password.matches(PASSWORD_REGEX);
    }

    //Email validation.
    public static boolean isEmailValid(String email) {


        final Pattern emailPattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = emailPattern.matcher(email);

        return !matcher.find();
    }

}
