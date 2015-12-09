package admin.registration;

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

import admin.Registration;
import admin.util.RegistrationUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("registrationPageManager")
public class RegistrationPageManager extends AbstractPageManager<Registration> implements Serializable {
	
	@Inject
	private RegistrationWizard registrationWizard;
	
	@Inject
	private RegistrationDataManager registrationDataManager;
	
	@Inject
	private RegistrationInfoManager registrationInfoManager;
	
	@Inject
	private RegistrationListManager registrationListManager;
	
	@Inject
	private RegistrationRecord_OverviewSection registrationOverviewSection;
	
	@Inject
	private RegistrationRecord_IdentificationSection registrationIdentificationSection;
	
	//@Inject
	//private RegistrationRecord_ContactSection registrationContactSection;
	
	@Inject
	private RegistrationRecord_SubmitSection registrationSubmitSection;
	
	@Inject
	private SelectionContext selectionContext;
	

	public RegistrationPageManager() {
		initializeSections();
	}
	
	
	public void refresh() {
		refresh("projectList");
	}
	
	public void refreshLocal() {
		refreshLocal("registration");
	}
	
	public void refreshMembers() {
		refreshMembers("registration");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		//refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		registrationDataManager.setScope(scope);
		registrationListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		//nothing for now
	}

	public String getRegistrationListPage() {
		return "/admin/registration/registrationListPage.xhtml";
	}
	
	public String getRegistrationTreePage() {
		return "/admin/registration/registrationTreePage.xhtml";
	}

	public String getRegistrationSummaryPage() {
		return "/admin/registration/registrationSummaryPage.xhtml";
	}
	
	public String getRegistrationRecordPage() {
		return "/admin/registration/registrationRecordPage.xhtml";
	}

	public String getRegistrationWizardPage() {
		return "/admin/registration/registrationWizardPage.xhtml";
	}

	public String getRegistrationManagementPage() {
		return "/admin/registration/registrationManagementPage.xhtml";
	}
	
	public void handleRegistrationSelected(@Observes @Selected Registration registration) {
		selectionContext.setSelection("registration",  registration);
		registrationInfoManager.setRecord(registration);
	}
	
	public void handleRegistrationUnselected(@Observes @Unselected Registration registration) {
		selectionContext.unsetSelection("registration",  registration);
		registrationInfoManager.unsetRecord(registration);
	}
	
	public void handleRegistrationChecked() {
		String scope = "registrationSelection";
		RegistrationListObject listObject = registrationListManager.getSelection();
		Registration registration = selectionContext.getSelection("registration");
		boolean checked = registrationListManager.getCheckedState();
		listObject.setChecked(checked);
		if (checked) {
			registrationInfoManager.setRecord(registration);
			selectionContext.setSelection(scope,  registration);
		} else {
			registrationInfoManager.unsetRecord(registration);
			selectionContext.unsetSelection(scope,  registration);
		}
		refreshLocal(scope);
	}
	
	public String initializeRegistrationListPage() {
		String pageLevelKey = "registrationList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Registrations", "showRegistrationManagementPage()");
		String url = getRegistrationListPage();
		selectionContext.setCurrentArea("registration");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeRegistrationTreePage() {
		String pageLevelKey = "registrationTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Registrations", "showRegistrationTreePage()");
		String url = getRegistrationTreePage();
		selectionContext.setCurrentArea("registration");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeRegistrationSummaryPage(Registration registration) {
		String pageLevelKey = "registrationSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Registrations", "showRegistrationSummaryPage()");
		String url = getRegistrationSummaryPage();
		selectionContext.setCurrentArea("registration");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeRegistrationRecordPage() {
		Registration registration = selectionContext.getSelection("registration");
		String registrationName = RegistrationUtil.getLabel(registration);
		
		String pageLevelKey = "registrationRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Registrations", "showRegistrationManagementPage()");
		addBreadcrumb(pageLevelKey, registrationName, "showRegistrationRecordPage()");
		String url = getRegistrationRecordPage();
		selectionContext.setCurrentArea("registration");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeRegistrationCreationPage(Registration registration) {
		setPageTitle("New "+getRegistrationLabel(registration));
		setPageIcon("/icons/nam/NewRegistration16.gif");
		setSectionIcon("/icons/nam/Registration16.gif");
		setSectionTitle("Identification");
		registrationWizard.setNewMode(true);
		
		String pageLevelKey = "registration";
		String wizardLevelKey = "registrationWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Registrations", "showRegistrationManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Registration", "showRegistrationWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showRegistrationWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Submit", "showRegistrationWizardPage('Submit')");
		
		registrationIdentificationSection.setOwner("registrationWizard");
		//registrationContactSection.setOwner("registrationWizard");
		registrationSubmitSection.setOwner("registrationWizard");

		sections.clear();
		sections.add(registrationIdentificationSection);
		//sections.add(registrationContactSection);
		sections.add(registrationSubmitSection);
		
		String url = getRegistrationWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("registration");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializeRegistrationUpdatePage(Registration registration) {
		setPageTitle(getRegistrationLabel(registration));
		setPageIcon("/icons/nam/Registration16.gif");
		setSectionTitle("Registration Overview");
		String registrationName = RegistrationUtil.getLabel(registration);
		registrationWizard.setNewMode(false);
		
		String pageLevelKey = "registration";
		String wizardLevelKey = "registrationWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Registrations", "showRegistrationManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(registrationName, "showRegistrationWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showRegistrationWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showRegistrationWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Contact", "showRegistrationWizardPage('Contact')");
		
		registrationOverviewSection.setOwner("registrationWizard");
		registrationIdentificationSection.setOwner("registrationWizard");
		//registrationContactSection.setOwner("registrationWizard");
		//registrationSubmitSection.setOwner("registrationWizard");

		sections.clear();
		sections.add(registrationOverviewSection);
		sections.add(registrationIdentificationSection);
		//sections.add(registrationContactSection);
		//sections.add(registrationSubmitSection);
		
		String url = getRegistrationWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("registration");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}

	public String initializeRegistrationManagementPage() {
		setPageTitle("Registrations");
		setPageIcon("/icons/nam/Registration16.gif");
		String pageLevelKey = "registrationManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Registrations", "showRegistrationManagementPage()");
		String url = getRegistrationManagementPage();
		selectionContext.setCurrentArea("registration");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}

	public void initializeDefaultView() {
		setPageTitle("Registrations");
		setPageIcon("/icons/nam/Registration16.gif");
		setSectionType("registration");
		setSectionName("Overview");
		setSectionTitle("Overview of Registrations");
		setSectionIcon("/icons/nam/Overview16.gif");
		String viewLevelKey = "registrationOverview";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Registrations", "showRegistrationManagementPage()");
		String scope = "projectList";
		refreshLocal(scope);
		sections.clear();
	}
	
	public String initializeRegistrationSummaryView(Registration registration) {
		//String viewTitle = getRegistrationLabel(registration);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("registration");
		setSectionName("Summary");
		setSectionTitle("Summary of Registration Records");
		setSectionIcon("/icons/nam/Registration16.gif");
		String viewLevelKey = "registrationSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Registrations", "showRegistrationManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getRegistrationLabel(Registration registration) {
		String label = "Registration";
		String name = RegistrationUtil.getLabel(registration);
			if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}

	protected void updateState() {
		AbstractWizardPage<Registration> page = registrationWizard.getPage();
		if (page != null)
			setSectionTitle("Registration " + page.getName());
	}
	
	protected void updateState(Registration registration) {
		String registrationName = RegistrationUtil.getLabel(registration);
		setSectionTitle(registrationName + " Registration");
	}
	
}
