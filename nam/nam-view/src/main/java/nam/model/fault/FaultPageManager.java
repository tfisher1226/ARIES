package nam.model.fault;

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

import nam.model.Fault;
import nam.model.util.FaultUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("faultPageManager")
public class FaultPageManager extends AbstractPageManager<Fault> implements Serializable {
	
	@Inject
	private FaultWizard faultWizard;
	
	@Inject
	private FaultDataManager faultDataManager;
	
	@Inject
	private FaultListManager faultListManager;
	
	@Inject
	private FaultRecord_OverviewSection faultOverviewSection;
	
	@Inject
	private FaultRecord_IdentificationSection faultIdentificationSection;
	
	@Inject
	private FaultRecord_ConfigurationSection faultConfigurationSection;
	
	@Inject
	private FaultRecord_DocumentationSection faultDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public FaultPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("fault");
	}
	
	public void refreshLocal() {
		refreshLocal("fault");
	}
	
	public void refreshMembers() {
		refreshMembers("fault");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		faultDataManager.setScope(scope);
		faultListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		faultListManager.refresh();
	}
	
	public String getFaultListPage() {
		return "/nam/model/fault/faultListPage.xhtml";
	}
	
	public String getFaultTreePage() {
		return "/nam/model/fault/faultTreePage.xhtml";
	}
	
	public String getFaultSummaryPage() {
		return "/nam/model/fault/faultSummaryPage.xhtml";
	}
	
	public String getFaultRecordPage() {
		return "/nam/model/fault/faultRecordPage.xhtml";
	}
	
	public String getFaultWizardPage() {
		return "/nam/model/fault/faultWizardPage.xhtml";
	}
	
	public String getFaultManagementPage() {
		return "/nam/model/fault/faultManagementPage.xhtml";
	}
	
	public String initializeFaultListPage() {
		String pageLevelKey = "faultList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Faults", "showFaultManagementPage()");
		String url = getFaultListPage();
		selectionContext.setCurrentArea("fault");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeFaultTreePage() {
		String pageLevelKey = "faultTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Faults", "showFaultTreePage()");
		String url = getFaultTreePage();
		selectionContext.setCurrentArea("fault");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeFaultSummaryPage(Fault fault) {
		String pageLevelKey = "faultSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Faults", "showFaultSummaryPage()");
		String url = getFaultSummaryPage();
		selectionContext.setCurrentArea("fault");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeFaultRecordPage() {
		Fault fault = selectionContext.getSelection("fault");
		String faultName = FaultUtil.getLabel(fault);
		
		String pageLevelKey = "faultRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Faults", "showFaultManagementPage()");
		addBreadcrumb(pageLevelKey, faultName, "showFaultRecordPage()");
		String url = getFaultRecordPage();
		selectionContext.setCurrentArea("fault");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeFaultCreationPage(Fault fault) {
		setPageTitle("New "+getFaultLabel(fault));
		setPageIcon("/icons/nam/NewFault16.gif");
		setSectionTitle("Fault Identification");
		faultWizard.setNewMode(true);
		
		String pageLevelKey = "fault";
		String wizardLevelKey = "faultWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Faults", "showFaultManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Fault", "showFaultWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showFaultWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showFaultWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showFaultWizardPage('Documentation')");
		
		faultIdentificationSection.setOwner("faultWizard");
		faultConfigurationSection.setOwner("faultWizard");
		faultDocumentationSection.setOwner("faultWizard");
		
		sections.clear();
		sections.add(faultIdentificationSection);
		sections.add(faultConfigurationSection);
		sections.add(faultDocumentationSection);
		
		String url = getFaultWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("fault");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeFaultUpdatePage(Fault fault) {
		setPageTitle(getFaultLabel(fault));
		setPageIcon("/icons/nam/Fault16.gif");
		setSectionTitle("Fault Overview");
		String faultName = FaultUtil.getLabel(fault);
		faultWizard.setNewMode(false);
		
		String pageLevelKey = "fault";
		String wizardLevelKey = "faultWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Faults", "showFaultManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(faultName, "showFaultWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showFaultWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showFaultWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showFaultWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showFaultWizardPage('Documentation')");
		
		faultOverviewSection.setOwner("faultWizard");
		faultIdentificationSection.setOwner("faultWizard");
		faultConfigurationSection.setOwner("faultWizard");
		faultDocumentationSection.setOwner("faultWizard");
		
		sections.clear();
		sections.add(faultOverviewSection);
		sections.add(faultIdentificationSection);
		sections.add(faultConfigurationSection);
		sections.add(faultDocumentationSection);
		
		String url = getFaultWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("fault");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeFaultManagementPage() {
		setPageTitle("Faults");
		setPageIcon("/icons/nam/Fault16.gif");
		String pageLevelKey = "faultManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Faults", "showFaultManagementPage()");
		String url = getFaultManagementPage();
		selectionContext.setCurrentArea("fault");
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
		setSectionType("fault");
		setSectionName("Overview");
		setSectionTitle("Overview of Faults");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeFaultSummaryView(Fault fault) {
		//String viewTitle = getFaultLabel(fault);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("fault");
		setSectionName("Summary");
		setSectionTitle("Summary of Fault Records");
		setSectionIcon("/icons/nam/Fault16.gif");
		String viewLevelKey = "faultSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Faults", "showFaultManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getFaultLabel(Fault fault) {
		String label = "Fault";
		String name = FaultUtil.getLabel(fault);
		if (name == null && fault.getName() != null)
			name = FaultUtil.getLabel(fault);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}

	protected void updateState() {
		AbstractWizardPage<Fault> page = faultWizard.getPage();
		if (page != null)
			setSectionTitle("Fault " + page.getName());
	}

	protected void updateState(Fault fault) {
		String faultName = FaultUtil.getLabel(fault);
		setSectionTitle(faultName + " Fault");
	}
	
}
