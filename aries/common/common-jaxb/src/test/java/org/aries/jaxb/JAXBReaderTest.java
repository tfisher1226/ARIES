package org.aries.jaxb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class JAXBReaderTest {

	private JAXBReader fixture;
	
	
	@Before
	public void setUp() throws Exception {
		fixture = createFixture();
	}

	protected JAXBReader createFixture() {
		fixture = new JAXBReaderImpl();
		return fixture;
	}

	@After
	public void tearDown() throws Exception {
		fixture = null;
	}
	
	@Test
	public void testParseXML() throws Exception {
		
	}
	
}
