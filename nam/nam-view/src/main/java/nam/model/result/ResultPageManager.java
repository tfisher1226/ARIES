package nam.model.result;

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

import nam.model.Result;
import nam.model.util.ResultUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("resultPageManager")
public class ResultPageManager extends AbstractPageManager<Result> implements Serializable {
	
	@Inject
	private ResultWizard resultWizard;
	
	@Inject
	private ResultDataManager resultDataManager;
	
	@Inject
	private ResultInfoManager resultInfoManager;
	
	@Inject
	private ResultListManager resultListManager;
	
	@Inject
	private ResultRecord_OverviewSection resultOverviewSection;
	
	@Inject
	private ResultRecord_IdentificationSection resultIdentificationSection;
	
	@Inject
	private ResultRecord_ConfigurationSection resultConfigurationSection;
	
	@Inject
	private ResultRecord_DocumentationSection resultDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ResultPageManager() {
		initializeSections();
	}
	
	
	public void refresh() {
		refresh("result");
	}
	
	public void refreshLocal() {
		refreshLocal("result");
	}
	
	public void refreshMembers() {
		refreshMembers("result");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		//refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		resultDataManager.setScope(scope);
		resultListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		//nothing for now
	}
	
	public String getResultListPage() {
		return "/nam/model/result/resultListPage.xhtml";
	}
	
	public String getResultTreePage() {
		return "/nam/model/result/resultTreePage.xhtml";
	}
	
	public String getResultSummaryPage() {
		return "/nam/model/result/resultSummaryPage.xhtml";
	}
	
	public String getResultRecordPage() {
		return "/nam/model/result/resultRecordPage.xhtml";
	}
	
	public String getResultWizardPage() {
		return "/nam/model/result/resultWizardPage.xhtml";
	}
	
	public String getResultManagementPage() {
		return "/nam/model/result/resultManagementPage.xhtml";
	}
	
	public void handleResultSelected(@Observes @Selected Result result) {
		selectionContext.setSelection("result",  result);
		resultInfoManager.setRecord(result);
	}
	
	public void handleResultUnselected(@Observes @Unselected Result result) {
		selectionContext.unsetSelection("result",  result);
		resultInfoManager.unsetRecord(result);
	}
	
	public void handleResultChecked() {
		String scope = "resultSelection";
		ResultListObject listObject = resultListManager.getSelection();
		Result result = selectionContext.getSelection("result");
		boolean checked = resultListManager.getCheckedState();
		listObject.setChecked(checked);
		if (checked) {
			resultInfoManager.setRecord(result);
			selectionContext.setSelection(scope,  result);
		} else {
			resultInfoManager.unsetRecord(result);
			selectionContext.unsetSelection(scope,  result);
		}
		refreshLocal(scope);
	}
	
	public String initializeResultListPage() {
		String pageLevelKey = "resultList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Results", "showResultManagementPage()");
		String url = getResultListPage();
		selectionContext.setCurrentArea("result");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeResultTreePage() {
		String pageLevelKey = "resultTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Results", "showResultTreePage()");
		String url = getResultTreePage();
		selectionContext.setCurrentArea("result");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeResultSummaryPage(Result result) {
		String pageLevelKey = "resultSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Results", "showResultSummaryPage()");
		String url = getResultSummaryPage();
		selectionContext.setCurrentArea("result");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeResultRecordPage() {
		Result result = selectionContext.getSelection("result");
		String resultName = ResultUtil.getLabel(result);
		
		String pageLevelKey = "resultRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Results", "showResultManagementPage()");
		addBreadcrumb(pageLevelKey, resultName, "showResultRecordPage()");
		String url = getResultRecordPage();
		selectionContext.setCurrentArea("result");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeResultCreationPage(Result result) {
		setPageTitle("New "+getResultLabel(result));
		setPageIcon("/icons/nam/NewResult16.gif");
		setSectionTitle("Result Identification");
		resultWizard.setNewMode(true);
		
		String pageLevelKey = "result";
		String wizardLevelKey = "resultWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Results", "showResultManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Result", "showResultWizardPage()"));
		
		
		resultIdentificationSection.setOwner("resultWizard");
		resultConfigurationSection.setOwner("resultWizard");
		resultDocumentationSection.setOwner("resultWizard");
		
		sections.clear();
		sections.add(resultIdentificationSection);
		sections.add(resultConfigurationSection);
		sections.add(resultDocumentationSection);
		
		String url = getResultWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("result");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializeResultUpdatePage(Result result) {
		setPageTitle(getResultLabel(result));
		setPageIcon("/icons/nam/Result16.gif");
		setSectionTitle("Result Overview");
		String resultName = ResultUtil.getLabel(result);
		resultWizard.setNewMode(false);
		
		String pageLevelKey = "result";
		String wizardLevelKey = "resultWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Results", "showResultManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(resultName, "showResultWizardPage()"));
		
		
		resultOverviewSection.setOwner("resultWizard");
		resultIdentificationSection.setOwner("resultWizard");
		resultConfigurationSection.setOwner("resultWizard");
		resultDocumentationSection.setOwner("resultWizard");
		
		sections.clear();
		sections.add(resultOverviewSection);
		sections.add(resultIdentificationSection);
		sections.add(resultConfigurationSection);
		sections.add(resultDocumentationSection);
		
		String url = getResultWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("result");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializeResultManagementPage() {
		setPageTitle("Results");
		setPageIcon("/icons/nam/Result16.gif");
		String pageLevelKey = "resultManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Results", "showResultManagementPage()");
		String url = getResultManagementPage();
		selectionContext.setCurrentArea("result");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public void initializeDefaultView() {
		setPageTitle("Results");
		setPageIcon("/icons/nam/Result16.gif");
		setSectionType("result");
		setSectionName("Overview");
		setSectionTitle("Overview of Results");
		setSectionIcon("/icons/nam/Overview16.gif");
		String viewLevelKey = "resultOverview";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Results", "showResultManagementPage()");
		String scope = "projectList";
		refreshLocal(scope);
		sections.clear();
	}
	
	public String initializeResultSummaryView(Result result) {
		//String viewTitle = getResultLabel(result);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("result");
		setSectionName("Summary");
		setSectionTitle("Summary of Result Records");
		setSectionIcon("/icons/nam/Result16.gif");
		String viewLevelKey = "resultSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Results", "showResultManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getResultLabel(Result result) {
		String label = "Result";
		String name = ResultUtil.getLabel(result);
		if (name == null && result.getName() != null)
			name = ResultUtil.getLabel(result);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}

	protected void updateState() {
		AbstractWizardPage<Result> page = resultWizard.getPage();
		if (page != null)
			setSectionTitle("Result " + page.getName());
	}

	protected void updateState(Result result) {
		String resultName = ResultUtil.getLabel(result);
		setSectionTitle(resultName + " Result");
	}
	
}
