package org.xbrlapi.aspects.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.util.HashMap;
import java.util.Map;

import org.xbrlapi.Context;
import org.xbrlapi.DOMLoadingTestCase;
import org.xbrlapi.Period;
import org.xbrlapi.aspects.PeriodAspectValue;
import org.xbrlapi.impl.ContextImpl;


/**
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class PeriodAspectTestCase extends DOMLoadingTestCase {
	private final String INSTANCE = "test.data.local.xbrl.period.interpretation";
	
    private final String DATE_ONLY = "date-only";
    private final String DATE_AND_TIME = "date-and-time";
    private final String DATE_AND_FRACTIONAL_TIME = "date-and-fractional-time";
    private final String UTC_DATE_AND_TIME = "utc-date-and-time";
    private final String UTC_OFFSET_DATE_AND_TIME = "utc-offset-date-and-time";
    private final String INFERRED_END = "inferred-end";
    private final String INFERRED_START_AND_END = "inferred-start-and-end";
	
	@BeforeMethod
    protected void setUp() throws Exception {
        super.setUp();
	}

	@AfterMethod
    protected void tearDown() throws Exception {
       super.tearDown();
	}	
	
    @Test
    public void testPeriodAspectValues() {
        try {

            // Load and retrieve the facts
            loader.discover(this.getURI(INSTANCE));
            Map<String,Context> contexts = new HashMap<String,Context>();
            for (Context context: store.<Context>getXMLResources(ContextImpl.class)) {
                contexts.put(context.getId(),context);
            }
            AssertJUnit.assertTrue(contexts.size() > 0);
            
            Period period = contexts.get(DATE_ONLY).getPeriod();
            PeriodAspectValue value = new PeriodAspectValue(period);
            AssertJUnit.assertEquals("2008-01-01, 00:00:00 no timezone",value.getId());

            period = contexts.get(DATE_AND_TIME).getPeriod();
            value = new PeriodAspectValue(period);
            AssertJUnit.assertEquals("2007-12-31, 11:35:01 no timezone",value.getId());

            period = contexts.get(DATE_AND_FRACTIONAL_TIME).getPeriod();
            value = new PeriodAspectValue(period);
            AssertJUnit.assertEquals("2007-12-31, 11:35:01.0035 no timezone",value.getId());
            
            period = contexts.get(UTC_DATE_AND_TIME).getPeriod();
            value = new PeriodAspectValue(period);
            AssertJUnit.assertEquals("2007-12-31, 11:35:01 GMT+00:00",value.getId());

            period = contexts.get(UTC_OFFSET_DATE_AND_TIME).getPeriod();
            value = new PeriodAspectValue(period);
            AssertJUnit.assertEquals("2007-12-31, 11:00:00 GMT+04:00",value.getId());
            
            period = contexts.get(this.INFERRED_END).getPeriod();
            value = new PeriodAspectValue(period);
            AssertJUnit.assertEquals("2008-01-01, 00:00:00 no timezone",value.getId());
            
            period = contexts.get(this.INFERRED_START_AND_END).getPeriod();
            value = new PeriodAspectValue(period);
            AssertJUnit.assertEquals("2007-01-01, 00:00:00 no timezone - 2008-01-01, 00:00:00 no timezone",value.getId());
            
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
	
}
