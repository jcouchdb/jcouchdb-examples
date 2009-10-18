package org.hood;

import java.util.ArrayList;
import java.util.List;

import org.hood.domain.Hood;
import org.hood.domain.LatLon;
import org.jcouchdb.db.Database;
import org.jcouchdb.document.ValueAndDocumentRow;
import org.jcouchdb.document.ViewAndDocumentsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

/**
 * Hood Service implementation based on couchdb.
 * 
 * @author shelmberger
 *
 */
public class HoodServiceImpl implements HoodService, InitializingBean
{
    private static Logger log = LoggerFactory.getLogger(HoodServiceImpl.class);
    
    /** the hood database */
    private Database systemDatabase;

    /** Did we have a default hood yet? */
    private boolean haveDefault = false; 
    
    /**
     * Provices the system database
     * 
     * @param systemDatabase
     */
    @Required
    public void setSystemDatabase(Database systemDatabase)
    {
        Assert.notNull(systemDatabase, "systemDatabase can't be null");
        
        this.systemDatabase = systemDatabase;
    }

    /**
     * {@inheritDoc}
     */
    public Hood createHood(String name, String description, String userId)
    {
        // create and store a new hood object
        Hood hood = new Hood();

        if (!haveDefault)
        {
            hood.setDefaultHood(true);
        }
        
        hood.setName(name);
        hood.setUserId(userId);
        hood.setDescription(description);
        systemDatabase.createDocument(hood);
        if (!haveDefault)
        {
            haveDefault = true;
        }
        return hood;
    }

    /**
     * {@inheritDoc}
     */
    public List<Hood> listHoods()
    {
        // query the hood/byName view including Documents 
        // we're using Object.class as view.class because we don't care.. if you use Object, the value ends up being a list or a map, depending on mapped value.
        // this is the normal state for the key object.
        ViewAndDocumentsResult<Object, Hood> result = systemDatabase.queryViewAndDocuments("hood/byName", Object.class, Hood.class, null, null);

        // iterate over all rows and collect hoods in list 
        List<Hood> hoods = new ArrayList<Hood>();
        for (ValueAndDocumentRow<Object, Hood> row : result.getRows())
        {
            hoods.add(row.getDocument());
        }
        return hoods;
    }

    /**
     * {@inheritDoc}
     */
    public Hood getDefault()
    {
        // query and return the supposedly only object in the all views 
        ViewAndDocumentsResult<String, Hood> result = systemDatabase.queryViewAndDocuments("hood/default", String.class, Hood.class, null, null);
        
        // if we have no object, we create a new one
        if (result.getRows().size() < 1)
        {
            Hood hood = new Hood();
            hood.setName("Default Hood");
            hood.setDescription("This only exists because there is no hood defined yet. It is placed here because of a reason connected to CouchDB.");
            // some place in Charlotte, NC, USA
            hood.setLocation(new LatLon(35.225052,-80.839291));
            hood.setDefaultHood(false);
            
            haveDefault = true;
            return hood;
        }
        else
        {
            haveDefault = false;
            return result.getRows().get(0).getDocument();
        }
    }

    /**
     * {@inheritDoc}
     */
    public Hood getHood(String id)
    {
        return systemDatabase.getDocument(Hood.class, id, null, null);
    }

    public void afterPropertiesSet() throws Exception
    {
        // initialize default hood
        getDefault();
    }
}
