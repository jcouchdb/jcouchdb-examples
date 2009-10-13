package org.hood.domain;

import java.util.Random;

import org.hood.AbstractAppIntegrationTestCase;
import org.jcouchdb.db.Database;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.dao.salt.ReflectionSaltSource;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


public class LoadTestCase extends AbstractAppIntegrationTestCase 
{
    @Autowired
    private Database sysDatabase;
    
    
    //@Test
    
    public void testCreateAdminUser()
    {
        Random rnd = new Random(0);
        
        //LatLon qs = new LatLon(51.49979            ,7.451662);
        
        for (int i=0; i < 10000; i++)
        {
        
            Place doc = new Place();
            doc.setName("Random "+i);
            doc.setDescription("");
            doc.setProperty("random", true);
            double lon = rnd.nextDouble() * 180 - 90;
            double lat = rnd.nextDouble() * 360 - 180;
            
            doc.setLocation(new LatLon(lat,lon));
            sysDatabase.createDocument(doc);
        }
    }

}
