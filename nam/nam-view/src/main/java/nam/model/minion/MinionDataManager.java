package nam.model.minion;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Minion;
import nam.model.util.MinionUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("minionDataManager")
public class MinionDataManager implements Serializable {
	
	@Inject
	private MinionEventManager minionEventManager;
	
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
	
	public Collection<Minion> getMinionList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Minion> getDefaultList() {
		return null;
	}
	
	public void saveMinion(Minion minion) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeMinion(Minion minion) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
