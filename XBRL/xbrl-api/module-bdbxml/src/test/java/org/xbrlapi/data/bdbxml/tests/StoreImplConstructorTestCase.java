package org.xbrlapi.data.bdbxml.tests;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.xbrlapi.data.bdbxml.StoreImpl;

/**
 * Test the eXist XBRLAPI Store implementation.
 * @author Geoffrey Shuetrim (geoff@galexy.net) 
*/
public class StoreImplConstructorTestCase extends BaseTestCase {

	@BeforeClass
    protected void setUp() throws Exception {
	    super.setUp();
	}

	@AfterClass
    protected void tearDown() throws Exception {
	    super.tearDown();
	}

	/**
	 * Test the a new store that uses existing collections for 
	 * data and metadata instead of creating them.
	 */
	@Test
    public void testStoreImplConnectsToAnExistingStore() {
        StoreImpl store = null, newStore = null;
        try {
            store = createStore("testStoreImplConnectsToAnExistingStore");
            newStore = createStore("testStoreImplConnectsToAnExistingStore");
            AssertJUnit.assertEquals(store,newStore);
            Assert.assertEquals(store.getSize(),newStore.getSize(),"Fragment counts do not match.");
        } catch (Exception e) {
            Assert.fail("Unexpected: " + e.getMessage());
        } finally {
            try {
                newStore.close();
                store.delete();
            } catch (Exception x) {
                Assert.fail("The store could not be deleted.");
            }
        }
	}

	/**
	 * Test store creation using a nonexistent parent collection to hold the store collection.
	 */
	@Test
    public void testStoreImplFailsOnNonexistentContainer() {
        StoreImpl store = null;
        try {
            new StoreImpl(location + "/doesNotExist","testStoreImplFailsOnNonexistentContainer");
            Assert.fail("The store should have failed to be created because no parent exists for the store collection.");
        } catch (Exception e) {
            ;
        } finally {
            try {
                if (store!= null) store.delete();
            } catch (Exception x) {
                Assert.fail("The store could not be deleted.");
            }
        }
	}



}
