package nam.model.variable;

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

import nam.model.Variable;
import nam.model.util.VariableUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("variablePageManager")
public class VariablePageManager extends AbstractPageManager<Variable> implements Serializable {
	
	@Inject
	private VariableWizard variableWizard;
	
	@Inject
	private VariableDataManager variableDataManager;
	
	@Inject
	private VariableListManager variableListManager;
	
	@Inject
	private VariableRecord_OverviewSection variableOverviewSection;
	
	@Inject
	private VariableRecord_IdentificationSection variableIdentificationSection;
	
	@Inject
	private VariableRecord_ConfigurationSection variableConfigurationSection;
	
	@Inject
	private VariableRecord_DocumentationSection variableDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public VariablePageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("variable");
	}
	
	public void refreshLocal() {
		refreshLocal("variable");
	}
	
	public void refreshMembers() {
		refreshMembers("variable");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		variableDataManager.setScope(scope);
		variableListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		variableListManager.refresh();
	}
	
	public String getVariableListPage() {
		return "/nam/model/variable/variableListPage.xhtml";
	}
	
	public String getVariableTreePage() {
		return "/nam/model/variable/variableTreePage.xhtml";
	}
	
	public String getVariableSummaryPage() {
		return "/nam/model/variable/variableSummaryPage.xhtml";
	}
	
	public String getVariableRecordPage() {
		return "/nam/model/variable/variableRecordPage.xhtml";
	}
	
	public String getVariableWizardPage() {
		return "/nam/model/variable/variableWizardPage.xhtml";
	}
	
	public String getVariableManagementPage() {
		return "/nam/model/variable/variableManagementPage.xhtml";
	}
	
	public String initializeVariableListPage() {
		String pageLevelKey = "variableList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Variables", "showVariableManagementPage()");
		String url = getVariableListPage();
		selectionContext.setCurrentArea("variable");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeVariableTreePage() {
		String pageLevelKey = "variableTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Variables", "showVariableTreePage()");
		String url = getVariableTreePage();
		selectionContext.setCurrentArea("variable");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeVariableSummaryPage(Variable variable) {
		String pageLevelKey = "variableSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Variables", "showVariableSummaryPage()");
		String url = getVariableSummaryPage();
		selectionContext.setCurrentArea("variable");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeVariableRecordPage() {
		Variable variable = selectionContext.getSelection("variable");
		String variableName = variable.getName();
		
		String pageLevelKey = "variableRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Variables", "showVariableManagementPage()");
		addBreadcrumb(pageLevelKey, variableName, "showVariableRecordPage()");
		String url = getVariableRecordPage();
		selectionContext.setCurrentArea("variable");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeVariableCreationPage(Variable variable) {
		setPageTitle("New "+getVariableLabel(variable));
		setPageIcon("/icons/nam/NewVariable16.gif");
		setSectionTitle("Variable Identification");
		variableWizard.setNewMode(true);
		
		String pageLevelKey = "variable";
		String wizardLevelKey = "variableWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Variables", "showVariableManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Variable", "showVariableWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showVariableWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showVariableWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showVariableWizardPage('Documentation')");
		
		variableIdentificationSection.setOwner("variableWizard");
		variableConfigurationSection.setOwner("variableWizard");
		variableDocumentationSection.setOwner("variableWizard");
		
		sections.clear();
		sections.add(variableIdentificationSection);
		sections.add(variableConfigurationSection);
		sections.add(variableDocumentationSection);
		
		String url = getVariableWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("variable");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeVariableUpdatePage(Variable variable) {
		setPageTitle(getVariableLabel(variable));
		setPageIcon("/icons/nam/Variable16.gif");
		setSectionTitle("Variable Overview");
		String variableName = variable.getName();
		variableWizard.setNewMode(false);
		
		String pageLevelKey = "variable";
		String wizardLevelKey = "variableWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Variables", "showVariableManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(variableName, "showVariableWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showVariableWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showVariableWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showVariableWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showVariableWizardPage('Documentation')");
		
		variableOverviewSection.setOwner("variableWizard");
		variableIdentificationSection.setOwner("variableWizard");
		variableConfigurationSection.setOwner("variableWizard");
		variableDocumentationSection.setOwner("variableWizard");
		
		sections.clear();
		sections.add(variableOverviewSection);
		sections.add(variableIdentificationSection);
		sections.add(variableConfigurationSection);
		sections.add(variableDocumentationSection);
		
		String url = getVariableWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("variable");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeVariableManagementPage() {
		setPageTitle("Variables");
		setPageIcon("/icons/nam/Variable16.gif");
		String pageLevelKey = "variableManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Variables", "showVariableManagementPage()");
		String url = getVariableManagementPage();
		selectionContext.setCurrentArea("variable");
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
		setSectionType("variable");
		setSectionName("Overview");
		setSectionTitle("Overview of Variables");
		setSectionIcon("/icons/nam/Variable16.gif");
	}
	
	public String initializeVariableSummaryView(Variable variable) {
		//String viewTitle = getVariableLabel(variable);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("variable");
		setSectionName("Summary");
		setSectionTitle("Summary of Variables");
		setSectionIcon("/icons/nam/Variable16.gif");
		String viewLevelKey = "variableSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Variables", "showVariableManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getVariableLabel(Variable variable) {
		String label = "Variable";
		String name = VariableUtil.getLabel(variable);
		if (name == null && variable.getName() != null)
			name = NameUtil.capName(variable.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Variable> page = variableWizard.getPage();
		if (page != null)
			setSectionTitle("Variable " + page.getName());
	}
	
	protected void updateState(Variable variable) {
		String variableName = NameUtil.capName(variable.getName());
		setSectionTitle(variableName + " Variable");
	}
	
}
