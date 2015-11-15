package nam.model.literal;

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

import nam.model.Literal;
import nam.model.util.LiteralUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("literalPageManager")
public class LiteralPageManager extends AbstractPageManager<Literal> implements Serializable {
	
	@Inject
	private LiteralWizard literalWizard;
	
	@Inject
	private LiteralDataManager literalDataManager;
	
	@Inject
	private LiteralListManager literalListManager;
	
	@Inject
	private LiteralRecord_OverviewSection literalOverviewSection;
	
	@Inject
	private LiteralRecord_IdentificationSection literalIdentificationSection;
	
	@Inject
	private LiteralRecord_ConfigurationSection literalConfigurationSection;
	
	@Inject
	private LiteralRecord_DocumentationSection literalDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public LiteralPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("literal");
	}
	
	public void refreshLocal() {
		refreshLocal("literal");
	}
	
	public void refreshMembers() {
		refreshMembers("literal");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		literalDataManager.setScope(scope);
		literalListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		literalListManager.refresh();
	}
	
	public String getLiteralListPage() {
		return "/nam/model/literal/literalListPage.xhtml";
	}
	
	public String getLiteralTreePage() {
		return "/nam/model/literal/literalTreePage.xhtml";
	}
	
	public String getLiteralSummaryPage() {
		return "/nam/model/literal/literalSummaryPage.xhtml";
	}
	
	public String getLiteralRecordPage() {
		return "/nam/model/literal/literalRecordPage.xhtml";
	}
	
	public String getLiteralWizardPage() {
		return "/nam/model/literal/literalWizardPage.xhtml";
	}
	
	public String getLiteralManagementPage() {
		return "/nam/model/literal/literalManagementPage.xhtml";
	}
	
	public String initializeLiteralListPage() {
		String pageLevelKey = "literalList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Literals", "showLiteralManagementPage()");
		String url = getLiteralListPage();
		selectionContext.setCurrentArea("literal");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeLiteralTreePage() {
		String pageLevelKey = "literalTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Literals", "showLiteralTreePage()");
		String url = getLiteralTreePage();
		selectionContext.setCurrentArea("literal");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeLiteralSummaryPage(Literal literal) {
		String pageLevelKey = "literalSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Literals", "showLiteralSummaryPage()");
		String url = getLiteralSummaryPage();
		selectionContext.setCurrentArea("literal");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeLiteralRecordPage() {
		Literal literal = selectionContext.getSelection("literal");
		String literalName = LiteralUtil.getLabel(literal);
		
		String pageLevelKey = "literalRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Literals", "showLiteralManagementPage()");
		addBreadcrumb(pageLevelKey, literalName, "showLiteralRecordPage()");
		String url = getLiteralRecordPage();
		selectionContext.setCurrentArea("literal");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeLiteralCreationPage(Literal literal) {
		setPageTitle("New "+getLiteralLabel(literal));
		setPageIcon("/icons/nam/NewLiteral16.gif");
		setSectionTitle("Literal Identification");
		literalWizard.setNewMode(true);
		
		String pageLevelKey = "literal";
		String wizardLevelKey = "literalWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Literals", "showLiteralManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Literal", "showLiteralWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showLiteralWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showLiteralWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showLiteralWizardPage('Documentation')");
		
		literalIdentificationSection.setOwner("literalWizard");
		literalConfigurationSection.setOwner("literalWizard");
		literalDocumentationSection.setOwner("literalWizard");
		
		sections.clear();
		sections.add(literalIdentificationSection);
		sections.add(literalConfigurationSection);
		sections.add(literalDocumentationSection);
		
		String url = getLiteralWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("literal");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeLiteralUpdatePage(Literal literal) {
		setPageTitle(getLiteralLabel(literal));
		setPageIcon("/icons/nam/Literal16.gif");
		setSectionTitle("Literal Overview");
		String literalName = LiteralUtil.getLabel(literal);
		literalWizard.setNewMode(false);
		
		String pageLevelKey = "literal";
		String wizardLevelKey = "literalWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Literals", "showLiteralManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(literalName, "showLiteralWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showLiteralWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showLiteralWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showLiteralWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showLiteralWizardPage('Documentation')");
		
		literalOverviewSection.setOwner("literalWizard");
		literalIdentificationSection.setOwner("literalWizard");
		literalConfigurationSection.setOwner("literalWizard");
		literalDocumentationSection.setOwner("literalWizard");
		
		sections.clear();
		sections.add(literalOverviewSection);
		sections.add(literalIdentificationSection);
		sections.add(literalConfigurationSection);
		sections.add(literalDocumentationSection);
		
		String url = getLiteralWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("literal");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeLiteralManagementPage() {
		setPageTitle("Literals");
		setPageIcon("/icons/nam/Literal16.gif");
		String pageLevelKey = "literalManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Literals", "showLiteralManagementPage()");
		String url = getLiteralManagementPage();
		selectionContext.setCurrentArea("literal");
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
		setSectionType("literal");
		setSectionName("Overview");
		setSectionTitle("Overview of Literals");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeLiteralSummaryView(Literal literal) {
		//String viewTitle = getLiteralLabel(literal);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("literal");
		setSectionName("Summary");
		setSectionTitle("Summary of Literal Records");
		setSectionIcon("/icons/nam/Literal16.gif");
		String viewLevelKey = "literalSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Literals", "showLiteralManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getLiteralLabel(Literal literal) {
		String label = "Literal";
		String name = LiteralUtil.getLabel(literal);
		if (name == null && literal.getName() != null)
			name = LiteralUtil.getLabel(literal);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Literal> page = literalWizard.getPage();
		if (page != null)
			setSectionTitle("Literal " + page.getName());
	}
	
	protected void updateState(Literal literal) {
		String literalName = LiteralUtil.getLabel(literal);
		setSectionTitle(literalName + " Literal");
	}
	
}
