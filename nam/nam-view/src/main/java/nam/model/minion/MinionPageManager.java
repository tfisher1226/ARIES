package nam.model.minion;

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

import nam.model.Minion;
import nam.model.util.MinionUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("minionPageManager")
public class MinionPageManager extends AbstractPageManager<Minion> implements Serializable {
	
	@Inject
	private MinionWizard minionWizard;
	
	@Inject
	private MinionDataManager minionDataManager;
	
	@Inject
	private MinionListManager minionListManager;
	
	@Inject
	private MinionRecord_OverviewSection minionOverviewSection;
	
	@Inject
	private MinionRecord_IdentificationSection minionIdentificationSection;
	
	@Inject
	private MinionRecord_ConfigurationSection minionConfigurationSection;
	
	@Inject
	private MinionRecord_DocumentationSection minionDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public MinionPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("minion");
	}
	
	public void refreshLocal() {
		refreshLocal("minion");
	}
	
	public void refreshMembers() {
		refreshMembers("minion");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		minionDataManager.setScope(scope);
		minionListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		minionListManager.refresh();
	}
	
	public String getMinionListPage() {
		return "/nam/model/minion/minionListPage.xhtml";
	}
	
	public String getMinionTreePage() {
		return "/nam/model/minion/minionTreePage.xhtml";
	}
	
	public String getMinionSummaryPage() {
		return "/nam/model/minion/minionSummaryPage.xhtml";
	}
	
	public String getMinionRecordPage() {
		return "/nam/model/minion/minionRecordPage.xhtml";
	}
	
	public String getMinionWizardPage() {
		return "/nam/model/minion/minionWizardPage.xhtml";
	}
	
	public String getMinionManagementPage() {
		return "/nam/model/minion/minionManagementPage.xhtml";
	}
	
	public String initializeMinionListPage() {
		String pageLevelKey = "minionList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Minions", "showMinionManagementPage()");
		String url = getMinionListPage();
		selectionContext.setCurrentArea("minion");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeMinionTreePage() {
		String pageLevelKey = "minionTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Minions", "showMinionTreePage()");
		String url = getMinionTreePage();
		selectionContext.setCurrentArea("minion");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeMinionSummaryPage(Minion minion) {
		String pageLevelKey = "minionSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Minions", "showMinionSummaryPage()");
		String url = getMinionSummaryPage();
		selectionContext.setCurrentArea("minion");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeMinionRecordPage() {
		Minion minion = selectionContext.getSelection("minion");
		String minionName = MinionUtil.getLabel(minion);
		
		String pageLevelKey = "minionRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Minions", "showMinionManagementPage()");
		addBreadcrumb(pageLevelKey, minionName, "showMinionRecordPage()");
		String url = getMinionRecordPage();
		selectionContext.setCurrentArea("minion");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeMinionCreationPage(Minion minion) {
		setPageTitle("New "+getMinionLabel(minion));
		setPageIcon("/icons/nam/NewMinion16.gif");
		setSectionTitle("Minion Identification");
		minionWizard.setNewMode(true);
		
		String pageLevelKey = "minion";
		String wizardLevelKey = "minionWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Minions", "showMinionManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Minion", "showMinionWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showMinionWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showMinionWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showMinionWizardPage('Documentation')");
		
		minionIdentificationSection.setOwner("minionWizard");
		minionConfigurationSection.setOwner("minionWizard");
		minionDocumentationSection.setOwner("minionWizard");
		
		sections.clear();
		sections.add(minionIdentificationSection);
		sections.add(minionConfigurationSection);
		sections.add(minionDocumentationSection);
		
		String url = getMinionWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("minion");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeMinionUpdatePage(Minion minion) {
		setPageTitle(getMinionLabel(minion));
		setPageIcon("/icons/nam/Minion16.gif");
		setSectionTitle("Minion Overview");
		String minionName = MinionUtil.getLabel(minion);
		minionWizard.setNewMode(false);
		
		String pageLevelKey = "minion";
		String wizardLevelKey = "minionWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Minions", "showMinionManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(minionName, "showMinionWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showMinionWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showMinionWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showMinionWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showMinionWizardPage('Documentation')");
		
		minionOverviewSection.setOwner("minionWizard");
		minionIdentificationSection.setOwner("minionWizard");
		minionConfigurationSection.setOwner("minionWizard");
		minionDocumentationSection.setOwner("minionWizard");
		
		sections.clear();
		sections.add(minionOverviewSection);
		sections.add(minionIdentificationSection);
		sections.add(minionConfigurationSection);
		sections.add(minionDocumentationSection);
		
		String url = getMinionWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("minion");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeMinionManagementPage() {
		setPageTitle("Minions");
		setPageIcon("/icons/nam/Minion16.gif");
		String pageLevelKey = "minionManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Minions", "showMinionManagementPage()");
		String url = getMinionManagementPage();
		selectionContext.setCurrentArea("minion");
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
		setSectionType("minion");
		setSectionName("Overview");
		setSectionTitle("Overview of Minions");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeMinionSummaryView(Minion minion) {
		//String viewTitle = getMinionLabel(minion);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("minion");
		setSectionName("Summary");
		setSectionTitle("Summary of Minion Records");
		setSectionIcon("/icons/nam/Minion16.gif");
		String viewLevelKey = "minionSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Minions", "showMinionManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getMinionLabel(Minion minion) {
		String label = "Minion";
		String name = MinionUtil.getLabel(minion);
		if (name == null && minion.getName() != null)
			name = MinionUtil.getLabel(minion);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Minion> page = minionWizard.getPage();
		if (page != null)
			setSectionTitle("Minion " + page.getName());
	}
	
	protected void updateState(Minion minion) {
		String minionName = MinionUtil.getLabel(minion);
		setSectionTitle(minionName + " Minion");
	}
	
}
