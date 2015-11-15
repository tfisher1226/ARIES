
package com.example.loan_approval.xsd.error_messages;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.example.loan_approval.xsd.error_messages package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Integer_QNAME = new QName("http://example.com/loan-approval/xsd/error-messages/", "integer");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.example.loan_approval.xsd.error_messages
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example.com/loan-approval/xsd/error-messages/", name = "integer")
    public JAXBElement<BigInteger> createInteger(BigInteger value) {
        return new JAXBElement<BigInteger>(_Integer_QNAME, BigInteger.class, null, value);
    }

}
