package org.hood.domain;

import java.util.Arrays;
import java.util.List;

import org.svenson.converter.TypeConverter;

/**
 * JSON type converter that convertes {@link LatLon} instances to Arrays ( <code>[ latitude, longitude ]</code> ) and back.
 * @author shelmberger
 *
 */
public class LatLongConverter implements TypeConverter
{

    public Object fromJSON(Object arg0)
    {
        List<Double> l = (List<Double>)arg0;
        return new LatLon(l.get(0).doubleValue(),l.get(1).doubleValue());
    }

    public Object toJSON(Object arg0)
    {
        LatLon latLon = (LatLon) arg0;
        return Arrays.asList(latLon.getLatitude(),latLon.getLongitude());
    }

}
