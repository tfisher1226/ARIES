package org.aries.registry;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import nam.model.Link;
import nam.model.Role;
import nam.model.Transport;


public class LinkState implements Serializable {

	private final static long serialVersionUID = 1L;

	protected Link link;

	protected Role role;

	protected List<Transport> transportSelectionPriority = new LinkedList();


	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Transport getDefaultTransport() {
		return transportSelectionPriority.get(0);
	}

	public List<Transport> getTransportSelectionPriority() {
		return transportSelectionPriority;
	}

	public void setTransportSelectionPriority(List<Transport> transportSelectionPriority) {
		this.transportSelectionPriority = transportSelectionPriority;
	}

	public String getLinkId() {
		return link.getName()+"/"+role.getName();
		//return link.getType()+"/"+link.getName()+"/"+role.getName();
	}

}
