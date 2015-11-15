package nam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Fault", namespace = "http://nam/model", propOrder = {
	"code",
    "message",
	"cause",
	"attributes"
})
@XmlRootElement(name = "fault", namespace = "http://nam/model")
public class Fault extends Element implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "code", namespace = "http://nam/model")
	private String code;
	
	@XmlElement(name = "message", namespace = "http://nam/model")
	private String message;
	
	@XmlElement(name = "cause", namespace = "http://nam/model")
	private Fault cause;
	
	@XmlElement(name = "attributes", namespace = "http://nam/model")
	private List<Attribute> attributes;
	
	
	public Fault() {
		attributes = new ArrayList<Attribute>();
	}
	
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

    public String getMessage() {
        return message;
    }

	public void setMessage(String message) {
		this.message = message;
	}
	
    public Fault getCause() {
        return cause;
    }

	public void setCause(Fault cause) {
		this.cause = cause;
        }
	
	public List<Attribute> getAttributes() {
		synchronized (attributes) {
			return attributes;
		}
	}
	
	public void setAttributes(Collection<Attribute> attributes) {
		if (attributes == null) {
			this.attributes = null;
		} else {
		synchronized (this.attributes) {
				this.attributes = new ArrayList<Attribute>();
				addToAttributes(attributes);
			}
		}
	}

	public void addToAttributes(Attribute attribute) {
		if (attribute != null ) {
			synchronized (this.attributes) {
				this.attributes.add(attribute);
			}
		}
	}

	public void addToAttributes(Collection<Attribute> attributeCollection) {
		if (attributeCollection != null && !attributeCollection.isEmpty()) {
			synchronized (this.attributes) {
				this.attributes.addAll(attributeCollection);
			}
		}
	}

	public void removeFromAttributes(Attribute attribute) {
		if (attribute != null ) {
			synchronized (this.attributes) {
				this.attributes.remove(attribute);
			}
		}
	}

	public void removeFromAttributes(Collection<Attribute> attributeCollection) {
		if (attributeCollection != null ) {
			synchronized (this.attributes) {
				this.attributes.removeAll(attributeCollection);
			}
		}
	}

	public void clearAttributes() {
		synchronized (attributes) {
			attributes.clear();
		}
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
			Fault other = (Fault) object;
			int status = compare(code, other.code);
			if (status != 0)
				return status;
			status = compare(message, other.message);
			if (status != 0)
				return status;
		}
		int status = super.compareTo(object);
		return status;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Fault other = (Fault) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (code != null)
			hashCode += code.hashCode();
		if (message != null)
			hashCode += message.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Fault: code="+code+", message="+message;
    }

}
