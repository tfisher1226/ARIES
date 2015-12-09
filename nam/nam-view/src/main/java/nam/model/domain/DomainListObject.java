package nam.model.domain;

import java.io.Serializable;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractListObject;

import nam.model.Domain;
import nam.model.util.DomainUtil;


public class DomainListObject extends AbstractListObject<Domain> implements Comparable<DomainListObject>, Serializable {
	
	private Domain domain;
	
	
	public DomainListObject(Domain domain) {
		this.domain = domain;
	}
	
	
	public Domain getDomain() {
		return domain;
	}
	
	@Override
	public Object getKey() {
		return getKey(domain);
	}
	
	public Object getKey(Domain domain) {
		return DomainUtil.getKey(domain);
	}
	
	@Override
	public String getLabel() {
		return getLabel(domain);
	}
	
	public String getLabel(Domain domain) {
		return DomainUtil.getLabel(domain);
	}
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
	}
	
	@Override
	public String getIcon() {
		return "/icons/nam/Domain16.gif";
	}
	
	@Override
	public String toString() {
		return toString(domain);
	}
	
	@Override
	public String toString(Domain domain) {
		return DomainUtil.toString(domain);
	}
	
	@Override
	public int compareTo(DomainListObject other) {
		Object thisKey = getKey(this.domain);
		Object otherKey = getKey(other.domain);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		DomainListObject other = (DomainListObject) object;
		Object thisKey = DomainUtil.getKey(this.domain);
		Object otherKey = DomainUtil.getKey(other.domain);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
