package nam.model.type;

import java.io.Serializable;

import nam.model.Element;
import nam.model.Enumeration;
import nam.model.Event;
import nam.model.Fault;
import nam.model.Type;
import nam.model.util.TypeUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractListObject;


public class TypeListObject extends AbstractListObject<Type> implements Comparable<TypeListObject>, Serializable {
	
	private Type type;
	
	
	public TypeListObject(Type type) {
		this.type = type;
	}
	
	
	public Type getType() {
		return type;
	}
	
	@Override
	public Object getKey() {
		return getKey(type);
	}
	
	public Object getKey(Type type) {
		return TypeUtil.getKey(type);
	}
	
	@Override
	public String getLabel() {
		return getLabel(type);
	}
	
	public String getLabel(Type type) {
		return TypeUtil.getLabel(type);
	}
	
	public String getPackageName() {
		return TypeUtil.getPackageName(type.getType());
	}
	
	public String getQualifiedName() {
		return TypeUtil.getPackageName(type.getType()) + "." + type.getName();
	}
	
	public String getIcon() {
		if (type instanceof Fault)
			return "/icons/nam/Fault16.gif";
		if (type instanceof Event)
			return "/icons/nam/Event16.gif";
		if (type instanceof Enumeration)
			return "/icons/nam/Enumeration16.gif";
		if (type instanceof Element)
			return "/icons/nam/Element16.gif";
		return "";
	}
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
		fireChangeEvent(type, checked);
	}
	
	protected static void fireChangeEvent(Type type, boolean checked) {
		TypeEventManager eventManager = BeanContext.getFromSession("typeEventManager");
		if (checked) {
			eventManager.fireSelectedEvent(type);
		} else {
			eventManager.fireUnselectedEvent(type);
		}
	}
	
	@Override
	public String toString() {
		return toString(type);
	}
	
	@Override
	public String toString(Type type) {
		return TypeUtil.toString(type);
	}
	
	@Override
	public int compareTo(TypeListObject other) {
		Object thisKey = getKey(this.type);
		Object otherKey = getKey(other.type);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		TypeListObject other = (TypeListObject) object;
		Object thisKey = TypeUtil.getKey(this.type);
		Object otherKey = TypeUtil.getKey(other.type);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
