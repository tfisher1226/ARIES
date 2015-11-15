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
@XmlType(name = "Networks", namespace = "http://nam/model", propOrder = {
	"networks"
})
@XmlRootElement(name = "networks", namespace = "http://nam/model")
public class Networks implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "networks", namespace = "http://nam/model")
	private List<Network> networks;
	
	
	public Networks() {
		networks = new ArrayList<Network>();
	}
	
	
	public List<Network> getNetworks() {
		synchronized (networks) {
			return networks;
		}
	}
	
	public void setNetworks(Collection<Network> networks) {
		if (networks == null) {
			this.networks = null;
		} else {
		synchronized (this.networks) {
				this.networks = new ArrayList<Network>();
				addToNetworks(networks);
			}
		}
	}

	public void addToNetworks(Network network) {
		if (network != null ) {
			synchronized (this.networks) {
				this.networks.add(network);
			}
		}
	}

	public void addToNetworks(Collection<Network> networkCollection) {
		if (networkCollection != null && !networkCollection.isEmpty()) {
			synchronized (this.networks) {
				this.networks.addAll(networkCollection);
			}
		}
	}

	public void removeFromNetworks(Network network) {
		if (network != null ) {
			synchronized (this.networks) {
				this.networks.remove(network);
			}
		}
	}

	public void removeFromNetworks(Collection<Network> networkCollection) {
		if (networkCollection != null ) {
			synchronized (this.networks) {
				this.networks.removeAll(networkCollection);
			}
		}
	}

	public void clearNetworks() {
		synchronized (networks) {
			networks.clear();
		}
	}
}
