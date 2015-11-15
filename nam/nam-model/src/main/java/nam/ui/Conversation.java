package nam.ui;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nam.model.Process;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Conversation", namespace = "http://nam/ui", propOrder = {
	"name",
	"process"
})
@XmlRootElement(name = "conversation", namespace = "http://nam/ui")
public class Conversation implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "name", namespace = "http://nam/ui", required = true)
	private String name;
	
	@XmlElement(name = "process", namespace = "http://nam/ui", required = true)
	private Process process;
	
	@XmlAttribute(name = "ref")
	private String ref;
	
	
	public Conversation() {
		//nothing for now
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Process getProcess() {
		return process;
	}
	
	public void setProcess(Process process) {
		this.process = process;
	}
	
	public String getRef() {
		return ref;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	@SuppressWarnings("rawtypes")
	protected <T extends Comparable> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		@SuppressWarnings("unchecked")
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(Object object) {
		if (object.getClass().isAssignableFrom(this.getClass())) {
			Conversation other = (Conversation) object;
			int status = compare(name, other.name);
			if (status != 0)
				return status;
			status = compare(process, other.process);
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
		Conversation other = (Conversation) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (name != null)
			hashCode += name.hashCode();
		if (process != null)
			hashCode += process.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Conversation: name="+name+", process="+process;
	}
	
}
