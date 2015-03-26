package org.xbrlapi.data.bdbxml.tests.query;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.exist.util.FileUtils;
import org.xbrlapi.Concept;
import org.xbrlapi.Instance;
import org.xbrlapi.Relationship;
import org.xbrlapi.aspects.Aspect;
import org.xbrlapi.aspects.PeriodAspect;
import org.xbrlapi.data.bdbxml.examples.utilities.BaseUtilityExample;
import org.xbrlapi.networks.AnalyserImpl;
import org.xbrlapi.networks.Storer;
import org.xbrlapi.networks.StorerImpl;
import org.xbrlapi.utilities.Constants;
import org.xbrlapi.utilities.XBRLException;
import org.xbrlapi.xdt.aspects.DimensionalAspectModel;
import org.xbrlapi.xdt.aspects.DimensionalAspectModelWithStoreCachingLabellers;

public class TestFactRetrievalFromConceptWalk extends BaseUtilityExample {
	public TestFactRetrievalFromConceptWalk(String[] args) throws XBRLException,
			URISyntaxException, IOException {
		
		ClearDatabase();
		
		argumentDocumentation = addArgumentDocumentation();
		parseArguments(args);
		String message = setUp();

		if (message.equals("")) {
			try {
				//FileListProvider fap = new FileListProvider();
				//List<URI> uris = fap.readLines("/home/matt/XBRLProject/database/bigtestfile.txt");
				List<URI> uris = new ArrayList<URI>();
				uris.add(new URI("http://www.sec.gov/Archives/edgar/data/320187/000119312510161874/nke-20100531.xml"));
				uris.add(new URI("http://www.sec.gov/Archives/edgar/data/789019/000119312511200680/msft-20110630.xml"));
				int uricount = 1;
				
				for (URI uri : uris) {
					try {
						System.out.println("Processing " + uri + ": Number "
								+ uricount + " out of " + uris.size());
						
						uricount++;

						Storer storer = new StorerImpl(store);
						loader.discover(uri);
						//URI preuri = new URI(uri.toString().replace(".xml", "_pre.xml"));
						storer.storeAllRelationships();
						store.setAnalyser(new AnalyserImpl(store)); // Crucial
																	// for using
																	// relationships
																	// and
																	// network
																	// roots!!!

						System.out.println("Starting to try...");

						List<Instance> instances = store
								.getFragmentsFromDocument(uri, "Instance");
						if (instances.size() > 1)
							throw new XBRLException(
									"There are duplicates of the target instance in the data store.  The store appears to be corrupted.");
						if (instances.size() == 0)
							continue;
						Instance instance = instances.get(0);
						
						String uristring = uri.toString();
						String[] uriarray = uristring.split("/");
						String filename = uriarray[uriarray.length - 1];
						String[] filearray = filename.split("\\.");
						filename = filearray[0];
						
						System.out.println("Building Aspect Model...");
						DimensionalAspectModel aspectModel = new DimensionalAspectModelWithStoreCachingLabellers(
								store);
						aspectModel.initialise();
						Aspect periodAspect = aspectModel.getAspect(PeriodAspect.ID);
						aspectModel.addAspect("column", periodAspect);

						String arcrole = Constants.PresentationArcrole;
						Set<String> linkroles = store.getLinkRoles(arcrole);
						
						System.out.println("Getting presentation relationships...");
						
						String linktype = "\""+"pre"+"\"";
						int relationshipcount = 0;
						for (String linkrole : linkroles) {
							Set<Concept> roots = store
							.<Concept> getNetworkRoots(linkrole,
									arcrole);
							for (Concept root : roots) {
									relationshipcount = RecurseConcepts(aspectModel, root, linkrole, arcrole, linktype, filename, instance, relationshipcount);
							}
							roots.clear();
						}
						
						DeleteDocumentsAndRelationships(uri, storer);

					} catch (Exception d) {
						System.out.println(d.getMessage());
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			tearDown();
		}
	}
	
	private int RecurseConcepts(DimensionalAspectModel aspectModel, Concept concept, String linkrole, String arcrole, String linktype, String filename, Instance instance, int relationshipcount)
			throws XBRLException {
		
		//Getting zero fact counts for most concepts.  Need to investigate.
		String conceptid = "\""+concept.getTargetNamespace()+":"+concept.getName()+"\"";
		long factcount = concept.getFactCount();
		if (factcount > 0){System.out.println("Parent Concept "+conceptid+" fact count: "+factcount);}
			
		SortedSet<Relationship> relationships = store.getRelationshipsFrom(
				concept.getIndex(), linkrole, arcrole);
		for (Relationship relationship : relationships) {
			Concept subconcept = relationship.getTarget();
			String subconceptid = "\""+subconcept.getTargetNamespace()+":"+subconcept.getName()+"\"";
			long subfactcount = subconcept.getFactCount();
			if(subfactcount>0){System.out.println("Child Concept "+subconceptid+" fact count: "+subfactcount);}
			relationshipcount++;
			relationshipcount = RecurseConcepts(aspectModel, subconcept, linkrole, arcrole, linktype, filename, instance, relationshipcount);
		}
		return(relationshipcount);
	}
	
	private void ClearDatabase() throws IOException {
		List<String> filelist = new ArrayList<String>();
		String databasepath = "/home/matt/XBRLProject/database/";
		filelist.add(databasepath + "__db.001");
		filelist.add(databasepath + "__db.002");
		filelist.add(databasepath + "__db.003");
		filelist.add(databasepath + "__db.004");
		filelist.add(databasepath + "__db.005");
		filelist.add(databasepath + "__db.006");
		filelist.add(databasepath + "log.0000000001");
		filelist.add(databasepath + "tempdata");
		
		for (String listitem : filelist) {
			File f = new File(listitem);
			if (f.exists() && f.isFile()){
				f.delete();
				System.out.println("Deleted file: " + listitem);
			}
		}
		
		FileUtils.delete(new File("/home/matt/XBRLProject/caches/temp_cache/http"));
		System.out.println("Deleted cache");
	}
	
	private void DeleteDocumentsAndRelationships(URI uri, Storer storer) throws XBRLException, URISyntaxException {
		System.out.println("Deleting document URIs...");
		Set<URI> storeuris = new HashSet<URI>();
		storeuris.add(uri);
		URI calcuri = new URI(uri.toString().replace(".xml",
				"_cal.xml"));
		storeuris.add(calcuri);
		URI defuri = new URI(uri.toString().replace(".xml",
				"_def.xml"));
		storeuris.add(defuri);
		URI laburi = new URI(uri.toString().replace(".xml",
				"_lab.xml"));
		storeuris.add(laburi);
		URI preuri = new URI(uri.toString().replace(".xml",
				"_pre.xml"));
		storeuris.add(preuri);
		URI xsduri = new URI(uri.toString().replace(".xml",
				".xsd"));
		storeuris.add(xsduri);

		for (URI storeuri : storeuris) {
			try {
				store.deleteRelatedDocuments(storeuri);
			} catch (Exception d) {
				System.out
						.println("Could not delete document: "
								+ storeuri);
			}
		}
		storer.deleteRelationships();
	}

	public static void main(String[] args) throws IOException,
			URISyntaxException, XBRLException {
		TestFactRetrievalFromConceptWalk utility = new TestFactRetrievalFromConceptWalk(args);
	}
}