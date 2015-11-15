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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Query", namespace = "http://nam/model", propOrder = {
	"name",
    "from",
	"orderBy",
    "conditionsAndCriterias",
	"distinct"
})
@XmlRootElement(name = "query", namespace = "http://nam/model")
public class Query implements Comparable<Object>, Serializable {

	private static final long serialVersionUID = 1L;
    
	@XmlElement(name = "name", namespace = "http://nam/model")
	private String name;
	
	@XmlElement(name = "from", namespace = "http://nam/model")
	private String from;
	
	@XmlElement(name = "orderBy", namespace = "http://nam/model")
	private OrderBy orderBy;
    
    @XmlElements({
        @XmlElement(name = "condition", namespace = "http://nam/model", type = Condition.class),
        @XmlElement(name = "criteria", namespace = "http://nam/model", type = Criteria.class)
    })
	private List<Serializable> conditionsAndCriterias;
    
	@XmlElement(name = "distinct", namespace = "http://nam/model", type = String.class, defaultValue = "false")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
	private Boolean distinct = false;
	
	@XmlAttribute(name = "ref")
	private String ref;
	
	
	public Query() {
		conditionsAndCriterias = new ArrayList<Serializable>();
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

    public String getFrom() {
        return from;
    }

	public void setFrom(String from) {
		this.from = from;
	}
	
	public OrderBy getOrderBy() {
		return orderBy;
	}
	
	public void setOrderBy(OrderBy orderBy) {
		this.orderBy = orderBy;
	}
	
    public List<Serializable> getConditionsAndCriterias() {
		synchronized (conditionsAndCriterias) {
			return conditionsAndCriterias;
		}
	}
	
	public void setConditionsAndCriterias(Collection<Serializable> conditionsAndCriterias) {
        if (conditionsAndCriterias == null) {
			this.conditionsAndCriterias = null;
		} else {
		synchronized (this.conditionsAndCriterias) {
				this.conditionsAndCriterias = new ArrayList<Serializable>();
				addToConditionsAndCriterias(conditionsAndCriterias);
			}
        }
    }

	public void addToConditionsAndCriterias(Serializable serializable) {
		if (serializable != null ) {
			synchronized (this.conditionsAndCriterias) {
				this.conditionsAndCriterias.add(serializable);
			}
		}
    }

	public void addToConditionsAndCriterias(Collection<Serializable> serializableCollection) {
		if (serializableCollection != null && !serializableCollection.isEmpty()) {
			synchronized (this.conditionsAndCriterias) {
				this.conditionsAndCriterias.addAll(serializableCollection);
			}
		}
	}

	public void removeFromConditionsAndCriterias(Serializable serializable) {
		if (serializable != null ) {
			synchronized (this.conditionsAndCriterias) {
				this.conditionsAndCriterias.remove(serializable);
			}
		}
	}

	public void removeFromConditionsAndCriterias(Collection<Serializable> serializableCollection) {
		if (serializableCollection != null ) {
			synchronized (this.conditionsAndCriterias) {
				this.conditionsAndCriterias.removeAll(serializableCollection);
			}
		}
	}

	public void clearConditionsAndCriterias() {
		synchronized (conditionsAndCriterias) {
			conditionsAndCriterias.clear();
		}
	}
	
	public Boolean isDistinct() {
		return distinct != null && distinct;
    }

    public Boolean getDistinct() {
		return distinct != null && distinct;
	}
	
	public void setDistinct(Boolean distinct) {
		this.distinct = distinct;
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
		Query other = (Query) object;
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
		return "Query: name="+name+", from="+from+", distinct="+distinct;
    }

}
