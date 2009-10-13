package org.hood.controller;

import java.util.List;

import org.hood.HoodService;
import org.hood.LocationService;
import org.hood.domain.Hood;
import org.jcouchdb.db.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HoodController
{
    private HoodService hoodService;

    @Autowired
    public void setHoodService(HoodService hoodService)
    {
        this.hoodService = hoodService;
    }
    
    
    @RequestMapping("/home")
    public String showHome(ModelMap model)
    {
        return showHood(model, hoodService.getDefault());
    }

    @RequestMapping("/hood")
    public String showHood(ModelMap model, @RequestParam("id") String id)
    {
        Hood hood = hoodService.getHood(id);
        return showHood(model, hood);
    }

    
    public String showHood(ModelMap model, Hood hood)
    {
        
        model.addAttribute("hoods", hoodService.listHoods());
        model.addAttribute("hood", hood);
        return "hood";
    }
    
}
