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
@XmlType(name = "Pod", namespace = "http://nam/model", propOrder = {
	"name",
	"label",
	"ipAddress",
	"containers",
	"volumes"
})
@XmlRootElement(name = "pod", namespace = "http://nam/model")
public class Pod implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "name", namespace = "http://nam/model")
	private String name;
	
	@XmlElement(name = "label", namespace = "http://nam/model")
	private String label;
	
	@XmlElement(name = "ipAddress", namespace = "http://nam/model")
	private IPAddress ipAddress;
	
	@XmlElement(name = "containers", namespace = "http://nam/model")
	private List<Container> containers;
	
	@XmlElement(name = "volumes", namespace = "http://nam/model")
	private List<Volume> volumes;
	
	
	public Pod() {
		containers = new ArrayList<Container>();
		volumes = new ArrayList<Volume>();
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
	
	public IPAddress getIpAddress() {
		return ipAddress;
	}
	
	public void setIpAddress(IPAddress ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public List<Container> getContainers() {
		synchronized (containers) {
			return containers;
		}
	}
	
	public void setContainers(Collection<Container> containers) {
		if (containers == null) {
			this.containers = null;
		} else {
		synchronized (this.containers) {
				this.containers = new ArrayList<Container>();
				addToContainers(containers);
			}
		}
	}

	public void addToContainers(Container container) {
		if (container != null ) {
			synchronized (this.containers) {
				this.containers.add(container);
			}
		}
	}

	public void addToContainers(Collection<Container> containerCollection) {
		if (containerCollection != null && !containerCollection.isEmpty()) {
			synchronized (this.containers) {
				this.containers.addAll(containerCollection);
			}
		}
	}

	public void removeFromContainers(Container container) {
		if (container != null ) {
			synchronized (this.containers) {
				this.containers.remove(container);
			}
		}
	}

	public void removeFromContainers(Collection<Container> containerCollection) {
		if (containerCollection != null ) {
			synchronized (this.containers) {
				this.containers.removeAll(containerCollection);
			}
		}
	}

	public void clearContainers() {
		synchronized (containers) {
			containers.clear();
		}
	}
	
	public List<Volume> getVolumes() {
		synchronized (volumes) {
			return volumes;
		}
	}
	
	public void setVolumes(Collection<Volume> volumes) {
		if (volumes == null) {
			this.volumes = null;
		} else {
		synchronized (this.volumes) {
				this.volumes = new ArrayList<Volume>();
				addToVolumes(volumes);
			}
		}
	}

	public void addToVolumes(Volume volume) {
		if (volume != null ) {
			synchronized (this.volumes) {
				this.volumes.add(volume);
			}
		}
	}

	public void addToVolumes(Collection<Volume> volumeCollection) {
		if (volumeCollection != null && !volumeCollection.isEmpty()) {
			synchronized (this.volumes) {
				this.volumes.addAll(volumeCollection);
			}
		}
	}

	public void removeFromVolumes(Volume volume) {
		if (volume != null ) {
			synchronized (this.volumes) {
				this.volumes.remove(volume);
			}
		}
	}

	public void removeFromVolumes(Collection<Volume> volumeCollection) {
		if (volumeCollection != null ) {
			synchronized (this.volumes) {
				this.volumes.removeAll(volumeCollection);
			}
		}
	}

	public void clearVolumes() {
		synchronized (volumes) {
			volumes.clear();
		}
	}
	
	@Override
	public int compareTo(Object object) {
		if (object.getClass().isAssignableFrom(this.getClass())) {
			Pod other = (Pod) object;
			int status = compare(name, other.name);
			if (status != 0)
				return status;
		}
		return 0;
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
		Pod other = (Pod) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (name != null)
			hashCode += name.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Pod: name="+name;
	}
	
}
