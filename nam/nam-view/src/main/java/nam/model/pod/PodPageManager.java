package nam.model.pod;

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

import nam.model.Pod;
import nam.model.util.PodUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("podPageManager")
public class PodPageManager extends AbstractPageManager<Pod> implements Serializable {
	
	@Inject
	private PodWizard podWizard;
	
	@Inject
	private PodDataManager podDataManager;
	
	@Inject
	private PodListManager podListManager;
	
	@Inject
	private PodRecord_OverviewSection podOverviewSection;
	
	@Inject
	private PodRecord_IdentificationSection podIdentificationSection;
	
	@Inject
	private PodRecord_ConfigurationSection podConfigurationSection;
	
	@Inject
	private PodRecord_DocumentationSection podDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public PodPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("pod");
	}
	
	public void refreshLocal() {
		refreshLocal("pod");
	}
	
	public void refreshMembers() {
		refreshMembers("pod");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		podDataManager.setScope(scope);
		podListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		podListManager.refresh();
	}
	
	public String getPodListPage() {
		return "/nam/model/pod/podListPage.xhtml";
	}
	
	public String getPodTreePage() {
		return "/nam/model/pod/podTreePage.xhtml";
	}
	
	public String getPodSummaryPage() {
		return "/nam/model/pod/podSummaryPage.xhtml";
	}
	
	public String getPodRecordPage() {
		return "/nam/model/pod/podRecordPage.xhtml";
	}
	
	public String getPodWizardPage() {
		return "/nam/model/pod/podWizardPage.xhtml";
	}
	
	public String getPodManagementPage() {
		return "/nam/model/pod/podManagementPage.xhtml";
	}
	
	public String initializePodListPage() {
		String pageLevelKey = "podList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Pods", "showPodManagementPage()");
		String url = getPodListPage();
		selectionContext.setCurrentArea("pod");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePodTreePage() {
		String pageLevelKey = "podTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Pods", "showPodTreePage()");
		String url = getPodTreePage();
		selectionContext.setCurrentArea("pod");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePodSummaryPage(Pod pod) {
		String pageLevelKey = "podSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Pods", "showPodSummaryPage()");
		String url = getPodSummaryPage();
		selectionContext.setCurrentArea("pod");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePodRecordPage() {
		Pod pod = selectionContext.getSelection("pod");
		String podName = pod.getName();
		
		String pageLevelKey = "podRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Pods", "showPodManagementPage()");
		addBreadcrumb(pageLevelKey, podName, "showPodRecordPage()");
		String url = getPodRecordPage();
		selectionContext.setCurrentArea("pod");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializePodCreationPage(Pod pod) {
		setPageTitle("New "+getPodLabel(pod));
		setPageIcon("/icons/nam/NewPod16.gif");
		setSectionTitle("Pod Identification");
		podWizard.setNewMode(true);
		
		String pageLevelKey = "pod";
		String wizardLevelKey = "podWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Pods", "showPodManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Pod", "showPodWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showPodWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showPodWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showPodWizardPage('Documentation')");
		
		podIdentificationSection.setOwner("podWizard");
		podConfigurationSection.setOwner("podWizard");
		podDocumentationSection.setOwner("podWizard");
		
		sections.clear();
		sections.add(podIdentificationSection);
		sections.add(podConfigurationSection);
		sections.add(podDocumentationSection);
		
		String url = getPodWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("pod");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializePodUpdatePage(Pod pod) {
		setPageTitle(getPodLabel(pod));
		setPageIcon("/icons/nam/Pod16.gif");
		setSectionTitle("Pod Overview");
		String podName = pod.getName();
		podWizard.setNewMode(false);
		
		String pageLevelKey = "pod";
		String wizardLevelKey = "podWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Pods", "showPodManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(podName, "showPodWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showPodWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showPodWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showPodWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showPodWizardPage('Documentation')");
		
		podOverviewSection.setOwner("podWizard");
		podIdentificationSection.setOwner("podWizard");
		podConfigurationSection.setOwner("podWizard");
		podDocumentationSection.setOwner("podWizard");
		
		sections.clear();
		sections.add(podOverviewSection);
		sections.add(podIdentificationSection);
		sections.add(podConfigurationSection);
		sections.add(podDocumentationSection);
		
		String url = getPodWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("pod");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializePodManagementPage() {
		setPageTitle("Pods");
		setPageIcon("/icons/nam/Pod16.gif");
		String pageLevelKey = "podManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Pods", "showPodManagementPage()");
		String url = getPodManagementPage();
		selectionContext.setCurrentArea("pod");
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
		setSectionType("pod");
		setSectionName("Overview");
		setSectionTitle("Overview of Pods");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializePodSummaryView(Pod pod) {
		//String viewTitle = getPodLabel(pod);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("pod");
		setSectionName("Summary");
		setSectionTitle("Summary of Pod Records");
		setSectionIcon("/icons/nam/Pod16.gif");
		String viewLevelKey = "podSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Pods", "showPodManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getPodLabel(Pod pod) {
		String label = "Pod";
		String name = PodUtil.getLabel(pod);
		if (name == null && pod.getName() != null)
			name = NameUtil.capName(pod.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Pod> page = podWizard.getPage();
		if (page != null)
			setSectionTitle("Pod " + page.getName());
	}
	
	protected void updateState(Pod pod) {
		String podName = NameUtil.capName(pod.getName());
		setSectionTitle(podName + " Pod");
	}
	
}
