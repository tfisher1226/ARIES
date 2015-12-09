package nam.model.namespace;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Namespace;
import nam.model.util.NamespaceUtil;


public class NamespaceListObject extends AbstractListObject<Namespace> implements Comparable<NamespaceListObject>, Serializable {
	
	private Namespace namespace;
	
	
	public NamespaceListObject(Namespace namespace) {
		this.namespace = namespace;
	}
	
	
	public Namespace getNamespace() {
		return namespace;
	}
	
	@Override
	public Object getKey() {
		return getKey(namespace);
	}
	
	public Object getKey(Namespace namespace) {
		return NamespaceUtil.getKey(namespace);
	}
	
	@Override
	public String getLabel() {
		return getLabel(namespace);
	}
	
	public String getLabel(Namespace namespace) {
		return NamespaceUtil.getLabel(namespace);
	}
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
	}
	
	@Override
	public String getIcon() {
		return "/icons/nam/Namespace16.gif";
	}
	
	@Override
	public String toString() {
		return toString(namespace);
	}
	
	@Override
	public String toString(Namespace namespace) {
		return NamespaceUtil.toString(namespace);
	}
	
	@Override
	public int compareTo(NamespaceListObject other) {
		Object thisKey = getKey(this.namespace);
		Object otherKey = getKey(other.namespace);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		NamespaceListObject other = (NamespaceListObject) object;
		Object thisKey = NamespaceUtil.getKey(this.namespace);
		Object otherKey = NamespaceUtil.getKey(other.namespace);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
