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
import java.util.TreeSet;

import nam.model.Application;
import nam.model.Cache;
import nam.model.Callback;
import nam.model.Channel;
import nam.model.Command;
import nam.model.Component;
import nam.model.Dependency;
import nam.model.Direction;
import nam.model.Execution;
import nam.model.Fault;
import nam.model.Group;
import nam.model.Interaction;
import nam.model.Interactor;
import nam.model.Invoker;
import nam.model.Link;
import nam.model.Listener;
import nam.model.Module;
import nam.model.Operation;
import nam.model.Process;
import nam.model.Project;
import nam.model.Receiver;
import nam.model.Result;
import nam.model.Sender;
import nam.model.Service;
import nam.model.Timeout;
import nam.model.Transacted;
import nam.model.TransactionScope;
import nam.model.TransactionUsage;
import nam.model.Unit;
import nam.model.statement.DefinitionCommand;
import nam.model.statement.ExpressionStatement;
import nam.model.statement.IfStatement;
import nam.model.statement.PostCommand;
import nam.model.statement.ReplyCommand;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.common.Property;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class ServiceUtil {

	public static String getKey(Service service) {
		return service.getDomain() + "." + service.getName();
	}
	
	public static String getLabel(Service service) {
		return NameUtil.capName(service.getName());
	}

	public static boolean isEmpty(Service service) {
		if (service == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Service> serviceList) {
		if (serviceList == null  || serviceList.size() == 0)
			return true;
		Iterator<Service> iterator = serviceList.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			if (!isEmpty(service))
				return false;
		}
		return true;
	}
	
	public static String toString(Service service) {
		if (isEmpty(service))
			return "Service: [uninitialized] "+service.toString();
		String text = service.toString();
		return text;
	}
	
	public static String toString(Collection<Service> serviceList) {
		if (isEmpty(serviceList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Service> iterator = serviceList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Service service = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(service);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Service create() {
		Service service = new Service();
		initialize(service);
		return service;
	}
	
	public static void initialize(Service service) {
		//nothing for now
	}
	
	public static boolean validate(Service service) {
		if (service == null)
			return false;
		Validator validator = Validator.getValidator();
		ListenerUtil.validate(getListeners(service));
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Service> serviceList) {
		Validator validator = Validator.getValidator();
		Iterator<Service> iterator = serviceList.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			//TODO break or accumulate?
			validate(service);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Service> serviceList) {
		Collections.sort(serviceList, createServiceComparator());
	}
	
	public static Collection<Service> sortRecords(Collection<Service> serviceCollection) {
		List<Service> list = new ArrayList<Service>(serviceCollection);
		Collections.sort(list, createServiceComparator());
		return list;
	}
	
	public static Comparator<Service> createServiceComparator() {
		return new Comparator<Service>() {
			public int compare(Service service1, Service service2) {
				Object key1 = getKey(service1);
				Object key2 = getKey(service2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				System.out.println(">>> "+status+". "+key2+", "+key2);
				return status;
			}
		};
	}
	
	public static Service clone(Service service) {
		if (service == null)
			return null;
		Service clone = create();
		clone.setElement(ObjectUtil.clone(service.getElement()));
		clone.setName(ObjectUtil.clone(service.getName()));
		//clone.setLabel(ObjectUtil.clone(service.getLabel()));
		clone.setDomain(ObjectUtil.clone(service.getDomain()));
		clone.setNamespace(ObjectUtil.clone(service.getNamespace()));
		clone.setVersion(ObjectUtil.clone(service.getVersion()));
		clone.setDescription(ObjectUtil.clone(service.getDescription()));
		clone.setTransacted(TransactedUtil.clone(service.getTransacted()));
		addListeners(clone, ListenerUtil.clone(getListeners(service)));
		addOperations(clone, OperationUtil.clone(getOperations(service)));
		addComponents(clone, ComponentUtil.clone(getComponents(service)));
		//clone.setCreationDate(ObjectUtil.clone(service.getCreationDate()));
		//clone.setLastUpdate(ObjectUtil.clone(service.getLastUpdate()));
		return clone;
	}
	
	public static boolean isStateful(Service service) {
		boolean isManager = service.getName().endsWith("Manager");
		return !isManager && ProcessUtil.isStateful(service.getProcess());
	}

	public static boolean hasTimeout(Service service) {
		List<Listener> listeners = getListeners(service);
		Iterator<Listener> iterator = listeners.iterator();
		while (iterator.hasNext()) {
			Listener listener = iterator.next();
			Long timeout = listener.getTimeout();
			if (timeout != null && timeout > 0L)
				return true;
		}
		return false;
	}
	
	//TODO make this a QName or better
	public static String getServiceId(Service service) {
		String domainName = service.getDomain();
		String serviceName = service.getName();
		String serviceId = getServiceId(domainName, serviceName);
		if (serviceId != null)
			return serviceId;
		if (service.getRef() != null)
			return service.getRef();
		return null;
	}

	public static String getServiceId(String domainName, String serviceName) {
		if (domainName != null && serviceName != null)
			return domainName + "." + serviceName;
		return null;
	}

//	public static String getServiceURL(Service service) {
//		return getServiceURL(RuntimeManager.INSTANCE.getDefaultURL(), service);
//	}

	//TODO finalize URL structure OR make it externally configurable with appropriate default
	public static String getServiceURL(String host, int port, Service service) {
		return "http://"+host+":"+port+"/"+service.getName()+"Service/"+service.getName();
	}
	
	//TODO finalize URL structure OR make it externally configurable with appropriate default
	public static String getServiceURL(String address, Service service) {
		return address+"/"+service.getName()+"Service/"+service.getName();
	}

	public static String getRootName(Service service) {
		String rootName = service.getName();
		if (rootName.endsWith("Service")) {
			int index = rootName.lastIndexOf("Service");
			rootName = rootName.substring(0, index);
		}
		rootName = NameUtil.capName(rootName);
		return rootName;
	}

	
//	public static List<Parameter> getParameters(Service service) {
//		return getObjectList(service, Parameter.class);
//	}

	public static List<Result> getResults(Service service) {
		return getObjectList(service, Result.class);
	}

	public static List<Timeout> getTimeouts(Service service) {
		return getObjectList(service, Timeout.class);
	}

	public static void addTimeout(Service service, Timeout timeout) {
		List<Serializable> list = service.getChannelsAndListenersAndOperations();
		synchronized (list) {
			if (list.contains(timeout)) {
				list.add(timeout);
			}
		}
	}

	public static boolean removeTimeout(Service service, Timeout timeout) {
		List<Serializable> list = service.getChannelsAndListenersAndOperations();
		synchronized (list) {
			if (list.contains(timeout)) {
				return list.remove(timeout);
			}
		}
		return false;
	}


	//TODO do better check for existence
	public static void addListener(Service service, Listener listener) {
		if (!hasListener(service, listener))
			service.getChannelsAndListenersAndOperations().add(listener);
	}
	
	public static void addListeners(Service service, List<Listener> listeners) {
		Iterator<Listener> iterator = listeners.iterator();
		while (iterator.hasNext()) {
			Listener listener = iterator.next();
			addListener(service, listener);
		}
	}
	
	public static boolean removeListener(Service service, Listener listener) {
		if (hasListener(service, listener))
			return service.getChannelsAndListenersAndOperations().remove(listener);
		return false;
	}

	public static void removeListeners(Service service) {
		List<Listener> listeners = getListeners(service);
		removeListeners(service, listeners);
	}
	
	public static void removeListeners(Service service, List<Listener> listeners) {
		Iterator<Listener> iterator = listeners.iterator();
		while (iterator.hasNext()) {
			Listener listener = iterator.next();
			removeListener(service, listener);
		}
	}
	
	public static boolean hasListener(Service service, Receiver receiver) {
		return hasListener(service, receiver.getChannel(), receiver.getLink(), receiver.getName());
	}

	public static boolean hasListener(Service service, Listener listener) {
		return hasListener(service, listener.getChannel(), listener.getLink(), listener.getRole());
	}

	public static boolean hasListener(Service service, String channel, String link, String role) {
		List<Listener> listeners = getListeners(service);
		Iterator<Listener> iterator = listeners.iterator();
		boolean found = false;
		while (iterator.hasNext()) {
			Listener listener = iterator.next();
			//listener.setRole(null);
			if (!listener.getChannel().equals(channel))
				continue;
			if (!listener.getLink().equals(link))
				continue;
			if (!listener.getRole().equals(role))
				continue;
			found = true;
			break;
		}
		return found;
	}
	
	public static List<Listener> getListeners(Service service) {
		return getObjectList(service, Listener.class);
	}

	public static List<Invoker> getInvokers(Service service) {
		return getObjectList(service, Invoker.class);
	}

	public static List<Sender> getSenders(Service service) {
		return getObjectList(service, Sender.class);
	}

	public static List<Receiver> getReceivers(Service service) {
		return getObjectList(service, Receiver.class);
	}
	
	public static List<Callback> getCallbacks(Service service) {
		return getObjectList(service, Callback.class);
	}
	
	public static List<Callback> getIncomingCallbacks(Service service) {
		return getCallbacks(service, Direction.IN);
	}
	
	public static List<Callback> getOutgoingCallbacks(Service service) {
		return getCallbacks(service, Direction.OUT);
	}

	public static List<Callback> getIncomingCallbacks(List<Service> services) {
		return getCallbacks(services, Direction.IN);
	}
	
	public static List<Callback> getOutgoingCallbacks(List<Service> services) {
		return getCallbacks(services, Direction.OUT);
	}

	public static List<Callback> getDistinctIncomingCallbacks(Service service) {
		return getDistinctCallbacks(service, getCallbacks(service, Direction.IN));
	}
	
	public static List<Callback> getDistinctOutgoingCallbacks(Service service) {
		return getDistinctCallbacks(service, getCallbacks(service, Direction.OUT));
	}
	
	public static List<Callback> getCallbacks(List<Service> services, Direction direction) {
		List<Callback> list = new ArrayList<Callback>();
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			list.addAll(getCallbacks(service, direction));
		}
		return list;
	}

	public static List<Callback> getCallbacks(Service service, Direction direction) {
		List<Callback> list = new ArrayList<Callback>();
		List<Callback> callbacks = getCallbacks(service);
		Iterator<Callback> iterator = callbacks.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			if (callback.getDirection() == direction)
				list.add(callback);
		}
		return list;
	}

	public static List<Callback> getDistinctCallbacks(Service service, List<Callback> callbacks) {
		Map<String, Callback> distinctCallbacks = new HashMap<String, Callback>();
		Iterator<Callback> iterator = callbacks.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			String key = callback.getNamespace() + "/" + callback.getName();
			distinctCallbacks.put(key, callback);
		}
		List<Callback> results = new ArrayList<Callback>();
		results.addAll(distinctCallbacks.values());
		sortServices(results);
		return results;
	}

	public static boolean isIncomingCallback(Service service) {
		return isCallback(service, Direction.IN);
	}

	public static boolean isOutgoingCallback(Service service) {
		return isCallback(service, Direction.OUT);
	}
	
	public static boolean isCallback(Service service, Direction direction) {
		if (service instanceof Callback) {
			Callback callback = (Callback) service;
			return callback.getDirection() == direction;
		}
		return false;
	}
	
	public static Callback getCallbackByName(Service service, String callbackInterfaceName) {
		List<Callback> callbacks = getCallbacks(service);
		return getCallbackByName(callbacks, callbackInterfaceName);
	}

	public static Callback getOutgoingCallbackByName(Service service, String callbackInterfaceName) {
		List<Callback> callbacks = getOutgoingCallbacks(service);
		return getCallbackByName(callbacks, callbackInterfaceName);
	}

	public static Callback getCallbackByName(List<Callback> callbacks, String callbackInterfaceName) {
		Iterator<Callback> iterator = callbacks.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			if (callback.getInterfaceName().equalsIgnoreCase(callbackInterfaceName)) {
				return callback;
			}
		}
		return null;
	}

	
	public static void addOperation(Service service, Operation operation) {
		List<Serializable> list = service.getChannelsAndListenersAndOperations();
		synchronized (list) {
			if (!list.contains(operation))
				list.add(operation);
		}
	}

	public static boolean removeOperation(Service service, Operation operation) {
		List<Serializable> list = service.getChannelsAndListenersAndOperations();
		synchronized (list) {
			if (!list.contains(operation))
				return list.remove(operation);
		}
		return false;
	}

	public static void addOperations(Service service, List<Operation> operations) {
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			addOperation(service, operation);
		}
	}
	
	public static List<Operation> getOperations(Service service) {
		return getObjectList(service, Operation.class);
	}

	public static Operation getDefaultOperation(Service service) {
		return getOperation(service, service.getName());
	}
	
	public static Operation getOperation(Service service, String operationName) {
		List<Operation> operations = getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			if (operation.getName().equalsIgnoreCase(operationName))
				return operation;
		}
		return null;
	}
	
	
	public static void addFault(Service service, Fault fault) {
		if (!service.getChannelsAndListenersAndOperations().contains(fault))
			service.getChannelsAndListenersAndOperations().add(fault);
	}
	
	public static List<Fault> getFaults(Service service) {
		return getObjectList(service, Fault.class);
	}

	public static List<Execution> getExecutions(Service service) {
		return getObjectList(service, Execution.class);
	}

	public static List<Dependency> getDependencies(Service service) {
		return getObjectList(service, Dependency.class);
	}

	public static List<Interactor> getInteractors(Service service) {
		return getObjectList(service, Interactor.class);
	}
	
	public static List<Interactor> getInteractors(Service service, Interaction interaction) {
		List<Interactor> list = new ArrayList<Interactor>();
		List<Interactor> interactors = getInteractors(service);
		Iterator<Interactor> iterator = interactors.iterator();
		while (iterator.hasNext()) {
			Interactor interactor = iterator.next();
			if (interactor.getInteraction() == interaction)
				list.add(interactor);
		}
		return list;
	}

	public static <T> T getObjectById(Service service, Class<?> objectClass, String name) {
		List<Object> objectList = getObjectList(service, objectClass);
		return ListUtil.getObjectByValue(objectList, objectClass, "getId", name);
	}
	
	public static <T> T getObjectByName(Service service, Class<?> objectClass, String name) {
		List<Object> objectList = getObjectList(service, objectClass);
		return ListUtil.getObjectByValue(objectList, objectClass, "getName", name);
	}
	
	public static <T> List<T> getObjectList(Service service, Class<?> objectClass) {
		List<Serializable> objects = service.getChannelsAndListenersAndOperations();
		return ListUtil.getObjectList(objects, objectClass);
	}
	
	public static <T> T getObject(Service service, Class<?> objectClass) {
		List<Serializable> objects = service.getChannelsAndListenersAndOperations();
		return ListUtil.getObject(objects, objectClass);
	}
	
	
//	public static List<Interactor> getListeners(Service service) {
//		List<Interactor> listeners = getInteractors(service, Interaction.LISTEN);
//		return listeners;
//	}

//	public static List<Interactor> getInteractors(Service service, Interaction interaction) {
//		List<Interactor> interactors = new ArrayList<Interactor>();
//		Iterator<Interactor> iterator = service.getInteractors().iterator();
//		while (iterator.hasNext()) {
//			Interactor interactor = iterator.next();
//			if (interactor.getInteraction() == interaction)
//				interactors.add(interactor);
//		}
//		return interactors;
//	}

//	public static List<Execution> getExecutions(Service service) {
//		List<Execution> executions = service.getExecutions();
//		return executions;
//	}
	
	public static List<Property> getProperties(Service service) {
		List<Property> properties = service.getProperties();
		return properties;
	}

	public static List<Link> getLinksOLD(Service service) {
		List<Link> links = new ArrayList<Link>();
		links.addAll(LinkUtil.createReceiveLinksFromInteractors(getListeners(service)));
		links.addAll(LinkUtil.createSendLinksFromInteractors(getSenders(service)));
		links.addAll(LinkUtil.createSendLinksFromInteractors(getInvokers(service)));
		return links;
	}

//	public static void addChannel(Service service, Channel channel) {
//		List<Channel> channels = getChannels(service);
//		if (!ChannelUtil.isChannelExists(channels, channel))
//			service.getChannelsAndListenersAndOperations().add(channel);
//	}

	public static void addChannel(Service service, Channel channel) {
		if (!service.getChannelsAndListenersAndOperations().contains(channel))
			service.getChannelsAndListenersAndOperations().add(channel);
	}

	public static void addChannels(Service service, List<Channel> channels) {
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			addChannel(service, channel);
		}
	}
	
	public static boolean removeChannel(Service service, Channel channel) {
		List<Channel> channels = getChannels(service);
		if (ChannelUtil.isChannelExists(channels, channel))
			return service.getChannelsAndListenersAndOperations().remove(channel);
		return false;
	}
	
	public static List<Channel> getChannels(Service service) {
		List<Channel> channels = getObjectList(service, Channel.class);
		return channels;
	}
	
	public static Collection<String> getChannelNames(Service service) {
		Set<String> channelNames = new TreeSet<String>();
		channelNames.addAll(getOutgoingChannelNames(service));
		channelNames.addAll(getIncomingChannelNames(service));
		return channelNames;
	}

	public static Collection<String> getOutgoingChannelNames(Service service) {
		Set<String> channelNames = new TreeSet<String>();
		channelNames.addAll(getChannelNames(getSenders(service)));
		channelNames.addAll(getChannelNames(getInvokers(service)));
		return channelNames;
	}
	
	public static Collection<String> getIncomingChannelNames(Service service) {
		Set<String> channelNames = new TreeSet<String>();
		channelNames.addAll(getChannelNames(getListeners(service)));
		channelNames.addAll(getChannelNames(getReceivers(service)));
		return channelNames;
	}
	
	public static <T extends Interactor> Collection<String> getChannelNames(List<T> interactors) {
		Set<String> channelNames = new TreeSet<String>();
		Iterator<? extends Interactor> iterator = interactors.iterator();
		while (iterator.hasNext()) {
			Interactor interactor = iterator.next();
			String channelName = interactor.getChannel();
			channelNames.add(channelName);
		}
		return channelNames;
	}

	//TODO: use only the first channel for now
	//TODO assuming only 1 incoming channel
	public static String getFirstIncomingChannelName(Service service) {
		String serviceId = ServiceUtil.getServiceId(service);
		
		Collection<String> channelNames = ServiceUtil.getIncomingChannelNames(service);
		Assert.notEmpty(channelNames, "No channel found for service: "+serviceId);
		
		String channelName = channelNames.iterator().next();
		int indexOfDot = channelName.indexOf(".");
		if (channelName.contains("."))
			channelName = channelName.substring(0, indexOfDot);
		channelName = NameUtil.uncapName(channelName);
		return channelName;
	}
	
	//TODO: use only the first channel for now
	//TODO assuming only 1 incoming channel
	public static String getFirstOutgoingChannelName(Service service) {
		String serviceId = ServiceUtil.getServiceId(service);
		Collection<String> channelNames = ServiceUtil.getOutgoingChannelNames(service);
		Assert.notEmpty(channelNames, "No channel found for service: "+serviceId);
		
		String channelName = channelNames.iterator().next();
		int indexOfDot = channelName.indexOf(".");
		if (channelName.contains("."))
			channelName = channelName.substring(0, indexOfDot);
		channelName = NameUtil.uncapName(channelName);
		return channelName;
	}
	
	
//	public static Set<Interactor> getLinks(Service service) {
//		Set<Interactor> list = new HashSet<Interactor>();
//		list.addAll(service.getListeners());
//		list.addAll(service.getInvokers());
//		list.addAll(service.getSenders());
//		return list;
//	}

//	public static List<Invoker> getInvokers(Service service) {
//		return service.getInvokers();
//	}

//	public static List<Sender> getSenders(Service service) {
//		return service.getSenders();
//	}

	

	/*
	 * Cache unit related
	 */
	
	public static boolean hasCacheUnitReference(Service service) {
		Set<Cache> references = getCacheUnitReferences(service);
		return !references.isEmpty();
	}
	
	public static boolean hasCacheUnitReference(Service service, String cacheName) {
		Cache cacheUnit = getCacheUnitReference(service, cacheName);
		return cacheUnit != null;
	}
	
	public static Cache getCacheUnitReference(Service service, String cacheName) {
		Process process = service.getProcess();
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		Operation processOperation = ProcessUtil.getOperation(process, serviceOperation.getName());
		List<Command> commands = processOperation.getCommands();

		Collection<ExpressionStatement> expressionStatements = CommandUtil.getCommands(commands, ExpressionStatement.class);
		Iterator<ExpressionStatement> iterator = expressionStatements.iterator();
		while (iterator.hasNext()) {
			ExpressionStatement expressionStatement = iterator.next();
			String targetName = expressionStatement.getTargetName();
			Cache cacheUnit = ProcessUtil.getCacheUnit(process, targetName);
			if (cacheUnit != null && cacheUnit.getName().equalsIgnoreCase(cacheName))
				return cacheUnit;
		}
		
		List<Callback> callbacks = ServiceUtil.getIncomingCallbacks(service);
		Iterator<Callback> iterator3 = callbacks.iterator();
		while (iterator3.hasNext()) {
			Callback callback = iterator3.next();
			Cache cacheUnit = getCacheUnitReference(callback, cacheName);	
			if (cacheUnit != null)
				return cacheUnit;
		}
		return null;
	}

	public static Set<Cache> getCacheUnitReferences(Collection<Service> services) {
		Set<Cache> cacheUnits = new TreeSet<Cache>();
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			Set<Cache> references = getCacheUnitReferences(service);
			cacheUnits.addAll(references);
		}
		return cacheUnits;
	}
	
	public static Set<Cache> getCacheUnitReferences(Service service) {
		Set<Cache> cacheUnits = new TreeSet<Cache>();
		Process process = service.getProcess();
		if (process != null)
			cacheUnits.addAll(process.getCacheUnits());
		
//		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
//		Operation processOperation = ProcessUtil.getOperation(process, serviceOperation.getName());
//		List<Command> commands = processOperation.getCommands();
//
//		Collection<DefinitionCommand> definitionCommands = CommandUtil.getCommands(commands, DefinitionCommand.class);
//		Iterator<DefinitionCommand> iterator = definitionCommands.iterator();
//		while (iterator.hasNext()) {
//			DefinitionCommand definitionCommand = iterator.next();
//			String targetName = definitionCommand.getDefinition().getTarget();
//			if (targetName.endsWith("Manager"))
//				targetName = targetName.substring(0, targetName.length()-7);
//			Cache cacheUnit = ProcessUtil.getCacheUnit(process, targetName);
//			if (cacheUnit != null)
//				cacheUnits.add(cacheUnit);
//		}
		
		List<Callback> callbacks = ServiceUtil.getIncomingCallbacks(service);
		Iterator<Callback> iterator3 = callbacks.iterator();
		while (iterator3.hasNext()) {
			Callback callback = iterator3.next();
			cacheUnits.addAll(getCacheUnitReferences(callback));			
		}
		return cacheUnits;
	}

	public static Set<Cache> getCacheUnitReferences(Process process, List<Command> commands) {
		Set<Cache> cacheUnits = new TreeSet<Cache>();

		Collection<ExpressionStatement> expressionStatements = CommandUtil.getCommands(commands, ExpressionStatement.class);
		Iterator<ExpressionStatement> iterator2 = expressionStatements.iterator();
		while (iterator2.hasNext()) {
			ExpressionStatement expressionStatement = iterator2.next();
			String targetName = expressionStatement.getTargetName();
			if (targetName.endsWith("Manager"))
				targetName = targetName.substring(0, targetName.length()-7);
			Cache cacheUnit = ProcessUtil.getCacheUnit(process, targetName);
			if (cacheUnit != null)
				cacheUnits.add(cacheUnit);
		}
		return cacheUnits;
	}

	
	/*
	 * Data unit related
	 */
	
	public static boolean hasDataUnitReference(Service service) {
		Set<Unit> references = getDataUnitReferences(service);
		return !references.isEmpty();
	}
	
	public static boolean hasDataUnitReference(Service service, String cacheName) {
		Unit dataUnit = getDataUnitReference(service, cacheName);
		return dataUnit != null;
	}
	
	public static Unit getDataUnitReference(Service service, String unitName) {
		Process process = service.getProcess();
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		Operation processOperation = ProcessUtil.getOperation(process, serviceOperation.getName());
		List<Command> commands = processOperation.getCommands();

		Collection<ExpressionStatement> expressionStatements = CommandUtil.getCommands(commands, ExpressionStatement.class);
		Iterator<ExpressionStatement> iterator = expressionStatements.iterator();
		while (iterator.hasNext()) {
			ExpressionStatement expressionStatement = iterator.next();
			String targetName = expressionStatement.getTargetName();
			Unit dataUnit = ProcessUtil.getDataUnit(process, targetName);
			if (dataUnit != null && dataUnit.getName().equalsIgnoreCase(unitName))
				return dataUnit;
		}
		
		List<Callback> callbacks = ServiceUtil.getIncomingCallbacks(service);
		Iterator<Callback> iterator3 = callbacks.iterator();
		while (iterator3.hasNext()) {
			Callback callback = iterator3.next();
			Unit dataUnit = getDataUnitReference(callback, unitName);	
			if (dataUnit != null)
				return dataUnit;
		}
		return null;
	}
	
	public static Set<Unit> getDataUnitReferences(Collection<Service> services) {
		Set<Unit> unitNames = new TreeSet<Unit>();
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			Set<Unit> references = getDataUnitReferences(service);
			unitNames.addAll(references);
		}
		return unitNames;
	}
	
	public static Set<Unit> getDataUnitReferences(Service service) {
		Set<Unit> units = new TreeSet<Unit>();
		Operation operation = ServiceUtil.getDefaultOperation(service);
		if (operation != null) {
			units.addAll(getDataUnitReferences(service, operation));
		} else {
			List<Operation> operations = ServiceUtil.getOperations(service);
			Iterator<Operation> iterator = operations.iterator();
			while (iterator.hasNext()) {
				operation = iterator.next();
				units.addAll(getDataUnitReferences(service, operation));
			}
		}
		return units;
	}
	
	public static Set<Unit> getDataUnitReferences(Service service, Operation operation) {
		Set<Unit> units = new TreeSet<Unit>();
		Process process = service.getProcess();
		if (process == null)
			return units;
		
		Operation processOperation = ProcessUtil.getOperation(process, operation.getName());
		List<Command> commands = processOperation.getCommands();

		Collection<DefinitionCommand> definitionCommands = CommandUtil.getCommands(commands, DefinitionCommand.class);
		Iterator<DefinitionCommand> iterator = definitionCommands.iterator();
		while (iterator.hasNext()) {
			DefinitionCommand definitionCommand = iterator.next();
			String targetName = definitionCommand.getDefinition().getTarget();
			if (targetName.endsWith("Manager"))
				targetName = targetName.substring(0, targetName.length()-7);
			Unit dataUnit = ProcessUtil.getDataUnit(process, targetName);
			if (dataUnit != null)
				units.add(dataUnit);
		}
		
		Collection<ExpressionStatement> expressionStatements = CommandUtil.getCommands(commands, ExpressionStatement.class);
		Iterator<ExpressionStatement> iterator2 = expressionStatements.iterator();
		while (iterator2.hasNext()) {
			ExpressionStatement expressionStatement = iterator2.next();
			String targetName = expressionStatement.getTargetName();
			if (targetName.endsWith("Manager"))
				targetName = targetName.substring(0, targetName.length()-7);
			Unit dataUnit = ProcessUtil.getDataUnit(process, targetName);
			if (dataUnit != null)
				units.add(dataUnit);
		}
		
		List<Callback> callbacks = ServiceUtil.getIncomingCallbacks(service);
		Iterator<Callback> iterator3 = callbacks.iterator();
		while (iterator3.hasNext()) {
			Callback callback = iterator3.next();
			units.addAll(getDataUnitReferences(callback));			
		}
		return units;
	}
	
	
	/*
	 * Remote service related
	 */
	public static Set<String> getRemoteServiceReferences(Service service) {
		Set<String> remoteServiceNames = new TreeSet<String>();
		Process process = service.getProcess();
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		Operation processOperation = ProcessUtil.getOperation(process, serviceOperation.getName());
		
		List<Interactor> interactors = new ArrayList<Interactor>();
		interactors.addAll(getInteractors(processOperation, Invoker.class));
		interactors.addAll(getInteractors(processOperation, Sender.class));
		Iterator<Interactor> iterator = interactors.iterator();
		while (iterator.hasNext()) {
			Interactor interactor = iterator.next();
			String targetName = interactor.getTarget();
			remoteServiceNames.add(targetName);
		}
		return remoteServiceNames;
	}
	
	

	public static Map<String, Service> createMap(List<Service> services) {
		Map<String, Service> map = new HashMap<String, Service>();
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			map.put(service.getName(), service);
		}
		return map;
	}

	//merges service2 into service1
	public static void mergeService(Service service1, Service service2) {
		if (service2.getName() != null)
			service1.setName(service2.getName());
		if (service2.getNamespace() != null)
			service1.setNamespace(service2.getNamespace());
		if (service2.getDomain() != null)
			service1.setDomain(service2.getDomain());
		if (service2.getVersion() != null)
			service1.setVersion(service2.getVersion());
		if (service2.getPortType() != null)
			service1.setPortType(service2.getPortType());
		if (service2.getPackageName() != null)
			service1.setPackageName(service2.getPackageName());
		if (service2.getInterfaceName() != null)
			service1.setInterfaceName(service2.getInterfaceName());
		if (service2.getClassName() != null)
			service1.setClassName(service2.getClassName());
		service1.getChannelsAndListenersAndOperations().addAll(service2.getChannelsAndListenersAndOperations());
		//if (service2.getState() != null)
		//	service1.setState(service2.getState());
		if (service2.getProcess() != null)
			service1.setProcess(service2.getProcess());
		
		//TODO - Do we need this anymore?
		//MergeResolver<Operation> operationMergeResolver = new OperationMergeResolver();
		//MergeResolver<Execution> executionMergeResolver = new ExecutionMergeResolver();
		//mergeElements(service1.getOperations(), service2.getOperations(), operationMergeResolver);
		//mergeElements(service1.getExecutions(), service2.getExecutions(), executionMergeResolver);
		
		if (service1.getVersion() == null)
			service1.setVersion("0.0.1-SNAPSHOT");
	}

	
	private static interface MergeResolver<T> {
		
		public String getKey(T arg); 
		
		public boolean equals(T arg1, T arg2);
	
	}
	
	private static class OperationMergeResolver implements MergeResolver<Operation> {
		
		public String getKey(Operation operation) {
			return operation.getName();
		}

		public boolean equals(Operation operation1, Operation operation2) {
			return OperationUtil.equals(operation1, operation2);
		}
	}

	private static class ExecutionMergeResolver implements MergeResolver<Execution> {
		
		public String getKey(Execution execution) {
			return execution.getName();
		}
		
		public boolean equals(Execution execution1, Execution execution2) {
			return ExecutionUtil.equals(execution1, execution2);
		}
	}

	public static <T> void mergeElements(List<T> elements1, List<T> elements2, MergeResolver<T> mergeResolver) {
		Map<String, T> elementMap = new HashMap<String, T>();
		List<T> elementList = new ArrayList<T>();
		Iterator<T> iterator1 = elements1.iterator();
		Iterator<T> iterator2 = elements2.iterator();

		while (iterator1.hasNext()) {
			T element = iterator1.next();
			String key = mergeResolver.getKey(element);
			elementMap.put(key, element);
		}

		while (iterator2.hasNext()) {
			T element = iterator2.next();
			String key = mergeResolver.getKey(element);
			if (!elementMap.containsKey(key)) {
				elementList.add(element);
			} else {
				//TODO merge here
			}
		}
		elements1.addAll(elementList);
	}

	public static void mergeExecutions(Service service1, Service service2) {
		Map<String, Execution> executionMap = new HashMap<String, Execution>();
		List<Execution> executions = new ArrayList<Execution>();
		List<Execution> executions1 = getExecutions(service1);
		List<Execution> executions2 = getExecutions(service2);
		Iterator<Execution> iterator1 = executions1.iterator();
		Iterator<Execution> iterator2 = executions2.iterator();

		while (iterator1.hasNext()) {
			Execution execution = iterator1.next();
			executionMap.put(execution.getName(), execution);
		}

		while (iterator2.hasNext()) {
			Execution execution = iterator2.next();
			if (!executionMap.containsKey(execution.getName())) {
				executions.add(execution);
			} else {
				//TODO merge here
			}
		}
		
		executions1.addAll(executions);
	}

//	public static String getDomain(Service service) {
//		if (!StringUtils.isEmpty(service.getDomain()))
//			return service.getDomain();
//		String domainName = NameUtil.getPackageNameFromNamespace(service.getNamespace());
//		return domainName;
//	}

//	public static String getPackageName(Service service) {
//		if (!StringUtils.isEmpty(service.getPackageName()))
//			return service.getPackageName();
//		String packageName = null;
//		if (service.getDomain() != null)
//			packageName = service.getDomain();
//		else packageName = NameUtil.getPackageNameFromNamespace(service.getNamespace());
//		return packageName + ".service";
//	}

//	public static String getPackageName(Service service, Channel channel) {
//		String packageName = null;
//		if (service.getDomain() != null)
//			packageName = service.getDomain();
//		else packageName = NameUtil.getPackageNameFromNamespace(service.getNamespace());
//		return packageName + NameUtil.uncapName(channel.getName());
//	}

//	public static String getInterfaceName(Service service) {
//		if (!StringUtils.isEmpty(service.getInterfaceName()))
//			return service.getInterfaceName();
//		String cappedName = NameUtil.capName(service.getName());
//		return cappedName;
//	}

//	public static String getClassName(Service service) {
//		if (!StringUtils.isEmpty(service.getClassName()))
//			return service.getClassName();
//		if (!StringUtils.isEmpty(service.getClazz()))
//			return service.getClazz();
//		String cappedName = NameUtil.capName(service.getName());
//		return cappedName + "Listener";
//	}

//	public static String getQualifiedName(Service service) {
//		String qualifiedName = getPackageName(service) + "." + getClassName(service);
//		return qualifiedName;
//	}
	
	public static String getType(Service service) {
		if (!StringUtils.isEmpty(service.getElement()))
			return service.getElement();
		String uncappedName = NameUtil.uncapName(service.getName());
		String typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(service.getNamespace() , uncappedName);
		return typeName;
	}

	public static String getNamespace(Service service) {
		String namespace = service.getNamespace();
		return namespace;
	}


	public static void setProperty(Service service, String name) {
		setProperty(service, name, "true");
	}
	
	public static void setProperty(Service service, String name, String value) {
		if (name.equals("transacted")) {
			Transacted transacted = getTransacted(service);
			if (value.equals("mandatory")) {
				transacted.setUse(TransactionUsage.MANDATORY);
			} else if (value.equals("required")) {
				transacted.setUse(TransactionUsage.REQUIRED);
			} else if (value.equals("requiresNew")) {
				transacted.setUse(TransactionUsage.REQUIRES_NEW);
			} else if (value.equals("supported")) {
				transacted.setUse(TransactionUsage.SUPPORTED);
			}
			
		} else if (name.equals("scope")) {
			Transacted transacted = getTransacted(service);
			if (value.equals("event")) {
				transacted.setScope(TransactionScope.EVENT);
			} else if (value.equals("method")) {
				transacted.setScope(TransactionScope.METHOD);
			} else if (value.equals("thread")) {
				transacted.setScope(TransactionScope.THREAD);
			} else if (value.equals("process")) {
				transacted.setScope(TransactionScope.PROCESS);
			} else if (value.equals("workflow")) {
				transacted.setScope(TransactionScope.CONVERSATION);
			}
			
		} else {
			Property property = lookupProperty(service, name);
			if (property == null) {
				property = new Property();
				property.setName(name);
				service.getProperties().add(property);
			}
			property.setValue(value);
		}
	}
	
	public static Transacted getTransacted(Service service) {
		Transacted transacted = service.getTransacted();
		if (transacted == null) {
			transacted = new Transacted();
			service.setTransacted(transacted);
		}
		return transacted;
	}

	
	public static Collection<Service> getInvokedServices(Project project, Service service) {
		List<Invoker> invokers = getInvokers(service);
		return getInvokedServices(project, invokers);
	}
	
	public static Collection<Service> getRemoteServices(Project project, Module module) {
		Set<Service> invokedServices = new HashSet<Service>();
		List<Process> processes = ModuleUtil.getProcesses(module);
		Iterator<Process> iterator = processes.iterator();
		while (iterator.hasNext()) {
			Process process = iterator.next();
			invokedServices.addAll(getRemoteServices(project, process));
		}
		return invokedServices;
	}
	
	public static Collection<Service> getRemoteServices(List<Project> projects, Service service) {
		Set<Service> set = new HashSet<Service>();
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			set.addAll(getRemoteServices(project, service));
		}
		List<Service> list = new ArrayList<Service>(set);
		sortServices(list);
		return list;
	}
	
	public static Collection<Service> getRemoteServices(Project project, Service service) {
		Set<Service> set = new HashSet<Service>();
		set.addAll(getInvokedServices(project, getInteractors(service, Invoker.class)));
		set.addAll(getInvokedServices(project, getInteractors(service, Sender.class)));
		
		List<Callback> incomingCallbacks = ServiceUtil.getIncomingCallbacks(service);
		Iterator<Callback> iterator2 = incomingCallbacks.iterator();
		while (iterator2.hasNext()) {
			Callback callback = iterator2.next();
			set.addAll(getInvokedServices(project, getInteractors(callback, Invoker.class)));
			set.addAll(getInvokedServices(project, getInteractors(callback, Sender.class)));
		}
		
		List<Service> list = new ArrayList<Service>(set);
		sortServices(list);
		return list;
	}
	
	public static Collection<Service> getRemoteServices(Project project, Process process) {
		Set<Service> services = new HashSet<Service>();
		services.addAll(getInvokedServices(project, getInteractors(process, Invoker.class)));
		services.addAll(getInvokedServices(project, getInteractors(process, Sender.class)));
		return services;
	}

	
	public static String getServiceName(Service service) {
		return NameUtil.capName(service.getName());
	}
	
	public static String getNamespaceAlias(Service service) {
		//String identifier = NameUtil.getLastSegment(service.getDomain(), ".");
		String identifier = NameUtil.getLastSegment(service.getNamespace(), "/");
		return NameUtil.capName(identifier);
	}

	public static String getDomainServiceName(Service service) {
		return getNamespaceAlias(service) + getServiceName(service);
	}
	
	public static String getDomainServiceName(Service service, Service service2) {
		//String identifier = NameUtil.getLastSegment(service.getDomain(), ".");
		String identifier = NameUtil.getLastSegment(service.getNamespace(), "/");
		return NameUtil.capName(identifier) + NameUtil.capName(service2.getName());
	}
	
	public static String getDomainServiceLabel(Service service) {
		String interfaceName = getDomainServiceName(service);
		String name = null;
		if (service instanceof Callback)
			name = "Local" + interfaceName + "";
		else name = "Remote" + interfaceName + "";
		return name;
	}
	
	public static String getExtendedDomainServiceName(Service service) {
		//String identifier = NameUtil.getLastSegment(service.getDomain(), ".");
		String identifier = NameUtil.getLastSegment(service.getNamespace(), "/");
		identifier = NameUtil.toCamelCase(identifier);
		return NameUtil.capName(identifier) + "_" + NameUtil.capName(service.getName());
	}
	
	public static String getExtendedDomainServiceName(Service service, Service service2) {
		//String identifier = NameUtil.getLastSegment(service.getDomain(), ".");
		String identifier = NameUtil.getLastSegment(service.getNamespace(), "/");
		return NameUtil.capName(identifier) + "_" + NameUtil.capName(service2.getName());
	}
	
	public static String getExtendedDomainServiceLabel(Service service) {
		String interfaceName = getExtendedDomainServiceName(service);
		String name = null;
		if (service instanceof Callback)
			name = "local_" + interfaceName + "";
		else name = "remote_" + interfaceName + "";
		return name;
	}

	public static String getExtendedDomainServiceLabel(Service service, Service service2) {
		//String identifier = NameUtil.getLastSegment(service.getDomain(), ".");
		String identifier = NameUtil.getLastSegment(service.getNamespace(), "/");
		String name = NameUtil.capName(identifier) + "_" + NameUtil.capName(service2.getName());
		if (service instanceof Callback)
			name = "local_" + name + "";
		else name = "remote_" + name + "";
		return name;
	}
	
	public static String getExtendedTypedDomainServiceLabel(Service service) {
		String interfaceName = getExtendedDomainServiceName(service);
		String name = null;
		if (service instanceof Callback)
			//locatedServiceId = "Local" + interfaceName + "";
			name = "local_" + interfaceName + "_response";
		else if (ServiceUtil.isSynchronous(service))
			name = "remote_" + interfaceName + "_request";
		else name = "remote_" + interfaceName + "_message";
		//else locatedServiceId = "Remote" + interfaceName + "";
		return name;
	}


	public static <T extends Interactor> Collection<Service> getInvokedServices(Project project, List<T> invokers) {
		Set<Service> invokedServices = new HashSet<Service>();
		Iterator<T> iterator = invokers.iterator();
		while (iterator.hasNext()) {
			T invoker = iterator.next();
			
			//This is the Linkage for now- 
			String targetName = invoker.getName();
			String serviceName = invoker.getService();
			//String channelId = invoker.getChannel();

			Group remoteGroup = ProjectUtil.getGroupByName(project, targetName);
			//Assert.notNull(remoteGroup, "Remote Group not found: " + targetName);
			if (remoteGroup != null) {
				List<Application> applications = remoteGroup.getApplications();
				Iterator<Application> iterator2 = applications.iterator();
				while (iterator2.hasNext()) {
					Application application = iterator2.next();
					Service remoteService = ApplicationUtil.getServiceByName(application, serviceName);
					Assert.notNull(remoteService, "Remote Service not found: " + serviceName);
					invokedServices.add(remoteService);
				}
				
			} else {
				Application remoteApplication = ProjectUtil.getApplicationByName(project, targetName);
				//Assert.notNull(remoteApplication, "Remote Application not found: " + targetName);
				if (remoteApplication == null)
					continue;
				
				Service remoteService = ApplicationUtil.getServiceByName(remoteApplication, serviceName);
				//Assert.notNull(remoteService, "Remote Service not found: " + serviceName);
				if (remoteService == null)
					continue;
				
				invokedServices.add(remoteService);
			}
		}
		return invokedServices;
	}

	public static <T extends Interactor> List<T> getInteractors(Service service, Class<T> interactorClass) {
		Process process = service.getProcess();
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
//		if (serviceOperation.getName().equals("queryBooks"))
//			System.out.println();
		Operation processOperation = ProcessUtil.getOperation(process, serviceOperation.getName());
		if (processOperation == null)
			System.out.println();
		List<T> interactors = getInteractors(processOperation, interactorClass);
		return interactors;
	}
	
	public static <T extends Interactor> List<T> getInteractors(Process process, Class<T> interactorClass) {
		List<T> interactors = new ArrayList<T>();
		List<Operation> operations = process.getOperations();
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			interactors.addAll(getInteractors(operation, interactorClass));
		}
		return interactors;
	}

	public static <T extends Interactor> List<T> getInteractors(Operation operation, Class<T> interactorClass) {
		return getInteractors(operation.getCommands(), interactorClass);
	}
	
	public static <T extends Interactor> List<T> getInteractors(List<Command> commands, Class<T> interactorClass) {
		List<T> interactors = new ArrayList<T>();
		Iterator<Command> iterator = commands.iterator();
		while (iterator.hasNext()) {
			Command command = iterator.next();
			Object actor = command.getActor();
			
			//TODO revisit this - why we need to skip Post and Reply commands...
			//TODO we skip them for now because we went Sender's from outgoing interactors
			if (command instanceof PostCommand)
				continue;
			if (command instanceof ReplyCommand)
				continue;
			
			if (command instanceof IfStatement) {
				IfStatement ifStatement = (IfStatement) command;
				interactors.addAll(getInteractors(ifStatement.getCommands(), interactorClass));
				interactors.addAll(getInteractors(ifStatement.getElseIfCommands(), interactorClass));
				interactors.addAll(getInteractors(ifStatement.getElseCommands(), interactorClass));
				continue;
			}
			
			if (command.getType().equals("unnamed")) {
				interactors.addAll(getInteractors(command.getCommands(), interactorClass));
				continue;
			}
				
//			if (actor == null)
//				System.out.println();
			if (actor != null && actor.getClass().equals(interactorClass)) {
				@SuppressWarnings("unchecked") T interactor = (T) actor;
				interactors.add(interactor);
			}
		}
		return interactors;
	}

	public static Collection<Service> getReplyToServices(Project project, Service service) {
		Set<Service> replyToServices = new HashSet<Service>();
		//Messaging messaging = ProjectUtil.getMessaging(project);
		//List<Channel> channels = MessagingUtil.getChannels(messaging);
		List<Callback> callbacks = ServiceUtil.getCallbacks(service);
		
		List<Sender> senders = getSenders(service);
		Iterator<Sender> iterator = senders.iterator();
		while (iterator.hasNext()) {
			Sender sender = iterator.next();
			
			//This is the Linkage for now- 
			String targetName = sender.getName();
			String serviceName = sender.getService();
			//String channelId = invoker.getChannel();

			Application remoteApplication = ProjectUtil.getApplicationByName(project, targetName);
			Assert.notNull(remoteApplication, "Remote Application not found: " + targetName);
			
			Service remoteService = ApplicationUtil.getServiceByName(remoteApplication, serviceName);
			Assert.notNull(remoteService, "Remote Service not found: " + serviceName);
			replyToServices.add(remoteService);
		}
		return replyToServices;
	}

	public static void setProcess(List<Service> services, Process process) {
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			service.setProcess(process);
		}
	}

	public static boolean isEnabled(Service service, String name) {
		List<Property> properties = service.getProperties();
		Iterator<Property> iterator = properties.iterator();
		while (iterator.hasNext()) {
			Property property = iterator.next();
			if (property.getName().equalsIgnoreCase(name)) {
				String value = property.getValue();
				if (value != null)
					return Boolean.valueOf(value);
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasProperty(Service service, String name) {
		return getProperty(service, name) != null;
	}
	
	public static String getProperty(Service service, String name) {
		List<Property> properties = service.getProperties();
		Iterator<Property> iterator = properties.iterator();
		while (iterator.hasNext()) {
			Property property = iterator.next();
			if (property.getName().equalsIgnoreCase(name)) {
				return property.getValue();
			}
		}
		return null;
	}

	public static Property lookupProperty(Service service, String name) {
		List<Property> properties = service.getProperties();
		Iterator<Property> iterator = properties.iterator();
		while (iterator.hasNext()) {
			Property property = iterator.next();
			if (property.getName().equalsIgnoreCase(name)) {
				return property;
			}
		}
		return null;
	}
	
	
	public static boolean isSynchronous(Service service) {
		//TODO this is a dumb assumption and should be taken out
		//List<Operation> operations = ServiceUtil.getOperations(service);
		//Iterator<Operation> iterator = operations.iterator();
		//while (iterator.hasNext()) {
		//	Operation operation = iterator.next();
		//	List<Result> results = operation.getResults();
		//	if (!results.isEmpty() && results.size() == 1) {
		//		return true;
		//	}
		//}
		boolean propertyEnabled = isEnabled(service, "synchronous");
		return propertyEnabled;
	}
	
	public static void setSynchronous(Service service) {
		setProperty(service, "synchronous");
	}

	public static void setSynchronous(Service service, boolean value) {
//		if (service.getName().equals("orderBooks") && value) 
//			System.out.println();
		setProperty(service, "synchronous", Boolean.toString(value));
	}


	public static boolean hasResult(Service service, Operation operation) {
		List<Result> results = operation.getResults();
		return !results.isEmpty();
	}

	public static boolean equals(Service service1, Service service2) {
		if (!service1.getNamespace().equals(service2.getNamespace()))
			return false;
		if (!service1.getName().equals(service2.getName()))
			return false;
		return true;
	}

	public static boolean containsService(List<Service> services, Service service) {
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service2 = iterator.next();
			if (ServiceUtil.equals(service2, service))
				return true;
			if (service instanceof Callback) {
				List<Callback> callbacks = ServiceUtil.getCallbacks(service2);
				if (containsCallback(callbacks, (Callback) service))
					return true;
			}
		}
		return false;
	}
	
	public static boolean containsCallback(List<Callback> callbacks, Callback callback) {
		Iterator<Callback> iterator = callbacks.iterator();
		while (iterator.hasNext()) {
			Callback callback2 = iterator.next();
			if (ServiceUtil.equals(callback2, callback))
				return true;
		}
		return false;
	}

	public static <T extends Service> void sortServices(List<T> services) {
		Collections.sort(services, new Comparator<T>() {
			public int compare(T service1, T service2) {
				return service1.getName().compareTo(service2.getName());
			}
		});
	}

	
	public static void addComponent(Service service, Component component) {
		if (!hasComponent(service, component))
			service.getChannelsAndListenersAndOperations().add(component);
	}
	
	public static void addComponents(Service service, List<Component> components) {
		Iterator<Component> iterator = components.iterator();
		while (iterator.hasNext()) {
			Component component = iterator.next();
			addComponent(service, component);
		}
	}
	
	public static boolean removeComponent(Service service, Component component) {
		if (hasComponent(service, component))
			return service.getChannelsAndListenersAndOperations().remove(component);
		return false;
	}

	public static void removeComponents(Service service) {
		List<Component> components = getComponents(service);
		removeComponents(service, components);
	}
	
	public static void removeComponents(Service service, List<Component> components) {
		Iterator<Component> iterator = components.iterator();
		while (iterator.hasNext()) {
			Component component = iterator.next();
			removeComponent(service, component);
		}
	}
	
	public static boolean hasComponent(Service service, Component component) {
		List<Component> components = getComponents(service);
		Iterator<Component> iterator = components.iterator();
		while (iterator.hasNext()) {
			Component component2 = iterator.next();
			if (component2.equals(component)) {
				return true;
			}
		}
		return false;
	}
	
	public static List<Component> getComponents(Service service) {
		return getObjectList(service, Component.class);
	}

}
