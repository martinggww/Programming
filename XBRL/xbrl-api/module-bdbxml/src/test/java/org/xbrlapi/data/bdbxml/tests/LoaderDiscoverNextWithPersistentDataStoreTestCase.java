package org.xbrlapi.data.bdbxml.tests;

import java.net.URI;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.data.bdbxml.StoreImpl;
import org.xbrlapi.loader.Loader;

/**
 * Test the loader discoverNext implementation using the 
 * BDB XML database as the persistent data store.
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class LoaderDiscoverNextWithPersistentDataStoreTestCase extends BaseTestCase {
	
	private final String STARTING_POINT = "test.data.small.schema";
	
	@BeforeMethod
    protected void setUp() throws Exception {
	    super.setUp();
	}

	@AfterMethod
    protected void tearDownMethod() throws Exception {
	    super.tearDown();
	}

	/**
	 * Test discovery one URI at a time
	 */
	@Test
    public void testDiscoverNext() {
        StoreImpl store = null;
        try {
            store = createStore("testDiscoverNext");
            Loader loader = this.createLoader(store);
            loader.stashURI(getURI(STARTING_POINT));
            loader.discoverNext();
            while (! loader.getDocumentsStillToAnalyse().isEmpty()) {
                for (URI document: loader.getDocumentsStillToAnalyse()) {
                    logger.info("still to process " + document);
                }
                loader.discoverNext();
            }
        } catch (Exception e) {
            Assert.fail("Unexpected: " + e.getMessage());
        } finally {
              try {
                  store.delete();
              } catch (Exception x) {
                  Assert.fail("The store could not be deleted.");
              }
        }
	}

}
