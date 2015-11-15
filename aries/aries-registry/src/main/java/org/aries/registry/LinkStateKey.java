package org.aries.registry;

import java.io.Serializable;

import org.aries.Assert;


@SuppressWarnings("serial")
public class LinkStateKey implements Serializable {

	private String name;
	
	private String type;
	
	
	public LinkStateKey(String name, String type) {
		Assert.notNull(name, "Name must be specified");
		Assert.notNull(type, "Type must be specified");
		this.name = name;
		this.type = type;
	}
	
    public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

    @Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		LinkStateKey other = (LinkStateKey) object;
		if (!name.equals(other.name))
			return false;
		if (!type.equals(other.type))
			return false;
		return true;
	}
	
    @Override
	public String toString() {
    	StringBuffer buf = new StringBuffer();
    	buf.append("/"+name);
    	buf.append("/"+type);
		return buf.toString();
	}

}
