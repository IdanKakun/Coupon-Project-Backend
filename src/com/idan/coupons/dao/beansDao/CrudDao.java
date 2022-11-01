
package com.idan.coupons.dao.beansDao;

import java.util.ArrayList;


//This interface has no use except for acting as a method list for the dao.
public interface CrudDao<ID, Entity> {

    //Creates a new entity.
    Long create(final Entity entity) throws Exception;

    //Updates an entity details.
    void update(final Entity entity) throws Exception;

    //Removes an entity by ID.
    void delete(final ID id) throws Exception;

    //Reads an entity by ID.
    Entity read(final ID id) throws Exception;

    //Read all entities.
    ArrayList<Entity> readAll() throws Exception;

}
