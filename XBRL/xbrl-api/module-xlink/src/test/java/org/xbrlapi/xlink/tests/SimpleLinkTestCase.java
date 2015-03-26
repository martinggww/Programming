package org.xbrlapi.xlink.tests;


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

/**
 * XLink Processor tests
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class SimpleLinkTestCase {

	private String xmlS1;

	@BeforeMethod
    protected void setUp() throws Exception {
		xmlS1 = 
			"<?xml version='1.0' ?>\n"
			+ "<my:root xmlns:my=\"http://www.xbrlapi.org/ns/\" xmlns:xlink=\"" + Constants.XLinkNamespace + "\">"

			+ "<my:child1 xlink:type=\"simple\" xlink:href=\"http://www.xbrlapi.org/\"/>"

			+ "<my:child2 xlink:type=\"simple\" xlink:href=\"http://www.xbrlapi.org/\" xlink:role=\"http://www.xbrlapi.org/role/\" xlink:arcrole=\"http://www.xbrlapi.org/arcrole/\" xlink:show=\"new\" xlink:actuate=\"onLoad\" xlink:title=\"Human readable title\"/>"

			+ "<my:child3 xlink:type=\"extended\">"
			+ "<my:child4 xlink:type=\"simple\"/>"
			+ "</my:child3>"

			+ "<my:child5 xlink:type=\"simple\" xlink:href=\"http://www.xbrlapi.org/\">"
			+ "<my:child6 xlink:type=\"extended\">"
			+ "<my:child7 xlink:type=\"simple\" xlink:show=\"embed\"/>"
			+ "</my:child6>"
			+ "</my:child5>"

			+ "<my:child8 xlink:type=\"simple\" xlink:href=\"http://www.xbrlapi.org/\" xlink:role=\"malformed role\" xlink:arcrole=\"http://www.xbrlapi.org/arcrole/\" xlink:show=\"new\" xlink:actuate=\"onLoad\" xlink:title=\"Human readable title\"/>"

			+ "</my:root>";
	}

	@AfterMethod
    protected void tearDown() throws Exception {
	}

	private boolean e8 = false;
	/**
	 * Test the basic features of a simple link
	 */
	@Test
    public final void testSimpleLink() throws Exception {
		XLinkProcessor xlinkProcessor = new XLinkProcessorImpl(new SimpleLinkHandler(this));		
        ContentHandlerImpl handler = new ContentHandlerImpl(xlinkProcessor);
		XMLReader reader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		reader.setContentHandler(handler);
		reader.setFeature("http://xml.org/sax/features/namespaces",true);
		reader.parse(new InputSource(new StringReader(xmlS1)));
		if ( ! e8 ) {
			Assert.fail("child8 malformed role not signalled.");
		}
	}

	protected final void noteE8() {
		e8 = true;
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
