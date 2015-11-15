package nam.model.library;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Library;
import nam.model.util.LibraryUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("libraryDataManager")
public class LibraryDataManager implements Serializable {
	
	@Inject
	private LibraryEventManager libraryEventManager;
	
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
	
	public Collection<Library> getLibraryList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Library> getDefaultList() {
		return null;
	}
	
	public void saveLibrary(Library library) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeLibrary(Library library) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
