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
import org.jcouchdb.document.ViewAndDocumentsResult;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Some location related tests. Shows how to setup CouchDB integration tests.
 * 
 * The base class of this test makes Spring load some of the normal spring contexts for testing.
 * the @Autowired annotations inject our dependencies by type.
 * 
 * @author shelmberger
 *
 */
public class LocationServiceImplTestCase extends AbstractAppIntegrationTestCase
{
    private static Logger log = LoggerFactory.getLogger(LocationServiceImplTestCase.class);
   
    /**
     * Only works this easy if we only have one jcouchdb Database object defined in our loaded contexts.
     */
    @Autowired
    private Database systemDatabase;
    
    /**
     * Not really needed here, just being injected for reference
     */
    @Autowired
    private JSONConfigFactory jsonConfigFactory; 
    
    
    @Test
    public void testGetByKeys()
    {
        // This test case proves the one of the cooler properties of our global JSON config

        // first we create two test objects and store them in CouchDB
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
        
        // Then we query their ids (which they now have because we created them) again
        List<String> keys = Arrays.asList(hood.getId(), place.getId());       
        
        // and although we just say that we want a PositionedDocument, the
        // config created via the org.hood.JSONConfigFactory makes
        // svenson refine our selection based on the "docType" properties of our
        // new location objects. See the source of the class Member
        // #jsonConfigFactory above for details
        ViewAndDocumentsResult<Object, PositionedDocument> result = systemDatabase.queryDocumentsByKeys(
            Object.class, PositionedDocument.class, keys, null, null);
        
        // and indeed, we get the correct types back.
        assertThat(result, is(notNullValue()));
        assertThat(result.getRows().size(), is(2));
        assertThat(result.getRows().get(0).getDocument(), is(Hood.class));
        assertThat(result.getRows().get(1).getDocument(), is(Place.class));
        
        // no need to leave a mess.. We delete our Objects again.
        systemDatabase.delete(place);
        systemDatabase.delete(hood);
        
    }

}
