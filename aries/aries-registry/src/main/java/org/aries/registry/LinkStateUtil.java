package org.aries.registry;

import nam.model.Link;


public class LinkStateUtil {

	public static LinkStateKey createLinkStateKey(LinkState linkState) {
		Link link = linkState.getLink();
		LinkStateKey key = createLinkStateKey(link.getName(), link.getType());
		return key;
	}

	public static LinkStateKey createLinkStateKey(String name, String type) {
		LinkStateKey key = new LinkStateKey(name, type);
		return key;
	}
	
}
