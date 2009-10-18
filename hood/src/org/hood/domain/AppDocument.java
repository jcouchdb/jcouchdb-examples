package org.hood.domain;

import org.hood.JSONConfigFactory;
import org.jcouchdb.document.BaseDocument;
import org.svenson.JSONProperty;

/**
 * Base class for the documents in our app. Contains a read-only documentType / docType property on
 * which the {@link JSONConfigFactory} used to discriminate types.
 * 
 * @author shelmberger
 *
 */
public class AppDocument extends BaseDocument
{
    /**
     * Returns the simple name of the class as doc type.
     * 
     * The annotation makes it a read-only property and also shortens the JSON name a little.
     * @return
     */
    @JSONProperty(value = "docType", readOnly = true)
    public String getDocumentType()
    {
        return this.getClass().getSimpleName();
    }
}
