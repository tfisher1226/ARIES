package nam.model.transport;

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

import nam.model.Transport;
import nam.model.util.TransportUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("transportPageManager")
public class TransportPageManager extends AbstractPageManager<Transport> implements Serializable {
	
	@Inject
	private TransportWizard transportWizard;
	
	@Inject
	private TransportDataManager transportDataManager;
	
	@Inject
	private TransportListManager transportListManager;
	
	@Inject
	private TransportRecord_OverviewSection transportOverviewSection;
	
	@Inject
	private TransportRecord_IdentificationSection transportIdentificationSection;
	
	@Inject
	private TransportRecord_ConfigurationSection transportConfigurationSection;
	
	@Inject
	private TransportRecord_DocumentationSection transportDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public TransportPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("transport");
	}
	
	public void refreshLocal() {
		refreshLocal("transport");
	}
	
	public void refreshMembers() {
		refreshMembers("transport");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		transportDataManager.setScope(scope);
		transportListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		transportListManager.refresh();
	}
	
	public String getTransportListPage() {
		return "/nam/model/transport/transportListPage.xhtml";
	}
	
	public String getTransportTreePage() {
		return "/nam/model/transport/transportTreePage.xhtml";
	}
	
	public String getTransportSummaryPage() {
		return "/nam/model/transport/transportSummaryPage.xhtml";
	}
	
	public String getTransportRecordPage() {
		return "/nam/model/transport/transportRecordPage.xhtml";
	}
	
	public String getTransportWizardPage() {
		return "/nam/model/transport/transportWizardPage.xhtml";
	}
	
	public String getTransportManagementPage() {
		return "/nam/model/transport/transportManagementPage.xhtml";
	}
	
	public String initializeTransportListPage() {
		String pageLevelKey = "transportList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Transports", "showTransportManagementPage()");
		String url = getTransportListPage();
		selectionContext.setCurrentArea("transport");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeTransportTreePage() {
		String pageLevelKey = "transportTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Transports", "showTransportTreePage()");
		String url = getTransportTreePage();
		selectionContext.setCurrentArea("transport");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeTransportSummaryPage(Transport transport) {
		String pageLevelKey = "transportSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Transports", "showTransportSummaryPage()");
		String url = getTransportSummaryPage();
		selectionContext.setCurrentArea("transport");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeTransportRecordPage() {
		Transport transport = selectionContext.getSelection("transport");
		String transportName = TransportUtil.getLabel(transport);
		
		String pageLevelKey = "transportRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Transports", "showTransportManagementPage()");
		addBreadcrumb(pageLevelKey, transportName, "showTransportRecordPage()");
		String url = getTransportRecordPage();
		selectionContext.setCurrentArea("transport");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeTransportCreationPage(Transport transport) {
		setPageTitle("New "+getTransportLabel(transport));
		setPageIcon("/icons/nam/NewTransport16.gif");
		setSectionTitle("Transport Identification");
		transportWizard.setNewMode(true);
		
		String pageLevelKey = "transport";
		String wizardLevelKey = "transportWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Transports", "showTransportManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Transport", "showTransportWizardPage()"));
		
		
		transportIdentificationSection.setOwner("transportWizard");
		transportConfigurationSection.setOwner("transportWizard");
		transportDocumentationSection.setOwner("transportWizard");
		
		sections.clear();
		sections.add(transportIdentificationSection);
		sections.add(transportConfigurationSection);
		sections.add(transportDocumentationSection);
		
		String url = getTransportWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("transport");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeTransportUpdatePage(Transport transport) {
		setPageTitle(getTransportLabel(transport));
		setPageIcon("/icons/nam/Transport16.gif");
		setSectionTitle("Transport Overview");
		String transportName = TransportUtil.getLabel(transport);
		transportWizard.setNewMode(false);
		
		String pageLevelKey = "transport";
		String wizardLevelKey = "transportWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Transports", "showTransportManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(transportName, "showTransportWizardPage()"));
		
		
		transportOverviewSection.setOwner("transportWizard");
		transportIdentificationSection.setOwner("transportWizard");
		transportConfigurationSection.setOwner("transportWizard");
		transportDocumentationSection.setOwner("transportWizard");
		
		sections.clear();
		sections.add(transportOverviewSection);
		sections.add(transportIdentificationSection);
		sections.add(transportConfigurationSection);
		sections.add(transportDocumentationSection);
		
		String url = getTransportWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("transport");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeTransportManagementPage() {
		setPageTitle("Transports");
		setPageIcon("/icons/nam/Transport16.gif");
		String pageLevelKey = "transportManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Transports", "showTransportManagementPage()");
		String url = getTransportManagementPage();
		selectionContext.setCurrentArea("transport");
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
		setSectionType("transport");
		setSectionName("Overview");
		setSectionTitle("Overview of Transports");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeTransportSummaryView(Transport transport) {
		//String viewTitle = getTransportLabel(transport);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("transport");
		setSectionName("Summary");
		setSectionTitle("Summary of Transport Records");
		setSectionIcon("/icons/nam/Transport16.gif");
		String viewLevelKey = "transportSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Transports", "showTransportManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getTransportLabel(Transport transport) {
		String label = "Transport";
		String name = TransportUtil.getLabel(transport);
		if (name == null && transport.getName() != null)
			name = TransportUtil.getLabel(transport);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Transport> page = transportWizard.getPage();
		if (page != null)
			setSectionTitle("Transport " + page.getName());
	}
	
	protected void updateState(Transport transport) {
		String transportName = TransportUtil.getLabel(transport);
		setSectionTitle(transportName + " Transport");
	}
	
}
