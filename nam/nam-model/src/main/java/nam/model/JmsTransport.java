package nam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JmsTransport", namespace = "http://nam/model", propOrder = {
	"jndiName",
	"portType",
	"destination",
	"destinationType",
	"connectionFactory",
	"redeliveryDelay",
	"maxDeliveryAttempts"
})
@XmlRootElement(name = "jms-Transport", namespace = "http://nam/model")
public class JmsTransport extends Transport implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "jndiName", namespace = "http://nam/model")
	private String jndiName;
	
	@XmlElement(name = "portType", namespace = "http://nam/model")
	private String portType;
	
	@XmlElement(name = "destination", namespace = "http://nam/model")
	private String destination;
	
	@XmlElement(name = "destinationType", namespace = "http://nam/model")
	private DestinationType destinationType;
	
	@XmlElement(name = "connectionFactory", namespace = "http://nam/model")
	private String connectionFactory;
	
	@XmlElement(name = "redeliveryDelay", namespace = "http://nam/model")
	private Integer redeliveryDelay;
	
	@XmlElement(name = "maxDeliveryAttempts", namespace = "http://nam/model")
	private Integer maxDeliveryAttempts;
	
	
	public JmsTransport() {
		//nothing for now
    }

	
    public String getJndiName() {
        return jndiName;
    }

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}
	
	public String getPortType() {
		return portType;
	}
	
	public void setPortType(String portType) {
		this.portType = portType;
	}
	
    public String getDestination() {
        return destination;
    }

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
    public DestinationType getDestinationType() {
        if (destinationType == null) {
            return DestinationType.TOPIC;
        } else {
            return destinationType;
        }
    }
    
	public void setDestinationType(DestinationType destinationType) {
		this.destinationType = destinationType;
    }

    public String getConnectionFactory() {
        return connectionFactory;
    }

	public void setConnectionFactory(String connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	public Integer getRedeliveryDelay() {
        if (redeliveryDelay == null) {
            return new Integer("1000");
        } else {
            return redeliveryDelay;
        }
	}
	
	public void setRedeliveryDelay(Integer redeliveryDelay) {
    }

	public Integer getMaxDeliveryAttempts() {
        if (maxDeliveryAttempts == null) {
            return new Integer("3");
        } else {
            return maxDeliveryAttempts;
        }
	}
	
	public void setMaxDeliveryAttempts(Integer maxDeliveryAttempts) {
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(Transport other) {
		int status = super.compareTo(other);
		return status;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Transport other = (Transport) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
    }

	@Override
	public String toString() {
		return "JMSTransport: jndiName="+jndiName+", portType="+portType+", destination="+destination+", destinationType="+destinationType+", connectionFactory="+connectionFactory+", redeliveryDelay="+redeliveryDelay+", maxDeliveryAttempts="+maxDeliveryAttempts;
    }

}
