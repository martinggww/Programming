package org.xbrlapi.data.bdbxml.examples.tests;

import java.net.URI;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.data.bdbxml.StoreImpl;
import org.xbrlapi.data.bdbxml.examples.load.Load;
import org.xbrlapi.utilities.XBRLException;

/**
 * Tests @link org.xbrlapi.bdbxml.examples.load.Load
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class LoadTestCase extends BaseTestCase {

    private final String DOCUMENT_URI = "test.data.small.schema";
    
    // Create the logger
    protected static Logger logger = Logger.getLogger(LoadTestCase.class);  

	@BeforeMethod
    protected void setUp() throws Exception {
	    super.setUp();
	}

	@AfterMethod
    protected void tearDown() throws Exception {
	    super.tearDown();
	}

    @Test
    public void testSuccessfulLoadOfSingleDocument() {
        StoreImpl store = null;
        try {
            store = createStore("testSuccessfulLoadOfSingleDocument");

            URI uri = getURI(DOCUMENT_URI);

            logger.info("The URI to load is " + uri);
            
            try {
                Assert.assertFalse(store.hasDocument(uri));
            } catch (XBRLException e) {
                Assert.fail(e.getMessage());
            }
            
            String[] args = {
                    "-database", location, 
                    "-container","testSuccessfulLoadOfSingleDocument", 
                    "-cache", cachePath, 
                    uri.toString() 
                    };
            
            logger.info("Starting the load process.");
            Load.main(args);

            try {
                logger.info("Checking if the store has " + uri);
                Assert.assertTrue(store.hasDocument(uri));
            } catch (XBRLException e) {
                Assert.fail(e.getMessage());
            }            

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
    
    @Test
    public void testLoadOfSingleDocumentFailingBecauseDatabaseParameterIsMissing() {
        StoreImpl store = null;
        try {
            store = createStore("testLoadOfSingleDocumentFailingBecauseDatabaseParameterIsMissing");

            URI uri = getURI(DOCUMENT_URI);

            logger.info("The URI to load is " + uri);
            
            try {
                Assert.assertFalse(store.hasDocument(uri));
            } catch (XBRLException e) {
                Assert.fail(e.getMessage());
            }
            
            String[] args = {
                    "-container","testLoadOfSingleDocumentFailingBecauseDatabaseParameterIsMissing", 
                    "-cache", cachePath, 
                    uri.toString() 
                    };
            
            logger.info("Starting the load process.");
            Load.main(args);

            try {
                logger.info("Checking if the store has " + uri);
                Assert.assertFalse(store.hasDocument(uri));
            } catch (XBRLException e) {
                Assert.fail(e.getMessage());
            }            

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