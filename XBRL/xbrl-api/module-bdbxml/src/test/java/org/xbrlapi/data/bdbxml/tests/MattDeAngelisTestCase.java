package org.xbrlapi.data.bdbxml.tests;

import java.net.URI;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.Fact;
import org.xbrlapi.Instance;
import org.xbrlapi.NonNumericItem;
import org.xbrlapi.SimpleNumericItem;
import org.xbrlapi.data.Store;
import org.xbrlapi.loader.Loader;
import org.xbrlapi.networks.AnalyserImpl;

/**
 * Test the Matt De Angelis Code.
 * @author Geoffrey Shuetrim (geoff@galexy.net) 
*/
public class MattDeAngelisTestCase extends BaseTestCase {
	
	@BeforeMethod
    protected void setUp() throws Exception {
	    super.setUp();
	}

	@AfterMethod
    protected void tearDown() throws Exception {
	    super.tearDown();
	}

	@Test
    public void testConceptFactRetrieval() {
        try {

            Store store = createStore("MattDeAngelis");
            Loader loader = createLoader(store);
    		store.setAnalyser(new AnalyserImpl(store));

			//URI uri = new URI("http://www.sec.gov/Archives/edgar/data/320187/000119312510161874/nke-20100531.xml");
			URI uri = new URI("http://www.sec.gov/Archives/edgar/data/789019/000119312511200680/msft-20110630.xml");
		
			loader.discover(uri);

			for (Instance instance: store.<Instance>getFragmentsFromDocument(uri, "Instance")) {
				for (Fact fact: instance.getAllFacts()) {
					if (fact.isNumeric() && ! fact.isFraction()) {
						SimpleNumericItem n = (SimpleNumericItem) fact;
						logger.info(fact.getConcept().getTargetNamespace() + ":" + fact.getConcept().getName() + " = " + n.getValue());
					} else if (! fact.isNumeric()) {
						NonNumericItem n = (NonNumericItem) fact;
						logger.info(fact.getConcept().getTargetNamespace() + ":" + fact.getConcept().getName() + " = " + n.getValue());
					}
					
					// Sort out the fact count...
					long count = fact.getConcept().getFactCount();
					logger.info(fact.getConcept().getTargetNamespace() + ":" + fact.getConcept().getName() + " fact count = " + count);
					if (count == 0) {
						logger.info("\n" + store.serialize(fact.getMetadataRootElement()));
						logger.info("\n" + store.serialize(fact.getConcept().getMetadataRootElement()));
					}
				}
			}
				
        } catch (Exception e) {
            Assert.fail("Unexpected: " + e.getMessage());
        }       
	}

}
