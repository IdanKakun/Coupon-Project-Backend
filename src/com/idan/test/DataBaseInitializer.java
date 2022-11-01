package com.idan.test;

import com.idan.coupons.dao.beansDao.CouponDao;
import com.idan.coupons.dao.connectionPool.ConnectionPool;
import com.idan.coupons.enums.CategoryType;
import com.idan.coupons.exceptions.ConnectionToDBError;

import java.sql.*;

//A class that defines the initializing of sql tables.
public class DataBaseInitializer {

    public static final DataBaseInitializer instance = new DataBaseInitializer();
    private static ConnectionPool connectionPool;

    //Constructor- creates an instance of connection to the database.
    private DataBaseInitializer() {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (Throwable e) {
            throw new ConnectionToDBError();
        }
    }

    //Return a sql function of create the companies table.
    private static String createCompaniesTable() {
        return "CREATE TABLE `coupon-project`.`companies` (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `email` VARCHAR(45) NOT NULL,\n" +
                "  `password` INT NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,\n" +
                "  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,\n" +
                "  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);\n";
    }

    //Return a sql function of create the categories table.
    private static String createCategoriesTable() {
        return "CREATE TABLE `coupon-project`.`categories` (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,\n" +
                "  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);";
    }

    //Insert the categories into the table.
    private static void insertCategories() throws Exception {
        CouponDao.instance.insertCategory(CategoryType.FOOD);
        CouponDao.instance.insertCategory(CategoryType.ELECTRICITY);
        CouponDao.instance.insertCategory(CategoryType.RESTAURANT);
        CouponDao.instance.insertCategory(CategoryType.VACATION);
        CouponDao.instance.insertCategory(CategoryType.GIFT);
        CouponDao.instance.insertCategory(CategoryType.VIDEO);
        CouponDao.instance.insertCategory(CategoryType.INTERNET);
    }

    //Return a sql function of create the coupons table.
    private static String createCouponsTable() {
        return "CREATE TABLE `coupon-project`.`coupons` (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  `company_id` BIGINT NOT NULL,\n" +
                "  `category` VARCHAR(45) NOT NULL,\n" +
                "  `title` VARCHAR(45) NOT NULL,\n" +
                "  `description` VARCHAR(45) NULL,\n" +
                "  `start_date` VARCHAR(45) NOT NULL,\n" +
                "  `end_date` VARCHAR(45) NOT NULL,\n" +
                "  `amount` INT NOT NULL,\n" +
                "  `price` DOUBLE NOT NULL,\n" +
                "  `image` VARCHAR(100) NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,\n" +
                "  UNIQUE INDEX `title_UNIQUE` (`title` ASC) VISIBLE,\n" +
                "  INDEX `company_id_idx` (`company_id` ASC) VISIBLE,\n" +
                "  CONSTRAINT `company_id` FOREIGN KEY (`company_id`) REFERENCES `coupon-project`.`companies` (`id`),\n" +
                "  CONSTRAINT `category` FOREIGN KEY (`category`) REFERENCES `coupon-project`.`categories` (`name`) \n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION);";
    }

    //Return a sql function of create the customers table.
    private static String createCustomerTable() {
        return "CREATE TABLE `coupon-project`.`customer` (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  `first_name` VARCHAR(45) NOT NULL,\n" +
                "  `last_name` VARCHAR(45) NOT NULL,\n" +
                "  `email` VARCHAR(45) NOT NULL,\n" +
                "  `password` INT NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,\n" +
                "  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);";
    }

    //Return a sql function of create the customer_vs_coupons table.
    private static String createCustomers_vs_CouponsTable() {
        return "CREATE TABLE `coupon-project`.`customers_vs_coupons` (\n" +
                "  `customer_id` BIGINT NOT NULL,\n" +
                "  `coupon_id` BIGINT NOT NULL,\n" +
                "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE,\n" +
                "  INDEX `customer_id_idx` (`customer_id` ASC) VISIBLE,\n" +
                "  CONSTRAINT `customer_id`\n" +
                "    FOREIGN KEY (`customer_id`)\n" +
                "    REFERENCES `coupon-project`.`customer` (`id`)\n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION,\n" +
                "  CONSTRAINT `coupon_id`\n" +
                "    FOREIGN KEY (`coupon_id`)\n" +
                "    REFERENCES `coupon-project`.`coupons` (`id`)\n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION);";
    }

    //Create tables in the database.
    public static void createTables() {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final Statement stmt = connection.createStatement();

            final String categoriesTable = createCategoriesTable();
            final String companiesTable = createCompaniesTable();
            final String couponsTable = createCouponsTable();
            final String customerTable = createCustomerTable();
            final String customers_vs_couponsTable = createCustomers_vs_CouponsTable();
            stmt.executeUpdate(categoriesTable);
            stmt.executeUpdate(companiesTable);
            stmt.executeUpdate(couponsTable);
            stmt.executeUpdate(customerTable);
            stmt.executeUpdate(customers_vs_couponsTable);
            insertCategories();
            System.out.println("\nCreated tables in given database...");
            System.out.println("\nThe tables were created successfully!\n");
        } catch (final Throwable e) {
            throw new ConnectionToDBError("Failed to create tables | Tables are already exist.");
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //Drop all tables in the database.
    public static void dropAllTables() {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            String query = "DROP TABLE `coupon-project`.`categories`,`coupon-project`.`companies`,`coupon-project`.`coupons`," +
                    "`coupon-project`.`customer`,`coupon-project`.`customers_vs_coupons` ";
            connection.prepareStatement(query).execute();
            System.out.println("\nAll tables have been dropped successfully!\n");

        } catch (final Throwable e) {
            throw new ConnectionToDBError("Failed to drop tables | No tables exist.");
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}

