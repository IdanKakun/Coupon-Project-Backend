package com.idan.coupons.dao.beansDao;

import com.idan.coupons.beans.Company;
import com.idan.coupons.dao.connectionPool.ConnectionPool;
import com.idan.coupons.enums.CrudType;
import com.idan.coupons.enums.EntityType;
import com.idan.coupons.exceptions.ConnectionToDBError;
import com.idan.coupons.exceptions.EntityCrudException;
import com.idan.coupons.utils.ObjectExtractionUtils;

import java.sql.*;
import java.util.ArrayList;

public class CompanyDao extends UserDao<Long, Company> {

    public static final CompanyDao instance = new CompanyDao();
    private final ConnectionPool connectionPool;

    //Constructor- creates a connection to the database when the instance created.
    public CompanyDao() {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (final Exception e) {
            throw new ConnectionToDBError();
        }
    }

    //                                      --IsExisting methods--


    //A method that checks if a company is exists in the database by company ID.
    public boolean isExistsById(final Long companyID) throws Exception {
        Connection connection = null;
        try {

            final String sqlStatement = "SELECT * FROM companies WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, companyID);

            final ResultSet result = preparedStatement.executeQuery();
            return result.next();
        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.READ, EntityType.COMPANY);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //A method that checks if a company is exists in the database by company email.
    @Override
    public boolean isExistsByEmail(final String email) throws Exception {
        Connection connection = null;
        try {
            final String sqlStatement = "SELECT * FROM companies WHERE email = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, email);

            final ResultSet result = preparedStatement.executeQuery();
            return result.next();
        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.READ, EntityType.COMPANY);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //A method that checks if a company is exists in the database by company name.
    public boolean isExistsByName(final String name) throws Exception {
        Connection connection = null;
        try {
            final String sqlStatement = "SELECT * FROM companies WHERE name = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);

            final ResultSet result = preparedStatement.executeQuery();
            return result.next();
        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.READ, EntityType.COMPANY);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //                                      --Create--

    //Insert a new company to the database.
    @Override
    public Long create(final Company company) throws Exception {
        Connection connection = null;
        try {

            final String sqlStatement = "INSERT INTO companies (name, email, password) VALUES(?,?,?)";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getEmail());
            preparedStatement.setLong(3, company.getPassword().hashCode());
            preparedStatement.executeUpdate();

            final ResultSet generatedKeysResult = preparedStatement.getGeneratedKeys();

            if (!generatedKeysResult.next()) {
                System.err.println("Failed to retrieve an auto-generated id from the database," +
                        " please check if the id is set on Auto-increment");
            }
            return generatedKeysResult.getLong(1);

        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.CREATE, EntityType.COMPANY);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //                                      --Update--

    //Updates a company details.
    @Override
    public void update(final Company company) throws Exception {
        Connection connection = null;
        try {
            final String sqlStatement = "UPDATE companies SET email = ?, password = ? WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, company.getEmail());
            preparedStatement.setLong(2, company.getPassword().hashCode());
            preparedStatement.setLong(3, company.getId());

            preparedStatement.executeUpdate();

        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.UPDATE, EntityType.COMPANY);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //                                      --Delete--

    //Removes a company by company ID.
    @Override
    public void delete(final Long companyID) throws Exception {
        Connection connection = null;
        try {

            final String sqlStatement = "DELETE FROM companies WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, companyID);

            preparedStatement.executeUpdate();

        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.DELETE, EntityType.COMPANY);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //                                      --Getters--

    // Gets a specific company by company ID.
    @Override
    public Company read(final Long companyID) throws Exception {
        Connection connection = null;
        try {

            final String sqlStatement = "SELECT * FROM companies WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, companyID);

            final ResultSet result = preparedStatement.executeQuery();
            if (!result.next()) {
                System.err.println(companyID + "is not found");
            }
            return ObjectExtractionUtils.extractCompanyFromResultSet(result);
        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.READ, EntityType.COMPANY);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    // Gets a specific company by company email.
    @Override
    public Company readByEmail(final String email) throws Exception {
        Connection connection = null;
        try {

            final String sqlStatement = "SELECT * FROM companies WHERE email ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, email);

            final ResultSet result = preparedStatement.executeQuery();
            if (!result.next()) {
                System.err.println(email + "is not found");
            }
            return ObjectExtractionUtils.extractCompanyFromResultSet(result);

        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.READ, EntityType.COMPANY);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    // Gets all the companies that exist in the database.
    @Override
    public ArrayList<Company> readAll() throws Exception {
        Connection connection = null;
        final ArrayList<Company> companies = new ArrayList<>();

        try {
            final String sqlStatement = "SELECT * FROM companies";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            final ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                companies.add(ObjectExtractionUtils.extractCompanyFromResultSet(result));
            }
            return companies;
        } catch (final Exception e) {
            throw new EntityCrudException(CrudType.READ_ALL, EntityType.COMPANY);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
