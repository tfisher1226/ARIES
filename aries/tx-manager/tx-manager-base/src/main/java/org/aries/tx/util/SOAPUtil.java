package org.aries.tx.util;

import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;

import javax.xml.soap.SOAPElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;


public class SOAPUtil {

	/**
	 * The XML input factory.
	 */
	private static final XMLInputFactory XML_INPUT_FACTORY = getXMLInputFactory();
	/**
	 * The XML output factory.
	 */
	private static final XMLOutputFactory XML_OUTPUT_FACTORY = XMLOutputFactory.newInstance();


	/**
	 * Get the XML stream reader.
	 * @param reader The input reader.
	 * @return The XML stream reader.
	 * @throws XMLStreamException For errors obtaining an XML stream reader.
	 */
	public static XMLStreamReader getXMLStreamReader(final Reader reader) throws XMLStreamException {
		return XML_INPUT_FACTORY.createXMLStreamReader(reader);
	}

	/**
	 * Get the XML stream writer.
	 * @param writer The output writer.
	 * @return The XML stream writer.
	 * @throws XMLStreamException For errors obtaining an XML stream writer.
	 */
	public static XMLStreamWriter getXMLStreamWriter(final Writer writer) throws XMLStreamException {
		return XML_OUTPUT_FACTORY.createXMLStreamWriter(writer);
	}

	/**
	 * Create the XML input factory.
	 * @return The XML input factory.
	 */
	private static XMLInputFactory getXMLInputFactory() {
		final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		xmlInputFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
		return xmlInputFactory;
	}

	/**
	 * Get an iterator containing just child elements.
	 * @param soapElement The parent soap element.
	 * @return The iterator of SOAPElements.
	 */
	@SuppressWarnings("unchecked")
	public static Iterator<Object> getChildElements(SOAPElement soapElement) {
		return new SOAPElementIterator(soapElement.getChildElements());
	}

}
