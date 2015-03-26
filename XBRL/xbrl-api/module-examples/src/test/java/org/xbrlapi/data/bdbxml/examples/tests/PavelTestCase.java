package org.xbrlapi.data.bdbxml.examples.tests;


import java.net.URI;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.Context;
import org.xbrlapi.ExtendedLink;
import org.xbrlapi.FootnoteResource;
import org.xbrlapi.Instance;
import org.xbrlapi.Item;
import org.xbrlapi.LabelResource;
import org.xbrlapi.Period;
import org.xbrlapi.Resource;
import org.xbrlapi.Unit;
import org.xbrlapi.data.bdbxml.StoreImpl;
import org.xbrlapi.impl.SimpleNumericItemImpl;
import org.xbrlapi.loader.Loader;

/**
 * Tests label retrieval
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class PavelTestCase extends BaseTestCase {


    // Create the logger
    protected static Logger logger = Logger.getLogger(LoadTestCase.class);  

	@BeforeMethod
    protected void setUp() throws Exception {
	    super.setUp();
	}

	@AfterMethod
    protected void tearDown() throws Exception {
	    super.tearDown();
	}

    @Test
    public void testLoadingOfPavelData() {
        StoreImpl store = null;
        try {

            String instanceUri = "http://pavel.com/nick-20110930.xml";
            URI targetURI = new URI(instanceUri);
            
            store = createStore("testPavelData");
            Loader loader = this.createLoader(store);
            loader.discover(targetURI);
            
            List<Instance> instances = store.getFragmentsFromDocument(targetURI, "Instance");
            Instance instance = instances.get(0);
            
            List<Unit> units = instance.getUnits();
            System.out.println("Units in the instance.");
            for (Unit unit: units) {
                System.out.println("Unit ID " + unit.getId());
            }
            
            List<ExtendedLink> links = instance.getFootnoteLinks();
            System.out.println("Footnote links in the instance.");
            for (ExtendedLink link: links) {            
                List<Resource> resources = link.getResources();
                for (Resource resource: resources) {
                    FootnoteResource fnr = (FootnoteResource) resource;
                    //System.out.println("Footnote resource: " + fnr.getDataRootElement().getTextContent());
                }
            }
            
            List<Context> contexts = instance.getContexts();
            System.out.println("Contexts in the instance.");
            for (Context context : contexts) {
                System.out.println("Context ID " + context.getId());
                Period period = context.getPeriod();
                if (!period.isInstantPeriod()) {
                  String start = period.getStart();
                  String end = period.getEnd();
                  System.out.println(end + ":" + start);
                } else {
                  String instant = period.getInstant();
                  System.out.println(instant + ":");
                }

                List<Item> items = context.getReferencingItems();
                System.out.println("size:" + items.size());

                for (Item item : items) {
                  if (item instanceof SimpleNumericItemImpl) {
                      SimpleNumericItemImpl itemImpl = (SimpleNumericItemImpl) item;
                      
                      String value = itemImpl.getValue();
                      String name = itemImpl.getLocalname();
                      
                      //List<LabelResource> lrs = itemImpl.getLabelsWithResourceRole(Constants.VerboseLabelRole);
                      List<LabelResource> lrs = itemImpl.getLabels();
                      
                      if (lrs.size() <= 0) {
                          throw new IllegalStateException();
                      }
                      
                      System.out.println(name + ":" +  value + ":" + lrs.size());
                  }
              }
              System.out.println("-----------------------------------");
            }            
            
        } catch (Exception e) {
            Assert.fail("Unexpected: " + e.getMessage());
        } finally {
            try {
                if (store!= null) store.delete();
            } catch (Exception x) {
                Assert.fail("The store could not be deleted.");
            }
        }
    }	

}