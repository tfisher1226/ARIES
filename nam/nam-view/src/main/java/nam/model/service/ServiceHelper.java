package nam.model.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Listener;
import nam.model.Service;
import nam.model.listener.ListenerListManager;
import nam.model.listener.ListenerListObject;
import nam.model.util.ServiceUtil;


@SessionScoped
@Named("serviceHelper")
public class ServiceHelper extends AbstractElementHelper<Service> implements Serializable {
	
	@Override
	public boolean isEmpty(Service service) {
		return ServiceUtil.isEmpty(service);
	}
	
	@Override
	public String toString(Service service) {
		return ServiceUtil.toString(service);
	}
	
	@Override
	public String toString(Collection<Service> serviceList) {
		return ServiceUtil.toString(serviceList);
	}
	
	@Override
	public boolean validate(Service service) {
		return ServiceUtil.validate(service);
	}
	
	@Override
	public boolean validate(Collection<Service> serviceList) {
		return ServiceUtil.validate(serviceList);
	}
	
	public DataModel<ListenerListObject> getListeners(Service service) {
		if (service == null)
			return null;
		List<Listener> listeners = ServiceUtil.getListeners(service);
		return getListeners(listeners);
	}
	
	public DataModel<ListenerListObject> getListeners(Collection<Listener> listenersList) {
		ListenerListManager listenerListManager = BeanContext.getFromSession("listenerListManager");
		DataModel<ListenerListObject> dataModel = listenerListManager.getDataModel(listenersList);
		return dataModel;
	}
	
}
