package com.idan.coupons.utils;

import com.idan.coupons.beans.Company;
import com.idan.coupons.beans.Coupons;
import com.idan.coupons.beans.Customer;
import com.idan.coupons.enums.CategoryType;

import java.sql.ResultSet;
import java.sql.SQLException;

//A class that mapping the objects to information in the database.
public class ObjectExtractionUtils {

    //Makes a company out of database info.
    public static Company extractCompanyFromResultSet(final ResultSet result) throws SQLException {

        final Company company = new Company();

        company.setId(result.getLong("id"));
        company.setName(result.getString("name"));
        company.setEmail(result.getString("email"));
        company.setPassword(result.getString("password"));

        return company;
    }

    //Makes a coupon out of database info.
    public static Coupons extractCouponFromResultSet(final ResultSet result) throws SQLException {

        final Coupons coupon = new Coupons();

        coupon.setId(result.getLong("id"));
        coupon.setCompanyId(result.getLong("company_id"));
        CategoryType category = CategoryType.valueOf(result.getString("category"));
        coupon.setCategory(category);
        coupon.setTitle(result.getString("title"));
        coupon.setDescription(result.getString("description"));
        coupon.setStartDate(result.getDate("start_date"));
        coupon.setEndDate(result.getDate("end_date"));
        coupon.setAmount(result.getInt("amount"));
        coupon.setPrice(result.getDouble("price"));
        coupon.setImage(result.getString("image"));
        return coupon;
    }

    //Makes a customer out of database info.
    public static Customer extractCustomerFromResultSet(final ResultSet result) throws SQLException {

        final Customer customer = new Customer();

        customer.setId(result.getLong("id"));
        customer.setFirstName(result.getString("first_name"));
        customer.setLastName(result.getString("last_name"));
        customer.setEmail(result.getString("email"));
        customer.setPassword(result.getString("password"));
        return customer;
    }
}
