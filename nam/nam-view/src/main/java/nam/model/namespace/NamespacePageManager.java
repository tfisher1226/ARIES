package nam.model.namespace;

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

import nam.model.Namespace;
import nam.model.util.NamespaceUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("namespacePageManager")
public class NamespacePageManager extends AbstractPageManager<Namespace> implements Serializable {
	
	@Inject
	private NamespaceWizard namespaceWizard;
	
	@Inject
	private NamespaceDataManager namespaceDataManager;
	
	@Inject
	private NamespaceListManager namespaceListManager;
	
	@Inject
	private NamespaceRecord_OverviewSection namespaceOverviewSection;
	
	@Inject
	private NamespaceRecord_IdentificationSection namespaceIdentificationSection;
	
	@Inject
	private NamespaceRecord_ConfigurationSection namespaceConfigurationSection;
	
	@Inject
	private NamespaceRecord_DocumentationSection namespaceDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public NamespacePageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("namespace");
	}
	
	public void refreshLocal() {
		refreshLocal("namespace");
	}
	
	public void refreshMembers() {
		refreshMembers("namespace");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		namespaceDataManager.setScope(scope);
		namespaceListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		namespaceListManager.refresh();
	}
	
	public String getNamespaceListPage() {
		return "/nam/model/namespace/namespaceListPage.xhtml";
	}
	
	public String getNamespaceTreePage() {
		return "/nam/model/namespace/namespaceTreePage.xhtml";
	}
	
	public String getNamespaceSummaryPage() {
		return "/nam/model/namespace/namespaceSummaryPage.xhtml";
	}
	
	public String getNamespaceRecordPage() {
		return "/nam/model/namespace/namespaceRecordPage.xhtml";
	}
	
	public String getNamespaceWizardPage() {
		return "/nam/model/namespace/namespaceWizardPage.xhtml";
	}
	
	public String getNamespaceManagementPage() {
		return "/nam/model/namespace/namespaceManagementPage.xhtml";
	}
	
	public String initializeNamespaceListPage() {
		String pageLevelKey = "namespaceList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Namespaces", "showNamespaceManagementPage()");
		String url = getNamespaceListPage();
		selectionContext.setCurrentArea("namespace");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeNamespaceTreePage() {
		String pageLevelKey = "namespaceTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Namespaces", "showNamespaceTreePage()");
		String url = getNamespaceTreePage();
		selectionContext.setCurrentArea("namespace");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeNamespaceSummaryPage(Namespace namespace) {
		String pageLevelKey = "namespaceSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Namespaces", "showNamespaceSummaryPage()");
		String url = getNamespaceSummaryPage();
		selectionContext.setCurrentArea("namespace");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeNamespaceRecordPage() {
		Namespace namespace = selectionContext.getSelection("namespace");
		String namespaceName = NamespaceUtil.getLabel(namespace);
		
		String pageLevelKey = "namespaceRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Namespaces", "showNamespaceManagementPage()");
		addBreadcrumb(pageLevelKey, namespaceName, "showNamespaceRecordPage()");
		String url = getNamespaceRecordPage();
		selectionContext.setCurrentArea("namespace");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeNamespaceCreationPage(Namespace namespace) {
		setPageTitle("New "+getNamespaceLabel(namespace));
		setPageIcon("/icons/nam/NewNamespace16.gif");
		setSectionTitle("Namespace Identification");
		namespaceWizard.setNewMode(true);
		
		String pageLevelKey = "namespace";
		String wizardLevelKey = "namespaceWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Namespaces", "showNamespaceManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Namespace", "showNamespaceWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showNamespaceWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showNamespaceWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showNamespaceWizardPage('Documentation')");
		
		namespaceIdentificationSection.setOwner("namespaceWizard");
		namespaceConfigurationSection.setOwner("namespaceWizard");
		namespaceDocumentationSection.setOwner("namespaceWizard");
		
		sections.clear();
		sections.add(namespaceIdentificationSection);
		sections.add(namespaceConfigurationSection);
		sections.add(namespaceDocumentationSection);
		
		String url = getNamespaceWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("namespace");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeNamespaceUpdatePage(Namespace namespace) {
		setPageTitle(getNamespaceLabel(namespace));
		setPageIcon("/icons/nam/Namespace16.gif");
		setSectionTitle("Namespace Overview");
		String namespaceName = NamespaceUtil.getLabel(namespace);
		namespaceWizard.setNewMode(false);
		
		String pageLevelKey = "namespace";
		String wizardLevelKey = "namespaceWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Namespaces", "showNamespaceManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(namespaceName, "showNamespaceWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showNamespaceWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showNamespaceWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showNamespaceWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showNamespaceWizardPage('Documentation')");
		
		namespaceOverviewSection.setOwner("namespaceWizard");
		namespaceIdentificationSection.setOwner("namespaceWizard");
		namespaceConfigurationSection.setOwner("namespaceWizard");
		namespaceDocumentationSection.setOwner("namespaceWizard");
		
		sections.clear();
		sections.add(namespaceOverviewSection);
		sections.add(namespaceIdentificationSection);
		sections.add(namespaceConfigurationSection);
		sections.add(namespaceDocumentationSection);
		
		String url = getNamespaceWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("namespace");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeNamespaceManagementPage() {
		setPageTitle("Namespaces");
		setPageIcon("/icons/nam/Namespace16.gif");
		String pageLevelKey = "namespaceManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Namespaces", "showNamespaceManagementPage()");
		String url = getNamespaceManagementPage();
		selectionContext.setCurrentArea("namespace");
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
		setSectionType("namespace");
		setSectionName("Overview");
		setSectionTitle("Overview of Namespaces");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeNamespaceSummaryView(Namespace namespace) {
		//String viewTitle = getNamespaceLabel(namespace);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("namespace");
		setSectionName("Summary");
		setSectionTitle("Summary of Namespace Records");
		setSectionIcon("/icons/nam/Namespace16.gif");
		String viewLevelKey = "namespaceSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Namespaces", "showNamespaceManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getNamespaceLabel(Namespace namespace) {
		String label = "Namespace";
		String name = NamespaceUtil.getLabel(namespace);
		if (name == null && namespace.getName() != null)
			name = NamespaceUtil.getLabel(namespace);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Namespace> page = namespaceWizard.getPage();
		if (page != null)
			setSectionTitle("Namespace " + page.getName());
	}
	
	protected void updateState(Namespace namespace) {
		String namespaceName = NamespaceUtil.getLabel(namespace);
		setSectionTitle(namespaceName + " Namespace");
	}
	
}
