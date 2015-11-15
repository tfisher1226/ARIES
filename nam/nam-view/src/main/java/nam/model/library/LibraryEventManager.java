package nam.model.library;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Library;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("libraryEventManager")
public class LibraryEventManager extends AbstractEventManager<Library> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Library getInstance() {
		return selectionContext.getSelection("library");
	}
	
	public void removeLibrary() {
		Library library = getInstance();
		fireRemoveEvent(library);
	}
	
}
