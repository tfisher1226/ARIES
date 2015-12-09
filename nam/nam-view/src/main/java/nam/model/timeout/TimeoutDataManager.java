package nam.model.timeout;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Timeout;
import nam.model.Operation;
import nam.model.Service;
import nam.model.Timeout;
import nam.model.util.OperationUtil;
import nam.model.util.ServiceUtil;
import nam.ui.design.SelectionContext;

import org.aries.Assert;


@SessionScoped
@Named("timeoutDataManager")
public class TimeoutDataManager implements Serializable {
	
	@Inject
	private TimeoutEventManager timeoutEventManager;
	
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
	
	public Collection<Timeout> getTimeoutList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("operation")) {
			return getTimeoutList((Operation) getOwner());
		//} else if (scope.equals("service")) {
		//	return getTimeoutList((Service) getOwner());
		}
		return null;
	}

	public Collection<Timeout> getTimeoutList(Operation operation) {
		Collection<Timeout> timeoutList = OperationUtil.getTimeouts(operation);
		return timeoutList;
	}

	public Collection<Timeout> getTimeoutList(Service service) {
		Collection<Timeout> timeoutList = ServiceUtil.getTimeouts(service);
		return timeoutList;
	}

	public void saveTimeout(Timeout timeout) {
		if (scope != null) {
			if (scope.equals("operation")) {
				OperationUtil.addTimeout((Operation) getOwner(), timeout);
			//} else if (scope.equals("service")) {
			//	ServiceUtil.addTimeout((Service) getOwner(), timeout);
			}
		}
	}
	
	public boolean removeTimeout(Timeout timeout) {
		if (scope != null) {
			if (scope.equals("operation")) {
				return OperationUtil.removeTimeout((Operation) getOwner(), timeout);
			//} else if (scope.equals("service")) {
			//	return ServiceUtil.removeTimeout((Service) getOwner(), timeout);
			}
		}
		return false;
	}
	
}
