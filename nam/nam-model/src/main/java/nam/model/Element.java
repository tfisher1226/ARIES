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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;
import org.aries.adapter.DateTimeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Element", namespace = "http://nam/model", propOrder = {
    "parentType",
    "superTypes",
	"subTypes",
	"referencedBy",
	"containedBy",
	"annotations",
	"idsAndItemsAndSecrets",
	"attributesAndReferencesAndGroups",
	"creationDate",
	"lastUpdate"
})
@XmlSeeAlso({
    Cache.class
})
@XmlRootElement(name = "element", namespace = "http://nam/model")
public class Element extends Type implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "root")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
	private Boolean root;
    
	@XmlAttribute(name = "abstract")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
	private Boolean _abstract;
    
	@XmlAttribute(name = "transient")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
	private Boolean _transient;
    
	@XmlAttribute(name = "persisted")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
	private Boolean persisted;
	
	@XmlAttribute(name = "hierarchical")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
	private Boolean hierarchical;
    
	@XmlAttribute(name = "sortable")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
	private Boolean sortable;

	@XmlAttribute(name = "extends")
	private String _extends;
    
	@XmlElement(name = "parentType", namespace = "http://nam/model")
	private String parentType;
    
	@XmlElement(name = "superTypes", namespace = "http://nam/model")
	private List<String> superTypes;
    
	@XmlElement(name = "subTypes", namespace = "http://nam/model")
	private List<String> subTypes;
    
	@XmlElement(name = "referencedBy", namespace = "http://nam/model")
	private List<ReferencedBy> referencedBy;
	
	@XmlElement(name = "containedBy", namespace = "http://nam/model")
	private List<ContainedBy> containedBy;
	
	@XmlElement(name = "annotations", namespace = "http://nam/model")
	private List<Annotation> annotations;
	
    @XmlElements({
        @XmlElement(name = "id", namespace = "http://nam/model", type = Id.class),
        @XmlElement(name = "item", namespace = "http://nam/model", type = Item.class),
        @XmlElement(name = "secret", namespace = "http://nam/model", type = Secret.class),
        @XmlElement(name = "listItem", namespace = "http://nam/model", type = ListItem.class),
        @XmlElement(name = "setItem", namespace = "http://nam/model", type = SetItem.class),
        @XmlElement(name = "mapItem", namespace = "http://nam/model", type = MapItem.class),
        @XmlElement(name = "grouping", namespace = "http://nam/model", type = Grouping.class)
    })
	private List<Serializable> idsAndItemsAndSecrets;
    
    @XmlElements({
        @XmlElement(name = "attribute", namespace = "http://nam/model", type = Attribute.class),
        @XmlElement(name = "reference", namespace = "http://nam/model", type = Reference.class),
		@XmlElement(name = "grouping", namespace = "http://nam/model", type = Grouping.class)
    })
	private List<Serializable> attributesAndReferencesAndGroups;
    
	@XmlElement(name = "creationDate", namespace = "http://nam/model", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date creationDate;
	
	@XmlElement(name = "lastUpdate", namespace = "http://nam/model", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date lastUpdate;
	
	
	public Element() {
		superTypes = new ArrayList<String>();
		subTypes = new ArrayList<String>();
		referencedBy = new ArrayList<ReferencedBy>();
		containedBy = new ArrayList<ContainedBy>();
		annotations = new ArrayList<Annotation>();
		idsAndItemsAndSecrets = new ArrayList<Serializable>();
		attributesAndReferencesAndGroups = new ArrayList<Serializable>();
	}
	
	
	public Boolean isRoot() {
		return root != null && root;
    }

	public Boolean getRoot() {
		return root != null && root;
	}
	
	public void setRoot(Boolean root) {
		this.root = root;
	}
	
	public Boolean isAbstract() {
		return _abstract != null && _abstract;
	}
	
	public Boolean getAbstract() {
		return _abstract != null && _abstract;
        }
	
	public void setAbstract(Boolean _abstract) {
		this._abstract = _abstract;
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
	
	public Boolean isPersisted() {
		return persisted != null && persisted;
	}
	
	public Boolean getPersisted() {
		return persisted != null && persisted;
	}
	
	public void setPersisted(Boolean persisted) {
		this.persisted = persisted;
	}
	
	public Boolean isHierarchical() {
		return hierarchical != null && hierarchical;
	}
	
	public Boolean getHierarchical() {
		return hierarchical != null && hierarchical;
	}
	
	public void setHierarchical(Boolean hierarchical) {
		this.hierarchical = hierarchical;
	}
	
	public Boolean isSortable() {
		return sortable != null && sortable;
	}
	
	public Boolean getSortable() {
		return sortable != null && sortable;
	}
	
	public void setSortable(Boolean sortable) {
		this.sortable = sortable;
	}
	
	public String getExtends() {
		return _extends;
	}
	
	public void setExtends(String _extends) {
		this._extends = _extends;
    }

    public String getParentType() {
        return parentType;
    }

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}
	
    public List<String> getSuperTypes() {
		synchronized (superTypes) {
			return superTypes;
		}
	}
	
	public void setSuperTypes(Collection<String> superTypes) {
        if (superTypes == null) {
			this.superTypes = null;
		} else {
		synchronized (this.superTypes) {
				this.superTypes = new ArrayList<String>();
				addToSuperTypes(superTypes);
			}
		}
	}

	public void addToSuperTypes(String superTypes) {
		if (superTypes != null ) {
			synchronized (this.superTypes) {
				this.superTypes.add(superTypes);
			}
		}
	}

	public void addToSuperTypes(Collection<String> superTypesCollection) {
		if (superTypesCollection != null && !superTypesCollection.isEmpty()) {
			synchronized (this.superTypes) {
				this.superTypes.addAll(superTypesCollection);
			}
		}
	}

	public void removeFromSuperTypes(String superTypes) {
		if (superTypes != null ) {
			synchronized (this.superTypes) {
				this.superTypes.remove(superTypes);
			}
		}
	}

	public void removeFromSuperTypes(Collection<String> superTypesCollection) {
		if (superTypesCollection != null ) {
			synchronized (this.superTypes) {
				this.superTypes.removeAll(superTypesCollection);
			}
		}
	}

	public void clearSuperTypes() {
		synchronized (superTypes) {
			superTypes.clear();
        }
    }

    public List<String> getSubTypes() {
		synchronized (subTypes) {
			return subTypes;
		}
	}
	
	public void setSubTypes(Collection<String> subTypes) {
        if (subTypes == null) {
			this.subTypes = null;
		} else {
		synchronized (this.subTypes) {
				this.subTypes = new ArrayList<String>();
				addToSubTypes(subTypes);
			}
        }
    }

	public void addToSubTypes(String subTypes) {
		if (subTypes != null ) {
			synchronized (this.subTypes) {
				this.subTypes.add(subTypes);
			}
		}
    }

	public void addToSubTypes(Collection<String> subTypesCollection) {
		if (subTypesCollection != null && !subTypesCollection.isEmpty()) {
			synchronized (this.subTypes) {
				this.subTypes.addAll(subTypesCollection);
			}
		}
    }

	public void removeFromSubTypes(String subTypes) {
		if (subTypes != null ) {
			synchronized (this.subTypes) {
				this.subTypes.remove(subTypes);
			}
        }
    }

	public void removeFromSubTypes(Collection<String> subTypesCollection) {
		if (subTypesCollection != null ) {
			synchronized (this.subTypes) {
				this.subTypes.removeAll(subTypesCollection);
			}
        }
    }

	public void clearSubTypes() {
		synchronized (subTypes) {
			subTypes.clear();
		}
	}
	
	public List<ReferencedBy> getReferencedBy() {
		synchronized (referencedBy) {
			return referencedBy;
		}
	}
	
	public void setReferencedBy(Collection<ReferencedBy> referencedBy) {
		if (referencedBy == null) {
			this.referencedBy = null;
		} else {
		synchronized (this.referencedBy) {
				this.referencedBy = new ArrayList<ReferencedBy>();
				addToReferencedBy(referencedBy);
			}
		}
	}

	public void addToReferencedBy(ReferencedBy referencedBy) {
		if (referencedBy != null ) {
			synchronized (this.referencedBy) {
				this.referencedBy.add(referencedBy);
			}
		}
	}

	public void addToReferencedBy(Collection<ReferencedBy> referencedByCollection) {
		if (referencedByCollection != null && !referencedByCollection.isEmpty()) {
			synchronized (this.referencedBy) {
				this.referencedBy.addAll(referencedByCollection);
			}
		}
	}

	public void removeFromReferencedBy(ReferencedBy referencedBy) {
		if (referencedBy != null ) {
			synchronized (this.referencedBy) {
				this.referencedBy.remove(referencedBy);
			}
		}
	}

	public void removeFromReferencedBy(Collection<ReferencedBy> referencedByCollection) {
		if (referencedByCollection != null ) {
			synchronized (this.referencedBy) {
				this.referencedBy.removeAll(referencedByCollection);
			}
		}
	}

	public void clearReferencedBy() {
		synchronized (referencedBy) {
			referencedBy.clear();
		}
	}
	
	public List<ContainedBy> getContainedBy() {
		synchronized (containedBy) {
			return containedBy;
		}
	}
	
	public void setContainedBy(Collection<ContainedBy> containedBy) {
		if (containedBy == null) {
			this.containedBy = null;
		} else {
			synchronized (this.containedBy) {
				this.containedBy = new ArrayList<ContainedBy>();
				addToContainedBy(containedBy);
			}
		}
	}

	public void addToContainedBy(ContainedBy containedBy) {
		if (containedBy != null ) {
			synchronized (this.containedBy) {
				this.containedBy.add(containedBy);
			}
		}
	}

	public void addToContainedBy(Collection<ContainedBy> containedByCollection) {
		if (containedByCollection != null && !containedByCollection.isEmpty()) {
			synchronized (this.containedBy) {
				this.containedBy.addAll(containedByCollection);
			}
		}
	}

	public void removeFromContainedBy(ContainedBy containedBy) {
		if (containedBy != null ) {
			synchronized (this.containedBy) {
				this.containedBy.remove(containedBy);
			}
		}
	}

	public void removeFromContainedBy(Collection<ContainedBy> containedByCollection) {
		if (containedByCollection != null ) {
			synchronized (this.containedBy) {
				this.containedBy.removeAll(containedByCollection);
			}
		}
	}

	public void clearContainedBy() {
		synchronized (containedBy) {
			containedBy.clear();
		}
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

	public void addToAnnotations(Annotation annotation) {
		if (annotation != null ) {
			synchronized (this.annotations) {
				this.annotations.add(annotation);
			}
		}
	}

	public void addToAnnotations(Collection<Annotation> annotationCollection) {
		if (annotationCollection != null && !annotationCollection.isEmpty()) {
			synchronized (this.annotations) {
				this.annotations.addAll(annotationCollection);
			}
		}
	}

	public void removeFromAnnotations(Annotation annotation) {
		if (annotation != null ) {
			synchronized (this.annotations) {
				this.annotations.remove(annotation);
			}
		}
	}

	public void removeFromAnnotations(Collection<Annotation> annotationCollection) {
		if (annotationCollection != null ) {
			synchronized (this.annotations) {
				this.annotations.removeAll(annotationCollection);
			}
		}
	}

	public void clearAnnotations() {
		synchronized (annotations) {
			annotations.clear();
		}
	}
	
	public List<Serializable> getIdsAndItemsAndSecrets() {
		synchronized (idsAndItemsAndSecrets) {
			return idsAndItemsAndSecrets;
		}
	}
	
	public void setIdsAndItemsAndSecrets(Collection<Serializable> idsAndItemsAndSecrets) {
		if (idsAndItemsAndSecrets == null) {
			this.idsAndItemsAndSecrets = null;
        } else {
		synchronized (this.idsAndItemsAndSecrets) {
				this.idsAndItemsAndSecrets = new ArrayList<Serializable>();
				addToIdsAndItemsAndSecrets(idsAndItemsAndSecrets);
			}
		}
	}

	public void addToIdsAndItemsAndSecrets(Serializable serializable) {
		if (serializable != null ) {
			synchronized (this.idsAndItemsAndSecrets) {
				this.idsAndItemsAndSecrets.add(serializable);
			}
		}
	}

	public void addToIdsAndItemsAndSecrets(Collection<Serializable> serializableCollection) {
		if (serializableCollection != null && !serializableCollection.isEmpty()) {
			synchronized (this.idsAndItemsAndSecrets) {
				this.idsAndItemsAndSecrets.addAll(serializableCollection);
        }
    }
	}

	public void removeFromIdsAndItemsAndSecrets(Serializable serializable) {
		if (serializable != null ) {
			synchronized (this.idsAndItemsAndSecrets) {
				this.idsAndItemsAndSecrets.remove(serializable);
			}
		}
	}

	public void removeFromIdsAndItemsAndSecrets(Collection<Serializable> serializableCollection) {
		if (serializableCollection != null ) {
			synchronized (this.idsAndItemsAndSecrets) {
				this.idsAndItemsAndSecrets.removeAll(serializableCollection);
			}
		}
	}

	public void clearIdsAndItemsAndSecrets() {
		synchronized (idsAndItemsAndSecrets) {
			idsAndItemsAndSecrets.clear();
		}
	}
	
	public List<Serializable> getAttributesAndReferencesAndGroups() {
		synchronized (attributesAndReferencesAndGroups) {
			return attributesAndReferencesAndGroups;
		}
	}
	
	public void setAttributesAndReferencesAndGroups(Collection<Serializable> attributesAndReferencesAndGroups) {
		if (attributesAndReferencesAndGroups == null) {
			this.attributesAndReferencesAndGroups = null;
		} else {
		synchronized (this.attributesAndReferencesAndGroups) {
				this.attributesAndReferencesAndGroups = new ArrayList<Serializable>();
				addToAttributesAndReferencesAndGroups(attributesAndReferencesAndGroups);
			}
		}
	}

	public void addToAttributesAndReferencesAndGroups(Serializable serializable) {
		if (serializable != null ) {
			synchronized (this.attributesAndReferencesAndGroups) {
				this.attributesAndReferencesAndGroups.add(serializable);
			}
		}
	}

	public void addToAttributesAndReferencesAndGroups(Collection<Serializable> serializableCollection) {
		if (serializableCollection != null && !serializableCollection.isEmpty()) {
			synchronized (this.attributesAndReferencesAndGroups) {
				this.attributesAndReferencesAndGroups.addAll(serializableCollection);
			}
		}
	}

	public boolean removeFromAttributesAndReferencesAndGroups(Serializable serializable) {
		if (serializable == null)
			return false;
		synchronized (this.attributesAndReferencesAndGroups) {
			return this.attributesAndReferencesAndGroups.remove(serializable);
		}
	}

	public void removeFromAttributesAndReferencesAndGroups(Collection<Serializable> serializableCollection) {
		if (serializableCollection != null ) {
			synchronized (this.attributesAndReferencesAndGroups) {
				this.attributesAndReferencesAndGroups.removeAll(serializableCollection);
			}
		}
	}

	public void clearAttributesAndReferencesAndGroups() {
		synchronized (attributesAndReferencesAndGroups) {
			attributesAndReferencesAndGroups.clear();
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
		if (object.getClass().isAssignableFrom(this.getClass())) {
			Element other = (Element) object;
			int status = compare(_extends, other._extends);
			if (status != 0)
				return status;
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
		Element other = (Element) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (_extends != null)
			hashCode += _extends.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}

	@Override
	public String toString() {
		return "Element: type="+type;
    }

}
