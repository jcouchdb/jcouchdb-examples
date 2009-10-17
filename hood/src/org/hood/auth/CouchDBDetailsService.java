package org.hood.auth;

import org.hood.domain.CouchDBDetails;
import org.hood.domain.User;
import org.jcouchdb.db.Database;
import org.jcouchdb.db.Options;
import org.jcouchdb.document.ViewAndDocumentsResult;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.svenson.JSONConfig;

/**
 * A Spring security {@link UserDetailsService} implementation for CouchDB
 * 
 * @author shelmberger
 *
 */
public class CouchDBDetailsService implements UserDetailsService
{
    private Database database;
    private JSONConfig jsonConfig;
    
    /**
     * Configures the CouchDB {@link Database} from which to retrieve the user objects
     * @param database
     */
    @Required
    public void setDatabase(Database database)
    {
        this.database = database;
    }

    /**
     * Loads the User based on the user name.
     */
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException,
        DataAccessException
    {
        // Retrieve the user/byName view including documents parsed into User objects.
        ViewAndDocumentsResult<Object, User> result =  database.queryViewAndDocuments("user/byName", Object.class, User.class, new Options().key(name), jsonConfig.getJsonParser());        
        if (result.getRows().size() == 0)
        {
            throw new UsernameNotFoundException("No user with name '" + name + "' found.");
        }
        if (result.getRows().size() > 1)
        {
            throw new UsernameNotFoundException("More than one user with name '" + name + "' found.");
        }
        
        return new CouchDBDetails(result.getRows().get(0).getDocument());
    }

}
