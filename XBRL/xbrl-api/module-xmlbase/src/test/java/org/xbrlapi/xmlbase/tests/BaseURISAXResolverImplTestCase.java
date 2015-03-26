package org.xbrlapi.xmlbase.tests;

import java.io.StringReader;
import java.net.URI;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.xmlbase.BaseURISAXResolver;
import org.xbrlapi.xmlbase.BaseURISAXResolverImpl;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class BaseURISAXResolverImplTestCase extends BaseTestCase {
	
	private String xml;
	private BaseURISAXResolver baseURIResolver, nullBaseURIResolver;

	@BeforeMethod
    protected void setUp() throws Exception {
		xml = 
			"<?xml version=\"1.0\" ?>\n"
			+ "<my:root xml:base=\"http://www.xbrlapi.org/root.xml\" xmlns:my=\"http://www.xbrlapi.org/ns/\">"
			+ "<my:child1 xml:base=\"http://www.xbrlapi.org/child1.xml\"/>"
			+ "<my:child2 xml:base=\"child2.xml\"/>"
			+ "<my:child3 />"
			+ "<my:child4 xml:base=\"\"/>"
			+ "</my:root>";

		baseURIResolver = new BaseURISAXResolverImpl(new URI("http://www.xbrlapi.org/document.xml"));
		nullBaseURIResolver = new BaseURISAXResolverImpl();
	}
	
	/**
	 * Test SAX parser XML Base resolver
	 */
	@Test
    public final void testSAXBaseURIResolution() throws Exception {
        new BaseTestHandler(this,nullBaseURIResolver);
		XMLReader reader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		reader.setFeature("http://xml.org/sax/features/namespaces",true);
		reader.parse(new InputSource(new StringReader(xml)));
	}

	protected final void checkSAXBaseURIHandling(String expected, Attributes attrs, String inheritedURI) {
		try {
			AssertJUnit.assertEquals(expected,baseURIResolver.getBaseURI());
			Assert.assertNotNull(attrs);
			Assert.assertNotNull(inheritedURI);
		} catch (Exception e) {
			Assert.fail("Unexpected Exception when testing SAX Base URI handling. " + e.getMessage());
		}
	}
}