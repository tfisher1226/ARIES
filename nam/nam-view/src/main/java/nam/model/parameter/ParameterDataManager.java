package nam.model.parameter;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Parameter;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Service;
import nam.model.Timeout;
import nam.model.util.OperationUtil;
import nam.model.util.ServiceUtil;
import nam.ui.design.SelectionContext;

import org.aries.Assert;


@SessionScoped
@Named("parameterDataManager")
public class ParameterDataManager implements Serializable {
	
	@Inject
	private ParameterEventManager parameterEventManager;
	
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
	
	public Collection<Parameter> getParameterList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("operation")) {
			return getParameterList((Operation) getOwner());
		//} else if (scope.equals("service")) {
		//	return getParameterList((Service) getOwner());
		} else {
			return getDefaultList();
		}
	}

	public Collection<Parameter> getParameterList(Operation operation) {
		Collection<Parameter> parameterList = OperationUtil.getParameters(operation);
		return parameterList;
	}
	
//	public Collection<Parameter> getParameterList(Service service) {
//		Collection<Parameter> parameterList = ServiceUtil.getParameters(service);
//		return parameterList;
//	}

	public Collection<Parameter> getDefaultList() {
		return null;
	}

	public void saveParameter(Parameter parameter) {
		if (scope != null) {
			Object owner = getOwner();
			if (scope.equals("operation")) {
				OperationUtil.addParameter((Operation) owner, parameter);
			//} else if (scope.equals("service")) {
			//	ServiceUtil.addParameter((Service) owner, parameter);
			}
		}
	}
	
	public boolean removeParameter(Parameter parameter) {
		if (scope != null) {
			Object owner = getOwner();
			if (scope.equals("operation")) {
				return OperationUtil.removeParameter((Operation) owner, parameter);
			//} else if (scope.equals("service")) {
			//	return ServiceUtil.removeParameter((Service) owner, parameter);
			}
		}
		return false;
	}

}
