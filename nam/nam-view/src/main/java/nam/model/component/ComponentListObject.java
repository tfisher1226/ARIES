package nam.model.component;

import java.io.Serializable;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractListObject;

import nam.model.Component;
import nam.model.util.ComponentUtil;


public class ComponentListObject extends AbstractListObject<Component> implements Comparable<ComponentListObject>, Serializable {
	
	private Component component;
	
	
	public ComponentListObject(Component component) {
		this.component = component;
	}
	
	
	public Component getComponent() {
		return component;
	}
	
	@Override
	public Object getKey() {
		return getKey(component);
	}
	
	public Object getKey(Component component) {
		return ComponentUtil.getKey(component);
	}
	
	@Override
	public String getLabel() {
		return getLabel(component);
	}
	
	public String getLabel(Component component) {
		return ComponentUtil.getLabel(component);
	}

	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
	}
	
	@Override
	public String getIcon() {
		String type = component.getType();
		if (type == null)
			return "/icons/common/Default16.gif";
		if (type.equals("cache"))
			return "/icons/nam/Cache16.gif";
		else if (type.equals("persistenceUnit"))
			return "/icons/nam/PersistenceUnit16.gif";
		else return "/icons/nam/Component16.gif";
	}

	@Override
	public String toString() {
		return toString(component);
	}
	
	@Override
	public String toString(Component component) {
		return ComponentUtil.toString(component);
	}
	
	@Override
	public int compareTo(ComponentListObject other) {
		Object thisKey = getKey(this.component);
		Object otherKey = getKey(other.component);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ComponentListObject other = (ComponentListObject) object;
		Object thisKey = ComponentUtil.getKey(this.component);
		Object otherKey = ComponentUtil.getKey(other.component);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
