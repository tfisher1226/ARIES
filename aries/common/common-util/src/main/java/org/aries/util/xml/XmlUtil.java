package org.aries.util.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.aries.util.IOUtil;
import org.xml.sax.SAXException;


public class XmlUtil {

	// new line character
	public static final String NL = System.getProperty("line.separator");
	
	// XSLT to format printing of raw XML - no spaces
	public static final String XSLT_RAW = 
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?><xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\"><xsl:output method=\"xml\" encoding=\"UTF-8\" indent=\"no\"/><xsl:strip-space elements=\"*\"/><xsl:template match=\"*\"><xsl:copy><xsl:copy-of select=\"@*\"/><xsl:apply-templates/></xsl:copy></xsl:template></xsl:stylesheet>";
	
	public static final String XSLT_READABLE = 
		"<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:output method=\"xml\"/><xsl:param name=\"indent-increment\" select=\"'  '\" /><xsl:template match=\"*\"><xsl:param name=\"indent\" select=\"'&#xA;'\"/><xsl:value-of select=\"$indent\"/><xsl:copy><xsl:copy-of select=\"@*\" /><xsl:apply-templates><xsl:with-param name=\"indent\" select=\"concat($indent, $indent-increment)\"/></xsl:apply-templates><xsl:if test=\"*\"><xsl:value-of select=\"$indent\"/></xsl:if></xsl:copy></xsl:template><xsl:template match=\"comment()|processing-instruction()\"><xsl:copy /></xsl:template><xsl:template match=\"text()[normalize-space(.)='']\"/></xsl:stylesheet>";
	
	// schema for WSDL 1.1 referenced by WS-I BP 1.1
	public static final String URL_XSD_WSIBP11_WSDL11 = "http://ws-i.org/profiles/basic/1.1/wsdl-2004-08-24.xsd";
	
	// schema for WSDL 1.1 SOAP 1.1 Binding referenced by WS-I BP 1.1
	public static final String URL_XSD_WSIBP11_WSDL11SOAP11 = "http://ws-i.org/profiles/basic/1.1/wsdlsoap-2004-08-24.xsd";
	
	// schema for SOAP 1.1 Envelope referenced by WS-I BP 1.1
	public static final String URL_XSD_WSIBP11_SOAP11 = "http://ws-i.org/profiles/basic/1.1/soap-envelope-2004-01-21.xsd";
	
	// Schema for validating WSDL 1.1 / SOAP 1.1 Binding
	private static Schema wsdl11soap11;

	private static Transformer rawXformer;

	private static Transformer readableXformer;
	
	private static Transformer simpleXformer;

	
	static {
		try {
			// transformers are *not* thread safe
			// TODO make thread safe
			TransformerFactory transFac = 
				TransformerFactory.newInstance();
			StreamSource rawSS = 
				new StreamSource(new ByteArrayInputStream
						(XSLT_RAW.getBytes()));
			StreamSource readableSS = 
				new StreamSource(new ByteArrayInputStream
						(XSLT_READABLE.getBytes()));
			rawXformer = transFac.newTransformer(rawSS);
			readableXformer = transFac.newTransformer(readableSS);
			simpleXformer = transFac.newTransformer();
		} catch (Exception e) {
			System.out.println(IOUtil.stackTrace(e));
			//System.exit(1);
		}
	}

	  
	public static Schema getSchemaWSDL11SOAP11() {
		if (wsdl11soap11 == null ) {
			SchemaFactory fac = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			StreamSource[] schemas = {new StreamSource(URL_XSD_WSIBP11_WSDL11),
					new StreamSource(URL_XSD_WSIBP11_WSDL11SOAP11) };
			try {
				wsdl11soap11 =  fac.newSchema(schemas);
			} catch (SAXException e) {
				throw new RuntimeException("Failed to create a validating schema for WSDL 1.1 / SOAP 1.1", e);
			}
		}
		return wsdl11soap11;

	}

	public static synchronized String formatXml(String xml) {

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			readableXformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(output));
		} catch (TransformerException e) {
			throw new RuntimeException("Failed to format XML", e);
		}
		return output.toString();

	}

}
