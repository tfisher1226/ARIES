package nam.model.source;

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

import nam.model.Source;
import nam.model.util.SourceUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("sourcePageManager")
public class SourcePageManager extends AbstractPageManager<Source> implements Serializable {
	
	@Inject
	private SourceWizard sourceWizard;
	
	@Inject
	private SourceDataManager sourceDataManager;
	
	@Inject
	private SourceListManager sourceListManager;
	
	@Inject
	private SourceRecord_OverviewSection sourceOverviewSection;
	
	@Inject
	private SourceRecord_IdentificationSection sourceIdentificationSection;
	
	@Inject
	private SourceRecord_ConfigurationSection sourceConfigurationSection;
	
	@Inject
	private SourceRecord_DocumentationSection sourceDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public SourcePageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("source");
	}
	
	public void refreshLocal() {
		refreshLocal("source");
	}
	
	public void refreshMembers() {
		refreshMembers("source");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		sourceDataManager.setScope(scope);
		sourceListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		sourceListManager.refresh();
	}
	
	public String getSourceListPage() {
		return "/nam/model/source/sourceListPage.xhtml";
	}
	
	public String getSourceTreePage() {
		return "/nam/model/source/sourceTreePage.xhtml";
	}
	
	public String getSourceSummaryPage() {
		return "/nam/model/source/sourceSummaryPage.xhtml";
	}
	
	public String getSourceRecordPage() {
		return "/nam/model/source/sourceRecordPage.xhtml";
	}
	
	public String getSourceWizardPage() {
		return "/nam/model/source/sourceWizardPage.xhtml";
	}
	
	public String getSourceManagementPage() {
		return "/nam/model/source/sourceManagementPage.xhtml";
	}
	
	public String initializeSourceListPage() {
		String pageLevelKey = "sourceList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Sources", "showSourceManagementPage()");
		String url = getSourceListPage();
		selectionContext.setCurrentArea("source");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeSourceTreePage() {
		String pageLevelKey = "sourceTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Sources", "showSourceTreePage()");
		String url = getSourceTreePage();
		selectionContext.setCurrentArea("source");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeSourceSummaryPage(Source source) {
		String pageLevelKey = "sourceSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Sources", "showSourceSummaryPage()");
		String url = getSourceSummaryPage();
		selectionContext.setCurrentArea("source");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeSourceRecordPage() {
		Source source = selectionContext.getSelection("source");
		String sourceName = SourceUtil.getLabel(source);
		
		String pageLevelKey = "sourceRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Sources", "showSourceManagementPage()");
		addBreadcrumb(pageLevelKey, sourceName, "showSourceRecordPage()");
		String url = getSourceRecordPage();
		selectionContext.setCurrentArea("source");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeSourceCreationPage(Source source) {
		setPageTitle("New "+getSourceLabel(source));
		setPageIcon("/icons/nam/NewSource16.gif");
		setSectionTitle("Source Identification");
		sourceWizard.setNewMode(true);
		
		String pageLevelKey = "source";
		String wizardLevelKey = "sourceWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Sources", "showSourceManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Source", "showSourceWizardPage()"));
		
		
		sourceIdentificationSection.setOwner("sourceWizard");
		sourceConfigurationSection.setOwner("sourceWizard");
		sourceDocumentationSection.setOwner("sourceWizard");
		
		sections.clear();
		sections.add(sourceIdentificationSection);
		sections.add(sourceConfigurationSection);
		sections.add(sourceDocumentationSection);
		
		String url = getSourceWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("source");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeSourceUpdatePage(Source source) {
		setPageTitle(getSourceLabel(source));
		setPageIcon("/icons/nam/Source16.gif");
		setSectionTitle("Source Overview");
		String sourceName = SourceUtil.getLabel(source);
		sourceWizard.setNewMode(false);
		
		String pageLevelKey = "source";
		String wizardLevelKey = "sourceWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Sources", "showSourceManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(sourceName, "showSourceWizardPage()"));
		
		
		sourceOverviewSection.setOwner("sourceWizard");
		sourceIdentificationSection.setOwner("sourceWizard");
		sourceConfigurationSection.setOwner("sourceWizard");
		sourceDocumentationSection.setOwner("sourceWizard");
		
		sections.clear();
		sections.add(sourceOverviewSection);
		sections.add(sourceIdentificationSection);
		sections.add(sourceConfigurationSection);
		sections.add(sourceDocumentationSection);
		
		String url = getSourceWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("source");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeSourceManagementPage() {
		setPageTitle("Sources");
		setPageIcon("/icons/nam/Source16.gif");
		String pageLevelKey = "sourceManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Sources", "showSourceManagementPage()");
		String url = getSourceManagementPage();
		selectionContext.setCurrentArea("source");
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
		setSectionType("source");
		setSectionName("Overview");
		setSectionTitle("Overview of Sources");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeSourceSummaryView(Source source) {
		//String viewTitle = getSourceLabel(source);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("source");
		setSectionName("Summary");
		setSectionTitle("Summary of Source Records");
		setSectionIcon("/icons/nam/Source16.gif");
		String viewLevelKey = "sourceSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Sources", "showSourceManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getSourceLabel(Source source) {
		String label = "Source";
		String name = SourceUtil.getLabel(source);
		if (name == null && source.getName() != null)
			name = SourceUtil.getLabel(source);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Source> page = sourceWizard.getPage();
		if (page != null)
			setSectionTitle("Source " + page.getName());
	}
	
	protected void updateState(Source source) {
		String sourceName = SourceUtil.getLabel(source);
		setSectionTitle(sourceName + " Source");
	}
	
}
