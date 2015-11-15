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
@XmlType(name = "Repository", namespace = "http://nam/model", propOrder = {
	"name",
	"label",
	"description",
	"properties",
	"members"
})
@XmlRootElement(name = "repository", namespace = "http://nam/model")
public class Repository implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "name", namespace = "http://nam/model")
	private String name;
	
	@XmlElement(name = "label", namespace = "http://nam/model")
	private String label;
	
	@XmlElement(name = "description", namespace = "http://nam/model")
	private String description;
	
	@XmlElement(name = "properties", namespace = "http://nam/model")
	private List<Property> properties;
	
	@XmlElements({
		@XmlElement(name = "unit", namespace = "http://nam/model", type = Unit.class)
	})
	private List<Serializable> members;

    @XmlAttribute(name = "ref")
	private String ref;
	
	
	public Repository() {
		properties = new ArrayList<Property>();
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
	
	@Override
	public int compareTo(Object object) {
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
		Repository other = (Repository) object;
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
		return "Repository: name="+name+", label="+label+", description="+description;
    }

}
