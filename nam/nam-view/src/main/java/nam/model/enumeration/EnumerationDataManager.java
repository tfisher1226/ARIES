package nam.model.enumeration;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Enumeration;
import nam.model.util.EnumerationUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("enumerationDataManager")
public class EnumerationDataManager implements Serializable {
	
	@Inject
	private EnumerationEventManager enumerationEventManager;
	
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
	
	public Collection<Enumeration> getEnumerationList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Enumeration> getDefaultList() {
		return null;
	}
	
	public void saveEnumeration(Enumeration enumeration) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeEnumeration(Enumeration enumeration) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
