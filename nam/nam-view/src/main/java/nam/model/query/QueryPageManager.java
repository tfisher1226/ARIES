package nam.model.query;

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

import nam.model.Query;
import nam.model.util.QueryUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("queryPageManager")
public class QueryPageManager extends AbstractPageManager<Query> implements Serializable {
	
	@Inject
	private QueryWizard queryWizard;
	
	@Inject
	private QueryDataManager queryDataManager;
	
	@Inject
	private QueryListManager queryListManager;
	
	@Inject
	private QueryRecord_OverviewSection queryOverviewSection;
	
	@Inject
	private QueryRecord_IdentificationSection queryIdentificationSection;
	
	@Inject
	private QueryRecord_ConfigurationSection queryConfigurationSection;
	
	@Inject
	private QueryRecord_DocumentationSection queryDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public QueryPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("query");
	}
	
	public void refreshLocal() {
		refreshLocal("query");
	}
	
	public void refreshMembers() {
		refreshMembers("query");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		queryDataManager.setScope(scope);
		queryListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		queryListManager.refresh();
	}
	
	public String getQueryListPage() {
		return "/nam/model/query/queryListPage.xhtml";
	}
	
	public String getQueryTreePage() {
		return "/nam/model/query/queryTreePage.xhtml";
	}
	
	public String getQuerySummaryPage() {
		return "/nam/model/query/querySummaryPage.xhtml";
	}
	
	public String getQueryRecordPage() {
		return "/nam/model/query/queryRecordPage.xhtml";
	}
	
	public String getQueryWizardPage() {
		return "/nam/model/query/queryWizardPage.xhtml";
	}
	
	public String getQueryManagementPage() {
		return "/nam/model/query/queryManagementPage.xhtml";
	}
	
	public String initializeQueryListPage() {
		String pageLevelKey = "queryList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Querys", "showQueryManagementPage()");
		String url = getQueryListPage();
		selectionContext.setCurrentArea("query");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeQueryTreePage() {
		String pageLevelKey = "queryTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Querys", "showQueryTreePage()");
		String url = getQueryTreePage();
		selectionContext.setCurrentArea("query");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeQuerySummaryPage(Query query) {
		String pageLevelKey = "querySummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Querys", "showQuerySummaryPage()");
		String url = getQuerySummaryPage();
		selectionContext.setCurrentArea("query");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeQueryRecordPage() {
		Query query = selectionContext.getSelection("query");
		String queryName = query.getName();
		
		String pageLevelKey = "queryRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Querys", "showQueryManagementPage()");
		addBreadcrumb(pageLevelKey, queryName, "showQueryRecordPage()");
		String url = getQueryRecordPage();
		selectionContext.setCurrentArea("query");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeQueryCreationPage(Query query) {
		setPageTitle("New "+getQueryLabel(query));
		setPageIcon("/icons/nam/NewQuery16.gif");
		setSectionTitle("Query Identification");
		queryWizard.setNewMode(true);
		
		String pageLevelKey = "query";
		String wizardLevelKey = "queryWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Querys", "showQueryManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Query", "showQueryWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showQueryWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showQueryWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showQueryWizardPage('Documentation')");
		
		queryIdentificationSection.setOwner("queryWizard");
		queryConfigurationSection.setOwner("queryWizard");
		queryDocumentationSection.setOwner("queryWizard");
		
		sections.clear();
		sections.add(queryIdentificationSection);
		sections.add(queryConfigurationSection);
		sections.add(queryDocumentationSection);
		
		String url = getQueryWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("query");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeQueryUpdatePage(Query query) {
		setPageTitle(getQueryLabel(query));
		setPageIcon("/icons/nam/Query16.gif");
		setSectionTitle("Query Overview");
		String queryName = query.getName();
		queryWizard.setNewMode(false);
		
		String pageLevelKey = "query";
		String wizardLevelKey = "queryWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Querys", "showQueryManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(queryName, "showQueryWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showQueryWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showQueryWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showQueryWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showQueryWizardPage('Documentation')");
		
		queryOverviewSection.setOwner("queryWizard");
		queryIdentificationSection.setOwner("queryWizard");
		queryConfigurationSection.setOwner("queryWizard");
		queryDocumentationSection.setOwner("queryWizard");
		
		sections.clear();
		sections.add(queryOverviewSection);
		sections.add(queryIdentificationSection);
		sections.add(queryConfigurationSection);
		sections.add(queryDocumentationSection);
		
		String url = getQueryWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("query");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeQueryManagementPage() {
		setPageTitle("Querys");
		setPageIcon("/icons/nam/Query16.gif");
		String pageLevelKey = "queryManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Querys", "showQueryManagementPage()");
		String url = getQueryManagementPage();
		selectionContext.setCurrentArea("query");
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
		setSectionType("query");
		setSectionName("Overview");
		setSectionTitle("Overview of Querys");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeQuerySummaryView(Query query) {
		//String viewTitle = getQueryLabel(query);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("query");
		setSectionName("Summary");
		setSectionTitle("Summary of Query Records");
		setSectionIcon("/icons/nam/Query16.gif");
		String viewLevelKey = "querySummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Querys", "showQueryManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getQueryLabel(Query query) {
		String label = "Query";
		String name = QueryUtil.getLabel(query);
		if (name == null && query.getName() != null)
			name = NameUtil.capName(query.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Query> page = queryWizard.getPage();
		if (page != null)
			setSectionTitle("Query " + page.getName());
	}
	
	protected void updateState(Query query) {
		String queryName = NameUtil.capName(query.getName());
		setSectionTitle(queryName + " Query");
	}
	
}
