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

import nam.ui.UserInterfaces;

import org.aries.adapter.DateTimeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Application", namespace = "http://nam/model", propOrder = {
	"type",
	"name",
	"label",
	"groupId",
	"artifactId",
	"version",
	"namespace",
	"contextRoot",
	"description",
	"packaging",
	"creator",
	"properties",
    "resources",
    "configuration",
    "dependencies",
    "channels",
    "projectNames",
	"modules",
    "services",
    "processes",
    "components",
    "information",
    "persistence",
	"userInterfaces",
	"creationDate",
	"lastUpdate"
})
@XmlRootElement(name = "application", namespace = "http://nam/model")
public class Application implements Comparable<Object>, Serializable {

	private static final long serialVersionUID = 1L;
    
	@XmlElement(name = "type", namespace = "http://nam/model")
	private ApplicationType type;
    
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
	
	@XmlElement(name = "contextRoot", namespace = "http://nam/model")
	private String contextRoot;
	
	@XmlElement(name = "description", namespace = "http://nam/model")
	private String description;
	
	@XmlElement(name = "packaging", namespace = "http://nam/model")
	private String packaging;
	
	@XmlElement(name = "creator", namespace = "http://nam/model")
	private String creator;
    
    @XmlElement(namespace = "http://nam/model")
    protected Properties properties;
    
    @XmlElement(namespace = "http://nam/model")
    protected Resources resources;
    
    @XmlElement(namespace = "http://nam/model")
    protected Configuration configuration;
    
    @XmlElement(namespace = "http://nam/model")
    protected Dependencies dependencies;
    
    @XmlElement(namespace = "http://nam/model")
    protected Channels channels;
    
    @XmlElement(namespace = "http://nam/model")
    protected ProjectNames projectNames;
    
	@XmlElement(name = "modules", namespace = "http://nam/model")
	private Modules modules;
    
    @XmlElement(namespace = "http://nam/model")
    protected Services services;
    
    @XmlElement(namespace = "http://nam/model")
    protected Processes processes;
    
    @XmlElement(namespace = "http://nam/model")
    protected Components components;
    
    @XmlElement(namespace = "http://nam/model")
    protected Information information;
    
    @XmlElement(namespace = "http://nam/model")
    protected Persistence persistence;
    
	@XmlElement(name = "userInterfaces", namespace = "http://nam/model")
	private UserInterfaces userInterfaces;
	
	@XmlElement(name = "creationDate", namespace = "http://nam/model", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date creationDate;
	
	@XmlElement(name = "lastUpdate", namespace = "http://nam/model", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date lastUpdate;
	
	@XmlAttribute(name = "ref")
	private String ref;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

    /**
     * Gets the value of the groupId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Sets the value of the groupId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupId(String value) {
        this.groupId = value;
    }

    /**
     * Gets the value of the artifactId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArtifactId() {
        return artifactId;
    }

    /**
     * Sets the value of the artifactId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArtifactId(String value) {
        this.artifactId = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationType }
     *     
     */
    public ApplicationType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationType }
     *     
     */
    public void setType(ApplicationType value) {
        this.type = value;
    }

//    /**
//     * Gets the value of the webEnabled property.
//     * 
//     * @return
//     *     possible object is
//     *     {@link String }
//     *     
//     */
//    public Boolean getWebEnabled() {
//        return webEnabled;
//    }

//    /**
//     * Sets the value of the webEnabled property.
//     * 
//     * @param value
//     *     allowed object is
//     *     {@link String }
//     *     
//     */
//    public void setWebEnabled(Boolean value) {
//        this.webEnabled = value;
//    }

//    /**
//     * Gets the value of the singleProject property.
//     * 
//     * @return
//     *     possible object is
//     *     {@link String }
//     *     
//     */
//    public Boolean getSingleProject() {
//        return singleProject;
//    }
//
//    /**
//     * Sets the value of the singleProject property.
//     * 
//     * @param value
//     *     allowed object is
//     *     {@link String }
//     *     
//     */
//    public void setSingleProject(Boolean value) {
//        this.singleProject = value;
//    }

    /**
     * Gets the value of the contextRoot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContextRoot() {
        return contextRoot;
    }

    /**
     * Sets the value of the contextRoot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContextRoot(String value) {
        this.contextRoot = value;
    }

    /**
     * Gets the value of the namespace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Sets the value of the namespace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamespace(String value) {
        this.namespace = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String value) {
        this.creator = value;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties value) {
        this.properties = value;
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
     * Gets the value of the channels property.
     * 
     * @return
     *     possible object is
     *     {@link Channels }
     *     
     */
    public Channels getChannels() {
        return channels;
    }

    /**
     * Sets the value of the channels property.
     * 
     * @param value
     *     allowed object is
     *     {@link Channels }
     *     
     */
    public void setChannels(Channels value) {
        this.channels = value;
    }

    /**
     * Gets the value of the projectNames property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectNames }
     *     
     */
    public ProjectNames getProjectNames() {
        return projectNames;
    }

    /**
     * Sets the value of the projectNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectNames }
     *     
     */
    public void setProjectNames(ProjectNames value) {
        this.projectNames = value;
    }

    /**
     * Gets the value of the modules property.
     * 
     * @return
     *     possible object is
     *     {@link Modules }
     *     
     */
    public Modules getModules() {
        return modules;
    }

    /**
     * Sets the value of the modules property.
     * 
     * @param value
     *     allowed object is
     *     {@link Modules }
     *     
     */
    public void setModules(Modules value) {
        this.modules = value;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date value) {
        this.creationDate = value;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date value) {
        this.lastUpdate = value;
    }

	public UserInterfaces getUserInterfaces() {
		return userInterfaces;
	}
	
	public void setUserInterfaces(UserInterfaces userInterfaces) {
		this.userInterfaces = userInterfaces;
	}
	
    public String getRef() {
        return ref;
    }

    public void setRef(String value) {
        this.ref = value;
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
			Application other = (Application) object;
			int status = compare(name, other.name);
			if (status != 0)
				return status;
			status = compare(type, other.type);
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
		Application other = (Application) object;
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
		return "Application: type="+type+", name="+name;
	}

}
