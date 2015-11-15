package nam.model.model;

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

import nam.model.Model;
import nam.model._import.ImportPageManager;
import nam.model.util.ModelUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("modelPageManager")
public class ModelPageManager extends AbstractPageManager<Model> implements Serializable {
	
	@Inject
	private ModelWizard modelWizard;
	
	@Inject
	private ModelDataManager modelDataManager;
	
	@Inject
	private ModelListManager modelListManager;
	
	@Inject
	private ImportPageManager importPageManager;
	
	@Inject
	private ModelRecord_OverviewSection modelOverviewSection;
	
	@Inject
	private ModelRecord_IdentificationSection modelIdentificationSection;
	
	@Inject
	private ModelRecord_ConfigurationSection modelConfigurationSection;
	
	@Inject
	private ModelRecord_DocumentationSection modelDocumentationSection;
	
	@Inject
	private ModelRecord_PackagesSection modelPackagesSection;
	
	@Inject
	private ModelRecord_ImportsSection modelImportsSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ModelPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("model");
	}
	
	public void refreshLocal() {
		refreshLocal("model");
	}
	
	public void refreshMembers() {
		refreshMembers("model");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		modelDataManager.setScope(scope);
		modelListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		//packagePageManager.refresh(scope);
		importPageManager.refresh(scope);
		modelListManager.refresh();
	}
	
	public String getModelListPage() {
		return "/nam/model/model/modelListPage.xhtml";
	}
	
	public String getModelTreePage() {
		return "/nam/model/model/modelTreePage.xhtml";
	}
	
	public String getModelSummaryPage() {
		return "/nam/model/model/modelSummaryPage.xhtml";
	}
	
	public String getModelRecordPage() {
		return "/nam/model/model/modelRecordPage.xhtml";
	}
	
	public String getModelWizardPage() {
		return "/nam/model/model/modelWizardPage.xhtml";
	}
	
	public String getModelManagementPage() {
		return "/nam/model/model/modelManagementPage.xhtml";
	}
	
	public String initializeModelListPage() {
		String pageLevelKey = "modelList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Models", "showModelManagementPage()");
		String url = getModelListPage();
		selectionContext.setCurrentArea("model");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeModelTreePage() {
		String pageLevelKey = "modelTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Models", "showModelTreePage()");
		String url = getModelTreePage();
		selectionContext.setCurrentArea("model");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeModelSummaryPage(Model model) {
		String pageLevelKey = "modelSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Models", "showModelSummaryPage()");
		String url = getModelSummaryPage();
		selectionContext.setCurrentArea("model");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeModelRecordPage() {
		Model model = selectionContext.getSelection("model");
		String modelName = ModelUtil.getLabel(model);
		
		String pageLevelKey = "modelRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Models", "showModelManagementPage()");
		addBreadcrumb(pageLevelKey, modelName, "showModelRecordPage()");
		String url = getModelRecordPage();
		selectionContext.setCurrentArea("model");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeModelCreationPage(Model model) {
		setPageTitle("New "+getModelLabel(model));
		setPageIcon("/icons/nam/NewModel16.gif");
		setSectionTitle("Model Identification");
		modelWizard.setNewMode(true);
		
		String pageLevelKey = "model";
		String wizardLevelKey = "modelWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Models", "showModelManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Model", "showModelWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Packages", "showModelWizardPage('Packages')");
		addBreadcrumb(wizardLevelKey, "Imports", "showModelWizardPage('Imports')");
		
		modelIdentificationSection.setOwner("modelWizard");
		modelConfigurationSection.setOwner("modelWizard");
		modelDocumentationSection.setOwner("modelWizard");
		modelPackagesSection.setOwner("modelWizard");
		modelImportsSection.setOwner("modelWizard");
		
		sections.clear();
		sections.add(modelIdentificationSection);
		sections.add(modelConfigurationSection);
		sections.add(modelDocumentationSection);
		sections.add(modelPackagesSection);
		sections.add(modelImportsSection);
		
		String url = getModelWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("model");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeModelUpdatePage(Model model) {
		setPageTitle(getModelLabel(model));
		setPageIcon("/icons/nam/Model16.gif");
		setSectionTitle("Model Overview");
		String modelName = ModelUtil.getLabel(model);
		modelWizard.setNewMode(false);
		
		String pageLevelKey = "model";
		String wizardLevelKey = "modelWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Models", "showModelManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(modelName, "showModelWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Packages", "showModelWizardPage('Packages')");
		addBreadcrumb(wizardLevelKey, "Imports", "showModelWizardPage('Imports')");
		
		modelOverviewSection.setOwner("modelWizard");
		modelIdentificationSection.setOwner("modelWizard");
		modelConfigurationSection.setOwner("modelWizard");
		modelDocumentationSection.setOwner("modelWizard");
		modelPackagesSection.setOwner("modelWizard");
		modelImportsSection.setOwner("modelWizard");
		
		sections.clear();
		sections.add(modelOverviewSection);
		sections.add(modelIdentificationSection);
		sections.add(modelConfigurationSection);
		sections.add(modelDocumentationSection);
		sections.add(modelPackagesSection);
		sections.add(modelImportsSection);
		
		String url = getModelWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("model");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeModelManagementPage() {
		setPageTitle("Models");
		setPageIcon("/icons/nam/Model16.gif");
		String pageLevelKey = "modelManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Models", "showModelManagementPage()");
		String url = getModelManagementPage();
		selectionContext.setCurrentArea("model");
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
		setSectionType("model");
		setSectionName("Overview");
		setSectionTitle("Overview of Models");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeModelPackagesView() {
		setSectionType("model");
		setSectionName("Packages");
		setSectionTitle("Model Packages");
		setSectionIcon("/icons/nam/Package16.gif");
		selectionContext.setMessageDomain("modelPackages");
		//packagePageManager.refresh("model");
		modelListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeModelImportsView() {
		setSectionType("model");
		setSectionName("Imports");
		setSectionTitle("Model Imports");
		setSectionIcon("/icons/nam/Import16.gif");
		selectionContext.setMessageDomain("modelImports");
		importPageManager.refresh("model");
		modelListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeModelSummaryView(Model model) {
		//String viewTitle = getModelLabel(model);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("model");
		setSectionName("Summary");
		setSectionTitle("Summary of Model Records");
		setSectionIcon("/icons/nam/Model16.gif");
		String viewLevelKey = "modelSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Models", "showModelManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getModelLabel(Model model) {
		String label = "Model";
		String name = ModelUtil.getLabel(model);
		if (name == null && model.getName() != null)
			name = ModelUtil.getLabel(model);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Model> page = modelWizard.getPage();
		if (page != null)
			setSectionTitle("Model " + page.getName());
	}
	
	protected void updateState(Model model) {
		String modelName = ModelUtil.getLabel(model);
		setSectionTitle(modelName + " Model");
	}
	
}
