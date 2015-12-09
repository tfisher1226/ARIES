package nam.model.parameter;

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

import nam.model.Parameter;
import nam.model.util.ParameterUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("parameterPageManager")
public class ParameterPageManager extends AbstractPageManager<Parameter> implements Serializable {
	
	@Inject
	private ParameterWizard parameterWizard;
	
	@Inject
	private ParameterDataManager parameterDataManager;
	
	@Inject
	private ParameterInfoManager parameterInfoManager;
	
	@Inject
	private ParameterListManager parameterListManager;
	
	@Inject
	private ParameterRecord_OverviewSection parameterOverviewSection;
	
	@Inject
	private ParameterRecord_IdentificationSection parameterIdentificationSection;
	
	@Inject
	private ParameterRecord_ConfigurationSection parameterConfigurationSection;
	
	@Inject
	private ParameterRecord_DocumentationSection parameterDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ParameterPageManager() {
		initializeSections();
	}
	
	
	public void refresh() {
		refresh("parameter");
	}
	
	public void refreshLocal() {
		refreshLocal("parameter");
	}
	
	public void refreshMembers() {
		refreshMembers("parameter");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		//refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		parameterDataManager.setScope(scope);
		parameterListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		//nothing for now
	}
	
	public String getParameterListPage() {
		return "/nam/model/parameter/parameterListPage.xhtml";
	}
	
	public String getParameterTreePage() {
		return "/nam/model/parameter/parameterTreePage.xhtml";
	}
	
	public String getParameterSummaryPage() {
		return "/nam/model/parameter/parameterSummaryPage.xhtml";
	}
	
	public String getParameterRecordPage() {
		return "/nam/model/parameter/parameterRecordPage.xhtml";
	}
	
	public String getParameterWizardPage() {
		return "/nam/model/parameter/parameterWizardPage.xhtml";
	}
	
	public String getParameterManagementPage() {
		return "/nam/model/parameter/parameterManagementPage.xhtml";
	}
	
	public void handleParameterSelected(@Observes @Selected Parameter parameter) {
		selectionContext.setSelection("parameter",  parameter);
		parameterInfoManager.setRecord(parameter);
	}
	
	public void handleParameterUnselected(@Observes @Unselected Parameter parameter) {
		selectionContext.unsetSelection("parameter",  parameter);
		parameterInfoManager.unsetRecord(parameter);
	}
	
	public void handleParameterChecked() {
		String scope = "parameterSelection";
		ParameterListObject listObject = parameterListManager.getSelection();
		Parameter parameter = selectionContext.getSelection("parameter");
		boolean checked = parameterListManager.getCheckedState();
		listObject.setChecked(checked);
		if (checked) {
			parameterInfoManager.setRecord(parameter);
			selectionContext.setSelection(scope,  parameter);
		} else {
			parameterInfoManager.unsetRecord(parameter);
			selectionContext.unsetSelection(scope,  parameter);
		}
		refreshLocal(scope);
	}
	
	public String initializeParameterListPage() {
		String pageLevelKey = "parameterList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Parameters", "showParameterManagementPage()");
		String url = getParameterListPage();
		selectionContext.setCurrentArea("parameter");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeParameterTreePage() {
		String pageLevelKey = "parameterTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Parameters", "showParameterTreePage()");
		String url = getParameterTreePage();
		selectionContext.setCurrentArea("parameter");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeParameterSummaryPage(Parameter parameter) {
		String pageLevelKey = "parameterSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Parameters", "showParameterSummaryPage()");
		String url = getParameterSummaryPage();
		selectionContext.setCurrentArea("parameter");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeParameterRecordPage() {
		Parameter parameter = selectionContext.getSelection("parameter");
		String parameterName = ParameterUtil.getLabel(parameter);
		
		String pageLevelKey = "parameterRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Parameters", "showParameterManagementPage()");
		addBreadcrumb(pageLevelKey, parameterName, "showParameterRecordPage()");
		String url = getParameterRecordPage();
		selectionContext.setCurrentArea("parameter");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeParameterCreationPage(Parameter parameter) {
		setPageTitle("New "+getParameterLabel(parameter));
		setPageIcon("/icons/nam/NewParameter16.gif");
		setSectionTitle("Parameter Identification");
		parameterWizard.setNewMode(true);
		
		String pageLevelKey = "parameter";
		String wizardLevelKey = "parameterWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Parameters", "showParameterManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Parameter", "showParameterWizardPage()"));
		
		
		parameterIdentificationSection.setOwner("parameterWizard");
		parameterConfigurationSection.setOwner("parameterWizard");
		parameterDocumentationSection.setOwner("parameterWizard");
		
		sections.clear();
		sections.add(parameterIdentificationSection);
		sections.add(parameterConfigurationSection);
		sections.add(parameterDocumentationSection);
		
		String url = getParameterWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("parameter");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializeParameterUpdatePage(Parameter parameter) {
		setPageTitle(getParameterLabel(parameter));
		setPageIcon("/icons/nam/Parameter16.gif");
		setSectionTitle("Parameter Overview");
		String parameterName = ParameterUtil.getLabel(parameter);
		parameterWizard.setNewMode(false);
		
		String pageLevelKey = "parameter";
		String wizardLevelKey = "parameterWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Parameters", "showParameterManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(parameterName, "showParameterWizardPage()"));
		
		
		parameterOverviewSection.setOwner("parameterWizard");
		parameterIdentificationSection.setOwner("parameterWizard");
		parameterConfigurationSection.setOwner("parameterWizard");
		parameterDocumentationSection.setOwner("parameterWizard");
		
		sections.clear();
		sections.add(parameterOverviewSection);
		sections.add(parameterIdentificationSection);
		sections.add(parameterConfigurationSection);
		sections.add(parameterDocumentationSection);
		
		String url = getParameterWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("parameter");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializeParameterManagementPage() {
		setPageTitle("Parameters");
		setPageIcon("/icons/nam/Parameter16.gif");
		String pageLevelKey = "parameterManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Parameters", "showParameterManagementPage()");
		String url = getParameterManagementPage();
		selectionContext.setCurrentArea("parameter");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public void initializeDefaultView() {
		setPageTitle("Parameters");
		setPageIcon("/icons/nam/Parameter16.gif");
		setSectionType("parameter");
		setSectionName("Overview");
		setSectionTitle("Overview of Parameters");
		setSectionIcon("/icons/nam/Overview16.gif");
		String viewLevelKey = "parameterOverview";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Parameters", "showParameterManagementPage()");
		String scope = "projectList";
		refreshLocal(scope);
		sections.clear();
	}
	
	public String initializeParameterSummaryView(Parameter parameter) {
		//String viewTitle = getParameterLabel(parameter);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("parameter");
		setSectionName("Summary");
		setSectionTitle("Summary of Parameter Records");
		setSectionIcon("/icons/nam/Parameter16.gif");
		String viewLevelKey = "parameterSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Parameters", "showParameterManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getParameterLabel(Parameter parameter) {
		String label = "Parameter";
		String name = ParameterUtil.getLabel(parameter);
		if (name == null && parameter.getName() != null)
			name = ParameterUtil.getLabel(parameter);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}

	protected void updateState() {
		AbstractWizardPage<Parameter> page = parameterWizard.getPage();
		if (page != null)
			setSectionTitle("Parameter " + page.getName());
	}

	protected void updateState(Parameter parameter) {
		String parameterName = ParameterUtil.getLabel(parameter);
		setSectionTitle(parameterName + " Parameter");
	}
	
}
