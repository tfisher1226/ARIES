package org.manufacturing.wsdl.purchase;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2012-04-24T14:25:34.605-07:00
 * Generated source version: 2.3.3
 * 
 */
 
@WebService(targetNamespace = "http://manufacturing.org/wsdl/purchase", name = "invoiceCallbackPT")
@XmlSeeAlso({org.manufacturing.xsd.purchase.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface InvoiceCallbackPT {

    @Oneway
    @WebMethod
    public void sendInvoice(
        @WebParam(partName = "IVC", name = "IVC", targetNamespace = "")
        org.manufacturing.xsd.purchase.Invoice ivc
    );
}
