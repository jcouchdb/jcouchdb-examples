package org.hood.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.hood.domain.LatLon;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LocationControllerTestCase
{
    private static Logger log = LoggerFactory.getLogger(LocationControllerTestCase.class);
    
    @Test 
    public void test()
    {
        LatLon[] res = LocationController.scale(new LatLon(0,0), new LatLon(10,10), 2.0);
        assertThat(res[0].getLatitude(), is(-5.0));
        assertThat(res[1].getLatitude(), is(15.0));

        res = LocationController.scale(new LatLon(10,10), new LatLon(0,0) , 3.0);
        assertThat(res[1].getLatitude(), is(-10.0));
        assertThat(res[0].getLatitude(), is(20.0));
    }
    

}
