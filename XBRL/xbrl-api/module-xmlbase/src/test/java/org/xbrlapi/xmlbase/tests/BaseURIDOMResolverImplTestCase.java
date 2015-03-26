package org.xbrlapi.xmlbase.tests;

import java.net.URI;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xbrlapi.utilities.XMLDOMBuilder;
import org.xbrlapi.xmlbase.BaseURIDOMResolver;
import org.xbrlapi.xmlbase.BaseURIDOMResolverImpl;
import org.xbrlapi.xmlbase.XMLBaseException;

/**
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class BaseURIDOMResolverImplTestCase extends BaseTestCase {
	
	private String xmlS1, xmlS2, xmlS3;
	private Document xmlD1, xmlD2, xmlD3;
	private BaseURIDOMResolver baseURIResolver, nullBaseURIResolver;

	@BeforeMethod
    protected void setUp() throws Exception {
		// Create the XML documents to analyse
		xmlS1 = 
			"<?xml version=\"1.0\" ?>\n"
			+ "<root xml:base=\"http://www.xbrlapi.org/root.xml\">"
			+ "<child1 xml:base=\"http://www.xbrlapi.org/child1.xml\"/>"
			+ "<child2 xml:base=\"child2.xml\"/>"
			+ "<child3 />"
			+ "<child4 xml:base=\"\"/>"
			+ "</root>";
		xmlD1 = (new XMLDOMBuilder()).newDocument(xmlS1);
		
		xmlS2 = 
			"<?xml version=\"1.0\" ?>\n"
			+ "<my:root xml:base=\"http://www.xbrlapi.org/root.xml\" xmlns:my=\"http://www.xbrlapi.org/ns/\">"
			+ "<my:child1 xml:base=\"http://www.xbrlapi.org/child1.xml\"/>"
			+ "<my:child2 xml:base=\"child2.xml\"/>"
			+ "<my:child3 />"
			+ "<my:child4 xml:base=\"\"/>"
			+ "</my:root>";
		xmlD2 = (new XMLDOMBuilder()).newDocument(xmlS2);

		xmlS3 = 
			"<?xml version=\"1.0\" ?>\n"
			+ "<my:root xmlns:my=\"http://www.xbrlapi.org/ns/\">"
			+ "<my:child1 xml:base=\"http://www.xbrlapi.org/child1.xml\"/>"
			+ "<my:child2 xml:base=\"child2.xml\"/>"
			+ "<my:child3 />"
			+ "<my:child4 xml:base=\"\"/>"
			+ "</my:root>";
		xmlD3 = (new XMLDOMBuilder()).newDocument(xmlS3);

		baseURIResolver = new BaseURIDOMResolverImpl(new URI("http://www.xbrlapi.org/document.xml"));
		nullBaseURIResolver = new BaseURIDOMResolverImpl();
	}

	/**
	 * Test the Base URI construction for the root element
	 * in an XML document without namespaces for elements and
	 * where the root element has an explicit xml:base attribute
	 */
	@Test
    public final void testGetExplicitBaseURIForRootElement() {
		NodeList elts = xmlD1.getElementsByTagName("root");
		AssertJUnit.assertEquals("DOM operation failed",1,elts.getLength());
		Element elt = (Element) elts.item(0);
		try {
			AssertJUnit.assertEquals("http://www.xbrlapi.org/root.xml",baseURIResolver.getBaseURI(elt).toString());
		} catch (XMLBaseException e) {
			Assert.fail("Unexpected XBRLException thrown when determining the base URI.");
		}
	}

	/**
	 * Test the Base URI construction for the root element
	 * in an XML document without namespaces for elements and
	 * where the root element has NO explicit xml:base attribute.
	 */
	@Test
    public final void testGetImplicitBaseURIForRootElement() {
		NodeList elts = xmlD3.getElementsByTagNameNS("http://www.xbrlapi.org/ns/","root");
		AssertJUnit.assertEquals("DOM operation failed",1,elts.getLength());
		Element elt = (Element) elts.item(0);
		try {
			AssertJUnit.assertEquals("http://www.xbrlapi.org/document.xml",baseURIResolver.getBaseURI(elt).toString());
		} catch (XMLBaseException e) {
			Assert.fail("Unexpected XBRLException thrown when determining the base URI.");
		}
	}

	/**
	 * Test the resolution of a relative child elements xml:base
	 * against the absolute xml:base of the root element.
	 */
	@Test
    public final void testGetResolvedBaseURI() {
		NodeList elts = xmlD1.getElementsByTagName("child2");
		AssertJUnit.assertEquals("DOM operation failed",1,elts.getLength());
		Element elt = (Element) elts.item(0);
		try {
			AssertJUnit.assertEquals("http://www.xbrlapi.org/child2.xml",nullBaseURIResolver.getBaseURI(elt).toString());
		} catch (XMLBaseException e) {
			Assert.fail("Unexpected XBRLException thrown when determining the base URI. " + e.getMessage());
		}
	}	

	/**
	 * Test the resolution of a relative child against a relative root
	 * against an absolute document URI.
	 */
	@Test
    public final void testGetResolvedBaseURICascadingToDocumentURI() {
		NodeList elts = xmlD2.getElementsByTagNameNS("http://www.xbrlapi.org/ns/","child2");
		AssertJUnit.assertEquals("DOM operation failed",1,elts.getLength());
		Element elt = (Element) elts.item(0);
		try {
			AssertJUnit.assertEquals("http://www.xbrlapi.org/child2.xml",baseURIResolver.getBaseURI(elt).toString());
		} catch (XMLBaseException e) {
			Assert.fail("Unexpected XBRLException thrown when determining the base URI. " + e.getMessage());
		}
	}
	
	/**
	 * Test the throwing of an exception when the xml:base attribute
	 * is a relative address and there is no absolute URI to resolve
	 * it against.
	 */
	@Test
    public final void testFailureForMalformedXMLBaseURI() {
		NodeList elts = xmlD3.getElementsByTagNameNS("http://www.xbrlapi.org/ns/","child2");
		AssertJUnit.assertEquals("Child element was not retrieved correctly.",1,elts.getLength());
		Element elt = (Element) elts.item(0);
		try {
			AssertJUnit.assertEquals("http://www.xbrlapi.org/child2.xml",nullBaseURIResolver.getBaseURI(elt).toString());
			Assert.fail("Relative URI with no absolute base URI to resolve against should have thrown an XBRLException.");
		} catch (XMLBaseException expected) {
			;
		}
	}	

}