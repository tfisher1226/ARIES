package nam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;


@XmlAccessorType(XmlAccessType.FIELD)

@XmlRootElement(name = "transport", namespace = "http://nam/model")
public class Transport implements Comparable<Transport>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "name", namespace = "http://nam/model", required = true)
	private String name;
	
	@XmlElement(name = "type", namespace = "http://nam/model")
	private TransportType type;

	@XmlElement(name = "host", namespace = "http://nam/model")
	private String host;
	
	@XmlElement(name = "port", namespace = "http://nam/model")
	private Integer port;
	
	@XmlElement(name = "scope", namespace = "http://nam/model")
	private String scope;
	
	@XmlElement(name = "transferMode", namespace = "http://nam/model")
	private TransferMode transferMode;
	
	@XmlElement(name = "transacted", namespace = "http://nam/model", type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
	private Boolean transacted;

	@XmlElement(name = "provider", namespace = "http://nam/model")
	private String provider;
	
	@XmlAttribute(name = "ref")
	private String ref;

	
	public Transport() {
		//nothing for now
    }

	
    public String getName() {
        return name;
    }

	public void setName(String name) {
		this.name = name;
	}
	
    public TransportType getType() {
        return type;
    }

	public void setType(TransportType type) {
		this.type = type;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public Integer getPort() {
		return port;
	}
	
	public void setPort(Integer port) {
		this.port = port;
	}
	
    public String getScope() {
        return scope;
    }

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public TransferMode getTransferMode() {
		return transferMode;
	}
	
	public void setTransferMode(TransferMode transferMode) {
		this.transferMode = transferMode;
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
	
	public String getProvider() {
		return provider;
	}
	
	public void setProvider(String provider) {
		this.provider = provider;
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
	public int compareTo(Transport other) {
		int status = compare(name, other.name);
		if (status != 0)
			return status;
		status = compare(type, other.type);
		if (status != 0)
			return status;
		return 0;
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
		if (name != null)
			hashCode += name.hashCode();
		if (type != null)
			hashCode += type.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Transport: name="+name+", type="+type;
	}

}
