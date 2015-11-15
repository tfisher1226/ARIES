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
@XmlType(name = "Provider", namespace = "http://nam/model", propOrder = {
    "type",
	"name",
	"store",
    "userName",
    "password",
	"jndiName",
	"jndiContext",
    "connectionUrl",
    "transacted",
    "properties"
})
@XmlRootElement(name = "provider", namespace = "http://nam/model")
public class Provider implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "type", namespace = "http://nam/model")
	private String type;
	
	@XmlElement(name = "name", namespace = "http://nam/model")
	private String name;
	
	@XmlElement(name = "store", namespace = "http://nam/model")
	private String store;
	
	@XmlElement(name = "userName", namespace = "http://nam/model")
	private String userName;

	@XmlElement(name = "password", namespace = "http://nam/model")
	private String password;
	
	@XmlElement(name = "jndiName", namespace = "http://nam/model")
	private String jndiName;
	
	@XmlElement(name = "jndiContext", namespace = "http://nam/model")
	private JndiContext jndiContext;
	
	@XmlElement(name = "connectionUrl", namespace = "http://nam/model")
	private String connectionUrl;
	
	@XmlElement(name = "transacted", namespace = "http://nam/model", type = String.class, defaultValue = "true")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
	private Boolean transacted = true;
	
	@XmlElement(name = "properties", namespace = "http://nam/model")
	private Properties properties;
	
	@XmlAttribute(name = "ref")
	private String ref;
	
	
	public Provider() {
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
	
	public String getStore() {
		return store;
	}
	
	public void setStore(String store) {
		this.store = store;
    }

    public String getUserName() {
        return userName;
    }

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
    public String getPassword() {
        return password;
    }

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getJndiName() {
		return jndiName;
	}
	
	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}
	
	public JndiContext getJndiContext() {
		return jndiContext;
	}
	
	public void setJndiContext(JndiContext jndiContext) {
		this.jndiContext = jndiContext;
	}
	
    public String getConnectionUrl() {
        return connectionUrl;
    }

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
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
			Provider other = (Provider) object;
		int status = compare(type, other.type);
		if (status != 0)
			return status;
		status = compare(name, other.name);
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
		Provider other = (Provider) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (type != null)
			hashCode += type.hashCode();
		if (name != null)
			hashCode += name.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Provider: type="+type+", name="+name;
    }

}
