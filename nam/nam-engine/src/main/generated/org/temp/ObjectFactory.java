
package org.temp;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.temp package. 
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

    private final static QName _ScheduleInfo_QNAME = new QName("http://manufacturing.org/xsd/purchase", "scheduleInfo");
    private final static QName _CustomerInfo_QNAME = new QName("http://manufacturing.org/xsd/purchase", "customerInfo");
    private final static QName _OrderFault_QNAME = new QName("http://manufacturing.org/xsd/purchase", "OrderFault");
    private final static QName _ShippingInfo_QNAME = new QName("http://manufacturing.org/xsd/purchase", "shippingInfo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.temp
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CustomerInfo }
     * 
     */
    public CustomerInfo createCustomerInfo() {
        return new CustomerInfo();
    }

    /**
     * Create an instance of {@link PurchaseOrder }
     * 
     */
    public PurchaseOrder createPurchaseOrder() {
        return new PurchaseOrder();
    }

    /**
     * Create an instance of {@link Purchase }
     * 
     */
    public Purchase createPurchase() {
        return new Purchase();
    }

    /**
     * Create an instance of {@link Invoice }
     * 
     */
    public Invoice createInvoice() {
        return new Invoice();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://manufacturing.org/xsd/purchase", name = "scheduleInfo")
    public JAXBElement<String> createScheduleInfo(String value) {
        return new JAXBElement<String>(_ScheduleInfo_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://manufacturing.org/xsd/purchase", name = "customerInfo")
    public JAXBElement<String> createCustomerInfo(String value) {
        return new JAXBElement<String>(_CustomerInfo_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://manufacturing.org/xsd/purchase", name = "OrderFault")
    public JAXBElement<String> createOrderFault(String value) {
        return new JAXBElement<String>(_OrderFault_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://manufacturing.org/xsd/purchase", name = "shippingInfo")
    public JAXBElement<String> createShippingInfo(String value) {
        return new JAXBElement<String>(_ShippingInfo_QNAME, String.class, null, value);
    }

}
