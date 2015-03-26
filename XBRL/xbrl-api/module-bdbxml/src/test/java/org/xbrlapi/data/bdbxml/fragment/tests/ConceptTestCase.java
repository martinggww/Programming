package org.xbrlapi.data.bdbxml.fragment.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.Concept;
import org.xbrlapi.data.bdbxml.StoreImpl;
import org.xbrlapi.data.bdbxml.tests.BaseTestCase;
import org.xbrlapi.impl.ConceptImpl;
import org.xbrlapi.loader.Loader;

/**
 * Tests the implementation of the org.xbrlapi.Concept getFactCount method.
 * Uses the Berkeley XML-based data store to confirm namespace declaration
 * handling testing.
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */

public class ConceptTestCase extends BaseTestCase {
	private final String FOOTNOTELINKS = "test.data.footnote.links";
	
	@BeforeMethod
    protected void setUp() throws Exception {
	    super.setUp();
	}

	@AfterMethod
    protected void tearDown() throws Exception {
	    super.tearDown();
	}	

    @Test
    public void testGetFactCount() {
        StoreImpl store = null;
        try {
            store = createStore("testGetFactCount");
            Loader loader = createLoader(store);
            loader.discover(this.getURI(FOOTNOTELINKS));        
            List<Concept> concepts = store.<Concept>getXMLResources(ConceptImpl.class);
            Assert.assertTrue(concepts.size() > 0);
            boolean foundFacts = false;
            for (Concept concept: concepts) {
                if (concept.getFactCount() > 0) {
                    foundFacts = true;
                }
                Assert.assertEquals(concept.getFactCount(),concept.getFacts().size());
            }
            Assert.assertTrue(foundFacts);
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
