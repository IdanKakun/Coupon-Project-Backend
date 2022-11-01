package com.idan.coupons.dao.beansDao;

import com.idan.coupons.beans.Coupons;
import com.idan.coupons.dao.connectionPool.ConnectionPool;
import com.idan.coupons.enums.CategoryType;
import com.idan.coupons.enums.CrudType;
import com.idan.coupons.enums.EntityType;
import com.idan.coupons.exceptions.ConnectionToDBError;
import com.idan.coupons.exceptions.EntityCrudException;
import com.idan.coupons.utils.ObjectExtractionUtils;

import java.sql.*;
import java.util.ArrayList;

public class CouponDao implements CrudDao<Long, Coupons> {

    public static final CouponDao instance = new CouponDao();
    private final ConnectionPool connectionPool;

    // Constructor- creates a connection to the database when the instance created.
    public CouponDao() {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (final Exception e) {
            throw new ConnectionToDBError();
        }
    }

    //                                      --IsExisting methods--


    //A method that checks if a coupon is exists in the database by coupon ID.
    public boolean isExistsById(final Long couponId) throws Exception {
        Connection connection = null;
        try {
            final String sqlStatement = "SELECT * FROM coupons WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, couponId);

            final ResultSet result = preparedStatement.executeQuery();
            return result.next();
        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.READ, EntityType.COUPONS);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //                                      --Create--

    //Insert a new coupon to the database.
    @Override
    public Long create(final Coupons coupons) throws Exception {
        Date startDate = new Date(coupons.getStartDate().getTime());
        Date endDate = new Date(coupons.getEndDate().getTime());
        Connection connection = null;
        try {
            final String sqlStatement = "INSERT INTO coupons (company_id, category" +
                    ", title, description, start_date, end_date, amount, price, image) VALUES(?,?,?,?,?,?,?,?,?)";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, coupons.getCompanyId());
            preparedStatement.setString(2, coupons.getCategory().toString());
            preparedStatement.setString(3, coupons.getTitle());
            preparedStatement.setString(4, coupons.getDescription());
            preparedStatement.setDate(5, startDate);
            preparedStatement.setDate(6, endDate);
            preparedStatement.setInt(7, coupons.getAmount());
            preparedStatement.setDouble(8, coupons.getPrice());
            preparedStatement.setString(9, coupons.getImage());

            preparedStatement.executeUpdate();

            final ResultSet generatedKeysResult = preparedStatement.getGeneratedKeys();

            if (!generatedKeysResult.next()) {
                System.err.println("Failed to retrieve an auto-generated id from the database," +
                        " please check if the id is set on Auto-increment");
            }
            return generatedKeysResult.getLong(1);

        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.CREATE, EntityType.COUPONS);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }


    //Insert a category to the database.
    public void insertCategory(final CategoryType category) throws Exception {
        Connection connection = null;
        try {
            final String sqlStatement = "INSERT INTO categories (name) VALUES(?)";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, category.name());

            preparedStatement.executeUpdate();

            final ResultSet generatedKeysResult = preparedStatement.getGeneratedKeys();

            if (!generatedKeysResult.next()) {
                System.err.println("Failed to retrieve an auto-generated id from the database," +
                        " please check if the id is set on Auto-increment");
            }
            generatedKeysResult.getLong(1);

        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.CREATE, EntityType.CATEGORY);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //A method that adds data to the table that defines the relationship between a customer and the coupons he has purchased.
    public void addCouponPurchase(final Long customerId, final Long couponId) throws Exception {
        Connection connection = null;
        try {
            final String sqlStatement = "INSERT INTO customers_vs_coupons (customer_id, coupon_id) VALUES(?,?)";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, couponId);

            preparedStatement.executeUpdate();

        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.CREATE, EntityType.COUPONS);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //                                      --Update--

    //Updates a coupon details.
    @Override
    public void update(final Coupons coupons) throws Exception {
        Date startDate = new Date(coupons.getStartDate().getTime());
        Date endDate = new Date(coupons.getEndDate().getTime());
        Connection connection = null;
        try {

            final String sqlStatement = "UPDATE coupons SET category = ?, title = ?, description = ?," +
                    " start_date = ?, end_date = ?, amount = ?, price = ?, image = ? WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, coupons.getCategory().toString());
            preparedStatement.setString(2, coupons.getTitle());
            preparedStatement.setString(3, coupons.getDescription());
            preparedStatement.setDate(4, startDate);
            preparedStatement.setDate(5, endDate);
            preparedStatement.setInt(6, coupons.getAmount());
            preparedStatement.setDouble(7, coupons.getPrice());
            preparedStatement.setString(8, coupons.getImage());
            preparedStatement.setLong(9, coupons.getId());

            preparedStatement.executeUpdate();

        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.UPDATE, EntityType.COUPONS);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //                                      --Delete--


    //Removes a coupon by coupon ID.
    @Override
    public void delete(final Long couponID) throws Exception {
        Connection connection = null;
        try {
            final String sqlStatement = "DELETE FROM coupons WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, couponID);

            preparedStatement.executeUpdate();

        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.DELETE, EntityType.COUPONS);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //Removes a purchase coupon by coupon ID.
    public void deletePurchasedCouponByCouponId(final Long couponId) throws Exception {
        Connection connection = null;
        try {
            final String sqlStatement = "DELETE FROM customers_vs_coupons WHERE coupon_id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, couponId);

            preparedStatement.executeUpdate();
        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.DELETE, EntityType.COUPONS);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //Removes a purchase coupon by customer ID.
    public void deletePurchaseCouponByCustomerId(final Long customerId) throws Exception {
        Connection connection = null;
        try {
            final String sqlStatement = "DELETE FROM customers_vs_coupons WHERE customer_id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, customerId);

            preparedStatement.executeUpdate();

        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.DELETE, EntityType.COUPONS);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //                                      --Getters--

    // Gets a specific coupon by coupon ID.
    @Override
    public Coupons read(final Long couponID) throws Exception {
        Connection connection = null;
        try {
            final String sqlStatement = "SELECT * FROM coupons WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, couponID);

            final ResultSet result = preparedStatement.executeQuery();
            if (!result.next()) {
                System.err.println(couponID + " is not found");
            }
            return ObjectExtractionUtils.extractCouponFromResultSet(result);

        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.READ, EntityType.COUPONS);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }


    // Gets all coupons of specific company by category.
    public ArrayList<Coupons> getCouponsOfCompanyByCategory(final Long companyId, final CategoryType category) throws Exception {
        Connection connection = null;
        final ArrayList<Coupons> couponsOfCompanyByCategory = new ArrayList<>();
        try {
            final String sqlStatement = "SELECT * FROM coupons WHERE company_id = ? AND category = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, companyId);
            preparedStatement.setString(2, category.toString());

            final ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                couponsOfCompanyByCategory.add(ObjectExtractionUtils.extractCouponFromResultSet(result));
            }

            return couponsOfCompanyByCategory;

        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.READ_ALL, EntityType.COUPONS);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    // Gets all coupons of specific company by company ID.
    public ArrayList<Coupons> getAllCouponsByCompany(final Long companyId) throws Exception {
        Connection connection = null;
        final ArrayList<Coupons> allCoupons = new ArrayList<>();

        try {
            final String sqlStatement = "SELECT * FROM coupons WHERE company_id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, companyId);

            final ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                allCoupons.add(ObjectExtractionUtils.extractCouponFromResultSet(result));
            }

            return allCoupons;

        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.READ_ALL, EntityType.COUPONS);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    // Gets all purchase coupons of specific customer by customer ID.
    public ArrayList<Coupons> getPurchasedCoupons(final Long customerId) throws Exception {
        Connection connection = null;
        final ArrayList<Coupons> allCoupons = new ArrayList<>();

        try {

            final String sqlStatement = "SELECT coupons.* FROM customers_vs_coupons JOIN coupons ON" +
                    " customers_vs_coupons.customer_id = ? AND customers_vs_coupons.coupon_id = coupons.id";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, customerId);


            final ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                allCoupons.add(ObjectExtractionUtils.extractCouponFromResultSet(result));
            }

            return allCoupons;

        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.READ_ALL, EntityType.COUPONS);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    // Gets all coupons.
    @Override
    public ArrayList<Coupons> readAll() throws Exception {
        Connection connection = null;
        ArrayList<Coupons> coupons = new ArrayList<>();
        try {
            final String sqlStatement = "SELECT * FROM coupons";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            final ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                coupons.add(ObjectExtractionUtils.extractCouponFromResultSet(result));
            }
            return coupons;
        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.READ_ALL, EntityType.COUPONS);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
