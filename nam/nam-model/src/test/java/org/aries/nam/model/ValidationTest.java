package org.aries.nam.model;

import java.io.IOException;
import java.net.URL;

import org.apache.xerces.parsers.SAXParser;
import org.aries.util.ResourceUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;


@RunWith(MockitoJUnitRunner.class)
public class ValidationTest {

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

		String SchemaUrl = "c:/workspace/ARIES/nam-model/source/main/resources/schema/model-0.0.1.xsd";
		//String SchemaUrl = "file://c:/schema/catalog.xsd";
		String XmlDocumentUrl = "/schema/bookshop.aries";
		//String XmlDocumentUrl = "file://c:/catalog/catalog.xml";

		SchemaValidator validator = new SchemaValidator(); 
		validator.validateSchema(SchemaUrl, XmlDocumentUrl);  
	}

	public class SchemaValidator {
		public void validateSchema(String SchemaUrl, String XmlDocumentUrl) {
			//To validate with a SAXParser, create a SAXParser. The SAXParser class is a subclass of the XMLParser class.
			SAXParser parser = new SAXParser();

			try {
				//Set the validation feature to true to report validation errors. If the validation feature is set to true, the XML document should specify a XML schema or a DTD.
				parser.setFeature("http://xml.org/sax/features/validation",
						true); 

				//Set the validation/schema feature to true to report validation errors against a schema.
				parser.setFeature("http://apache.org/xml/features/validation/schema", 
						true);

				//Set the validation/schema-full-checking feature to true to enable full schema, grammar-constraint checking.
				parser.setFeature("http://apache.org/xml/features/validation/schema-full-checking",
						true); 

				//parser.setProperty(
				//		"http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",
				//		SchemaUrl);

				parser.setProperty(
						"http://apache.org/xml/properties/schema/external-schemaLocation",
						SchemaUrl);

				ExceptionHandler handler = createHandler();
				parser.setErrorHandler(handler);  

				URL resource = ResourceUtil.getResource(XmlDocumentUrl);
				String file = resource.getFile();
				//parser.parse("schema/bookshop2.aries");
				parser.parse(file);

				if (handler.validationWarning == true)
					System.out.println("XML Document has Warning: "+handler.validationWarning+""+handler.saxWarningException.getMessage());
				if (handler.validationError == true)
					System.out.println("XML Document has Error: "+handler.validationError+""+handler.saxErrorException.getMessage());
				else 
					System.out.println("XML Document is valid");

			} catch (IOException ioe){   
				System.out.println("IOException"+ioe.getMessage());

			} catch (SAXException e) { 
				System.out.println("SAXException"+e.getMessage());    
			}     
		}

		public ExceptionHandler createHandler() {
			ExceptionHandler handler = new ExceptionHandler();
			return handler;
		}

		private class ExceptionHandler extends DefaultHandler {

			public boolean validationError = false;  

			public boolean validationWarning = false;  

			public SAXParseException saxErrorException = null;

			public SAXParseException saxWarningException = null;


			public void error(SAXParseException exception) throws SAXException {
				validationError = true;
				saxErrorException = exception;
			}     

			public void fatalError(SAXParseException exception) throws SAXException {
				validationError = true;	    
				saxErrorException = exception;	     
			}

			public void warning(SAXParseException exception) throws SAXException {
				validationWarning = true;	    
				saxWarningException = exception;	     
			}
		}

	}
}
