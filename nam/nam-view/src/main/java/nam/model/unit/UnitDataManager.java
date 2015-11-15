package nam.model.unit;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Unit;
import nam.model.util.UnitUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("unitDataManager")
public class UnitDataManager implements Serializable {
	
	@Inject
	private UnitEventManager unitEventManager;
	
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
	
	public Collection<Unit> getUnitList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Unit> getDefaultList() {
		return null;
	}
	
	public void saveUnit(Unit unit) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeUnit(Unit unit) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
