package nam.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import nam.ui.View;

import org.aries.adapter.DateTimeAdapter;
import org.aries.validate.Checkpoints;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Module", namespace = "http://nam/model", propOrder = {
	"type",
	"level",
	"name",
	"label",
	"groupId",
	"artifactId",
	"version",
	"namespace",
	"description",
	"packaging",
	"properties",
	"dependencies",
	"configuration",
	"interactors",
	"resources",
	"information",
	"services",
	"processes",
	"components",
	"persistence",
	"view",
	"checkpoints",
	"creator",
	"creationDate",
	"lastUpdate"
})
@XmlRootElement(name = "module", namespace = "http://nam/model")
public class Module implements Comparable<Object>, Serializable {

	private static final long serialVersionUID = 1L;
    
	@XmlElement(name = "type", namespace = "http://nam/model")
	private ModuleType type;
	
	@XmlElement(name = "level", namespace = "http://nam/model")
	private ModuleLevel level;
	
	@XmlElement(name = "name", namespace = "http://nam/model")
	private String name;
	
	@XmlElement(name = "label", namespace = "http://nam/model")
	private String label;
	
	@XmlElement(name = "groupId", namespace = "http://nam/model")
	private String groupId;
	
	@XmlElement(name = "artifactId", namespace = "http://nam/model")
	private String artifactId;
	
	@XmlElement(name = "version", namespace = "http://nam/model")
	private String version;
	
	@XmlElement(name = "namespace", namespace = "http://nam/model")
	private String namespace;
	
	@XmlElement(name = "description", namespace = "http://nam/model")
	private String description;
    
	@XmlElement(name = "packaging", namespace = "http://nam/model")
	private PackageType packaging;
	
    @XmlElement(namespace = "http://nam/model")
    protected Properties properties;
    
    @XmlElement(namespace = "http://nam/model")
    protected Dependencies dependencies;
    
    @XmlElement(namespace = "http://nam/model")
    protected Configuration configuration;
    
    @XmlElement(namespace = "http://nam/model")
    protected Interactors interactors;
    
    @XmlElement(namespace = "http://nam/model")
    protected Resources resources;
    
    @XmlElement(namespace = "http://nam/model")
    protected Information information;
    
    @XmlElement(namespace = "http://nam/model")
    protected Services services;
    
    @XmlElement(namespace = "http://nam/model")
    protected Processes processes;
    
    @XmlElement(namespace = "http://nam/model")
    protected Components components;
    
    @XmlElement(namespace = "http://nam/model")
    protected Persistence persistence;
    
    @XmlElement(namespace = "http://nam/ui")
    protected View view;
    
    @XmlElement(namespace = "http://www.aries.org/validate")
    protected Checkpoints checkpoints;
    
	@XmlElement(name = "creator", namespace = "http://nam/model")
	private String creator;
	
	@XmlElement(name = "creationDate", namespace = "http://nam/model", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date creationDate;
	
	@XmlElement(name = "lastUpdate", namespace = "http://nam/model", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date lastUpdate;
    
    @XmlAttribute(name = "ref")
    protected String ref;


	public Module() {
		//nothing for now
	}

	
    public ModuleType getType() {
        return type;
    }

	public void setType(ModuleType type) {
		this.type = type;
	}
	
    public ModuleLevel getLevel() {
        return level;
    }

	public void setLevel(ModuleLevel level) {
		this.level = level;
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

    public String getGroupId() {
        return groupId;
    }

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
    public String getArtifactId() {
        return artifactId;
    }

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}
	
    public String getVersion() {
        return version;
    }

	public void setVersion(String version) {
		this.version = version;
	}
	
    public String getNamespace() {
        return namespace;
    }

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
    public PackageType getPackaging() {
        return packaging;
    }

	public void setPackaging(PackageType packaging) {
		this.packaging = packaging;
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
     * Gets the value of the dependencies property.
     * 
     * @return
     *     possible object is
     *     {@link Dependencies }
     *     
     */
    public Dependencies getDependencies() {
        return dependencies;
    }

    /**
     * Sets the value of the dependencies property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dependencies }
     *     
     */
    public void setDependencies(Dependencies value) {
        this.dependencies = value;
    }

    /**
     * Gets the value of the configuration property.
     * 
     * @return
     *     possible object is
     *     {@link Configuration }
     *     
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Sets the value of the configuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Configuration }
     *     
     */
    public void setConfiguration(Configuration value) {
        this.configuration = value;
    }

    /**
     * Gets the value of the interactors property.
     * 
     * @return
     *     possible object is
     *     {@link Interactors }
     *     
     */
    public Interactors getInteractors() {
        return interactors;
    }

    /**
     * Sets the value of the interactors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Interactors }
     *     
     */
    public void setInteractors(Interactors value) {
        this.interactors = value;
    }

    /**
     * Gets the value of the resources property.
     * 
     * @return
     *     possible object is
     *     {@link Resources }
     *     
     */
    public Resources getResources() {
        return resources;
    }

    /**
     * Sets the value of the resources property.
     * 
     * @param value
     *     allowed object is
     *     {@link Resources }
     *     
     */
    public void setResources(Resources value) {
        this.resources = value;
    }

    /**
     * Gets the value of the information property.
     * 
     * @return
     *     possible object is
     *     {@link Information }
     *     
     */
    public Information getInformation() {
        return information;
    }

    /**
     * Sets the value of the information property.
     * 
     * @param value
     *     allowed object is
     *     {@link Information }
     *     
     */
    public void setInformation(Information value) {
        this.information = value;
    }

    /**
     * Gets the value of the services property.
     * 
     * @return
     *     possible object is
     *     {@link Services }
     *     
     */
    public Services getServices() {
        return services;
    }

    /**
     * Sets the value of the services property.
     * 
     * @param value
     *     allowed object is
     *     {@link Services }
     *     
     */
    public void setServices(Services value) {
        this.services = value;
    }

    /**
     * Gets the value of the processes property.
     * 
     * @return
     *     possible object is
     *     {@link Processes }
     *     
     */
    public Processes getProcesses() {
        return processes;
    }

    /**
     * Sets the value of the processes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Processes }
     *     
     */
    public void setProcesses(Processes value) {
        this.processes = value;
    }

    /**
     * Gets the value of the components property.
     * 
     * @return
     *     possible object is
     *     {@link Components }
     *     
     */
    public Components getComponents() {
        return components;
    }

    /**
     * Sets the value of the components property.
     * 
     * @param value
     *     allowed object is
     *     {@link Components }
     *     
     */
    public void setComponents(Components value) {
        this.components = value;
    }

    /**
     * Gets the value of the persistence property.
     * 
     * @return
     *     possible object is
     *     {@link Persistence }
     *     
     */
    public Persistence getPersistence() {
        return persistence;
    }

    /**
     * Sets the value of the persistence property.
     * 
     * @param value
     *     allowed object is
     *     {@link Persistence }
     *     
     */
    public void setPersistence(Persistence value) {
        this.persistence = value;
    }

    /**
     * Gets the value of the view property.
     * 
     * @return
     *     possible object is
     *     {@link View }
     *     
     */
    public View getView() {
        return view;
    }

    /**
     * Sets the value of the view property.
     * 
     * @param value
     *     allowed object is
     *     {@link View }
     *     
     */
    public void setView(View value) {
        this.view = value;
    }

    /**
     * Gets the value of the checkpoints property.
     * 
     * @return
     *     possible object is
     *     {@link Checkpoints }
     *     
     */
    public Checkpoints getCheckpoints() {
        return checkpoints;
    }

    /**
     * Sets the value of the checkpoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link Checkpoints }
     *     
     */
    public void setCheckpoints(Checkpoints value) {
        this.checkpoints = value;
    }
	
    public String getCreator() {
        return creator;
    }
	
	public void setCreator(String creator) {
		this.creator = creator;
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
	
    public String getRef() {
        return ref;
    }
	
    public void setRef(String value) {
        this.ref = value;
    }

	@Override
	public int compareTo(Object object) {
		if (object.getClass().isAssignableFrom(this.getClass())) {
			Module other = (Module) object;
			int status = compare(name, other.name);
			if (status != 0)
				return status;
			status = compare(type.name(), other.type.name());
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
		Module other = (Module) object;
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
		return "Module: type="+type+", name="+name;
    }

}
