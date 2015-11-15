package nam.model.operation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Operation;
import nam.model.Service;
import nam.model.util.OperationUtil;
import nam.ui.design.SelectionContext;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractPageManager;
import org.aries.ui.AbstractWizardPage;
import org.aries.ui.Breadcrumb;
import org.aries.ui.PageManager;
import org.aries.util.NameUtil;


@SessionScoped
@Named("operationPageManager")
public class OperationPageManager extends AbstractPageManager<Operation> implements Serializable {
	
	@Inject
	private OperationWizard operationWizard;
	
	@Inject
	private OperationDataManager operationDataManager;

	@Inject
	private OperationListManager operationListManager;
	
	@Inject
	private OperationRecord_OverviewSection operationOverviewSection;
	
	@Inject
	private OperationRecord_IdentificationSection operationIdentificationSection;
	
	@Inject
	private OperationRecord_ConfigurationSection operationConfigurationSection;
	
	@Inject
	private OperationRecord_DocumentationSection operationDocumentationSection;

	@Inject
	private OperationRecord_ParametersSection operationParametersSection;

	@Inject
	private OperationRecord_CommandsSection operationCommandsSection;

	@Inject
	private OperationRecord_ResultsSection operationResultsSection;

	@Inject
	private OperationRecord_TimeoutsSection operationTimeoutsSection;

	@Inject
	private OperationRecord_FaultsSection operationFaultsSection;

	@Inject
	private SelectionContext selectionContext;
	
	
	public OperationPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("operation");
	}
	
	public void refreshLocal() {
		refreshLocal("operation");
	}
	
	public void refreshMembers() {
		refreshMembers("operation");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		operationDataManager.setScope(scope);
		operationListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		operationListManager.refresh();
	}
	
	public String getOperationListPage() {
		return "/nam/model/operation/operationListPage.xhtml";
	}
	
	public String getOperationTreePage() {
		return "/nam/model/operation/operationTreePage.xhtml";
	}
	
	public String getOperationSummaryPage() {
		return "/nam/model/operation/operationSummaryPage.xhtml";
	}
	
	public String getOperationRecordPage() {
		return "/nam/model/operation/operationRecordPage.xhtml";
	}
	
	public String getOperationWizardPage() {
		return "/nam/model/operation/operationWizardPage.xhtml";
	}
	
	public String getOperationManagementPage() {
		return "/nam/model/operation/operationManagementPage.xhtml";
	}
	
	public String initializeOperationListPage() {
		String pageLevelKey = "operationList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Operations", "showOperationManagementPage()");
		String url = getOperationListPage();
		selectionContext.setCurrentArea("operation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeOperationTreePage() {
		String pageLevelKey = "operationTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Operations", "showOperationTreePage()");
		String url = getOperationTreePage();
		selectionContext.setCurrentArea("operation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeOperationSummaryPage(Operation operation) {
		String pageLevelKey = "operationSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Operations", "showOperationSummaryPage()");
		String url = getOperationSummaryPage();
		selectionContext.setCurrentArea("operation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeOperationRecordPage() {
		Service service = selectionContext.getSelection("service");
		Operation operation = selectionContext.getSelection("operation");
		String serviceName = service.getName();
		String operationName = operation.getName();
		
		String pageLevelKey = "operationRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Services", "showServiceManagementPage()");
		addBreadcrumb(pageLevelKey, "Service "+serviceName, "showServiceRecordPage()");
		addBreadcrumb(pageLevelKey, operationName, "showOperationRecordPage()");
		String url = getOperationRecordPage();
		selectionContext.setCurrentArea("operation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeOperationCreationPage(Operation operation) {
		setPageTitle("New "+getOperationLabel(operation));
		setPageIcon("/icons/nam/NewOperation16.gif");
		setSectionTitle("Operation Identification");
		operationWizard.setNewMode(true);
		
		String pageLevelKey = "operation";
		String wizardLevelKey = "operationWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Operations", "showOperationManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Operation", "showOperationWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showOperationWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showOperationWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showOperationWizardPage('Documentation')");
		
		operationIdentificationSection.setOwner("operationWizard");
		operationConfigurationSection.setOwner("operationWizard");
		operationDocumentationSection.setOwner("operationWizard");
		operationParametersSection.setOwner("operationWizard");
		operationCommandsSection.setOwner("operationWizard");
		operationResultsSection.setOwner("operationWizard");
		operationTimeoutsSection.setOwner("operationWizard");
		operationFaultsSection.setOwner("operationWizard");
		
		sections.clear();
		sections.add(operationIdentificationSection);
		sections.add(operationConfigurationSection);
		sections.add(operationDocumentationSection);
		sections.add(operationParametersSection);
		sections.add(operationCommandsSection);
		sections.add(operationResultsSection);
		sections.add(operationTimeoutsSection);
		sections.add(operationFaultsSection);
		
		String url = getOperationWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("operation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeOperationUpdatePage(Operation operation) {
		setPageTitle(getOperationLabel(operation));
		setPageIcon("/icons/nam/Operation16.gif");
		setSectionTitle("Operation Overview");
		String operationName = operation.getName();
		operationWizard.setNewMode(false);
		
		String pageLevelKey = "operation";
		String wizardLevelKey = "operationWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Operations", "showOperationManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(operationName, "showOperationWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showOperationWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showOperationWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showOperationWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showOperationWizardPage('Documentation')");
		
		operationOverviewSection.setOwner("operationWizard");
		operationIdentificationSection.setOwner("operationWizard");
		operationConfigurationSection.setOwner("operationWizard");
		operationDocumentationSection.setOwner("operationWizard");
		operationParametersSection.setOwner("operationWizard");
		operationCommandsSection.setOwner("operationWizard");
		operationResultsSection.setOwner("operationWizard");
		operationTimeoutsSection.setOwner("operationWizard");
		operationFaultsSection.setOwner("operationWizard");
		
		sections.clear();
		sections.add(operationOverviewSection);
		sections.add(operationIdentificationSection);
		sections.add(operationConfigurationSection);
		sections.add(operationDocumentationSection);
		sections.add(operationParametersSection);
		sections.add(operationCommandsSection);
		sections.add(operationResultsSection);
		sections.add(operationTimeoutsSection);
		sections.add(operationFaultsSection);
		
		String url = getOperationWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("operation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeOperationManagementPage() {
		setPageTitle("Operations");
		setPageIcon("/icons/nam/Operation16.gif");
		String pageLevelKey = "operationManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Operations", "showOperationManagementPage()");
		String url = getOperationManagementPage();
		selectionContext.setCurrentArea("operation");
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
		setSectionType("operation");
		setSectionName("Overview");
		setSectionTitle("Overview of Operations");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeOperationSummaryView(Operation operation) {
		//String viewTitle = getOperationLabel(operation);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("operation");
		setSectionName("Summary");
		setSectionTitle("Summary of Operation Records");
		setSectionIcon("/icons/nam/Operation16.gif");
		String viewLevelKey = "operationSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Operations", "showOperationManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getOperationLabel(Operation operation) {
		String label = "Operation";
		String name = OperationUtil.getLabel(operation);
		if (name == null && operation.getName() != null)
			name = NameUtil.capName(operation.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}

	protected void updateState() {
		AbstractWizardPage<Operation> page = operationWizard.getPage();
		if (page != null)
			setSectionTitle("Operation " + page.getName());
	}

	protected void updateState(Operation operation) {
		String operationName = NameUtil.capName(operation.getName());
		setSectionTitle(operationName + " Operation");
	}
	
}
