package org.xbrlapi.data.bdbxml.fragment.tests;

import java.net.URI;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.Arc;
import org.xbrlapi.Concept;
import org.xbrlapi.Context;
import org.xbrlapi.ExtendedLink;
import org.xbrlapi.Fact;
import org.xbrlapi.FootnoteResource;
import org.xbrlapi.Instance;
import org.xbrlapi.Item;
import org.xbrlapi.LabelResource;
import org.xbrlapi.Period;
import org.xbrlapi.Relationship;
import org.xbrlapi.Resource;
import org.xbrlapi.Unit;
import org.xbrlapi.data.bdbxml.StoreImpl;
import org.xbrlapi.data.bdbxml.tests.BaseTestCase;
import org.xbrlapi.impl.SimpleNumericItemImpl;
import org.xbrlapi.loader.Loader;
import org.xbrlapi.networks.AnalyserImpl;
import org.xbrlapi.utilities.Constants;

/**
 * Tests label retrieval
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class PavelTestCase extends BaseTestCase {

    // Create the logger
    protected static Logger logger = Logger.getLogger(PavelTestCase.class);  

	@BeforeMethod
    protected void setUp() throws Exception {
	    super.setUp();
	}

	@AfterMethod
    protected void tearDown() throws Exception {
	    super.tearDown();
	}


	
    @Test
    @Ignore
    public void testCalculationWeightRetrieval() {
        StoreImpl store = null;
        try {
            store = createStore("test2PavelData");
            store.setAnalyser(new AnalyserImpl(store));
            Loader loader = this.createLoader(store);
            String instanceUri = "http://pavel.com/nick-20110930.xml";
            URI targetURI = new URI(instanceUri);
            loader.discover(targetURI);
            
            AnalyserImpl analyzerImpl = (AnalyserImpl)store.getAnalyser();
            
            List<Relationship> relationships = analyzerImpl.getRelationships(Constants.CalculationArcrole);
            
            for (Relationship relationship : relationships) {
                Arc arc = relationship.getArc();
                System.out.println(store.serialize(arc.getMetadataRootElement()));
                System.out.println(arc.getFrom() + ":" + arc.getTo() + ":" + arc.getUse() + ":" + arc.getOrder());
                
                // problem when getting weight of the arc
                System.out.println(arc.getFrom() + ":" + arc.getTo() + ":" + arc.getUse() + ":" + arc.getOrder() + ":" + arc.getWeight());
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
            Assert.fail("Unexpected: " + e.getMessage());
        } finally {
            try {
                //store.delete();
            } catch (Exception x) {
                Assert.fail("The store could not be deleted.");
            }
        }
    }		
	
	
    @Test
    @Ignore
    public void testLoadingOfPavelData() {
        StoreImpl store = null;
        try {
            store = createStore("testPavelData");
            store.setAnalyser(new AnalyserImpl(store));
            Loader loader = this.createLoader(store);
            String instanceUri = "http://pavel.com/nick-20110930.xml";
            URI targetURI = new URI(instanceUri);
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
                      
                      Fact fact = (Fact) store.getXMLResource("ygoQYr_96");
                      Concept concept = fact.getConcept();
                      //logger.info("\n" + store.serialize(concept.getMetadataRootElement()));
                      List<LabelResource> lrs = concept.getLabelsWithResourceRole(Constants.VerboseLabelRole);
                      logger.info("# verbose labels = " + lrs.size());
                      if (lrs.size() <= 0) {
                          throw new IllegalStateException();
                      }

                      lrs = concept.getLabels();
                      logger.info("# labels = " + lrs.size());
                      if (lrs.size() <= 0) {
                          throw new IllegalStateException();
                      }
                                            
                      System.out.println(name + ":" +  value + ":" + lrs.size());
                  }
              }
              System.out.println("-----------------------------------");
            }                  
            
        } catch (Exception e) {
        	e.printStackTrace();
            Assert.fail("Unexpected: " + e.getMessage());
        } finally {
            try {
                //store.delete();
            } catch (Exception x) {
                Assert.fail("The store could not be deleted.");
            }
        }
    }	

}
