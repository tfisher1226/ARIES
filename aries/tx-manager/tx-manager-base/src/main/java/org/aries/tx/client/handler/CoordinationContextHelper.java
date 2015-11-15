package org.aries.tx.client.handler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.tx.model.context.CoordinationContextType;


public class CoordinationContextHelper {

	/**
	 * Deserialise a coordination context from a DOM SOAP Header Element.
	 * @param headerElement The SOAP header element to deserialise.
	 * @return The coordination context.
	 * @throws javax.xml.stream.XMLStreamException for errors during parsing.
	 */
	public static CoordinationContextType deserialise(final Element headerElement) throws JAXBException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance("common.tx.model.context");
			Unmarshaller unmarshaller;
			unmarshaller = jaxbContext.createUnmarshaller();
			// the header element is a valid CoordinationContextType node so we can unpack it directly
			// using JAXB. n.b. we will see a mustUnderstand=1 in the otherAttributes which we probably don't
			// want but it will do no harm.
			CoordinationContextType coordinationContextType = unmarshaller.unmarshal(headerElement, CoordinationContextType.class).getValue();

			return coordinationContextType;
		} catch (JAXBException jaxbe) {
			return null;
		}
	}

	/**
	 * Deserialise a coordination context from a DOM SOAP Header Element.
	 * @param headerElement The SOAP header element to deserialise.
	 * @return The coordination context.
	 * @throws javax.xml.stream.XMLStreamException for errors during parsing.
	 */
	public static void serialise(CoordinationContextType  coordinationContextType, Element headerElement) throws JAXBException {
		// we would really like to just serialise the coordinationContextType direct. But the JAXB context will
		// only generate a Node and we need to add a SOAPHeaderElement. So, we cheat by serialising the
		// coordinationContextType into a header created by the caller, moving all its children into the
		// header element and then deleting it.
		try {
			//JAXBContext jaxbContext = JAXBContext.newInstance("org.oasis_open.docs.ws_tx.wscoor._2006._06");
			JAXBContext jaxbContext = JAXBContext.newInstance("common.tx.model.context");
			Marshaller marshaller;
			marshaller = jaxbContext.createMarshaller();
			marshaller.marshal(coordinationContextType, headerElement);
			Node element = headerElement.getFirstChild();
			NamedNodeMap map = element.getAttributes();
			// we also need to copy namespace declarations into the parent
			int l = map.getLength();
			for (int i = 0; i < l; i++) {
				Attr attr = (Attr)map.item(i);
				if (attr.getPrefix().equals("xmlns")) {
					headerElement.setAttribute(attr.getName(),attr.getValue());
				}
			}
			// copy the children
			NodeList children = element.getChildNodes();
			l = children.getLength();
			Node[] copy = new Node[l];
			// sigh. native creates a copy list while CXF gives us the actual child list so there is no simple way
			// to index into it from 0 to l which will work with both stacks. instead we copy the entries to an array
			// and use it to locate the element we want to delete and the append to the parent.
			for (int i = 0; i < l; i++) {
				copy[i] = children.item(i);
			}
			for (int i = 0; i < l; i++) {
				Node child = copy[i];
				element.removeChild(child);
				headerElement.appendChild(child);
			}
			headerElement.removeChild(element);
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}