package nam.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Channel;
import nam.model.Interactor;
import nam.model.Interactors;
import nam.model.Link;
import nam.model.ObjectFactory;
import nam.model.Project;
import nam.model.Receiver;
import nam.model.Role2;
import nam.model.Sender;
import nam.model.Transport;

import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;


public class LinkUtil {

	public static List<Role2> getRoles(Link link) {
		return getObjectList(link, Role2.class);
	}
	
	public static void addRole(Link link, Role2 role) {
		if (!link.getMembers().contains(role))
			link.getMembers().add(role);
	}

	public static void addRoles(Link link, List<Role2> roles) {
		Iterator<Role2> iterator = roles.iterator();
		while (iterator.hasNext()) {
			Role2 role = iterator.next();
			addRole(link, role);
		}
	}

	public static void addTransport(Link link, Transport transport) {
		if (!link.getMembers().contains(transport))
			link.getMembers().add(transport);
	}
	
	public static void removeTransport(Link link, Transport transport) {
		if (link.getMembers().contains(transport))
			link.getMembers().remove(transport);
	}
	
	public static List<Transport> getTransports(Link link) {
		return getObjectList(link, Transport.class);
	}
	
	public static <T> T getObjectByName(Link link, Class<?> objectClass, String name) {
		List<Object> objectList = getObjectList(link, objectClass);
		return ListUtil.getObjectByValue(objectList, objectClass, "getName", name);
	}
	
	public static <T> List<T> getObjectList(Link link, Class<?> objectClass) {
		List<Serializable> objects = link.getMembers();
		return ListUtil.getObjectList(objects, objectClass);
	}
	
	public static <T> T getObject(Link link, Class<?> objectClass) {
		List<Serializable> objects = link.getMembers();
		return ListUtil.getObject(objects, objectClass);
	}
	
	
	private static ObjectFactory objectFactory = new ObjectFactory();
	
	
	/**
	 * Return an <code>EList</code> containing all partnerLink elements defined
	 * on Scopes in this process.
	 * 
	 * @param process <code>EObject</code> representing the Process element
	 * @return <code>EList</code> containing all <code>PartnerLink</code>s 
	 * defined on any Scopes (or none)
	 */
	public static EList<PartnerLink> getScopePartnerLinks(final EObject process) {
		TreeIterator<EObject> contents = process.eAllContents();
		EList<PartnerLink> results = new BasicEList<PartnerLink>();
		
		while (contents.hasNext()) {
			EObject obj = contents.next();
			
			if (obj instanceof Scope) {
				results.addAll(((Scope) obj).getPartnerLinks().getChildren());
			}	
		}
		return results;
	}

	public static List<Link> createSendLinksFromInteractors(Interactors interactors) {
		return createLinksFromInteractors(interactors.getInteractors(), "Send");
	}

	public static List<Link> createReceiveLinksFromInteractors(Interactors interactors) {
		return createLinksFromInteractors(interactors.getInteractors(), "Receive");
	}

	public static List<Link> createSendLinksFromInteractors(List<? extends Interactor> interactors) {
		return createLinksFromInteractors(interactors, "Send");
	}

	public static List<Link> createReceiveLinksFromInteractors(List<? extends Interactor> interactors) {
		return createLinksFromInteractors(interactors, "Receive");
	}

	//TODO restrict to any specific set of Interactions?
	//Note: we are assuming for now: An Interactor's link is globally unique 
	public static List<Link> createLinksFromInteractors(List<? extends Interactor> interactors, String type) {
		Map<String, Link> linkMap = new HashMap<String, Link>();
		return createLinksFromInteractors(linkMap, interactors, type);
	}
	
	public static List<Link> createLinksFromInteractors(Map<String, Link> linkMap, List<? extends Interactor> interactors, String type) {
		Iterator<? extends Interactor> iterator = interactors.iterator();
		while (iterator.hasNext()) {
			Interactor interactor = iterator.next();
			
			String linkName = interactor.getLink();
			String channelName = interactor.getChannel();
			//TODO find a better place for this cache of project
			Project project = ProjectUtil.getProject();
			Channel channel = MessagingUtil.getChannelByName(project, channelName);
			
			if (StringUtils.isEmpty(linkName)) {
				if (type.equals("Send")) {
					System.out.println();
//					linkName = channel.getSendLink();
//					if (linkName == null) {
//						ChannelUtil.populateSenders(channel);
//						List<Sender> senders = ChannelUtil.getSenders(channel);
//						createLinksFromInteractors(linkMap, senders, type);
//					}
				}
				if (type.equals("Receive")) {
					System.out.println();
//					linkName = channel.getReceiveLink();
//					if (linkName == null) {
//						ChannelUtil.populateReceivers(channel);
//						List<Receiver> receivers = ChannelUtil.getReceivers(channel);
//						createLinksFromInteractors(linkMap, receivers, type);
//					}
				}
				
				
			} else {
				Assert.notNull(linkName, type+" link not found for: "+interactor.getName());
				if (!linkMap.containsKey(linkName)) {
					Link link = new Link();
					link.setName(linkName);
					
					if (type.equals("Send")) {
						List<Sender> senders = ChannelUtil.getSenders(channel);
						Iterator<Sender> senderIterator = senders.iterator();
						while (senderIterator.hasNext()) {
							Sender sender = senderIterator.next();
							String roleName = sender.getName();
							Role2 role = objectFactory.createRole2();
							role.setName(roleName);
							addRole(link, role);
						}
					}
	
					if (type.equals("Receive")) {
						List<Receiver> receivers = ChannelUtil.getReceivers(channel);
						Iterator<Receiver> receiverIterator = receivers.iterator();
						while (receiverIterator.hasNext()) {
							Receiver receiver = receiverIterator.next();
							String roleName = receiver.getName();
							Role2 role = objectFactory.createRole2();
							role.setName(roleName);
							addRole(link, role);
						}
					}
	
					
					//link.setRole(interactor.getRole());
					//link.setType(interactor.getType());
					linkMap.put(linkName, link);
				}
			}
		}
		List<Link> links = new ArrayList<Link>(linkMap.values());
		return links;
	}

	public static Set<Link> createLinks(List<PartnerLink> partnerLinks) {
		Set<Link> links = new HashSet<Link>();
		Iterator<PartnerLink> iterator = partnerLinks.iterator();
		while (iterator.hasNext()) {
			PartnerLink partnerLink = iterator.next();
			PartnerLinkType partnerLinkType = partnerLink.getPartnerLinkType();
			Link link = objectFactory.createLink();
			link.setName(partnerLink.getName());
			//link.setType(partnerLink.getPartnerLinkType().getName());
			List<Role2> roles = createRoles(partnerLinkType.getRole());
			addRoles(link, roles);
			links.add(link);
		}
		return links;
	}

	public static List<Role2> createRoles(List<org.eclipse.bpel.model.partnerlinktype.Role> partnerLinkTypeRoles) {
		Iterator<org.eclipse.bpel.model.partnerlinktype.Role> iterator = partnerLinkTypeRoles.iterator();
		List<Role2> roles = new ArrayList<Role2>();
		while (iterator.hasNext()) {
			org.eclipse.bpel.model.partnerlinktype.Role partnerLinkTypeRole = iterator.next();
			Role2 role2 = objectFactory.createRole2();
			role2.setName(partnerLinkTypeRole.getName());
			Object portType = partnerLinkTypeRole.getPortType();
			if (portType != null)
				role2.setPortType(portType.toString());
			roles.add(role2);
		}
		return roles;
	}

	public static void addAll(Map<String, Link> linkMap, Set<Link> links) {
		Iterator<Link> iterator = links.iterator();
		while (iterator.hasNext()) {
			Link link = iterator.next();
//			if (link == null)
//				System.out.println();
			linkMap.put(link.getName(), link);
		}
	}

}
