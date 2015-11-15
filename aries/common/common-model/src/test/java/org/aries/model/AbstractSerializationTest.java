package org.aries.model;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBReaderImpl;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.jaxb.JAXBWriterImpl;
import org.junit.After;
import org.junit.Before;


public abstract class AbstractSerializationTest extends TestCase {

	protected Log log = LogFactory.getLog(getClass());

	private JAXBReader jaxbReader;
	
	private JAXBWriter jaxbWriter;

	
	public AbstractSerializationTest() {
		//nothing for now
	}
	
	@Before
	public void setUp() throws Exception {
		createJAXBContext();
		super.setUp();
	}

	protected void createJAXBContext() throws Exception {
		jaxbReader = new JAXBReaderImpl();
		jaxbWriter = new JAXBWriterImpl();
		JAXBSessionCache jaxbSessionCache = new JAXBSessionCache("test");
		jaxbSessionCache.addReader("default", jaxbReader);
		jaxbSessionCache.addWriter("default", jaxbWriter);
		jaxbSessionCache.addSchema("/schema/aries-common-1.0.xsd", org.aries.common.ObjectFactory.class);
		//jaxbSessionCache.addSchema("/schema/aries-message-1.0.xsd", org.aries.message.ObjectFactory.class);
		jaxbReader.setSchema(jaxbSessionCache.getSchema());
		jaxbWriter.setSchema(jaxbSessionCache.getSchema());
		jaxbReader.setPackagesToLoad(jaxbSessionCache.getPackagesToLoad());
		jaxbWriter.setPackagesToLoad(jaxbSessionCache.getPackagesToLoad());
		//jaxbReader.setClasses(jaxbSessionCache.getClassesToLoad());
		//jaxbWriter.setClasses(jaxbSessionCache.getClassesToLoad());
		jaxbReader.initialize();
		jaxbWriter.initialize();
	}

	@After
	public void tearDown() throws Exception {
		jaxbReader = null;
		jaxbWriter = null;
		super.tearDown();
	}
	
}
