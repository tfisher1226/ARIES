package org.manufacturing.wsdl.purchase;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2012-04-24T14:25:34.553-07:00
 * Generated source version: 2.3.3
 * 
 */
 
@WebService(targetNamespace = "http://manufacturing.org/wsdl/purchase", name = "schedulingPT")
@XmlSeeAlso({org.manufacturing.xsd.purchase.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SchedulingPT {

    @Oneway
    @WebMethod
    public void sendShippingSchedule(
        @WebParam(partName = "schedule", name = "scheduleInfo", targetNamespace = "http://manufacturing.org/xsd/purchase")
        java.lang.String schedule
    );

    @Oneway
    @WebMethod
    public void requestProductionScheduling(
        @WebParam(partName = "customerInfo", name = "customerInfo", targetNamespace = "")
        org.manufacturing.xsd.purchase.CustomerInfo customerInfo,
        @WebParam(partName = "purchaseOrder", name = "purchaseOrder", targetNamespace = "")
        org.manufacturing.xsd.purchase.PurchaseOrder purchaseOrder
    );
}