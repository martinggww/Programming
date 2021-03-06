package org.xbrlapi.aspects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.xbrlapi.EntityResource;
import org.xbrlapi.LabelResource;
import org.xbrlapi.impl.EntityResourceImpl;
import org.xbrlapi.utilities.XBRLException;

/**
 * <p>
 * A labeller for the entity aspect based upon generic XBRL labels.
 * </p>
 * 
 * <p>
 * This labeller does not make use of label caching systems.  Instead labels are obtained from
 * the XLink relationships from concepts to XBRL 2.1 and generic labels.
 * </p>
 * 
 * @author Geoff Shuetrim (geoff@galexy.net)
 */
public class EntityLabeller extends BaseLabeller implements Labeller {

    /**
     * 
     */
    private static final long serialVersionUID = -2263929608570454217L;

    /**
     * @param aspect The aspect to be a labeller for.
     */
    public EntityLabeller(Aspect aspect) throws XBRLException {
        super(aspect);
        if (! aspect.getId().equals(EntityAspect.ID)) throw new XBRLException("This labeller only works for the aspect: " + EntityAspect.ID);
    }

    /**
     * @see Labeller#getAspectLabelWithoutFallback(String, String, String)
     */
    @Override
    public String getAspectLabelWithoutFallback(String locale, String resourceRole, String linkRole) {
        return "entity";
    }    
    

    /**
     * @see Labeller#getAspectValueLabel(AspectValue, String, String, String)
     */
    public String getAspectValueLabel(AspectValue value, String locale, String resourceRole, String linkRole) {
        String label = this.getAspectValueLabelWithoutFallback(value, locale,resourceRole, linkRole);
        if (label != null) return label;
    	return super.getAspectValueLabelWithoutFallback(value, locale, resourceRole, linkRole);
    }
    
    /**
     * Returns a label based upon XBRL generic labels for XLink Entity Resources (a resource defined by XBRLAPI.ORG to 
     * support documentation of XBRL Entity identifiers).  A label for a matching entity resource (with a scheme and identifier
     * value matching the aspect value) is preferred over a label for an entity resource that is connected to a matching
     * entity resource via a chain of XLink equivalency relationships.
     * @see Labeller#getAspectValueLabelWithoutFallback(AspectValue, String, String, String)
     */
    public String getAspectValueLabelWithoutFallback(AspectValue value, String locale, String resourceRole, String linkRole) {

        try {
            EntityAspectValue v = (EntityAspectValue) value;
            String scheme = v.getScheme();
            String identifier = v.getValue();
            
            // Get any matching entity resources.
            String query = "for $root in #roots#[@type='"+EntityResourceImpl.class.getName()+"'] let $data:=$root/xbrlapi:data/* where $data/@scheme='"+scheme+"' and $data/@value='"+identifier+"' return $root";
            List<EntityResource> entityResources = getStore().<EntityResource>queryForXMLResources(query);
            for (EntityResource entityResource: entityResources) {
                List<LabelResource> labels = entityResource.getLabelsWithLanguageAndResourceRoleAndLinkRole(locale,resourceRole,linkRole);
                if (! labels.isEmpty()) return labels.get(0).getContentAsXHTMLString();
            }
            
            // Try for a label on an equivalent entity resource.
            Set<EntityResource> equivalentEntityResources = new HashSet<EntityResource>();
            for (EntityResource entityResource: entityResources) {
                equivalentEntityResources.addAll(entityResource.getEquivalents());
            }
            equivalentEntityResources.removeAll(entityResources);
            for (EntityResource entityResource: equivalentEntityResources) {
                List<LabelResource> labels = entityResource.getLabelsWithLanguageAndResourceRoleAndLinkRole(locale,resourceRole,linkRole);
                if (! labels.isEmpty()) return labels.get(0).getContentAsXHTMLString();
            }

        } catch (Throwable e) {
            logger.info(e.getMessage());
        }
        return null;

    }

}
