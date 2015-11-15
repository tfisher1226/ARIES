package nam.model.deployment;

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

import nam.model.Deployment;
import nam.model.util.DeploymentUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("deploymentPageManager")
public class DeploymentPageManager extends AbstractPageManager<Deployment> implements Serializable {
	
	@Inject
	private DeploymentWizard deploymentWizard;
	
	@Inject
	private DeploymentDataManager deploymentDataManager;
	
	@Inject
	private DeploymentListManager deploymentListManager;
	
	@Inject
	private DeploymentRecord_OverviewSection deploymentOverviewSection;
	
	@Inject
	private DeploymentRecord_IdentificationSection deploymentIdentificationSection;
	
	@Inject
	private DeploymentRecord_ConfigurationSection deploymentConfigurationSection;
	
	@Inject
	private DeploymentRecord_DocumentationSection deploymentDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public DeploymentPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("deployment");
	}
	
	public void refreshLocal() {
		refreshLocal("deployment");
	}
	
	public void refreshMembers() {
		refreshMembers("deployment");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		deploymentDataManager.setScope(scope);
		deploymentListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		deploymentListManager.refresh();
	}
	
	public String getDeploymentListPage() {
		return "/nam/model/deployment/deploymentListPage.xhtml";
	}
	
	public String getDeploymentTreePage() {
		return "/nam/model/deployment/deploymentTreePage.xhtml";
	}
	
	public String getDeploymentSummaryPage() {
		return "/nam/model/deployment/deploymentSummaryPage.xhtml";
	}
	
	public String getDeploymentRecordPage() {
		return "/nam/model/deployment/deploymentRecordPage.xhtml";
	}
	
	public String getDeploymentWizardPage() {
		return "/nam/model/deployment/deploymentWizardPage.xhtml";
	}
	
	public String getDeploymentManagementPage() {
		return "/nam/model/deployment/deploymentManagementPage.xhtml";
	}
	
	public String initializeDeploymentListPage() {
		String pageLevelKey = "deploymentList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Deployments", "showDeploymentManagementPage()");
		String url = getDeploymentListPage();
		selectionContext.setCurrentArea("deployment");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeDeploymentTreePage() {
		String pageLevelKey = "deploymentTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Deployments", "showDeploymentTreePage()");
		String url = getDeploymentTreePage();
		selectionContext.setCurrentArea("deployment");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeDeploymentSummaryPage(Deployment deployment) {
		String pageLevelKey = "deploymentSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Deployments", "showDeploymentSummaryPage()");
		String url = getDeploymentSummaryPage();
		selectionContext.setCurrentArea("deployment");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeDeploymentRecordPage() {
		Deployment deployment = selectionContext.getSelection("deployment");
		String deploymentName = DeploymentUtil.getLabel(deployment);
		
		String pageLevelKey = "deploymentRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Deployments", "showDeploymentManagementPage()");
		addBreadcrumb(pageLevelKey, deploymentName, "showDeploymentRecordPage()");
		String url = getDeploymentRecordPage();
		selectionContext.setCurrentArea("deployment");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeDeploymentCreationPage(Deployment deployment) {
		setPageTitle("New "+getDeploymentLabel(deployment));
		setPageIcon("/icons/nam/NewDeployment16.gif");
		setSectionTitle("Deployment Identification");
		deploymentWizard.setNewMode(true);
		
		String pageLevelKey = "deployment";
		String wizardLevelKey = "deploymentWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Deployments", "showDeploymentManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Deployment", "showDeploymentWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showDeploymentWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showDeploymentWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showDeploymentWizardPage('Documentation')");
		
		deploymentIdentificationSection.setOwner("deploymentWizard");
		deploymentConfigurationSection.setOwner("deploymentWizard");
		deploymentDocumentationSection.setOwner("deploymentWizard");
		
		sections.clear();
		sections.add(deploymentIdentificationSection);
		sections.add(deploymentConfigurationSection);
		sections.add(deploymentDocumentationSection);
		
		String url = getDeploymentWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("deployment");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeDeploymentUpdatePage(Deployment deployment) {
		setPageTitle(getDeploymentLabel(deployment));
		setPageIcon("/icons/nam/Deployment16.gif");
		setSectionTitle("Deployment Overview");
		String deploymentName = DeploymentUtil.getLabel(deployment);
		deploymentWizard.setNewMode(false);
		
		String pageLevelKey = "deployment";
		String wizardLevelKey = "deploymentWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Deployments", "showDeploymentManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(deploymentName, "showDeploymentWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showDeploymentWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showDeploymentWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showDeploymentWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showDeploymentWizardPage('Documentation')");
		
		deploymentOverviewSection.setOwner("deploymentWizard");
		deploymentIdentificationSection.setOwner("deploymentWizard");
		deploymentConfigurationSection.setOwner("deploymentWizard");
		deploymentDocumentationSection.setOwner("deploymentWizard");
		
		sections.clear();
		sections.add(deploymentOverviewSection);
		sections.add(deploymentIdentificationSection);
		sections.add(deploymentConfigurationSection);
		sections.add(deploymentDocumentationSection);
		
		String url = getDeploymentWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("deployment");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeDeploymentManagementPage() {
		setPageTitle("Deployments");
		setPageIcon("/icons/nam/Deployment16.gif");
		String pageLevelKey = "deploymentManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Deployments", "showDeploymentManagementPage()");
		String url = getDeploymentManagementPage();
		selectionContext.setCurrentArea("deployment");
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
		setSectionType("deployment");
		setSectionName("Overview");
		setSectionTitle("Overview of Deployments");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeDeploymentSummaryView(Deployment deployment) {
		//String viewTitle = getDeploymentLabel(deployment);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("deployment");
		setSectionName("Summary");
		setSectionTitle("Summary of Deployment Records");
		setSectionIcon("/icons/nam/Deployment16.gif");
		String viewLevelKey = "deploymentSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Deployments", "showDeploymentManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getDeploymentLabel(Deployment deployment) {
		String label = "Deployment";
		String name = DeploymentUtil.getLabel(deployment);
		if (name == null && deployment.getName() != null)
			name = DeploymentUtil.getLabel(deployment);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Deployment> page = deploymentWizard.getPage();
		if (page != null)
			setSectionTitle("Deployment " + page.getName());
	}
	
	protected void updateState(Deployment deployment) {
		String deploymentName = DeploymentUtil.getLabel(deployment);
		setSectionTitle(deploymentName + " Deployment");
	}
	
}
