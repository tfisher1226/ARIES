package nam.model.library;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractPageManager;
import org.aries.ui.AbstractWizardPage;
import org.aries.ui.Breadcrumb;
import org.aries.util.NameUtil;

import nam.model.Library;
import nam.model.util.LibraryUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("libraryPageManager")
public class LibraryPageManager extends AbstractPageManager<Library> implements Serializable {
	
	@Inject
	private LibraryWizard libraryWizard;
	
	@Inject
	private LibraryDataManager libraryDataManager;
	
	@Inject
	private LibraryListManager libraryListManager;
	
	@Inject
	private LibraryRecord_OverviewSection libraryOverviewSection;
	
	@Inject
	private LibraryRecord_IdentificationSection libraryIdentificationSection;
	
	@Inject
	private LibraryRecord_ConfigurationSection libraryConfigurationSection;
	
	@Inject
	private LibraryRecord_DocumentationSection libraryDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public LibraryPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("library");
	}
	
	public void refreshLocal() {
		refreshLocal("library");
	}
	
	public void refreshMembers() {
		refreshMembers("library");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		libraryDataManager.setScope(scope);
		libraryListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		libraryListManager.refresh();
	}
	
	public String getLibraryListPage() {
		return "/nam/model/library/libraryListPage.xhtml";
	}
	
	public String getLibraryTreePage() {
		return "/nam/model/library/libraryTreePage.xhtml";
	}
	
	public String getLibrarySummaryPage() {
		return "/nam/model/library/librarySummaryPage.xhtml";
	}
	
	public String getLibraryRecordPage() {
		return "/nam/model/library/libraryRecordPage.xhtml";
	}
	
	public String getLibraryWizardPage() {
		return "/nam/model/library/libraryWizardPage.xhtml";
	}
	
	public String getLibraryManagementPage() {
		return "/nam/model/library/libraryManagementPage.xhtml";
	}
	
	public String initializeLibraryListPage() {
		String pageLevelKey = "libraryList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Librarys", "showLibraryManagementPage()");
		String url = getLibraryListPage();
		selectionContext.setCurrentArea("library");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeLibraryTreePage() {
		String pageLevelKey = "libraryTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Librarys", "showLibraryTreePage()");
		String url = getLibraryTreePage();
		selectionContext.setCurrentArea("library");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeLibrarySummaryPage(Library library) {
		String pageLevelKey = "librarySummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Librarys", "showLibrarySummaryPage()");
		String url = getLibrarySummaryPage();
		selectionContext.setCurrentArea("library");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeLibraryRecordPage() {
		Library library = selectionContext.getSelection("library");
		String libraryName = LibraryUtil.getLabel(library);
		
		String pageLevelKey = "libraryRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Librarys", "showLibraryManagementPage()");
		addBreadcrumb(pageLevelKey, libraryName, "showLibraryRecordPage()");
		String url = getLibraryRecordPage();
		selectionContext.setCurrentArea("library");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeLibraryCreationPage(Library library) {
		setPageTitle("New "+getLibraryLabel(library));
		setPageIcon("/icons/nam/NewLibrary16.gif");
		setSectionTitle("Library Identification");
		libraryWizard.setNewMode(true);
		
		String pageLevelKey = "library";
		String wizardLevelKey = "libraryWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Librarys", "showLibraryManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Library", "showLibraryWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showLibraryWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showLibraryWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showLibraryWizardPage('Documentation')");
		
		libraryIdentificationSection.setOwner("libraryWizard");
		libraryConfigurationSection.setOwner("libraryWizard");
		libraryDocumentationSection.setOwner("libraryWizard");
		
		sections.clear();
		sections.add(libraryIdentificationSection);
		sections.add(libraryConfigurationSection);
		sections.add(libraryDocumentationSection);
		
		String url = getLibraryWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("library");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeLibraryUpdatePage(Library library) {
		setPageTitle(getLibraryLabel(library));
		setPageIcon("/icons/nam/Library16.gif");
		setSectionTitle("Library Overview");
		String libraryName = LibraryUtil.getLabel(library);
		libraryWizard.setNewMode(false);
		
		String pageLevelKey = "library";
		String wizardLevelKey = "libraryWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Librarys", "showLibraryManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(libraryName, "showLibraryWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showLibraryWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showLibraryWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showLibraryWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showLibraryWizardPage('Documentation')");
		
		libraryOverviewSection.setOwner("libraryWizard");
		libraryIdentificationSection.setOwner("libraryWizard");
		libraryConfigurationSection.setOwner("libraryWizard");
		libraryDocumentationSection.setOwner("libraryWizard");
		
		sections.clear();
		sections.add(libraryOverviewSection);
		sections.add(libraryIdentificationSection);
		sections.add(libraryConfigurationSection);
		sections.add(libraryDocumentationSection);
		
		String url = getLibraryWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("library");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeLibraryManagementPage() {
		setPageTitle("Librarys");
		setPageIcon("/icons/nam/Library16.gif");
		String pageLevelKey = "libraryManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Librarys", "showLibraryManagementPage()");
		String url = getLibraryManagementPage();
		selectionContext.setCurrentArea("library");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		refresh();
		return url;
	}
	
	public void initializeDefaultView() {
		setSectionType("library");
		setSectionName("Overview");
		setSectionTitle("Overview of Librarys");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeLibrarySummaryView(Library library) {
		//String viewTitle = getLibraryLabel(library);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("library");
		setSectionName("Summary");
		setSectionTitle("Summary of Library Records");
		setSectionIcon("/icons/nam/Library16.gif");
		String viewLevelKey = "librarySummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Librarys", "showLibraryManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getLibraryLabel(Library library) {
		String label = "Library";
		String name = LibraryUtil.getLabel(library);
		if (name == null && library.getName() != null)
			name = LibraryUtil.getLabel(library);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Library> page = libraryWizard.getPage();
		if (page != null)
			setSectionTitle("Library " + page.getName());
	}
	
	protected void updateState(Library library) {
		String libraryName = LibraryUtil.getLabel(library);
		setSectionTitle(libraryName + " Library");
	}
	
}
