package org.xbrlapi.utils.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import java.net.URI;

import org.xbrlapi.utilities.BaseTestCase;
import org.xbrlapi.utilities.XMLDOMBuilder;

/**
 * @author Geoff Shuetrim (geoff@galexy.net)
 */
public class XMLDOMBuilderTest extends BaseTestCase {
    
    private final String DOCUMENT = "test.data.small.schema";
    private URI uri = null;

    @BeforeMethod
    protected void setUp() throws Exception {
        super.setUp();
        uri = getURI(DOCUMENT);
    }

    @AfterMethod
    protected void tearDown() throws Exception {
        super.tearDown();
    }   
    
    @Test
    public void testDOMConstructionFromAURI() {
        try {
            XMLDOMBuilder builder = new XMLDOMBuilder();
            builder.newDocument(uri);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown.");
        }
    }
    
}
