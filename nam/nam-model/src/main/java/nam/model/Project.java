package nam.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;
import org.aries.adapter.DateAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Project", namespace = "http://nam/model", propOrder = {

})
@XmlRootElement(name = "project", namespace = "http://nam/model")
public class Project implements Comparable<Object>, Serializable {

    private final static long serialVersionUID = 1L;
    
    @XmlElement(namespace = "http://nam/model")
    protected Long id;
    
    @XmlElement(namespace = "http://nam/model")
    protected String name;
    
    @XmlElement(namespace = "http://nam/model")
    protected String label;
    
    @XmlElement(namespace = "http://nam/model")
    protected String domain;
    
    @XmlElement(namespace = "http://nam/model")
    protected String namespace;
    
    @XmlElement(namespace = "http://nam/model")
    protected String description;
    
    @XmlElement(namespace = "http://nam/model")
    protected String version;
    
    @XmlElement(namespace = "http://nam/model")
    protected String owner;
    
    @XmlElement(namespace = "http://nam/model", type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean enabled;
    
    @XmlElement(namespace = "http://nam/model", type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean shared;
    
    @XmlElement(namespace = "http://nam/model", type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean webEnabled;
    
    @XmlElement(namespace = "http://nam/model", type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean singleProject;
    
    @XmlElement(namespace = "http://nam/model")
    protected Imports imports;
    
    @XmlElement(namespace = "http://nam/model")
    protected Projects subProjects;
    
    @XmlElement(namespace = "http://nam/model")
    protected Configurations configurations;
    
    @XmlElement(namespace = "http://nam/model")
    protected Applications applications;
    
    @XmlElement(namespace = "http://nam/model")
    protected Groups groups;
    
    @XmlElement(namespace = "http://nam/model")
    protected Modules modules;
    
    @XmlElement(namespace = "http://nam/model")
    protected Providers providers;
    
    @XmlElement(namespace = "http://nam/model")
    protected Placeholders placeholders;
    
    @XmlElement(namespace = "http://nam/model", required = true)
    protected Extensions extensions;

    @XmlElement(namespace = "http://nam/model", required = true)
    protected Networks networks;

    @XmlElement(namespace = "http://nam/model", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date creationDate;
    
    @XmlElement(namespace = "http://nam/model", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date lastUpdate;
    
    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

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
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomain(String value) {
        this.domain = value;
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

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
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
     * Gets the value of the owner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwner(String value) {
        this.owner = value;
    }

    /**
     * Gets the value of the enabled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the shared property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getShared() {
        return shared;
    }

    /**
     * Sets the value of the shared property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShared(Boolean value) {
        this.shared = value;
    }

    /**
     * Gets the value of the webEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getWebEnabled() {
        return webEnabled;
    }

    /**
     * Sets the value of the webEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebEnabled(Boolean value) {
        this.webEnabled = value;
    }

    /**
     * Gets the value of the singleProject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getSingleProject() {
        return singleProject;
    }

    /**
     * Sets the value of the singleProject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSingleProject(Boolean value) {
        this.singleProject = value;
    }

    /**
     * Gets the value of the imports property.
     * 
     * @return
     *     possible object is
     *     {@link Imports }
     *     
     */
    public Imports getImports() {
        return imports;
    }

    /**
     * Sets the value of the imports property.
     * 
     * @param value
     *     allowed object is
     *     {@link Imports }
     *     
     */
    public void setImports(Imports value) {
        this.imports = value;
    }

    /**
     * Gets the value of the subProjects property.
     * 
     * @return
     *     possible object is
     *     {@link Projects }
     *     
     */
    public Projects getSubProjects() {
        return subProjects;
    }

    /**
     * Sets the value of the subProjects property.
     * 
     * @param value
     *     allowed object is
     *     {@link Projects }
     *     
     */
    public void setSubProjects(Projects value) {
        this.subProjects = value;
    }

    /**
     * Gets the value of the configurations property.
     * 
     * @return
     *     possible object is
     *     {@link Configurations }
     *     
     */
    public Configurations getConfigurations() {
        return configurations;
    }

    /**
     * Sets the value of the configurations property.
     * 
     * @param value
     *     allowed object is
     *     {@link Configurations }
     *     
     */
    public void setConfigurations(Configurations value) {
        this.configurations = value;
    }

    /**
     * Gets the value of the applications property.
     * 
     * @return
     *     possible object is
     *     {@link Applications }
     *     
     */
    public Applications getApplications() {
        return applications;
    }

    /**
     * Sets the value of the applications property.
     * 
     * @param value
     *     allowed object is
     *     {@link Applications }
     *     
     */
    public void setApplications(Applications value) {
        this.applications = value;
    }

    /**
     * Gets the value of the groups property.
     * 
     * @return
     *     possible object is
     *     {@link Groups }
     *     
     */
    public Groups getGroups() {
        return groups;
    }

    /**
     * Sets the value of the groups property.
     * 
     * @param value
     *     allowed object is
     *     {@link Groups }
     *     
     */
    public void setGroups(Groups value) {
        this.groups = value;
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
     * Gets the value of the providers property.
     * 
     * @return
     *     possible object is
     *     {@link Providers }
     *     
     */
    public Providers getProviders() {
        return providers;
    }

    /**
     * Sets the value of the providers property.
     * 
     * @param value
     *     allowed object is
     *     {@link Providers }
     *     
     */
    public void setProviders(Providers value) {
        this.providers = value;
    }

    /**
     * Gets the value of the placeholders property.
     * 
     * @return
     *     possible object is
     *     {@link Placeholders }
     *     
     */
    public Placeholders getPlaceholders() {
        return placeholders;
    }

    /**
     * Sets the value of the placeholders property.
     * 
     * @param value
     *     allowed object is
     *     {@link Placeholders }
     *     
     */
    public void setPlaceholders(Placeholders value) {
        this.placeholders = value;
    }

    /**
     * Gets the value of the creationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the value of the creationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreationDate(Date value) {
        this.creationDate = value;
    }

    /**
     * Gets the value of the lastUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the value of the lastUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdate(Date value) {
        this.lastUpdate = value;
    }

    public Extensions getExtensions() {
        return extensions;
    }

    public void setExtensions(Extensions value) {
        this.extensions = value;
    }

    public Networks getNetworks() {
        return networks;
    }

    public void setNetworks(Networks networks) {
        this.networks = networks;
    }


	@Override
	public int compareTo(Object object) {
		if (object.getClass().isAssignableFrom(this.getClass())) {
			Project other = (Project) object;
			int status = compare(name, other.name);
			if (status != 0)
				return status;
			status = compare(domain, other.domain);
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
		Project other = (Project) object;
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
		return "Project: name="+name+", domain="+domain;
	}

}
