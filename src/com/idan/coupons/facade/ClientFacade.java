package com.idan.coupons.facade;

public interface ClientFacade {

    // Login method that checks if the inputs email and password are valid.
    boolean login(final String email, final String password) throws Exception;

}
