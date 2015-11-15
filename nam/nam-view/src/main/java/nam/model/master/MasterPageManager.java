package nam.model.master;

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

import nam.model.Master;
import nam.model.util.MasterUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("masterPageManager")
public class MasterPageManager extends AbstractPageManager<Master> implements Serializable {
	
	@Inject
	private MasterWizard masterWizard;
	
	@Inject
	private MasterDataManager masterDataManager;
	
	@Inject
	private MasterListManager masterListManager;
	
	@Inject
	private MasterRecord_OverviewSection masterOverviewSection;
	
	@Inject
	private MasterRecord_IdentificationSection masterIdentificationSection;
	
	@Inject
	private MasterRecord_ConfigurationSection masterConfigurationSection;
	
	@Inject
	private MasterRecord_DocumentationSection masterDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public MasterPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("master");
	}
	
	public void refreshLocal() {
		refreshLocal("master");
	}
	
	public void refreshMembers() {
		refreshMembers("master");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		masterDataManager.setScope(scope);
		masterListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		masterListManager.refresh();
	}
	
	public String getMasterListPage() {
		return "/nam/model/master/masterListPage.xhtml";
	}
	
	public String getMasterTreePage() {
		return "/nam/model/master/masterTreePage.xhtml";
	}
	
	public String getMasterSummaryPage() {
		return "/nam/model/master/masterSummaryPage.xhtml";
	}
	
	public String getMasterRecordPage() {
		return "/nam/model/master/masterRecordPage.xhtml";
	}
	
	public String getMasterWizardPage() {
		return "/nam/model/master/masterWizardPage.xhtml";
	}
	
	public String getMasterManagementPage() {
		return "/nam/model/master/masterManagementPage.xhtml";
	}
	
	public String initializeMasterListPage() {
		String pageLevelKey = "masterList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Masters", "showMasterManagementPage()");
		String url = getMasterListPage();
		selectionContext.setCurrentArea("master");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeMasterTreePage() {
		String pageLevelKey = "masterTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Masters", "showMasterTreePage()");
		String url = getMasterTreePage();
		selectionContext.setCurrentArea("master");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeMasterSummaryPage(Master master) {
		String pageLevelKey = "masterSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Masters", "showMasterSummaryPage()");
		String url = getMasterSummaryPage();
		selectionContext.setCurrentArea("master");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeMasterRecordPage() {
		Master master = selectionContext.getSelection("master");
		String masterName = MasterUtil.getLabel(master);
		
		String pageLevelKey = "masterRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Masters", "showMasterManagementPage()");
		addBreadcrumb(pageLevelKey, masterName, "showMasterRecordPage()");
		String url = getMasterRecordPage();
		selectionContext.setCurrentArea("master");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeMasterCreationPage(Master master) {
		setPageTitle("New "+getMasterLabel(master));
		setPageIcon("/icons/nam/NewMaster16.gif");
		setSectionTitle("Master Identification");
		masterWizard.setNewMode(true);
		
		String pageLevelKey = "master";
		String wizardLevelKey = "masterWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Masters", "showMasterManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Master", "showMasterWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showMasterWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showMasterWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showMasterWizardPage('Documentation')");
		
		masterIdentificationSection.setOwner("masterWizard");
		masterConfigurationSection.setOwner("masterWizard");
		masterDocumentationSection.setOwner("masterWizard");
		
		sections.clear();
		sections.add(masterIdentificationSection);
		sections.add(masterConfigurationSection);
		sections.add(masterDocumentationSection);
		
		String url = getMasterWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("master");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeMasterUpdatePage(Master master) {
		setPageTitle(getMasterLabel(master));
		setPageIcon("/icons/nam/Master16.gif");
		setSectionTitle("Master Overview");
		String masterName = MasterUtil.getLabel(master);
		masterWizard.setNewMode(false);
		
		String pageLevelKey = "master";
		String wizardLevelKey = "masterWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Masters", "showMasterManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(masterName, "showMasterWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showMasterWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showMasterWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showMasterWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showMasterWizardPage('Documentation')");
		
		masterOverviewSection.setOwner("masterWizard");
		masterIdentificationSection.setOwner("masterWizard");
		masterConfigurationSection.setOwner("masterWizard");
		masterDocumentationSection.setOwner("masterWizard");
		
		sections.clear();
		sections.add(masterOverviewSection);
		sections.add(masterIdentificationSection);
		sections.add(masterConfigurationSection);
		sections.add(masterDocumentationSection);
		
		String url = getMasterWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("master");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeMasterManagementPage() {
		setPageTitle("Masters");
		setPageIcon("/icons/nam/Master16.gif");
		String pageLevelKey = "masterManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Masters", "showMasterManagementPage()");
		String url = getMasterManagementPage();
		selectionContext.setCurrentArea("master");
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
		setSectionType("master");
		setSectionName("Overview");
		setSectionTitle("Overview of Masters");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeMasterSummaryView(Master master) {
		//String viewTitle = getMasterLabel(master);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("master");
		setSectionName("Summary");
		setSectionTitle("Summary of Master Records");
		setSectionIcon("/icons/nam/Master16.gif");
		String viewLevelKey = "masterSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Masters", "showMasterManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getMasterLabel(Master master) {
		String label = "Master";
		String name = MasterUtil.getLabel(master);
		if (name == null && master.getName() != null)
			name = MasterUtil.getLabel(master);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Master> page = masterWizard.getPage();
		if (page != null)
			setSectionTitle("Master " + page.getName());
	}
	
	protected void updateState(Master master) {
		String masterName = MasterUtil.getLabel(master);
		setSectionTitle(masterName + " Master");
	}
	
}
