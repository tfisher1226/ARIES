package nam.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.model.Channel;
import nam.model.Channels;
import nam.model.Interactor;
import nam.model.Invoker;
import nam.model.Receiver;
import nam.model.Sender;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.Assert;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class ChannelUtil extends BaseUtil {

	public static Object getKey(Channel channel) {
		return channel.getName() + "[" + channel.getTransferMode() + "]";
	}

	public static String getLabel(Channel channel) {
		return NameUtil.capName(channel.getName());
	}

	public static boolean isEmpty(Channel channel) {
		if (channel == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Channel> channelList) {
		if (channelList == null  || channelList.size() == 0)
			return true;
		Iterator<Channel> iterator = channelList.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			if (!isEmpty(channel))
				return false;
		}
		return true;
	}
	
	public static String toString(Channel channel) {
		if (isEmpty(channel))
			return "";
		String text = channel.toString();
		return text;
	}
	
	public static String toString(Collection<Channel> channelList) {
		if (isEmpty(channelList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Channel> iterator = channelList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Channel channel = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(channel);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Channel create() {
		Channel channel = new Channel();
		initialize(channel);
		return channel;
	}
	
	public static void initialize(Channel channel) {
		//nothing for now
	}
	
	public static boolean validate(Channel channel) {
		if (channel == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Channel> channelList) {
		Validator validator = Validator.getValidator();
		Iterator<Channel> iterator = channelList.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			//TODO break or accumulate?
			validate(channel);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Channel> channelList) {
		Collections.sort(channelList, createChannelComparator());
	}
	
	public static Collection<Channel> sortRecords(Collection<Channel> channelCollection) {
		List<Channel> list = new ArrayList<Channel>(channelCollection);
		Collections.sort(list, createChannelComparator());
		return list;
	}
	
	public static Comparator<Channel> createChannelComparator() {
		return new Comparator<Channel>() {
			public int compare(Channel channel1, Channel channel2) {
				Object key1 = getKey(channel1);
				Object key2 = getKey(channel2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Channel clone(Channel channel) {
		if (channel == null)
			return null;
		Channel clone = create();
		clone.setRef(ObjectUtil.clone(channel.getRef()));
		clone.setName(ObjectUtil.clone(channel.getName()));
		clone.setHost(ObjectUtil.clone(channel.getHost()));
		clone.setPort(ObjectUtil.clone(channel.getPort()));
		clone.setJndiName(ObjectUtil.clone(channel.getJndiName()));
		clone.setTransferMode(channel.getTransferMode());
		clone.setAcknowledgeMode(channel.getAcknowledgeMode());
		clone.setRedeliveryDelay(ObjectUtil.clone(channel.getRedeliveryDelay()));
		clone.setMaxDeliveryAttempts(ObjectUtil.clone(channel.getMaxDeliveryAttempts()));
		clone.getClients().addAll(ObjectUtil.clone(channel.getClients(), String.class));
		clone.getServices().addAll(ObjectUtil.clone(channel.getServices(), String.class));
		clone.getManagers().addAll(ObjectUtil.clone(channel.getManagers(), String.class));
		return clone;
	}
	
	public static Channels newChannels() {
		Channels channels = new Channels();
		return channels;
	}
	
	public static boolean isChannelExists(List<Channel> channels, Channel channel) {
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel1 = iterator.next();
			if (equals(channel1, channel)) {
				return true;
			}
		}
		return false;
	}
	
	public static void addChannel(List<Channel> channels, Channel channel) {
		if (!isChannelExists(channels, channel)) {
			channels.add(channel);
		}
	}
	

	public static Sender getSenderByName(Channel channel, String name) {
		return getInteractorByName(getSenders(channel), name);
	}
	
	public static Sender getSenderByLinkAndRole(Channel channel, String link, String role) {
		return getInteractorByLinkAndRole(getSenders(channel), link, role);
	}
	
	public static Invoker getInvokerByName(Channel channel, String name) {
		return getInteractorByName(getInvokers(channel), name);
	}
	
	public static Receiver getReceiverByName(Channel channel, String name) {
		return getInteractorByName(getReceivers(channel), name);
	}
	
	public static Receiver getReceiverByLinkAndRole(Channel channel, String link, String role) {
		return getInteractorByLinkAndRole(getReceivers(channel), link, role);
	}
	
	public static void addSender(Channel channel, Sender sender) {
		addInteractor(channel, getSenders(channel), sender);
	}

	public static void addInvoker(Channel channel, Invoker invoker) {
		addInteractor(channel, getInvokers(channel), invoker);
	}

	public static void addReceiver(Channel channel, Receiver receiver) {
		addInteractor(channel, getReceivers(channel), receiver);
	}

	public static void addReceivers(Channel channel, List<Receiver> receivers) {
		addInteractors(channel, receivers);
	}

	public static List<Sender> getSenders(Channel channel) {
		return getObjectList(channel, Sender.class);
	}
	
	public static List<Invoker> getInvokers(Channel channel) {
		return getObjectList(channel, Invoker.class);
	}
	
	public static List<Receiver> getReceivers(Channel channel) {
		return getObjectList(channel, Receiver.class);
	}

	public static void populateSenders(Channel channel) {
		populateInteractors(channel, getSenders(channel));
	}

	public static void populateReceivers(Channel channel) {
		populateInteractors(channel, getReceivers(channel));
	}

	

	public static <T extends Interactor> boolean containsInteractor(List<T> interactors, Class<T> interactorType, String interactorName) {
		Iterator<T> iterator = interactors.iterator();
		while (iterator.hasNext()) {
			T interactor = iterator.next();
			if (!interactor.getClass().equals(interactorType))
				continue;
			if (!interactor.getName().equalsIgnoreCase(interactorName))
				continue;
			return true;
		}
		return false;
	}
	
	public static <T extends Interactor> boolean containsInteractor(List<T> interactors, T interactor) {
		Iterator<T> iterator = interactors.iterator();
		while (iterator.hasNext()) {
			T object = iterator.next();
			if (object.getName().equalsIgnoreCase(interactor.getName()))
				return true;
		}
		return false;
	}
	
	public static <T extends Interactor> void addInteractors(Channel channel, List<T> interactors) {
		Iterator<T> iterator = interactors.iterator();
		while (iterator.hasNext()) {
			T interactor = iterator.next();
			addInteractor(channel, interactors, interactor);
		}
	}
	
	public static <T extends Interactor> void addInteractor(Channel channel, List<T> interactors, T interactor) {
		if (!containsInteractor(interactors, interactor)) {
			interactor.setChannel(channel.getName());
			channel.getInteractors().add(interactor);
		}
	}
	
	public static <T extends Interactor> T getInteractorByName(List<T> interactors, String name) {
		Iterator<T> iterator = interactors.iterator();
		while (iterator.hasNext()) {
			T interactor = iterator.next();
			String interactorName = interactor.getName();
			if (interactorName.equalsIgnoreCase(name))
				return interactor;
		}
		return null;
	}

	public static <T extends Interactor> T getInteractorByLinkAndRole(List<T> interactors, String link, String role) {
		Iterator<T> iterator = interactors.iterator();
		while (iterator.hasNext()) {
			T interactor = iterator.next();
			String interactorLink = interactor.getLink();
			String interactorRole = interactor.getRole();
			if (interactorLink.equalsIgnoreCase(link) && interactorRole.equalsIgnoreCase(role))
				return interactor;
		}
		return null;
	}

	public static <T extends Interactor> void populateInteractors(Channel channel, List<T> interactors) {
		Iterator<T> iterator = interactors.iterator();
		while (iterator.hasNext()) {
			Interactor interactor = iterator.next();
			interactor.setChannel(channel.getName());
		}
	}
	
	public static <T> T getObjectByName(Channel channel, Class<?> objectClass, String name) {
		List<Object> objectList = getObjectList(channel, objectClass);
		return ListUtil.getObjectByValue(objectList, objectClass, "getName", name);
	}
	
	public static <T> List<T> getObjectList(Channel channel, Class<?> objectClass) {
		List<Serializable> objects = channel.getInteractors();
		return ListUtil.getObjectList(objects, objectClass);
	}
	
	public static <T> T getObject(Channel channel, Class<?> objectClass) {
		List<Serializable> objects = channel.getInteractors();
		return ListUtil.getObject(objects, objectClass);
	}
	

	public static Map<String, Channel> createChannelMap(Channels channels) {
		if (channels == null)
			return new HashMap<String, Channel>();
		return createChannelMap(channels.getChannels());
	}

	public static Map<String, Channel> createChannelMap(List<Channel> channels) {
		Map<String, Channel> map = new HashMap<String, Channel>();
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = (Channel) iterator.next();
			String name = channel.getName();
			if (name != null)
				map.put(name, channel);
		}
		return map;
	}

	public static boolean equals(Channel channel1, Channel channel2) {
		Assert.notNull(channel1, "First channel must be specified");
		Assert.notNull(channel2, "Second channel must be specified");
		if (!channel1.getName().equals(channel2.getName()))
			return false;
		return true;
	}

}
