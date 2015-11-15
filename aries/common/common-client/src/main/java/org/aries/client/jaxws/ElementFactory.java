package org.aries.client.jaxws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * Contains factory methods for each Java content interface 
 * and Java element interface of the <code>org.aries.client</code> package. 
 * <p>
 * An ObjectFactory allows you to programatically construct new instances 
 * of the Java representation for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces and classes representing 
 * the binding of schema type definitions, element declarations and model 
 * groups.  Factory methods for each of these are provided below.
 * 
 */
@XmlRegistry
public class ElementFactory {

    private final static QName _InvokeRequest_QNAME = new QName("http://jaxws.aries.org", "invokeRequest");
    
    private final static QName _InvokeResponse_QNAME = new QName("http://jaxws.aries.org", "invokeResponse");
    
    private final static QName _ServiceException_QNAME = new QName("http://jaxws.aries.org", "ServiceException");

    /**
     * Creates new ObjectFactory that can be used to create new instances 
     * of schema derived classes for <code>org.aries.client</code>
     * 
     */
    public ElementFactory() {
    	//does nothing
    }

    /**
     * Creates an instance of {@link ServiceException}.
     * 
     */
    public ServiceException createServiceException() {
        return new ServiceException();
    }

    /**
     * Creates an instance of {@link InvokeRequest}.
     * 
     */
    public InvokeRequest createInvokeRequest() {
        return new InvokeRequest();
    }

    /**
     * Creates an instance of {@link InvokeResponse}.
     * 
     */
    public InvokeResponse createInvokeResponse() {
        return new InvokeResponse();
    }

    /**
     * Creates an instance of {@link JAXBElement}{@code <}{@link InvokeResponse }{@code >}}.
     * 
     */
    @XmlElementDecl(namespace = "http://jaxws.aries.org", name = "invokeResponse")
    public JAXBElement<InvokeResponse> createInvokeResponse(InvokeResponse value) {
        JAXBElement<InvokeResponse> element = new JAXBElement<InvokeResponse>(_InvokeResponse_QNAME, InvokeResponse.class, null, value);
		return element;
    }

    /**
     * Creates an instance of {@link JAXBElement}{@code <}{@link InvokeRequest }{@code >}}.
     * 
     */
    @XmlElementDecl(namespace = "http://jaxws.aries.org", name = "invoke")
    public JAXBElement<InvokeRequest> createInvoke(InvokeRequest value) {
        JAXBElement<InvokeRequest> element = new JAXBElement<InvokeRequest>(_InvokeRequest_QNAME, InvokeRequest.class, null, value);
		return element;
    }

    /**
     * Creates an instance of {@link JAXBElement }{@code <}{@link ServiceException }{@code >}}.
     * 
     */
    @XmlElementDecl(namespace = "http://jaxws.aries.org", name = "Exception")
    public JAXBElement<ServiceException> createServiceException(ServiceException value) {
        JAXBElement<ServiceException> element = new JAXBElement<ServiceException>(_ServiceException_QNAME, ServiceException.class, null, value);
		return element;
    }

}
