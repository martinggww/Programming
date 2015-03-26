package org.xbrlapi.data.bdbxml.tests;

import java.net.URI;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.data.bdbxml.StoreImpl;
import org.xbrlapi.loader.Loader;

/**
 * Added to enable testing of XBRL instance loading problems with
 * XML Schema validation identified by Matthew DeAngelis.
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class InstanceSchemaRefElementUsageForSchemaValidationTestCase extends BaseTestCase {

    private URI uri;
	
	@BeforeMethod
    protected void setUp() throws Exception {
	    super.setUp();
		uri = URI.create("http://www.sec.gov/Archives/edgar/data/916540/000091654011000018/dar-20110615.xml");
	}

	@AfterMethod
    protected void tearDown() throws Exception {
	    super.tearDown();
	}	
	
    /**
     * Test persisting of active relationships for each document in turn.
     */
    @Test(enabled=false)
    public void testParsingOfSECInstance() { 
        StoreImpl store = null;
        try {
            store = createStore("testParsingOfSECInstance");
            Loader loader = createLoader(store);
            loader.discover(uri);
        } catch (Exception e) {
            Assert.fail("Unexpected: " + e.getMessage());
        } finally {
            try {
                if (store!= null) store.delete();
            } catch (Exception x) {
                Assert.fail("The store could not be deleted.");
            }
        }
    }
    
}
