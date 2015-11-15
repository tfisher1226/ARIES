package nam.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Channel", namespace = "http://nam/model", propOrder = {
    "name",
    "host",
    "port",
    "jndiName",
    "transferMode",
    "acknowledgeMode",
    "redeliveryDelay",
    "maxDeliveryAttempts",
    "adapter",
    "interactors",
    "clients",
    "services",
    "managers",
    "properties"
})
@XmlRootElement(name = "channel", namespace = "http://nam/model")
public class Channel implements Comparable<Object>, Serializable {

    private final static long serialVersionUID = 1L;
    
//    @XmlElement(name = "send-link", namespace = "http://nam/model")
//    protected String sendLink;
//    
//    @XmlElement(name = "receive-link", namespace = "http://nam/model")
//    protected String receiveLink;
//    
//    @XmlElement(name = "invalid-link", namespace = "http://nam/model")
//    protected String invalidLink;
//    
//    @XmlElement(name = "expired-link", namespace = "http://nam/model")
//    protected String expiredLink;
    
    @XmlElement(namespace = "http://nam/model")
    protected String name;
    
    @XmlElement(namespace = "http://nam/model")
    protected String host;
    
    @XmlElement(namespace = "http://nam/model")
    protected BigInteger port;
    
    @XmlElement(name = "jndi-name", namespace = "http://nam/model")
    protected String jndiName;
    
    @XmlElement(name = "transfer-mode", namespace = "http://nam/model")
    protected TransferMode transferMode;
    
    @XmlElement(name = "acknowledge-mode", namespace = "http://nam/model")
    protected AcknowledgeMode acknowledgeMode;
    
    @XmlElement(name = "redelivery-delay", namespace = "http://nam/model", defaultValue = "1000")
    protected BigInteger redeliveryDelay;
    
    @XmlElement(name = "max-delivery-attempts", namespace = "http://nam/model", defaultValue = "3")
    protected BigInteger maxDeliveryAttempts;
    
    @XmlElement(namespace = "http://nam/model")
    protected Adapter adapter;
    
    @XmlElements({
        @XmlElement(name = "sender", namespace = "http://nam/model", type = Sender.class),
        @XmlElement(name = "invoker", namespace = "http://nam/model", type = Invoker.class),
        @XmlElement(name = "receiver", namespace = "http://nam/model", type = Receiver.class),
        @XmlElement(name = "property", namespace = "http://nam/model", type = Property.class)
    })
    protected List<Serializable> interactors;
    
    @XmlElement(namespace = "http://nam/model")
    protected List<String> clients;
    
    @XmlElement(namespace = "http://nam/model")
    protected List<String> services;
    
    @XmlElement(namespace = "http://nam/model")
    protected List<String> managers;
    
    @XmlElement(namespace = "http://nam/model")
    protected Properties properties;
    
    @XmlAttribute(name = "ref")
	private String ref;
	
	
	public Channel() {
		clients = new ArrayList<String>();
		services = new ArrayList<String>();
		managers = new ArrayList<String>();
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<Serializable> getInteractors() {
        if (interactors == null) {
            interactors = new ArrayList<Serializable>();
        }
        return this.interactors;
    }

//    public String getSendLink() {
//        return sendLink;
//    }
//
//    public void setSendLink(String value) {
//        this.sendLink = value;
//    }
//
//    public String getReceiveLink() {
//        return receiveLink;
//    }
//
//    public void setReceiveLink(String value) {
//        this.receiveLink = value;
//    }
//
//    public String getInvalidLink() {
//        return invalidLink;
//    }
//
//    public void setInvalidLink(String value) {
//        this.invalidLink = value;
//    }
//
//    public String getExpiredLink() {
//        return expiredLink;
//    }
//
//    public void setExpiredLink(String value) {
//        this.expiredLink = value;
//    }

    /**
     * Gets the value of the host property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the value of the host property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHost(String value) {
        this.host = value;
    }

    /**
     * Gets the value of the port property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPort() {
        return port;
    }

    /**
     * Sets the value of the port property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPort(BigInteger value) {
        this.port = value;
    }

    /**
     * Gets the value of the jndiName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJndiName() {
        return jndiName;
    }

    /**
     * Sets the value of the jndiName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJndiName(String value) {
        this.jndiName = value;
    }

    /**
     * Gets the value of the transferMode property.
     * 
     * @return
     *     possible object is
     *     {@link TransferMode }
     *     
     */
    public TransferMode getTransferMode() {
        return transferMode;
    }

    /**
     * Sets the value of the transferMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransferMode }
     *     
     */
    public void setTransferMode(TransferMode value) {
        this.transferMode = value;
    }

    /**
     * Gets the value of the acknowledgeMode property.
     * 
     * @return
     *     possible object is
     *     {@link AcknowledgeMode }
     *     
     */
    public AcknowledgeMode getAcknowledgeMode() {
        return acknowledgeMode;
    }

    /**
     * Sets the value of the acknowledgeMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcknowledgeMode }
     *     
     */
    public void setAcknowledgeMode(AcknowledgeMode value) {
        this.acknowledgeMode = value;
    }

    /**
     * Gets the value of the redeliveryDelay property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRedeliveryDelay() {
        return redeliveryDelay;
    }

    /**
     * Sets the value of the redeliveryDelay property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRedeliveryDelay(BigInteger value) {
        this.redeliveryDelay = value;
    }

    /**
     * Gets the value of the maxDeliveryAttempts property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxDeliveryAttempts() {
        return maxDeliveryAttempts;
    }

    /**
     * Sets the value of the maxDeliveryAttempts property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxDeliveryAttempts(BigInteger value) {
        this.maxDeliveryAttempts = value;
    }

    /**
     * Gets the value of the adapter property.
     * 
     * @return
     *     possible object is
     *     {@link Adapter }
     *     
     */
    public Adapter getAdapter() {
        return adapter;
    }

    /**
     * Sets the value of the adapter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Adapter }
     *     
     */
    public void setAdapter(Adapter value) {
        this.adapter = value;
    }

    /**
     * Gets the value of the properties property.
     * 
     * @return
     *     possible object is
     *     {@link Properties }
     *     
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Sets the value of the properties property.
     * 
     * @param value
     *     allowed object is
     *     {@link Properties }
     *     
     */
    public void setProperties(Properties value) {
        this.properties = value;
    }

    /**
     * Gets the value of the clients property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clients property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClients().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getClients() {
        if (clients == null) {
            clients = new ArrayList<String>();
        }
        return this.clients;
    }

    /**
     * Gets the value of the services property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the services property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getServices() {
        if (services == null) {
            services = new ArrayList<String>();
        }
        return this.services;
    }

    /**
     * Gets the value of the managers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the managers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getManagers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getManagers() {
        if (managers == null) {
            managers = new ArrayList<String>();
        }
        return this.managers;
    }

    public String getRef() {
        return ref;
    }

	public void setRef(String ref) {
		this.ref = ref;
	}
	
	@Override
	public int compareTo(Object object) {
		if (object.getClass().isAssignableFrom(this.getClass())) {
			Channel other = (Channel) object;
			int status = compare(name, other.name);
			if (status != 0)
				return status;
		}
		return 0;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Channel other = (Channel) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (name != null)
			hashCode += name.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Channel: name="+name;
    }

}
