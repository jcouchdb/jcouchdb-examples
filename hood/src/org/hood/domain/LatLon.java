package org.hood.domain;

/**
 * Wraps a latitude/longitude pair that represents a global position. The calculation methods treat
 * the LatLon like as vector.
 * 
 * @author shelmberger
 *
 */
public class LatLon
{
    private double latitude, longitude;

    /**
     * Creates a default LatLon instance at <code>0.0, 0.0</code>.
     */
    public LatLon()
    {
        this(0.0,0.0);
    }
    
    /**
     * Creates a LatLong object 
     * @param latitude      latitude 
     * @param longitude     longitude
     */
    public LatLon(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }
        
    public double getLongitude()
    {
        return longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }
    
    /**
     * Returns  a new LatLon that is the subsraction of this one minus the given
     * @param latLon    
     * @return
     */
    public LatLon substract(LatLon latLon)
    {
        return new LatLon(this.getLatitude() - latLon.getLatitude(), this.getLongitude() - latLon.getLongitude());
    }

    /**
     * Returns  a new LatLon that is the addition of this one minus the given
     * @param latLon    
     * @return
     */
    public LatLon add(LatLon that)
    {
        return new LatLon(this.getLatitude() + that.getLatitude(), this.getLongitude() + that.getLongitude());
    }
    
    /**
     * Creates a new scalar multiplation of the lat/long vector
     * @param latLon    
     * @return
     */
    public LatLon scale(double d)
    {
        return new LatLon(this.getLatitude() * d, this.getLongitude() * d);
    }
    
    @Override
    public String toString()
    {
        return super.toString() + ": latitude = " + latitude + ", longitude = " + longitude;
    }
}
