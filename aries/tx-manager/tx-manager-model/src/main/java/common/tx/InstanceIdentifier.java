package common.tx;

import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;

import org.w3c.dom.Element;


public class InstanceIdentifier {

	private String instanceIdentifier ;


	public InstanceIdentifier() {
	}

	public InstanceIdentifier(String instanceIdentifier) {
		this.instanceIdentifier = instanceIdentifier ;
	}

	/**
	 * Set the instance identifier of this element.
	 * @param instanceIdentifier The instance identifier of the element.
	 */
	public void setInstanceIdentifier(String instanceIdentifier) {
		this.instanceIdentifier = instanceIdentifier ;
	}

	/**
	 * Get the instance identifier of this element.
	 * @return The instance identifier of the element or null if not set.
	 */
	public String getInstanceIdentifier() {
		return instanceIdentifier ;
	}

	/**
	 * Is the configuration of this element valid?
	 * @return true if valid, false otherwise.
	 */
	public boolean isValid() {
		return (instanceIdentifier != null) && (instanceIdentifier.trim().length() > 0);
	}

	/**
	 * Get a string representation of this instance identifier.
	 * @return the string representation.
	 */
	public String toString() {
		return (instanceIdentifier != null ? instanceIdentifier : "") ;
	}

	/**
	 * Set the identifier on a W3C endpoint reference under construction.
	 * @param builder The endpoint reference builder.
	 * @param identifier The identifier.
	 */
	public static void setEndpointInstanceIdentifier(W3CEndpointReferenceBuilder builder, String identifier) {
		builder.referenceParameter(createInstanceIdentifierElement(identifier));
	}

	/**
	 * Set the identifier on a W3C endpoint reference under construction.
	 * @param builder The endpoint reference builder.
	 * @param instanceIdentifier The identifier.
	 */
	public static void setEndpointInstanceIdentifier(W3CEndpointReferenceBuilder builder, InstanceIdentifier instanceIdentifier) {
		builder.referenceParameter(createInstanceIdentifierElement(instanceIdentifier.getInstanceIdentifier())) ;
	}

	/**
	 * Set the identifier on a WS Addressing endpoint reference under construction.
	 * @param epReference The WS Addressing endpoint reference.
	 * @param instanceIdentifier The identifier.
	 */
//	public static void setEndpointInstanceIdentifier(MAPEndpoint epReference, InstanceIdentifier instanceIdentifier) {
//		setEndpointInstanceIdentifier(epReference, instanceIdentifier.getInstanceIdentifier());
//	}

	/**
	 * Set the identifier on a WS Addressing endpoint reference under construction.
	 * @param epReference The WS Addressing endpoint reference.
	 * @param instanceIdentifier The identifier string.
	 */
//	public static void setEndpointInstanceIdentifier(MAPEndpoint epReference, String instanceIdentifier) {
//		epReference.addReferenceParameter(createInstanceIdentifierElement(instanceIdentifier));
//	}

	/**
	 * a soap factory used to construct SOAPElement instances representing InstanceIdentifier instances
	 */
	private static SOAPFactory factory = createSoapFactory();

	/**
	 * a name for the WSArj Instance element
	 */
	private static Name WSARJ_ELEMENT_INSTANCE_NAME;

	/**
	 * Create a SOAPElement representing an InstanceIdentifier
	 * @param instanceIdentifier the identifier string of the InstanceIdentifier being represented
	 * @return a SOAPElement with the InstancreIdentifier QName as its element tag and a text node containing the
	 * suppliedidentifier string as its value
	 */

	public static Element createInstanceIdentifierElement(String instanceIdentifier) {
		try {
			SOAPElement element = factory.createElement(WSARJ_ELEMENT_INSTANCE_NAME);
			element.addNamespaceDeclaration(CoordinationConstants.WSARJ_PREFIX, CoordinationConstants.WSARJ_NAMESPACE);
			element.addTextNode(instanceIdentifier);
			return element;
		} catch (SOAPException se) {
			// TODO log error here (should never happen)
			return null;
		}
	}

	private static SOAPFactory createSoapFactory() {
		try {
			SOAPFactory factory = SOAPFactory.newInstance();
			Name name = factory.createName(CoordinationConstants.WSARJ_ELEMENT_INSTANCE_IDENTIFIER,
					CoordinationConstants.WSARJ_PREFIX,
					CoordinationConstants.WSARJ_NAMESPACE);
			WSARJ_ELEMENT_INSTANCE_NAME = name;
			return factory;
		} catch (SOAPException e) {
			// TODO log error here (should never happen)
		}
		return null;
	}

}

