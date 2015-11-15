package nam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JndiContext", namespace = "http://nam/model", propOrder = {
	"name",
    "userName",
    "password",
    "connectionUrl",
    "contextFactory",
	"contextPackages",
	"properties"
})
@XmlRootElement(name = "jndiContext", namespace = "http://nam/model")
public class JndiContext implements Comparable<JndiContext>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "name", namespace = "http://nam/model")
	private String name;
	
	@XmlElement(name = "userName", namespace = "http://nam/model")
	private String userName;
	
	@XmlElement(name = "password", namespace = "http://nam/model")
	private String password;
	
	@XmlElement(name = "connectionUrl", namespace = "http://nam/model")
	private String connectionUrl;
	
	@XmlElement(name = "contextFactory", namespace = "http://nam/model")
	private String contextFactory;
	
	@XmlElement(name = "contextPackages", namespace = "http://nam/model")
	private String contextPackages;
	
	@XmlElement(name = "properties", namespace = "http://nam/model")
	private Properties properties;
	
	
	public JndiContext() {
		//nothing for now
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
    public String getConnectionUrl() {
        return connectionUrl;
    }

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}
	
    public String getContextFactory() {
        return contextFactory;
    }

	public void setContextFactory(String contextFactory) {
		this.contextFactory = contextFactory;
	}
	
    public String getContextPackages() {
        return contextPackages;
    }

	public void setContextPackages(String contextPackages) {
		this.contextPackages = contextPackages;
	}
	
	public Properties getProperties() {
		return properties;
	}
	
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(JndiContext other) {
		int status = compare(name, other.name);
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
		JndiContext other = (JndiContext) object;
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
		return "JndiContext: name="+name;
    }

}
