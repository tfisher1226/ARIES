package nam.model.source;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Source;
import nam.model.util.SourceUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("sourceDataManager")
public class SourceDataManager implements Serializable {
	
	@Inject
	private SourceEventManager sourceEventManager;
	
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
	
	public Collection<Source> getSourceList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Source> getDefaultList() {
		return null;
	}
	
	public void saveSource(Source source) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeSource(Source source) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
