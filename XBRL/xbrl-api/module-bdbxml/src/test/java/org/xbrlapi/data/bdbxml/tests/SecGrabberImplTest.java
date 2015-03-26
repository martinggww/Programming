package org.xbrlapi.data.bdbxml.tests;

import java.net.URI;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.data.bdbxml.StoreImpl;
import org.xbrlapi.grabber.Grabber;
import org.xbrlapi.grabber.SecGrabberImpl;
import org.xbrlapi.loader.Loader;

public class SecGrabberImplTest extends BaseTestCase {
    


    private List<URI> resources = null;
    @BeforeMethod
    protected void setUp() throws Exception {
        super.setUp();
    }

    @AfterMethod
    protected void tearDown() throws Exception {
        super.tearDown();
    }   
    
    @Test
    public void testSecGrabberResourceRetrieval() {
        StoreImpl store = null;
        try {
            
            String secFeed = configuration.getProperty("real.data.sec");
            URI feedURI = new URI(secFeed);
            Grabber grabber = new SecGrabberImpl(feedURI);
            resources = grabber.getResources();
            Assert.assertTrue(resources.size() > 50);           
            
            store = createStore("testSecGrabberResourceRetrieval");
            Loader loader = createLoader(store);
            
            int cnt = 3;
            List<URI> r1 = resources.subList(0,cnt);
            for (URI uri: r1) {
                loader.stashURI(uri);
            }
            loader.discoverNext();
            loader.discoverNext();
            loader.discoverNext();
            
        } catch (Exception e) {
            e.printStackTrace();
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
