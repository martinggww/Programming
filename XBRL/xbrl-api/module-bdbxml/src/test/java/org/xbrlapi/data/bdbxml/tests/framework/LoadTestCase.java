package org.xbrlapi.data.bdbxml.tests.framework;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xbrlapi.utilities.XBRLException;

import com.sleepycat.db.DatabaseException;
import com.sleepycat.db.Environment;
import com.sleepycat.db.EnvironmentConfig;
import com.sleepycat.dbxml.XmlContainer;
import com.sleepycat.dbxml.XmlDocument;
import com.sleepycat.dbxml.XmlDocumentConfig;
import com.sleepycat.dbxml.XmlException;
import com.sleepycat.dbxml.XmlInputStream;
import com.sleepycat.dbxml.XmlManager;
import com.sleepycat.dbxml.XmlManagerConfig;
import com.sleepycat.dbxml.XmlUpdateContext;

/**
 * Designed to simplify the analysis of memory leaks leading 
 * to SEG faults that bring down the java runtime environment.
 */
public class LoadTestCase {

    protected static Logger logger = Logger.getLogger(LoadTestCase.class);  
    
    private final int iterations = 10;
    private String container = "container";
    private String location = null;
    private String prefix = "<a_node xmlns='http://www.xbrlapi.org/' xmlns:p1='http://www.xbrlapi.org/' xmlns:p2='http://www.xbrlapi.org/'>";
    private String suffix = "</a_node>";
    private SimpleStore store = null;
        
    @BeforeMethod
    protected void setUp() throws Exception {

        String configurationPropertiesIdentifier = "test.configuration.properties";
        Properties configuration = new Properties();
        File configurationFile = new File(System.getProperty("xbrlapi.test.configuration"));
        if (!configurationFile.exists()) {
            configurationFile = new File(configurationPropertiesIdentifier);
        }
        if (!configurationFile.exists()) {
            Assert.fail("The configuration File " + configurationFile + " does not exist.");
        }
        configuration.load(new FileInputStream(configurationFile));
        location = configuration.getProperty("bdbxml.store.location");
        store = new SimpleStore(location, container);

    }

    @AfterMethod
    protected void tearDown() throws Exception {
        long start = System.currentTimeMillis();
        logger.info("Starting store deletion at " + start);
        store.delete();
        logger.info("Store deletion took " + ((System.currentTimeMillis() - start) / 1000) + " seconds.");
    }

    @Test
    public final void testRepeatedDocumentAddition() {
        
        try {
            for (int i=1; i<=iterations; i++) {
                store.putDocument(prefix + "Document: " + i + suffix);
            }
        
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("The database insertions failed.");
        }
    }
    
    @Test
    public final void testLoadingDocumentsWithMultiplePrefixDeclarationsForTheOneNamespace() {
        
        try {

                String xml = prefix + "Document 1" + suffix;
                logger.info(xml);
                store.putDocument(xml);
                logger.info(store.getDocument("1"));
                AssertJUnit.assertTrue(store.getDocument("1").contains("xmlns="));
                AssertJUnit.assertTrue(store.getDocument("1").contains("xmlns:p1="));
                AssertJUnit.assertTrue(store.getDocument("1").contains("xmlns:p2="));
                
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("The database insertions failed.");
        }
    }    

    private class SimpleStore {

        private Environment environment = null;
        private XmlManagerConfig managerConfiguration = null;
        private XmlManager dataManager = null;
        private XmlUpdateContext xmlUpdateContext = null;
        private XmlContainer dataContainer = null;
        private XmlDocumentConfig documentConfiguration = null;

        public SimpleStore(String location, String container) 
        throws FileNotFoundException, DatabaseException, XmlException {
            EnvironmentConfig environmentConfiguration = new EnvironmentConfig();
            environmentConfiguration.setThreaded(true);
            environmentConfiguration.setAllowCreate(true);         // If the environment does not exist, create it.
            environmentConfiguration.setInitializeLocking(true);   // Turn on the locking subsystem.
            environmentConfiguration.setErrorStream(System.err);   // Capture error information in more detail.
            environmentConfiguration.setInitializeCache(true);
            environmentConfiguration.setCacheSize(1024 * 1024 * 500);
            environmentConfiguration.setInitializeLogging(true);   // Turn off the logging subsystem.
            environmentConfiguration.setTransactional(false);       // Turn on the transactional subsystem.
            environment = new Environment(new File(location), environmentConfiguration);
            
            managerConfiguration = new XmlManagerConfig();
            managerConfiguration.setAdoptEnvironment(true);
            managerConfiguration.setAllowExternalAccess(true);
        
            dataManager = new XmlManager(environment, managerConfiguration);
            
            if (dataManager.existsContainer(container) != 0) {
                dataContainer = dataManager.openContainer(container);
            } else {
                dataContainer = dataManager.createContainer(container);
            }

            xmlUpdateContext = dataManager.createUpdateContext();
            documentConfiguration = new XmlDocumentConfig();
            documentConfiguration.setWellFormedOnly(true);

        }

        public void close() throws XmlException {
            if (dataContainer != null) {
                dataContainer.sync();
                dataContainer.close();
                dataContainer.delete();
            }
            if (dataManager != null) {
                dataManager.delete();
            }
        }
        
        public void delete() throws XmlException, Exception {

            String dataContainerName = null;
            if (dataContainer != null) dataContainerName = dataContainer.getName();
            else throw new Exception("The data container to be deleted is null.");
        
            if (dataContainer != null) {
                dataContainer.sync();
                dataContainer.close();
                dataContainer.delete();
                dataContainer = null;
            }

            if (dataManager != null) {
                if (dataManager.existsContainer(dataContainerName) != 0) {
                    dataManager.removeContainer(dataContainerName);
                }
            } else {
                throw new Exception("The data manager was null when an attempt to delete a data store was attempted.");
            }

            // Close the data store.
            this.close();
        }
        
        private int index = 0;
        public void putDocument(String document) throws XmlException {
            InputStream inputStream = new ByteArrayInputStream(document.getBytes());
            XmlInputStream xmlInputStream = dataManager.createInputStream(inputStream);
            System.out.println("Adding document " + index++);
            dataContainer.putDocument(""+ index, xmlInputStream, xmlUpdateContext, documentConfiguration);
        }
        
        public synchronized String getDocument(String index) throws XBRLException {
            try {
                XmlDocument xmlDocument = dataContainer.getDocument(index);
                return xmlDocument.getContentAsString();
            } catch (XmlException e) { // Thrown if the document is not found
                throw new XBRLException("The fragment " + index + " could not be retrieved from the store.",e);
            }
        }        

    }

}

