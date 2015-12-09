package nam.model.component;

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

import nam.model.Component;
import nam.model.util.ComponentUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("componentPageManager")
public class ComponentPageManager extends AbstractPageManager<Component> implements Serializable {
	
	@Inject
	private ComponentWizard componentWizard;
	
	@Inject
	private ComponentDataManager componentDataManager;
	
	@Inject
	private ComponentInfoManager componentInfoManager;
	
	@Inject
	private ComponentListManager componentListManager;
	
	@Inject
	private ComponentListManager_Cache cacheComponentListManager;
	
	@Inject
	private ComponentListManager_Persistence persistenceComponentListManager;
	
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
		//refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		componentDataManager.setScope(scope);
		componentListManager.refresh();
		cacheComponentListManager.refresh();
		persistenceComponentListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		//nothing for now
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
	
	public void handleComponentSelected(@Observes @Selected Component component) {
		selectionContext.setSelection("component",  component);
		componentInfoManager.setRecord(component);
	}
	
	public void handleComponentUnselected(@Observes @Unselected Component component) {
		selectionContext.unsetSelection("component",  component);
		componentInfoManager.unsetRecord(component);
	}
	
	public void handleComponentChecked() {
		String scope = "componentSelection";
		ComponentListObject listObject = componentListManager.getSelection();
		Component component = selectionContext.getSelection("component");
		boolean checked = componentListManager.getCheckedState();
		listObject.setChecked(checked);
		if (checked) {
			componentInfoManager.setRecord(component);
			selectionContext.setSelection(scope,  component);
		} else {
			componentInfoManager.unsetRecord(component);
			selectionContext.unsetSelection(scope,  component);
		}
		refreshLocal(scope);
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
		refreshLocal();
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
		refreshLocal();
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
		return url;
	}
	
	public void initializeDefaultView() {
		setPageTitle("Components");
		setPageIcon("/icons/nam/Component16.gif");
		setSectionType("component");
		setSectionName("Overview");
		setSectionTitle("Overview of Components");
		setSectionIcon("/icons/nam/Overview16.gif");
		String viewLevelKey = "componentOverview";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Components", "showComponentManagementPage()");
		String scope = "projectList";
		refreshLocal(scope);
		sections.clear();
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
