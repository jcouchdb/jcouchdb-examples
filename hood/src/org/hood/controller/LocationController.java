package org.hood.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hood.HoodService;
import org.hood.LocationService;
import org.hood.domain.LatLon;
import org.hood.domain.PositionedDocument;
import org.hood.util.GzippedResponseUtil;
import org.jcouchdb.db.Database;
import org.jcouchdb.db.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.svenson.JSONConfig;

@Controller
public class LocationController
{
    private static Logger log = LoggerFactory.getLogger(LocationController.class);
    
    private final static double DEFAULT_LAT_OFFSET = 0.032;
    private final static double DEFAULT_LON_OFFSET = 0.013;
    
    
    private LocationService locationService;
    
    private JSONConfig jsonConfig;
    
    @Autowired
    public void setLocationService(LocationService locationService)
    {
        this.locationService = locationService;
    }
    
    @Autowired
    public void setJsonConfig(JSONConfig jsonConfig)
    {
        this.jsonConfig = jsonConfig;
    }
    
    
    @RequestMapping("/objects")
    public String listObjects(
        ModelMap model,
        @RequestParam("center_lat") double centerLat,
        @RequestParam("center_lon") double centerLon)
    {    
        double neLat = centerLat - DEFAULT_LAT_OFFSET;
        double neLon = centerLon - DEFAULT_LON_OFFSET;
        double swLat = centerLat + DEFAULT_LAT_OFFSET;
        double swLon = centerLon + DEFAULT_LON_OFFSET;

        LatLon ne = new LatLon(neLat, neLon);
        LatLon sw = new LatLon(swLat, swLon);
        
        List<PositionedDocument> docs = locationService.getDocumentsWithinBounds(ne, sw);
        
        log.debug("docs = {}", docs);
        
        model.addAttribute("docs", docs);
        return "objects";
    }
    
    @RequestMapping("/objects/json")
    public String listObjectsJSON(ModelMap model,
        HttpServletRequest request, HttpServletResponse response,
        @RequestParam("ne_lat") double neLat,
        @RequestParam("ne_lon") double neLon,
        @RequestParam("sw_lat") double swLat,
        @RequestParam("sw_lon") double swLon,
        @RequestParam(value = "over", required = false) Double overSubscribe
        ) throws UnsupportedEncodingException, IOException
    {

        LatLon ne = new LatLon(neLat, neLon);
        LatLon sw = new LatLon(swLat, swLon);

        LatLon[] corners = scale(ne,sw,overSubscribe);
        ne = corners[0];
        sw = corners[1];
        
        String json = locationService.getDocumentsWithinBoundsJSON(ne, sw);
        
        response.setContentType("application/json");
        GzippedResponseUtil.sendResponse(request, response, json.getBytes("UTF-8"), false);
        return null;
    }

    static LatLon[] scale(LatLon ne, LatLon sw, Double overLoad)
    {
        log.debug("input: ne = {}, sw = {}", ne, sw);
        
        if (overLoad != null && overLoad != 1)
        {
            LatLon diff = ne.substract(sw).scale(0.5);
            
            LatLon center = sw.add(diff);
            
            diff = diff.scale(overLoad);
            
            ne = center.add(diff);
            sw = center.substract(diff);
        }
        log.debug("output: ne = {}, sw = {}", ne, sw);
        return new LatLon[] { ne, sw };
    }    
}
