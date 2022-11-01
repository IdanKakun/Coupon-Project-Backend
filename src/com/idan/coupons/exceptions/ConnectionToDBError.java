package com.idan.coupons.exceptions;

//A class that defines the error of connection to the database.
public class ConnectionToDBError extends Error {

    //Specific constructor for errors connection.
    public ConnectionToDBError() {
        super("Failed to establish connection with database");
    }

    //General constructor for errors connection.
    public ConnectionToDBError(String message) {
        super(message);
    }
}
