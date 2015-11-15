
package com.example.loan_approval.wsdl;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.3.3
 * 2012-05-01T01:27:49.654-07:00
 * Generated source version: 2.3.3
 * 
 */

@WebFault(name = "integer", targetNamespace = "http://example.com/loan-approval/xsd/error-messages/")
public class ErrorMessage extends Exception {
    public static final long serialVersionUID = 20120501012749L;
    
    private java.math.BigInteger integer;

    public ErrorMessage() {
        super();
    }
    
    public ErrorMessage(String message) {
        super(message);
    }
    
    public ErrorMessage(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorMessage(String message, java.math.BigInteger integer) {
        super(message);
        this.integer = integer;
    }

    public ErrorMessage(String message, java.math.BigInteger integer, Throwable cause) {
        super(message, cause);
        this.integer = integer;
    }

    public java.math.BigInteger getFaultInfo() {
        return this.integer;
    }
}
