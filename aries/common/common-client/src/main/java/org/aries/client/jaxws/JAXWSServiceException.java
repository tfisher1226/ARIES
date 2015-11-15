package org.aries.client.jaxws;

import javax.xml.ws.WebFault;


@WebFault(name = "JAXWSServiceException", targetNamespace = "http://jaxws.aries.org")
public class JAXWSServiceException extends java.lang.Exception {

	private static final long serialVersionUID = 1000L;

	
	/** Java type that goes as soapenv:Fault detail element. */
    private org.aries.client.jaxws.ServiceException faultInfo;

    /**
     * Creates new instance of <code>Exception_Exception</code>
     * @param message
     * @param faultInfo
     */
    public JAXWSServiceException(String message, org.aries.client.jaxws.ServiceException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * Creates new instance of <code>Exception_Exception</code>
     * @param message
     * @param faultInfo
     * @param cause
     */
    public JAXWSServiceException(String message, org.aries.client.jaxws.ServiceException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    public org.aries.client.jaxws.ServiceException getFaultInfo() {
        return faultInfo;
    }

}
