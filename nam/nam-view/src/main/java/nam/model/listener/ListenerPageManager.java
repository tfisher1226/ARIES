package nam.model.listener;

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

import nam.model.Listener;
import nam.model.util.ListenerUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("listenerPageManager")
public class ListenerPageManager extends AbstractPageManager<Listener> implements Serializable {
	
	@Inject
	private ListenerWizard listenerWizard;

	@Inject
	private ListenerDataManager listenerDataManager;

	@Inject
	private ListenerListManager listenerListManager;

	@Inject
	private ListenerRecord_OverviewSection listenerOverviewSection;
	
	@Inject
	private ListenerRecord_IdentificationSection listenerIdentificationSection;
	
	@Inject
	private ListenerRecord_ConfigurationSection listenerConfigurationSection;
	
	@Inject
	private ListenerRecord_DocumentationSection listenerDocumentationSection;
	
	@Inject
	private ListenerRecord_ChannelsSection listenerChannelsSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ListenerPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("listener");
	}
	
	public void refreshLocal() {
		refreshLocal("listener");
	}
	
	public void refreshMembers() {
		refreshMembers("listener");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		listenerDataManager.setScope(scope);
		listenerListManager.refresh();
	}

	public void refreshMembers(String scope) {
		listenerListManager.refresh();
	}
	
	public String getListenerListPage() {
		return "/nam/model/listener/listenerListPage.xhtml";
	}
	
	public String getListenerTreePage() {
		return "/nam/model/listener/listenerTreePage.xhtml";
	}
	
	public String getListenerSummaryPage() {
		return "/nam/model/listener/listenerSummaryPage.xhtml";
	}
	
	public String getListenerRecordPage() {
		return "/nam/model/listener/listenerRecordPage.xhtml";
	}
	
	public String getListenerWizardPage() {
		return "/nam/model/listener/listenerWizardPage.xhtml";
	}
	
	public String getListenerManagementPage() {
		return "/nam/model/listener/listenerManagementPage.xhtml";
	}
	
	public String initializeListenerListPage() {
		String pageLevelKey = "listenerList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Listeners", "showListenerManagementPage()");
		String url = getListenerListPage();
		selectionContext.setCurrentArea("listener");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeListenerTreePage() {
		String pageLevelKey = "listenerTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Listeners", "showListenerTreePage()");
		String url = getListenerTreePage();
		selectionContext.setCurrentArea("listener");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeListenerSummaryPage(Listener listener) {
		String pageLevelKey = "listenerSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Listeners", "showListenerSummaryPage()");
		String url = getListenerSummaryPage();
		selectionContext.setCurrentArea("listener");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeListenerRecordPage() {
		Listener listener = selectionContext.getSelection("listener");
		String listenerName = ListenerUtil.getLabel(listener);
		
		String pageLevelKey = "listenerRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Listeners", "showListenerManagementPage()");
		addBreadcrumb(pageLevelKey, listenerName, "showListenerRecordPage()");
		String url = getListenerRecordPage();
		selectionContext.setCurrentArea("listener");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeListenerCreationPage(Listener listener) {
		setPageTitle("New "+getListenerLabel(listener));
		setPageIcon("/icons/nam/NewListener16.gif");
		setSectionTitle("Listener Identification");
		listenerWizard.setNewMode(true);
		
		String pageLevelKey = "listener";
		String wizardLevelKey = "listenerWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Listeners", "showListenerManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Listener", "showListenerWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showListenerWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showListenerWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showListenerWizardPage('Documentation')");
		
		listenerIdentificationSection.setOwner("listenerWizard");
		listenerConfigurationSection.setOwner("listenerWizard");
		listenerDocumentationSection.setOwner("listenerWizard");
		listenerChannelsSection.setOwner("listenerWizard");
		
		sections.clear();
		sections.add(listenerIdentificationSection);
		sections.add(listenerConfigurationSection);
		sections.add(listenerDocumentationSection);
		sections.add(listenerChannelsSection);
		
		String url = getListenerWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("listener");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeListenerUpdatePage(Listener listener) {
		setPageTitle(getListenerLabel(listener));
		setPageIcon("/icons/nam/Listener16.gif");
		setSectionTitle("Listener Overview");
		String listenerName = ListenerUtil.getLabel(listener);
		listenerWizard.setNewMode(false);
		
		String pageLevelKey = "listener";
		String wizardLevelKey = "listenerWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Listeners", "showListenerManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(listenerName, "showListenerWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showListenerWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showListenerWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showListenerWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showListenerWizardPage('Documentation')");
		
		listenerOverviewSection.setOwner("listenerWizard");
		listenerIdentificationSection.setOwner("listenerWizard");
		listenerConfigurationSection.setOwner("listenerWizard");
		listenerDocumentationSection.setOwner("listenerWizard");
		listenerChannelsSection.setOwner("listenerWizard");
		
		sections.clear();
		sections.add(listenerOverviewSection);
		sections.add(listenerIdentificationSection);
		sections.add(listenerConfigurationSection);
		sections.add(listenerDocumentationSection);
		sections.add(listenerChannelsSection);
		
		String url = getListenerWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("listener");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeListenerManagementPage() {
		setPageTitle("Listeners");
		setPageIcon("/icons/nam/Listener16.gif");
		String pageLevelKey = "listenerManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Listeners", "showListenerManagementPage()");
		String url = getListenerManagementPage();
		selectionContext.setCurrentArea("listener");
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
		setSectionType("listener");
		setSectionName("Overview");
		setSectionTitle("Overview of Listeners");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeListenerSummaryView(Listener listener) {
		//String viewTitle = getListenerLabel(listener);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("listener");
		setSectionName("Summary");
		setSectionTitle("Summary of Listener Records");
		setSectionIcon("/icons/nam/Listener16.gif");
		String viewLevelKey = "listenerSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Listeners", "showListenerManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getListenerLabel(Listener listener) {
		String label = "Listener";
		String name = ListenerUtil.getLabel(listener);
		if (name == null && listener.getName() != null)
			name = ListenerUtil.getLabel(listener);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Listener> page = listenerWizard.getPage();
		if (page != null)
			setSectionTitle("Listener " + page.getName());
	}
	
	protected void updateState(Listener listener) {
		String listenerName = ListenerUtil.getLabel(listener);
		setSectionTitle(listenerName + " Listener");
	}

}
