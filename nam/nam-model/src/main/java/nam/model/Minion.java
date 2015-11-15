package nam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Minion", namespace = "http://nam/model", propOrder = {
	"version",
	"dnsDomain",
	"dnsIP",
	"bindAddress",
	"volumeDirectory",
	"pods"
})
@XmlRootElement(name = "minion", namespace = "http://nam/model")
public class Minion extends Node implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "version", namespace = "http://nam/model")
	private String version;
	
	@XmlElement(name = "dnsDomain", namespace = "http://nam/model")
	private String dnsDomain;
	
	@XmlElement(name = "dnsIP", namespace = "http://nam/model")
	private IPAddress dnsIP;
	
	@XmlElement(name = "bindAddress", namespace = "http://nam/model")
	private IPAddress bindAddress;
	
	@XmlElement(name = "volumeDirectory", namespace = "http://nam/model")
	private String volumeDirectory;
	
	@XmlElement(name = "pods", namespace = "http://nam/model")
	private List<Pod> pods;
	
	
	public Minion() {
		pods = new ArrayList<Pod>();
	}
	
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getDnsDomain() {
		return dnsDomain;
	}
	
	public void setDnsDomain(String dnsDomain) {
		this.dnsDomain = dnsDomain;
	}
	
	public IPAddress getDnsIP() {
		return dnsIP;
	}
	
	public void setDnsIP(IPAddress dnsIP) {
		this.dnsIP = dnsIP;
	}
	
	public IPAddress getBindAddress() {
		return bindAddress;
	}
	
	public void setBindAddress(IPAddress bindAddress) {
		this.bindAddress = bindAddress;
	}
	
	public String getVolumeDirectory() {
		return volumeDirectory;
	}
	
	public void setVolumeDirectory(String volumeDirectory) {
		this.volumeDirectory = volumeDirectory;
	}
	
	public List<Pod> getPods() {
		synchronized (pods) {
			return pods;
		}
	}
	
	public void setPods(Collection<Pod> pods) {
		if (pods == null) {
			this.pods = null;
		} else {
		synchronized (this.pods) {
				this.pods = new ArrayList<Pod>();
				addToPods(pods);
			}
		}
	}

	public void addToPods(Pod pod) {
		if (pod != null ) {
			synchronized (this.pods) {
				this.pods.add(pod);
			}
		}
	}

	public void addToPods(Collection<Pod> podCollection) {
		if (podCollection != null && !podCollection.isEmpty()) {
			synchronized (this.pods) {
				this.pods.addAll(podCollection);
			}
		}
	}

	public void removeFromPods(Pod pod) {
		if (pod != null ) {
			synchronized (this.pods) {
				this.pods.remove(pod);
			}
		}
	}

	public void removeFromPods(Collection<Pod> podCollection) {
		if (podCollection != null ) {
			synchronized (this.pods) {
				this.pods.removeAll(podCollection);
			}
		}
	}

	public void clearPods() {
		synchronized (pods) {
			pods.clear();
		}
	}
	
	@Override
	public int compareTo(Object object) {
		if (object.getClass().isAssignableFrom(this.getClass())) {
			Minion other = (Minion) object;
			int status = compare(dnsDomain, other.dnsDomain);
			if (status != 0)
				return status;
			status = compareObject(dnsIP, other.dnsIP);
			if (status != 0)
				return status;
		}
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
	
	protected <T extends Comparable<Object>> int compareObject(T value1, T value2) {
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
		Minion other = (Minion) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (dnsDomain != null)
			hashCode += dnsDomain.hashCode();
		if (dnsIP != null)
			hashCode += dnsIP.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Minion: dnsDomain="+dnsDomain+", dnsIP="+dnsIP;
	}
	
}
