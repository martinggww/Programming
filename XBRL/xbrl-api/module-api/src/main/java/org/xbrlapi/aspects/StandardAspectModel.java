package org.xbrlapi.aspects;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.xbrlapi.Context;
import org.xbrlapi.Entity;
import org.xbrlapi.Fact;
import org.xbrlapi.Fragment;
import org.xbrlapi.Item;
import org.xbrlapi.NumericItem;
import org.xbrlapi.Period;
import org.xbrlapi.Scenario;
import org.xbrlapi.Segment;
import org.xbrlapi.data.Store;
import org.xbrlapi.utilities.XBRLException;

/**
 * <h2>Standard extender</h2>
 * 
 * <p>
 * The standard extender discovers the following aspects
 * </p>
 * 
 * <ul>
 *  <li>@see LocationAspect</li>
 *  <li>@see ConceptAspect</li>
 *  <li>@see EntityAspect</li>
 *  <li>@see PeriodAspect</li>
 *  <li>@see SegmentAspect</li>
 *  <li>@see ScenarioAspect</li>
 *  <li>@see UnitAspect</li>
 * </ul>
 * 
 * These are the full set of aspects defined under the non-XDT 
 * aspect model in the 
 * <a href="http://www.xbrl.org/Specification/variables/REC-2009-06-22/variables-REC-2009-06-22.html">XBRL Variables 1.0 specification</a>.
 * 
 * That set of standard aspects can be augmented with other aspects as required.
 * 
 * @author Geoff Shuetrim (geoff@galexy.net)
 */

public class StandardAspectModel extends AspectModelImpl implements AspectModel {
    
    /**
     * 
     */
    private static final long serialVersionUID = -7677982636300869250L;
    private static final Logger logger = Logger
    .getLogger(StandardAspectModel.class);

    public StandardAspectModel(Store store) throws XBRLException {
        super(store);
    }

    /**
     * @see AspectModel#initialise()
     */
    public void initialise() throws XBRLException {
        addAspect(new LocationAspect(new LocationDomain(getStore())));
        addAspect(new ConceptAspect(new ConceptDomain(getStore())));
        addAspect(new UnitAspect(new UnitDomain(getStore())));
        addAspect(new PeriodAspect(new PeriodDomain(getStore())));
        addAspect(new EntityAspect(new EntityDomain(getStore())));
        addAspect(new SegmentAspect(new SegmentDomain(getStore())));
        addAspect(new ScenarioAspect(new ScenarioDomain(getStore())));
    }
    
    /**
     * @see AspectModel#getAspectValues(Fact)
     */
    @Override
    public Map<String, AspectValue> getAspectValues(Fact fact) throws XBRLException {
        Map<String,AspectValue> result = new HashMap<String,AspectValue>();

        result.put(ConceptAspect.ID, ((ConceptAspect)getAspect(ConceptAspect.ID)).getValue(fact) );
        
        Fragment parent = fact.getParent();
        result.put(LocationAspect.ID, ((LocationAspect)getAspect(LocationAspect.ID)).getValue(fact,parent) );

        if (fact.isNil() || fact.isTuple()) {
            result.put(EntityAspect.ID, getAspect(EntityAspect.ID).getMissingValue() );
            result.put(PeriodAspect.ID, getAspect(PeriodAspect.ID).getMissingValue() );
            result.put(SegmentAspect.ID, getAspect(SegmentAspect.ID).getMissingValue() );
            result.put(ScenarioAspect.ID, getAspect(ScenarioAspect.ID).getMissingValue() );
            result.put(UnitAspect.ID, getAspect(UnitAspect.ID).getMissingValue() );
        } else {
        
            Item item = (Item) fact;
            
            Context context = item.getContext();
            Entity entity = context.getEntity();
            Period period = context.getPeriod();
            Scenario scenario = context.getScenario();
            Segment segment = entity.getSegment();
            
            result.put(EntityAspect.ID, ((EntityAspect)getAspect(EntityAspect.ID)).getValue(entity) );
            result.put(PeriodAspect.ID, ((PeriodAspect)getAspect(PeriodAspect.ID)).getValue(period) );
            result.put(SegmentAspect.ID, ((SegmentAspect)getAspect(SegmentAspect.ID)).getValue(segment) );
            result.put(ScenarioAspect.ID, ((ScenarioAspect)getAspect(ScenarioAspect.ID)).getValue(scenario) );
            
            if (item.isNumeric()) 
                result.put(UnitAspect.ID, ((UnitAspect)getAspect(UnitAspect.ID)).getValue(((NumericItem) item).getUnit()) );
            else 
                result.put(UnitAspect.ID, getAspect(UnitAspect.ID).getMissingValue());
        }   
        // Return the map of aspect values, filling in gaps for any aspects other than those dealt with above.
        return this.getAspectValuesFromFact(fact,result);
        
    }

    
    
}
