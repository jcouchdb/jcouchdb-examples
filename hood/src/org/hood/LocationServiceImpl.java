package org.hood;

import java.util.ArrayList;
import java.util.List;

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

public class LocationServiceImpl
    implements LocationService
{
    private static Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);
    
    private Database systemDatabase;

    private JSONConfig jsonConfig;


    @Required
    public void setJsonConfig(JSONConfig jsonConfig)
    {
        this.jsonConfig = jsonConfig;
    }


    @Required
    public void setSystemDatabase(Database systemDatabase)
    {
        this.systemDatabase = systemDatabase;
    }


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


    public String getDocumentsWithinBoundsJSON(LatLon ne, LatLon sw)
    {
        StringBuilder json = new StringBuilder();

        // XXX: Sucky querying algorithm, should be replaced by a real external
        // lat/lon indexer

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

        // JSON data Flow would be couchdb -> controller -> client-side js
        // so we just pass along the uninterpreted couchdb JSON documents to
        // save some JSON parsing / JSONifiying which
        // we don't need because the data here is only used in client-side js
        json.append('[');
        boolean first = true;
        for (String docId : docIds)
        {
            String content = getRawDocument(docId);
            if (!first)
            {
                json.append(',');
            }
            json.append(content);
            first = false;
        }
        json.append(']');
        return json.toString();
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


    private String getRawDocument(String docId)
    {
        Response response = null;
        try
        {
            // get the document content as string from couchdb
            response = systemDatabase.getServer().get("/" + systemDatabase.getName() + "/" + docId);
            return response.getContentAsString();
        }
        finally
        {
            // *DON'T* forget to destroy the response object if you do stuff
            // like this
            // otherwise bad things will happen (connection leaks in http core
            // to be exact with following
            // hanging of everything due to hitting the connection limit)
            if (response != null)
            {
                response.destroy();
            }
        }
    }
}
