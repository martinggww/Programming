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
 * Tests of illegal URI characters using the DOM based XML Base resolver
 * TODO check that xml base is adequately testing illegal URI characters
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class BaseURIDOMResolverImplIllegalCharacterHandlingTestCase extends BaseTestCase {
	
	private String xmlS1;
	private Document xmlD1;
	
	private BaseURIDOMResolver baseURIResolver;

	@BeforeMethod
    protected void setUp() throws Exception {
		// Create the XML documents to analyse
		xmlS1 = 
			"<?xml version=\"1.0\" ?>\n"
			+ "<root xml:base=\"http://www.xbrlapi.org/r[oo]t.xml\">"
			+ "<child1 xml:base=\"http://www.xbrlapi.org/child1.xml\"/>"
			+ "<child2 xml:base=\"child2.xml\"/>"
			+ "<child3 />"
			+ "<child4 xml:base=\"\"/>"
			+ "</root>";
		xmlD1 = (new XMLDOMBuilder()).newDocument(xmlS1);

		baseURIResolver = new BaseURIDOMResolverImpl(new URI("http://www.xbrlapi.org/document.xml"));
		
	}

	/**
	 * Test the encoding of the space character in an xml:base attribute
	 */
	@Test
    public final void testGetExplicitBaseURIForRootElement() {
		NodeList elts = xmlD1.getElementsByTagName("root");
		AssertJUnit.assertEquals("DOM operation failed",1,elts.getLength());
		Element elt = (Element) elts.item(0);
		try {
			URI baseURI = baseURIResolver.getBaseURI(elt);
			logger.info(baseURI);
            Assert.fail("Should have thrown an XML Base Exception when determining the base URI.");
		} catch (XMLBaseException expected) {
		    ;
		}
	}
	
}