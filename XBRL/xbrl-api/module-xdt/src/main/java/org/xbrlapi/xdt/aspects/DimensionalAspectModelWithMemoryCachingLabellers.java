package org.xbrlapi.xdt.aspects;

import org.apache.log4j.Logger;
import org.xbrlapi.aspects.Aspect;
import org.xbrlapi.aspects.AspectModel;
import org.xbrlapi.aspects.CachingLabeller;
import org.xbrlapi.aspects.ConceptAspect;
import org.xbrlapi.aspects.ConceptLabeller;
import org.xbrlapi.aspects.EntityAspect;
import org.xbrlapi.aspects.EntityLabeller;
import org.xbrlapi.aspects.LabelCache;
import org.xbrlapi.aspects.LocationAspect;
import org.xbrlapi.aspects.LocationLabeller;
import org.xbrlapi.aspects.MemoryLabelCache;
import org.xbrlapi.aspects.PeriodAspect;
import org.xbrlapi.aspects.PeriodLabeller;
import org.xbrlapi.aspects.UnitAspect;
import org.xbrlapi.aspects.UnitLabeller;
import org.xbrlapi.data.Store;
import org.xbrlapi.utilities.XBRLException;

/**
 * This is the same as the dimensional aspect model except that it 
 * also sets up the store-based caching aspect value labeller system
 * automatically as part of the constructor.
 * 
 * @author Geoff Shuetrim (geoff@galexy.net)
 */
public class DimensionalAspectModelWithMemoryCachingLabellers extends DimensionalAspectModelImpl implements DimensionalAspectModel {

    /**
     * 
     */
    private static final long serialVersionUID = 5698921583875791928L;
    private static final Logger logger = Logger.getLogger(DimensionalAspectModelWithMemoryCachingLabellers.class);
    
    /**
     * @param store The data store.
     * @throws XBRLException if a parameter is null.
     */
    public DimensionalAspectModelWithMemoryCachingLabellers(Store store) throws XBRLException {
        super(store);
    }

    /**
     * @see AspectModel#initialise()
     */
    public void initialise() throws XBRLException {
        super.initialise();
        LabelCache cache = new MemoryLabelCache();
        this.setLabeller(LocationAspect.ID, new CachingLabeller(cache, new LocationLabeller(this.getAspect(LocationAspect.ID))));
        this.setLabeller(ConceptAspect.ID, new CachingLabeller(cache, new ConceptLabeller(this.getAspect(ConceptAspect.ID))));
        this.setLabeller(UnitAspect.ID, new CachingLabeller(cache, new UnitLabeller(this.getAspect(UnitAspect.ID))));
        this.setLabeller(PeriodAspect.ID, new CachingLabeller(cache, new PeriodLabeller(this.getAspect(PeriodAspect.ID))));
        this.setLabeller(EntityAspect.ID, new CachingLabeller(cache, new EntityLabeller(this.getAspect(EntityAspect.ID))));
        this.setLabeller(SegmentRemainderAspect.ID, new CachingLabeller(cache, new SegmentRemainderLabeller(this.getAspect(SegmentRemainderAspect.ID))));
        this.setLabeller(ScenarioRemainderAspect.ID, new CachingLabeller(cache, new ScenarioRemainderLabeller(this.getAspect(ScenarioRemainderAspect.ID))));

        for (Aspect aspect: this.getTypedDimensionAspects()) {
            this.setLabeller(aspect.getId(), new CachingLabeller(cache, new TypedDimensionLabeller(this.getAspect(aspect.getId()))));
        }
        
        for (Aspect aspect: this.getExplicitDimensionAspects()) {
            this.setLabeller(aspect.getId(), new CachingLabeller(cache, new ExplicitDimensionLabeller(this.getAspect(aspect.getId()))));
        }        
        
    }       
    
}
