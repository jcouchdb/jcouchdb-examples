package org.hood.domain;

public interface Positioned
{
    /**
     * Returns the location of the object
     * @return
     */
    LatLon getLocation();

    /**
     * Returns the name of the object at the position.
     * @return
     */
    String getName();

    
    /**
     * Returns the type of the object 
     * @return
     */
    String getDocumentType();
}
