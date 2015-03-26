package org.xbrlapi.xlink.tests;
/**
 * XLink Processor tests
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.io.StringReader;

import org.xbrlapi.utilities.Constants;
import org.xbrlapi.xlink.XLinkProcessor;
import org.xbrlapi.xlink.XLinkProcessorImpl;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ResourceTestCase {

	private String xmlS1;

	@BeforeMethod
    protected void setUp() throws Exception {
		xmlS1 = 
			"<?xml version='1.0' ?>\n"
			+ "<root xmlns:xlink=\"" + Constants.XLinkNamespace + "\">"

			+ "<child1 xlink:type=\"resource\" />"

			+ "<child2 xlink:type=\"resource\" xlink:role=\"http://www.xbrlapi.org/role/\" xlink:title=\"Human readable title\"/>"

			+ "<child3 xlink:type=\"extended\">"
			+ "<child4 xlink:type=\"resource\" />"
			+ "<child5 xlink:type=\"resource\" xlink:role=\"http://www.xbrlapi.org/role/\" xlink:title=\"Human readable title\"/>"
			+ "<child6 xlink:type=\"resource\" xlink:role=\"http://www.xbrlapi.org/role/\" xlink:title=\"Human readable title\">"
			+ "<child7 xlink:type=\"resource\" xlink:role=\"http://www.xbrlapi.org/role/\" xlink:title=\"Human readable title\"/>"
			+ "</child6>"
			+ "<child8 xlink:type=\"resource\" xlink:role=\"http://www.xbrlapi.org/role/\" xlink:label=\"1resource\"/>"
			+ "</child3>"
			
			
			+ "</root>";
	}

	@AfterMethod
    protected void tearDown() throws Exception {
	}

	/**
	 * Test the basic features of a resource
	 */
	@Test
    public final void testResource() throws Exception {
		XLinkProcessor xlinkProcessor = new XLinkProcessorImpl(new ResourceHandler(this));		
        ContentHandlerImpl handler = new ContentHandlerImpl(xlinkProcessor);
		XMLReader reader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		reader.setContentHandler(handler);
		reader.setFeature("http://xml.org/sax/features/namespaces",true);
		reader.parse(new InputSource(new StringReader(xmlS1)));
	}

	protected final void checkEqual(String expected, String actual) {
		try {
			AssertJUnit.assertEquals(expected,actual);
		} catch (Exception e) {
			Assert.fail("Unexpected Exception when testing SAX Base URI handling. " + e.getMessage());
		}
	}
	
	protected final void checkIsNull(Object actual) {
		try {
			AssertJUnit.assertNull(actual);
		} catch (Exception e) {
			Assert.fail("Unexpected Exception when testing SAX Base URI handling. " + e.getMessage());
		}
	}
	
	protected final void confirmFail(String message) {
		Assert.fail(message);
	}
		
}
