package org.hood.controller;

import org.hood.domain.Hood;
import org.hood.domain.Person;
import org.hood.domain.Place;

public class NewObjectCommand
{
    private String name, description;
    private double lat, lon;
    private Type type = Type.PLACE;
    
    public String getName()
    {
        return name;
    }
    public String getDescription()
    {
        return description;
    }
    public double getLat()
    {
        return lat;
    }
    public double getLon()
    {
        return lon;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public void setLat(double lat)
    {
        this.lat = lat;
    }
    public void setLon(double lon)
    {
        this.lon = lon;
    }
    
    public Type getType()
    {
        return type;
    }
    public void setType(Type type)
    {
        this.type = type;
    }
    
    
    
    public static enum Type
    {
        HOOD(Hood.class), PLACE(Place.class), PERSON(Person.class);
        
        private Class domainType;
        
        private Type(Class cls)
        {
            this.domainType = cls;
        }
        
        public Class getDomainType()
        {
            return domainType;
        }
    }



    @Override
    public String toString()
    {
        return "NewObjectCommand [description=" + description + ", lat=" + lat + ", lon=" + lon +
            ", name=" + name + ", type=" + type + "]";
    }

}
