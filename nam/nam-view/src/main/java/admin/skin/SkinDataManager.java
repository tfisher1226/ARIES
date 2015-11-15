package admin.skin;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import admin.Skin;
import admin.util.SkinUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("skinDataManager")
public class SkinDataManager implements Serializable {
	
	@Inject
	private SkinEventManager skinEventManager;
	
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
	
	public Collection<Skin> getSkinList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Skin> getDefaultList() {
		return null;
	}
	
	public void saveSkin(Skin skin) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeSkin(Skin skin) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
