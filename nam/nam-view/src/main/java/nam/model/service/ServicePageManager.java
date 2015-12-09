package nam.model.service;

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

import nam.model.Service;
import nam.model.component.ComponentPageManager;
import nam.model.domain.DomainPageManager;
import nam.model.listener.ListenerPageManager;
import nam.model.operation.OperationPageManager;
import nam.model.util.ServiceUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("servicePageManager")
public class ServicePageManager extends AbstractPageManager<Service> implements Serializable {
	
	@Inject
	private ServiceWizard serviceWizard;
	
	@Inject
	private ServiceDataManager serviceDataManager;

	@Inject
	private ServiceInfoManager serviceInfoManager;
	
	@Inject
	private ServiceListManager serviceListManager;

	@Inject
	private DomainPageManager domainPageManager;

	@Inject
	private ListenerPageManager listenerPageManager;
	
	@Inject
	private OperationPageManager operationPageManager;
	
	@Inject
	private ComponentPageManager componentPageManager;
	
	@Inject
	private ServiceRecord_OverviewSection serviceOverviewSection;
	
	@Inject
	private ServiceRecord_IdentificationSection serviceIdentificationSection;
	
	@Inject
	private ServiceRecord_ConfigurationSection serviceConfigurationSection;
	
	@Inject
	private ServiceRecord_DocumentationSection serviceDocumentationSection;
	
	@Inject
	private ServiceRecord_ListenersSection serviceListenersSection;
	
	@Inject
	private ServiceRecord_OperationsSection serviceOperationsSection;
	
	@Inject
	private ServiceRecord_ComponentsSection serviceComponentsSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ServicePageManager() {
		initializeSections();
	}

	
	public void refresh() {
		refresh("service");
	}

	public void refreshLocal() {
		refreshLocal("service");
	}

	public void refreshMembers() {
		refreshMembers("service");
	}

	public void refresh(String scope) {
		refreshLocal(scope);
		//refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		serviceDataManager.setScope(scope);
		serviceListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		listenerPageManager.refreshLocal(scope);
		operationPageManager.refreshLocal(scope);
		componentPageManager.refreshLocal(scope);
	}
	
	public String getServiceListPage() {
		return "/nam/model/service/serviceListPage.xhtml";
	}
	
	public String getServiceTreePage() {
		return "/nam/model/service/serviceTreePage.xhtml";
	}
	
	public String getServiceSummaryPage() {
		return "/nam/model/service/serviceSummaryPage.xhtml";
	}
	
	public String getServiceRecordPage() {
		return "/nam/model/service/serviceRecordPage.xhtml";
	}
	
	public String getServiceWizardPage() {
		return "/nam/model/service/serviceWizardPage.xhtml";
	}
	
	public String getServiceManagementPage() {
		return "/nam/model/service/serviceManagementPage.xhtml";
	}
	
	public void handleServiceSelected(@Observes @Selected Service service) {
		selectionContext.setSelection("service",  service);
		serviceInfoManager.setRecord(service);
	}
	
	public void handleServiceUnselected(@Observes @Unselected Service service) {
		selectionContext.unsetSelection("service",  service);
		serviceInfoManager.unsetRecord(service);
	}
	
	public void handleServiceChecked() {
		String scope = "serviceSelection";
		ServiceListObject listObject = serviceListManager.getSelection();
		Service service = selectionContext.getSelection("service");
		boolean checked = serviceListManager.getCheckedState();
		listObject.setChecked(checked);
		if (checked) {
			serviceInfoManager.setRecord(service);
			selectionContext.setSelection(scope,  service);
		} else {
			serviceInfoManager.unsetRecord(service);
			selectionContext.unsetSelection(scope,  service);
		}
		String target = selectionContext.getCurrentTarget();
		if (target.equals("listener"))
			listenerPageManager.refreshLocal(scope);
		if (target.equals("operation"))
			operationPageManager.refreshLocal(scope);
		if (target.equals("component"))
			componentPageManager.refreshLocal(scope);
		refreshLocal(scope);
	}
	
	public String initializeServiceListPage() {
		String pageLevelKey = "serviceList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Services", "showServiceManagementPage()");
		String url = getServiceListPage();
		selectionContext.setCurrentArea("service");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeServiceTreePage() {
		String pageLevelKey = "serviceTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Services", "showServiceTreePage()");
		String url = getServiceTreePage();
		selectionContext.setCurrentArea("service");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeServiceSummaryPage(Service service) {
		String pageLevelKey = "serviceSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Services", "showServiceSummaryPage()");
		String url = getServiceSummaryPage();
		selectionContext.setCurrentArea("service");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeServiceRecordPage() {
		Service service = selectionContext.getSelection("service");
		String serviceName = ServiceUtil.getLabel(service);
		
		String pageLevelKey = "serviceRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Services", "showServiceManagementPage()");
		addBreadcrumb(pageLevelKey, serviceName, "showServiceRecordPage()");
		String url = getServiceRecordPage();
		selectionContext.setCurrentArea("service");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeServiceCreationPage(Service service) {
		setPageTitle("New "+getServiceLabel(service));
		setPageIcon("/icons/nam/NewService16.gif");
		setSectionTitle("Service Identification");
		serviceWizard.setNewMode(true);
		
		String pageLevelKey = "service";
		String wizardLevelKey = "serviceWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Services", "showServiceManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Service", "showServiceWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Listeners", "showServiceWizardPage('Listeners')");
		addBreadcrumb(wizardLevelKey, "Operations", "showServiceWizardPage('Operations')");
		addBreadcrumb(wizardLevelKey, "Components", "showServiceWizardPage('Components')");
		
		serviceIdentificationSection.setOwner("serviceWizard");
		serviceConfigurationSection.setOwner("serviceWizard");
		serviceDocumentationSection.setOwner("serviceWizard");
		serviceListenersSection.setOwner("serviceWizard");
		serviceOperationsSection.setOwner("serviceWizard");
		serviceComponentsSection.setOwner("serviceWizard");
		
		sections.clear();
		sections.add(serviceIdentificationSection);
		sections.add(serviceConfigurationSection);
		sections.add(serviceDocumentationSection);
		sections.add(serviceListenersSection);
		sections.add(serviceOperationsSection);
		sections.add(serviceComponentsSection);
		
		String url = getServiceWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("service");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializeServiceUpdatePage(Service service) {
		setPageTitle(getServiceLabel(service));
		setPageIcon("/icons/nam/Service16.gif");
		setSectionTitle("Service Overview");
		String serviceName = ServiceUtil.getLabel(service);
		serviceWizard.setNewMode(false);
		
		String pageLevelKey = "service";
		String wizardLevelKey = "serviceWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Services", "showServiceManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(serviceName, "showServiceWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Listeners", "showServiceWizardPage('Listeners')");
		addBreadcrumb(wizardLevelKey, "Operations", "showServiceWizardPage('Operations')");
		addBreadcrumb(wizardLevelKey, "Components", "showServiceWizardPage('Components')");

		serviceOverviewSection.setOwner("serviceWizard");
		serviceIdentificationSection.setOwner("serviceWizard");
		serviceConfigurationSection.setOwner("serviceWizard");
		serviceDocumentationSection.setOwner("serviceWizard");
		serviceListenersSection.setOwner("serviceWizard");
		serviceOperationsSection.setOwner("serviceWizard");
		serviceComponentsSection.setOwner("serviceWizard");
		
		sections.clear();
		sections.add(serviceOverviewSection);
		sections.add(serviceIdentificationSection);
		sections.add(serviceConfigurationSection);
		sections.add(serviceDocumentationSection);
		sections.add(serviceListenersSection);
		sections.add(serviceOperationsSection);
		sections.add(serviceComponentsSection);
		
		String url = getServiceWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("service");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializeServiceManagementPage() {
//		Collection<Project> projectList = selectionContext.getSelection("projectList");
//		ListenerDataManager listenerDataManager = BeanContext.getFromSession("listenerDataManager");
//		listenerDataManager.setScope("projectList");
		String currentArea = selectionContext.getCurrentArea();
		refresh(currentArea);
		
		setPageTitle("Services");
		setPageIcon("/icons/nam/Service16.gif");
		String pageLevelKey = "serviceManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Services", "showServiceManagementPage()");
		String url = getServiceManagementPage();
		selectionContext.setCurrentArea("service");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public void initializeDefaultView() {
		setPageTitle("Services");
		setPageIcon("/icons/nam/Service16.gif");
		setSectionType("service");
		setSectionName("Overview");
		setSectionTitle("Overview of Services");
		setSectionIcon("/icons/nam/Overview16.gif");
		String viewLevelKey = "serviceOverview";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Services", "showServiceManagementPage()");
		String scope = "projectList";
		refreshLocal(scope);
		sections.clear();
	}

	public String initializeServiceDomainsView() {
		setSectionType("service");
		setSectionName("Domains");
		setSectionTitle("Domains");
		setSectionIcon("/icons/nam/Domain16.gif");
		selectionContext.setMessageDomain("serviceDomains");
		domainPageManager.refreshLocal("serviceSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeServiceListenersView() {
		setSectionType("service");
		setSectionName("Listeners");
		setSectionTitle("Listeners");
		setSectionIcon("/icons/nam/Listener16.gif");
		selectionContext.setMessageDomain("serviceListeners");
		listenerPageManager.refreshLocal("serviceSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeServiceOperationsView() {
		setSectionType("service");
		setSectionName("Operations");
		setSectionTitle("Operations");
		setSectionIcon("/icons/nam/Operation16.gif");
		selectionContext.setMessageDomain("serviceOperations");
		operationPageManager.refreshLocal("serviceSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}

	public String initializeServiceComponentsView() {
		setSectionType("service");
		setSectionName("Components");
		setSectionTitle("Components");
		setSectionIcon("/icons/nam/Component16.gif");
		selectionContext.setMessageDomain("serviceComponents");
		componentPageManager.refreshLocal("serviceSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}

	public String initializeServiceSummaryView(Service service) {
		//String viewTitle = getServiceLabel(service);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("service");
		setSectionName("Summary");
		setSectionTitle("Summary of Service Records");
		setSectionIcon("/icons/nam/Service16.gif");
		String viewLevelKey = "serviceSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Services", "showServiceManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getServiceLabel(Service service) {
		String label = "Service";
		String name = ServiceUtil.getLabel(service);
		if (name == null && service.getName() != null)
			name = ServiceUtil.getLabel(service);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Service> page = serviceWizard.getPage();
		if (page != null)
			setSectionTitle("Service " + page.getName());
	}

	protected void updateState(Service service) {
		String serviceName = ServiceUtil.getLabel(service);
		setSectionTitle(serviceName + " Service"); 
	}

}
