package nam.model.connectionPool;

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

import nam.model.ConnectionPool;
import nam.model.util.ConnectionPoolUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("connectionPoolPageManager")
public class ConnectionPoolPageManager extends AbstractPageManager<ConnectionPool> implements Serializable {
	
	@Inject
	private ConnectionPoolWizard connectionPoolWizard;
	
	@Inject
	private ConnectionPoolDataManager connectionPoolDataManager;
	
	@Inject
	private ConnectionPoolListManager connectionPoolListManager;
	
	@Inject
	private ConnectionPoolRecord_OverviewSection connectionPoolOverviewSection;
	
	@Inject
	private ConnectionPoolRecord_IdentificationSection connectionPoolIdentificationSection;
	
	@Inject
	private ConnectionPoolRecord_ConfigurationSection connectionPoolConfigurationSection;
	
	@Inject
	private ConnectionPoolRecord_DocumentationSection connectionPoolDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ConnectionPoolPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("connectionPool");
	}
	
	public void refreshLocal() {
		refreshLocal("connectionPool");
	}
	
	public void refreshMembers() {
		refreshMembers("connectionPool");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		connectionPoolDataManager.setScope(scope);
		connectionPoolListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		connectionPoolListManager.refresh();
	}
	
	public String getConnectionPoolListPage() {
		return "/nam/model/connectionPool/connectionPoolListPage.xhtml";
	}
	
	public String getConnectionPoolTreePage() {
		return "/nam/model/connectionPool/connectionPoolTreePage.xhtml";
	}
	
	public String getConnectionPoolSummaryPage() {
		return "/nam/model/connectionPool/connectionPoolSummaryPage.xhtml";
	}
	
	public String getConnectionPoolRecordPage() {
		return "/nam/model/connectionPool/connectionPoolRecordPage.xhtml";
	}
	
	public String getConnectionPoolWizardPage() {
		return "/nam/model/connectionPool/connectionPoolWizardPage.xhtml";
	}
	
	public String getConnectionPoolManagementPage() {
		return "/nam/model/connectionPool/connectionPoolManagementPage.xhtml";
	}
	
	public String initializeConnectionPoolListPage() {
		String pageLevelKey = "connectionPoolList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "ConnectionPools", "showConnectionPoolManagementPage()");
		String url = getConnectionPoolListPage();
		selectionContext.setCurrentArea("connectionPool");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeConnectionPoolTreePage() {
		String pageLevelKey = "connectionPoolTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "ConnectionPools", "showConnectionPoolTreePage()");
		String url = getConnectionPoolTreePage();
		selectionContext.setCurrentArea("connectionPool");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeConnectionPoolSummaryPage(ConnectionPool connectionPool) {
		String pageLevelKey = "connectionPoolSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "ConnectionPools", "showConnectionPoolSummaryPage()");
		String url = getConnectionPoolSummaryPage();
		selectionContext.setCurrentArea("connectionPool");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeConnectionPoolRecordPage() {
		ConnectionPool connectionPool = selectionContext.getSelection("connectionPool");
		String connectionPoolName = ConnectionPoolUtil.getLabel(connectionPool);
		
		String pageLevelKey = "connectionPoolRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "ConnectionPools", "showConnectionPoolManagementPage()");
		addBreadcrumb(pageLevelKey, connectionPoolName, "showConnectionPoolRecordPage()");
		String url = getConnectionPoolRecordPage();
		selectionContext.setCurrentArea("connectionPool");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeConnectionPoolCreationPage(ConnectionPool connectionPool) {
		setPageTitle("New "+getConnectionPoolLabel(connectionPool));
		setPageIcon("/icons/nam/NewConnectionPool16.gif");
		setSectionTitle("ConnectionPool Identification");
		connectionPoolWizard.setNewMode(true);
		
		String pageLevelKey = "connectionPool";
		String wizardLevelKey = "connectionPoolWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "ConnectionPools", "showConnectionPoolManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New ConnectionPool", "showConnectionPoolWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showConnectionPoolWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showConnectionPoolWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showConnectionPoolWizardPage('Documentation')");
		
		connectionPoolIdentificationSection.setOwner("connectionPoolWizard");
		connectionPoolConfigurationSection.setOwner("connectionPoolWizard");
		connectionPoolDocumentationSection.setOwner("connectionPoolWizard");
		
		sections.clear();
		sections.add(connectionPoolIdentificationSection);
		sections.add(connectionPoolConfigurationSection);
		sections.add(connectionPoolDocumentationSection);
		
		String url = getConnectionPoolWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("connectionPool");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeConnectionPoolUpdatePage(ConnectionPool connectionPool) {
		setPageTitle(getConnectionPoolLabel(connectionPool));
		setPageIcon("/icons/nam/ConnectionPool16.gif");
		setSectionTitle("ConnectionPool Overview");
		String connectionPoolName = ConnectionPoolUtil.getLabel(connectionPool);
		connectionPoolWizard.setNewMode(false);
		
		String pageLevelKey = "connectionPool";
		String wizardLevelKey = "connectionPoolWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "ConnectionPools", "showConnectionPoolManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(connectionPoolName, "showConnectionPoolWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showConnectionPoolWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showConnectionPoolWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showConnectionPoolWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showConnectionPoolWizardPage('Documentation')");
		
		connectionPoolOverviewSection.setOwner("connectionPoolWizard");
		connectionPoolIdentificationSection.setOwner("connectionPoolWizard");
		connectionPoolConfigurationSection.setOwner("connectionPoolWizard");
		connectionPoolDocumentationSection.setOwner("connectionPoolWizard");
		
		sections.clear();
		sections.add(connectionPoolOverviewSection);
		sections.add(connectionPoolIdentificationSection);
		sections.add(connectionPoolConfigurationSection);
		sections.add(connectionPoolDocumentationSection);
		
		String url = getConnectionPoolWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("connectionPool");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeConnectionPoolManagementPage() {
		setPageTitle("ConnectionPools");
		setPageIcon("/icons/nam/ConnectionPool16.gif");
		String pageLevelKey = "connectionPoolManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "ConnectionPools", "showConnectionPoolManagementPage()");
		String url = getConnectionPoolManagementPage();
		selectionContext.setCurrentArea("connectionPool");
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
		setSectionType("connectionPool");
		setSectionName("Overview");
		setSectionTitle("Overview of ConnectionPools");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeConnectionPoolSummaryView(ConnectionPool connectionPool) {
		//String viewTitle = getConnectionPoolLabel(connectionPool);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("connectionPool");
		setSectionName("Summary");
		setSectionTitle("Summary of ConnectionPool Records");
		setSectionIcon("/icons/nam/ConnectionPool16.gif");
		String viewLevelKey = "connectionPoolSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "ConnectionPools", "showConnectionPoolManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getConnectionPoolLabel(ConnectionPool connectionPool) {
		String label = "ConnectionPool";
		String name = ConnectionPoolUtil.getLabel(connectionPool);
		if (name == null && connectionPool.getName() != null)
			name = ConnectionPoolUtil.getLabel(connectionPool);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<ConnectionPool> page = connectionPoolWizard.getPage();
		if (page != null)
			setSectionTitle("ConnectionPool " + page.getName());
	}
	
	protected void updateState(ConnectionPool connectionPool) {
		String connectionPoolName = ConnectionPoolUtil.getLabel(connectionPool);
		setSectionTitle(connectionPoolName + " ConnectionPool");
	}
	
}
