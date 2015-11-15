package nam.ui.control;

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

import nam.ui.Control;
import nam.ui.design.SelectionContext;
import nam.ui.util.ControlUtil;


@SessionScoped
@Named("controlPageManager")
public class ControlPageManager extends AbstractPageManager<Control> implements Serializable {
	
	@Inject
	private ControlWizard controlWizard;
	
	@Inject
	private ControlDataManager controlDataManager;
	
	@Inject
	private ControlListManager controlListManager;
	
	@Inject
	private ControlRecord_OverviewSection controlOverviewSection;
	
	@Inject
	private ControlRecord_IdentificationSection controlIdentificationSection;
	
	@Inject
	private ControlRecord_ConfigurationSection controlConfigurationSection;
	
	@Inject
	private ControlRecord_DocumentationSection controlDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ControlPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("control");
	}
	
	public void refreshLocal() {
		refreshLocal("control");
	}
	
	public void refreshMembers() {
		refreshMembers("control");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		controlDataManager.setScope(scope);
		controlListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		controlListManager.refresh();
	}
	
	public String getControlListPage() {
		return "/nam/ui/control/controlListPage.xhtml";
	}
	
	public String getControlTreePage() {
		return "/nam/ui/control/controlTreePage.xhtml";
	}
	
	public String getControlSummaryPage() {
		return "/nam/ui/control/controlSummaryPage.xhtml";
	}
	
	public String getControlRecordPage() {
		return "/nam/ui/control/controlRecordPage.xhtml";
	}
	
	public String getControlWizardPage() {
		return "/nam/ui/control/controlWizardPage.xhtml";
	}
	
	public String getControlManagementPage() {
		return "/nam/ui/control/controlManagementPage.xhtml";
	}
	
	public String initializeControlListPage() {
		String pageLevelKey = "controlList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Controls", "showControlManagementPage()");
		String url = getControlListPage();
		selectionContext.setCurrentArea("control");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeControlTreePage() {
		String pageLevelKey = "controlTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Controls", "showControlTreePage()");
		String url = getControlTreePage();
		selectionContext.setCurrentArea("control");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeControlSummaryPage(Control control) {
		String pageLevelKey = "controlSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Controls", "showControlSummaryPage()");
		String url = getControlSummaryPage();
		selectionContext.setCurrentArea("control");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeControlRecordPage() {
		Control control = selectionContext.getSelection("control");
		String controlName = control.getName();
		
		String pageLevelKey = "controlRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Controls", "showControlManagementPage()");
		addBreadcrumb(pageLevelKey, controlName, "showControlRecordPage()");
		String url = getControlRecordPage();
		selectionContext.setCurrentArea("control");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeControlCreationPage(Control control) {
		setPageTitle("New "+getControlLabel(control));
		setPageIcon("/icons/nam/NewControl16.gif");
		setSectionTitle("Control Identification");
		controlWizard.setNewMode(true);
		
		String pageLevelKey = "control";
		String wizardLevelKey = "controlWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Controls", "showControlManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Control", "showControlWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showControlWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showControlWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showControlWizardPage('Documentation')");
		
		controlIdentificationSection.setOwner("controlWizard");
		controlConfigurationSection.setOwner("controlWizard");
		controlDocumentationSection.setOwner("controlWizard");
		
		sections.clear();
		sections.add(controlIdentificationSection);
		sections.add(controlConfigurationSection);
		sections.add(controlDocumentationSection);
		
		String url = getControlWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("control");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeControlUpdatePage(Control control) {
		setPageTitle(getControlLabel(control));
		setPageIcon("/icons/nam/Control16.gif");
		setSectionTitle("Control Overview");
		String controlName = control.getName();
		controlWizard.setNewMode(false);
		
		String pageLevelKey = "control";
		String wizardLevelKey = "controlWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Controls", "showControlManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(controlName, "showControlWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showControlWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showControlWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showControlWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showControlWizardPage('Documentation')");
		
		controlOverviewSection.setOwner("controlWizard");
		controlIdentificationSection.setOwner("controlWizard");
		controlConfigurationSection.setOwner("controlWizard");
		controlDocumentationSection.setOwner("controlWizard");
		
		sections.clear();
		sections.add(controlOverviewSection);
		sections.add(controlIdentificationSection);
		sections.add(controlConfigurationSection);
		sections.add(controlDocumentationSection);
		
		String url = getControlWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("control");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeControlManagementPage() {
		setPageTitle("Controls");
		setPageIcon("/icons/nam/Control16.gif");
		String pageLevelKey = "controlManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Controls", "showControlManagementPage()");
		String url = getControlManagementPage();
		selectionContext.setCurrentArea("control");
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
		setSectionType("control");
		setSectionName("Overview");
		setSectionTitle("Overview of Controls");
		setSectionIcon("/icons/nam/Control16.gif");
	}
	
	public String initializeControlSummaryView(Control control) {
		//String viewTitle = getControlLabel(control);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("control");
		setSectionName("Summary");
		setSectionTitle("Summary of Control Records");
		setSectionIcon("/icons/nam/Control16.gif");
		String viewLevelKey = "controlSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Controls", "showControlManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getControlLabel(Control control) {
		String label = "Control";
		String name = ControlUtil.getLabel(control);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Control> page = controlWizard.getPage();
		if (page != null)
			setSectionTitle("Control " + page.getName());
	}
	
	protected void updateState(Control control) {
		String controlName = NameUtil.capName(control.getName());
		setSectionTitle(controlName + " Control");
	}
	
}
