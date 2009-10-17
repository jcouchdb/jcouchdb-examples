package org.hood.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import junit.framework.Assert;

import org.hood.AbstractAppIntegrationTestCase;
import org.jcouchdb.db.Database;
import org.jcouchdb.document.BaseDocument;
import org.jcouchdb.document.DocumentInfo;
import org.jcouchdb.document.ValueAndDocumentRow;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewAndDocumentsResult;
import org.jcouchdb.document.ViewResult;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class LoadTestCase extends AbstractAppIntegrationTestCase 
{
    private static Logger log = LoggerFactory.getLogger(LoadTestCase.class);
    
    @Autowired
    private Database sysDatabase;
    
    
    //@Test
//    @Ignore
//    public void testCreateAdminUser()
//    {
//        Random rnd = new Random(0);
//        
//        //LatLon qs = new LatLon(51.49979            ,7.451662);
//        
//        for (int i=0; i < 10000; i++)
//        {
//        
//            Place doc = new Place();
//            doc.setName("Random "+i);
//            doc.setDescription("");
//            doc.setProperty("random", true);
//            double lon = rnd.nextDouble() * 180 - 90;
//            double lat = rnd.nextDouble() * 360 - 180;
//            
//            doc.setLocation(new LatLon(lat,lon));
//            sysDatabase.createDocument(doc);
//        }
//    }

    @Test
    public void testRemoveRandom()
    {
        ViewResult<String> result = sysDatabase.queryView("random/list", String.class, null, null);
        
        List<BaseDocument> docs = new ArrayList<BaseDocument>();
        for (ValueRow<String> row : result.getRows())
        {
            BaseDocument doc = new BaseDocument();
            doc.setId(row.getId());
            doc.setRevision((String)row.getKey());
            docs.add(doc);
        }
        
        log.info("deleting {} random ", docs);
        
        List<DocumentInfo> infos = sysDatabase.bulkDeleteDocuments(docs);
        
        for (DocumentInfo info : infos)
        {
            if (!info.isOk())
            {
                Assert.fail("info !ok: " +info);
            }
        }
    }
}
