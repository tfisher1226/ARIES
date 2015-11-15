package org.aries.nam.model;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;


@RunWith(MockitoJUnitRunner.class)
public class ValidationJAXPTest {

	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		//		fixture = null;
	}

	//	protected ShipBooksTransactor createFixture(String correlationId) throws Exception {
	//		fixture = new ShipBooksTransactor(correlationId);
	//		return fixture;
	//	}

	@Test
	@Ignore
	public void testValidate() throws Exception {

		String SchemaUrl = "/schema/model-0.0.1.xsd";
		//String SchemaUrl = "file://c:/schema/catalog.xsd";
		String XmlDocumentUrl = "/schema/bookshop.aries";
		//String XmlDocumentUrl = "file://c:/catalog/catalog.xml";

		System.setProperty(
				"javax.xml.parsers.DocumentBuilderFactory",
				"org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");

		DocumentBuilderFactory factory =
			DocumentBuilderFactory.newInstance();

		factory.setNamespaceAware(true);
		factory.setValidating(true);

		factory.setAttribute(
				"http://java.sun.com/xml/jaxp/properties/schemaLanguage",
				"http://www.w3.org/2001/XMLSchema");
		
		factory.setAttribute(
				"http://java.sun.com/xml/jaxp/properties/schemaSource",
				SchemaUrl);

		DocumentBuilder builder = factory.newDocumentBuilder();
		Validator handler = new Validator();
		builder.setErrorHandler(handler); 

		Document parse = builder.parse(XmlDocumentUrl);

	}


	private class Validator extends DefaultHandler {

		public boolean validationError = false;  

		public SAXParseException saxParseException = null;


		public void error(SAXParseException exception) throws SAXException {
			validationError = true;
			saxParseException = exception;
		}     

		public void fatalError(SAXParseException exception) throws SAXException {
			validationError = true;	    
			saxParseException=exception;	     
		}

		public void warning(SAXParseException exception) throws SAXException {

		}
	}

}
