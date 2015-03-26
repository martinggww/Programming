package org.xbrlapi.data.bdbxml.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.data.bdbxml.StoreImpl;
import org.xbrlapi.loader.Loader;

/**
 * @author Geoffrey Shuetrim (geoff@galexy.net) 
 */
public class StoreSerializationTestCase extends BaseTestCase {
    
    private final String START = "test.data.small.schema";    
    
	@BeforeMethod
    protected void setUp() throws Exception {
	    super.setUp();
	}

	@AfterMethod
    protected void tearDown() throws Exception {
	    super.tearDown();
	}

    @Test
    public final void testStoreSerialization() {
        StoreImpl store = null, copy = null;
        try {
            store = createStore("testStoreSerialization");
            Loader loader = createLoader(store);
            loader.discover(this.getURI(START));
            copy = (StoreImpl) getDeepCopy(store);
            this.assessCustomEquality(store,copy);
        } catch (Exception e) {
            Assert.fail("Unexpected: " + e.getMessage());
        } finally {
            try {
                if (copy != null) copy.close();
                if (store!= null) store.delete();
            } catch (Exception x) {
                Assert.fail("The store could not be deleted.");
            }
        }
    }	

}
