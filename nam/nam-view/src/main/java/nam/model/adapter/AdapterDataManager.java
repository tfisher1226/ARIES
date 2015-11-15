package nam.model.adapter;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Adapter;
import nam.model.util.AdapterUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("adapterDataManager")
public class AdapterDataManager implements Serializable {
	
	@Inject
	private AdapterEventManager adapterEventManager;
	
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
	
	public Collection<Adapter> getAdapterList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Adapter> getDefaultList() {
		return null;
	}
	
	public void saveAdapter(Adapter adapter) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeAdapter(Adapter adapter) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
