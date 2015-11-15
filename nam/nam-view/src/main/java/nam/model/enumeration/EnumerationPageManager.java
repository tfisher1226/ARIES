package nam.model.enumeration;

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

import nam.model.Enumeration;
import nam.model.util.EnumerationUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("enumerationPageManager")
public class EnumerationPageManager extends AbstractPageManager<Enumeration> implements Serializable {
	
	@Inject
	private EnumerationWizard enumerationWizard;
	
	@Inject
	private EnumerationDataManager enumerationDataManager;
	
	@Inject
	private EnumerationListManager enumerationListManager;
	
	@Inject
	private EnumerationRecord_OverviewSection enumerationOverviewSection;
	
	@Inject
	private EnumerationRecord_IdentificationSection enumerationIdentificationSection;
	
	@Inject
	private EnumerationRecord_ConfigurationSection enumerationConfigurationSection;
	
	@Inject
	private EnumerationRecord_DocumentationSection enumerationDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public EnumerationPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("enumeration");
	}
	
	public void refreshLocal() {
		refreshLocal("enumeration");
	}
	
	public void refreshMembers() {
		refreshMembers("enumeration");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		enumerationDataManager.setScope(scope);
		enumerationListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		enumerationListManager.refresh();
	}
	
	public String getEnumerationListPage() {
		return "/nam/model/enumeration/enumerationListPage.xhtml";
	}
	
	public String getEnumerationTreePage() {
		return "/nam/model/enumeration/enumerationTreePage.xhtml";
	}
	
	public String getEnumerationSummaryPage() {
		return "/nam/model/enumeration/enumerationSummaryPage.xhtml";
	}
	
	public String getEnumerationRecordPage() {
		return "/nam/model/enumeration/enumerationRecordPage.xhtml";
	}
	
	public String getEnumerationWizardPage() {
		return "/nam/model/enumeration/enumerationWizardPage.xhtml";
	}
	
	public String getEnumerationManagementPage() {
		return "/nam/model/enumeration/enumerationManagementPage.xhtml";
	}
	
	public String initializeEnumerationListPage() {
		String pageLevelKey = "enumerationList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Enumerations", "showEnumerationManagementPage()");
		String url = getEnumerationListPage();
		selectionContext.setCurrentArea("enumeration");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeEnumerationTreePage() {
		String pageLevelKey = "enumerationTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Enumerations", "showEnumerationTreePage()");
		String url = getEnumerationTreePage();
		selectionContext.setCurrentArea("enumeration");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeEnumerationSummaryPage(Enumeration enumeration) {
		String pageLevelKey = "enumerationSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Enumerations", "showEnumerationSummaryPage()");
		String url = getEnumerationSummaryPage();
		selectionContext.setCurrentArea("enumeration");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeEnumerationRecordPage() {
		Enumeration enumeration = selectionContext.getSelection("enumeration");
		String enumerationName = EnumerationUtil.getLabel(enumeration);
		
		String pageLevelKey = "enumerationRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Enumerations", "showEnumerationManagementPage()");
		addBreadcrumb(pageLevelKey, enumerationName, "showEnumerationRecordPage()");
		String url = getEnumerationRecordPage();
		selectionContext.setCurrentArea("enumeration");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeEnumerationCreationPage(Enumeration enumeration) {
		setPageTitle("New "+getEnumerationLabel(enumeration));
		setPageIcon("/icons/nam/NewEnumeration16.gif");
		setSectionTitle("Enumeration Identification");
		enumerationWizard.setNewMode(true);
		
		String pageLevelKey = "enumeration";
		String wizardLevelKey = "enumerationWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Enumerations", "showEnumerationManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Enumeration", "showEnumerationWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showEnumerationWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showEnumerationWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showEnumerationWizardPage('Documentation')");
		
		enumerationIdentificationSection.setOwner("enumerationWizard");
		enumerationConfigurationSection.setOwner("enumerationWizard");
		enumerationDocumentationSection.setOwner("enumerationWizard");
		
		sections.clear();
		sections.add(enumerationIdentificationSection);
		sections.add(enumerationConfigurationSection);
		sections.add(enumerationDocumentationSection);
		
		String url = getEnumerationWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("enumeration");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeEnumerationUpdatePage(Enumeration enumeration) {
		setPageTitle(getEnumerationLabel(enumeration));
		setPageIcon("/icons/nam/Enumeration16.gif");
		setSectionTitle("Enumeration Overview");
		String enumerationName = EnumerationUtil.getLabel(enumeration);
		enumerationWizard.setNewMode(false);
		
		String pageLevelKey = "enumeration";
		String wizardLevelKey = "enumerationWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Enumerations", "showEnumerationManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(enumerationName, "showEnumerationWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showEnumerationWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showEnumerationWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showEnumerationWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showEnumerationWizardPage('Documentation')");
		
		enumerationOverviewSection.setOwner("enumerationWizard");
		enumerationIdentificationSection.setOwner("enumerationWizard");
		enumerationConfigurationSection.setOwner("enumerationWizard");
		enumerationDocumentationSection.setOwner("enumerationWizard");
		
		sections.clear();
		sections.add(enumerationOverviewSection);
		sections.add(enumerationIdentificationSection);
		sections.add(enumerationConfigurationSection);
		sections.add(enumerationDocumentationSection);
		
		String url = getEnumerationWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("enumeration");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeEnumerationManagementPage() {
		setPageTitle("Enumerations");
		setPageIcon("/icons/nam/Enumeration16.gif");
		String pageLevelKey = "enumerationManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Enumerations", "showEnumerationManagementPage()");
		String url = getEnumerationManagementPage();
		selectionContext.setCurrentArea("enumeration");
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
		setSectionType("enumeration");
		setSectionName("Overview");
		setSectionTitle("Overview of Enumerations");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeEnumerationSummaryView(Enumeration enumeration) {
		//String viewTitle = getEnumerationLabel(enumeration);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("enumeration");
		setSectionName("Summary");
		setSectionTitle("Summary of Enumeration Records");
		setSectionIcon("/icons/nam/Enumeration16.gif");
		String viewLevelKey = "enumerationSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Enumerations", "showEnumerationManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getEnumerationLabel(Enumeration enumeration) {
		String label = "Enumeration";
		String name = EnumerationUtil.getLabel(enumeration);
		if (name == null && enumeration.getName() != null)
			name = EnumerationUtil.getLabel(enumeration);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Enumeration> page = enumerationWizard.getPage();
		if (page != null)
			setSectionTitle("Enumeration " + page.getName());
	}
	
	protected void updateState(Enumeration enumeration) {
		String enumerationName = EnumerationUtil.getLabel(enumeration);
		setSectionTitle(enumerationName + " Enumeration");
	}
	
}
