package org.xbrlapi.xpointer.tests;

import java.util.Vector;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.xpointer.ParseException;
import org.xbrlapi.xpointer.PointerGrammar;
import org.xbrlapi.xpointer.PointerPart;
import org.xbrlapi.xpointer.TokenMgrError;

/**
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class PointerGrammarTestCase {

	@BeforeMethod
    protected void setUp() throws Exception {
	}

	@AfterMethod
    protected void tearDown() throws Exception {
	}

	@Test
	public void testShortHandPointer() {
		String pointer = "qwerty";
	  	java.io.StringReader sr = new java.io.StringReader(pointer);
		java.io.Reader r = new java.io.BufferedReader(sr);
		PointerGrammar parser = new PointerGrammar(r);
		try {
			Vector<PointerPart> pointerParts = parser.Pointer();
			PointerPart p = (PointerPart) pointerParts.get(0);
			AssertJUnit.assertEquals(pointer,p.getEscapedSchemeData());
			AssertJUnit.assertEquals(PointerPart.DefaultPointerNamespace,p.getSchemeNamespace().toString());
			AssertJUnit.assertEquals("element",p.getSchemeLocalName());			
		} catch (ParseException e) {
			Assert.fail("X Pointer, " + pointer + " should not cause a PointerGrammar parse error.");
		} catch (Exception e) {
			Assert.fail("The shorthand pointer parts were not correctly constructed by the Pointer Grammar.");
		}
	}
	
	@Test
    public void testInvalidShortHandPointer() {
		String pointer = "Invalid/Id";
	  	java.io.StringReader sr = new java.io.StringReader(pointer);
		java.io.Reader r = new java.io.BufferedReader(sr);
		PointerGrammar parser = new PointerGrammar(r);
		try {
			parser.Pointer();
			Assert.fail("X Pointer, " + pointer + ", is not valid.");
		} catch (TokenMgrError e) {
		} catch (ParseException e) {
		}
	}

	@Test
    public void testInvalidShortHandPointerAfterSchemePointer() {
		String pointer = "element(/1) qwerty";
	  	java.io.StringReader sr = new java.io.StringReader(pointer);
		java.io.Reader r = new java.io.BufferedReader(sr);
		PointerGrammar parser = new PointerGrammar(r);
		try {
			parser.Pointer();
			Assert.fail("X Pointer, " + pointer + ", is not valid.");
		} catch (TokenMgrError e) {
		} catch (ParseException e) {
		}
	}
	
	@Test
	public void testElementPointer() {
		String pointer = "element(/1)";
	  	java.io.StringReader sr = new java.io.StringReader(pointer);
		java.io.Reader r = new java.io.BufferedReader(sr);
		PointerGrammar parser = new PointerGrammar(r);
		try {
			Vector<PointerPart> pointerParts = parser.Pointer();
			PointerPart p = (PointerPart) pointerParts.get(0);
			AssertJUnit.assertEquals("/1",p.getEscapedSchemeData());
			AssertJUnit.assertEquals(PointerPart.DefaultPointerNamespace,p.getSchemeNamespace().toString());
			AssertJUnit.assertEquals("element",p.getSchemeLocalName());			
		} catch (TokenMgrError e) {
			Assert.fail("Unexpected failure to parse tokens in " + pointer);
		} catch (ParseException e) {
			Assert.fail("Unexpected failure to parse " + pointer);
		}
	}

	@Test
	public void testXMLNSPointer() {
		String pointer = "xmlns(x http://www.xbrlapi.org/stuff)";
		// TODO make sure that xmlns scheme pointer part syntax is used correctly.
	  	java.io.StringReader sr = new java.io.StringReader(pointer);
		java.io.Reader r = new java.io.BufferedReader(sr);
		PointerGrammar parser = new PointerGrammar(r);
		try {
			Vector<PointerPart> pointerParts = parser.Pointer();
			PointerPart p = (PointerPart) pointerParts.get(0);
			AssertJUnit.assertEquals("x http://www.xbrlapi.org/stuff",p.getEscapedSchemeData());
			AssertJUnit.assertEquals(PointerPart.DefaultPointerNamespace,p.getSchemeNamespace().toString());
			AssertJUnit.assertEquals("xmlns",p.getSchemeLocalName());			
		} catch (TokenMgrError e) {
			Assert.fail("Unexpected failure to parse tokens in " + pointer);
		} catch (ParseException e) {
			Assert.fail("Unexpected failure to parse " + pointer);
		}
	}

	@Test
	public void testTwoPartPointer() {
		String pointer = "element(/1/123) \n\r\telement(/1)";
	  	java.io.StringReader sr = new java.io.StringReader(pointer);
		java.io.Reader r = new java.io.BufferedReader(sr);
		PointerGrammar parser = new PointerGrammar(r);
		try {
			Vector<PointerPart> pointerParts = parser.Pointer();
			PointerPart p0 = (PointerPart) pointerParts.get(0);
			AssertJUnit.assertEquals("/1/123",p0.getEscapedSchemeData());
			AssertJUnit.assertEquals(PointerPart.DefaultPointerNamespace,p0.getSchemeNamespace().toString());
			AssertJUnit.assertEquals("element",p0.getSchemeLocalName());			
			PointerPart p1 = (PointerPart) pointerParts.get(1);
			AssertJUnit.assertEquals("/1",p1.getEscapedSchemeData());
			AssertJUnit.assertEquals(PointerPart.DefaultPointerNamespace,p1.getSchemeNamespace().toString());
			AssertJUnit.assertEquals("element",p1.getSchemeLocalName());			
		} catch (TokenMgrError e) {
			Assert.fail("Unexpected failure to parse tokens in " + pointer);
		} catch (ParseException e) {
			Assert.fail("Unexpected failure to parse " + pointer);
		}
	}
}
