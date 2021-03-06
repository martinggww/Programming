package org.xbrlapi.aspects;

import org.apache.log4j.Logger;
import org.xbrlapi.data.Store;
import org.xbrlapi.utilities.XBRLException;

/**
 * This is the same as the standard aspect model except that it 
 * also sets up the store-based caching aspect value labeller system
 * automatically as part of the constructor.
 * 
 * @author Geoff Shuetrim (geoff@galexy.net)
 */
public class StandardAspectModelWithStoreCachingLabellers extends StandardAspectModel implements AspectModel {

    /**
     * 
     */
    private static final long serialVersionUID = 5288184252388411327L;
    
    private static final Logger logger = Logger.getLogger(StandardAspectModelWithStoreCachingLabellers.class);

    /**
     * @param store The data store.
     * @throws XBRLException if a parameter is null.
     */
    public StandardAspectModelWithStoreCachingLabellers(Store store) throws XBRLException {
        super(store);
    }

    /**
     * @see AspectModel#initialise()
     */
    public void initialise() throws XBRLException {
        super.initialise();
        LabelCache cache = new StoreLabelCache(getStore());
        this.setLabeller(LocationAspect.ID, new CachingLabeller(cache, new LocationLabeller(this.getAspect(LocationAspect.ID))));
        this.setLabeller(ConceptAspect.ID, new CachingLabeller(cache, new ConceptLabeller(this.getAspect(ConceptAspect.ID))));
        this.setLabeller(UnitAspect.ID, new CachingLabeller(cache, new UnitLabeller(this.getAspect(UnitAspect.ID))));
        this.setLabeller(PeriodAspect.ID, new CachingLabeller(cache, new PeriodLabeller(this.getAspect(PeriodAspect.ID))));
        this.setLabeller(EntityAspect.ID, new CachingLabeller(cache, new EntityLabeller(this.getAspect(EntityAspect.ID))));
        this.setLabeller(SegmentAspect.ID, new CachingLabeller(cache, new SegmentLabeller(this.getAspect(SegmentAspect.ID))));
        this.setLabeller(ScenarioAspect.ID, new CachingLabeller(cache, new ScenarioLabeller(this.getAspect(ScenarioAspect.ID))));
        
    }

}
