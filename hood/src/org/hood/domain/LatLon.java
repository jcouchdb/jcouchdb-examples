package org.hood.domain;


public class LatLon
{
    private double latitude, longitude;

    public LatLon()
    {
        this(0.0,0.0);
    }
    
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
    
    public LatLon substract(LatLon that)
    {
        return new LatLon(this.getLatitude() - that.getLatitude(), this.getLongitude() - that.getLongitude());
    }

    public LatLon add(LatLon that)
    {
        return new LatLon(this.getLatitude() + that.getLatitude(), this.getLongitude() + that.getLongitude());
    }
    
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
