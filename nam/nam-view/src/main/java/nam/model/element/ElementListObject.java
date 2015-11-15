package nam.model.element;

import java.io.Serializable;

import nam.model.Element;
import nam.model.Type;
import nam.model.type.TypeEventManager;
import nam.model.util.ElementUtil;
import nam.model.util.TypeUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractListObject;


public class ElementListObject extends AbstractListObject<Element> implements Comparable<ElementListObject>, Serializable {
	
	private Element element;

	
	public ElementListObject(Element element) {
		this.element = element;
	}

	
	public Element getElement() {
		return element;
	}

	@Override
	public Object getKey() {
		return getKey(element);
	}
	
	public Object getKey(Element element) {
		return ElementUtil.getKey(element);
	}
	
	@Override
	public String getLabel() {
		return getLabel(element);
	}
	
	public String getLabel(Element element) {
		return ElementUtil.getLabel(element);
	}
	
	public String getPackageName() {
		return TypeUtil.getPackageName(element.getType());
	}
	
	public String getQualifiedName() {
		return TypeUtil.getPackageName(element.getType()) + "." + element.getName();
	}
	
	public String getIcon() {
		return "/icons/nam/Element16.gif";
	}
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
		fireChangeEvent(element, checked);
	}
	
	protected static void fireChangeEvent(Element element, boolean checked) {
		ElementEventManager eventManager = BeanContext.getFromSession("elementEventManager");
		if (checked) {
			eventManager.fireSelectedEvent(element);
		} else {
			eventManager.fireUnselectedEvent(element);
		}
	}
	
	@Override
	public String toString() {
		return toString(element);
	}
	
	@Override
	public String toString(Element element) {
		return ElementUtil.toString(element);
	}

	@Override
	public int compareTo(ElementListObject other) {
		Object thisKey = getKey(this.element);
		Object otherKey = getKey(other.element);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ElementListObject other = (ElementListObject) object;
		Object thisKey = ElementUtil.getKey(this.element);
		Object otherKey = ElementUtil.getKey(other.element);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}

}
