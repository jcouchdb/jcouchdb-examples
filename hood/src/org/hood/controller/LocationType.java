/**
 * 
 */
package org.hood.controller;

import org.hood.domain.Hood;
import org.hood.domain.Person;
import org.hood.domain.Place;

public enum LocationType
{
    HOOD(Hood.class), PLACE(Place.class), PERSON(Person.class);
    
    private Class domainType;
    
    private LocationType(Class cls)
    {
        this.domainType = cls;
    }
    
    public Class getDomainType()
    {
        return domainType;
    }
}