package nam.model.timeout;

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

import nam.model.Timeout;
import nam.model.util.TimeoutUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("timeoutPageManager")
public class TimeoutPageManager extends AbstractPageManager<Timeout> implements Serializable {
	
	@Inject
	private TimeoutWizard timeoutWizard;
	
	@Inject
	private TimeoutDataManager timeoutDataManager;
	
	@Inject
	private TimeoutInfoManager timeoutInfoManager;
	
	@Inject
	private TimeoutListManager timeoutListManager;
	
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
	}
	
	
	public void refresh() {
		refresh("timeout");
	}
	
	public void refreshLocal() {
		refreshLocal("timeout");
	}
	
	public void refreshMembers() {
		refreshMembers("timeout");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		//refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		timeoutDataManager.setScope(scope);
		timeoutListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		//nothing for now
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
	
	public void handleTimeoutSelected(@Observes @Selected Timeout timeout) {
		selectionContext.setSelection("timeout",  timeout);
		timeoutInfoManager.setRecord(timeout);
	}
	
	public void handleTimeoutUnselected(@Observes @Unselected Timeout timeout) {
		selectionContext.unsetSelection("timeout",  timeout);
		timeoutInfoManager.unsetRecord(timeout);
	}
	
	public void handleTimeoutChecked() {
		String scope = "timeoutSelection";
		TimeoutListObject listObject = timeoutListManager.getSelection();
		Timeout timeout = selectionContext.getSelection("timeout");
		boolean checked = timeoutListManager.getCheckedState();
		listObject.setChecked(checked);
		if (checked) {
			timeoutInfoManager.setRecord(timeout);
			selectionContext.setSelection(scope,  timeout);
		} else {
			timeoutInfoManager.unsetRecord(timeout);
			selectionContext.unsetSelection(scope,  timeout);
		}
		refreshLocal(scope);
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
		String timeoutName = TimeoutUtil.getLabel(timeout);
		
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
		setPageIcon("/icons/nam/NewTimeout16.gif");
		setSectionTitle("Timeout Identification");
		timeoutWizard.setNewMode(true);
		
		String pageLevelKey = "timeout";
		String wizardLevelKey = "timeoutWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Timeouts", "showTimeoutManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Timeout", "showTimeoutWizardPage()"));
			
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
		refreshLocal();
		return url;
	}
	
	public String initializeTimeoutUpdatePage(Timeout timeout) {
		setPageTitle(getTimeoutLabel(timeout));
		setPageIcon("/icons/nam/Timeout16.gif");
		setSectionTitle("Timeout Overview");
		String timeoutName = TimeoutUtil.getLabel(timeout);
		timeoutWizard.setNewMode(false);
		
		String pageLevelKey = "timeout";
		String wizardLevelKey = "timeoutWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Timeouts", "showTimeoutManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(timeoutName, "showTimeoutWizardPage()"));
		
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
		refreshLocal();
		return url;
	}
	
	public String initializeTimeoutManagementPage() {
		setPageTitle("Timeouts");
		setPageIcon("/icons/nam/Timeout16.gif");
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
		setPageTitle("Timeouts");
		setPageIcon("/icons/nam/Timeout16.gif");
		setSectionType("timeout");
		setSectionName("Overview");
		setSectionTitle("Overview of Timeouts");
		setSectionIcon("/icons/nam/Overview16.gif");
		String viewLevelKey = "timeoutOverview";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Timeouts", "showTimeoutManagementPage()");
		String scope = "projectList";
		refreshLocal(scope);
		sections.clear();
	}
	
	public String initializeTimeoutSummaryView(Timeout timeout) {
		//String viewTitle = getTimeoutLabel(timeout);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("timeout");
		setSectionName("Summary");
		setSectionTitle("Summary of Timeout Records");
		setSectionIcon("/icons/nam/Timeout16.gif");
		String viewLevelKey = "timeoutSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Timeouts", "showTimeoutManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getTimeoutLabel(Timeout timeout) {
		String label = "Timeout";
		String name = TimeoutUtil.getLabel(timeout);
		if (name == null && timeout.getName() != null)
			name = TimeoutUtil.getLabel(timeout);
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
		String timeoutName = TimeoutUtil.getLabel(timeout);
		setSectionTitle(timeoutName + " Timeout");
	}
	
}
