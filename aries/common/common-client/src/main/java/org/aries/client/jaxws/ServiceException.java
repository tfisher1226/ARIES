package org.aries.client.jaxws;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for the <code>ServiceException</code> complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceException">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceException", propOrder = {
    "message",
    "cause"
})
@SuppressWarnings("serial")
public class ServiceException implements Serializable {

    protected String message;

    protected String cause;


	public ServiceException() {
		//nothing for now
	}
	
	public ServiceException(String message) {
        this.message = message;
	}
	
	public ServiceException(String message, String cause) {
        this.message = message;
        this.cause = cause;
	}
	
    public String getMessage() {
        return message;
    }

//    public void setMessage(String message) {
//        this.message = message;
//    }

    public String getCause() {
        return cause;
    }

//    public void setCause(String cause) {
//        this.cause = cause;
//    }

}
