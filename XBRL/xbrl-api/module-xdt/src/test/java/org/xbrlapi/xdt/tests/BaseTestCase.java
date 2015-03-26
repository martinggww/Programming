package org.xbrlapi.xdt.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.File;

import org.xbrlapi.cache.CacheImpl;
import org.xbrlapi.data.Store;
import org.xbrlapi.data.dom.StoreImpl;
import org.xbrlapi.loader.Loader;
import org.xbrlapi.sax.EntityResolver;
import org.xbrlapi.sax.EntityResolverImpl;
import org.xbrlapi.xdt.CustomLinkRecogniserImpl;
import org.xbrlapi.xdt.LoaderImpl;
import org.xbrlapi.xdt.XLinkHandlerImpl;
import org.xbrlapi.xlink.XLinkProcessor;
import org.xbrlapi.xlink.XLinkProcessorImpl;

/**
 * Provides a base test case for all XDT tests.
 *  
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public abstract class BaseTestCase extends org.xbrlapi.utilities.BaseTestCase {

	protected Store store = null;
	protected Loader loader = null;
	
	private XLinkHandlerImpl xlinkHandler = null;
	private XLinkProcessor xlinkProcessor = null;
	private EntityResolver entityResolver = null;
	
	@BeforeMethod
    protected void setUp() throws Exception {
	    super.setUp();
		store = new StoreImpl();
		xlinkHandler = new XLinkHandlerImpl();
		CustomLinkRecogniserImpl clr = new CustomLinkRecogniserImpl(); 
		xlinkProcessor = new XLinkProcessorImpl(xlinkHandler ,clr);
		File cacheFile = new File(configuration.getProperty("local.cache"));
		entityResolver = new EntityResolverImpl(cacheFile);
		loader = new LoaderImpl(store,xlinkProcessor, entityResolver);
		loader.setCache(new CacheImpl(cacheFile));
		loader.setEntityResolver(entityResolver);
		xlinkHandler.setLoader(loader);
	}
	
	@AfterMethod
    protected void tearDown() throws Exception {
	    super.tearDown();
	}
	
}
