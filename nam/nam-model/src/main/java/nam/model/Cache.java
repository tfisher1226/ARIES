package nam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cache", namespace = "http://nam/model", propOrder = {
	"name",
	"label",
	"namespace",
	"description",
    "scope",
    "level",
	"transacted",
	"items",
	"ref"
})
@XmlRootElement(name = "cache", namespace = "http://nam/model")
public class Cache extends Element implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "name", namespace = "http://nam/model")
	private String name;
	
	@XmlElement(name = "label", namespace = "http://nam/model")
	private String label;
	
	@XmlElement(name = "namespace", namespace = "http://nam/model")
	private String namespace;
	
	@XmlElement(name = "description", namespace = "http://nam/model")
	private String description;
	
	@XmlElement(name = "scope", namespace = "http://nam/model")
	private String scope;
	
	@XmlElement(name = "level", namespace = "http://nam/model")
	private String level;
	
	@XmlElement(name = "transacted", namespace = "http://nam/model")
	private Transacted transacted;
	
	@XmlElement(name = "items", namespace = "http://nam/model")
	private Items items;
	
	@XmlAttribute(name = "ref", namespace = "http://nam/model")
	private String ref;
	
	
	public Cache() {
		//nothing for now
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

    public String getScope() {
        return scope;
    }

	public void setScope(String scope) {
		this.scope = scope;
	}
	
    public String getLevel() {
        return level;
    }

	public void setLevel(String level) {
		this.level = level;
	}
	
	public Transacted getTransacted() {
		return transacted;
	}
	
	public void setTransacted(Transacted transacted) {
		this.transacted = transacted;
	}
	
    public Items getItems() {
        return items;
    }

	public void setItems(Items items) {
		this.items = items;
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
		if (!object.getClass().isAssignableFrom(this.getClass())) {
			Cache other = (Cache) object;
		}
		int status = super.compareTo(object);
		return status;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Cache other = (Cache) object;
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
		return "Cache: name="+name+", label="+label+", namespace="+namespace+", description="+description+", scope="+scope+", level="+level;
    }

}
