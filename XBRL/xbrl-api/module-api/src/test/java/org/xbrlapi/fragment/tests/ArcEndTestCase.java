package org.xbrlapi.fragment.tests;

/**
 * Tests the implementation of the org.xbrlapi.ArcEnd interface.
 * Uses the DOM-based data store to ensure rapid testing.
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.net.URI;
import java.util.List;
import java.util.regex.Pattern;

import org.xbrlapi.DOMLoadingTestCase;
import org.xbrlapi.Locator;

public class ArcEndTestCase extends DOMLoadingTestCase {

	private final String STARTING_POINT = "test.data.multi.concept.schema";

	URI uri = null;
	String document = 
		"<linkbase xmlns=\"http://www.xbrl.org/2003/linkbase\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
	    "<presentationLink xlink:type=\"extended\" xlink:role=\"http://www.xbrl.org/2003/role/link\">\n" + 
		"<loc xlink:type=\"locator\" xlink:href=\"397-ABC.xsd#A\" xlink:label=\"parent\" />\n" + 
		"<loc xlink:type=\"locator\" xlink:href=\"397-ABC.xsd#B\" xlink:label=\"child\" />\n" + 
		"<loc id=\"unique\" xlink:role=\"http://www.xbrlapi.org/xlink/role\" xlink:type=\"locator\" xlink:href=\"397-ABC.xsd#C\" xlink:label=\"child\" />\n" + 
		"<presentationArc xlink:type=\"arc\" xlink:arcrole=\"http://www.xbrl.org/2003/arcrole/parent-child\" xlink:from=\"parent\" xlink:to=\"child\" />\n" +
		"</presentationLink>\n" +
		"</linkbase>\n";
	
	@BeforeMethod
    protected void setUp() throws Exception {
        super.setUp();
		uri = getURI(STARTING_POINT);
		String myURI = Pattern.compile("397-ABC.xsd").matcher(uri.toString()).replaceAll("string.xml");
		loader.discover(new URI(myURI), document);
	}

	@AfterMethod
    protected void tearDown() throws Exception {
       super.tearDown();
	}	
	
	/**
	 * Test getting xlink label value.
	 */
	@Test
    public void testGetLabel() {	

		// Case where one exists
		try {
			List<Locator> locators = store.<Locator>getXMLResources("Locator");
			boolean gotParent = false;
            boolean gotChild = false;
			for (Locator locator: locators) {
			    if (locator.getLabel().equals("parent")) {
			        gotParent = true;
			    }
                if (locator.getLabel().equals("child")) {
                    gotChild = true;
                }
			}
			AssertJUnit.assertTrue(gotParent && gotChild);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}	

	/**
	 * Test getting xlink role value.
	 */
	@Test
    public void testGetRole() {	

		// Case where one exists
		try {
			List<Locator> locators = store.<Locator>getXMLResources("Locator");
            boolean gotRole = false;
            for (Locator locator: locators) {
                if (locator.getRole() != null) {
                    if (locator.getRole().toString().equals("http://www.xbrlapi.org/xlink/role")) {
                        gotRole = true;
                    }
                }
            }
            AssertJUnit.assertTrue(gotRole);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}	
	
	/**
	 * Test getting xlink role value.
	 */
	@Test
    public void testGetArcEndId() {	

		// Case where one exists
		try {
			List<Locator> locators = store.<Locator>getXMLResources("Locator");
            boolean gotArcEndId = false;
            for (Locator locator: locators) {
                if (locator.getArcEndId() != null) {
                    if (locator.getArcEndId().equals("unique")) {
                        gotArcEndId = true;
                    }
                }
            }
            AssertJUnit.assertTrue(gotArcEndId);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}	
	
}
