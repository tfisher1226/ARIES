package nam.model.container;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Container;
import nam.model.util.ContainerUtil;


public class ContainerListObject extends AbstractListObject<Container> implements Comparable<ContainerListObject>, Serializable {
	
	private Container container;
	
	
	public ContainerListObject(Container container) {
		this.container = container;
	}
	
	
	public Container getContainer() {
		return container;
	}
	
	@Override
	public Object getKey() {
		return getKey(container);
	}
	
	public Object getKey(Container container) {
		return ContainerUtil.getKey(container);
	}
	
	@Override
	public String getLabel() {
		return getLabel(container);
	}
	
	public String getLabel(Container container) {
		return ContainerUtil.getLabel(container);
	}
	
	@Override
	public String toString() {
		return toString(container);
	}
	
	@Override
	public String toString(Container container) {
		return ContainerUtil.toString(container);
	}
	
	@Override
	public int compareTo(ContainerListObject other) {
		Object thisKey = getKey(this.container);
		Object otherKey = getKey(other.container);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ContainerListObject other = (ContainerListObject) object;
		Object thisKey = ContainerUtil.getKey(this.container);
		Object otherKey = ContainerUtil.getKey(other.container);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
