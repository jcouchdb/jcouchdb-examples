package org.hood.domain;

import org.jcouchdb.document.BaseDocument;
import org.svenson.JSONProperty;

public class AppDocument extends BaseDocument
{
    @JSONProperty(value = "docType", readOnly = true)
    public String getDocumentType()
    {
        return this.getClass().getSimpleName();
    }
    
}
