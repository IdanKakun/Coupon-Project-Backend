package com.idan.coupons.facade;

import com.idan.coupons.beans.Coupons;
import com.idan.coupons.dao.beansDao.CompanyDao;
import com.idan.coupons.dao.beansDao.CouponDao;
import com.idan.coupons.dao.beansDao.CrudDao;
import com.idan.coupons.enums.CategoryType;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.enums.InputType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.InputValidationUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

import static com.idan.coupons.constants.testsConstants.CouponConstants.couponForUpdate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

//A class that expresses the business logic of a company-type user
public class CompanyFacade implements CrudDao<Long, Coupons>, ClientFacade {
    public static final CompanyFacade instance = new CompanyFacade();

    private final CompanyDao companyDao = CompanyDao.instance;
    private final CouponDao couponDao = CouponDao.instance;

    // Login method that checks if the inputs email and password are valid.
    @Override
    public boolean login(final String email, final String password) throws Exception {

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

    // -----------------------------------Coupons-----------------------------------------

    //                                      --Create--

    //Creates a new coupon by checking in the database if it already exists.
    @Override
    public Long create(final Coupons coupon) throws Exception {
        if (companyDao.isExistsById(coupon.getCompanyId())) {
            if (!couponDao.getAllCouponsByCompany(coupon.getCompanyId()).contains(coupon)) {
                Long couponId = couponDao.create(coupon);
                System.out.println("A new coupon by the name " + coupon.getTitle() + " was created successfully!");
                return couponId;
            } else {
                throw new ApplicationException(ErrorType.ILLEGAL_USER_INPUT, "This coupon already exists in the company");
            }
        } else {
            throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "company number " + coupon.getCompanyId() + " is not found.");
        }
    }

    //                                      --Update--

    //Updates a coupon's details.
    @Override
    public void update(final Coupons coupon) throws Exception {
        if (couponDao.isExistsById(coupon.getId())) {
            coupon.setEndDate(couponForUpdate.getEndDate());
            couponDao.update(coupon);
            System.out.println("Coupon number " + coupon.getId() + " was updated successfully!");
        } else {
            throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "coupon " + coupon.getId() + " is not found.");
        }
    }

    //                                      --Delete--

    //Removes coupon by coupon ID,and also removes all purchased coupons.
    @Override
    public void delete(final Long couponId) throws Exception {
        if (couponDao.isExistsById(couponId)) {
            couponDao.deletePurchasedCouponByCouponId(couponId);
            couponDao.delete(couponId);
            System.out.println("Coupon number " + couponId + " was removed.");
        } else {
            throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "coupon number " + couponId + " is not found.");
        }
    }


    //                                      --Getters--


    // Gets a specific coupon by coupon ID.
    @Override
    public Coupons read(final Long couponId) throws Exception {
        if (couponDao.isExistsById(couponId)) {
            return couponDao.read(couponId);
        } else {
            throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "coupon number " + couponId + " is not found.");
        }
    }

    // Gets a specific coupon by coupon category type.
    public ArrayList<Coupons> getCompanyCouponsByCategory(final Long companyId, final CategoryType category) throws Exception {
        if (companyDao.isExistsById(companyId)) {
            final ArrayList<Coupons> coupons = couponDao.getCouponsOfCompanyByCategory(companyId, category);

            if (coupons == null) {
                throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "No coupons could be found.");
            } else {
                return coupons;
            }
        } else {
            throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "company number " + companyId + " is not found.");
        }
    }

    // Gets all company coupons by max price of the company and company id.
    public ArrayList<Coupons> getCompanyCouponsByMaxPrice(final Long companyId, final double maxPrice) throws Exception {
        final ArrayList<Coupons> coupons = couponDao.getAllCouponsByCompany(companyId);
        final ArrayList<Coupons> maxPriceCoupons = new ArrayList<>();
        for (Coupons coupon : coupons) {
            if (coupon.getPrice() <= maxPrice) {
                maxPriceCoupons.add(coupon);
            }
        }
        return maxPriceCoupons;
    }

    // Gets all the coupons that exist in the database.
    @Override
    public ArrayList<Coupons> readAll() throws Exception {
        final ArrayList<Coupons> coupons = couponDao.readAll();
        if (coupons == null) {
            throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "No coupons could be found.");
        }
        return coupons;
    }

    // Gets all the coupons of specific company.
    public ArrayList<Coupons> readAllByCompany(final Long companyId) throws Exception {
        final ArrayList<Coupons> coupons = couponDao.getAllCouponsByCompany(companyId);

        if (coupons == null) {
            throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "No coupons could be found.");
        }
        return coupons;
    }

}