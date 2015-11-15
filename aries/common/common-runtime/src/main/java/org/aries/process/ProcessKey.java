package org.aries.process;

import java.io.Serializable;

import org.aries.Assert;


@SuppressWarnings("serial")
public class ProcessKey implements Serializable {

	private String name;
	
	private String version;

	private Object correlationId;
	
	
	public ProcessKey(String name, String version, Object correlationId) {
		Assert.notNull(name, "Name must be specified");
		Assert.notNull(version, "Version must be specified");
		Assert.notNull(correlationId, "CorrelationId must be specified");
		this.name = name;
		this.version = version;
		this.correlationId = correlationId;
	}
	
    public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public Object getCorrelationId() {
		return correlationId;
	}

	@Override
	public int hashCode() {
		return correlationId.hashCode();
	}

    @Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		ProcessKey other = (ProcessKey) object;
		if (!name.equals(other.name))
			return false;
		if (!version.equals(other.version))
			return false;
		if (!correlationId.equals(other.correlationId))
			return false;
		return true;
	}
	
    @Override
	public String toString() {
    	StringBuffer buf = new StringBuffer();
    	buf.append("/"+name);
    	buf.append("/"+version);
    	buf.append("/"+correlationId);
		return buf.toString();
	}

}
