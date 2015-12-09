package nam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.DateTimeAdapter;
import org.aries.common.Property;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Service", namespace = "http://nam/model", propOrder = {
    "name",
    "label",
    "domain",
    "namespace",
    "version",
    "description",
    "element",
    "packageName",
    "interfaceName",
	"className",
    "portType",
    "transacted",
    "namespaces",
    "channelsAndListenersAndOperations",
    "process",
	"properties",
	"creationDate",
	"lastUpdate"
})
@XmlSeeAlso({
    Callback.class
})
@XmlRootElement(name = "service", namespace = "http://nam/model")
public class Service implements Comparable<Service>, Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "name", namespace = "http://nam/model")
    private String name;
    
	@XmlElement(name = "label", namespace = "http://nam/model")
    private String label;
    
	@XmlElement(name = "domain", namespace = "http://nam/model")
    private String domain;
    
	@XmlElement(name = "namespace", namespace = "http://nam/model")
    private String namespace;
    
	@XmlElement(name = "version", namespace = "http://nam/model")
    private String version;
    
	@XmlElement(name = "description", namespace = "http://nam/model")
    private String description;
    
	@XmlElement(name = "element", namespace = "http://nam/model")
    private String element;
    
	@XmlElement(name = "packageName", namespace = "http://nam/model")
    private String packageName;
    
	@XmlElement(name = "interfaceName", namespace = "http://nam/model")
    private String interfaceName;
    
	@XmlElement(name = "className", namespace = "http://nam/model")
    private String className;
    
	@XmlElement(name = "portType", namespace = "http://nam/model")
    private String portType;
    
	@XmlElement(name = "transacted", namespace = "http://nam/model")
    private Transacted transacted;
    
	@XmlElement(name = "namespaces", namespace = "http://nam/model")
    private List<Namespace> namespaces;
    
    @XmlElements({
        @XmlElement(name = "channel", namespace = "http://nam/model", type = Channel.class),
        @XmlElement(name = "listener", namespace = "http://nam/model", type = Listener.class),
        @XmlElement(name = "operation", namespace = "http://nam/model", type = Operation.class),
        @XmlElement(name = "component", namespace = "http://nam/model", type = Component.class),
        @XmlElement(name = "sender", namespace = "http://nam/model", type = Sender.class),
        @XmlElement(name = "invoker", namespace = "http://nam/model", type = Invoker.class),
        @XmlElement(name = "callback", namespace = "http://nam/model", type = Callback.class),
        @XmlElement(name = "execution", namespace = "http://nam/model", type = Execution.class),
		@XmlElement(name = "timeout", namespace = "http://nam/model", type = Timeout.class),
        @XmlElement(name = "fault", namespace = "http://nam/model", type = Fault.class)
    })
    private List<Serializable> channelsAndListenersAndOperations;
    
	@XmlElement(name = "process", namespace = "http://nam/model")
    private Process process;
    
	@XmlElement(name = "properties", namespace = "http://nam/model")
    private List<Property> properties;
    
	@XmlElement(name = "creationDate", namespace = "http://nam/model", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date creationDate;
	
	@XmlElement(name = "lastUpdate", namespace = "http://nam/model", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date lastUpdate;
	
    @XmlAttribute(name = "pattern")
    private String pattern;
    
    @XmlAttribute(name = "ref")
    private String ref;

    
    public Service() {	
		namespaces = new ArrayList<Namespace>();
		channelsAndListenersAndOperations = new ArrayList<Serializable>();
		properties = new ArrayList<Property>();
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
    }

    public String getDomain() {
        return domain;
    }

	public void setDomain(String domain) {
		this.domain = domain;
    }

    public String getNamespace() {
        return namespace;
    }

	public void setNamespace(String namespace) {
		this.namespace = namespace;
    }

    public String getVersion() {
        return version;
    }

	public void setVersion(String version) {
		this.version = version;
	}
	
    public String getDescription() {
        return description;
    }

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getElement() {
		return element;
    }

	public void setElement(String element) {
		this.element = element;
    }

    public String getPackageName() {
        return packageName;
    }

	public void setPackageName(String packageName) {
		this.packageName = packageName;
    }

	public String getInterfaceName() {
		return interfaceName;
    }

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
    }

	public String getClassName() {
		return className;
    }

	public void setClassName(String className) {
		this.className = className;
    }

    public String getPortType() {
        return portType;
    }

	public void setPortType(String portType) {
		this.portType = portType;
    }

	public Transacted getTransacted() {
		return transacted;
	}
	
	public void setTransacted(Transacted transacted) {
		this.transacted = transacted;
	}
	
    public List<Namespace> getNamespaces() {
		synchronized (namespaces) {
			return namespaces;
		}
	}
	
	public void setNamespaces(Collection<Namespace> namespaces) {
        if (namespaces == null) {
			this.namespaces = null;
		} else {
			synchronized (this.namespaces) {
				this.namespaces = new ArrayList<Namespace>();
				addToNamespaces(namespaces);
			}
        }
    }

	public void addToNamespaces(Namespace namespace) {
		if (namespace != null ) {
			synchronized (this.namespaces) {
				this.namespaces.add(namespace);
			}
		}
	}

	public void addToNamespaces(Collection<Namespace> namespaceCollection) {
		if (namespaceCollection != null && !namespaceCollection.isEmpty()) {
			synchronized (this.namespaces) {
				this.namespaces.addAll(namespaceCollection);
			}
		}
	}

	public void removeFromNamespaces(Namespace namespace) {
		if (namespace != null ) {
			synchronized (this.namespaces) {
				this.namespaces.remove(namespace);
			}
		}
	}

	public void removeFromNamespaces(Collection<Namespace> namespaceCollection) {
		if (namespaceCollection != null ) {
			synchronized (this.namespaces) {
				this.namespaces.removeAll(namespaceCollection);
			}
		}
    }

	public void clearNamespaces() {
		synchronized (namespaces) {
			namespaces.clear();
		}
    }

    public List<Serializable> getChannelsAndListenersAndOperations() {
		synchronized (channelsAndListenersAndOperations) {
			return channelsAndListenersAndOperations;
		}
	}
	
	public void setChannelsAndListenersAndOperations(Collection<Serializable> channelsAndListenersAndOperations) {
        if (channelsAndListenersAndOperations == null) {
			this.channelsAndListenersAndOperations = null;
		} else {
		synchronized (this.channelsAndListenersAndOperations) {
				this.channelsAndListenersAndOperations = new ArrayList<Serializable>();
				addToChannelsAndListenersAndOperations(channelsAndListenersAndOperations);
			}
		}
	}

	public void addToChannelsAndListenersAndOperations(Serializable serializable) {
		if (serializable != null ) {
			synchronized (this.channelsAndListenersAndOperations) {
				this.channelsAndListenersAndOperations.add(serializable);
			}
		}
	}

	public void addToChannelsAndListenersAndOperations(Collection<Serializable> serializableCollection) {
		if (serializableCollection != null && !serializableCollection.isEmpty()) {
			synchronized (this.channelsAndListenersAndOperations) {
				this.channelsAndListenersAndOperations.addAll(serializableCollection);
			}
		}
	}

	public void removeFromChannelsAndListenersAndOperations(Serializable serializable) {
		if (serializable != null ) {
			synchronized (this.channelsAndListenersAndOperations) {
				this.channelsAndListenersAndOperations.remove(serializable);
			}
		}
	}

	public void removeFromChannelsAndListenersAndOperations(Collection<Serializable> serializableCollection) {
		if (serializableCollection != null ) {
			synchronized (this.channelsAndListenersAndOperations) {
				this.channelsAndListenersAndOperations.removeAll(serializableCollection);
			}
		}
	}

	public void clearChannelsAndListenersAndOperations() {
		synchronized (channelsAndListenersAndOperations) {
			channelsAndListenersAndOperations.clear();
        }
    }

    public Process getProcess() {
        return process;
    }

	public void setProcess(Process process) {
		this.process = process;
    }

    public List<Property> getProperties() {
		synchronized (properties) {
			return properties;
		}
	}
	
	public void setProperties(Collection<Property> properties) {
        if (properties == null) {
			this.properties = null;
		} else {
		synchronized (this.properties) {
				this.properties = new ArrayList<Property>();
				addToProperties(properties);
			}
        }
    }

	public void addToProperties(Property property) {
		if (property != null ) {
			synchronized (this.properties) {
				this.properties.add(property);
			}
		}
	}

	public void addToProperties(Collection<Property> propertyCollection) {
		if (propertyCollection != null && !propertyCollection.isEmpty()) {
			synchronized (this.properties) {
				this.properties.addAll(propertyCollection);
			}
		}
	}

	public void removeFromProperties(Property property) {
		if (property != null ) {
			synchronized (this.properties) {
				this.properties.remove(property);
			}
		}
	}

	public void removeFromProperties(Collection<Property> propertyCollection) {
		if (propertyCollection != null ) {
			synchronized (this.properties) {
				this.properties.removeAll(propertyCollection);
			}
		}
	}

	public void clearProperties() {
		synchronized (properties) {
			properties.clear();
		}
	}
	
	public Date getCreationDate() {
		return creationDate;
    }

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
    }

	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String value) {
        this.pattern = value;
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
	public int compareTo(Service other) {
		int status = compare(domain, other.domain);
		if (status != 0)
			return status;
		status = compare(name, other.name);
		return status;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Service other = (Service) object;
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
		return "Service: name="+name+", domain="+domain;
	}

}
