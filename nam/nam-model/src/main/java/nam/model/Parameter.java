package nam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Parameter", namespace = "http://nam/model", propOrder = {
	"name",
	"type",
	"key",
	"construct",
	"required"
})
@XmlRootElement(name = "parameter", namespace = "http://nam/model")
public class Parameter implements Comparable<Parameter>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "name", namespace = "http://nam/model", required = true)
	private String name;
	
	@XmlElement(name = "type", namespace = "http://nam/model", required = true)
	private String type;
	
	@XmlElement(name = "key", namespace = "http://nam/model")
	private String key;
	
	@XmlElement(name = "construct", namespace = "http://nam/model")
	private String construct;
	
	@XmlElement(name = "required", namespace = "http://nam/model", type = String.class)
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean required;
	
	
	public Parameter() {
		//nothing for now
	}
	
	
    public String getName() {
        return name;
    }

	public void setName(String name) {
		this.name = name;
	}
	
    public String getType() {
        return type;
    }

	public void setType(String type) {
		this.type = type;
	}
	
    public String getKey() {
        return key;
    }

	public void setKey(String key) {
		this.key = key;
	}
	
    public String getConstruct() {
        return construct;
    }

	public void setConstruct(String construct) {
		this.construct = construct;
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
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(Parameter other) {
		int status = compare(name, other.name);
		if (status != 0)
			return status;
		status = compare(type, other.type);
		if (status != 0)
			return status;
		return 0;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Parameter other = (Parameter) object;
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
		return "Parameter: name="+name+", type="+type;
    }

}
