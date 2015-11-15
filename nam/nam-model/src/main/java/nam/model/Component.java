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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;
import org.aries.adapter.DateTimeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Component", namespace = "http://nam/model", propOrder = {
	"name",
	"label",
	"type",
	"baseType",
	"description",
	"namespace",
	"version",
	"element",
	"packageName",
	"interfaceName",
	"className",
	"published",
	"cached",
	"transacted",
    "annotations",
	"components",
	"operations",
    "fields",
	"creationDate",
	"lastUpdate",
	"ref"
})
@XmlRootElement(name = "component", namespace = "http://nam/model")
public class Component implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "name", namespace = "http://nam/model", required = true)
	private String name;
	
	@XmlElement(name = "label", namespace = "http://nam/model", required = true)
	private String label;
	
	@XmlElement(name = "type", namespace = "http://nam/model", required = true)
	private String type;
	
	@XmlElement(name = "baseType", namespace = "http://nam/model")
	private String baseType;
	
	@XmlElement(name = "description", namespace = "http://nam/model")
	private String description;
	
	@XmlElement(name = "namespace", namespace = "http://nam/model")
	private String namespace;
	
	@XmlElement(name = "version", namespace = "http://nam/model")
	private String version;
	
	@XmlElement(name = "element", namespace = "http://nam/model")
	private String element;
	
	@XmlElement(name = "packageName", namespace = "http://nam/model")
	private String packageName;
	
	@XmlElement(name = "interfaceName", namespace = "http://nam/model")
	private String interfaceName;
	
	@XmlElement(name = "className", namespace = "http://nam/model")
	private String className;
	
	@XmlElement(name = "published", namespace = "http://nam/model", type = String.class)
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean published;
	
	@XmlElement(name = "cached", namespace = "http://nam/model", type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
	private Boolean cached;
	
	@XmlElement(name = "transacted", namespace = "http://nam/model")
	private Transacted transacted;
	
	@XmlElement(name = "annotations", namespace = "http://nam/model")
	private List<String> annotations;
	
	@XmlElement(name = "components", namespace = "http://nam/model")
	private List<Component> components;
	
	@XmlElement(name = "operations", namespace = "http://nam/model")
	private List<Operation> operations;
	
	@XmlElement(name = "fields", namespace = "http://nam/model")
	private List<String> fields;
	
	@XmlElement(name = "creationDate", namespace = "http://nam/model", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date creationDate;
	
	@XmlElement(name = "lastUpdate", namespace = "http://nam/model", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date lastUpdate;
	
	@XmlAttribute(name = "ref", namespace = "http://nam/model")
	private String ref;
	
	
	public Component() {
		annotations = new ArrayList<String>();
		components = new ArrayList<Component>();
		operations = new ArrayList<Operation>();
		fields = new ArrayList<String>();
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
	
    public String getType() {
        return type;
    }

	public void setType(String type) {
		this.type = type;
	}
	
	public String getBaseType() {
		return baseType;
	}
	
	public void setBaseType(String baseType) {
		this.baseType = baseType;
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
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getElement() {
		return element;
	}
	
	public void setElement(String element) {
		this.element = element;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String getInterfaceName() {
		return interfaceName;
	}
	
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public Boolean isPublished() {
		return published != null && published;
	}
	
	public Boolean getPublished() {
		return published != null && published;
	}
	
	public void setPublished(Boolean published) {
		this.published = published;
	}
	
	public Boolean isCached() {
		return cached != null && cached;
    }

    public Boolean getCached() {
		return cached != null && cached;
    }

	public void setCached(Boolean cached) {
		this.cached = cached;
	}
	
	public Transacted getTransacted() {
		return transacted;
	}
	
	public void setTransacted(Transacted transacted) {
		this.transacted = transacted;
	}
	
	public List<String> getAnnotations() {
		synchronized (annotations) {
			return annotations;
		}
	}
	
	public void setAnnotations(Collection<String> annotations) {
		if (annotations == null) {
			this.annotations = null;
		} else {
		synchronized (this.annotations) {
				this.annotations = new ArrayList<String>();
				addToAnnotations(annotations);
			}
		}
	}

	public void addToAnnotations(String annotations) {
		if (annotations != null ) {
			synchronized (this.annotations) {
				this.annotations.add(annotations);
			}
		}
	}

	public void addToAnnotations(Collection<String> annotationsCollection) {
		if (annotationsCollection != null && !annotationsCollection.isEmpty()) {
			synchronized (this.annotations) {
				this.annotations.addAll(annotationsCollection);
			}
		}
	}

	public void removeFromAnnotations(String annotations) {
		if (annotations != null ) {
			synchronized (this.annotations) {
				this.annotations.remove(annotations);
			}
		}
	}

	public void removeFromAnnotations(Collection<String> annotationsCollection) {
		if (annotationsCollection != null ) {
			synchronized (this.annotations) {
				this.annotations.removeAll(annotationsCollection);
			}
		}
	}

	public void clearAnnotations() {
		synchronized (annotations) {
			annotations.clear();
		}
	}
	
	public List<Component> getComponents() {
		synchronized (components) {
			return components;
		}
	}
	
	public void setComponents(Collection<Component> components) {
		if (components == null) {
			this.components = null;
		} else {
		synchronized (this.components) {
				this.components = new ArrayList<Component>();
				addToComponents(components);
			}
		}
	}

	public void addToComponents(Component component) {
		if (component != null ) {
			synchronized (this.components) {
				this.components.add(component);
			}
		}
	}

	public void addToComponents(Collection<Component> componentCollection) {
		if (componentCollection != null && !componentCollection.isEmpty()) {
			synchronized (this.components) {
				this.components.addAll(componentCollection);
			}
		}
	}

	public void removeFromComponents(Component component) {
		if (component != null ) {
			synchronized (this.components) {
				this.components.remove(component);
			}
		}
	}

	public void removeFromComponents(Collection<Component> componentCollection) {
		if (componentCollection != null ) {
			synchronized (this.components) {
				this.components.removeAll(componentCollection);
			}
		}
	}

	public void clearComponents() {
		synchronized (components) {
			components.clear();
		}
	}
	
	public List<Operation> getOperations() {
		synchronized (operations) {
			return operations;
		}
	}
	
	public void setOperations(Collection<Operation> operations) {
		if (operations == null) {
			this.operations = null;
		} else {
		synchronized (this.operations) {
				this.operations = new ArrayList<Operation>();
				addToOperations(operations);
			}
		}
	}

	public void addToOperations(Operation operation) {
		if (operation != null ) {
			synchronized (this.operations) {
				this.operations.add(operation);
			}
		}
	}

	public void addToOperations(Collection<Operation> operationCollection) {
		if (operationCollection != null && !operationCollection.isEmpty()) {
			synchronized (this.operations) {
				this.operations.addAll(operationCollection);
			}
		}
	}

	public void removeFromOperations(Operation operation) {
		if (operation != null ) {
			synchronized (this.operations) {
				this.operations.remove(operation);
			}
		}
	}

	public void removeFromOperations(Collection<Operation> operationCollection) {
		if (operationCollection != null ) {
			synchronized (this.operations) {
				this.operations.removeAll(operationCollection);
			}
		}
	}

	public void clearOperations() {
		synchronized (operations) {
			operations.clear();
		}
	}
	
	public List<String> getFields() {
		synchronized (fields) {
			return fields;
		}
	}
	
	public void setFields(Collection<String> fields) {
		if (fields == null) {
			this.fields = null;
		} else {
		synchronized (this.fields) {
				this.fields = new ArrayList<String>();
				addToFields(fields);
			}
		}
	}

	public void addToFields(String fields) {
		if (fields != null ) {
			synchronized (this.fields) {
				this.fields.add(fields);
			}
		}
	}

	public void addToFields(Collection<String> fieldsCollection) {
		if (fieldsCollection != null && !fieldsCollection.isEmpty()) {
			synchronized (this.fields) {
				this.fields.addAll(fieldsCollection);
			}
		}
	}

	public void removeFromFields(String fields) {
		if (fields != null ) {
			synchronized (this.fields) {
				this.fields.remove(fields);
			}
		}
	}

	public void removeFromFields(Collection<String> fieldsCollection) {
		if (fieldsCollection != null ) {
			synchronized (this.fields) {
				this.fields.removeAll(fieldsCollection);
			}
		}
	}

	public void clearFields() {
		synchronized (fields) {
			fields.clear();
		}
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
			Component other = (Component) object;
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
		Component other = (Component) object;
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
		return "Component: name="+name+", type="+type;
    }

}
