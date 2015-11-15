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
@XmlType(name = "Adapter", namespace = "http://nam/model", propOrder = {
    "type",
    "name",
    "className",
    "transacted",
    "maxThreads",
    "connectionPool",
    "provider",
    "properties"
})
@XmlRootElement(name = "adapter", namespace = "http://nam/model")
public class Adapter implements Comparable<Object>, Serializable {

	private static final long serialVersionUID = 1L;
    
	@XmlElement(name = "type", namespace = "http://nam/model")
	private String type;
    
	@XmlElement(name = "name", namespace = "http://nam/model")
	private String name;
    
	@XmlElement(name = "className", namespace = "http://nam/model")
	private String className;
    
	@XmlElement(name = "transacted", namespace = "http://nam/model", type = String.class, defaultValue = "true")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
	private Boolean transacted = true;
    
	@XmlElement(name = "maxThreads", namespace = "http://nam/model")
	private Integer maxThreads;
    
	@XmlElement(name = "connectionPool", namespace = "http://nam/model")
	private ConnectionPool connectionPool;
    
	@XmlElement(name = "provider", namespace = "http://nam/model")
	private Provider provider;
    
	@XmlElement(name = "properties", namespace = "http://nam/model")
	private Properties properties;
    
    @XmlAttribute(name = "ref")
	private String ref;
	
	
	public Adapter() {
		//nothing for now
	}


    public String getType() {
        return type;
    }

	public void setType(String type) {
		this.type = type;
    }

	public String getName() {
		return name;
    }

	public void setName(String name) {
		this.name = name;
    }

    public String getClassName() {
        return className;
    }

	public void setClassName(String className) {
		this.className = className;
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
	
	public Integer getMaxThreads() {
		return maxThreads;
	}
	
	public void setMaxThreads(Integer maxThreads) {
		this.maxThreads = maxThreads;
	}
	
    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

	public void setConnectionPool(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}
	
    public Provider getProvider() {
        return provider;
    }

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	
    public Properties getProperties() {
        return properties;
    }

	public void setProperties(Properties properties) {
		this.properties = properties;
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
			Adapter other = (Adapter) object;
			int status = compare(name, other.name);
			if (status != 0)
				return status;
			status = compare(className, other.className);
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
		Adapter other = (Adapter) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (name != null)
			hashCode += name.hashCode();
		if (className != null)
			hashCode += className.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Adapter: name="+name+", className="+className;
    }

}
