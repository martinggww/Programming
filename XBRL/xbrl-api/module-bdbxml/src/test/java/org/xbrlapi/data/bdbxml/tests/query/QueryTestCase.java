package org.xbrlapi.data.bdbxml.tests.query;


import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.Fragment;
import org.xbrlapi.data.Store;
import org.xbrlapi.data.bdbxml.StoreImpl;

/**
 * @author Geoff Shuetrim (geoff@galexy.net)
 */
public class QueryTestCase {
    
    protected static Logger logger = Logger.getLogger(QueryTestCase.class);  
    
    private final String CONTAINER = "xbrlapiContainer";
    private final String LOCATION = "/home/geoff/Data/bdbxml";

    private Store store = null;
    
    private int maxDocuments = 10;

    @BeforeMethod
    protected void setUp() throws Exception {
        store = new StoreImpl(LOCATION, CONTAINER);
    }

    @AfterMethod
    protected void tearDown() throws Exception {
        store.close();
    }

    @Test
    public final void testStubsRetrieval() throws Exception {

        String query = "#roots#[@type='org.xbrlapi.impl.StubImpl']";
        List<Fragment> fragments = store.<Fragment>queryForXMLResources(query);
        for (Fragment stub: fragments) {
            List<Fragment> referrers = store.<Fragment>queryForXMLResources("#roots#[@targetDocumentURI='"+stub.getURI()+"']");
            TreeMap<URI,String> map = new TreeMap<URI,String>();
            for (Fragment referrer: referrers) {
                if (! map.containsKey(referrer.getURI())) {
                    map.put(referrer.getURI(),"");
                }
            }
            logger.info("---------------------------------------------");
            logger.info(stub.getMetadataRootElement().getAttribute("reason") + ": " + stub.getURI());
            logger.info("This document is referred to by:");
            for (URI uri: map.keySet()) {
                List<Fragment> sources = store.<Fragment>queryForXMLResources("#roots#[@uri='"+ uri +"' and @targetDocumentURI='"+stub.getURI()+"']");
                logger.info(uri + " contains " + sources.size() + " references.");
            }
        }
    }    
    
    @Test
    public final void testWildcardQueryWithSingleResult() {
        try {
            iterateURIs("#roots#[@uri='","' and @parentIndex='']");
        } catch (Exception e) {
            e.printStackTrace();    
            Assert.fail(e.getMessage());
        }  
    }

    @Test
    public final void testSpecificNameWithSingleResult() {
        try {
            iterateURIs("/xbrlapi:fragment[@uri='","' and @parentIndex='']");
        } catch (Exception e) {
            e.printStackTrace();    
            Assert.fail(e.getMessage());
        }  
    }

    private void iterateURIs(String prefix, String suffix) throws Exception {
        Set<URI> uris = store.getDocumentURIs();
        int count = 1;
        long cumulativeDuration = 0;
        URI_LOOP: for (URI uri : uris) {
            count++;
            if (count > maxDocuments) {
                break URI_LOOP;
            }
            long duration = runQuery(prefix + uri + suffix);
            logger.info(uri + " took " + duration);
            cumulativeDuration += duration;
        }
        logger.info("Average duration = " + (cumulativeDuration / count) + " milliseconds.");
        
    }      

    private long runQuery(String query) throws Exception {
        long start = System.currentTimeMillis();
        List<Fragment> fragments = store.<Fragment>queryForXMLResources(query);
        long result = (System.currentTimeMillis() - start);
        Assert.assertTrue(fragments.size() == 1);
        return result;
    }    
    

}

