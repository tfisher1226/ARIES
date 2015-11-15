package nam.model.interactor;

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

import nam.model.Interactor;
import nam.model.util.InteractorUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("interactorPageManager")
public class InteractorPageManager extends AbstractPageManager<Interactor> implements Serializable {
	
	@Inject
	private InteractorWizard interactorWizard;
	
	@Inject
	private InteractorDataManager interactorDataManager;
	
	@Inject
	private InteractorListManager interactorListManager;
	
	@Inject
	private InteractorRecord_OverviewSection interactorOverviewSection;
	
	@Inject
	private InteractorRecord_IdentificationSection interactorIdentificationSection;
	
	@Inject
	private InteractorRecord_ConfigurationSection interactorConfigurationSection;
	
	@Inject
	private InteractorRecord_DocumentationSection interactorDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public InteractorPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("interactor");
	}
	
	public void refreshLocal() {
		refreshLocal("interactor");
	}
	
	public void refreshMembers() {
		refreshMembers("interactor");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		interactorDataManager.setScope(scope);
		interactorListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		interactorListManager.refresh();
	}
	
	public String getInteractorListPage() {
		return "/nam/model/interactor/interactorListPage.xhtml";
	}
	
	public String getInteractorTreePage() {
		return "/nam/model/interactor/interactorTreePage.xhtml";
	}
	
	public String getInteractorSummaryPage() {
		return "/nam/model/interactor/interactorSummaryPage.xhtml";
	}
	
	public String getInteractorRecordPage() {
		return "/nam/model/interactor/interactorRecordPage.xhtml";
	}
	
	public String getInteractorWizardPage() {
		return "/nam/model/interactor/interactorWizardPage.xhtml";
	}
	
	public String getInteractorManagementPage() {
		return "/nam/model/interactor/interactorManagementPage.xhtml";
	}
	
	public String initializeInteractorListPage() {
		String pageLevelKey = "interactorList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Interactors", "showInteractorManagementPage()");
		String url = getInteractorListPage();
		selectionContext.setCurrentArea("interactor");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeInteractorTreePage() {
		String pageLevelKey = "interactorTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Interactors", "showInteractorTreePage()");
		String url = getInteractorTreePage();
		selectionContext.setCurrentArea("interactor");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeInteractorSummaryPage(Interactor interactor) {
		String pageLevelKey = "interactorSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Interactors", "showInteractorSummaryPage()");
		String url = getInteractorSummaryPage();
		selectionContext.setCurrentArea("interactor");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeInteractorRecordPage() {
		Interactor interactor = selectionContext.getSelection("interactor");
		String interactorName = InteractorUtil.getLabel(interactor);
		
		String pageLevelKey = "interactorRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Interactors", "showInteractorManagementPage()");
		addBreadcrumb(pageLevelKey, interactorName, "showInteractorRecordPage()");
		String url = getInteractorRecordPage();
		selectionContext.setCurrentArea("interactor");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeInteractorCreationPage(Interactor interactor) {
		setPageTitle("New "+getInteractorLabel(interactor));
		setPageIcon("/icons/nam/NewInteractor16.gif");
		setSectionTitle("Interactor Identification");
		interactorWizard.setNewMode(true);
		
		String pageLevelKey = "interactor";
		String wizardLevelKey = "interactorWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Interactors", "showInteractorManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Interactor", "showInteractorWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showInteractorWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showInteractorWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showInteractorWizardPage('Documentation')");
		
		interactorIdentificationSection.setOwner("interactorWizard");
		interactorConfigurationSection.setOwner("interactorWizard");
		interactorDocumentationSection.setOwner("interactorWizard");
		
		sections.clear();
		sections.add(interactorIdentificationSection);
		sections.add(interactorConfigurationSection);
		sections.add(interactorDocumentationSection);
		
		String url = getInteractorWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("interactor");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeInteractorUpdatePage(Interactor interactor) {
		setPageTitle(getInteractorLabel(interactor));
		setPageIcon("/icons/nam/Interactor16.gif");
		setSectionTitle("Interactor Overview");
		String interactorName = InteractorUtil.getLabel(interactor);
		interactorWizard.setNewMode(false);
		
		String pageLevelKey = "interactor";
		String wizardLevelKey = "interactorWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Interactors", "showInteractorManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(interactorName, "showInteractorWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showInteractorWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showInteractorWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showInteractorWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showInteractorWizardPage('Documentation')");
		
		interactorOverviewSection.setOwner("interactorWizard");
		interactorIdentificationSection.setOwner("interactorWizard");
		interactorConfigurationSection.setOwner("interactorWizard");
		interactorDocumentationSection.setOwner("interactorWizard");
		
		sections.clear();
		sections.add(interactorOverviewSection);
		sections.add(interactorIdentificationSection);
		sections.add(interactorConfigurationSection);
		sections.add(interactorDocumentationSection);
		
		String url = getInteractorWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("interactor");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeInteractorManagementPage() {
		setPageTitle("Interactors");
		setPageIcon("/icons/nam/Interactor16.gif");
		String pageLevelKey = "interactorManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Interactors", "showInteractorManagementPage()");
		String url = getInteractorManagementPage();
		selectionContext.setCurrentArea("interactor");
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
		setSectionType("interactor");
		setSectionName("Overview");
		setSectionTitle("Overview of Interactors");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeInteractorSummaryView(Interactor interactor) {
		//String viewTitle = getInteractorLabel(interactor);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("interactor");
		setSectionName("Summary");
		setSectionTitle("Summary of Interactor Records");
		setSectionIcon("/icons/nam/Interactor16.gif");
		String viewLevelKey = "interactorSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Interactors", "showInteractorManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getInteractorLabel(Interactor interactor) {
		String label = "Interactor";
		String name = InteractorUtil.getLabel(interactor);
		if (name == null && interactor.getName() != null)
			name = InteractorUtil.getLabel(interactor);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Interactor> page = interactorWizard.getPage();
		if (page != null)
			setSectionTitle("Interactor " + page.getName());
	}
	
	protected void updateState(Interactor interactor) {
		String interactorName = InteractorUtil.getLabel(interactor);
		setSectionTitle(interactorName + " Interactor");
	}
	
}
