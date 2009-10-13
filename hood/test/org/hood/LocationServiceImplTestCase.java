package org.hood;

import java.util.Arrays;
import java.util.List;

import org.hood.domain.PositionedDocument;
import org.jcouchdb.db.Database;
import org.jcouchdb.document.ValueAndDocumentRow;
import org.jcouchdb.document.ViewAndDocumentsResult;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.svenson.JSONConfig;


public class LocationServiceImplTestCase extends AbstractAppIntegrationTestCase
{
    private static Logger log = LoggerFactory.getLogger(LocationServiceImplTestCase.class);
    
    @Autowired
    private Database systemDatabase;
    @Autowired
    private JSONConfig jsonConfig;
    
    @Test
    public void testGetByKeys()
    {
        List<String> keys = Arrays.asList("7c2e48fc12e5e8f08c836e06972ab416","4baae3e9cf34df59eca5ca6b6709fb87","1ee33fa85efc7c41dc837b86d3ad31d6");

        ViewAndDocumentsResult<Object, PositionedDocument> result = systemDatabase.queryDocumentsByKeys(Object.class, PositionedDocument.class, keys, null, jsonConfig.getJsonParser());
        
        for (ValueAndDocumentRow<Object, PositionedDocument> row : result.getRows())
        {
            log.info("{}", row.getDocument());
        }
        
    }
}
