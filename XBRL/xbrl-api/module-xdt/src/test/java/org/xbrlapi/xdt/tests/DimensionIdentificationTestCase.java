package org.xbrlapi.xdt.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.util.List;

import org.xbrlapi.xdt.ExplicitDimension;
import org.xbrlapi.xdt.TypedDimension;

/**
 * Tests the identification of XDT fragments.
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class DimensionIdentificationTestCase extends BaseTestCase {

    private final String TYPED_DIMENSION_STARTING_POINT = "test.data.xdt.typed.dimension.declaration";
    private final String EXPLICIT_DIMENSION_STARTING_POINT = "test.data.xdt.explicit.dimension.declaration";
    
	@BeforeMethod
    protected void setUp() throws Exception {
	    super.setUp();
	}

	@AfterMethod
    protected void tearDown() throws Exception {
		super.tearDown();
	}	
	


	@Test
    public void testExplicitDimensionIdentification() {

		try {
	        loader.discover(this.getURI(EXPLICIT_DIMENSION_STARTING_POINT));
			List<ExplicitDimension> fragments = store.<ExplicitDimension>getXMLResources("org.xbrlapi.xdt.ExplicitDimensionImpl");
			AssertJUnit.assertTrue(fragments.size() > 0);
			for (ExplicitDimension fragment: fragments) {
	            AssertJUnit.assertEquals("org.xbrlapi.xdt.ExplicitDimensionImpl",fragment.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
    @Test
    public void testTypedDimensionIdentification() {

        try {
            loader.discover(this.getURI(TYPED_DIMENSION_STARTING_POINT));
            List<TypedDimension> fragments = store.<TypedDimension>getXMLResources("org.xbrlapi.xdt.TypedDimensionImpl");
            AssertJUnit.assertTrue(fragments.size() > 0);
            for (TypedDimension fragment: fragments) {
                AssertJUnit.assertEquals("org.xbrlapi.xdt.TypedDimensionImpl",fragment.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }	
	
	


}
