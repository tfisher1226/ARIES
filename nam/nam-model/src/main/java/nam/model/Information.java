package nam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;
import org.aries.adapter.DateTimeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Information", namespace = "http://nam/model", propOrder = {
    "imports",
    "domain",
    "name",
    "label",
    "version",
    "description",
    "namespaces",
	"creationDate",
	"lastUpdate"
})
@XmlRootElement(name = "information", namespace = "http://nam/model")
public class Information implements Comparable<Object>, Serializable {

    private final static long serialVersionUID = 1L;
    
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
    
    @XmlElement(name = "namespace", namespace = "http://nam/model")
    protected List<Namespace> namespaces;
    
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


    public List<Import> getImports() {
        if (imports == null) {
            imports = new ArrayList<Import>();
        }
        return this.imports;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String value) {
        this.domain = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String value) {
        this.version = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the value of the namespaces property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the namespaces property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNamespaces().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Namespace }
     * 
     * 
     */
    public List<Namespace> getNamespaces() {
        if (namespaces == null) {
            namespaces = new ArrayList<Namespace>();
        }
        return this.namespaces;
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
			Information other = (Information) object;
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
		Information other = (Information) object;
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
		return "Information: name="+name+", domain="+domain;
	}
	

}
