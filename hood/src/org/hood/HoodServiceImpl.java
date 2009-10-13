package org.hood;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hood.domain.Hood;
import org.jcouchdb.db.Database;
import org.jcouchdb.document.ValueAndDocumentRow;
import org.jcouchdb.document.ViewAndDocumentsResult;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;
import org.svenson.JSONConfig;

public class HoodServiceImpl implements HoodService
{

    private Database systemDatabase;

    private JSONConfig jsonConfig;

    private Map<String,Database> hoodDbs = new HashMap<String, Database>();

    @Required
    public void setJsonConfig(JSONConfig jsonConfig)
    {
        this.jsonConfig = jsonConfig;
    }

    @Required
    public void setSystemDatabase(Database systemDatabase)
    {
        this.systemDatabase = systemDatabase;
    }

    private File designDocsBaseDir;

    public void setDesignDocsBaseDir(Resource designDocsBaseDir) throws IOException
    {
        File file = designDocsBaseDir.getFile();

        if (!file.exists())
        {
            file.mkdirs();
        }
        else
        {
            if (!file.isDirectory())
            {
                throw new IllegalArgumentException(designDocsBaseDir + " is not a directory");
            }
        }

        this.designDocsBaseDir = file;
    }

    public Hood createHood(String name, String description, String userId)
    {
        Hood hood = new Hood();
        hood.setName(name);
        hood.setUserId(userId);
        hood.setDescription(description);
        systemDatabase.createDocument(hood);
        return hood;
    }

    private String getDbNameFor(String name)
    {
        return "hood_" + name;
    }

    public List<Hood> listHoods()
    {
        ViewAndDocumentsResult<Object, Hood> result = systemDatabase.queryViewAndDocuments("hood/byName", Object.class, Hood.class, null, jsonConfig.getJsonParser());

        List<Hood> hoods = new ArrayList<Hood>();
        for (ValueAndDocumentRow<Object, Hood> row : result.getRows())
        {
            hoods.add(row.getDocument());
        }

        return hoods;
    }

    public Hood getDefault()
    {
        ViewAndDocumentsResult<String, Hood> result = systemDatabase.queryViewAndDocuments("hood/default", String.class, Hood.class, null, jsonConfig.getJsonParser());
        return result.getRows().get(0).getDocument();
    }

    public Hood getHood(String id)
    {
        return systemDatabase.getDocument(Hood.class, id, null, jsonConfig.getJsonParser());
    }

}
