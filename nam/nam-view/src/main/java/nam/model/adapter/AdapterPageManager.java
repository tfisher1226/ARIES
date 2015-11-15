package nam.model.adapter;

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

import nam.model.Adapter;
import nam.model.util.AdapterUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("adapterPageManager")
public class AdapterPageManager extends AbstractPageManager<Adapter> implements Serializable {
	
	@Inject
	private AdapterWizard adapterWizard;
	
	@Inject
	private AdapterDataManager adapterDataManager;
	
	@Inject
	private AdapterListManager adapterListManager;
	
	@Inject
	private AdapterRecord_OverviewSection adapterOverviewSection;
	
	@Inject
	private AdapterRecord_IdentificationSection adapterIdentificationSection;
	
	@Inject
	private AdapterRecord_ConfigurationSection adapterConfigurationSection;
	
	@Inject
	private AdapterRecord_DocumentationSection adapterDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public AdapterPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("adapter");
	}
	
	public void refreshLocal() {
		refreshLocal("adapter");
	}
	
	public void refreshMembers() {
		refreshMembers("adapter");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		adapterDataManager.setScope(scope);
		adapterListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		adapterListManager.refresh();
	}
	
	public String getAdapterListPage() {
		return "/nam/model/adapter/adapterListPage.xhtml";
	}
	
	public String getAdapterTreePage() {
		return "/nam/model/adapter/adapterTreePage.xhtml";
	}
	
	public String getAdapterSummaryPage() {
		return "/nam/model/adapter/adapterSummaryPage.xhtml";
	}
	
	public String getAdapterRecordPage() {
		return "/nam/model/adapter/adapterRecordPage.xhtml";
	}
	
	public String getAdapterWizardPage() {
		return "/nam/model/adapter/adapterWizardPage.xhtml";
	}
	
	public String getAdapterManagementPage() {
		return "/nam/model/adapter/adapterManagementPage.xhtml";
	}
	
	public String initializeAdapterListPage() {
		String pageLevelKey = "adapterList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Adapters", "showAdapterManagementPage()");
		String url = getAdapterListPage();
		selectionContext.setCurrentArea("adapter");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeAdapterTreePage() {
		String pageLevelKey = "adapterTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Adapters", "showAdapterTreePage()");
		String url = getAdapterTreePage();
		selectionContext.setCurrentArea("adapter");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeAdapterSummaryPage(Adapter adapter) {
		String pageLevelKey = "adapterSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Adapters", "showAdapterSummaryPage()");
		String url = getAdapterSummaryPage();
		selectionContext.setCurrentArea("adapter");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeAdapterRecordPage() {
		Adapter adapter = selectionContext.getSelection("adapter");
		String adapterName = AdapterUtil.getLabel(adapter);
		
		String pageLevelKey = "adapterRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Adapters", "showAdapterManagementPage()");
		addBreadcrumb(pageLevelKey, adapterName, "showAdapterRecordPage()");
		String url = getAdapterRecordPage();
		selectionContext.setCurrentArea("adapter");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeAdapterCreationPage(Adapter adapter) {
		setPageTitle("New "+getAdapterLabel(adapter));
		setPageIcon("/icons/nam/NewAdapter16.gif");
		setSectionTitle("Adapter Identification");
		adapterWizard.setNewMode(true);
		
		String pageLevelKey = "adapter";
		String wizardLevelKey = "adapterWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Adapters", "showAdapterManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Adapter", "showAdapterWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showAdapterWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showAdapterWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showAdapterWizardPage('Documentation')");
		
		adapterIdentificationSection.setOwner("adapterWizard");
		adapterConfigurationSection.setOwner("adapterWizard");
		adapterDocumentationSection.setOwner("adapterWizard");
		
		sections.clear();
		sections.add(adapterIdentificationSection);
		sections.add(adapterConfigurationSection);
		sections.add(adapterDocumentationSection);
		
		String url = getAdapterWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("adapter");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeAdapterUpdatePage(Adapter adapter) {
		setPageTitle(getAdapterLabel(adapter));
		setPageIcon("/icons/nam/Adapter16.gif");
		setSectionTitle("Adapter Overview");
		String adapterName = AdapterUtil.getLabel(adapter);
		adapterWizard.setNewMode(false);
		
		String pageLevelKey = "adapter";
		String wizardLevelKey = "adapterWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Adapters", "showAdapterManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(adapterName, "showAdapterWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showAdapterWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showAdapterWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showAdapterWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showAdapterWizardPage('Documentation')");
		
		adapterOverviewSection.setOwner("adapterWizard");
		adapterIdentificationSection.setOwner("adapterWizard");
		adapterConfigurationSection.setOwner("adapterWizard");
		adapterDocumentationSection.setOwner("adapterWizard");
		
		sections.clear();
		sections.add(adapterOverviewSection);
		sections.add(adapterIdentificationSection);
		sections.add(adapterConfigurationSection);
		sections.add(adapterDocumentationSection);
		
		String url = getAdapterWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("adapter");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeAdapterManagementPage() {
		setPageTitle("Adapters");
		setPageIcon("/icons/nam/Adapter16.gif");
		String pageLevelKey = "adapterManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Adapters", "showAdapterManagementPage()");
		String url = getAdapterManagementPage();
		selectionContext.setCurrentArea("adapter");
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
		setSectionType("adapter");
		setSectionName("Overview");
		setSectionTitle("Overview of Adapters");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeAdapterSummaryView(Adapter adapter) {
		//String viewTitle = getAdapterLabel(adapter);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("adapter");
		setSectionName("Summary");
		setSectionTitle("Summary of Adapter Records");
		setSectionIcon("/icons/nam/Adapter16.gif");
		String viewLevelKey = "adapterSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Adapters", "showAdapterManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getAdapterLabel(Adapter adapter) {
		String label = "Adapter";
		String name = AdapterUtil.getLabel(adapter);
		if (name == null && adapter.getName() != null)
			name = AdapterUtil.getLabel(adapter);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Adapter> page = adapterWizard.getPage();
		if (page != null)
			setSectionTitle("Adapter " + page.getName());
	}
	
	protected void updateState(Adapter adapter) {
		String adapterName = AdapterUtil.getLabel(adapter);
		setSectionTitle(adapterName + " Adapter");
	}
	
}
