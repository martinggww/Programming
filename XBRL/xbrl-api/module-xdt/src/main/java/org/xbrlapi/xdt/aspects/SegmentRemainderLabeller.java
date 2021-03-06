package org.xbrlapi.xdt.aspects;


import org.xbrlapi.aspects.Aspect;
import org.xbrlapi.aspects.BaseLabeller;
import org.xbrlapi.aspects.Labeller;
import org.xbrlapi.utilities.XBRLException;

/**
 * <p>
 * A labeller for the segment remainder aspect.  This labeller ignores 
 * link role and resource role values.
 * </p>
 * 
 * @author Geoff Shuetrim (geoff@galexy.net)
 */
public class SegmentRemainderLabeller extends BaseLabeller implements Labeller {

    /**
     * 
     */
    private static final long serialVersionUID = -3690456459667430825L;

    /**
     * @param aspect The aspect to be a labeller for.
     */
    public SegmentRemainderLabeller(Aspect aspect) throws XBRLException {
        super(aspect);
        if (! aspect.getId().equals(SegmentRemainderAspect.ID)) throw new XBRLException("This labeller only works for the aspect: " + SegmentRemainderAspect.ID);
    }

    /**
     * @see Labeller#getAspectLabelWithoutFallback(String, String, String)
     */
    @Override
    public String getAspectLabelWithoutFallback(String locale, String resourceRole, String linkRole) {
        return "segment";
    }
}
