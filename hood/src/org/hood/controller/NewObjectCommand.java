package org.hood.controller;


/**
 * Wrap new object form data
 * 
 * @author shelmberger
 *
 */
public class NewObjectCommand
{
    private String name, description;
    private double lat, lon;
    private LocationType locationType = LocationType.PLACE;
    
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
    
    public LocationType getLocationType()
    {
        return locationType;
    }
    public void setLocationType(LocationType locationType)
    {
        this.locationType = locationType;
    }
    
    
    
    @Override
    public String toString()
    {
        return "NewObjectCommand [description=" + description + ", lat=" + lat + ", lon=" + lon +
            ", name=" + name + ", type=" + locationType + "]";
    }

}
