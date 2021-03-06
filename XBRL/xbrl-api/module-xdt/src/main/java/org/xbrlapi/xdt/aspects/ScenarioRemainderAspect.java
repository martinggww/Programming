package org.xbrlapi.xdt.aspects;

import org.apache.log4j.Logger;
import org.xbrlapi.Context;
import org.xbrlapi.Fact;
import org.xbrlapi.Item;
import org.xbrlapi.Scenario;
import org.xbrlapi.aspects.Aspect;
import org.xbrlapi.aspects.AspectImpl;
import org.xbrlapi.aspects.Domain;
import org.xbrlapi.utilities.XBRLException;

/**
 * <h2>Scenario remainder aspect details</h2>
 * 
 * <p>
 * The value is based on the non-XDT content of 
 * the context scenario.
 * </p>
 * 
 * @author Geoff Shuetrim (geoff@galexy.net)
 */
public class ScenarioRemainderAspect extends AspectImpl implements Aspect {

    /**
     * 
     */
    private static final long serialVersionUID = -6218851432259892401L;

    private static final Logger logger = Logger.getLogger(ScenarioRemainderAspect.class);

    /**
     * The URI uniquely identifying this concept aspect.
     */
    public static String ID = "http://xbrlapi.org/aspect/scenario-remainder/1.0";
    
    /**
     * @see Aspect#getId()
     */
    public String getId() {
        return ID;
    }
    
    /**
     * @param domain The domain for this aspect.
     * @throws XBRLException
     */
    public ScenarioRemainderAspect(Domain domain) throws XBRLException {
        super(domain);
    }
    
    /**
     * @see Aspect#getValue(Fact)
     */
    public ScenarioRemainderAspectValue getValue(Fact fact) throws XBRLException {
        if (fact.isTuple()) return getMissingValue();
        if (fact.isNil()) return getMissingValue();
        return getValue(((Item) fact).getContext());
    }

    /**
     * @see Aspect#getValue(Context)
     */
    public ScenarioRemainderAspectValue getValue(Context context) throws XBRLException {
        return getValue(context.getScenario());
    }
    
    /**
     * @param scenario The context scenario, which may be null.
     * @return the scenario remainder aspect value.
     * @throws XBRLException
     */
    public ScenarioRemainderAspectValue getValue(Scenario scenario) throws XBRLException {
        if (scenario == null) return getMissingValue();
        return new ScenarioRemainderAspectValue(scenario);
    }

    /**
     * @see Aspect#getMissingValue()
     */
    public ScenarioRemainderAspectValue getMissingValue() {
        return new ScenarioRemainderAspectValue();
    }

}
