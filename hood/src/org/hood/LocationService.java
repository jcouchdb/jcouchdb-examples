package org.hood;

import java.util.List;

import org.hood.domain.LatLon;
import org.hood.domain.PositionedDocument;

public interface LocationService
{
    List<PositionedDocument> getDocumentsWithinBounds(LatLon ne, LatLon sw);
    String getDocumentsWithinBoundsJSON(LatLon ne, LatLon sw);
}
