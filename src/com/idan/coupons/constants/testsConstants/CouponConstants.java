package com.idan.coupons.constants.testsConstants;

import com.idan.coupons.beans.Coupons;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.exceptions.ApplicationException;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.idan.coupons.enums.CategoryType.*;


public class CouponConstants {

    //A coupon created to run the update coupon function.
    public static final Coupons couponForUpdate = new Coupons(2L, RESTAURANT, "Vivino",
            "30% off for dinner"
            , getStartDate("2019-05-02"), getEndDate("2022-01-12"), 1, 30, "image");

    //List of coupons that created for system testing.
    public static ArrayList<Coupons> couponsForTest() {
        final ArrayList<Coupons> coupons = new ArrayList<>();

        final Coupons coupon1 = new Coupons(1L, FOOD, "Pizza",
                "Buy a pizza tray and get one free pizza tray!"
                , getStartDate("2021-09-09"), getEndDate("2022-12-12"), 2, 12.2, "image");


        final Coupons coupon2 = new Coupons(2L, ELECTRICITY, "Fridge",
                "Buy a fridge at 50 percent off!"
                , getStartDate("2021-03-09"), getEndDate("2022-09-14"), 4, 50.5, "image");

        final Coupons coupon3 = new Coupons(1L, GIFT, "Dubi",
                "Sale of dubi at 70 percent off!"
                , getStartDate("2021-06-09"), getEndDate("2022-11-16"), 3, 45, "image");


        final Coupons coupon4 = new Coupons(1L, FOOD, "IceCream",
                "Buy Golda, get a salty pretzel sauce free!"
                , getStartDate("2021-08-09"), getEndDate("2022-09-12"), 1, 20, "image");

        final Coupons coupon5 = new Coupons(2L, INTERNET, "Internet package",
                "Get 100GB for 3 month free!"
                , getStartDate("2020-04-05"), getEndDate("2027-03-10"), 7, 79, "image");

        final Coupons coupon6 = new Coupons(1L, RESTAURANT, "Burgerim",
                "Free meal!"
                , getStartDate("2018-03-08"), getEndDate("2022-06-10"), 8, 10.5, "image");

        final Coupons coupon7 = new Coupons(2L, VACATION, "Vacation to Maldives",
                "Vacation to the Maldives at 400 NIS discount."
                , getStartDate("2022-03-05"), getEndDate("2023-04-10"), 20, 450, "image");

        coupons.add(coupon1);
        coupons.add(coupon2);
        coupons.add(coupon3);
        coupons.add(coupon4);
        coupons.add(coupon5);
        coupons.add(coupon6);
        coupons.add(coupon7);

        return coupons;
    }

    //Convert start date.
    private static Date getStartDate(String stDate) {
        return parseDateFormat(stDate);
    }

    //Convert end date.
    private static Date getEndDate(String stDate) {
        return parseDateFormat(stDate);
    }

    //A function that converts the date format to a simple date format.
    // Using sneaky throws in order to throw an application exception into the function.
    @SneakyThrows
    private static Date parseDateFormat(String stDate) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = simpleDateFormat.parse(stDate);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.CANNOT_PARSE_DATE, "Failed to parse date format");
        }
        return date;
    }

}

