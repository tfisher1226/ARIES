package aries.codegen.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nam.model.Callback;
import nam.model.Channel;
import nam.model.JmsTransport;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.MessagingUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;

import org.aries.util.NameUtil;


public class JMSUtil {
	
	public static class JMSDestination {
		public Service service;
		public Channel channel;
		public JmsTransport transport;
	}

	public static String getDestinationName(JMSDestination jmsDestination) {
		return getDestinationName(jmsDestination.service.getDomain(), jmsDestination.service.getName(), jmsDestination.channel);
	}
	
	public static String getDestinationName(Service service, Channel channel) {
		return getDestinationName(service, channel.getName());
	}
	
	public static String getDestinationName(Service service, String channel) {
		return getDestinationName(service.getDomain(), service.getName(), channel);
	}
	
	public static String getDestinationName(String domain, String service, Channel channel) {
		return getDestinationName(domain, service, channel.getName());
	}
	
	public static String getDestinationName(String domain, String service, String channel) {
		String channelPart = NameUtil.uncapName(channel);
		String domainPart = domain.replace(".", "_").replace("-", "_");
		String servicePart = NameUtil.splitStringUsingUnderscores(service);
		//String servicePart = NameUtil.uncapName(service.getName());
		if (!domainPart.equalsIgnoreCase(channelPart))
			domainPart = channelPart + "_" + domainPart;
		String name = domainPart + "_" + servicePart;
		return name;
	}
	
	public static Map<String, JMSDestination> buildJMSDestinations(Project project) {
		return buildJMSDestinations(project, ProjectUtil.getServicesSorted(project));
	}

	public static Map<String, JMSDestination> buildJMSDestinations(Project project, Collection<Service> services) {
		Map<String, JMSDestination> destinations = new LinkedHashMap<String, JMSDestination>();
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			//build destinations for incoming service requests
			destinations.putAll(buildJMSDestinations(project, service));

			//build destinations for incoming callback responses
			List<Callback> callbacks = ServiceUtil.getIncomingCallbacks(service);
			//List<Callback> callbacks = ServiceUtil.getOutgoingCallbacks(service);
			destinations.putAll(buildJMSDestinations(project, service, callbacks));
		}
		return destinations;
	}

	public static Map<String, JMSDestination> buildJMSDestinations(Project project, Service service, List<Callback> callbacks) {
		Map<String, JMSDestination> destinations = new LinkedHashMap<String, JMSDestination>();
		Iterator<Callback> iterator = callbacks.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			destinations.putAll(buildJMSDestinations(project, service, callback));
		}
		return destinations;
	}
	
	public static Map<String, JMSDestination> buildJMSDestinations(Project project, Service service) {
		return buildJMSDestinations(project, service, (Callback) null);
	}
	
	public static Map<String, JMSDestination> buildJMSDestinations(Project project, Service service, Callback callback) {
		Map<String, JMSDestination> destinations = new LinkedHashMap<String, JMSDestination>();
		List<Channel> channels = ServiceUtil.getChannels(service);
		Iterator<Channel> iterator = channels.iterator();
		Service destinationService = callback;
		if (destinationService == null)
			destinationService = service;
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			destinations.putAll(buildJMSDestinations(project, destinationService, channel));
		}
		return destinations;
	}

	public static Map<String, JMSDestination> buildJMSDestinations(Project project, Service service, Channel channel) {
		Map<String, JMSDestination> destinations = new LinkedHashMap<String, JMSDestination>();
		Collection<JmsTransport> transports = MessagingUtil.getJMSTransports(project, channel);
		destinations.putAll(buildJMSDestinations(service, channel, transports));
		
//		//List<Receiver> receivers = ChannelUtil.getReceivers(channel);
//		List<Listener> listeners = ServiceUtil.getListeners(service);
//		Iterator<Listener> iterator = listeners.iterator();
//		while (iterator.hasNext()) {
//			Listener listener = iterator.next();
//			Role role = MessagingUtil.getRole(project, channel, "user");
//			Role role2 = MessagingUtil.getRole(project, channel, listener.getRole());
//			Collection<JmsTransport> transports = MessagingUtil.getJMSTransports(project, channel, null);
//			destinations.putAll(buildJMSDestinations(service, channel, transports));
//		}
		
		return destinations;
	}

	public static Map<String, JMSDestination> buildJMSDestinations(Service service, Channel channel, Collection<JmsTransport> transports) {
		Map<String, JMSDestination> destinations = new LinkedHashMap<String, JMSDestination>();
		Iterator<JmsTransport> iterator = transports.iterator();
		while (iterator.hasNext()) {
			JmsTransport transport = iterator.next();
//			if (service instanceof Callback)
//				System.out.println();
			JMSDestination jmsDestination = buildJMSDestination(service, channel, transport);
			String key = channel.getName()+"_"+service.getInterfaceName();
			destinations.put(key, jmsDestination);
		}
		return destinations;
	}

	public static JMSDestination buildJMSDestination(Service service, Channel channel, JmsTransport transport) {
		JMSDestination destination = new JMSDestination();
		destination.service = service;
		destination.channel = channel;
		destination.transport = transport;
		return destination;
	}
	

}
