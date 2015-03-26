package org.xbrlapi.xdt.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.util.List;

import org.xbrlapi.xdt.Dimension;
import org.xbrlapi.xdt.Hypercube;

/**
 * Tests the identification of XDT fragments.
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class HypercubeTestCase extends BaseTestCase {

    private final String STARTING_POINT = "test.data.xdt.hypercube.declaration";
    
	@BeforeMethod
    protected void setUp() throws Exception {
        super.setUp();
	}

	@AfterMethod
    protected void tearDown() throws Exception {
        super.tearDown();
	}	
	


	@Test
    public void testHypercubeIdentification() {

		try {
	        loader.discover(this.getURI(STARTING_POINT));
			List<Hypercube> fragments = store.<Hypercube>getXMLResources("org.xbrlapi.xdt.HypercubeImpl");
			AssertJUnit.assertTrue(fragments.size() > 0);
			for (Hypercube fragment: fragments) {
	            AssertJUnit.assertEquals("org.xbrlapi.xdt.HypercubeImpl",fragment.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
    @Test
    public void testGettingDimensionsForAnEmptyHypercube() {

        try {
            loader.discover(this.getURI(STARTING_POINT));
            List<Hypercube> fragments = store.<Hypercube>getXMLResources("org.xbrlapi.xdt.HypercubeImpl");
            AssertJUnit.assertTrue(fragments.size() > 0);
            for (Hypercube fragment: fragments) {
                List<Dimension> dimensions = fragment.getDimensions();
                AssertJUnit.assertTrue(dimensions.size() == 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }	


}
