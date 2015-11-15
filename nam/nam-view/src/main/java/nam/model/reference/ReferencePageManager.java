package nam.model.reference;

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

import nam.model.Reference;
import nam.model.util.ReferenceUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("referencePageManager")
public class ReferencePageManager extends AbstractPageManager<Reference> implements Serializable {

	@Inject
	private ReferenceWizard referenceWizard;
	
	@Inject
	private ReferenceDataManager referenceDataManager;
	
	@Inject
	private ReferenceListManager referenceListManager;
	
	@Inject
	private ReferenceRecord_OverviewSection referenceOverviewSection;
	
	@Inject
	private ReferenceRecord_IdentificationSection referenceIdentificationSection;
	
	@Inject
	private ReferenceRecord_ConfigurationSection referenceConfigurationSection;
	
	@Inject
	private ReferenceRecord_DocumentationSection referenceDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ReferencePageManager() {
		initializeSections();
		initializeDefaultView();
	}
	

	public void refresh() {
		refresh("reference");
	}
	
	public void refreshLocal() {
		refreshLocal("reference");
	}
	
	public void refreshMembers() {
		refreshMembers("reference");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		referenceDataManager.setScope(scope);
		referenceListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		referenceListManager.refresh();
	}
	
	public String getReferenceListPage() {
		return "/nam/model/reference/referenceListPage.xhtml";
	}

	public String getReferenceTreePage() {
		return "/nam/model/reference/referenceTreePage.xhtml";
	}
	
	public String getReferenceSummaryPage() {
		return "/nam/model/reference/referenceSummaryPage.xhtml";
	}
	
	public String getReferenceRecordPage() {
		return "/nam/model/reference/referenceRecordPage.xhtml";
	}

	public String getReferenceWizardPage() {
		return "/nam/model/reference/referenceWizardPage.xhtml";
	}

	public String getReferenceManagementPage() {
		return "/nam/model/reference/referenceManagementPage.xhtml";
	}
	
	public String initializeReferenceListPage() {
		String pageLevelKey = "referenceList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "References", "showReferenceManagementPage()");
		String url = getReferenceListPage();
		selectionContext.setCurrentArea("reference");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeReferenceTreePage() {
		String pageLevelKey = "referenceTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "References", "showReferenceTreePage()");
		String url = getReferenceTreePage();
		selectionContext.setCurrentArea("reference");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeReferenceSummaryPage(Reference reference) {
		String pageLevelKey = "referenceSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "References", "showReferenceSummaryPage()");
		String url = getReferenceSummaryPage();
		selectionContext.setCurrentArea("reference");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeReferenceRecordPage() {
		Reference reference = selectionContext.getSelection("reference");
		String referenceName = reference.getName();
		
		String pageLevelKey = "referenceRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "References", "showReferenceManagementPage()");
		addBreadcrumb(pageLevelKey, referenceName, "showReferenceRecordPage()");
		String url = getReferenceRecordPage();
		selectionContext.setCurrentArea("reference");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeReferenceCreationPage(Reference reference) {
		setPageTitle("New "+getReferenceLabel(reference));
		setPageIcon("/icons/nam/NewReference16.gif");
		setSectionTitle("Reference Identification");
		referenceWizard.setNewMode(true);
		
		String pageLevelKey = "reference";
		String wizardLevelKey = "referenceWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "References", "showReferenceManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Reference", "showReferenceWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showReferenceWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showReferenceWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showReferenceWizardPage('Documentation')");
		
		referenceIdentificationSection.setOwner("referenceWizard");
		referenceConfigurationSection.setOwner("referenceWizard");
		referenceDocumentationSection.setOwner("referenceWizard");
		
		sections.clear();
		sections.add(referenceIdentificationSection);
		sections.add(referenceConfigurationSection);
		sections.add(referenceDocumentationSection);
		
		String url = getReferenceWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("reference");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeReferenceUpdatePage(Reference reference) {
		setPageTitle(getReferenceLabel(reference));
		setPageIcon("/icons/nam/Reference16.gif");
		setSectionTitle("Reference Overview");
		String referenceName = reference.getName();
		referenceWizard.setNewMode(false);
		
		String pageLevelKey = "reference";
		String wizardLevelKey = "referenceWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "References", "showReferenceManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(referenceName, "showReferenceWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showReferenceWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showReferenceWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showReferenceWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showReferenceWizardPage('Documentation')");
		
		referenceOverviewSection.setOwner("referenceWizard");
		referenceIdentificationSection.setOwner("referenceWizard");
		referenceConfigurationSection.setOwner("referenceWizard");
		referenceDocumentationSection.setOwner("referenceWizard");
		
		sections.clear();
		sections.add(referenceOverviewSection);
		sections.add(referenceIdentificationSection);
		sections.add(referenceConfigurationSection);
		sections.add(referenceDocumentationSection);
		
		String url = getReferenceWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("reference");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
		
	public String initializeReferenceManagementPage() {
		setPageTitle("References");
		setPageIcon("/icons/nam/Reference16.gif");
		String pageLevelKey = "referenceManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "References", "showReferenceManagementPage()");
		String url = getReferenceManagementPage();
		selectionContext.setCurrentArea("reference");
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
		setSectionType("reference");
		setSectionName("Overview");
		setSectionTitle("Overview of References");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeReferenceSummaryView(Reference reference) {
		//String viewTitle = getReferenceLabel(reference);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("reference");
		setSectionName("Summary");
		setSectionTitle("Summary of Reference Records");
		setSectionIcon("/icons/nam/Reference16.gif");
		String viewLevelKey = "referenceSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "References", "showReferenceManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getReferenceLabel(Reference reference) {
		String label = "Reference";
		String name = ReferenceUtil.getLabel(reference);
		if (name == null && reference.getName() != null)
			name = NameUtil.capName(reference.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}

	protected void updateState() {
		AbstractWizardPage<Reference> page = referenceWizard.getPage();
		if (page != null)
			setSectionTitle("Reference " + page.getName());
	}
	
	protected void updateState(Reference reference) {
		String referenceName = NameUtil.capName(reference.getName());
		setSectionTitle(referenceName + " Reference");
	}

}
