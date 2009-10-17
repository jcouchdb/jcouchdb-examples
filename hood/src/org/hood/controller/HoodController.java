package org.hood.controller;

import org.hood.HoodService;
import org.hood.domain.Hood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Allows viewing the hoods. 
 * @author shelmberger
 *
 */
@Controller
public class HoodController
{
    private HoodService hoodService;

    @Autowired
    public void setHoodService(HoodService hoodService)
    {
        this.hoodService = hoodService;
    }
    
    /**
     * Shows the hood view with default hood.
     * 
     * @param model     model map
     * @return
     */
    @RequestMapping("/home")
    public String showHome(ModelMap model)
    {
        return showHood(model, hoodService.getDefault());
    }

    /**
     * Shows the hood with the given id
     * @param model     model map
     * @param id        id
     * @return
     */
    @RequestMapping("/hood")
    public String showHood(ModelMap model, @RequestParam("id") String id)
    {
        Hood hood = hoodService.getHood(id);
        return showHood(model, hood);
    }

    /**
     * Populate the model with the given hood and return the hood view.
     * 
     * @param model     model map
     * @param hood      hood object
     * @return
     */
    public String showHood(ModelMap model, Hood hood)
    {
        
        model.addAttribute("hoods", hoodService.listHoods());
        model.addAttribute("hood", hood);
        return "hood";
    }
}
