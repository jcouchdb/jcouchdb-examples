package org.hood;

import java.util.List;

import org.hood.domain.Hood;

public interface HoodService
{
    Hood createHood(String name, String description, String userId);
    
    List<Hood> listHoods();
    
    Hood getDefault();
    
    Hood getHood(String id);    
}
