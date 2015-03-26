package org.xbrlapi.data.bdbxml.aspects.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.Fact;
import org.xbrlapi.aspects.AspectModel;
import org.xbrlapi.aspects.AspectValue;
import org.xbrlapi.aspects.FactSet;
import org.xbrlapi.aspects.FactSetImpl;
import org.xbrlapi.aspects.Labeller;
import org.xbrlapi.aspects.StandardAspectModelWithStoreCachingLabellers;
import org.xbrlapi.data.bdbxml.StoreImpl;
import org.xbrlapi.data.bdbxml.tests.BaseTestCase;
import org.xbrlapi.impl.AspectValueLabelImpl;
import org.xbrlapi.loader.Loader;

/**
 * Tests XDT store caching aspect labelling
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class LabellerTestCase extends BaseTestCase {
    
    private final String FIRST_SMALL_INSTANCE = "test.data.small.instance";
    private final String SECOND_SMALL_INSTANCE = "test.data.small.instance.2";
    private final String TUPLE_INSTANCE = "test.data.local.xbrl.instance.tuples.with.units";
    private final String EXPLICIT_DIMENSIONS = "test.data.local.xdt.several.explicit.dimension.values";
    private final String EXPLICIT_DIMENSIONS_WITH_DEFAULTS = "test.data.local.xdt.several.explicit.dimension.values.with.defaults";
    private final String TYPED_DIMENSIONS = "test.data.local.xdt.typed.dimension.values";

    private final String MEASURES = "real.data.xbrl.api.measures";
	
	@BeforeMethod
    protected void setUp() throws Exception {
	    super.setUp();
	}

	@AfterMethod
    protected void tearDown() throws Exception {
	    super.tearDown();
	}	

	@Test
    public void testAspectLabelsWithInStoreCaching() {
	    
        StoreImpl store = null;
        try {
            store = createStore("testAspectLabelsWithInStoreCaching");
            Loader loader = createLoader(store);

            // Set up the aspect model
            AspectModel model = new StandardAspectModelWithStoreCachingLabellers(store);
            model.initialise();
            
            // Load and retrieve the facts
            loader.discover(this.getURI(MEASURES));       
            loader.discover(this.getURI(FIRST_SMALL_INSTANCE));       
            loader.discover(this.getURI(SECOND_SMALL_INSTANCE));
            loader.discover(this.getURI(TUPLE_INSTANCE));       
            loader.discover(this.getURI(EXPLICIT_DIMENSIONS));       
            loader.discover(this.getURI(EXPLICIT_DIMENSIONS_WITH_DEFAULTS));       

            List<Fact> facts = store.getAllFacts();
            AssertJUnit.assertTrue(facts.size() > 0);
            
            // Create the fact set
            FactSet factSet = new FactSetImpl(model);
            factSet.addFacts(facts);

            long fCount = store.getSize();
            
            logger.info(System.currentTimeMillis());
            for (Fact fact: factSet.getFacts()) {
                for (AspectValue value: factSet.getAspectValues(fact)) {
                    Labeller labeller = model.getLabeller(value.getAspectId());
                }
            }
            
            Assert.assertEquals(fCount + store.getNumberOfXMLResources(AspectValueLabelImpl.class),store.getSize());
            
            logger.info(System.currentTimeMillis());
            for (Fact fact: factSet.getFacts()) {
                for (AspectValue value: factSet.getAspectValues(fact)) {
                    Labeller labeller = model.getLabeller(value.getAspectId());
                }
            }
            logger.info(System.currentTimeMillis());
        
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
