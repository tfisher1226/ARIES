package tx.manager.registry;

import java.io.Serializable;

//NOT USED NOW
public class ServiceRecord implements Serializable {

	private final static long serialVersionUID = 1L;

	protected String serviceId;

	protected String serviceUri;

	
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceUri() {
		return serviceUri;
	}

	public void setServiceUri(String serviceUri) {
		this.serviceUri = serviceUri;
	}



}
