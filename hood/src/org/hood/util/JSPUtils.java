package org.hood.util;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.svenson.JSONConfig;

/**
 * Contains static JSP helper methods.
 * 
 * @author shelmberger
 *
 */
public class JSPUtils
{
    private static Logger log = LoggerFactory.getLogger(JSPUtils.class);
    
    public static String toJSONHtmlEscaped(ServletRequest request, Object o)
    {
        log.debug("request = {}, object = {}", request, o);
        
        WebApplicationContext applicationContext = RequestContextUtils.getWebApplicationContext(request);
        JSONConfig jsonConfig = (JSONConfig)applicationContext.getBean("jsonConfig");
        return StringEscapeUtils.escapeHtml(jsonConfig.getJsonGenerator().forValue(o));
    }
}
