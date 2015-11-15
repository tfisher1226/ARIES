package nam.model.library;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Library;
import nam.model.Project;
import nam.model.util.LibraryUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("libraryWizard")
@SuppressWarnings("serial")
public class LibraryWizard extends AbstractDomainElementWizard<Library> implements Serializable {
	
	@Inject
	private LibraryDataManager libraryDataManager;
	
	@Inject
	private LibraryPageManager libraryPageManager;
	
	@Inject
	private LibraryEventManager libraryEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Library";
	}
	
	@Override
	public String getUrlContext() {
		return libraryPageManager.getLibraryWizardPage();
	}
	
	@Override
	public void initialize(Library library) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(libraryPageManager.getSections());
		super.initialize(library);
	}
	
	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}
	
	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}
	
	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		libraryPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		libraryPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		libraryPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		libraryPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Library library = getInstance();
		libraryDataManager.saveLibrary(library);
		libraryEventManager.fireSavedEvent(library);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Library library = getInstance();
		//TODO take this out soon
		if (library == null)
			library = new Library();
		libraryEventManager.fireCancelledEvent(library);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Library library = selectionContext.getSelection("library");
		String name = library.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("libraryWizard");
			display.error("Library name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
