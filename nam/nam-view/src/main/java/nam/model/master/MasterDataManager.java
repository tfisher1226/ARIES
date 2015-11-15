package nam.model.master;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Master;
import nam.model.util.MasterUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("masterDataManager")
public class MasterDataManager implements Serializable {
	
	@Inject
	private MasterEventManager masterEventManager;
	
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
	
	public Collection<Master> getMasterList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Master> getDefaultList() {
		return null;
	}
	
	public void saveMaster(Master master) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeMaster(Master master) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
