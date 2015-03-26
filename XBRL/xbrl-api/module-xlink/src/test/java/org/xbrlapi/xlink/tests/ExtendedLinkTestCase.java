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

public class ExtendedLinkTestCase {

	private String xmlS1;

	@BeforeMethod
    protected void setUp() throws Exception {
		xmlS1 = 
			"<?xml version='1.0' ?>\n"
			+ "<root xmlns:xlink=\"" + Constants.XLinkNamespace + "\">"

			+ "<child1 xlink:type=\"simple\" xlink:href=\"http://www.xbrlapi.org/\">"
			+ "<child2 xlink:type=\"extended\"/>"
			+ "<child3 xlink:type=\"extended\" xlink:role=\"www.xbrlapi.org/malformedRole/\"/>"
			+ "</child1>"

			+ "<child4 xlink:type=\"extended\" xlink:title=\"Human readable extended link title\">"
			+ "<child5 xlink:type=\"extended\"/>"
			+ "</child4>"

			+ "</root>";
	}

	@AfterMethod
    protected void tearDown() throws Exception {
	}

	/**
	 * Test the basic features of an extended link
	 */
	@Test
    public final void testExtendedLink() throws Exception {
		XLinkProcessor xlinkProcessor = new XLinkProcessorImpl(new ExtendedLinkHandler(this));		
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
