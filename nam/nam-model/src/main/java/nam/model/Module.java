//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.11 at 12:10:41 PM PDT 
//


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

import org.aries.adapter.BooleanAdapter;
import org.aries.adapter.DateAdapter;
import org.aries.validate.Checkpoints;


/**
 * <p>Java class for Module complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Module">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="type" type="{http://nam/model}ModuleType" minOccurs="0"/>
 *         &lt;element name="level" type="{http://nam/model}ModuleLevel" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="label" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="groupId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="artifactId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="web-enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="namespace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="packaging" type="{http://nam/model}PackageType" minOccurs="0"/>
 *         &lt;element name="creator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://nam/model}properties" minOccurs="0"/>
 *         &lt;element ref="{http://nam/model}dependencies" minOccurs="0"/>
 *         &lt;element ref="{http://nam/model}configuration" minOccurs="0"/>
 *         &lt;element ref="{http://nam/model}interactors" minOccurs="0"/>
 *         &lt;element ref="{http://nam/model}resources" minOccurs="0"/>
 *         &lt;element ref="{http://nam/model}information" minOccurs="0"/>
 *         &lt;element ref="{http://nam/model}services" minOccurs="0"/>
 *         &lt;element ref="{http://nam/model}processes" minOccurs="0"/>
 *         &lt;element ref="{http://nam/model}components" minOccurs="0"/>
 *         &lt;element ref="{http://nam/model}persistence" minOccurs="0"/>
 *         &lt;element ref="{http://nam/model}view" minOccurs="0"/>
 *         &lt;element ref="{http://www.aries.org/validate}checkpoints" minOccurs="0"/>
 *         &lt;element name="creationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="lastUpdate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/all>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Module", namespace = "http://nam/model", propOrder = {

})
@XmlRootElement(name = "module", namespace = "http://nam/model")
public class Module implements Serializable
{

    private final static long serialVersionUID = 1L;
    
    @XmlElement(namespace = "http://nam/model")
    protected ModuleType type;
    
    @XmlElement(namespace = "http://nam/model")
    protected ModuleLevel level;
    
    @XmlElement(namespace = "http://nam/model")
    protected String name;
    
    @XmlElement(namespace = "http://nam/model")
    protected String label;
    
    @XmlElement(namespace = "http://nam/model")
    protected String description;
    
    @XmlElement(namespace = "http://nam/model")
    protected String groupId;
    
    @XmlElement(namespace = "http://nam/model")
    protected String artifactId;
    
    @XmlElement(namespace = "http://nam/model")
    protected String version;
    
    @XmlElement(name = "web-enabled", namespace = "http://nam/model", type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean webEnabled;
    
    @XmlElement(namespace = "http://nam/model")
    protected String namespace;
    
    @XmlElement(namespace = "http://nam/model")
    protected PackageType packaging;
    
    @XmlElement(namespace = "http://nam/model")
    protected String creator;
    
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
    
    @XmlElement(namespace = "http://nam/model", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date creationDate;
    
    @XmlElement(namespace = "http://nam/model", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date lastUpdate;
    
    @XmlAttribute(name = "id")
    protected Long id;
    
    @XmlAttribute(name = "ref")
    protected String ref;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ModuleType }
     *     
     */
    public ModuleType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModuleType }
     *     
     */
    public void setType(ModuleType value) {
        this.type = value;
    }

    /**
     * Gets the value of the level property.
     * 
     * @return
     *     possible object is
     *     {@link ModuleLevel }
     *     
     */
    public ModuleLevel getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModuleLevel }
     *     
     */
    public void setLevel(ModuleLevel value) {
        this.level = value;
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
     * Gets the value of the packaging property.
     * 
     * @return
     *     possible object is
     *     {@link PackageType }
     *     
     */
    public PackageType getPackaging() {
        return packaging;
    }

    /**
     * Sets the value of the packaging property.
     * 
     * @param value
     *     allowed object is
     *     {@link PackageType }
     *     
     */
    public void setPackaging(PackageType value) {
        this.packaging = value;
    }

    /**
     * Gets the value of the creator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Sets the value of the creator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreator(String value) {
        this.creator = value;
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
     * Gets the value of the ref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRef() {
        return ref;
    }

    /**
     * Sets the value of the ref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRef(String value) {
        this.ref = value;
    }

}