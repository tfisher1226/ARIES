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
@XmlType(name = "Providers", namespace = "http://nam/model", propOrder = {
    "providers"
})
@XmlRootElement(name = "providers", namespace = "http://nam/model")
public class Providers implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "provider", namespace = "http://nam/model")
	private List<Provider> providers;
	
	
	public Providers() {
		providers = new ArrayList<Provider>();
	}
	
	
    public List<Provider> getProviders() {
		synchronized (providers) {
			return providers;
		}
	}
	
	public void setProviders(Collection<Provider> providers) {
        if (providers == null) {
			this.providers = null;
		} else {
		synchronized (this.providers) {
				this.providers = new ArrayList<Provider>();
				addToProviders(providers);
			}
		}
	}

	public void addToProviders(Provider provider) {
		if (provider != null ) {
			synchronized (this.providers) {
				this.providers.add(provider);
			}
		}
	}

	public void addToProviders(Collection<Provider> providerCollection) {
		if (providerCollection != null && !providerCollection.isEmpty()) {
			synchronized (this.providers) {
				this.providers.addAll(providerCollection);
			}
		}
	}

	public void removeFromProviders(Provider provider) {
		if (provider != null ) {
			synchronized (this.providers) {
				this.providers.remove(provider);
			}
		}
	}

	public void removeFromProviders(Collection<Provider> providerCollection) {
		if (providerCollection != null ) {
			synchronized (this.providers) {
				this.providers.removeAll(providerCollection);
			}
        }
    }

	public void clearProviders() {
		synchronized (providers) {
			providers.clear();
		}
	}
}
