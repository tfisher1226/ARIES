package nam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Process", namespace = "http://nam/model", propOrder = {
    "stateful",
    "object",
	"dataUnits",
	"cacheUnits",
	"state"
})
@XmlRootElement(name = "process", namespace = "http://nam/model")
public class Process extends Component implements Comparable<Object>, Serializable {

	private static final long serialVersionUID = 1L;
    
	@XmlElement(name = "stateful", namespace = "http://nam/model", type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
	private Boolean stateful;
    
	@XmlElement(name = "object", namespace = "http://nam/model")
	private Object object;
    
	@XmlElement(name = "dataUnits", namespace = "http://nam/model")
	private List<Unit> dataUnits;
    
	@XmlElement(name = "cacheUnits", namespace = "http://nam/model")
	private List<Cache> cacheUnits;
    
	@XmlElement(name = "state", namespace = "http://nam/model")
	private ProcessState state;
	
    
	public Process() {
		dataUnits = new ArrayList<Unit>();
		cacheUnits = new ArrayList<Cache>();
	}
    
    
	public Boolean isStateful() {
		return stateful != null && stateful;
	}

    public Boolean getStateful() {
		return stateful != null && stateful;
	}
	
	public void setStateful(Boolean stateful) {
		this.stateful = stateful;
    }

    public Object getObject() {
        return object;
    }

	public void setObject(Object object) {
		this.object = object;
	}
	
	public List<Unit> getDataUnits() {
		synchronized (dataUnits) {
			return dataUnits;
		}
	}
	
	public void setDataUnits(Collection<Unit> dataUnits) {
		if (dataUnits == null) {
			this.dataUnits = null;
		} else {
		synchronized (this.dataUnits) {
				this.dataUnits = new ArrayList<Unit>();
				addToDataUnits(dataUnits);
			}
		}
	}

	public void addToDataUnits(Unit unit) {
		if (unit != null ) {
			synchronized (this.dataUnits) {
				this.dataUnits.add(unit);
			}
		}
	}

	public void addToDataUnits(Collection<Unit> unitCollection) {
		if (unitCollection != null && !unitCollection.isEmpty()) {
			synchronized (this.dataUnits) {
				this.dataUnits.addAll(unitCollection);
			}
		}
	}

	public void removeFromDataUnits(Unit unit) {
		if (unit != null ) {
			synchronized (this.dataUnits) {
				this.dataUnits.remove(unit);
			}
		}
	}

	public void removeFromDataUnits(Collection<Unit> unitCollection) {
		if (unitCollection != null ) {
			synchronized (this.dataUnits) {
				this.dataUnits.removeAll(unitCollection);
			}
		}
	}

	public void clearDataUnits() {
		synchronized (dataUnits) {
			dataUnits.clear();
		}
	}
	
    public List<Cache> getCacheUnits() {
		synchronized (cacheUnits) {
			return cacheUnits;
		}
	}
	
	public void setCacheUnits(Collection<Cache> cacheUnits) {
        if (cacheUnits == null) {
			this.cacheUnits = null;
		} else {
		synchronized (this.cacheUnits) {
				this.cacheUnits = new ArrayList<Cache>();
				addToCacheUnits(cacheUnits);
			}
        }
    }

	public void addToCacheUnits(Cache cache) {
		if (cache != null ) {
			synchronized (this.cacheUnits) {
				this.cacheUnits.add(cache);
			}
		}
	}

	public void addToCacheUnits(Collection<Cache> cacheCollection) {
		if (cacheCollection != null && !cacheCollection.isEmpty()) {
			synchronized (this.cacheUnits) {
				this.cacheUnits.addAll(cacheCollection);
			}
		}
        }

	public void removeFromCacheUnits(Cache cache) {
		if (cache != null ) {
			synchronized (this.cacheUnits) {
				this.cacheUnits.remove(cache);
			}
		}
	}

	public void removeFromCacheUnits(Collection<Cache> cacheCollection) {
		if (cacheCollection != null ) {
			synchronized (this.cacheUnits) {
				this.cacheUnits.removeAll(cacheCollection);
			}
		}
	}

	public void clearCacheUnits() {
		synchronized (cacheUnits) {
			cacheUnits.clear();
		}
	}
	
	public ProcessState getState() {
		return state;
	}
	
	public void setState(ProcessState state) {
		this.state = state;
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
		int status = super.compareTo(object);
		return status;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Process other = (Process) object;
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
		return "Process: stateful="+stateful+", object="+object;
    }

}
