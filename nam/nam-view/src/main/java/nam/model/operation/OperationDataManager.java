package nam.model.operation;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.Assert;

import nam.model.Component;
import nam.model.Module;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Service;
import nam.model.Timeout;
import nam.model.util.ModuleUtil;
import nam.model.util.OperationUtil;
import nam.model.util.ServiceUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("operationDataManager")
public class OperationDataManager implements Serializable {
	
	@Inject
	private OperationEventManager operationEventManager;
	
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
	
	@SuppressWarnings("unchecked")
	public Collection<Operation> getOperationList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("service")) {
			return getOperationList((Service) owner);
		} else if (scope.equals("component")) {
			return getOperationList((Service) owner);
		} else {
			return getDefaultList();
		}
	}
	
	public Collection<Operation> getOperationList(Service service) {
		List<Operation> operationList = ServiceUtil.getOperations(service);
		return operationList;
	}
		
	public Collection<Operation> getDefaultList() {
		return null;
	}

	public void saveOperation(Operation operation) {
		if (scope != null) {
			if (scope.equals("service")) {
				ServiceUtil.addOperation((Service) getOwner(), operation);
			}
		}
	}
	
	public boolean removeOperation(Operation operation) {
		if (scope != null) {
			if (scope.equals("service"))
				return ServiceUtil.removeOperation((Service) getOwner(), operation);
		}
		return false;
	}
	
}
