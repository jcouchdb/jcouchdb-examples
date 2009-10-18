package org.hood;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * Base class for CouchDB integration tests. This just
 * reuses some of our (conviently separated) spring context 
 * definitions for test cases. You can extend this class
 * for your own tests. 
 * 
 * @author shelmberger
 *
 */
public abstract class AbstractAppIntegrationTestCase
    extends AbstractDependencyInjectionSpringContextTests
{
    @Override
    protected String[] getConfigLocations()
    {
        return new String[] { 
            "file:WebContent/WEB-INF/config/couchdb-config.xml",
            "file:WebContent/WEB-INF/config/services-config.xml"
            };
    }
}
