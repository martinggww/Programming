package org.xbrlapi.fragment.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.net.URI;
import java.net.URISyntaxException;

import org.xbrlapi.impl.FragmentComparator;
import org.xbrlapi.impl.MockImpl;
import org.xbrlapi.utilities.BaseTestCase;

/**
 * Tests the fragment comparator implementation.
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class FragmentComparatorTestCase extends BaseTestCase {

	MockImpl f1;
	MockImpl f2;
	MockImpl f3;
	MockImpl f4;
	MockImpl f5;
	MockImpl f6;
	FragmentComparator comparator;
	
	/*
	 * @see TestCase#setUp()
	 */
	@BeforeMethod
    protected void setUp() throws Exception {
        super.setUp();
		comparator = new FragmentComparator();

        try {
    		f1 = new MockImpl("10");
    	    f1.setURI(new URI("http://uriA/"));
    	    f1.setSequenceToParentElement("");
    		f1.setParentIndex("1");

    		f2 = new MockImpl("10");
    		f2.setURI(new URI("http://uriA/"));
    		f2.setSequenceToParentElement("");
    		f2.setParentIndex("1");

    		f3 = new MockImpl("12");
    		f3.setURI(new URI("http://uriA/"));
    		f3.setSequenceToParentElement("");
    		f3.setParentIndex("1");

    		f4 = new MockImpl("13");
    		f4.setURI(new URI("http://uriA/"));
    		f4.setSequenceToParentElement("");
    		f4.setParentIndex("2");
    		
    		f5 = new MockImpl("14");
    		f5.setURI(new URI("http://uriA/"));
    		f5.setSequenceToParentElement("68 2");
    		f5.setParentIndex("1");
    
    		f6 = new MockImpl("15");
    		f6.setURI(new URI("http://uriB/"));
    		f6.setSequenceToParentElement("");
    		f6.setParentIndex("1");
    		
        } catch (URISyntaxException e) {
            Assert.fail("URI syntax is invalid.");
        }
		
	}

	/*
	 * @see TestCase#tearDown()
	 */
	@AfterMethod
    protected void tearDown() throws Exception {
       super.tearDown();
	}

	/**
	 * Test the comparison of fragment with itself.
	 */
	@Test
    public void testCompareFragmentWithItself() {
		AssertJUnit.assertEquals(0,this.comparator.compare(f1,f1));
	}

	/**
	 * Test the comparison of two equivalent fragments
	 */
	@Test
    public void testCompareEquivalentFragments() {
	    AssertJUnit.assertEquals(0,this.comparator.compare(f1,f2));
	}
	
	/**
	 * Test the comparison of two equivalent fragments
	 */
	@Test
    public void testCompareFragmentsWithDifferentNumbersOfPrecedingSiblings() {
	    try {
	        logger.info(f1.getIndex() + f3.getIndex());
            logger.info(f1.getURI() + " " +  f3.getURI());
            logger.info(comparator.compare(f1,f3));
	        AssertJUnit.assertTrue(this.comparator.compare(f1,f3) < 0);
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Unexpected exception.");
	    }
	}

	/**
	 * Test the comparison of two equivalent fragments
	 */
	@Test
    public void testCompareFragmentsWithDifferentParents() {
		AssertJUnit.assertTrue(this.comparator.compare(f1,f4) < 0);
	}

	/**
	 * Test the comparison of two equivalent fragments
	 */
	@Test
    public void testCompareFragmentsWithDifferentXPathsToContainerElements() {
	    try {
	        AssertJUnit.assertTrue(this.comparator.compare(f1,f5) > 0);
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail(e.getMessage());
	    }
	}
	
	/**
	 * Test the comparison of two equivalent fragments
	 */
	@Test
    public void testCompareFragmentsWithDifferentURIs() {
		AssertJUnit.assertTrue(this.comparator.compare(f1,f6) < 0);
	}

	
	
}
