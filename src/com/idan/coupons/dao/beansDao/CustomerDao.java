package com.idan.coupons.dao.beansDao;

import com.idan.coupons.beans.Customer;
import com.idan.coupons.dao.connectionPool.ConnectionPool;
import com.idan.coupons.enums.CrudType;
import com.idan.coupons.enums.EntityType;
import com.idan.coupons.exceptions.ConnectionToDBError;
import com.idan.coupons.exceptions.EntityCrudException;
import com.idan.coupons.utils.ObjectExtractionUtils;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDao extends UserDao<Long, Customer> {

    public static final CustomerDao instance = new CustomerDao();
    private final ConnectionPool connectionPool;

    //Constructor- creates a connection to the database when the instance created.
    public CustomerDao() {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (final Exception e) {
            throw new ConnectionToDBError();
        }
    }

    //                                      --IsExisting methods--

    //A method that checks if a customer is exists in the database by customer ID.
    public boolean isExistsById(final Long customerId) throws Exception {
        Connection connection = null;
        try {

            final String sqlStatement = "SELECT * FROM customer WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, customerId);

            final ResultSet result = preparedStatement.executeQuery();
            return result.next();
        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.READ, EntityType.CUSTOMER);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //A method that checks if a customer is exists in the database by customer email.
    @Override
    public boolean isExistsByEmail(final String email) throws Exception {
        Connection connection = null;
        try {

            final String sqlStatement = "SELECT * FROM customer WHERE email = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, email);

            final ResultSet result = preparedStatement.executeQuery();
            return result.next();
        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.READ, EntityType.CUSTOMER);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //                                      --Create--

    //Insert a new customer to the database.
    @Override
    public Long create(final Customer customer) throws Exception {
        Connection connection = null;
        try {

            final String sqlStatement = "INSERT INTO customer (first_name,last_name, email, password) VALUES(?,?,?,?)";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setLong(4, customer.getPassword().hashCode());


            preparedStatement.executeUpdate();

            final ResultSet generatedKeysResult = preparedStatement.getGeneratedKeys();

            if (!generatedKeysResult.next()) {
                System.err.println("Failed to retrieve an auto-generated id from the database," +
                        " please check if the id is set on Auto-increment");
            }
            return generatedKeysResult.getLong(1);

        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.CREATE, EntityType.CUSTOMER);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //                                      --Update--

    //Updates a customer details.
    @Override
    public void update(final Customer customer) throws Exception {
        Connection connection = null;
        try {
            final String sqlStatement = "UPDATE customer SET first_name = ?,last_name = ?, email = ?, password = ? WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setLong(4, customer.getPassword().hashCode());
            preparedStatement.setLong(5, customer.getId());

            preparedStatement.executeUpdate();

        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.UPDATE, EntityType.CUSTOMER);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //                                      --Delete--

    //Removes a company by customer ID.
    @Override
    public void delete(final Long customerID) throws Exception {
        Connection connection = null;
        try {

            final String sqlStatement = "DELETE FROM customer WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, customerID);

            preparedStatement.executeUpdate();

        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.DELETE, EntityType.CUSTOMER);

        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //                                      --Getters--

    // Gets a specific customer by customer ID.
    @Override
    public Customer read(final Long customerID) throws Exception {
        Connection connection = null;
        try {
            final String sqlStatement = "SELECT * FROM customer WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, customerID);

            final ResultSet result = preparedStatement.executeQuery();
            if (!result.next()) {
                System.err.println("Customer number " + customerID + " is not found");
            }
            return ObjectExtractionUtils.extractCustomerFromResultSet(result);
        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.READ, EntityType.CUSTOMER);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    // Gets a specific customer by customer email.
    @Override
    public Customer readByEmail(final String email) throws Exception {
        Connection connection = null;
        try {

            final String sqlStatement = "SELECT * FROM customer WHERE email = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, email);

            final ResultSet result = preparedStatement.executeQuery();
            if (!result.next()) {
                System.err.println(email + " is not found");
            }
            return ObjectExtractionUtils.extractCustomerFromResultSet(result);
        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.READ, EntityType.CUSTOMER);

        } finally {
            connectionPool.returnConnection(connection);
        }
    }


    // Gets all the customers that exist in the database.
    @Override
    public ArrayList<Customer> readAll() throws Exception {
        Connection connection = null;
        ArrayList<Customer> customers = new ArrayList<>();
        try {

            final String sqlStatement = "SELECT * FROM customer";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            final ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                customers.add(ObjectExtractionUtils.extractCustomerFromResultSet(result));
            }
        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.READ_ALL, EntityType.CUSTOMER);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return customers;
    }
}
