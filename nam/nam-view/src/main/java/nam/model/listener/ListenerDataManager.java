package nam.model.listener;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Listener;
import nam.model.Messaging;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.MessagingUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("listenerDataManager")
public class ListenerDataManager implements Serializable {
	
	@Inject
	private ListenerEventManager listenerEventManager;
	
	@Inject
	private SelectionContext selectionContext;

	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	public Collection<Listener> getListenerList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("service")) {
			return getListenerList((Service) owner);
		} else if (scope.equals("projectList")) {
			return getListenerList((Collection<Project>) getOwner());
		} else {
			return getDefaultList();
		}
	}
	
	public Collection<Listener> getListenerList(Service service) {
		List<Listener> listenerList = ServiceUtil.getListeners(service);
		return listenerList;
	}

	public Collection<Listener> getListenerList(Collection<Project> projectList) {
		List<Messaging> messagingBlocks = ProjectUtil.getMessagingBlocks(projectList);
		List<Listener> listenerList = MessagingUtil.getListeners(messagingBlocks);
		return listenerList;
	}


//	public Collection<Listener> getListenerList(String selector) {
//		Service service = selectionContext.getSelection("service");
//		return getListenerList(service);
//	}

	public Collection<Listener> getDefaultList() {
		return null;
	}

	public void saveListener(Listener listener) {
		if (scope != null) {
			if (scope.equals("service")) {
				ServiceUtil.addListener((Service) getOwner(), listener);
			}
		}
	}
	
	public boolean removeListener(Listener listener) {
		if (scope != null) {
			if (scope.equals("service"))
				return ServiceUtil.removeListener((Service) getOwner(), listener);
		}
		return false;
	}
	
}
