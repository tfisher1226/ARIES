package org.aries.message;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import junit.framework.TestCase;

import org.aries.Assert;
import org.aries.message.util.MessageUtil;
import org.aries.util.ResourceUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class MessageTest extends TestCase {

	private String schemaFile = "aries.person-1.0.xsd";
	
	private URL schema;
	
	
	@Before
	public void setUp() throws Exception {
		schema = ResourceUtil.getResource(schemaFile);
	}

	@After
	public void tearDown() throws Exception {
		schema = null;
	}

	@Ignore
	@Test
	public void testSerialization() throws Exception {
		Message unmarshalledMessage = new Message();
		MessageUtil.addPart(unmarshalledMessage, "key", "value");
		
		// Marshall
		JAXBContext context = JAXBContext.newInstance(unmarshalledMessage.getClass());
		Marshaller marshaller = context.createMarshaller();
		StringWriter writer = new StringWriter();
		marshaller.marshal(unmarshalledMessage, writer);
		String outString = writer.toString();
		assertTrue(outString.contains("<message"));
		assertTrue(outString.contains("</message>"));

		// Unmarshall
		context = JAXBContext.newInstance(Message.class, Message.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		StringReader reader = new StringReader(outString);
		Message marshalledMessage = (Message) unmarshaller.unmarshal(reader);
		Assert.isFalse(unmarshalledMessage == marshalledMessage);
		Assert.isTrue(MessageUtil.equals(unmarshalledMessage, marshalledMessage));
	}

}
