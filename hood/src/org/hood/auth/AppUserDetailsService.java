package org.hood.auth;

import org.hood.domain.AppUserDetails;
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

public class AppUserDetailsService implements UserDetailsService
{
    private Database systemDatabase;
    private JSONConfig jsonConfig;
    
    @Required
    public void setSystemDatabase(Database systemDatabase)
    {
        this.systemDatabase = systemDatabase;
    }

    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException,
        DataAccessException
    {
        
        ViewAndDocumentsResult<Object, User> result =  systemDatabase.queryViewAndDocuments("user/byName", Object.class, User.class, new Options().key(name), jsonConfig.getJsonParser());        
        if (result.getRows().size() == 0)
        {
            throw new UsernameNotFoundException("No user with name '" + name + "' found.");
        }
        if (result.getRows().size() > 1)
        {
            throw new UsernameNotFoundException("More than one user with name '" + name + "' found.");
        }
        
        return new AppUserDetails(result.getRows().get(0).getDocument());
    }

}
