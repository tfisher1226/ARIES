package org.aries.launcher.jaxws;

import javax.xml.ws.Endpoint;

import nam.model.HttpTransport;
import nam.model.Role2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.launcher.ServiceLauncher;


/**
 * This can be used to launch a JAXWS web service standalone 
 * i.e. independently of the application server (if one is being 
 * used).
 * @author tfisher
 */
public class JAXWSLauncher implements ServiceLauncher {

	private static Log log = LogFactory.getLog(JAXWSLauncher.class);
	
	private String serviceId;

	private Object instance;

	private String address;

	private Role2 role;

	private Endpoint endpoint;

	private HttpTransport httpTransport;


	public JAXWSLauncher(HttpTransport httpTransport) {
		this.httpTransport = httpTransport;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public Object getInstance() {
		return instance;
	}

	public void setInstance(Object instance) {
		this.instance = instance;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Role2 getChannel() {
		return role;
	}

	public void setChannel(Role2 channel) {
		this.role = channel;
	}


	public void launch() {
		validate();
		Assert.notNull(address, "HTTP address must be specified");
		//String url = ServiceUtil.getServiceURL(address, service);
		endpoint = Endpoint.publish(address, instance);
		log.info("JAXWS service launched: "+address);
		//endpoint.getBinding().getHandlerChain().add(new WSSEHeaderHandler());
		//EndpointImpl endpoint = (EndpointImpl) Endpoint.create(instance);
		//endpoint.getFeatures().add(new WSAddressingFeature());
		//endpoint.publish(url);
	}

	//validate inputs
	public void validate() {
		Assert.notNull(address, "Service address not specified");
		Assert.notNull(serviceId, "Service ID not specified");
		Assert.notNull(instance, "Service instance not specified");
		Assert.notNull(role, "Channel Descripter not specified");
		Assert.notNull(role.getName(), "Channel Name not specified");
		//Assert.notNull(channel.getProvider(), "Channel ProviderName not specified");
		//Assert.isTrue(channel.getTransport().toLowerCase().equals("jms"), "Channel Type must be JMS");
		//Assert.notNull(channel.getConnectionFactory(), "Channel ConnectionFactory not specified");
	}

	public void shutdown() {
		log.info("Shutting down listener: "+getServiceId());
		if (endpoint != null)
			endpoint.stop();
		log.info("Shutdown listener: "+getServiceId());
	}

}
