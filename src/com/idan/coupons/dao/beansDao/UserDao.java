package com.idan.coupons.dao.beansDao;

//This abstract has no use except for acting as a method list for the user dao.
public abstract class UserDao<ID, Entity> implements CrudDao<ID, Entity> {

    //Checks if an entity is exists in the database by entity email.
    public abstract boolean isExistsByEmail(final String email) throws Exception;

    //Reads an entity by email.
    public abstract Entity readByEmail(final String email) throws Exception;

}
