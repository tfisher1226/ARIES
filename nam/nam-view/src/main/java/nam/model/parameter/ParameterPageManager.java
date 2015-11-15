package nam.model.parameter;

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
		initializeDefaultView();
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
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		parameterDataManager.setScope(scope);
		parameterListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		parameterListManager.refresh();
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
		String parameterName = parameter.getName();
		
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
		
		addBreadcrumb(wizardLevelKey, "Identification", "showParameterWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showParameterWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showParameterWizardPage('Documentation')");
		
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
		refresh();
		return url;
	}
	
	public String initializeParameterUpdatePage(Parameter parameter) {
		setPageTitle(getParameterLabel(parameter));
		setPageIcon("/icons/nam/Parameter16.gif");
		setSectionTitle("Parameter Overview");
		String parameterName = parameter.getName();
		parameterWizard.setNewMode(false);
		
		String pageLevelKey = "parameter";
		String wizardLevelKey = "parameterWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Parameters", "showParameterManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(parameterName, "showParameterWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showParameterWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showParameterWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showParameterWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showParameterWizardPage('Documentation')");
		
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
		refresh();
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
		refresh();
		return url;
	}
	
	public void initializeDefaultView() {
		setSectionType("parameter");
		setSectionName("Overview");
		setSectionTitle("Overview of Parameters");
		setSectionIcon("/icons/nam/Overview16.gif");
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
			name = NameUtil.capName(parameter.getName());
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
		String parameterName = NameUtil.capName(parameter.getName());
		setSectionTitle(parameterName + " Parameter");
	}
	
}
