package nam.model.fault;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Fault;
import nam.model.Operation;
import nam.model.util.OperationUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("faultDataManager")
public class FaultDataManager implements Serializable {
	
	@Inject
	private FaultEventManager faultEventManager;
	
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
	
	public Collection<Fault> getFaultList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("operation")) {
			return getFaultList((Operation) getOwner());
		} else {
			return getDefaultList();
		}
	}

	public Collection<Fault> getFaultList(Operation operation) {
		Collection<Fault> faultList = OperationUtil.getFaults(operation);
		return faultList;
	}

	public Collection<Fault> getDefaultList() {
		return null;
	}

	public void saveFault(Fault fault) {
		if (scope != null) {
			if (scope.equals("operation")) {
				OperationUtil.addFault((Operation) getOwner(), fault);
			}
		}
	}
	
	public boolean removeFault(Fault fault) {
		if (scope != null) {
			if (scope.equals("operation")) {
				return OperationUtil.removeFault((Operation) getOwner(), fault);
			}
		}
		return false;
	}
	
}
