package org.xbrlapi.utils.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.xbrlapi.utilities.BaseTestCase;
import org.xbrlapi.utilities.XMLDOMBuilder;
import org.xbrlapi.utilities.XMLDOMSerializer;

/**
 * @author Geoff Shuetrim (geoff@galexy.net)
 */
public class XMLDOMSerializerTest extends BaseTestCase {
    
    private String xml = 
    		"<?xml version='1.0' encoding='UTF-8'?>" + 
    		"<root a='value'>" +
    		"<child>test</child>" +    				
    		"</root>";

    @BeforeMethod
    protected void setUp() throws Exception {
        super.setUp();
    }

    @AfterMethod
    protected void tearDown() throws Exception {
        super.tearDown();
    }   
    
    @Test
    public void testDOMSerialization() {
        try {
            XMLDOMBuilder builder = new XMLDOMBuilder();
            Document dom = builder.newDocument(xml);
            logger.info("\n" + XMLDOMSerializer.serialize(dom));
            logger.info("\n" + XMLDOMSerializer.serialize(dom.getDocumentElement().getFirstChild()));
            logger.info("\n" + XMLDOMSerializer.serialize(dom.getDocumentElement().getFirstChild().getFirstChild()));
            
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown.");
        }
    }
    
}
