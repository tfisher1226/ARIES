package nam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;
import org.aries.adapter.DateTimeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Messaging", namespace = "http://nam/model", propOrder = {
    "imports",
    "domain",
    "name",
    "label",
    "version",
    "description",
    "namespace",
	"members",
	"creationDate",
	"lastUpdate"
})
@XmlRootElement(name = "messaging", namespace = "http://nam/model")
public class Messaging implements Comparable<Object>, Serializable {

	private static final long serialVersionUID = 1L;
    
    @XmlElement(name = "import", namespace = "http://nam/model")
    protected List<Import> imports;
    
    @XmlElement(namespace = "http://nam/model")
    protected String domain;
    
    @XmlElement(namespace = "http://nam/model")
    protected String name;
    
    @XmlElement(namespace = "http://nam/model")
    protected String label;
    
    @XmlElement(namespace = "http://nam/model")
    protected String version;
    
    @XmlElement(namespace = "http://nam/model")
    protected String description;

    @XmlElement(namespace = "http://nam/model")
    protected String namespace;
    
    @XmlElements({
        @XmlElement(name = "resource", namespace = "http://nam/model", type = Resource.class),
        @XmlElement(name = "provider", namespace = "http://nam/model", type = Provider.class),
        @XmlElement(name = "adapter", namespace = "http://nam/model", type = Adapter.class),
        @XmlElement(name = "transports", namespace = "http://nam/model", type = Transports.class),
        @XmlElement(name = "links", namespace = "http://nam/model", type = Links.class),
        @XmlElement(name = "channels", namespace = "http://nam/model", type = Channels.class),
        @XmlElement(name = "listeners", namespace = "http://nam/model", type = Listeners.class),
        @XmlElement(name = "routers", namespace = "http://nam/model", type = Routers.class),
        @XmlElement(name = "interactors", namespace = "http://nam/model", type = Interactors.class)
    })
    protected List<Serializable> members;
    
    @XmlAttribute(name = "imported")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean imported;

    @XmlAttribute(name = "included")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean included;

    @XmlAttribute(name = "exposed")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean exposed;

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
    
    
    /**
     * Gets the value of the imports property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the imports property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImports().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Import }
     * 
     * 
     */
    public List<Import> getImports() {
        if (imports == null) {
            imports = new ArrayList<Import>();
        }
        return this.imports;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String value) {
        this.namespace = value;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the value of the resourcesAndProvidersAndAdapters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resourcesAndProvidersAndAdapters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResourcesAndProvidersAndAdapters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Resource }
     * {@link Provider }
     * {@link Adapter }
     * {@link Transports }
     * {@link Links }
     * {@link Channels }
     * {@link Listeners }
     * {@link Routers }
     * {@link Interactors }
     * 
     * 
     */
    public List<Serializable> getMembers() {
        if (members == null) {
            members = new ArrayList<Serializable>();
        }
        return this.members;
    }

    public Boolean getImported() {
        if (imported == null) {
            return new Boolean(false);
        } else {
            return imported;
        }
    }

    public void setImported(Boolean value) {
        this.imported = value;
    }

    public Boolean getIncluded() {
        if (included == null) {
            return new Boolean(false);
        } else {
            return included;
        }
    }

    public void setIncluded(Boolean included) {
        this.included = included;
    }

    public Boolean getExposed() {
        if (exposed == null) {
            return new Boolean(false);
        } else {
            return exposed;
        }
    }

    public void setExposed(Boolean exposed) {
        this.exposed = exposed;
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
			Messaging other = (Messaging) object;
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
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Messaging other = (Messaging) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (name != null)
			hashCode += name.hashCode();
		if (domain != null)
			hashCode += domain.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Messaging: name="+name+", domain="+domain;
	}
	
}
