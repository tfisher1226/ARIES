package nam.ui.invocation;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.Assert;

import nam.ui.Invocation;
import nam.ui.design.SelectionContext;
import nam.ui.util.InvocationUtil;


@SessionScoped
@Named("invocationDataManager")
public class InvocationDataManager implements Serializable {
	
	@Inject
	private InvocationEventManager invocationEventManager;
	
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
	public Collection<Invocation> getInvocationList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Invocation> getDefaultList() {
		return null;
	}
	
	public void saveInvocation(Invocation invocation) {
		if (scope != null) {
		}
	}
	
	public boolean removeInvocation(Invocation invocation) {
		if (scope != null) {
		}
		return false;
	}
	
}
