package nam.model.result;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Operation;
import nam.model.Result;
import nam.model.Service;
import nam.model.util.OperationUtil;
import nam.model.util.ServiceUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("resultDataManager")
public class ResultDataManager implements Serializable {
	
	@Inject
	private ResultEventManager resultEventManager;
	
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
	
	public Collection<Result> getResultList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("operation")) {
			return getResultList((Service) getOwner());
		//} else if (scope.equals("service")) {
		//	return getResultList((Service) getOwner());
		}
		return null;
	}
	
	public Collection<Result> getResultList(Operation operation) {
		Collection<Result> resultList = OperationUtil.getResults(operation);
		return resultList;
	}

	public Collection<Result> getResultList(Service service) {
		Collection<Result> resultList = ServiceUtil.getResults(service);
		return resultList;
	}

	public void saveResult(Result result) {
		if (scope != null) {
			if (scope.equals("operation")) {
				OperationUtil.addResult((Operation) getOwner(), result);
			//} else if (scope.equals("service")) {
			//	ServiceUtil.addResult((Service) getOwner(), result);
			}
		}
	}
	
	public boolean removeResult(Result result) {
		if (scope != null) {
			if (scope.equals("operation")) {
				return OperationUtil.removeResult((Operation) getOwner(), result);
			//} else if (scope.equals("service")) {
			//	return ServiceUtil.removeResult((Service) getOwner(), result);
			}
		}
		return false;
	}
	
}
