package nam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Domain", namespace = "http://nam/model", propOrder = {
    "name",
	"label",
    "version",
	"namespace",
	"description",
	"members"
})
@XmlRootElement(name = "domain", namespace = "http://nam/model")
public class Domain implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "name", namespace = "http://nam/model")
	private String name;
	
	@XmlElement(name = "label", namespace = "http://nam/model")
	private String label;
	
	@XmlElement(name = "version", namespace = "http://nam/model")
	private String version;
	
	@XmlElement(name = "namespace", namespace = "http://nam/model")
	private Namespace namespace;
	
	@XmlElement(name = "description", namespace = "http://nam/model")
	private String description;

    @XmlElements({
		@XmlElement(name = "application", namespace = "http://nam/model", type = Application.class),
		@XmlElement(name = "module", namespace = "http://nam/model", type = Module.class),
        @XmlElement(name = "information", namespace = "http://nam/model", type = Information.class),
        @XmlElement(name = "persistence", namespace = "http://nam/model", type = Persistence.class),
        @XmlElement(name = "service", namespace = "http://nam/model", type = Service.class),
        @XmlElement(name = "listener", namespace = "http://nam/model", type = Listener.class),
        @XmlElement(name = "router", namespace = "http://nam/model", type = Router.class)
    })
	private List<Serializable> members;
	
	@XmlAttribute(name = "ref")
	private String ref;
	
	
	public Domain() {
		members = new ArrayList<Serializable>();
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
	
	public Namespace getNamespace() {
		return namespace;
	}
	
	public void setNamespace(Namespace namespace) {
		this.namespace = namespace;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
			Domain other = (Domain) object;
			int status = compare(name, other.name);
			if (status != 0)
				return status;
			status = compare(label, other.label);
			if (status != 0)
				return status;
			status = compare(version, other.version);
			if (status != 0)
				return status;
			status = compare(description, other.description);
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
		Domain other = (Domain) object;
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
		return "Domain: name="+name+", label="+label+", version="+version+", description="+description;
    }

}
