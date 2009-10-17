package org.hood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hood.domain.LatLon;
import org.hood.domain.PositionedDocument;
import org.jcouchdb.db.Database;
import org.jcouchdb.db.Options;
import org.jcouchdb.db.Response;
import org.jcouchdb.document.ValueAndDocumentRow;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewAndDocumentsResult;
import org.jcouchdb.document.ViewResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.svenson.JSONConfig;

/**
 * CouchDB implementation of the location interface
 * 
 * @author shelmberger
 *
 */
public class LocationServiceImpl
    implements LocationService
{
    private static Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);
    
    private Database systemDatabase;

    private JSONConfig jsonConfig;

    /**
     * Configures the JSONConfig to be used 
     * @param jsonConfig
     */
    @Required
    public void setJsonConfig(JSONConfig jsonConfig)
    {
        this.jsonConfig = jsonConfig;
    }

    /**
     * Configures the hood system database
     * 
     * @param systemDatabase
     */
    @Required
    public void setSystemDatabase(Database systemDatabase)
    {
        this.systemDatabase = systemDatabase;
    }

    /**
     * {@inheritDoc}
     */
    public List<PositionedDocument> getDocumentsWithinBounds(LatLon ne, LatLon sw)
    {
        double startLon = ne.getLongitude();
        double endLon = sw.getLongitude();

        if (endLon < startLon)
        {
            double h = endLon;
            endLon = startLon;
            startLon = h;
        }

        List<PositionedDocument> docs = new ArrayList<PositionedDocument>();

        ViewAndDocumentsResult<Object, PositionedDocument> result = systemDatabase
            .queryViewAndDocuments("objects/byLat", Object.class, PositionedDocument.class,
                getLatLonOptions(ne, sw), jsonConfig.getJsonParser());
        for (ValueAndDocumentRow<Object, PositionedDocument> row : result.getRows())
        {
            Double longitude = getDoubleFromRow(row);

            if (longitude >= startLon && longitude <= endLon)
            {
                docs.add(row.getDocument());
            }
        }

        return docs;
    }

    /**
     * {@inheritDoc}
     */
    public String getDocumentsWithinBoundsJSON(LatLon ne, LatLon sw)
    {
        // XXX: Sucky querying algorithm, should be replaced by a better view
        

        // first just query all objects with in the latitude interval in
        // question
        ViewResult<Object> result = systemDatabase.queryView("objects/byLat", Object.class,
            getLatLonOptions(ne, sw), jsonConfig.getJsonParser());
        double startLon = ne.getLongitude();
        double endLon = sw.getLongitude();

        if (endLon < startLon)
        {
            double h = endLon;
            endLon = startLon;
            startLon = h;
        }

        // iterate over all matching results and collect the ids of those who
        // also match the long
        List<String> docIds = new ArrayList<String>();
        for (ValueRow<Object> row : result.getRows())
        {
            Double longitude = getDoubleFromRow(row);

            if (longitude >= startLon && longitude <= endLon)
            {
                docIds.add(row.getId());
            }
        }
        
        String json = getRawDocuments(docIds);
        log.debug("Raw JSON: {}", json);
        return json.toString();
    }

    /**
     * This method shows how you can get the raw JSON data in cases where you are just proxying the request for 
     * a javascript client. This saves a little server processing as there is no JSONifying or JSON parsing nescessary.
     * 
     * @param docIds    list of doc ids to get as raw JSON from the _all_docs view
     * @return
     */
    private String getRawDocuments(List<String> docIds)
    {
        Map m = new HashMap();
        m.put("keys", docIds);

        Response response = null;
        try
        {
            // get all docs by posting the keys to _all_docs
            response = systemDatabase.getServer().post("/" + systemDatabase.getName() + "/_all_docs?include_docs=true", jsonConfig.getJsonGenerator().forValue(m));
            return response.getContentAsString();
        }
        finally
        {
            // *DON'T* forget to destroy the response object if you do stuff
            // like this otherwise bad things will happen 
            // (e.g. connection leaks in http core making everything hang due
            //  to hitting the connection limit)
            if (response != null)
            {
                response.destroy();
            }
        }
    }


    private Double getDoubleFromRow(ValueRow<Object> row)
    {
        Object value = row.getValue();
        if (value instanceof String)
        {
            return Double.parseDouble((String)value);
        }
        else
        {
            return (Double)value;
        }
    }


    private Options getLatLonOptions(LatLon ne, LatLon sw)
    {
        double startLat = ne.getLatitude();
        double endLat = sw.getLatitude();

        if (endLat < startLat)
        {
            double h = endLat;
            endLat = startLat;
            startLat = h;
        }

        return new Options().startKey(endLat).endKey(startLat).descending(true);
    }

}
