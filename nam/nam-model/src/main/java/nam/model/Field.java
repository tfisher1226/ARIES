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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;
import org.aries.adapter.DateTimeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Field", namespace = "http://nam/model", propOrder = {
	"annotations",
	"creationDate",
	"lastUpdate"
})
@XmlSeeAlso({
    Attribute.class,
    Reference.class
})
@XmlRootElement(name = "field", namespace = "http://nam/model")
public class Field extends Type implements Serializable {

	private static final long serialVersionUID = 1L;
    
    @XmlAttribute(name = "simple")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    private Boolean simple;
    
    @XmlAttribute(name = "unique")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    private Boolean unique;
    
    @XmlAttribute(name = "required")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    private Boolean required;
    
    @XmlAttribute(name = "nullable")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    private Boolean nullable;
    
    @XmlAttribute(name = "transient")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    private Boolean _transient;
    
    @XmlAttribute(name = "volatile")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    private Boolean _volatile;
    
    @XmlAttribute(name = "indexed")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    private Boolean indexed;
    
    @XmlAttribute(name = "derived")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    private Boolean derived;
    
    @XmlAttribute(name = "changeable")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    private Boolean changeable;
    
    @XmlAttribute(name = "unsettable")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    private Boolean unsettable;
    
    @XmlAttribute(name = "ordered")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    private Boolean ordered;
    
    @XmlAttribute(name = "managed")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    private Boolean managed;
    
    @XmlAttribute(name = "minOccurs")
    private Integer minOccurs;
    
    @XmlAttribute(name = "maxOccurs")
    private Integer maxOccurs;
    
    @XmlAttribute(name = "many")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    private Boolean many;
    
    @XmlAttribute(name = "column")
    private String column;
    
    @XmlAttribute(name = "fetch")
    private FetchType fetch;
    
    @XmlAttribute(name = "cascade")
    private String cascade;
    
    @XmlAttribute(name = "enact")
    private String enact;
    
    @XmlAttribute(name = "hash")
    private HashType hash;
    
    @XmlAttribute(name = "default")
    private String _default;
    
    @XmlAttribute(name = "useForLabel")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    private Boolean useForLabel;
    
    @XmlAttribute(name = "useForEquals")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    private Boolean useForEquals;
    
    @XmlAttribute(name = "placeholder")
    private String placeholder;

	@XmlElement(name = "annotations", namespace = "http://nam/model")
	private List<Annotation> annotations;
	
	@XmlElement(name = "creationDate", namespace = "http://nam/model", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date creationDate;
	
	@XmlElement(name = "lastUpdate", namespace = "http://nam/model", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date lastUpdate;
	
	
	public Field() {
		annotations = new ArrayList<Annotation>();
	}
	
	
	public Boolean isSimple() {
		return simple != null && simple;
	}
	
	public Boolean getSimple() {
		return simple != null && simple;
	}
	
	public void setSimple(Boolean simple) {
		this.simple = simple;
	}
	
	public Boolean isUnique() {
		return unique != null && unique;
	}
	
	public Boolean getUnique() {
		return unique != null && unique;
	}
	
	public void setUnique(Boolean unique) {
		this.unique = unique;
	}
	
	public Boolean isNullable() {
		return nullable != null && nullable;
	}
	
	public Boolean getNullable() {
		return nullable != null && nullable;
	}
	
	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}
	
	public Boolean isRequired() {
		return required != null && required;
	}
	
	public Boolean getRequired() {
		return required != null && required;
	}
	
	public void setRequired(Boolean required) {
		this.required = required;
	}
	
	public Boolean isTransient() {
		return _transient != null && _transient;
	}
	
	public Boolean getTransient() {
		return _transient != null && _transient;
	}
	
	public void setTransient(Boolean _transient) {
		this._transient = _transient;
	}
	
	public Boolean isVolatile() {
		return _volatile != null && _volatile;
	}
	
	public Boolean getVolatile() {
		return _volatile != null && _volatile;
	}
	
	public void setVolatile(Boolean _volatile) {
		this._volatile = _volatile;
	}
	
	public Boolean isIndexed() {
		return indexed != null && indexed;
	}
	
	public Boolean getIndexed() {
		return indexed != null && indexed;
	}
	
	public void setIndexed(Boolean indexed) {
		this.indexed = indexed;
	}
	
	public Boolean isDerived() {
		return derived != null && derived;
	}
	
	public Boolean getDerived() {
		return derived != null && derived;
	}
	
	public void setDerived(Boolean derived) {
		this.derived = derived;
	}
	
	public Boolean isChangeable() {
		return changeable != null && changeable;
	}
	
	public Boolean getChangeable() {
		return changeable != null && changeable;
	}
	
	public void setChangeable(Boolean changeable) {
		this.changeable = changeable;
	}
	
	public Boolean isUnsettable() {
		return unsettable != null && unsettable;
	}
	
	public Boolean getUnsettable() {
		return unsettable != null && unsettable;
	}
	
	public void setUnsettable(Boolean unsettable) {
		this.unsettable = unsettable;
	}
	
	public Boolean isOrdered() {
		return ordered != null && ordered;
	}
	
	public Boolean getOrdered() {
		return ordered != null && ordered;
	}
	
	public void setOrdered(Boolean ordered) {
		this.ordered = ordered;
	}
	
	public Boolean isManaged() {
		return managed != null && managed;
	}
	
	public Boolean getManaged() {
		return managed != null && managed;
	}
	
	public void setManaged(Boolean managed) {
		this.managed = managed;
	}
	
	public Integer getMinOccurs() {
		if (minOccurs == null)
			minOccurs = new Integer(0);
		return minOccurs;
	}
	
	public void setMinOccurs(Integer minOccurs) {
		this.minOccurs = minOccurs;
	}
	
	public Integer getMaxOccurs() {
		if (maxOccurs == null)
			maxOccurs = new Integer(0);
		return maxOccurs;
	}
	
	public void setMaxOccurs(Integer maxOccurs) {
		this.maxOccurs = maxOccurs;
	}
	
	public Boolean isMany() {
		return many != null && many;
	}
	
	public Boolean getMany() {
		return many != null && many;
	}
	
	public void setMany(Boolean many) {
		this.many = many;
	}
	
	public String getColumn() {
		return column;
	}
	
	public void setColumn(String column) {
		this.column = column;
	}
	
	public FetchType getFetch() {
		return fetch;
	}
	
	public void setFetch(FetchType fetch) {
		this.fetch = fetch;
	}
	
	public String getCascade() {
		return cascade;
	}
	
	public void setCascade(String cascade) {
		this.cascade = cascade;
	}
	
	public String getEnact() {
		return enact;
	}
	
	public void setEnact(String enact) {
		this.enact = enact;
	}
	
	public HashType getHash() {
		return hash;
	}
	
	public void setHash(HashType hash) {
		this.hash = hash;
	}
	
	public String getDefault() {
		return _default;
	}
	
	public void setDefault(String _default) {
		this._default = _default;
	}
	
	public Boolean isUseForLabel() {
		return useForLabel != null && useForLabel;
	}
	
	public Boolean getUseForLabel() {
		return useForLabel != null && useForLabel;
	}
	
	public void setUseForLabel(Boolean useForLabel) {
		this.useForLabel = useForLabel;
	}
	
	public Boolean isUseForEquals() {
		return useForEquals != null && useForEquals;
	}
	
	public Boolean getUseForEquals() {
		return useForEquals != null && useForEquals;
	}
	
	public void setUseForEquals(Boolean useForEquals) {
		this.useForEquals = useForEquals;
	}
	
	public String getPlaceholder() {
		return placeholder;
	}
	
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
  
	public List<Annotation> getAnnotations() {
		synchronized (annotations) {
			return annotations;
		}
	}
	
	public void setAnnotations(Collection<Annotation> annotations) {
		if (annotations == null) {
			this.annotations = null;
		} else {
		synchronized (this.annotations) {
				this.annotations = new ArrayList<Annotation>();
				addToAnnotations(annotations);
			}
		}
	}

	public void addToAnnotations(Annotation annotations) {
		if (annotations != null ) {
			synchronized (this.annotations) {
				this.annotations.add(annotations);
			}
		}
	}

	public void addToAnnotations(Collection<Annotation> annotationsCollection) {
		if (annotationsCollection != null && !annotationsCollection.isEmpty()) {
			synchronized (this.annotations) {
				this.annotations.addAll(annotationsCollection);
			}
		}
	}

	public void removeFromAnnotations(Annotation annotations) {
		if (annotations != null ) {
			synchronized (this.annotations) {
				this.annotations.remove(annotations);
			}
		}
	}

	public void removeFromAnnotations(Collection<Annotation> annotationsCollection) {
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
			Fault other = (Fault) object;
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
		Field other = (Field) object;
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
		return "Field: name="+name+", type="+type;
	}

}
