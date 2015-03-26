package org.xbrlapi.xdt.aspects.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.util.List;

import org.xbrlapi.DOMLoadingTestCase;
import org.xbrlapi.Fact;
import org.xbrlapi.aspects.AspectModel;
import org.xbrlapi.aspects.AspectValue;
import org.xbrlapi.aspects.FactSet;
import org.xbrlapi.aspects.FactSetImpl;
import org.xbrlapi.aspects.Labeller;
import org.xbrlapi.xdt.aspects.DimensionalAspectModelWithMemoryCachingLabellers;


/**
 * Tests XDT store caching aspect labelling
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class LabellerTestCase extends DOMLoadingTestCase {
    
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
    public void testAspectLabelsWithInMemoryCaching() {
		try {

			// Set up the aspect model
            AspectModel model = new DimensionalAspectModelWithMemoryCachingLabellers(store);
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

            logger.info(System.currentTimeMillis());
            for (Fact fact: factSet.getFacts()) {
                for (AspectValue value: factSet.getAspectValues(fact)) {
                    Labeller labeller = model.getLabeller(value.getAspectId());
                    logger.info(labeller.getAspectLabel(null,null,null) + " " + labeller.getAspectValueLabel(value,"en",null,null));
                }
            }
            logger.info(System.currentTimeMillis());
            for (Fact fact: factSet.getFacts()) {
                for (AspectValue value: factSet.getAspectValues(fact)) {
                    Labeller labeller = model.getLabeller(value.getAspectId());
                    logger.info(labeller.getAspectLabel(null,null,null) + " " + labeller.getAspectValueLabel(value,"en",null,null));
                }
            }
            logger.info(System.currentTimeMillis());
            
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	
	
	
	
}
