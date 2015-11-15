package nam.model.timeout;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Timeout;
import nam.model.util.TimeoutUtil;
import nam.ui.design.SelectionContext;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractPageManager;
import org.aries.ui.AbstractWizardPage;
import org.aries.ui.Breadcrumb;
import org.aries.ui.PageManager;
import org.aries.util.NameUtil;


@SessionScoped
@Named("timeoutPageManager")
public class TimeoutPageManager extends AbstractPageManager<Timeout> implements Serializable {
	
	@Inject
	private TimeoutWizard timeoutWizard;
	
//	@Inject
//	private TimeoutRecord_IdentificationSection timeoutIdentificationSection;
//	
//	@Inject
//	private TimeoutRecord_ConfigurationSection timeoutConfigurationSection;
//	
//	@Inject
//	private TimeoutRecord_DocumentationSection timeoutDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public TimeoutPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public String getTimeoutListPage() {
		return "/nam/model/timeout/timeoutListPage.xhtml";
	}
	
	public String getTimeoutTreePage() {
		return "/nam/model/timeout/timeoutTreePage.xhtml";
	}
	
	public String getTimeoutSummaryPage() {
		return "/nam/model/timeout/timeoutSummaryPage.xhtml";
	}
	
	public String getTimeoutRecordPage() {
		return "/nam/model/timeout/timeoutRecordPage.xhtml";
	}
	
	public String getTimeoutWizardPage() {
		return "/nam/model/timeout/timeoutWizardPage.xhtml";
	}
	
	public String getTimeoutManagementPage() {
		return "/nam/model/timeout/timeoutManagementPage.xhtml";
	}
	
	public String initializeTimeoutListPage() {
		String pageLevelKey = "timeoutList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Timeouts", "showTimeoutManagementPage()");
		String url = getTimeoutListPage();
		selectionContext.setCurrentArea("timeout");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeTimeoutTreePage() {
		String pageLevelKey = "timeoutTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Timeouts", "showTimeoutTreePage()");
		String url = getTimeoutTreePage();
		selectionContext.setCurrentArea("timeout");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeTimeoutSummaryPage(Timeout timeout) {
		String pageLevelKey = "timeoutSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Timeouts", "showTimeoutSummaryPage()");
		String url = getTimeoutSummaryPage();
		selectionContext.setCurrentArea("timeout");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeTimeoutRecordPage() {
		Timeout timeout = selectionContext.getSelection("timeout");
		String timeoutName = timeout.getName();
		
		String pageLevelKey = "timeoutRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Timeouts", "showTimeoutManagementPage()");
		addBreadcrumb(pageLevelKey, timeoutName, "showTimeoutRecordPage()");
		String url = getTimeoutRecordPage();
		selectionContext.setCurrentArea("timeout");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeTimeoutCreationPage(Timeout timeout) {
		setPageTitle("New "+getTimeoutLabel(timeout));
		setSectionTitle("Timeout Identification");
		timeoutWizard.setNewMode(true);
		
		String pageLevelKey = "timeout";
		String wizardLevelKey = "timeoutWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Timeouts", "showTimeoutManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Timeout", "showTimeoutWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showTimeoutWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showTimeoutWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showTimeoutWizardPage('Documentation')");
		
//		timeoutIdentificationSection.setOwner("timeoutWizard");
//		timeoutConfigurationSection.setOwner("timeoutWizard");
//		timeoutDocumentationSection.setOwner("timeoutWizard");
		
		sections.clear();
//		sections.add(timeoutIdentificationSection);
//		sections.add(timeoutConfigurationSection);
//		sections.add(timeoutDocumentationSection);
		
		String url = getTimeoutWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("timeout");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		return url;
	}
	
	public String initializeTimeoutUpdatePage(Timeout timeout) {
		setPageTitle(getTimeoutLabel(timeout));
		setSectionTitle("Timeout Identification");
		String timeoutName = timeout.getName();
		timeoutWizard.setNewMode(false);
		
		String pageLevelKey = "timeout";
		String wizardLevelKey = "timeoutWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Timeouts", "showTimeoutManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(timeoutName, "showTimeoutWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showTimeoutWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showTimeoutWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showTimeoutWizardPage('Documentation')");
		
//		timeoutIdentificationSection.setOwner("timeoutWizard");
//		timeoutConfigurationSection.setOwner("timeoutWizard");
//		timeoutDocumentationSection.setOwner("timeoutWizard");
		
		sections.clear();
//		sections.add(timeoutIdentificationSection);
//		sections.add(timeoutConfigurationSection);
//		sections.add(timeoutDocumentationSection);
		
		String url = getTimeoutWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("timeout");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		return url;
	}
	
	public String initializeTimeoutManagementPage() {
		String pageLevelKey = "timeoutManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Timeouts", "showTimeoutManagementPage()");
		String url = getTimeoutManagementPage();
		selectionContext.setCurrentArea("timeout");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public void initializeDefaultView() {
		initializeView(this, "Timeouts", "Overview") ;
	}
	
	public void initializeView(String elementType, String viewTitle, String sectionName) {
		String pageManagerName = NameUtil.uncapName(elementType) + "PageManager";
		PageManager<?> pageManager = BeanContext.getFromSession(pageManagerName);
		initializeView(pageManager, viewTitle, sectionName);
	}
	
	public void initializeView(PageManager<?> pageManager, String viewTitle, String sectionName) {
		pageManager.setSectionName(sectionName);
		pageManager.setSectionTitle(viewTitle);
		pageManager.setSectionType("timeout");
		pageManager.setSectionIcon("/icons/nam/Timeout16.gif");
	}
	
	public String initializeTimeoutSummaryView(Timeout timeout) {
		String viewTitle = getTimeoutLabel(timeout);
		String currentArea = selectionContext.getCurrentArea();
		initializeView(currentArea, viewTitle, "Summary");
		String viewLevelKey = "timeoutSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Timeouts", "showTimeoutSummaryPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getTimeoutLabel(Timeout timeout) {
		String label = "Timeout";
		String name = TimeoutUtil.getLabel(timeout);
		if (name == null && timeout.getName() != null)
			name = NameUtil.capName(timeout.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}

	protected void updateState() {
		AbstractWizardPage<Timeout> page = timeoutWizard.getPage();
		if (page != null)
			setSectionTitle("Timeout " + page.getName());
	}

	protected void updateState(Timeout timeout) {
		String timeoutName = NameUtil.capName(timeout.getName());
		setSectionTitle(timeoutName + " Timeout");
	}
	
}
