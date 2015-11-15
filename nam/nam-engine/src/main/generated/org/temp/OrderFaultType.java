
package org.temp;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.3.1-patch-01
 * Fri May 04 11:39:49 PDT 2012
 * Generated source version: 2.3.1-patch-01
 * 
 */

@WebFault(name = "OrderFault", targetNamespace = "http://manufacturing.org/xsd/purchase")
public class OrderFaultType extends Exception {
    public static final long serialVersionUID = 20120504113949L;
    
    private java.lang.String orderFault;

    public OrderFaultType() {
        super();
    }
    
    public OrderFaultType(String message) {
        super(message);
    }
    
    public OrderFaultType(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderFaultType(String message, java.lang.String orderFault) {
        super(message);
        this.orderFault = orderFault;
    }

    public OrderFaultType(String message, java.lang.String orderFault, Throwable cause) {
        super(message, cause);
        this.orderFault = orderFault;
    }

    public java.lang.String getFaultInfo() {
        return this.orderFault;
    }
}
