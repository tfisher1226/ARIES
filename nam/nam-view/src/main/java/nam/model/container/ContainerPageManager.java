package nam.model.container;

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

import nam.model.Container;
import nam.model.util.ContainerUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("containerPageManager")
public class ContainerPageManager extends AbstractPageManager<Container> implements Serializable {
	
	@Inject
	private ContainerWizard containerWizard;
	
	@Inject
	private ContainerDataManager containerDataManager;
	
	@Inject
	private ContainerListManager containerListManager;
	
	@Inject
	private ContainerRecord_OverviewSection containerOverviewSection;
	
	@Inject
	private ContainerRecord_IdentificationSection containerIdentificationSection;
	
	@Inject
	private ContainerRecord_ConfigurationSection containerConfigurationSection;
	
	@Inject
	private ContainerRecord_DocumentationSection containerDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ContainerPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("container");
	}
	
	public void refreshLocal() {
		refreshLocal("container");
	}
	
	public void refreshMembers() {
		refreshMembers("container");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		containerDataManager.setScope(scope);
		containerListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		containerListManager.refresh();
	}
	
	public String getContainerListPage() {
		return "/nam/model/container/containerListPage.xhtml";
	}
	
	public String getContainerTreePage() {
		return "/nam/model/container/containerTreePage.xhtml";
	}
	
	public String getContainerSummaryPage() {
		return "/nam/model/container/containerSummaryPage.xhtml";
	}
	
	public String getContainerRecordPage() {
		return "/nam/model/container/containerRecordPage.xhtml";
	}
	
	public String getContainerWizardPage() {
		return "/nam/model/container/containerWizardPage.xhtml";
	}
	
	public String getContainerManagementPage() {
		return "/nam/model/container/containerManagementPage.xhtml";
	}
	
	public String initializeContainerListPage() {
		String pageLevelKey = "containerList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Containers", "showContainerManagementPage()");
		String url = getContainerListPage();
		selectionContext.setCurrentArea("container");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeContainerTreePage() {
		String pageLevelKey = "containerTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Containers", "showContainerTreePage()");
		String url = getContainerTreePage();
		selectionContext.setCurrentArea("container");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeContainerSummaryPage(Container container) {
		String pageLevelKey = "containerSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Containers", "showContainerSummaryPage()");
		String url = getContainerSummaryPage();
		selectionContext.setCurrentArea("container");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeContainerRecordPage() {
		Container container = selectionContext.getSelection("container");
		String containerName = ContainerUtil.getLabel(container);
		
		String pageLevelKey = "containerRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Containers", "showContainerManagementPage()");
		addBreadcrumb(pageLevelKey, containerName, "showContainerRecordPage()");
		String url = getContainerRecordPage();
		selectionContext.setCurrentArea("container");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeContainerCreationPage(Container container) {
		setPageTitle("New "+getContainerLabel(container));
		setPageIcon("/icons/nam/NewContainer16.gif");
		setSectionTitle("Container Identification");
		containerWizard.setNewMode(true);
		
		String pageLevelKey = "container";
		String wizardLevelKey = "containerWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Containers", "showContainerManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Container", "showContainerWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showContainerWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showContainerWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showContainerWizardPage('Documentation')");
		
		containerIdentificationSection.setOwner("containerWizard");
		containerConfigurationSection.setOwner("containerWizard");
		containerDocumentationSection.setOwner("containerWizard");
		
		sections.clear();
		sections.add(containerIdentificationSection);
		sections.add(containerConfigurationSection);
		sections.add(containerDocumentationSection);
		
		String url = getContainerWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("container");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeContainerUpdatePage(Container container) {
		setPageTitle(getContainerLabel(container));
		setPageIcon("/icons/nam/Container16.gif");
		setSectionTitle("Container Overview");
		String containerName = ContainerUtil.getLabel(container);
		containerWizard.setNewMode(false);
		
		String pageLevelKey = "container";
		String wizardLevelKey = "containerWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Containers", "showContainerManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(containerName, "showContainerWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showContainerWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showContainerWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showContainerWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showContainerWizardPage('Documentation')");
		
		containerOverviewSection.setOwner("containerWizard");
		containerIdentificationSection.setOwner("containerWizard");
		containerConfigurationSection.setOwner("containerWizard");
		containerDocumentationSection.setOwner("containerWizard");
		
		sections.clear();
		sections.add(containerOverviewSection);
		sections.add(containerIdentificationSection);
		sections.add(containerConfigurationSection);
		sections.add(containerDocumentationSection);
		
		String url = getContainerWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("container");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeContainerManagementPage() {
		setPageTitle("Containers");
		setPageIcon("/icons/nam/Container16.gif");
		String pageLevelKey = "containerManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Containers", "showContainerManagementPage()");
		String url = getContainerManagementPage();
		selectionContext.setCurrentArea("container");
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
		setSectionType("container");
		setSectionName("Overview");
		setSectionTitle("Overview of Containers");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeContainerSummaryView(Container container) {
		//String viewTitle = getContainerLabel(container);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("container");
		setSectionName("Summary");
		setSectionTitle("Summary of Container Records");
		setSectionIcon("/icons/nam/Container16.gif");
		String viewLevelKey = "containerSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Containers", "showContainerManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getContainerLabel(Container container) {
		String label = "Container";
		String name = ContainerUtil.getLabel(container);
		if (name == null && container.getName() != null)
			name = ContainerUtil.getLabel(container);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Container> page = containerWizard.getPage();
		if (page != null)
			setSectionTitle("Container " + page.getName());
	}
	
	protected void updateState(Container container) {
		String containerName = ContainerUtil.getLabel(container);
		setSectionTitle(containerName + " Container");
	}
	
}
