package nam.model.query;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Query;
import nam.model.util.QueryUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("queryDataManager")
public class QueryDataManager implements Serializable {
	
	@Inject
	private QueryEventManager queryEventManager;
	
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
	
	public Collection<Query> getQueryList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Query> getDefaultList() {
		return null;
	}
	
	public void saveQuery(Query query) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeQuery(Query query) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
