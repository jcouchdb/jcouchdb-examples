package org.hood;

import java.util.List;

import org.hood.controller.LocationType;
import org.hood.domain.Hood;
import org.hood.domain.LatLon;
import org.hood.domain.Person;
import org.hood.domain.Place;
import org.hood.domain.PositionedDocument;

/**
 * Service interface for PositionedDocuments 
 * @author shelmberger
 *
 */
public interface LocationService
{
    /**
     * Returns a list with instances of the positioned document types ( {@link Hood}, {@link Person} and {@link Place} ) within
     * the given area. The area is given as pair of {@link LatLon} that desribe the latitude/longitude values for the north east
     * and the south west corner of the area of interest.
     * 
     * @param ne    north east LatLong 
     * @param sw    south west LatLong
     * @return
     */
    List<PositionedDocument> getDocumentsWithinBounds(LatLon ne, LatLon sw);
    
    /**
     * Returns a raw CouchDB view with documents in the given area. 
     * 
     *  @see #getDocumentsWithinBounds(LatLon, LatLon)
     * 
     * @param ne    north east LatLong 
     * @param sw    south west LatLong
     * @return
     */
    String getDocumentsWithinBoundsJSON(LatLon ne, LatLon sw);

    /**
     * Creates a new location with the given parameters
     * 
     * @param type              type enum
     * @param name              Name of the location
     * @param description       description of the location
     * @param location          location
     */
    void createLocation(LocationType type, String name, String description, LatLon location);
}
