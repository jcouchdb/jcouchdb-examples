package org.hood.domain;

import org.jcouchdb.db.Database;
import org.junit.Test;
import org.springframework.security.providers.dao.salt.ReflectionSaltSource;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


public class UserTestCase extends AbstractDependencyInjectionSpringContextTests
{
    @Override
    protected String[] getConfigLocations()
    {
        return new String[] {
            "file:WebContent/WEB-INF/config/services-config.xml",
            "file:WebContent/WEB-INF/config/data-access-config.xml"
          };
    }
    
    @Test
    public void testCreateAdminUser()
    {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        
        User u = new User();
        u.setName("admin");
        AppUserDetails appUserDetails = new AppUserDetails(u);
        ReflectionSaltSource reflectionSaltSource = new ReflectionSaltSource();
        reflectionSaltSource.setUserPropertyToUse("saltKey");
        String hash = encoder.encodePassword("admin", reflectionSaltSource.getSalt(appUserDetails));
        u.setPasswordHash(hash);
    
        Database db =(Database)applicationContext.getBean("systemDatabase");
        db.createDocument(u);

    }

}
