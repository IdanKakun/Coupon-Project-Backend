
package com.idan.coupons.exceptions;

import com.idan.coupons.enums.CrudType;
import com.idan.coupons.enums.EntityType;

//A class that defines exceptions to crud entities functions.
public class EntityCrudException extends Exception {

    //Constructor- exceptions to crud entities functions.
    public EntityCrudException(final CrudType crudType, final EntityType entityType) {
        super("Error | " + crudType + " | " + entityType);
    }
}
