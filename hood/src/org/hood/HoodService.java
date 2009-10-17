package org.hood;

import java.util.List;

import org.hood.domain.Hood;

/**
 * Service Interface to handle Hood Objects
 * 
 * @author shelmberger
 *
 */
public interface HoodService
{
    /**
     * Creates the given hood with the given name and description for the given userId
     * 
     * @param name          name 
     * @param description   description
     * @param userId        user id
     * @return
     */
    Hood createHood(String name, String description, String userId);
    
    /**
     * Returns a list of all hoods
     * 
     * @return
     */
    List<Hood> listHoods();
    
    /**
     * Returns the hood marked as default.
     * @return
     */
    Hood getDefault();
    
    /**
     * Returns the hood with the given id.
     * @param id
     * @return
     */
    Hood getHood(String id);    
}
