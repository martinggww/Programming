package org.xbrlapi.xdt.validation.tests;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.Concept;
import org.xbrlapi.xdt.Hypercube;
import org.xbrlapi.xdt.HypercubeImpl;
import org.xbrlapi.xdt.tests.BaseTestCase;

import com.google.common.collect.ListMultimap;

/**
 * Tests the identification of XDT fragments.
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class HypercubeInheritanceTestCase extends BaseTestCase {

    private final String INHERITANCE = "test.data.local.xdt.hypercube.inheritance";

    private final String NONINHERITANCE = "test.data.local.xdt.hypercube.non.inheritance";

    // The namespace of the concepts in the schema
    private final String InheritanceNS = "http://xbrlapi.org/test/xdt/003";
    private final String NonInheritanceNS = "http://xbrlapi.org/test/xdt/004";
    private final String concept0 = "PrimaryParent";
    private final String concept1 = "FirstChildren";
    private final String concept2 = "SecondChildren";
    private final String concept3 = "ThirdChildren";
    
	@BeforeMethod
    protected void setUp() throws Exception {
	    super.setUp();
	}

	@AfterMethod
    protected void tearDown() throws Exception {
	    super.tearDown();
	}	

	@Test
    public void testHypercubeInheritance() {

		try {
	        loader.discover(this.getURI(INHERITANCE));

            // One of its own
	        Concept concept0 = store.getConcept(this.InheritanceNS,this.concept0);
            ListMultimap<String,Hypercube> hypercubes = HypercubeImpl.getInheritedHypercubes(concept0);
            AssertJUnit.assertEquals(1,hypercubes.size());
            AssertJUnit.assertEquals(1,hypercubes.keySet().size());
            
            // Inherits from concept0 and one of its own
            Concept concept1 = store.getConcept(this.InheritanceNS,this.concept1);
            hypercubes = HypercubeImpl.getInheritedHypercubes(concept1);
            AssertJUnit.assertEquals(2,hypercubes.size());
            AssertJUnit.assertEquals(1,hypercubes.keySet().size());
            for (String uri: hypercubes.keySet()) {
                AssertJUnit.assertEquals(2,hypercubes.get(uri).size());
            }
            
            // Inherits from concept1 and one of its own
            Concept concept2 = store.getConcept(this.InheritanceNS,this.concept2);
            hypercubes = HypercubeImpl.getInheritedHypercubes(concept2);
            AssertJUnit.assertEquals(3,hypercubes.size());
            AssertJUnit.assertEquals(2,hypercubes.keySet().size());

            // None of its own but inherits from concept2
            Concept concept3 = store.getConcept(this.InheritanceNS,this.concept3);
            hypercubes = HypercubeImpl.getInheritedHypercubes(concept3);
            AssertJUnit.assertEquals(3,hypercubes.size());
            AssertJUnit.assertEquals(2,hypercubes.keySet().size());
		
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}


    @Test
    public void testHypercubeNonInheritance() {

        try {
            loader.discover(this.getURI(NONINHERITANCE));

            // One of its own
            Concept concept0 = store.getConcept(this.NonInheritanceNS,this.concept0);
            ListMultimap<String,Hypercube> hypercubes = HypercubeImpl.getInheritedHypercubes(concept0);
            AssertJUnit.assertEquals(1,hypercubes.size());
            AssertJUnit.assertEquals(1,hypercubes.keySet().size());
            
            // Inherits from concept0 and one of its own
            Concept concept1 = store.getConcept(this.NonInheritanceNS,this.concept1);
            hypercubes = HypercubeImpl.getInheritedHypercubes(concept1);
            AssertJUnit.assertEquals(2,hypercubes.size());
            AssertJUnit.assertEquals(1,hypercubes.keySet().size());
            for (String uri: hypercubes.keySet()) {
                AssertJUnit.assertEquals(2,hypercubes.get(uri).size());
            }
            
            
            // Inherits from concept1 and one of its own
            Concept concept2 = store.getConcept(this.NonInheritanceNS,this.concept2);
            hypercubes = HypercubeImpl.getInheritedHypercubes(concept2);
            AssertJUnit.assertEquals(3,hypercubes.size());
            AssertJUnit.assertEquals(2,hypercubes.keySet().size());

            // None of its own but inherits from concept2
            Concept concept3 = store.getConcept(this.NonInheritanceNS,this.concept3);
            hypercubes = HypercubeImpl.getInheritedHypercubes(concept3);
            AssertJUnit.assertEquals(1,hypercubes.size());
            AssertJUnit.assertEquals(1,hypercubes.keySet().size());
        
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
	
	
}
