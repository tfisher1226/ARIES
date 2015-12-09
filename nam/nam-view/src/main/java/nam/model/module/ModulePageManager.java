package nam.model.module;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Module;
import nam.model.ModuleType;
import nam.model.component.ComponentPageManager;
import nam.model.domain.DomainPageManager;
import nam.model.element.ElementPageManager;
import nam.model.project.ProjectPageManager;
import nam.model.provider.ProviderPageManager;
import nam.model.service.ServicePageManager;
import nam.model.util.ModuleUtil;
import nam.ui.design.SelectionContext;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractPageManager;
import org.aries.ui.AbstractWizard;
import org.aries.ui.AbstractWizardPage;
import org.aries.ui.Breadcrumb;
import org.aries.ui.PageManager;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Unselected;
import org.aries.util.NameUtil;


@SessionScoped
@Named("modulePageManager")
public class ModulePageManager extends AbstractPageManager<Module> implements Serializable {

	@Inject
	private ModuleWizard moduleWizard;

	@Inject
	private ModuleDataManager moduleDataManager;
	
	@Inject
	private ModuleInfoManager moduleInfoManager;
	
	@Inject
	private ModuleListManager moduleListManager;
	
	@Inject
	private ModuleListManager_Basic basicModuleListManager;
	
	@Inject
	private ModuleListManager_Client clientModuleListManager;
	
	@Inject
	private ModuleListManager_Namespace namespaceModuleListManager;
	
	@Inject
	private ModuleListManager_Persistence persistenceModuleListManager;
	
	@Inject
	private ModuleListManager_Project projectModuleListManager;
	
	@Inject
	private ModuleListManager_Service serviceModuleListManager;
	
	@Inject
	private ModuleListManager_View viewModuleListManager;
	
	@Inject
	private ModuleListManager_Test testModuleListManager;
	
	@Inject
	private ModuleListManager_EAR earModuleListManager;
	
	@Inject
	private ModuleListManager_EAR warModuleListManager;
	
	@Inject
	private ProjectPageManager projectPageManager;
	
	@Inject
	private DomainPageManager domainPageManager;
	
	@Inject
	private ServicePageManager servicePageManager;
	
	@Inject
	private ElementPageManager elementPageManager;
	
	@Inject
	private ComponentPageManager componentPageManager;

	@Inject
	private ProviderPageManager providerPageManager;

	@Inject
	private ModuleRecord_OverviewSection moduleOverviewSection;
	
	@Inject
	private ModuleRecord_SourceSelectionSection moduleSourceSelectionSection;
	
	@Inject
	private ModuleRecord_TypeSelectionSection moduleTypeSelectionSection;
	
	@Inject
	private ModuleRecord_IdentificationSection moduleIdentificationSection;
	
	@Inject
	private ModuleRecord_ConfigurationSection moduleConfigurationSection;
	
	@Inject
	private ModuleRecord_DocumentationSection moduleDocumentationSection;

	@Inject
	private ModuleRecord_DomainsSection moduleDomainsSection;
	
	@Inject
	private ModuleRecord_ServicesSection moduleServicesSection;

	@Inject
	private ModuleRecord_ElementsSection moduleElementsSection;

	@Inject
	private ModuleRecord_ComponentsSection moduleComponentsSection;
	
	@Inject
	private ModuleRecord_ProvidersSection moduleProvidersSection;
	
	@Inject
	private SelectionContext selectionContext;

	
	public ModulePageManager() {
		initializeSections();
	}

	
	public void refresh() {
		refresh("projectList");
	}

	public void refreshLocal() {
		refreshLocal("module");
	}

	public void refreshMembers() {
		refreshMembers("module");
	}

	public void refresh(String scope) {
		refreshLocal(scope);
		//refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		moduleDataManager.setScope(scope);
		moduleListManager.refresh();
		basicModuleListManager.refresh();
		clientModuleListManager.refresh();
		namespaceModuleListManager.refresh();
		persistenceModuleListManager.refresh();
		projectModuleListManager.refresh();
		serviceModuleListManager.refresh();
		viewModuleListManager.refresh();
		testModuleListManager.refresh();
		earModuleListManager.refresh();
		warModuleListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		domainPageManager.refresh(scope);
		servicePageManager.refresh(scope);
		elementPageManager.refresh(scope);
		componentPageManager.refresh(scope);
		providerPageManager.refresh(scope);
	}

	private boolean subListsVisible = true;
	
	public boolean isSubListsVisible() {
		return subListsVisible;
	}

	public void setSubListsVisible(boolean subListsVisible) {
		this.subListsVisible = subListsVisible;
	}

	public void expandCollapseModuleList() {
		subListsVisible = !subListsVisible;
		boolean visible = subListsVisible;
		
//		basicModuleListManager.isVisible();
//		clientModuleListManager.isVisible();
//		namespaceModuleListManager.isVisible();
//		persistenceModuleListManager.isVisible();
//		projectModuleListManager.isVisible();
//		serviceModuleListManager.isVisible();
//		viewModuleListManager.isVisible();
//		testModuleListManager.isVisible();
//		earModuleListManager.isVisible();
//		warModuleListManager.isVisible();
		
		basicModuleListManager.setVisible(visible);
		clientModuleListManager.setVisible(visible);
		namespaceModuleListManager.setVisible(visible);
		persistenceModuleListManager.setVisible(visible);
		projectModuleListManager.setVisible(visible);
		serviceModuleListManager.setVisible(visible);
		viewModuleListManager.setVisible(visible);
		testModuleListManager.setVisible(visible);
		earModuleListManager.setVisible(visible);
		warModuleListManager.setVisible(visible);
	}
	
	public String getModuleListPage() {
		return "/nam/model/module/moduleListPage.xhtml";
	}

	public String getModuleTreePage() {
		return "/nam/model/module/moduleTreePage.xhtml";
	}

	public String getModuleSummaryPage() {
		return "/nam/model/module/moduleSummaryPage.xhtml";
	}

	public String getModuleRecordPage() {
		return "/nam/model/module/moduleRecordPage.xhtml";
	}
	
	public String getModuleWizardPage() {
		return "/nam/model/module/moduleWizardPage.xhtml";
	}

	public String getModuleManagementPage() {
		return "/nam/model/module/moduleManagementPage.xhtml";
	}

	public String getModuleSourceSelectionPage() {
		return getModuleWizardPage() + "?section=sourceSelection";
	}

	public String getModuleTypeSelectionPage() {
		return getModuleWizardPage() + "?section=typeSelection";
	}
	
	public void handleModuleSelected(@Observes @Selected Module module) {
		selectionContext.setSelection("module",  module);
		moduleInfoManager.setRecord(module);
	}
	
	public void handleModuleUnselected(@Observes @Unselected Module module) {
		selectionContext.unsetSelection("module",  module);
		moduleInfoManager.unsetRecord(module);
	}
	
	public void handleModuleChecked() {
		String scope = "moduleSelection";
		ModuleListObject listObject = moduleListManager.getSelection();
		Module module = selectionContext.getSelection("module");
		boolean checked = moduleListManager.getCheckedState();
		listObject.setChecked(checked);
		if (checked) {
			moduleInfoManager.setRecord(module);
			selectionContext.setSelection(scope,  module);
		} else {
			moduleInfoManager.unsetRecord(module);
			selectionContext.unsetSelection(scope,  module);
		}
		String target = selectionContext.getCurrentTarget();
		if (target.equals("domain"))
			domainPageManager.refreshLocal(scope);
		if (target.equals("service"))
			servicePageManager.refreshLocal(scope);
		if (target.equals("element"))
			elementPageManager.refreshLocal(scope);
		if (target.equals("component"))
			componentPageManager.refreshLocal(scope);
		if (target.equals("provider"))
			providerPageManager.refreshLocal(scope);
		refreshLocal(scope);
	}
	
	public String initializeModuleListPage() {
		String pageLevelKey = "moduleList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Modules", "showModuleManagementPage()");
		String url = getModuleListPage();
		selectionContext.setCurrentArea("module");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeModuleTreePage() {
		String pageLevelKey = "moduleTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Modules", "showModuleTreePage()");
		String url = getModuleTreePage();
		selectionContext.setCurrentArea("module");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}

	public PageManager<?> getPageManager(String elementType) {
		String elementTypeUncapped = NameUtil.uncapName(elementType);
		PageManager<?> pageManager = BeanContext.getFromSession(elementTypeUncapped + "PageManager");
		return pageManager;
	}
	
	public String initializeModuleSummaryPage(Module module) {
		String currentArea = selectionContext.getCurrentArea();
		PageManager<?> pageManager = getPageManager(currentArea);
		pageManager.setSectionIcon("/icons/nam/Module16.gif"); 
		pageManager.setSectionTitle(getModuleLabel(module));
		pageManager.setSectionType("module"); 
		pageManager.setSectionName("Summary"); 
		
		String pageLevelKey = "moduleSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Modules", "showModuleSummaryPage()");
		String url = getModuleSummaryPage();
		selectionContext.setCurrentArea("module");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeModuleRecordPage() {
		Module module = selectionContext.getSelection("module");
		String moduleName = ModuleUtil.getLabel(module);
		
		String pageLevelKey = "moduleRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Modules", "showModuleManagementPage()");
		addBreadcrumb(pageLevelKey, moduleName, "showModuleRecordPage()");
		String url = getModuleRecordPage();
		selectionContext.setCurrentArea("module");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeModuleCreationPage(Module module) {
		module.setType(moduleWizard.getModuleType());
		setPageTitle("New "+getModuleLabel(module)); 
		setPageIcon("/icons/nam/NewModule16.gif");
		setSectionTitle("Module Identification"); 
		moduleWizard.setNewMode(true);
		
		String pageLevelKey = "module";
		String wizardLevelKey = "moduleWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Modules", "showModuleManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Module", "showModuleWizardPage()"));
		
//		addBreadcrumb(wizardLevelKey, "Domains", "showModuleWizardPage('Domains')");
//		addBreadcrumb(wizardLevelKey, "Services", "showModuleWizardPage('Services')");
//		addBreadcrumb(wizardLevelKey, "Elements", "showModuleWizardPage('Elements')");
//		addBreadcrumb(wizardLevelKey, "Components", "showModuleWizardPage('Components')");
//		addBreadcrumb(wizardLevelKey, "Providers", "showModuleWizardPage('Providers')");
		
		moduleSourceSelectionSection.setOwner("moduleWizard");
		moduleTypeSelectionSection.setOwner("moduleWizard");
		moduleIdentificationSection.setOwner("moduleWizard");
		moduleConfigurationSection.setOwner("moduleWizard");
		moduleDocumentationSection.setOwner("moduleWizard");
		moduleDomainsSection.setOwner("moduleWizard");
		moduleServicesSection.setOwner("moduleWizard");
		moduleElementsSection.setOwner("moduleWizard");
		moduleComponentsSection.setOwner("moduleWizard");
		moduleProvidersSection.setOwner("moduleWizard");

		sections.clear();
		sections.add(moduleSourceSelectionSection);
		sections.add(moduleTypeSelectionSection);
		sections.add(moduleIdentificationSection);
		sections.add(moduleConfigurationSection);
		sections.add(moduleDocumentationSection);
		sections.add(moduleDomainsSection);
		sections.add(moduleServicesSection);
		sections.add(moduleElementsSection);
		sections.add(moduleComponentsSection);
		sections.add(moduleProvidersSection);

		ModuleType moduleType = moduleWizard.getModuleType();
		if (moduleType != null) {
			switch (moduleType) {
			case POM:
				break;
			case MODEL:
				//addBreadcrumb(wizardLevelKey, "Namespaces", "showModuleWizardPage('Namespaces')");
				addBreadcrumb(wizardLevelKey, "Elements", "showModuleWizardPage('Elements')");
				sections.add(moduleElementsSection);
				break;
			case SERVICE:
				addBreadcrumb(wizardLevelKey, "Services", "showModuleWizardPage('Services')");
				sections.add(moduleServicesSection);
				break;
			case CLIENT:
				addBreadcrumb(wizardLevelKey, "Clients", "showModuleWizardPage('Clients')");
				break;
			case DATA:
				addBreadcrumb(wizardLevelKey, "Persistence", "showModuleWizardPage('Persistence')");
				break;
			case VIEW:
				break;
			case WAR:
				break;
			case EAR:
				break;
			}
		}

		String url = getModuleWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("module");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}

	public String initializeModuleUpdatePage(Module module) {
		setPageTitle(getModuleLabel(module)); 
		setPageIcon("/icons/nam/Module16.gif");
		setSectionTitle("Module Overview");
		String moduleName = ModuleUtil.getLabel(module);
		moduleWizard.setNewMode(false);

		String pageLevelKey = "module";
		String wizardLevelKey = "moduleWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Bookshop", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Modules", "showModuleManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(moduleName, "showModuleWizardPage()"));
		
//		addBreadcrumb(wizardLevelKey, "Domains", "showModuleWizardPage('Domains')");
//		addBreadcrumb(wizardLevelKey, "Services", "showModuleWizardPage('Services')");
//		addBreadcrumb(wizardLevelKey, "Elements", "showModuleWizardPage('Elements')");
//		addBreadcrumb(wizardLevelKey, "Components", "showModuleWizardPage('Components')");
//		addBreadcrumb(wizardLevelKey, "Providers", "showModuleWizardPage('Providers')");

		moduleOverviewSection.setOwner("moduleWizard");
		moduleIdentificationSection.setOwner("moduleWizard");
		moduleConfigurationSection.setOwner("moduleWizard");
		moduleDocumentationSection.setOwner("moduleWizard");
		moduleDomainsSection.setOwner("moduleWizard");
		moduleServicesSection.setOwner("moduleWizard");
		moduleElementsSection.setOwner("moduleWizard");
		moduleComponentsSection.setOwner("moduleWizard");
		moduleProvidersSection.setOwner("moduleWizard");

		sections.clear();
		sections.add(moduleOverviewSection);
		sections.add(moduleIdentificationSection);
		sections.add(moduleConfigurationSection);
		sections.add(moduleDocumentationSection);
		sections.add(moduleDomainsSection);
		sections.add(moduleServicesSection);
		sections.add(moduleElementsSection);
		sections.add(moduleComponentsSection);
		sections.add(moduleProvidersSection);
		
		String url = getModuleWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("module");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializeModuleManagementPage() {
//		AbstractWizard<?> peek = null;
//		if (!activeContexts.isEmpty())
//			peek = activeContexts.peek();
//		String currentContext2 = currentContext;
//		String currentArea = selectionContext.getCurrentArea();
//		refresh(currentArea);
		
		setPageTitle("Modules");
		setPageIcon("/icons/nam/Module16.gif");
		String pageLevelKey = "moduleManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		//TODO
		addBreadcrumb(pageLevelKey, "Modules", "showModuleManagementPage()");
		String url = getModuleManagementPage();
		selectionContext.setCurrentArea("module");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;	
	}
	
	public String initializeModuleSourceSelectionPage() {
		clearBreadcrumbs("moduleWizard");
		setPageTitle("New Module"); 
		setPageIcon("/icons/nam/NewModule16.gif");
		setSectionTitle("Module Source Selection"); 
		String pageLevelKey = "module";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Modules", "showModuleManagementPage()");
		sections.clear();
		sections.add(moduleSourceSelectionSection);
		moduleSourceSelectionSection.setOwner("moduleWizard");
		moduleWizard.setModuleType(null);
		String url = getModuleSourceSelectionPage();
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		moduleWizard.addPages(sections);
		moduleWizard.setNewMode(true);
		moduleWizard.initialize();
		return url;
	}
	
	public String initializeModuleTypeSelectionPage() {
		clearBreadcrumbs("moduleWizard");
		setPageTitle("New Module"); 
		setPageIcon("/icons/nam/NewModule16.gif");
		setSectionTitle("Module Type Selection"); 
		String pageLevelKey = "module";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Modules", "showModuleManagementPage()");
		sections.clear();
		sections.add(moduleTypeSelectionSection);
		moduleTypeSelectionSection.setOwner("moduleWizard");
		moduleWizard.setModuleType(null);
		String url = getModuleTypeSelectionPage();
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		moduleWizard.addPages(sections);
		moduleWizard.setNewMode(true);
		moduleWizard.initialize();
		return url;
	}
	
	public void initializeDefaultView() {
		setPageTitle("Modules");
		setPageIcon("/icons/nam/Module16.gif");
		setSectionType("module");
		setSectionName("Overview");
		setSectionTitle("Overview of Modules");
		setSectionIcon("/icons/nam/Overview16.gif");
		String viewLevelKey = "moduleOverview";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Modules", "showModuleManagementPage()");
		String scope = "projectList";
		projectPageManager.refreshLocal(scope);
		refreshLocal(scope);
		sections.clear();
	}

	public String initializeModuleDomainsView() {
		setSectionType("module");
		setSectionName("Domains");
		setSectionTitle("Domains");
		setSectionIcon("/icons/nam/ModuleDomain16.gif");
		selectionContext.setMessageDomain("moduleDomains");
		domainPageManager.refreshLocal("projectList");
		refreshLocal("domainSelection");
		sections.clear();
		return null;
	}
	
	public String initializeModuleServicesView() {
		setSectionType("module");
		setSectionName("Services");
		setSectionTitle("Services");
		setSectionIcon("/icons/nam/Service16.gif");
		selectionContext.setMessageDomain("moduleServices");
		servicePageManager.refreshLocal("moduleSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeModuleElementsView() {
		setSectionType("module");
		setSectionName("Elements");
		setSectionTitle("Elements");
		setSectionIcon("/icons/nam/Element16.gif");
		selectionContext.setMessageDomain("moduleElements");
		elementPageManager.refreshLocal("moduleSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeModuleComponentsView() {
		setSectionType("module");
		setSectionName("Components");
		setSectionTitle("Components");
		setSectionIcon("/icons/nam/Component16.gif");
		selectionContext.setMessageDomain("moduleComponents");
		componentPageManager.refreshLocal("moduleSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeModuleProvidersView() {
		setSectionType("module");
		setSectionName("Providers");
		setSectionTitle("Providers");
		setSectionIcon("/icons/nam/Provider16.gif");
		selectionContext.setMessageDomain("moduleProviders");
		providerPageManager.refreshLocal("moduleSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeModuleSummaryView(Module module) {
		//String viewTitle = getModuleLabel(module);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("module");
		setSectionName("Summary");
		setSectionTitle("Summary of Module Records");
		setSectionIcon("/icons/nam/Module16.gif");
		String viewLevelKey = "moduleSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Modules", "showModuleManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getModuleLabel(Module module) {
		ModuleType moduleType = module.getType();
		if (moduleType == null)
			return "Module";
		String label = moduleType.name() + " Module";
		String name = module.getLabel();
		if (name == null && module.getName() != null)
			name = ModuleUtil.getLabel(module);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}

	protected void updateState() {
		AbstractWizardPage<Module> page = moduleWizard.getPage();
		if (page != null)
			setSectionTitle("Module " + page.getName());
	}
	
	protected void updateState(Module module) {
		String moduleName = ModuleUtil.getLabel(module);
		setSectionTitle(moduleName + " Module");
	}

}
