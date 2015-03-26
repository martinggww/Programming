package org.xbrlapi.xdt.validation.tests;

import java.util.List;
import java.util.SortedSet;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.Concept;
import org.xbrlapi.Relationship;
import org.xbrlapi.utilities.XBRLException;
import org.xbrlapi.xdt.Dimension;
import org.xbrlapi.xdt.ExplicitDimension;
import org.xbrlapi.xdt.Hypercube;
import org.xbrlapi.xdt.HypercubeImpl;
import org.xbrlapi.xdt.tests.BaseTestCase;
import org.xbrlapi.xdt.validation.XDTAnalyser;

import com.google.common.collect.ListMultimap;

/**
 * Tests the identification of XDT fragments.
 * @author Geoffrey Shuetrim (geoff@galexy.net)
 */
public class HypercubeConjunctionAnalysisTestCase extends BaseTestCase {

    private final String INHERITANCE = "test.data.local.xdt.hypercube.inheritance";

    // The namespace of the concepts in the schema
    private final String InheritanceNS = "http://xbrlapi.org/test/xdt/003";
    private final String concept0 = "PrimaryParent";
    private final String concept1 = "FirstChildren";
    private final String concept2 = "SecondChildren";
    private final String concept3 = "ThirdChildren";
    
    private final XDTAnalyser xdtAnalyser = new XDTAnalyser();
    
	@BeforeMethod
    protected void setUp() throws Exception {
	    super.setUp();
	}

	@AfterMethod
    protected void tearDown() throws Exception {
		super.tearDown();
	}	

	private void processConcept(Concept concept) throws XBRLException {
        logger.info("Concept: " + concept.getTargetNamespace() + "#" + concept.getName());
        ListMultimap<String,Relationship> map = HypercubeImpl.getInheritedHasHypercubeRelationships(concept);
        for (String uri: map.keySet()) {
            List<Relationship> relationships = map.get(uri);
            for (Relationship relationship: relationships) {
            	String hypercubeDimensionTargetLinkRole = xdtAnalyser.getTargetRole(relationship);
                Hypercube cube = (Hypercube) relationship.getTarget();
                String cubeType = "open";
                if (xdtAnalyser.isClosedCube(relationship)) cubeType = "closed";
                String cubeContainer = "segment";
                if (xdtAnalyser.isScenarioCube(relationship)) cubeType = "scenario";
                logger.info("\t"+ cubeType + " " + cubeContainer + " hypercube: " + cube.getTargetNamespace() + "#" + cube.getName());
                SortedSet<Relationship> dimensionRelationships = cube.getRelationshipsToDimensions(hypercubeDimensionTargetLinkRole);
                for (Relationship dimensionRelationship: dimensionRelationships) {
                    Dimension dimension = (Dimension) dimensionRelationship.getTarget();
                    logger.info("\t\tDimension: " + dimension.getTargetNamespace() + "#" + dimension.getName());
                    if (dimension.isExplicitDimension()) {
                        ExplicitDimension explicitDimension = (ExplicitDimension) dimension;
                        String dimensionDomainTargetRole = xdtAnalyser.getTargetRole(dimensionRelationship);
                        SortedSet<Relationship> domainRelationships = explicitDimension.getRelationshipsToDomains(dimensionDomainTargetRole);
                        for (Relationship domainRelationship: domainRelationships) {
                            Concept domain = (Concept) domainRelationship.getTarget();
                            logger.info("\t\t\tDomain: " + domain.getTargetNamespace() + "#" + domain.getName());
                            processDomainMembers(domainRelationship,3);
                        }
                    }
                    
                }
            }
        }
	    
	}

	private void processDomainMembers(Relationship relationship, int indentation) throws XBRLException {
        String targetRole = xdtAnalyser.getTargetRole(relationship);
        Concept member = (Concept) relationship.getTarget();
        String indent = "";
        for (int i=1; i< indentation; i++) {
            indent += "\t";
        }
        if (indentation > 3) 
            logger.info(indent + "Member: " + member.getTargetNamespace() + "#" + member.getName());
        SortedSet<Relationship> relationships = xdtAnalyser.getDomainMemberRelationshipsFrom(targetRole, member);
        for (Relationship r: relationships) processDomainMembers(r, indentation + 1);
	}
	
	@Test
    public void testHypercubeConjunctions() {

		try {
	        loader.discover(this.getURI(INHERITANCE));

            // One of its own
	        Concept concept = store.getConcept(InheritanceNS,concept0);
            processConcept(concept);

            concept = store.getConcept(InheritanceNS,concept1);
            processConcept(concept);
            
            concept = store.getConcept(InheritanceNS,concept2);
            processConcept(concept);

            concept = store.getConcept(InheritanceNS,concept3);
            processConcept(concept);

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}


 
	
	
}
