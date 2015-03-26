package org.xbrlapi.xlink.tests;

import java.io.StringReader;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.utilities.Constants;
import org.xbrlapi.xlink.XLinkProcessor;
import org.xbrlapi.xlink.XLinkProcessorImpl;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * XLink Processor tests
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class TitleTestCase {

	private String xmlS1;

	@BeforeMethod
    protected void setUp() throws Exception {
		xmlS1 = 
			"<?xml version='1.0' ?>\n"
			+ "<root xmlns:xlink=\"" + Constants.XLinkNamespace + "\">"

			+ "<child1 xlink:type=\"title\">"
			+ "Child 1 title"
			+ "</child1>"

			+ "<child2 xlink:type=\"extended\">"
			
			+ "<child3 xlink:type=\"title\">"
			+ "Child 3 title"
			+ "<child6 xlink:type=\"title\">Child 6 title</child6>"
			+ "</child3>"
			
			+ "<child4 xlink:type=\"arc\">"
			+ "<child5 xlink:type=\"title\">Child 5 title</child5>"
			+ "</child4>"

			+ "<child7 xlink:type=\"resource\">"
			+ "<child8 xlink:type=\"title\">Child 8 title</child8>"
			+ "</child7>"
			
			+ "</child2>"
			
			+ "</root>";
	}

	@AfterMethod
    protected void tearDown() throws Exception {
	}

	/**
	 * Test the basic features of a title
	 */
	@Test
    public final void testTitle() throws Exception {
		XLinkProcessor xlinkProcessor = new XLinkProcessorImpl(new TitleHandler(this));		
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
