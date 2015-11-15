package common.jmx.bean;


public abstract class AbstractJmxEndpoint {

	private String host = "localhost";

	private int port = 1099;

	private String service = "jmxri";


	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getURL() {
		return getURL(host, port, service);
	}

	// If this is an IPV6 literal address, surround with square brackets as per rfc2732.
	// IPV6 literal addresses have one or more colons
	// IPV4 addresses/hostnames have no colons
	public String getURL(String host, int port, String service) {
		if (host.indexOf(':') != -1)
			host = "[" + host + "]";
		String url = "service:jmx:rmi:///jndi/rmi://"+host+":"+port+"/"+service;
		return url;
	}

}
