package nam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IPAddress", namespace = "http://nam/model", propOrder = {
	"dnsName",
	"address"
})
@XmlRootElement(name = "iPAddress", namespace = "http://nam/model")
public class IPAddress implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "dnsName", namespace = "http://nam/model")
	private String dnsName;
	
	@XmlElement(name = "address", namespace = "http://nam/model", required = true)
	private String address;
	
	@XmlAttribute(name = "ref")
	private String ref;
	
	
	public IPAddress() {
		//nothing for now
	}
	
	
	public String getDnsName() {
		return dnsName;
	}
	
	public void setDnsName(String dnsName) {
		this.dnsName = dnsName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
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
			IPAddress other = (IPAddress) object;
			int status = compare(dnsName, other.dnsName);
			if (status != 0)
				return status;
			status = compare(address, other.address);
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
		IPAddress other = (IPAddress) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (dnsName != null)
			hashCode += dnsName.hashCode();
		if (address != null)
			hashCode += address.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "IPAddress: dnsName="+dnsName+", address="+address;
	}
	
}
