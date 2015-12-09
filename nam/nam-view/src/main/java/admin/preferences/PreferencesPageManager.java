package admin.preferences;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractPageManager;
import org.aries.ui.AbstractWizardPage;
import org.aries.ui.Breadcrumb;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Unselected;

import admin.Preferences;
import admin.util.PreferencesUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("preferencesPageManager")
public class PreferencesPageManager extends AbstractPageManager<Preferences> implements Serializable {
	
	@Inject
	private PreferencesWizard preferencesWizard;
	
	@Inject
	private PreferencesDataManager preferencesDataManager;
	
	@Inject
	private PreferencesInfoManager preferencesInfoManager;
	
	@Inject
	private PreferencesListManager preferencesListManager;
	
	@Inject
	private PreferencesRecord_OverviewSection preferencesOverviewSection;
	
	@Inject
	private PreferencesRecord_IdentificationSection preferencesIdentificationSection;
	
	@Inject
	private PreferencesRecord_ConfigurationSection preferencesConfigurationSection;
	
	@Inject
	private PreferencesRecord_DocumentationSection preferencesDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	

	public PreferencesPageManager() {
		initializeSections();
	}
	

	public void refresh() {
		refresh("projectList");
	}
	
	public void refreshLocal() {
		refreshLocal("preferences");
	}
	
	public void refreshMembers() {
		refreshMembers("preferences");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		//refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		preferencesDataManager.setScope(scope);
		preferencesListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		//nothing for now
	}
	
	public String getPreferencesListPage() {
		return "/admin/preferences/preferencesListPage.xhtml";
	}
	
	public String getPreferencesTreePage() {
		return "/admin/preferences/preferencesTreePage.xhtml";
	}

	public String getPreferencesSummaryPage() {
		return "/admin/preferences/preferencesSummaryPage.xhtml";
	}
	
	public String getPreferencesRecordPage() {
		return "/admin/preferences/preferencesRecordPage.xhtml";
	}

	public String getPreferencesWizardPage() {
		return "/admin/preferences/preferencesWizardPage.xhtml";
	}

	public String getPreferencesManagementPage() {
		return "/admin/preferences/preferencesManagementPage.xhtml";
	}
	
	public void handlePreferencesSelected(@Observes @Selected Preferences preferences) {
		selectionContext.setSelection("preferences",  preferences);
		preferencesInfoManager.setRecord(preferences);
	}
	
	public void handlePreferencesUnselected(@Observes @Unselected Preferences preferences) {
		selectionContext.unsetSelection("preferences",  preferences);
		preferencesInfoManager.unsetRecord(preferences);
	}
	
	public void handlePreferencesChecked() {
		String scope = "preferencesSelection";
		PreferencesListObject listObject = preferencesListManager.getSelection();
		Preferences preferences = selectionContext.getSelection("preferences");
		boolean checked = preferencesListManager.getCheckedState();
		listObject.setChecked(checked);
		if (checked) {
			preferencesInfoManager.setRecord(preferences);
			selectionContext.setSelection(scope,  preferences);
		} else {
			preferencesInfoManager.unsetRecord(preferences);
			selectionContext.unsetSelection(scope,  preferences);
		}
		refreshLocal(scope);
	}
	
	public String initializePreferencesListPage() {
		String pageLevelKey = "preferencesList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Preferencess", "showPreferencesManagementPage()");
		String url = getPreferencesListPage();
		selectionContext.setCurrentArea("preferences");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePreferencesTreePage() {
		String pageLevelKey = "preferencesTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Preferencess", "showPreferencesTreePage()");
		String url = getPreferencesTreePage();
		selectionContext.setCurrentArea("preferences");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePreferencesSummaryPage(Preferences preferences) {
		String pageLevelKey = "preferencesSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Preferencess", "showPreferencesSummaryPage()");
		String url = getPreferencesSummaryPage();
		selectionContext.setCurrentArea("preferences");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePreferencesRecordPage() {
		Preferences preferences = selectionContext.getSelection("preferences");
		String preferencesName = PreferencesUtil.getLabel(preferences);
		
		String pageLevelKey = "preferencesRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Preferencess", "showPreferencesManagementPage()");
		addBreadcrumb(pageLevelKey, preferencesName, "showPreferencesRecordPage()");
		String url = getPreferencesRecordPage();
		selectionContext.setCurrentArea("preferences");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializePreferencesCreationPage(Preferences preferences) {
		setPageTitle("New "+getPreferencesLabel(preferences));
		setPageIcon("/icons/nam/NewPreferences16.gif");
		setSectionTitle("Preferences Identification");
		preferencesWizard.setNewMode(true);
		
		String pageLevelKey = "preferences";
		String wizardLevelKey = "preferencesWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Preferencess", "showPreferencesManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Preferences", "showPreferencesWizardPage()"));
		

		preferencesIdentificationSection.setOwner("preferencesWizard");
		preferencesConfigurationSection.setOwner("preferencesWizard");
		preferencesDocumentationSection.setOwner("preferencesWizard");
		
		sections.clear();
		sections.add(preferencesIdentificationSection);
		sections.add(preferencesConfigurationSection);
		sections.add(preferencesDocumentationSection);
		
		String url = getPreferencesWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("preferences");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializePreferencesUpdatePage(Preferences preferences) {
		setPageTitle(getPreferencesLabel(preferences));
		setPageIcon("/icons/nam/Preferences16.gif");
		setSectionTitle("Preferences Overview");
		String preferencesName = PreferencesUtil.getLabel(preferences);
		preferencesWizard.setNewMode(false);
		
		String pageLevelKey = "preferences";
		String wizardLevelKey = "preferencesWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Preferencess", "showPreferencesManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(preferencesName, "showPreferencesWizardPage()"));
		
		
		preferencesOverviewSection.setOwner("preferencesWizard");
		preferencesIdentificationSection.setOwner("preferencesWizard");
		preferencesConfigurationSection.setOwner("preferencesWizard");
		preferencesDocumentationSection.setOwner("preferencesWizard");

		sections.clear();
		sections.add(preferencesOverviewSection);
		sections.add(preferencesIdentificationSection);
		sections.add(preferencesConfigurationSection);
		sections.add(preferencesDocumentationSection);
		
		String url = getPreferencesWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("preferences");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}

	public String initializePreferencesManagementPage() {
		setPageTitle("Preferenceses");
		setPageIcon("/icons/nam/Preferences16.gif");
		String pageLevelKey = "preferencesManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Preferenceses", "showPreferencesManagementPage()");
		String url = getPreferencesManagementPage();
		selectionContext.setCurrentArea("preferences");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}

	public void initializeDefaultView() {
		setPageTitle("Preferenceses");
		setPageIcon("/icons/nam/Preferences16.gif");
		setSectionType("preferences");
		setSectionName("Overview");
		setSectionTitle("Overview of Preferenceses");
		setSectionIcon("/icons/nam/Overview16.gif");
		String viewLevelKey = "preferencesOverview";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Preferenceses", "showPreferencesManagementPage()");
		String scope = "projectList";
		refreshLocal(scope);
		sections.clear();
	}
	
	public String initializePreferencesSummaryView(Preferences preferences) {
		//String viewTitle = getPreferencesLabel(preferences);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("preferences");
		setSectionName("Summary");
		setSectionTitle("Summary of Preferences Records");
		setSectionIcon("/icons/nam/Preferences16.gif");
		String viewLevelKey = "preferencesSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Preferencess", "showPreferencesManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getPreferencesLabel(Preferences preferences) {
		String label = "Preferences";
		String name = PreferencesUtil.getLabel(preferences);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Preferences> page = preferencesWizard.getPage();
		if (page != null)
			setSectionTitle("Preferences " + page.getName());
	}
	
	protected void updateState(Preferences preferences) {
		String preferencesName = PreferencesUtil.getLabel(preferences);
		setSectionTitle(preferencesName + " Preferences");
	}

}
