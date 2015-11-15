package nam.model.component;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Component;
import nam.model.util.ComponentUtil;
import nam.ui.design.SelectionContext;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractPageManager;
import org.aries.ui.AbstractWizardPage;
import org.aries.ui.Breadcrumb;
import org.aries.ui.PageManager;
import org.aries.util.NameUtil;


@SessionScoped
@Named("componentPageManager")
public class ComponentPageManager extends AbstractPageManager<Component> implements Serializable {
	
	@Inject
	private ComponentWizard componentWizard;
	
	@Inject
	private ComponentDataManager componentDataManager;

	@Inject
	private ComponentListManager componentListManager;
	
	@Inject
	private ComponentRecord_OverviewSection componentOverviewSection;
	
	@Inject
	private ComponentRecord_IdentificationSection componentIdentificationSection;
	
	@Inject
	private ComponentRecord_ConfigurationSection componentConfigurationSection;
	
	@Inject
	private ComponentRecord_DocumentationSection componentDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ComponentPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("component");
	}
	
	public void refreshLocal() {
		refreshLocal("component");
	}
	
	public void refreshMembers() {
		refreshMembers("component");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		componentDataManager.setScope(scope);
		componentListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		componentListManager.refresh();
	}
	
	public String getComponentListPage() {
		return "/nam/model/component/componentListPage.xhtml";
	}
	
	public String getComponentTreePage() {
		return "/nam/model/component/componentTreePage.xhtml";
	}
	
	public String getComponentSummaryPage() {
		return "/nam/model/component/componentSummaryPage.xhtml";
	}
	
	public String getComponentRecordPage() {
		return "/nam/model/component/componentRecordPage.xhtml";
	}
	
	public String getComponentWizardPage() {
		return "/nam/model/component/componentWizardPage.xhtml";
	}
	
	public String getComponentManagementPage() {
		return "/nam/model/component/componentManagementPage.xhtml";
	}
	
	public String initializeComponentListPage() {
		String pageLevelKey = "componentList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Components", "showComponentManagementPage()");
		String url = getComponentListPage();
		selectionContext.setCurrentArea("component");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeComponentTreePage() {
		String pageLevelKey = "componentTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Components", "showComponentTreePage()");
		String url = getComponentTreePage();
		selectionContext.setCurrentArea("component");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeComponentSummaryPage(Component component) {
		String pageLevelKey = "componentSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Components", "showComponentSummaryPage()");
		String url = getComponentSummaryPage();
		selectionContext.setCurrentArea("component");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeComponentRecordPage() {
		Component component = selectionContext.getSelection("component");
		String componentName = ComponentUtil.getLabel(component);
		
		String pageLevelKey = "componentRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Applications", "showApplicationManagementPage()");
		addBreadcrumb(pageLevelKey, "Services", "showServiceManagementPage()");
		addBreadcrumb(pageLevelKey, "Components", "showComponentManagementPage()");
		addBreadcrumb(pageLevelKey, componentName, "showComponentRecordPage()");
		String url = getComponentRecordPage();
		selectionContext.setCurrentArea("component");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeComponentCreationPage(Component component) {
		setPageTitle("New "+getComponentLabel(component));
		setPageIcon("/icons/nam/NewComponent16.gif");
		setSectionTitle("Component Identification");
		componentWizard.setNewMode(true);
		
		String pageLevelKey = "component";
		String wizardLevelKey = "componentWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Applications", "showApplicationManagementPage()");
		addBreadcrumb(pageLevelKey, "Services", "showServiceManagementPage()");
		addBreadcrumb(pageLevelKey, "Components", "showComponentManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Component", "showComponentWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showComponentWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showComponentWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showComponentWizardPage('Documentation')");
		
		componentIdentificationSection.setOwner("componentWizard");
		componentConfigurationSection.setOwner("componentWizard");
		componentDocumentationSection.setOwner("componentWizard");
		
		sections.clear();
		sections.add(componentIdentificationSection);
		sections.add(componentConfigurationSection);
		sections.add(componentDocumentationSection);
		
		String url = getComponentWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("component");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeComponentUpdatePage(Component component) {
		setPageTitle(getComponentLabel(component));
		setPageIcon("/icons/nam/Component16.gif");
		setSectionTitle("Component Overview");
		String componentName = ComponentUtil.getLabel(component);
		componentWizard.setNewMode(false);
		
		String pageLevelKey = "component";
		String wizardLevelKey = "componentWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Components", "showComponentManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(componentName, "showComponentWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showComponentWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showComponentWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showComponentWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showComponentWizardPage('Documentation')");
		
		componentOverviewSection.setOwner("componentWizard");
		componentIdentificationSection.setOwner("componentWizard");
		componentConfigurationSection.setOwner("componentWizard");
		componentDocumentationSection.setOwner("componentWizard");
		
		sections.clear();
		sections.add(componentOverviewSection);
		sections.add(componentIdentificationSection);
		sections.add(componentConfigurationSection);
		sections.add(componentDocumentationSection);
		
		String url = getComponentWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("component");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeComponentManagementPage() {
		setPageTitle("Components");
		setPageIcon("/icons/nam/Component16.gif");
		String pageLevelKey = "componentManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Components", "showComponentManagementPage()");
		String url = getComponentManagementPage();
		selectionContext.setCurrentArea("component");
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
		setSectionType("component");
		setSectionName("Overview");
		setSectionTitle("Overview of Components");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeComponentSummaryView(Component component) {
		//String viewTitle = getComponentLabel(component);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("component");
		setSectionName("Summary");
		setSectionTitle("Summary of Component Records");
		setSectionIcon("/icons/nam/Component16.gif");
		String viewLevelKey = "componentSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Components", "showComponentManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getComponentLabel(Component component) {
		String label = "Component";
		String name = ComponentUtil.getLabel(component);
		if (name == null && component.getName() != null)
			name = ComponentUtil.getLabel(component);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}

	protected void updateState() {
		AbstractWizardPage<Component> page = componentWizard.getPage();
		if (page != null)
			setSectionTitle("Component " + page.getName());
	}

	protected void updateState(Component component) {
		String componentName = ComponentUtil.getLabel(component);
		setSectionTitle(componentName + " Component"); 
	}
	
}
