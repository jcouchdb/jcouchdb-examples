package org.hood;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Arrays;
import java.util.List;

import org.hood.domain.Hood;
import org.hood.domain.LatLon;
import org.hood.domain.Place;
import org.hood.domain.PositionedDocument;
import org.jcouchdb.db.Database;
import org.jcouchdb.document.ValueAndDocumentRow;
import org.jcouchdb.document.ViewAndDocumentsResult;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class LocationServiceImplTestCase extends AbstractAppIntegrationTestCase
{
    private static Logger log = LoggerFactory.getLogger(LocationServiceImplTestCase.class);
    
    @Autowired
    private Database systemDatabase;
    
    @Test
    public void testGetByKeys()
    {
        Hood hood = new Hood();
        hood.setName("Test Hood");
        hood.setLocation(new LatLon(1,1));
        hood.setProperty("test", true);
        systemDatabase.createDocument(hood);
       
        Place place = new Place();
        place.setName("Test Place");
        place.setLocation(new LatLon(2,2));
        place.setProperty("test", true);
        systemDatabase.createDocument(place);
        
        List<String> keys = Arrays.asList(hood.getId(), place.getId());
        
        ViewAndDocumentsResult<Object, PositionedDocument> result = systemDatabase.queryDocumentsByKeys(Object.class, PositionedDocument.class, keys, null, null);
        
        assertThat(result, is(notNullValue()));
        assertThat(result.getRows().size(), is(2));
        assertThat(result.getRows().get(0).getDocument(), is(Hood.class));
        assertThat(result.getRows().get(1).getDocument(), is(Place.class));
        
        systemDatabase.delete(place);
        systemDatabase.delete(hood);
        
    }
}
