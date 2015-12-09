package nam.model.service;

import java.io.Serializable;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractListObject;

import nam.model.Service;
import nam.model.util.ServiceUtil;


public class ServiceListObject extends AbstractListObject<Service> implements Comparable<ServiceListObject>, Serializable {
	
	private Service service;
	
	
	public ServiceListObject(Service service) {
		this.service = service;
	}
	
	
	public Service getService() {
		return service;
	}
	
	@Override
	public Object getKey() {
		return getKey(service);
	}
	
	public Object getKey(Service service) {
		return ServiceUtil.getKey(service);
	}
	
	@Override
	public String getLabel() {
		return getLabel(service);
	}
	
	public String getLabel(Service service) {
		return ServiceUtil.getLabel(service);
	}
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
	}
	
	@Override
	public String getIcon() {
		return "/icons/nam/Service16.gif";
	}
	
	@Override
	public String toString() {
		return toString(service);
	}
	
	@Override
	public String toString(Service service) {
		return ServiceUtil.toString(service);
	}
	
	@Override
	public int compareTo(ServiceListObject other) {
		Object thisKey = getKey(this.service);
		Object otherKey = getKey(other.service);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ServiceListObject other = (ServiceListObject) object;
		Object thisKey = ServiceUtil.getKey(this.service);
		Object otherKey = ServiceUtil.getKey(other.service);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
