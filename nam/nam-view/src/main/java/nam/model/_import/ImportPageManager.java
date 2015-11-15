package nam.model._import;

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

import nam.model.Import;
import nam.model.util.ImportUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("importPageManager")
public class ImportPageManager extends AbstractPageManager<Import> implements Serializable {
	
	@Inject
	private ImportWizard importWizard;
	
	@Inject
	private ImportDataManager importDataManager;
	
	@Inject
	private ImportListManager importListManager;
	
	@Inject
	private ImportRecord_OverviewSection importOverviewSection;
	
	@Inject
	private ImportRecord_IdentificationSection importIdentificationSection;
	
	@Inject
	private ImportRecord_ConfigurationSection importConfigurationSection;
	
	@Inject
	private ImportRecord_DocumentationSection importDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ImportPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("import");
	}
	
	public void refreshLocal() {
		refreshLocal("import");
	}
	
	public void refreshMembers() {
		refreshMembers("import");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		importDataManager.setScope(scope);
		importListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		importListManager.refresh();
	}
	
	public String getImportListPage() {
		return "/nam/model/_import/importListPage.xhtml";
	}
	
	public String getImportTreePage() {
		return "/nam/model/_import/importTreePage.xhtml";
	}
	
	public String getImportSummaryPage() {
		return "/nam/model/_import/importSummaryPage.xhtml";
	}
	
	public String getImportRecordPage() {
		return "/nam/model/_import/importRecordPage.xhtml";
	}
	
	public String getImportWizardPage() {
		return "/nam/model/_import/importWizardPage.xhtml";
	}
	
	public String getImportManagementPage() {
		return "/nam/model/_import/importManagementPage.xhtml";
	}
	
	public String initializeImportListPage() {
		String pageLevelKey = "importList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Imports", "showImportManagementPage()");
		String url = getImportListPage();
		selectionContext.setCurrentArea("import");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeImportTreePage() {
		String pageLevelKey = "importTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Imports", "showImportTreePage()");
		String url = getImportTreePage();
		selectionContext.setCurrentArea("import");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeImportSummaryPage(Import _import) {
		String pageLevelKey = "importSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Imports", "showImportSummaryPage()");
		String url = getImportSummaryPage();
		selectionContext.setCurrentArea("import");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeImportRecordPage() {
		Import _import = selectionContext.getSelection("import");
		String importName = ImportUtil.getLabel(_import);
		
		String pageLevelKey = "importRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Imports", "showImportManagementPage()");
		addBreadcrumb(pageLevelKey, importName, "showImportRecordPage()");
		String url = getImportRecordPage();
		selectionContext.setCurrentArea("import");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeImportCreationPage(Import _import) {
		setPageTitle("New "+getImportLabel(_import));
		setPageIcon("/icons/nam/NewImport16.gif");
		setSectionTitle("Import Identification");
		importWizard.setNewMode(true);
		
		String pageLevelKey = "import";
		String wizardLevelKey = "importWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Imports", "showImportManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Import", "showImportWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showImportWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showImportWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showImportWizardPage('Documentation')");
		
		importIdentificationSection.setOwner("importWizard");
		importConfigurationSection.setOwner("importWizard");
		importDocumentationSection.setOwner("importWizard");
		
		sections.clear();
		sections.add(importIdentificationSection);
		sections.add(importConfigurationSection);
		sections.add(importDocumentationSection);
		
		String url = getImportWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("import");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeImportUpdatePage(Import _import) {
		setPageTitle(getImportLabel(_import));
		setPageIcon("/icons/nam/Import16.gif");
		setSectionTitle("Import Overview");
		String importName = ImportUtil.getLabel(_import);
		importWizard.setNewMode(false);
		
		String pageLevelKey = "import";
		String wizardLevelKey = "importWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Imports", "showImportManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(importName, "showImportWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showImportWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showImportWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showImportWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showImportWizardPage('Documentation')");
		
		importOverviewSection.setOwner("importWizard");
		importIdentificationSection.setOwner("importWizard");
		importConfigurationSection.setOwner("importWizard");
		importDocumentationSection.setOwner("importWizard");
		
		sections.clear();
		sections.add(importOverviewSection);
		sections.add(importIdentificationSection);
		sections.add(importConfigurationSection);
		sections.add(importDocumentationSection);
		
		String url = getImportWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("import");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeImportManagementPage() {
		setPageTitle("Imports");
		setPageIcon("/icons/nam/Import16.gif");
		String pageLevelKey = "importManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Imports", "showImportManagementPage()");
		String url = getImportManagementPage();
		selectionContext.setCurrentArea("import");
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
		setSectionType("_import");
		setSectionName("Overview");
		setSectionTitle("Overview of Imports");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeImportSummaryView(Import _import) {
		//String viewTitle = getImportLabel(import);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("_import");
		setSectionName("Summary");
		setSectionTitle("Summary of Import Records");
		setSectionIcon("/icons/nam/Import16.gif");
		String viewLevelKey = "importSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Imports", "showImportManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getImportLabel(Import _import) {
		String label = "Import";
		String name = ImportUtil.getLabel(_import);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Import> page = importWizard.getPage();
		if (page != null)
			setSectionTitle("Import " + page.getName());
	}
	
	protected void updateState(Import _import) {
		String importName = ImportUtil.getLabel(_import);
		setSectionTitle(importName + " Import");
	}
	
}
