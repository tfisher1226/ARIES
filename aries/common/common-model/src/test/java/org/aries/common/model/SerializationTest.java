package org.aries.common.model;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import junit.framework.TestCase;

import org.aries.Assert;
import org.aries.common.EmailAddress;
import org.aries.util.ResourceUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class SerializationTest extends TestCase {

	private String schemaFile = "common-1.0.xsd";
	
	private URL schema;
	
	
	@Before
	public void setUp() throws Exception {
		schema = ResourceUtil.getResource(schemaFile);
	}

	@After
	public void tearDown() throws Exception {
		schema = null;
	}

	protected EmailAddress createFixture() throws Exception {
		EmailAddress emailAddress = new EmailAddress();
		emailAddress.setUrl("tfisher@aaa.com");
		emailAddress.setFirstName("Tom");
		emailAddress.setLastName("Fisher");
		return emailAddress;
	}
	
	@Test
	@Ignore
	public void testSerialization() throws Exception {
		Root unmarshalledRoot = new Root();
		EmailAddress unmarshalledObject = createFixture();
		unmarshalledRoot.setEmailAddress(unmarshalledObject);
		
		// Marshall
		JAXBContext context = JAXBContext.newInstance(Root.class, EmailAddress.class);
		Marshaller marshaller = context.createMarshaller();
		StringWriter writer = new StringWriter();
		marshaller.marshal(unmarshalledRoot, writer);
		String outString = writer.toString();
		assertTrue(outString.contains("<emailAddress"));

		// Unmarshall
		context = JAXBContext.newInstance(Root.class, EmailAddress.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		StringReader reader = new StringReader(outString);
		Root marshalledRoot = (Root) unmarshaller.unmarshal(reader);
		Assert.isTrue(unmarshalledRoot.equals(marshalledRoot));
		assertEquals(unmarshalledRoot, marshalledRoot);
	}
	
	@XmlType
	@XmlRootElement
	static class Root {

		@XmlElement(name = "emailAddress")
		private EmailAddress emailAddress;

		public void setEmailAddress(EmailAddress emailAddress) {
			this.emailAddress = emailAddress;
		}
		
		@Override
		public boolean equals(Object object) {
			Root other = (Root) object;
			return emailAddress.equals(other.emailAddress);
		}
	}
	
}
