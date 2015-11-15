package nam.model.process;

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

import nam.model.Process;
import nam.model.util.ProcessUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("processPageManager")
public class ProcessPageManager extends AbstractPageManager<Process> implements Serializable {
	
	@Inject
	private ProcessWizard processWizard;
	
	@Inject
	private ProcessDataManager processDataManager;
	
	@Inject
	private ProcessListManager processListManager;
	
	@Inject
	private ProcessRecord_OverviewSection processOverviewSection;
	
	@Inject
	private ProcessRecord_IdentificationSection processIdentificationSection;
	
	@Inject
	private ProcessRecord_ConfigurationSection processConfigurationSection;
	
	@Inject
	private ProcessRecord_DocumentationSection processDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ProcessPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("process");
	}
	
	public void refreshLocal() {
		refreshLocal("process");
	}
	
	public void refreshMembers() {
		refreshMembers("process");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		processDataManager.setScope(scope);
		processListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		processListManager.refresh();
	}
	
	public String getProcessListPage() {
		return "/nam/model/process/processListPage.xhtml";
	}
	
	public String getProcessTreePage() {
		return "/nam/model/process/processTreePage.xhtml";
	}
	
	public String getProcessSummaryPage() {
		return "/nam/model/process/processSummaryPage.xhtml";
	}
	
	public String getProcessRecordPage() {
		return "/nam/model/process/processRecordPage.xhtml";
	}
	
	public String getProcessWizardPage() {
		return "/nam/model/process/processWizardPage.xhtml";
	}
	
	public String getProcessManagementPage() {
		return "/nam/model/process/processManagementPage.xhtml";
	}
	
	public String initializeProcessListPage() {
		String pageLevelKey = "processList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Processs", "showProcessManagementPage()");
		String url = getProcessListPage();
		selectionContext.setCurrentArea("process");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeProcessTreePage() {
		String pageLevelKey = "processTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Processs", "showProcessTreePage()");
		String url = getProcessTreePage();
		selectionContext.setCurrentArea("process");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeProcessSummaryPage(Process process) {
		String pageLevelKey = "processSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Processs", "showProcessSummaryPage()");
		String url = getProcessSummaryPage();
		selectionContext.setCurrentArea("process");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeProcessRecordPage() {
		Process process = selectionContext.getSelection("process");
		String processName = process.getName();
		
		String pageLevelKey = "processRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Processs", "showProcessManagementPage()");
		addBreadcrumb(pageLevelKey, processName, "showProcessRecordPage()");
		String url = getProcessRecordPage();
		selectionContext.setCurrentArea("process");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeProcessCreationPage(Process process) {
		setPageTitle("New "+getProcessLabel(process));
		setPageIcon("/icons/nam/NewProcess16.gif");
		setSectionTitle("Process Identification");
		processWizard.setNewMode(true);
		
		String pageLevelKey = "process";
		String wizardLevelKey = "processWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Processs", "showProcessManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Process", "showProcessWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showProcessWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showProcessWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showProcessWizardPage('Documentation')");
		
		processIdentificationSection.setOwner("processWizard");
		processConfigurationSection.setOwner("processWizard");
		processDocumentationSection.setOwner("processWizard");
		
		sections.clear();
		sections.add(processIdentificationSection);
		sections.add(processConfigurationSection);
		sections.add(processDocumentationSection);
		
		String url = getProcessWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("process");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeProcessUpdatePage(Process process) {
		setPageTitle(getProcessLabel(process));
		setPageIcon("/icons/nam/Process16.gif");
		setSectionTitle("Process Overview");
		String processName = process.getName();
		processWizard.setNewMode(false);
		
		String pageLevelKey = "process";
		String wizardLevelKey = "processWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Processs", "showProcessManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(processName, "showProcessWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showProcessWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showProcessWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showProcessWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showProcessWizardPage('Documentation')");
		
		processOverviewSection.setOwner("processWizard");
		processIdentificationSection.setOwner("processWizard");
		processConfigurationSection.setOwner("processWizard");
		processDocumentationSection.setOwner("processWizard");
		
		sections.clear();
		sections.add(processOverviewSection);
		sections.add(processIdentificationSection);
		sections.add(processConfigurationSection);
		sections.add(processDocumentationSection);
		
		String url = getProcessWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("process");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeProcessManagementPage() {
		setPageTitle("Processes");
		setPageIcon("/icons/nam/Process16.gif");
		String pageLevelKey = "processManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Processes", "showProcessManagementPage()");
		String url = getProcessManagementPage();
		selectionContext.setCurrentArea("process");
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
		setSectionType("process");
		setSectionName("Overview");
		setSectionTitle("Overview of Processes");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeProcessSummaryView(Process process) {
		//String viewTitle = getProcessLabel(process);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("process");
		setSectionName("Summary");
		setSectionTitle("Summary of Process Records");
		setSectionIcon("/icons/nam/Process16.gif");
		String viewLevelKey = "processSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Processs", "showProcessManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getProcessLabel(Process process) {
		String label = "Process";
		String name = ProcessUtil.getLabel(process);
		if (name == null && process.getName() != null)
			name = NameUtil.capName(process.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Process> page = processWizard.getPage();
		if (page != null)
			setSectionTitle("Process " + page.getName());
	}
	
	protected void updateState(Process process) {
		String processName = NameUtil.capName(process.getName());
		setSectionTitle(processName + " Process");
	}
	
}
