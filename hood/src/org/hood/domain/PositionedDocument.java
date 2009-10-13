package org.hood.domain;

import org.svenson.JSONProperty;
import org.svenson.converter.JSONConverter;

public class PositionedDocument
    extends AppDocument
    implements Positioned
{
    private LatLon location;

    private String name;


    /** {@inheritDoc} */
    public LatLon getLocation()
    {
        return location;
    }


    /** {@inheritDoc} */
    @JSONProperty("loc")
    @JSONConverter(type = LatLongConverter.class)
    public void setLocation(LatLon location)
    {
        this.location = location;
    }


    /** {@inheritDoc} */
    public String getName()
    {
        return name;
    }

    /** {@inheritDoc} */
    public void setName(String name)
    {
        this.name = name;
    }

}
