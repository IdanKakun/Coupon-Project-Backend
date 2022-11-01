package com.idan.coupons.constants.testsConstants;

import com.idan.coupons.beans.Company;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

import static com.idan.coupons.constants.testsConstants.UserConstants.USER_PASSWORD;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
//Constants of login to the system using a company-type user.
public class CompanyConstants {

    //A company created to run the update company function.
    public static final Company companyForUpdate = new Company("Ori", "ori@gmail.com", USER_PASSWORD, new ArrayList<>());

    //List of companies that created for system testing.
    public static ArrayList<Company> companiesForTest() {
        final ArrayList<Company> companies = new ArrayList<>();
        final Company company1 = new Company("Google", "google@gmail.com", USER_PASSWORD, new ArrayList<>());
        final Company company2 = new Company("Elbit", "elbit@gmail.com", USER_PASSWORD, new ArrayList<>());
        companies.add(company1);
        companies.add(company2);

        return companies;
    }

}
