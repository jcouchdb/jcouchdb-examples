package org.hood.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hood.LocationService;
import org.hood.domain.LatLon;
import org.hood.util.JSONView;
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

/**
 * Drives the new Object form with AJAX or not
 * @author shelmberger
 *
 */
@Controller
public class NewObjectController
{
    private static Logger log = LoggerFactory.getLogger(NewObjectController.class);

    private LocationService locationService;
        
    @Autowired
    public void setLocationService(LocationService locationService)
    {
        this.locationService = locationService;
    }
    
    /**
     * Shows the form for the new object. AJAX handling is done by the page.tag
     * @return
     */
    @RequestMapping("/new")
    public ModelAndView showNewObjectForm()
    {
        return new ModelAndView("newObject");
    }
    
    /**
     * Provides the form with an object to bind to
     * @return
     */
    @ModelAttribute("newObject")
    public NewObjectCommand initCommand()
    {
        return new NewObjectCommand();
    }

    private NewObjectCommandValidator newObjectCommandValidator = new NewObjectCommandValidator();
    
    /**
     * Handle form post back.
     * 
     * @param request               request
     * @param ajax                  if not <code>null</code>, respond with JSON, otherwise redirect to <code>/app/home</code>
     * @param newObjectCommand      object the request params were bound to
     * @param bindingResult         binding result for that object
     * @return
     */
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

        log.debug("Request value is {}, creating {}", request.getParameter("name"), newObjectCommand);
        
        locationService.createLocation(newObjectCommand.getLocationType(), newObjectCommand.getName(), newObjectCommand.getDescription(), new LatLon(newObjectCommand.getLat(), newObjectCommand.getLon()));
        
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
