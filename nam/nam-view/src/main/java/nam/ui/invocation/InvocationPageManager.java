package nam.ui.invocation;

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

import nam.ui.Invocation;
import nam.ui.design.SelectionContext;
import nam.ui.util.InvocationUtil;


@SessionScoped
@Named("invocationPageManager")
public class InvocationPageManager extends AbstractPageManager<Invocation> implements Serializable {
	
	@Inject
	private InvocationWizard invocationWizard;
	
	@Inject
	private InvocationDataManager invocationDataManager;
	
	@Inject
	private InvocationListManager invocationListManager;
	
	@Inject
	private InvocationRecord_OverviewSection invocationOverviewSection;
	
	@Inject
	private InvocationRecord_IdentificationSection invocationIdentificationSection;
	
	@Inject
	private InvocationRecord_ConfigurationSection invocationConfigurationSection;
	
	@Inject
	private InvocationRecord_DocumentationSection invocationDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public InvocationPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("invocation");
	}
	
	public void refreshLocal() {
		refreshLocal("invocation");
	}
	
	public void refreshMembers() {
		refreshMembers("invocation");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		invocationDataManager.setScope(scope);
		invocationListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		invocationListManager.refresh();
	}
	
	public String getInvocationListPage() {
		return "/nam/ui/invocation/invocationListPage.xhtml";
	}
	
	public String getInvocationTreePage() {
		return "/nam/ui/invocation/invocationTreePage.xhtml";
	}
	
	public String getInvocationSummaryPage() {
		return "/nam/ui/invocation/invocationSummaryPage.xhtml";
	}
	
	public String getInvocationRecordPage() {
		return "/nam/ui/invocation/invocationRecordPage.xhtml";
	}
	
	public String getInvocationWizardPage() {
		return "/nam/ui/invocation/invocationWizardPage.xhtml";
	}
	
	public String getInvocationManagementPage() {
		return "/nam/ui/invocation/invocationManagementPage.xhtml";
	}
	
	public String initializeInvocationListPage() {
		String pageLevelKey = "invocationList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Invocations", "showInvocationManagementPage()");
		String url = getInvocationListPage();
		selectionContext.setCurrentArea("invocation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeInvocationTreePage() {
		String pageLevelKey = "invocationTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Invocations", "showInvocationTreePage()");
		String url = getInvocationTreePage();
		selectionContext.setCurrentArea("invocation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeInvocationSummaryPage(Invocation invocation) {
		String pageLevelKey = "invocationSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Invocations", "showInvocationSummaryPage()");
		String url = getInvocationSummaryPage();
		selectionContext.setCurrentArea("invocation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeInvocationRecordPage() {
		Invocation invocation = selectionContext.getSelection("invocation");
		String invocationName = invocation.getName();
		
		String pageLevelKey = "invocationRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Invocations", "showInvocationManagementPage()");
		addBreadcrumb(pageLevelKey, invocationName, "showInvocationRecordPage()");
		String url = getInvocationRecordPage();
		selectionContext.setCurrentArea("invocation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeInvocationCreationPage(Invocation invocation) {
		setPageTitle("New "+getInvocationLabel(invocation));
		setPageIcon("/icons/nam/NewInvocation16.gif");
		setSectionTitle("Invocation Identification");
		invocationWizard.setNewMode(true);
		
		String pageLevelKey = "invocation";
		String wizardLevelKey = "invocationWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Invocations", "showInvocationManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Invocation", "showInvocationWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showInvocationWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showInvocationWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showInvocationWizardPage('Documentation')");
		
		invocationIdentificationSection.setOwner("invocationWizard");
		invocationConfigurationSection.setOwner("invocationWizard");
		invocationDocumentationSection.setOwner("invocationWizard");
		
		sections.clear();
		sections.add(invocationIdentificationSection);
		sections.add(invocationConfigurationSection);
		sections.add(invocationDocumentationSection);
		
		String url = getInvocationWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("invocation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeInvocationUpdatePage(Invocation invocation) {
		setPageTitle(getInvocationLabel(invocation));
		setPageIcon("/icons/nam/Invocation16.gif");
		setSectionTitle("Invocation Overview");
		String invocationName = invocation.getName();
		invocationWizard.setNewMode(false);
		
		String pageLevelKey = "invocation";
		String wizardLevelKey = "invocationWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Invocations", "showInvocationManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(invocationName, "showInvocationWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showInvocationWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showInvocationWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showInvocationWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showInvocationWizardPage('Documentation')");
		
		invocationOverviewSection.setOwner("invocationWizard");
		invocationIdentificationSection.setOwner("invocationWizard");
		invocationConfigurationSection.setOwner("invocationWizard");
		invocationDocumentationSection.setOwner("invocationWizard");
		
		sections.clear();
		sections.add(invocationOverviewSection);
		sections.add(invocationIdentificationSection);
		sections.add(invocationConfigurationSection);
		sections.add(invocationDocumentationSection);
		
		String url = getInvocationWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("invocation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeInvocationManagementPage() {
		setPageTitle("Invocations");
		setPageIcon("/icons/nam/Invocation16.gif");
		String pageLevelKey = "invocationManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Invocations", "showInvocationManagementPage()");
		String url = getInvocationManagementPage();
		selectionContext.setCurrentArea("invocation");
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
		setSectionType("invocation");
		setSectionName("Overview");
		setSectionTitle("Overview of Invocations");
		setSectionIcon("/icons/nam/Invocation16.gif");
	}
	
	public String initializeInvocationSummaryView(Invocation invocation) {
		//String viewTitle = getInvocationLabel(invocation);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("invocation");
		setSectionName("Summary");
		setSectionTitle("Summary of Invocation Records");
		setSectionIcon("/icons/nam/Invocation16.gif");
		String viewLevelKey = "invocationSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Invocations", "showInvocationManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getInvocationLabel(Invocation invocation) {
		String label = "Invocation";
		String name = InvocationUtil.getLabel(invocation);
		if (name == null && invocation.getName() != null)
			name = NameUtil.capName(invocation.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Invocation> page = invocationWizard.getPage();
		if (page != null)
			setSectionTitle("Invocation " + page.getName());
	}
	
	protected void updateState(Invocation invocation) {
		String invocationName = NameUtil.capName(invocation.getName());
		setSectionTitle(invocationName + " Invocation");
	}
	
}
