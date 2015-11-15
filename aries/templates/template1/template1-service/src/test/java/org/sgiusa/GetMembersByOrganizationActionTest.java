package org.sgiusa;

import junit.framework.TestCase;

import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBReaderImpl;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.jaxb.JAXBWriterImpl;
import org.aries.message.init.Bootstrap;
import org.aries.model.util.TypeMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class GetMembersByOrganizationActionTest extends TestCase {

	private String serviceId = "serviceId";
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	protected void initializeModel() throws Exception {
		JAXBWriter jaxbWriter = new JAXBWriterImpl();
		JAXBReader jaxbReader = new JAXBReaderImpl();
		JAXBSessionCache jaxbSessionCache = new JAXBSessionCache("test");
		jaxbSessionCache.addWriter(serviceId, jaxbWriter);
		jaxbSessionCache.addReader(serviceId, jaxbReader);
		jaxbSessionCache.addSchema("/schema/aries-common-1.0.xsd", org.aries.common.ObjectFactory.class);
		jaxbSessionCache.addSchema("/schema/aries-message-1.0.xsd", org.aries.message.ObjectFactory.class);
		jaxbReader.setSchema(jaxbSessionCache.getSchema());
		jaxbWriter.setSchema(jaxbSessionCache.getSchema());
		jaxbReader.setPackagesToLoad(jaxbSessionCache.getPackagesToLoad());
		jaxbWriter.setPackagesToLoad(jaxbSessionCache.getPackagesToLoad());
		//jaxbReader.setClasses(jaxbSessionCache.getClassesToLoad());
		//jaxbWriter.setClasses(jaxbSessionCache.getClassesToLoad());
		jaxbWriter.initialize();
		jaxbReader.initialize();
	}
	
	@Test
	public void testProcess() throws Exception {
		
	}
    
}
