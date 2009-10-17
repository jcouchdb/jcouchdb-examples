package org.hood.controller;

import java.util.HashMap;
import java.util.Map;

import org.hood.domain.PositionedDocument;
import org.hood.util.JSONView;
import org.jcouchdb.db.Database;
import org.jcouchdb.document.BaseDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.svenson.JSONConfig;

/**
 * Deletes positioned documents after confirmation.
 * 
 * @author shelmberger
 *
 */
@Controller
public class DelObjectController
{
    private Database systemDatabase;
    
    private JSONConfig jsonConfig;
    
    @Autowired
    public void setJsonConfig(JSONConfig jsonConfig)
    {
        this.jsonConfig = jsonConfig;
    }
    
    @Autowired
    public void setSystemDatabase(Database systemDatabase)
    {
        this.systemDatabase = systemDatabase;
    }
    
    @RequestMapping("/del")
    public ModelAndView showNewObjectForm(
        @RequestParam("id") String id)
    {
        ModelAndView modelAndView = new ModelAndView("delObject");
        
        PositionedDocument doc = systemDatabase.getDocument(PositionedDocument.class, id, null, jsonConfig.getJsonParser());
        modelAndView.addObject("doc", doc);
        
        return modelAndView;
    }
   
    /**
     * Really deletes an object
     * 
     * @param ajax      if not <code>null</code>, render JSON response, else redirect to "/app/home" after deletion
     * @param ok        if not <code>null</code> signals that the user clicked ok
     * @param id        _id of the document to delete
     * @param rev       _rev of the document to delete
     * @return
     */
    @RequestMapping("/del/ok")
    public ModelAndView createNew(
        @RequestParam(value = "ajax", required = false) String ajax,
        @RequestParam(value = "ok", required = false) String ok,
        @RequestParam("id") String id,
        @RequestParam("rev") String rev)
    {
        if (ok != null)
        {
            BaseDocument document = new BaseDocument();
            document.setId(id);
            document.setRevision(rev);
            systemDatabase.delete(document);
        }
        
        if (ajax != null)
        {            
            Map m = new HashMap();
            m.put("ok", true);
            m.put("deleted", id);
            return JSONView.modelAndView(m);
        }
        else
        {
            return new ModelAndView("redirect:/app/home");
        }
    }
}
