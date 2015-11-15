package nam.model.variable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.Assert;

import nam.model.Variable;
import nam.model.util.VariableUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("variableDataManager")
public class VariableDataManager implements Serializable {
	
	@Inject
	private VariableEventManager variableEventManager;
	
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
	
	public Collection<Variable> getVariableList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Variable> getDefaultList() {
		return null;
	}
	
	public void saveVariable(Variable variable) {
		if (scope != null) {
		}
	}
	
	public boolean removeVariable(Variable variable) {
		if (scope != null) {
		}
		return false;
	}
	
}
