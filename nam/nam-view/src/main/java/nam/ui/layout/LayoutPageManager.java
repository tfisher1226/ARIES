package nam.ui.layout;

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

import nam.ui.Layout;
import nam.ui.design.SelectionContext;
import nam.ui.util.LayoutUtil;


@SessionScoped
@Named("layoutPageManager")
public class LayoutPageManager extends AbstractPageManager<Layout> implements Serializable {
	
	@Inject
	private LayoutWizard layoutWizard;
	
	@Inject
	private LayoutDataManager layoutDataManager;
	
	@Inject
	private LayoutListManager layoutListManager;
	
	@Inject
	private LayoutRecord_OverviewSection layoutOverviewSection;
	
	@Inject
	private LayoutRecord_IdentificationSection layoutIdentificationSection;
	
	@Inject
	private LayoutRecord_ConfigurationSection layoutConfigurationSection;
	
	@Inject
	private LayoutRecord_DocumentationSection layoutDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public LayoutPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("layout");
	}
	
	public void refreshLocal() {
		refreshLocal("layout");
	}
	
	public void refreshMembers() {
		refreshMembers("layout");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		layoutDataManager.setScope(scope);
		layoutListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		layoutListManager.refresh();
	}
	
	public String getLayoutListPage() {
		return "/nam/ui/layout/layoutListPage.xhtml";
	}
	
	public String getLayoutTreePage() {
		return "/nam/ui/layout/layoutTreePage.xhtml";
	}
	
	public String getLayoutSummaryPage() {
		return "/nam/ui/layout/layoutSummaryPage.xhtml";
	}
	
	public String getLayoutRecordPage() {
		return "/nam/ui/layout/layoutRecordPage.xhtml";
	}
	
	public String getLayoutWizardPage() {
		return "/nam/ui/layout/layoutWizardPage.xhtml";
	}
	
	public String getLayoutManagementPage() {
		return "/nam/ui/layout/layoutManagementPage.xhtml";
	}
	
	public String initializeLayoutListPage() {
		String pageLevelKey = "layoutList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Layouts", "showLayoutManagementPage()");
		String url = getLayoutListPage();
		selectionContext.setCurrentArea("layout");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeLayoutTreePage() {
		String pageLevelKey = "layoutTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Layouts", "showLayoutTreePage()");
		String url = getLayoutTreePage();
		selectionContext.setCurrentArea("layout");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeLayoutSummaryPage(Layout layout) {
		String pageLevelKey = "layoutSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Layouts", "showLayoutSummaryPage()");
		String url = getLayoutSummaryPage();
		selectionContext.setCurrentArea("layout");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeLayoutRecordPage() {
		Layout layout = selectionContext.getSelection("layout");
		String layoutName = layout.getName();
		
		String pageLevelKey = "layoutRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Layouts", "showLayoutManagementPage()");
		addBreadcrumb(pageLevelKey, layoutName, "showLayoutRecordPage()");
		String url = getLayoutRecordPage();
		selectionContext.setCurrentArea("layout");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeLayoutCreationPage(Layout layout) {
		setPageTitle("New "+getLayoutLabel(layout));
		setPageIcon("/icons/nam/NewLayout16.gif");
		setSectionTitle("Layout Identification");
		layoutWizard.setNewMode(true);
		
		String pageLevelKey = "layout";
		String wizardLevelKey = "layoutWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Layouts", "showLayoutManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Layout", "showLayoutWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showLayoutWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showLayoutWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showLayoutWizardPage('Documentation')");
		
		layoutIdentificationSection.setOwner("layoutWizard");
		layoutConfigurationSection.setOwner("layoutWizard");
		layoutDocumentationSection.setOwner("layoutWizard");
		
		sections.clear();
		sections.add(layoutIdentificationSection);
		sections.add(layoutConfigurationSection);
		sections.add(layoutDocumentationSection);
		
		String url = getLayoutWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("layout");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeLayoutUpdatePage(Layout layout) {
		setPageTitle(getLayoutLabel(layout));
		setPageIcon("/icons/nam/Layout16.gif");
		setSectionTitle("Layout Overview");
		String layoutName = layout.getName();
		layoutWizard.setNewMode(false);
		
		String pageLevelKey = "layout";
		String wizardLevelKey = "layoutWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Layouts", "showLayoutManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(layoutName, "showLayoutWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showLayoutWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showLayoutWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showLayoutWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showLayoutWizardPage('Documentation')");
		
		layoutOverviewSection.setOwner("layoutWizard");
		layoutIdentificationSection.setOwner("layoutWizard");
		layoutConfigurationSection.setOwner("layoutWizard");
		layoutDocumentationSection.setOwner("layoutWizard");
		
		sections.clear();
		sections.add(layoutOverviewSection);
		sections.add(layoutIdentificationSection);
		sections.add(layoutConfigurationSection);
		sections.add(layoutDocumentationSection);
		
		String url = getLayoutWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("layout");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeLayoutManagementPage() {
		setPageTitle("Layouts");
		setPageIcon("/icons/nam/Layout16.gif");
		String pageLevelKey = "layoutManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Layouts", "showLayoutManagementPage()");
		String url = getLayoutManagementPage();
		selectionContext.setCurrentArea("layout");
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
		setSectionType("layout");
		setSectionName("Overview");
		setSectionTitle("Overview of Layouts");
		setSectionIcon("/icons/nam/Layout16.gif");
	}
	
	public String initializeLayoutSummaryView(Layout layout) {
		//String viewTitle = getLayoutLabel(layout);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("layout");
		setSectionName("Summary");
		setSectionTitle("Summary of Layout Records");
		setSectionIcon("/icons/nam/Layout16.gif");
		String viewLevelKey = "layoutSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Layouts", "showLayoutManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getLayoutLabel(Layout layout) {
		String label = "Layout";
		String name = LayoutUtil.getLabel(layout);
		if (name == null && layout.getName() != null)
			name = NameUtil.capName(layout.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Layout> page = layoutWizard.getPage();
		if (page != null)
			setSectionTitle("Layout " + page.getName());
	}
	
	protected void updateState(Layout layout) {
		String layoutName = NameUtil.capName(layout.getName());
		setSectionTitle(layoutName + " Layout");
	}
	
}
