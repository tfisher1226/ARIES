package nam.model.library;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.Library;
import nam.model.Project;
import nam.model.util.LibraryUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("libraryInfoManager")
public class LibraryInfoManager extends AbstractNamRecordManager<Library> implements Serializable {
	
	@Inject
	private LibraryWizard libraryWizard;
	
	@Inject
	private LibraryDataManager libraryDataManager;
	
	@Inject
	private LibraryPageManager libraryPageManager;
	
	@Inject
	private LibraryEventManager libraryEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private LibraryHelper libraryHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public LibraryInfoManager() {
		setInstanceName("library");
	}
	
	
	public Library getLibrary() {
		return getRecord();
	}
	
	public Library getSelectedLibrary() {
		return selectionContext.getSelection("library");
	}
	
	@Override
	public Class<Library> getRecordClass() {
		return Library.class;
	}
	
	@Override
	public boolean isEmpty(Library library) {
		return libraryHelper.isEmpty(library);
	}
	
	@Override
	public String toString(Library library) {
		return libraryHelper.toString(library);
	}
	
	@Override
	public void initialize() {
		Library library = selectionContext.getSelection("library");
		if (library != null)
			initialize(library);
	}
	
	protected void initialize(Library library) {
		LibraryUtil.initialize(library);
		libraryWizard.initialize(library);
		setContext("library", library);
	}
	
	public void handleLibrarySelected(@Observes @Selected Library library) {
		selectionContext.setSelection("library",  library);
		libraryPageManager.updateState(library);
		libraryPageManager.refreshMembers();
		setRecord(library);
	}
	
	@Override
	public String newRecord() {
		return newLibrary();
	}
	
	public String newLibrary() {
		try {
			Library library = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("library",  library);
			String url = libraryPageManager.initializeLibraryCreationPage(library);
			libraryPageManager.pushContext(libraryWizard);
			initialize(library);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Library create() {
		Library library = LibraryUtil.create();
		return library;
	}
	
	@Override
	public Library clone(Library library) {
		library = LibraryUtil.clone(library);
		return library;
	}
	
	@Override
	public String viewRecord() {
		return viewLibrary();
	}
	
	public String viewLibrary() {
		Library library = selectionContext.getSelection("library");
		String url = viewLibrary(library);
		return url;
	}
	
	public String viewLibrary(Library library) {
		try {
			String url = libraryPageManager.initializeLibrarySummaryView(library);
			libraryPageManager.pushContext(libraryWizard);
			initialize(library);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editLibrary();
	}
	
	public String editLibrary() {
		Library library = selectionContext.getSelection("library");
		String url = editLibrary(library);
		return url;
	}
	
	public String editLibrary(Library library) {
		try {
			//library = clone(library);
			selectionContext.resetOrigin();
			selectionContext.setSelection("library",  library);
			String url = libraryPageManager.initializeLibraryUpdatePage(library);
			libraryPageManager.pushContext(libraryWizard);
			initialize(library);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveLibrary() {
		Library library = getLibrary();
		if (validateLibrary(library)) {
			if (isImmediate())
				persistLibrary(library);
			outject("library", library);
		}
	}
	
	public void persistLibrary(Library library) {
		saveLibrary(library);
	}
	
	public void saveLibrary(Library library) {
		try {
			saveLibraryToSystem(library);
			libraryEventManager.fireAddedEvent(library);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveLibraryToSystem(Library library) {
		libraryDataManager.saveLibrary(library);
	}
	
	public void handleSaveLibrary(@Observes @Add Library library) {
		saveLibrary(library);
	}
	
	public void addLibrary(Library library) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichLibrary(Library library) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Library library) {
		return validateLibrary(library);
	}
	
	public boolean validateLibrary(Library library) {
		Validator validator = getValidator();
		boolean isValid = LibraryUtil.validate(library);
		Display display = getFromSession("display");
		display.setModule("libraryInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveLibrary() {
		display = getFromSession("display");
		display.setModule("libraryInfo");
		Library library = selectionContext.getSelection("library");
		if (library == null) {
			display.error("Library record must be selected.");
		}
	}
	
	public String handleRemoveLibrary(@Observes @Remove Library library) {
		display = getFromSession("display");
		display.setModule("libraryInfo");
		try {
			display.info("Removing Library "+LibraryUtil.getLabel(library)+" from the system.");
			removeLibraryFromSystem(library);
			selectionContext.clearSelection("library");
			libraryEventManager.fireClearSelectionEvent();
			libraryEventManager.fireRemovedEvent(library);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeLibraryFromSystem(Library library) {
		if (libraryDataManager.removeLibrary(library))
			setRecord(null);
	}
	
	public void cancelLibrary() {
		BeanContext.removeFromSession("library");
		libraryPageManager.removeContext(libraryWizard);
	}
	
}
