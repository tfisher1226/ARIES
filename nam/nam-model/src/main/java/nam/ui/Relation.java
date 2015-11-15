package nam.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Relation", namespace = "http://nam/ui", propOrder = {
	"name",
	"pattern",
	"container",
	"type"
})
@XmlRootElement(name = "relation", namespace = "http://nam/ui")
public class Relation implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "name", namespace = "http://nam/ui")
	private String name;
	
	@XmlElement(name = "pattern", namespace = "http://nam/ui")
	private String pattern;
	
	@XmlElement(name = "container", namespace = "http://nam/ui")
	private List<String> container;
	
	@XmlElement(name = "type", namespace = "http://nam/ui")
	private List<String> type;
	
	@XmlAttribute(name = "ref")
	private String ref;
	
	
	public Relation() {
		container = new ArrayList<String>();
		type = new ArrayList<String>();
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPattern() {
		return pattern;
	}
	
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	public List<String> getContainer() {
		synchronized (container) {
			return container;
		}
	}
	
	public void setContainer(Collection<String> container) {
		if (container == null) {
			this.container = null;
		} else {
		synchronized (this.container) {
				this.container = new ArrayList<String>();
				addToContainer(container);
			}
		}
	}
	
	public void addToContainer(String container) {
		if (container != null ) {
			synchronized (this.container) {
				this.container.add(container);
			}
		}
	}

	public void addToContainer(Collection<String> containerCollection) {
		if (containerCollection != null && !containerCollection.isEmpty()) {
			synchronized (this.container) {
				this.container.addAll(containerCollection);
			}
		}
	}

	public void removeFromContainer(String container) {
		if (container != null ) {
			synchronized (this.container) {
				this.container.remove(container);
			}
		}
	}
	
	public void removeFromContainer(Collection<String> containerCollection) {
		if (containerCollection != null ) {
			synchronized (this.container) {
				this.container.removeAll(containerCollection);
			}
		}
	}

	public void clearContainer() {
		synchronized (container) {
			container.clear();
		}
	}
	
	public List<String> getType() {
		synchronized (type) {
			return type;
		}
	}
	
	public void setType(Collection<String> type) {
		if (type == null) {
			this.type = null;
		} else {
		synchronized (this.type) {
				this.type = new ArrayList<String>();
				addToType(type);
			}
		}
	}

	public void addToType(String type) {
		if (type != null ) {
			synchronized (this.type) {
				this.type.add(type);
			}
		}
	}

	public void addToType(Collection<String> typeCollection) {
		if (typeCollection != null && !typeCollection.isEmpty()) {
			synchronized (this.type) {
				this.type.addAll(typeCollection);
			}
		}
	}

	public void removeFromType(String type) {
		if (type != null ) {
			synchronized (this.type) {
				this.type.remove(type);
			}
		}
	}

	public void removeFromType(Collection<String> typeCollection) {
		if (typeCollection != null ) {
			synchronized (this.type) {
				this.type.removeAll(typeCollection);
			}
		}
	}

	public void clearType() {
		synchronized (type) {
			type.clear();
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
		return 0;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Relation other = (Relation) object;
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
		return "Relation: name="+name+", pattern="+pattern;
	}
	
}
