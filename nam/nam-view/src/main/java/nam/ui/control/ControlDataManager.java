package nam.ui.control;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.Assert;

import nam.ui.Control;
import nam.ui.design.SelectionContext;
import nam.ui.util.ControlUtil;


@SessionScoped
@Named("controlDataManager")
public class ControlDataManager implements Serializable {
	
	@Inject
	private ControlEventManager controlEventManager;
	
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
	public Collection<Control> getControlList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Control> getDefaultList() {
		return null;
	}
	
	public void saveControl(Control control) {
		if (scope != null) {
		}
	}
	
	public boolean removeControl(Control control) {
		if (scope != null) {
		}
		return false;
	}
	
}
