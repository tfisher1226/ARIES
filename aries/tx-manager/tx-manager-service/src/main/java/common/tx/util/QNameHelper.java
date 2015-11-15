package common.tx.util;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;


public class QNameHelper {

	/**
	 * Return a qualified representation of the qname.
	 * @param qname The qname.
	 * @return The qualified name.
	 */
	public static String toQualifiedName(final QName qname) {
		return toQualifiedName(qname.getPrefix(), qname.getLocalPart());
	}

	/**
	 * Return a qualified representation of the prefix and local name.
	 * @param prefix The prefix.
	 * @param localName The local name.
	 * @return The qualified name.
	 */
	public static String toQualifiedName(String prefix, String localName) {
		if (!StringUtils.isEmpty(prefix))
			return prefix + ":" + localName;
		return localName;
	}

	/**
	 * Return the qname represented by the qualified name.
	 * @param namespaceContext The namespace context.
	 * @param qualifiedName The qualified name.
	 * @return The qname.
	 */
	public static QName toQName(NamespaceContext namespaceContext, String qualifiedName) {
		final int index = qualifiedName.indexOf(':');
		if (index == -1) {
			return new QName(qualifiedName);
		} else {
			String prefix = qualifiedName.substring(0, index);
			String localName = qualifiedName.substring(index+1);
			String namespaceURI = getNormalisedValue(namespaceContext.getNamespaceURI(prefix));
			return new QName(namespaceURI, localName, prefix);
		}
	}

	/**
	 * Get the normalised value of the string.
	 * @param value The string value.
	 * @return The normalised value.
	 */
	public static String getNormalisedValue(final String value) {
		if (value != null)
			return value;
		return "";
	}
}
