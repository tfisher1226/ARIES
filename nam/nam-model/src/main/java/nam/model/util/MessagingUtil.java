package nam.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Adapter;
import nam.model.Application;
import nam.model.Channel;
import nam.model.Channels;
import nam.model.EjbTransport;
import nam.model.Group;
import nam.model.HttpTransport;
import nam.model.Import;
import nam.model.Interactor;
import nam.model.JmsTransport;
import nam.model.Link;
import nam.model.Links;
import nam.model.Listener;
import nam.model.Listeners;
import nam.model.Messaging;
import nam.model.Project;
import nam.model.Provider;
import nam.model.Receiver;
import nam.model.RmiTransport;
import nam.model.Role2;
import nam.model.Router;
import nam.model.Service;
import nam.model.Transport;
import nam.model.TransportType;
import nam.model.Transports;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class MessagingUtil extends BaseUtil {
	
	public static Object getKey(Messaging messaging) {
		return messaging.getDomain() + "." + messaging.getName();
	}
	
	public static String getLabel(Messaging messaging) {
		return messaging.getDomain() + "." + messaging.getName();
	}
	
	public static boolean isEmpty(Messaging messaging) {
		if (messaging == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Messaging> messagingList) {
		if (messagingList == null  || messagingList.size() == 0)
			return true;
		Iterator<Messaging> iterator = messagingList.iterator();
		while (iterator.hasNext()) {
			Messaging messaging = iterator.next();
			if (!isEmpty(messaging))
				return false;
		}
		return true;
	}
	
	public static String toString(Messaging messaging) {
		if (isEmpty(messaging))
			return "Messaging: [uninitialized] "+messaging.toString();
		String text = messaging.toString();
		return text;
	}
	
	public static String toString(Collection<Messaging> messagingList) {
		if (isEmpty(messagingList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Messaging> iterator = messagingList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Messaging messaging = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(messaging);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Messaging create() {
		Messaging messaging = new Messaging();
		initialize(messaging);
		return messaging;
	}
	
	public static void initialize(Messaging messaging) {
		if (messaging.getExposed() == null)
			messaging.setExposed(false);
		if (messaging.getImported() == null)
			messaging.setImported(false);
		if (messaging.getIncluded() == null)
			messaging.setIncluded(false);
	}
	
	public static boolean validate(Messaging messaging) {
		if (messaging == null)
			return false;
		Validator validator = Validator.getValidator();
		ImportUtil.validate(messaging.getImports());
		//SerializableUtil.validate(messaging.getMembers());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Messaging> messagingList) {
		Validator validator = Validator.getValidator();
		Iterator<Messaging> iterator = messagingList.iterator();
		while (iterator.hasNext()) {
			Messaging messaging = iterator.next();
			//TODO break or accumulate?
			validate(messaging);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Messaging> messagingList) {
		Collections.sort(messagingList, createMessagingComparator());
	}
	
	public static Collection<Messaging> sortRecords(Collection<Messaging> messagingCollection) {
		List<Messaging> list = new ArrayList<Messaging>(messagingCollection);
		Collections.sort(list, createMessagingComparator());
		return list;
	}
	
	public static Comparator<Messaging> createMessagingComparator() {
		return new Comparator<Messaging>() {
			public int compare(Messaging messaging1, Messaging messaging2) {
				Object key1 = getKey(messaging1);
				Object key2 = getKey(messaging2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Messaging clone(Messaging messaging) {
		if (messaging == null)
			return null;
		Messaging clone = create();
		clone.getImports().addAll(ImportUtil.clone(messaging.getImports()));
		clone.setDomain(ObjectUtil.clone(messaging.getDomain()));
		clone.setName(ObjectUtil.clone(messaging.getName()));
		clone.setLabel(ObjectUtil.clone(messaging.getLabel()));
		clone.setVersion(ObjectUtil.clone(messaging.getVersion()));
		clone.setDescription(ObjectUtil.clone(messaging.getDescription()));
		clone.setNamespace(ObjectUtil.clone(messaging.getNamespace()));
		//TODO clone.setMembers(SerializableUtil.clone(messaging.getMembers()));
		clone.setImported(ObjectUtil.clone(messaging.getImported()));
		clone.setIncluded(ObjectUtil.clone(messaging.getIncluded()));
		clone.setExposed(ObjectUtil.clone(messaging.getExposed()));
		clone.setCreationDate(ObjectUtil.clone(messaging.getCreationDate()));
		clone.setLastUpdate(ObjectUtil.clone(messaging.getLastUpdate()));
		return clone;
	}
	

	public static String getJMSDescriptorFileName(Messaging messagingBlock) {
		String domain = messagingBlock.getDomain();
		String name = messagingBlock.getName();
		if (!name.equalsIgnoreCase(domain) && !name.startsWith(domain))
			name = domain + "-" + name;
		String fileName = name+"-jms.xml";
		return fileName;
	}
	
	public static Messaging newMessaging() {
		Messaging messaging = new Messaging();
		messaging.getMembers().add(new Links());
		messaging.getMembers().add(new Channels());
		messaging.getMembers().add(new Transports());
		return messaging;
	}

	public static boolean containsImport(Messaging messaging, Import model) {
		return ProjectUtil.containsImport(getImports(messaging), model);
	}
	
	public static void addImport(Messaging messaging, Import model) {
		if (!containsImport(messaging, model))
			messaging.getImports().add(model);
	}

//	public static void addImports(Project project, List<Import> imports) {
//		addImports(ProjectUtil.getMessaging(project), imports);
//	}
	
	public static void addImports(Messaging messaging, List<Import> imports) {
		Iterator<Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import model = iterator.next();
			addImport(messaging, model);
		}
	}
	
	public static List<Import> getImports(Messaging messaging) {
		return getObjectList(messaging, Import.class);
	}
	
	
//	public static void addProviders(Project project, List<Provider> providers) {
//		addProviders(ProjectUtil.getMessaging(project), providers);
//	}
	
	public static void addProviders(Messaging messaging, List<Provider> providers) {
		Iterator<Provider> iterator = providers.iterator();
		while (iterator.hasNext()) {
			Provider provider = iterator.next();
			addProvider(messaging, provider);
		}
	}

	public static void addProvider(Messaging messaging, Provider provider) {
		if (!messaging.getMembers().contains(provider))
			messaging.getMembers().add(provider);
	}
	
	public static boolean removeProvider(Messaging messaging, Provider provider) {
		if (!messaging.getMembers().contains(provider))
			return messaging.getMembers().remove(provider);
		return false;
	}
	
	public static List<Provider> getProviders(Project project) {
		List<Provider> list = new ArrayList<Provider>();
		List<Messaging> messagingBlocks = ProjectUtil.getMessagingBlocks(project);
		Iterator<Messaging> iterator = messagingBlocks.iterator();
		while (iterator.hasNext()) {
			Messaging messaging = iterator.next();
			List<Provider> providers = getProviders(messaging);
			//TODO prevent duplicates
			list.addAll(providers);
		}
		return list;
	}

	public static List<Provider> getProviders(Messaging messaging) {
		return getObjectList(messaging, Provider.class);
	}

//	public static List<Provider> getProviders(Messaging messaging) {
//		if (messaging == null)
//			return new ArrayList<Provider>();
//		Providers providers = messaging.getProviders();
//		if (providers == null)
//			providers = new Providers();
//		return providers.getProviders();
//	}

	public static Map<String, Provider> getProvidersAsMap(Messaging messaging) {
		return ProviderUtil.createProviderMap(getProviders(messaging));
	}

	public static Provider getProviderByName(Project project, String name) {
		List<Messaging> messagingBlocks = ProjectUtil.getMessagingBlocks(project);
		Iterator<Messaging> iterator = messagingBlocks.iterator();
		while (iterator.hasNext()) {
			Messaging messaging = iterator.next();
			Provider provider = getProviderByName(messaging, name);
			if (provider != null)
				return provider;
		}
		return null;
	}
	
	public static Provider getProviderByName(Messaging messaging, String name) {
		List<Provider> providers = getProviders(messaging);
		Iterator<Provider> iterator = providers.iterator();
		while (iterator.hasNext()) {
			Provider provider = iterator.next();
			String providerName = provider.getName();
			if (providerName == null)
				System.out.println();
			if (providerName.equalsIgnoreCase(name)) {
				return provider;
			}
		}
		return null;
	}


//	public static List<Adapter> getAdapters(Project project) {
//		return getAdapters(ProjectUtil.getMessaging(project));
//	}

	public static List<Adapter> getAdapters(Messaging messaging) {
		return getObjectList(messaging, Adapter.class);
	}

	
	@SuppressWarnings("unchecked")
	public static <T extends Transport> void addTransport(Messaging messaging, T transport) {
		Transports parent = getObject(messaging, Transports.class);
		List<T> list = null;
		if (transport instanceof RmiTransport)
			list = (List<T>) getRmiTransports(parent);
		else if (transport instanceof EjbTransport)
			list = (List<T>) getEjbTransports(parent);
		else if (transport instanceof HttpTransport)
			list = (List<T>) getHttpTransports(parent);
		else if (transport instanceof JmsTransport)
			list = (List<T>) getJmsTransports(parent);
		if (!list.contains(transport))
			list.add(transport);
	}

//	public static void addTransports(Project project, List<Transport> transports) {
//		addTransports(ProjectUtil.getMessaging(project), transports);
//	}
	
	public static void addTransports(Messaging messaging, List<Transport> transports) {
		Iterator<Transport> iterator = transports.iterator();
		while (iterator.hasNext()) {
			Transport transport = iterator.next();
			addTransport(messaging, transport);
		}
	}
	
//	public static List<Transport> getTransports(Project project) {
//		return getTransports(ProjectUtil.getMessaging(project));
//	}

	public static List<Transport> getTransports(Messaging messaging) {
		List<Transport> list = new ArrayList<Transport>();
		Transports transports = getObject(messaging, Transports.class);
		list.addAll(getRmiTransports(transports));
		list.addAll(getEjbTransports(transports));
		list.addAll(getHttpTransports(transports));
		list.addAll(getJmsTransports(transports));
		return list;
	}

	public static Map<String, Transport> getTransportsByNameMap(Messaging messaging) {
		Map<String, Transport> map = new HashMap<String, Transport>();
		List<Transport> transports = getTransports(messaging);
		Iterator<Transport> iterator = transports.iterator();
		while (iterator.hasNext()) {
			Transport transport = iterator.next();
			Assert.notNull(transport.getName(), "Transport name not found");
			map.put(transport.getName(), transport);
		}
		return map;
	}
	
	public static void addLink(Messaging messaging, Link link) {
		Links parent = getObject(messaging, Links.class);
		if (!parent.getLinks().contains(link))
			parent.getLinks().add(link);
	}

//	public static void addLinks(Project project, List<Link> links) {
//		addLinks(ProjectUtil.getMessaging(project), links);
//	}
	
	public static void addLinks(Messaging messaging, List<Link> links) {
		Iterator<Link> iterator = links.iterator();
		while (iterator.hasNext()) {
			Link link = iterator.next();
			addLink(messaging, link);
		}
	}

	public static List<Link> getLinks(Project project) {
		List<Link> list = new ArrayList<Link>();
		List<Messaging> messagingBlocks = ProjectUtil.getMessagingBlocks(project);
		Iterator<Messaging> iterator = messagingBlocks.iterator();
		while (iterator.hasNext()) {
			Messaging messaging = iterator.next();
			//TODO prevent duplicates
			List<Link> links = getLinks(messaging);
			list.addAll(links);
		}
		List<Project> subProjects = ProjectUtil.getSubProjects(project);
		Iterator<Project> iterator2 = subProjects.iterator();
		while (iterator2.hasNext()) {
			Project subProject = iterator2.next();
			List<Link> links = getLinks(subProject);
			list.addAll(links);
		}
		return list;
	}

	public static List<Link> getLinks(Messaging messaging) {
		Links links = getObject(messaging, Links.class);
		return links.getLinks();
	}

	
	public static void addChannel(Messaging messaging, Channel channel) {
		Channels parent = getObject(messaging, Channels.class);
		if (!parent.getChannels().contains(channel))
			parent.getChannels().add(channel);
	}

//	public static void addChannels(Project project, List<Channel> channels) {
//		addChannels(ProjectUtil.getMessaging(project), channels);
//	}
	
	public static void addChannels(Messaging messaging, List<Channel> channels) {
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			addChannel(messaging, channel);
		}
	}
	
//	public static List<Channel> getChannels(Project project) {
//		return getChannels(ProjectUtil.getMessaging(project));
//	}

	public static List<Channel> getChannels(Project project) {
		List<Channel> list = new ArrayList<Channel>();
		List<Messaging> messagingBlocks = ProjectUtil.getMessagingBlocks(project);
		Iterator<Messaging> iterator = messagingBlocks.iterator();
		while (iterator.hasNext()) {
			Messaging messaging = iterator.next();
			//TODO prevent duplicates
			List<Channel> channels = getChannels(messaging);
			list.addAll(channels);
		}
		return list;
	}
	
	public static List<Channel> getChannels(Application application) {
		return ApplicationUtil.getChannels(application);
	}
	
	/*
	 * At this point, 
	 * the interactor may NOT yet currently hold the correct value of the channelName,
	 * but instead hold the value of the remoteServiceName,  we need to convert that 
	 * remoteServiceName to the correct channelName, and use it to set interactor's 
	 * channel name.
	 */
	public static List<Channel> getChannels(List<Project> projects, Service service) {
		List<Channel> list = new ArrayList<Channel>();
		List<Interactor> interactors = ServiceUtil.getInteractors(service);
		Iterator<Interactor> iterator = interactors.iterator();
		while (iterator.hasNext()) {
			Interactor interactor = iterator.next();
			String channelName = interactor.getChannel();
			Channel channel = getChannelByName(projects, channelName);
			//Assert.notNull(channel, "Channel not found: "+channelName);
			if (channel != null) {
				list.add(channel);
				
			} else {
				//channel == null
				int indexOf = channelName.indexOf(".");
				if (indexOf == -1) {
					String remoteServiceName = channelName;
					//Service remoteService = ProjectUtil.getServiceByName(projects, remoteServiceName);
					//Assert.notNull(remoteService, "Service not found: "+remoteServiceName);
					
				} else {
					String remoteApplicationName = channelName.substring(0, indexOf);
					String remoteServiceName = channelName.substring(indexOf+1);
					Service remoteService = getServiceByName(projects, remoteApplicationName, remoteServiceName);
					//Assert.notNull(remoteService, "Service not found: "+remoteServiceName);
					if (remoteService != null) {
						List<Channel> channels = ServiceUtil.getChannels(remoteService);
						list.addAll(channels);
					} else {
						System.err.println("Service not found: "+remoteServiceName);
					}
				}
			}
		}
		return list;
	}
	
	public static Service getServiceByName(List<Project> projects, String remoteApplicationName, String remoteServiceName) {
		//getApplicationNameFromGroup(projects, remoteApplicationName);
		Group group = ProjectUtil.getGroupByName(projects, remoteApplicationName);
		if (group != null) {
			List<Application> applications = group.getApplications();
			Iterator<Application> iterator = applications.iterator();
			while (iterator.hasNext()) {
				Application application = iterator.next();
				String applicationName = application.getName();
				Service remoteService = ProjectUtil.getServiceByName(projects, applicationName, remoteServiceName);
				if (remoteService != null) {
					return remoteService;
				}
			}
		}
		
		Service remoteService = ProjectUtil.getServiceByName(projects, remoteApplicationName, remoteServiceName);
		return remoteService;
	}

	public static List<Channel> getChannels(Messaging messaging) {
		Channels channels = getObject(messaging, Channels.class);
		return channels.getChannels();
	}

	
//	public static List<Listener> getListeners(Project project) {
//		return getListeners(ProjectUtil.getMessaging(project));
//	}

	public static List<Listener> getListeners(List<Messaging> messagingBlocks) {
		List<Listener> listenerList = new ArrayList<Listener>();
		Iterator<Messaging> iterator = messagingBlocks.iterator();
		while (iterator.hasNext()) {
			Messaging messaging = iterator.next();
			listenerList.addAll(getListeners(messaging));
		}
		return listenerList;
	}

	public static List<Listener> getListeners(Messaging messaging) {
		Listeners listeners = getObject(messaging, Listeners.class);
		return listeners.getListeners();
	}
	
	public static Listener getListenerByName(List<Project> projectList, String name) {
		List<Messaging> messagingBlocks = ProjectUtil.getMessagingBlocks(projectList);
		List<Listener> listeners = MessagingUtil.getListeners(messagingBlocks);
		Iterator<Listener> iterator = listeners.iterator();
		while (iterator.hasNext()) {
			Listener listener = iterator.next();
			if (listener == null)
				continue;
			if (listener.getName() == null)
				continue;
			if (listener.getName().equalsIgnoreCase(name)) {
				return listener;
			}
		}
		return null;
	}
	

//	public static List<Router> getRouters(Project project) {
//		return getRouters(ProjectUtil.getMessaging(project));
//	}

	public static List<Router> getRouters(Messaging messaging) {
		return getObjectList(messaging, Router.class);
	}

//	public static List<Interactor> getInteractors(Project project) {
//		return getInteractors(ProjectUtil.getMessaging(project));
//	}

	public static List<Interactor> getInteractors(Messaging messaging) {
		return getObjectList(messaging, Interactor.class);
	}

	
//	public static <T> List<T> getObjectList(Project project, Class<?> objectClass) {
//		return getObjectList(ProjectUtil.getMessaging(project), objectClass);
//	}
	
	public static <T> List<T> getObjectList(Messaging messaging, Class<?> objectClass) {
		List<Serializable> objects = messaging.getMembers();
		return ListUtil.getObjectList(objects, objectClass);
	}

//	public static <T> T getObject(Project project, Class<?> objectClass) {
//		return getObject(ProjectUtil.getMessaging(project), objectClass);
//	}
	
	public static <T> T getObject(Messaging messaging, Class<?> objectClass) {
		List<Serializable> objects = messaging.getMembers();
		return ListUtil.getObject(objects, objectClass);
	}

	
	public static List<Channel> getChannels(List<Messaging> messagingBlocks) {
		List<Channel> channelList = new ArrayList<Channel>();
		Iterator<Messaging> iterator = messagingBlocks.iterator();
		while (iterator.hasNext()) {
			Messaging messaging = iterator.next();
			channelList.addAll(getChannels(messaging));
		}
		return channelList;
	}

//	public static List<Channel> getChannels(Project project, Service service) {
//		return getChannels(ProjectUtil.getMessaging(project), service);
//	}

	public static List<Channel> getChannels(Messaging messaging, Service service) {
		Map<String, Channel> map = new HashMap<String, Channel>();
		Collection<String> channelNames = ServiceUtil.getChannelNames(service);
		Iterator<String> iterator = channelNames.iterator();
		while (iterator.hasNext()) {
			String channelName = iterator.next();
			if (!map.containsKey(channelName)) {
				Channel channel = MessagingUtil.getChannelByName(messaging, channelName);
				if (channel != null)
					map.put(channelName, channel);
			}
		}
		List<Channel> list = new ArrayList<Channel>();
		list.addAll(map.values());
		return list;
	}

	public static List<Channel> getChannel2(Messaging messaging, Service service) {
		Map<String, Channel> map = new HashMap<String, Channel>();
		
		List<Listener> listeners = ServiceUtil.getListeners(service);
		Iterator<Listener> iterator = listeners.iterator();
		while (iterator.hasNext()) {
			Listener listener = iterator.next();
			String channelName = listener.getChannel();
			Channel channel = getChannelByName(service, channelName);
			if (channel == null)
				channel = getChannelByName(messaging, channelName);
			if (channel == null)
				continue;
			Assert.notNull(channel, "Channel not found: "+channelName);
			map.put(channelName, channel);
		}
		
		List<Channel> list = new ArrayList<Channel>();
		list.addAll(map.values());
		return list;
	}

	//Channel names are expected to be globally unique
	public static Channel getChannelByName(List<Project> projects, String name) {
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			Channel channel = getChannelByName(project, name);
			if (channel != null)
				return channel;
		}
		return null;
	}
	
	public static Channel getChannelByName(Project project, String name) {
		List<Messaging> messagingBlocks = ProjectUtil.getMessagingBlocks(project);
		Iterator<Messaging> iterator = messagingBlocks.iterator();
		while (iterator.hasNext()) {
			Messaging messaging = iterator.next();
			Channel channel = getChannelByName(messaging, name);
			if (channel != null)
				return channel;
		}
		List<Project> subProjects = ProjectUtil.getSubProjects(project);
		Iterator<Project> iterator2 = subProjects.iterator();
		while (iterator2.hasNext()) {
			Project subProject = iterator2.next();
			Channel channel = getChannelByName(subProject, name);
			if (channel != null)
				return channel;
		}
		return null;
	}
	
	public static Channel getChannelByName(Messaging messaging, String name) {
		if (name == null)
			return null;
		if (name.startsWith("PLACEHOLDER"))
			return null;
		//Channels channels = getChannels(messaging);
		List<Channel> channels = getChannels(messaging);
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			if (name.equalsIgnoreCase(channel.getName())) {
				return channel;
			}
		}
		return null;
	}

	public static Channel getChannelByName(Service service, String channelName) {
		List<Channel> channels = ServiceUtil.getChannels(service);
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			if (channel.getName().equalsIgnoreCase(channelName)) {
				return channel;
			}
		}
		return null;
	}
	
	public static Set<Link> getLinks(List<Project> projects, Service service) {
		Set<Link> set = new HashSet<Link>();
		List<Channel> channels = getChannels(projects, service);
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			Set<Link> links = getLinks(projects, channel);
			//TODO prevent duplicates
			set.addAll(links);
		}
		return set;
	}	

	public static Set<Link> getLinks(List<Project> projects, Channel channel) {
		Set<Link> set = new HashSet<Link>();
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			List<Link> links = getLinks(project, channel);
			//TODO prevent duplicates
			set.addAll(links);
		}
		return set;
	}
	
	public static List<Link> getLinks(Project project, Channel channel) {
		Map<String, Link> map = new HashMap<String, Link>();
//		addLink(project, map, channel.getSendLink());
//		addLink(project, map, channel.getReceiveLink());
//		addLink(project, map, channel.getExpiredLink());
//		addLink(project, map, channel.getInvalidLink());
		addLinks(project, map, ChannelUtil.getSenders(channel));
		addLinks(project, map, ChannelUtil.getInvokers(channel));
		addLinks(project, map, ChannelUtil.getReceivers(channel));
		List<Link> list = new ArrayList<Link>();
		list.addAll(map.values());
		return list;
	}
	
	public static void addLink(Project project, Map<String, Link> map, String linkName) {
		if (linkName != null) {
			if (!map.containsKey(linkName)) {
				Link link = getLinkByName(project, linkName);
				if (link != null)
					map.put(linkName, link);
			}
		}
	}
	
	public static <T extends Interactor> void addLinks(Project project, Map<String, Link> map, List<T> interactors) {
		Iterator<T> iterator = interactors.iterator();
		while (iterator.hasNext()) {
			T interactor = iterator.next();
			addLink(project, map, interactor.getLink());
			addLink(project, map, interactor.getInvalid());
		}
	}

	public static <T extends Interactor> List<Link> getLinks(Messaging messaging, List<T> interactors) {
		Map<String, Link> map = new HashMap<String, Link>();
		Iterator<T> iterator = interactors.iterator();
		while (iterator.hasNext()) {
			T interactor = iterator.next();
			String linkName = interactor.getLink();
			Link link = getLinkByName(messaging, linkName);
			if (!map.containsKey(linkName)) {
				map.put(linkName, link);
			}
		}
		List<Link> links = new ArrayList<Link>(map.values());
		return links;
	}
	
	public static Link getLinkByName(Project project, String name) {
		List<Messaging> messagingBlocks = ProjectUtil.getMessagingBlocks(project);
		Iterator<Messaging> iterator = messagingBlocks.iterator();
		while (iterator.hasNext()) {
			Messaging messaging = iterator.next();
			Link link = getLinkByName(messaging, name);
			if (link != null)
				return link;
		}
		List<Project> subProjects = ProjectUtil.getSubProjects(project);
		Iterator<Project> iterator2 = subProjects.iterator();
		while (iterator2.hasNext()) {
			Project subProject = iterator2.next();
			Link link = getLinkByName(subProject, name);
			if (link != null)
				return link;
		}
		return null;
	}
	
	public static Link getLinkByName(Messaging messaging, String name) {
		List<Link> links = getLinks(messaging);
		Iterator<Link> iterator = links.iterator();
		while (iterator.hasNext()) {
			Link link = iterator.next();
			if (link.getName().equalsIgnoreCase(name)) {
				return link;
			}
		}
		return null;
	}


//	public List<Transport> getTransportsNew(Project project, Service service) {
//		return getTransportsNew(ProjectUtil.getMessaging(project), service);
//	}

	public List<Transport> getTransportsNew(List<Project> projects, Service service) {
		Map<String, Transport> map = new HashMap<String, Transport>();
		Set<Link> links = getLinks(projects, service);
		Iterator<Link> iterator = links.iterator();
		while (iterator.hasNext()) {
			Link link = iterator.next();
			List<Transport> transports = LinkUtil.getTransports(link);
			Iterator<Transport> transportIterator = transports.iterator();
			while (transportIterator.hasNext()) {
				Transport transport = transportIterator.next();
				String transportName = transport.getName();
				if (!map.containsKey(transportName)) {
					map.put(transportName, transport);
				}
			}
		}

		List<Transport> list = new ArrayList<Transport>();
		list.addAll(map.values());
		return list;
	}
	
	public static List<Transport> getTransports(Messaging messaging, Service service) {
		Map<TransportType, Transport> map = new HashMap<TransportType, Transport>();
		
		List<Listener> listeners = ServiceUtil.getListeners(service);
		Iterator<Listener> iterator = listeners.iterator();
		while (iterator.hasNext()) {
			Listener listener = iterator.next();
			String channelName = listener.getChannel();
			Channel channel = getChannelByName(service, channelName);
			if (channel == null)
				channel = getChannelByName(messaging, channelName);
			if (channel == null)
				continue;
			Assert.notNull(channel, "Channel not found: "+channelName);
			List<Receiver> receivers = ChannelUtil.getReceivers(channel);
			List<Link> links = getLinks(messaging, receivers);
			Iterator<Link> linkIterator = links.iterator();
			while (linkIterator.hasNext()) {
				Link link = linkIterator.next();
				List<Transport> transports = LinkUtil.getTransports(link);
				Iterator<Transport> transportIterator = transports.iterator();
				while (transportIterator.hasNext()) {
					Transport transport = transportIterator.next();
					if (!StringUtils.isEmpty(transport.getRef()))
						transport = getTransportByName(messaging, transport.getRef());
					map.put(transport.getType(), transport);
				}
			}
		}
		
		List<Transport> list = new ArrayList<Transport>(map.values());
		return list;
	}

//	public static List<Transport> getOutgoingTransports(Project project, Service service) {
//		Map<TransportType, Transport> map = new HashMap<TransportType, Transport>();
//		Messaging messaging = ProjectUtil.getMessaging(project);
//		List<Channel> channels = MessagingUtil.getChannels(messaging);
//		
//		List<Invoker> invokers = ServiceUtil.getInvokers(service);
//		Iterator<Invoker> iterator = invokers.iterator();
//		while (iterator.hasNext()) {
//			Invoker invoker = iterator.next();
//			
//			//This is the Linkage for now- 
//			String applicationName = invoker.getName();
//			String serviceName = invoker.getService();
//			//String channelId = invoker.getChannel();
//			
//			Application remoteApplication = ProjectUtil.getApplicationByName(project, applicationName);
//			Assert.notNull(remoteApplication, "Remote application not found: " + applicationName);
//			
//			Service remoteService = ApplicationUtil.getServiceByName(remoteApplication, serviceName);
//			Assert.notNull(remoteService, "Remote service not found: " + serviceName);
//			
//			
//			
//			Iterator<Channel> channelIterator = channels.iterator();
//			while (channelIterator.hasNext()) {
//				Channel channel = channelIterator.next();
//				Assert.notNull(channel, "Channel not found: "+channelId);
//				List<Sender> senders = ChannelUtil.getSenders(channel);
//				List<Link> links = getLinks(messaging, senders);
//				Iterator<Link> linkIterator = links.iterator();
//				while (linkIterator.hasNext()) {
//					Link link = linkIterator.next();
//					List<Transport> transports = LinkUtil.getTransports(link);
//					Iterator<Transport> transportIterator = transports.iterator();
//					while (transportIterator.hasNext()) {
//						Transport transport = transportIterator.next();
//						if (!StringUtils.isEmpty(transport.getRef()))
//							transport = getTransportByName(messaging, transport.getRef());
//						map.put(transport.getType(), transport);
//					}
//				}
//			}
//			Channel channel = getChannelByName(service, channelId);
//			if (channel == null)
//				channel = getChannelByName(messaging, channelId);
//			if (channel == null)
//				continue;
//
//		}
//		
//		List<Transport> list = new ArrayList<Transport>(map.values());
//		return list;
//	}
	
	
	public static Transport getTransportByName(Project project, String name) {
		List<Messaging> messagingBlocks = ProjectUtil.getMessagingBlocks(project);
		Iterator<Messaging> iterator = messagingBlocks.iterator();
		while (iterator.hasNext()) {
			Messaging messaging = iterator.next();
			Transport transport = getTransportByName(messaging, name);
			if (transport != null)
				return transport;
		}
		return null;
	}
	
	public static Transport getTransportByName(Messaging messaging, String name) {
		List<Transport> transports = getTransports(messaging);
		Iterator<Transport> iterator = transports.iterator();
		while (iterator.hasNext()) {
			Transport transport = iterator.next();
			if (transport.getName().equals(name)) {
				return transport;
			}
		}
		return null;
	}

	public static Collection<RmiTransport> getRMITransports(Project project, Channel channel, String roleName) {
		return getTransports(project, channel, TransportType.RMI, roleName);
	}
	
//	public static Collection<RmiTransport> getRMITransports(Project project, Channel channel, Role role) {
//		return getTransports(project, channel, TransportType.RMI, role);
//	}
	
	public static Collection<HttpTransport> getHTTPTransports(Project project, Channel channel, String roleName) {
		return getTransports(project, channel, TransportType.HTTP, roleName);
	}
	
//	public static Collection<HttpTransport> getHTTPTransports(Project project, Channel channel, Role role) {
//		return getTransports(project, channel, TransportType.HTTP, role);
//	}
	
	public static Collection<JmsTransport> getJMSTransports(Project project, Channel channel) {
		return getTransports(project, channel, TransportType.JMS, null);
	}

	public static Collection<JmsTransport> getJMSTransports(Project project, Channel channel, String roleName) {
		return getTransports(project, channel, TransportType.JMS, roleName);
	}

//	public static Collection<JmsTransport> getJMSTransports(Project project, Channel channel, Role role) {
//		return getTransports(project, channel, TransportType.JMS, role);
//	}
	
//	public static <T extends Transport> Collection<T> getTransports(Project project, Channel channel, TransportType transportType, String roleName) {
//		Role role = getRole(project, channel, roleName);
//		return getTransports(project, channel, transportType, role);
//	}
	
	/**
	 * Role will be used for security check.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Transport> Collection<T> getTransports(Project project, Channel channel, TransportType transportType, String roleName) {
		List<T> transports = new ArrayList<T>();
		List<Link> links = MessagingUtil.getLinks(project);
		
		List<Receiver> receivers = ChannelUtil.getReceivers(channel);
		Iterator<Receiver> receiverIterator = receivers.iterator();
		while (receiverIterator.hasNext()) {
			Receiver receiver = receiverIterator.next();
			String receiverLink = receiver.getLink();
//			if (StringUtils.isEmpty(receiverLink))
//				receiverLink = channel.getReceiveLink();
			Assert.notNull(receiverLink, "Link not found for receiver: "+receiver.getName());
			String listenerLinkName = receiver.getLink();
			Link listenerLink = null;
			Role2 listenerRole = null;
			
			Iterator<Link> linkIterator = links.iterator();
			while (linkIterator.hasNext()) {
				Link link = linkIterator.next();
				if (link.getName().equals(listenerLinkName)) {
					listenerLink = link;
					break;
				}
			}
			
			Assert.notNull(listenerLink, "Listener link not found");
			
			if (roleName != null) {
				Role2 role = getRole(project, channel, roleName);
				List<Role2> roles = LinkUtil.getRoles(listenerLink);
				Iterator<Role2> roleIterator = roles.iterator();
				while (roleIterator.hasNext()) {
					Role2 roleFromLink = roleIterator.next();
					if (roleFromLink.getName().equals(role.getName())) {
						listenerRole = roleFromLink;
						break;
					}
				}
			}
			
			if (roleName != null)
				Assert.notNull(listenerRole, "Listener role not found");
			//String defaultTransport = listenerRole.getDefaultTransport();
			//Assert.notNull(transport, "Transport not found for role: "+listenerRole.getName());

			List<Transport> transportsFromLink = LinkUtil.getTransports(listenerLink);
			Iterator<Transport> transportIterator = transportsFromLink.iterator();
			while (transportIterator.hasNext()) {
				Transport transport = transportIterator.next();
				String transportRef = transport.getRef();
				if (transportRef != null)
					transport = MessagingUtil.getTransportByName(project, transportRef);
				Assert.notNull(transport, "Transport reference not found: "+transportRef);
				//TransportType transportType = TransportUtil.getTransportType(transport);
				if (transport.getType() == transportType) {
					transports.add((T) transport);
				}
			}
		}
		return transports;
	}
	
	
	public static Role2 getRole(Project project, Channel channel, String roleName) {
		List<Receiver> receivers = ChannelUtil.getReceivers(channel);
		Iterator<Receiver> receiverIterator = receivers.iterator();
		while (receiverIterator.hasNext()) {
			Receiver receiver = receiverIterator.next();
			String receiverLink = receiver.getLink();
//			if (StringUtils.isEmpty(receiverLink))
//				receiverLink = channel.getReceiveLink();
			Assert.notNull(receiverLink, "Link not found for receiver: "+receiver.getName());
			String receiverLinkName = receiver.getLink();
			
			Link listenerLink = null;
			List<Link> links = MessagingUtil.getLinks(project);
			Iterator<Link> linkIterator = links.iterator();
			while (linkIterator.hasNext()) {
				Link link = linkIterator.next();
				if (link.getName().equals(receiverLinkName)) {
					listenerLink = link;
					break;
				}
			}
			
			Assert.notNull(listenerLink, "Listener link not found");
			List<Role2> roles = LinkUtil.getRoles(listenerLink);
			Iterator<Role2> roleIterator = roles.iterator();
			while (roleIterator.hasNext()) {
				Role2 role = roleIterator.next();
				if (role.getName().equals(roleName)) {
					return role;
				}
			}
		}
		return null;
	}

	
	public static String getDestinationName(Service service, Channel channel) {
		String channelPart = NameUtil.uncapName(channel.getName());
		String domainPart = service.getDomain().replace(".", "_").replace("-", "_");
		String servicePart = NameUtil.splitStringUsingUnderscores(service.getName());
		//String servicePart = NameUtil.uncapName(service.getName());
		String type = "queue";
		String name = channelPart+"_"+domainPart+"_"+servicePart+"_"+type;
		if (channelPart.endsWith(domainPart) ||
			channelPart.equalsIgnoreCase(domainPart))
			name = domainPart+"_"+servicePart+"_"+type;
		return name;
	}

	
	public static List<RmiTransport> getRmiTransports(Transports transports) {
		return getObjectList(transports, RmiTransport.class);
	}

	public static List<EjbTransport> getEjbTransports(Transports transports) {
		return getObjectList(transports, EjbTransport.class);
	}
	
	public static List<HttpTransport> getHttpTransports(Transports transports) {
		return getObjectList(transports, HttpTransport.class);
	}
	
	public static List<JmsTransport> getJmsTransports(Transports transports) {
		return getObjectList(transports, JmsTransport.class);
	}

	public static <T> List<T> getObjectList(Transports transports, Class<?> objectClass) {
		List<Transport> objects = transports.getRmiTransportsAndEjbTransportsAndHttpTransports();
		return ListUtil.getObjectList(objects, objectClass);
	}
	
	public static <T> T getObject(Transports transports, Class<?> objectClass) {
		List<Transport> objects = transports.getRmiTransportsAndEjbTransportsAndHttpTransports();
		return ListUtil.getObject(objects, objectClass);
	}

	
}
