package org.xbrlapi.xdt.tests;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class LoaderSerializationTestCase extends BaseTestCase {
	
	private final String STARTING_POINT = "test.data.small.schema";
	private final String STARTING_POINT_2 = "test.data.small.instance";
	private URI uri1 = null;
	private URI uri2 = null;
	private List<URI> uris = new LinkedList<URI>();
	
	@BeforeMethod
    protected void setUp() throws Exception {
        super.setUp();
		uri1 = getURI(this.STARTING_POINT);
		uri2 = getURI(this.STARTING_POINT_2);
		uris.add(uri1);
		uris.add(uri2);
	}

    @AfterMethod
    protected void tearDown() throws Exception {
        super.tearDown();
    }

	@Test
    public void testLoaderImplStoreXLinkProcessor_FailOnNullStore() throws Exception {
		try {
		    this.assessCustomEquality(loader,this.getDeepCopy(loader));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}


}
