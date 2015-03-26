package org.xbrlapi.data.bdbxml.tests;

import java.net.URI;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.data.bdbxml.StoreImpl;
import org.xbrlapi.data.resource.InStoreMatcherImpl;
import org.xbrlapi.data.resource.Matcher;
import org.xbrlapi.loader.Loader;

/**
 * Tests the Store based matcher using the BDB XML data store.
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class StoreMatcherTestCase extends BaseTestCase {
	private final String ORIGINAL = "real.data.sec.usgaap.1";
	private final String DUPLICATE = "real.data.sec.usgaap.2";
	
	@BeforeMethod
    protected void setUp() throws Exception {
	    super.setUp();
	}

	@AfterMethod
    protected void tearDown() throws Exception {
	    super.tearDown();
	}	
	

	
	@Test
    public void testURIStashingWithMatcher() {
        StoreImpl store = null;
        try {
            store = createStore("testURIStashingWithMatcher");
            Loader loader = createLoader(store);
            
            Matcher matcher = new InStoreMatcherImpl(store,loader.getCache());
            store.setMatcher(matcher);
            
            loader.discover(this.getURI(ORIGINAL));
            loader.discover(this.getURI(DUPLICATE));
            
            URI uri1 = getURI(ORIGINAL);
            URI uri2 = getURI(DUPLICATE);
            
            Assert.assertEquals(store.getMatcher().getMatch(uri2),uri1);
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
