package org.hood;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public abstract class AbstractAppIntegrationTestCase
    extends AbstractDependencyInjectionSpringContextTests
{
    @Override
    protected String[] getConfigLocations()
    {
        return new String[] { 
            "file:WebContent/WEB-INF/config/services-config.xml",
            "file:WebContent/WEB-INF/config/couchdb-config.xml", 
            "file:WebContent/WEB-INF/config/couchdb-services.xml" 
            };
    }
}
