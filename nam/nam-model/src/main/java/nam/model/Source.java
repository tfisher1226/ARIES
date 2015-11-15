package nam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Source", namespace = "http://nam/model", propOrder = {
    "driverClass",
    "maxActive",
    "maxIdle",
    "maxWait",
    "adapter"
})
@XmlRootElement(name = "source", namespace = "http://nam/model")
public class Source extends Provider implements Comparable<Object>, Serializable {

	private static final long serialVersionUID = 1L;
    
	@XmlElement(name = "driverClass", namespace = "http://nam/model")
	private String driverClass;
    
	@XmlElement(name = "maxActive", namespace = "http://nam/model")
	private Integer maxActive;
    
	@XmlElement(name = "maxIdle", namespace = "http://nam/model")
	private Integer maxIdle;
    
	@XmlElement(name = "maxWait", namespace = "http://nam/model")
	private Integer maxWait;
    
	@XmlElement(name = "adapter", namespace = "http://nam/model")
	private Adapter adapter;
	
	
	public Source() {
		//nothing for now
	}
    

    public String getDriverClass() {
        return driverClass;
    }

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	
    public Integer getMaxActive() {
        return maxActive;
    }

	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}
	
    public Integer getMaxIdle() {
        return maxIdle;
    }

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}
	
    public Integer getMaxWait() {
        return maxWait;
    }

	public void setMaxWait(Integer maxWait) {
		this.maxWait = maxWait;
	}
	
    public Adapter getAdapter() {
        return adapter;
    }

	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;
	}
	
	@Override
	public int compareTo(Object object) {
		int status = super.compareTo(object);
		return status;
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
		Source other = (Source) object;
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
		return "Source: driverClass="+driverClass+", maxActive="+maxActive+", maxIdle="+maxIdle+", maxWait="+maxWait;
    }

}
