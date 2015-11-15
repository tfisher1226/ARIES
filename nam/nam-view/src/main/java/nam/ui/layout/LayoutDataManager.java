package nam.ui.layout;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.Assert;

import nam.ui.Layout;
import nam.ui.design.SelectionContext;
import nam.ui.util.LayoutUtil;


@SessionScoped
@Named("layoutDataManager")
public class LayoutDataManager implements Serializable {
	
	@Inject
	private LayoutEventManager layoutEventManager;
	
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
	public Collection<Layout> getLayoutList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Layout> getDefaultList() {
		return null;
	}
	
	public void saveLayout(Layout layout) {
		if (scope != null) {
		}
	}
	
	public boolean removeLayout(Layout layout) {
		if (scope != null) {
		}
		return false;
	}
	
}
