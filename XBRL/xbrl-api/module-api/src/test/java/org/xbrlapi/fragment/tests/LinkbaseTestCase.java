package org.xbrlapi.fragment.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.xbrlapi.DOMLoadingTestCase;
import org.xbrlapi.Fragment;
import org.xbrlapi.Linkbase;
import org.xbrlapi.SimpleLink;

/**
 * Tests the implementation of the org.xbrlapi.Linkbase interface.
 * Uses the DOM-based data store to ensure rapid testing.
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class LinkbaseTestCase extends DOMLoadingTestCase {
	private final String STARTING_POINT = "test.data.arcrole.reference.instance";
	private final String STARTING_POINT_B = "test.data.linkbase.documentation.element";
    private final String STARTING_POINT_C = "test.data.custom.resource.role";	
	
	@BeforeMethod
    protected void setUp() throws Exception {
        super.setUp();
		loader.discover(this.getURI(STARTING_POINT));
        loader.discover(this.getURI(STARTING_POINT_B));     
		loader.discover(this.getURI(STARTING_POINT_C));		
	}

	@AfterMethod
    protected void tearDown() throws Exception {
       super.tearDown();
	}	
	
	/**
	 * Test getting arcrole references from the linkbase.
	 */
	@Test
    public void testGetArcroleRefs() {	
		try {
            List<SimpleLink> links = store.<SimpleLink>getXMLResources("SimpleLink");
            for (SimpleLink link: links) {
                if (link.getLocalname().equals("arcroleRef")) {
                    Linkbase parent = (Linkbase) link.getParent();
                    int count = 0;
                    List<Fragment> children = parent.getAllChildren();
                    for (Fragment child: children) {
                        if (child.getLocalname().equals("arcroleRef")) count++;                            
                    }
                    AssertJUnit.assertEquals(count,parent.getArcroleRefs().size());
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * Test getting role references from the linkbase.
	 */
	@Test
    public void testGetRoleRefs() {	

		try {
            List<SimpleLink> links = store.<SimpleLink>getXMLResources("SimpleLink");
            for (SimpleLink link: links) {
                if (link.getLocalname().equals("roleRef")) {
                    Linkbase parent = (Linkbase) link.getParent();
                    int count = 0;
                    List<Fragment> children = parent.getAllChildren();
                    for (Fragment child: children) {
                        if (child.getLocalname().equals("roleRef")) count++;                            
                    }
                    AssertJUnit.assertEquals(count,parent.getRoleRefs().size());
                }
            }
		} catch (Exception e) {
		    e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
    public void testGetExtendedLinks() {	

		try {
            List<Linkbase> linkbases = store.<Linkbase>getXMLResources("Linkbase");
            for (Linkbase linkbase: linkbases) {
                List<Fragment> children = linkbase.getAllChildren();
                int count = 0;
                for (Fragment child: children) {
                    if (child.getType().equals("org.xbrlapi.impl.ExtendedLinkImpl")) 
                        count++;
                }
                AssertJUnit.assertEquals(count,linkbase.getExtendedLinks().size());
            }

		} catch (Exception e) {
		    e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
    @Test
    public void testGetExtendedLinkQNames() {    

        QName qname2 = new QName("http:/asdf/", "name");
        QName qname1 = new QName("http:/asdf/", "name");
        AssertJUnit.assertEquals(qname1,qname2);
        
        try {
            List<Linkbase> linkbases = store.<Linkbase>getXMLResources("Linkbase");
            for (Linkbase linkbase: linkbases) {
                Set<QName> qnames = linkbase.getExtendedLinkQNames();
                AssertJUnit.assertTrue(qnames.size() > 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }	
	
	/**
	 * Test getting documentation fragments from the linkbase.
	 */
	@Test
    public void testGetDocumentations() {	

		try {
            List<Linkbase> linkbases = store.<Linkbase>getXMLResources("Linkbase");
            for (Linkbase linkbase: linkbases) {
                List<Fragment> children = linkbase.getAllChildren();
                int count = 0;
                for (Fragment child: children) {
                    if (child.getType().equals("org.xbrlapi.impl.XlinkDocumentationImpl")) 
                        count++;
                }
                logger.info(count);
                AssertJUnit.assertEquals(count,linkbase.getDocumentations().size());
            }
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}	
	
}
