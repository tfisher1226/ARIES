package nam.model.property;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Property;
import nam.model.util.PropertyUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("propertyDataManager")
public class PropertyDataManager implements Serializable {
	
	@Inject
	private PropertyEventManager propertyEventManager;
	
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
	
	public Collection<Property> getPropertyList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Property> getDefaultList() {
		return null;
	}

	public void saveProperty(Property property) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}

	public boolean removeProperty(Property property) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
