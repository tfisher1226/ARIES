package nam.model._import;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Import;
import nam.model.util.ImportUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("importDataManager")
public class ImportDataManager implements Serializable {
	
	@Inject
	private ImportEventManager importEventManager;
	
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
	
	public Collection<Import> getImportList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Import> getDefaultList() {
		return null;
	}
	
	public void saveImport(Import _import) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeImport(Import _import) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
