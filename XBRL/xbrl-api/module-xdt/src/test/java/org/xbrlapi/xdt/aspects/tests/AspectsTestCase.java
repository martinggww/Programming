package org.xbrlapi.xdt.aspects.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.util.List;
import java.util.Vector;

import org.xbrlapi.Concept;
import org.xbrlapi.Fact;
import org.xbrlapi.aspects.Aspect;
import org.xbrlapi.aspects.AspectModel;
import org.xbrlapi.aspects.AspectModelImpl;
import org.xbrlapi.aspects.ConceptAspect;
import org.xbrlapi.aspects.ConceptAspectValue;
import org.xbrlapi.aspects.ConceptDomain;
import org.xbrlapi.aspects.FactSet;
import org.xbrlapi.aspects.FactSetImpl;
import org.xbrlapi.aspects.Filter;
import org.xbrlapi.aspects.FilterImpl;
import org.xbrlapi.aspects.LocationAspect;
import org.xbrlapi.aspects.LocationAspectValue;
import org.xbrlapi.aspects.LocationDomain;
import org.xbrlapi.aspects.PeriodAspect;
import org.xbrlapi.aspects.PeriodDomain;
import org.xbrlapi.impl.ConceptImpl;
import org.xbrlapi.xdt.aspects.DimensionalAspectModel;
import org.xbrlapi.xdt.aspects.DimensionalAspectModelImpl;
import org.xbrlapi.xdt.tests.BaseTestCase;


/**
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class AspectsTestCase extends BaseTestCase {
    
	private final String FIRST_SMALL_INSTANCE = "test.data.small.instance";
    private final String SECOND_SMALL_INSTANCE = "test.data.small.instance.2";
    private final String TUPLE_INSTANCE = "test.data.local.xbrl.instance.tuples.with.units";
    private final String EXPLICIT_DIMENSIONS = "test.data.local.xdt.several.explicit.dimension.values";
    private final String EXPLICIT_DIMENSIONS_WITH_DEFAULTS = "test.data.local.xdt.several.explicit.dimension.values.with.defaults";
    private final String TYPED_DIMENSIONS = "test.data.local.xdt.typed.dimension.values";
    
	@BeforeMethod
    protected void setUp() throws Exception {
	    super.setUp();
	}

	@AfterMethod
    protected void tearDown() throws Exception {
	    super.tearDown();
	}	
	


	@Test
    public void testAspectModel() {
		try {

		    // Load and retrieve the facts
		    loader.discover(this.getURI(FIRST_SMALL_INSTANCE));       
	        loader.discover(this.getURI(SECOND_SMALL_INSTANCE));
            loader.discover(this.getURI(TUPLE_INSTANCE));       
            loader.discover(this.getURI(EXPLICIT_DIMENSIONS));
            loader.discover(this.getURI(EXPLICIT_DIMENSIONS_WITH_DEFAULTS));       
            loader.discover(this.getURI(TYPED_DIMENSIONS));     
            
			List<Fact> facts = store.getAllFacts();
			AssertJUnit.assertTrue(facts.size() > 0);
			
			// Set up the aspect model
            AspectModel model = new AspectModelImpl(store);
            ConceptDomain conceptDomain = new ConceptDomain(store);
            List<Concept> concepts = store.<Concept>getXMLResources(ConceptImpl.class);
            List<Concept> concreteConcepts = new Vector<Concept>();
            for (Concept concept: concepts) {
                if (! concept.isAbstract()) concreteConcepts.add(concept);
            }
            AssertJUnit.assertEquals(concreteConcepts.size(),conceptDomain.getSize());
            AssertJUnit.assertEquals(conceptDomain.getSize(), conceptDomain.getAllAspectValues().size());
            ConceptAspect conceptAspect = new ConceptAspect(conceptDomain);
            model.addAspect("row", conceptAspect);
            AssertJUnit.assertEquals(1,model.getAspects().size());
            AssertJUnit.assertTrue(model.hasAxis("row"));
            AssertJUnit.assertFalse(model.hasAxis("col"));
            AssertJUnit.assertEquals(1,model.getAspects("row").size());
            AssertJUnit.assertEquals(1,model.getAxes().size());

            // Add in the location aspect
            LocationAspect locationAspect = new LocationAspect(new LocationDomain(store));
            model.addAspect("row", locationAspect);
            AssertJUnit.assertEquals(2,model.getAspects("row").size());
            model.addAspect("col", locationAspect);
            AssertJUnit.assertEquals(1,model.getAspects("row").size());
            AssertJUnit.assertEquals(2,model.getAxes().size());
            AssertJUnit.assertTrue(model.hasAxis("col"));

            // Set up the filtration system
            Filter filter = new FilterImpl();
            Fact firstFact = facts.get(0);
            ConceptAspectValue conceptAspectValue = new ConceptAspectValue(firstFact.getNamespace(), firstFact.getLocalname());
            filter.addCriterion(conceptAspectValue);
            AssertJUnit.assertTrue(filter.filtersOn(ConceptAspect.ID));
            AssertJUnit.assertFalse(filter.filtersOn(LocationAspect.ID));
            LocationAspectValue locationAspectValue = new LocationAspectValue(firstFact.getIndex());
            filter.addCriterion(locationAspectValue);
            AssertJUnit.assertTrue(filter.filtersOn(LocationAspect.ID));
            filter.removeCriterion(LocationAspect.ID);
            AssertJUnit.assertFalse(filter.filtersOn(LocationAspect.ID));

            // Create a fact set
            model.addAspect(new PeriodAspect(new PeriodDomain(store)));
            FactSet factSet = new FactSetImpl(model);
            factSet.addFacts(facts);

            AssertJUnit.assertEquals(3, model.getAxes().size());
            AssertJUnit.assertTrue(model.hasAxis("orphan"));
            AssertJUnit.assertTrue(model.hasAspect(PeriodAspect.ID));

            for (String axis: model.getAxes()) {
                logger.info(axis);
                for (Aspect aspect: model.getAspects(axis)) {
                    logger.info(aspect.getId());
                }
            }
            
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
    @Test
    public void testDimensionalAspectModelConstruction() {

        try {
            loader.discover(this.getURI(this.EXPLICIT_DIMENSIONS));
            loader.discover(this.getURI(this.TYPED_DIMENSIONS));
            
            DimensionalAspectModel model = new DimensionalAspectModelImpl(store);
            model.initialise();
            FactSet factSet = new FactSetImpl(model);
            factSet.addFacts(store.getAllFacts());

            AssertJUnit.assertEquals(4, factSet.getSize());
            for (Aspect aspect: model.getAspects()) {
                logger.info(aspect.getId());
            }
            AssertJUnit.assertEquals(10,model.getAspects().size());
            List<Aspect> explicitAspects = model.getExplicitDimensionAspects();
            AssertJUnit.assertEquals(2, explicitAspects.size());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }
    
    @Test
    public void testDimensionalAspectModelWithDefaults() {

        try {
            loader.discover(this.getURI(this.EXPLICIT_DIMENSIONS_WITH_DEFAULTS));
            DimensionalAspectModel model = new DimensionalAspectModelImpl(store);
            model.initialise();
            FactSet factSet = new FactSetImpl(model);
            factSet.addFacts(store.getAllFacts());
            List<Aspect> aspects = model.getExplicitDimensionAspects();
            AssertJUnit.assertEquals(2, aspects.size());

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }	
	
	
}
