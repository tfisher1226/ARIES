package nam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Interactor", namespace = "http://nam/model", propOrder = {
	"name",
	"role",
	"link",
	"invalid",
	"channel",
	"target",
	"service",
    "asynchronous",
	"timeToLive",
    "transacted",
    "durable",
    "priority",
	"timeout",
	"deliveryMode",
	"interaction"
})
@XmlRootElement(name = "interactor", namespace = "http://nam/model")
public class Interactor implements Comparable<Object>, Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "name", namespace = "http://nam/model")
	private String name;

	@XmlElement(name = "role", namespace = "http://nam/model")
	private String role;

	@XmlElement(name = "link", namespace = "http://nam/model")
	private String link;

	@XmlElement(name = "invalid", namespace = "http://nam/model")
	private String invalid;
	
	@XmlElement(name = "channel", namespace = "http://nam/model")
	private String channel;
	
	@XmlElement(name = "target", namespace = "http://nam/model")
	private String target;
	
	@XmlElement(name = "service", namespace = "http://nam/model")
	private String service;
	
	@XmlElement(name = "asynchronous", namespace = "http://nam/model", type = String.class, defaultValue = "false")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
	private Boolean asynchronous = false;

	@XmlElement(name = "timeToLive", namespace = "http://nam/model", defaultValue = "720000")
	private Long timeToLive = 720000L;
	
	@XmlElement(name = "transacted", namespace = "http://nam/model", type = String.class, defaultValue = "false")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
	private Boolean transacted = false;

	@XmlElement(name = "durable", namespace = "http://nam/model", type = String.class, defaultValue = "true")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
	private Boolean durable = true;

	@XmlElement(name = "priority", namespace = "http://nam/model", type = String.class, defaultValue = "false")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
	private Boolean priority = false;

	@XmlElement(name = "timeout", namespace = "http://nam/model", defaultValue = "360000")
	private Long timeout = 360000L;
	
	@XmlElement(name = "deliveryMode", namespace = "http://nam/model", defaultValue = "NON_PERSISTENT")
	private DeliveryMode deliveryMode = DeliveryMode.NON_PERSISTENT;
	
	@XmlElement(name = "interaction", namespace = "http://nam/model")
	private Interaction interaction;

    @XmlAttribute(name = "ref")
	private String ref;


	public Interactor() {
		//nothing for now
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
    }

	public String getLink() {
		return link;
    }

	public void setLink(String link) {
		this.link = link;
	}
	
	public String getInvalid() {
		return invalid;
	}
	
	public void setInvalid(String invalid) {
		this.invalid = invalid;
	}
	
	public String getChannel() {
		return channel;
	}
	
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getService() {
		return service;
	}
	
	public void setService(String service) {
		this.service = service;
	}
	
	public Boolean isAsynchronous() {
		return asynchronous != null && asynchronous;
    }

    public Boolean getAsynchronous() {
		return asynchronous != null && asynchronous;
	}
	
	public void setAsynchronous(Boolean asynchronous) {
		this.asynchronous = asynchronous;
	}
	
	public Long getTimeToLive() {
		return timeToLive;
	}
	
	public void setTimeToLive(Long timeToLive) {
		this.timeToLive = timeToLive;
	}
	
	public Boolean isTransacted() {
		return transacted != null && transacted;
    }

    public Boolean getTransacted() {
		return transacted != null && transacted;
	}
	
	public void setTransacted(Boolean transacted) {
		this.transacted = transacted;
	}
	
	public Boolean isDurable() {
		return durable != null && durable;
    }

    public Boolean getDurable() {
		return durable != null && durable;
	}
	
	public void setDurable(Boolean durable) {
		this.durable = durable;
	}
	
	public Boolean isPriority() {
		return priority != null && priority;
    }

    public Boolean getPriority() {
		return priority != null && priority;
	}
	
	public void setPriority(Boolean priority) {
		this.priority = priority;
    }

    public Long getTimeout() {
        return timeout;
    }

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
    }

	public DeliveryMode getDeliveryMode() {
		return deliveryMode;
    }

	public void setDeliveryMode(DeliveryMode deliveryMode) {
		this.deliveryMode = deliveryMode;
    }

	public Interaction getInteraction() {
		return interaction;
    }

	public void setInteraction(Interaction interaction) {
		this.interaction = interaction;
    }

	public String getRef() {
		return ref;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
    }

	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(Object object) {
		if (object.getClass().isAssignableFrom(this.getClass())) {
			Interactor other = (Interactor) object;
			int status = compare(name, other.name);
			if (status != 0)
				return status;
			status = compare(role, other.role);
			if (status != 0)
				return status;
			status = compare(link, other.link);
			if (status != 0)
				return status;
		} else {
			String name1 = this.getClass().getName();
			String name2 = object.getClass().getName();
			int status = compare(name1, name2);
			if (status != 0)
				return status;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Interactor other = (Interactor) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (name != null)
			hashCode += name.hashCode();
		if (role != null)
			hashCode += role.hashCode();
		if (link != null)
			hashCode += link.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Interactor: name="+name+", role="+role+", link="+link;
    }

}
