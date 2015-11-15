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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;
import org.aries.adapter.DateTimeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Network", namespace = "http://nam/model", propOrder = {
	"imports",
	"domain",
	"name",
	"label",
	"version",
	"description",
	"namespace",
	"initialSize",
	"minSize",
	"maxSize",
	"members",
	"imported",
	"included",
	"exposed",
	"creationDate",
	"lastUpdate"
})
@XmlRootElement(name = "network", namespace = "http://nam/model")
public class Network implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "imports", namespace = "http://nam/model")
	private List<Import> imports;
	
	@XmlElement(name = "domain", namespace = "http://nam/model")
	private String domain;
	
	@XmlElement(name = "name", namespace = "http://nam/model")
	private String name;
	
	@XmlElement(name = "label", namespace = "http://nam/model")
	private String label;
	
	@XmlElement(name = "version", namespace = "http://nam/model")
	private String version;
	
	@XmlElement(name = "description", namespace = "http://nam/model")
	private String description;
	
	@XmlElement(name = "namespace", namespace = "http://nam/model")
	private String namespace;
	
	@XmlElement(name = "initialSize", namespace = "http://nam/model")
	private Integer initialSize;
	
	@XmlElement(name = "minSize", namespace = "http://nam/model")
	private Integer minSize;
	
	@XmlElement(name = "maxSize", namespace = "http://nam/model")
	private Integer maxSize;
	
	@XmlElements({
		@XmlElement(name = "master", namespace = "http://nam/model", type = Master.class),
		@XmlElement(name = "minion", namespace = "http://nam/model", type = Minion.class)
	})
	private List<Serializable> members;
	
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
	private String ref;
	
	
	public Network() {
		imports = new ArrayList<Import>();
		members = new ArrayList<Serializable>();
	}
	
	
	public List<Import> getImports() {
		synchronized (imports) {
			return imports;
		}
	}
	
	public void setImports(Collection<Import> imports) {
		if (imports == null) {
			this.imports = null;
		} else {
		synchronized (this.imports) {
				this.imports = new ArrayList<Import>();
				addToImports(imports);
			}
		}
	}

	public void addToImports(Import _import) {
		if (_import != null ) {
			synchronized (this.imports) {
				this.imports.add(_import);
			}
		}
	}

	public void addToImports(Collection<Import> importCollection) {
		if (importCollection != null && !importCollection.isEmpty()) {
			synchronized (this.imports) {
				this.imports.addAll(importCollection);
			}
		}
	}
	
	public void removeFromImports(Import _import) {
		if (_import != null ) {
			synchronized (this.imports) {
				this.imports.remove(_import);
			}
		}
	}

	public void removeFromImports(Collection<Import> importCollection) {
		if (importCollection != null ) {
			synchronized (this.imports) {
				this.imports.removeAll(importCollection);
			}
		}
	}

	public void clearImports() {
		synchronized (imports) {
			imports.clear();
		}
	}
	
	public String getDomain() {
		return domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
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
	
	public String getNamespace() {
		return namespace;
	}
	
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	public Integer getInitialSize() {
		return initialSize;
	}
	
	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}
	
	public Integer getMinSize() {
		return minSize;
	}
	
	public void setMinSize(Integer minSize) {
		this.minSize = minSize;
	}
	
	public Integer getMaxSize() {
		return maxSize;
	}
	
	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}
	
	public List<Serializable> getMembers() {
		synchronized (members) {
			return members;
		}
	}
	
	public void setMembers(Collection<Serializable> members) {
		if (members == null) {
			this.members = null;
		} else {
		synchronized (this.members) {
				this.members = new ArrayList<Serializable>();
				addToMembers(members);
			}
		}
	}

	public void addToMembers(Serializable serializable) {
		if (serializable != null ) {
			synchronized (this.members) {
				this.members.add(serializable);
			}
		}
	}

	public void addToMembers(Collection<Serializable> serializableCollection) {
		if (serializableCollection != null && !serializableCollection.isEmpty()) {
			synchronized (this.members) {
				this.members.addAll(serializableCollection);
			}
		}
	}

	public void removeFromMembers(Serializable serializable) {
		if (serializable != null ) {
			synchronized (this.members) {
				this.members.remove(serializable);
			}
		}
	}

	public void removeFromMembers(Collection<Serializable> serializableCollection) {
		if (serializableCollection != null ) {
			synchronized (this.members) {
				this.members.removeAll(serializableCollection);
			}
		}
	}

	public void clearMembers() {
		synchronized (members) {
			members.clear();
		}
	}
	
	public Boolean isImported() {
		return imported != null && imported;
	}
	
	public Boolean getImported() {
		return imported != null && imported;
	}
	
	public void setImported(Boolean imported) {
		this.imported = imported;
	}
	
	public Boolean isIncluded() {
		return included != null && included;
	}
	
	public Boolean getIncluded() {
		return included != null && included;
	}
	
	public void setIncluded(Boolean included) {
		this.included = included;
	}
	
	public Boolean isExposed() {
		return exposed != null && exposed;
	}
	
	public Boolean getExposed() {
		return exposed != null && exposed;
	}
	
	public void setExposed(Boolean exposed) {
		this.exposed = exposed;
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
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	@Override
	public int compareTo(Object object) {
		if (object.getClass().isAssignableFrom(this.getClass())) {
			Network other = (Network) object;
			int status = compare(domain, other.domain);
			if (status != 0)
				return status;
			status = compare(name, other.name);
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
		Network other = (Network) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (domain != null)
			hashCode += domain.hashCode();
		if (name != null)
			hashCode += name.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Network: domain="+domain+", name="+name;
	}
	
}
