package org.hood.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hood.util.JSONView;
import org.jcouchdb.db.Database;
import org.jcouchdb.document.BaseDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.svenson.JSONConfig;

@Controller
public class NewObjectController
{
    private static Logger log = LoggerFactory.getLogger(NewObjectController.class);
    
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
    
    @RequestMapping("/new")
    public ModelAndView showNewObjectForm()
    {
        return new ModelAndView("newObject");
    }
    
    @ModelAttribute("newObject")
    public NewObjectCommand initCommand()
    {
        return new NewObjectCommand();
    }

    private NewObjectCommandValidator newObjectCommandValidator = new NewObjectCommandValidator();
    
    @RequestMapping(value = "/new/create")
    public ModelAndView createNew(
        HttpServletRequest request,
        @RequestParam(value = "ajax", required = false) String ajax,
        @ModelAttribute("newObject") NewObjectCommand newObjectCommand, BindingResult bindingResult)
    {
        
        
        ValidationUtils.invokeValidator(newObjectCommandValidator, newObjectCommand, bindingResult);
        if (bindingResult.hasErrors())
        {
            if (ajax != null)
            {
                Map m = new HashMap();
                m.put("ok", false);
                m.put("errors", bindingResult.getAllErrors());
                return JSONView.modelAndView(m);
            }
            else
            {
                return showNewObjectForm();
            }
        }

        log.debug("Request encoding is {}, creating {}", request.getCharacterEncoding(), newObjectCommand);
        
        BaseDocument doc = new BaseDocument();
        doc.setProperty("docType", newObjectCommand.getType().getDomainType().getSimpleName());
        doc.setProperty("name", newObjectCommand.getName());
        doc.setProperty("description", newObjectCommand.getDescription());
        doc.setProperty("loc", Arrays.asList(newObjectCommand.getLat(), newObjectCommand.getLon()));


        systemDatabase.createDocument(doc);
        
        if (ajax != null)
        {            
            Map m = new HashMap();
            m.put("ok", true);
            return JSONView.modelAndView(m);
        }
        else
        {
            return new ModelAndView("redirect:/app/home");
        }
    }
}
