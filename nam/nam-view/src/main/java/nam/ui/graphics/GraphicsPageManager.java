package nam.ui.graphics;

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

import nam.ui.Graphics;
import nam.ui.design.SelectionContext;
import nam.ui.util.GraphicsUtil;


@SessionScoped
@Named("graphicsPageManager")
public class GraphicsPageManager extends AbstractPageManager<Graphics> implements Serializable {
	
	@Inject
	private GraphicsWizard graphicsWizard;
	
	@Inject
	private GraphicsDataManager graphicsDataManager;
	
	@Inject
	private GraphicsListManager graphicsListManager;
	
	@Inject
	private GraphicsRecord_OverviewSection graphicsOverviewSection;
	
	@Inject
	private GraphicsRecord_IdentificationSection graphicsIdentificationSection;
	
	@Inject
	private GraphicsRecord_ConfigurationSection graphicsConfigurationSection;
	
	@Inject
	private GraphicsRecord_DocumentationSection graphicsDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public GraphicsPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("graphics");
	}
	
	public void refreshLocal() {
		refreshLocal("graphics");
	}
	
	public void refreshMembers() {
		refreshMembers("graphics");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		graphicsDataManager.setScope(scope);
		graphicsListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		graphicsListManager.refresh();
	}
	
	public String getGraphicsListPage() {
		return "/nam/ui/graphics/graphicsListPage.xhtml";
	}
	
	public String getGraphicsTreePage() {
		return "/nam/ui/graphics/graphicsTreePage.xhtml";
	}
	
	public String getGraphicsSummaryPage() {
		return "/nam/ui/graphics/graphicsSummaryPage.xhtml";
	}
	
	public String getGraphicsRecordPage() {
		return "/nam/ui/graphics/graphicsRecordPage.xhtml";
	}
	
	public String getGraphicsWizardPage() {
		return "/nam/ui/graphics/graphicsWizardPage.xhtml";
	}
	
	public String getGraphicsManagementPage() {
		return "/nam/ui/graphics/graphicsManagementPage.xhtml";
	}
	
	public String initializeGraphicsListPage() {
		String pageLevelKey = "graphicsList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Graphicss", "showGraphicsManagementPage()");
		String url = getGraphicsListPage();
		selectionContext.setCurrentArea("graphics");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeGraphicsTreePage() {
		String pageLevelKey = "graphicsTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Graphicss", "showGraphicsTreePage()");
		String url = getGraphicsTreePage();
		selectionContext.setCurrentArea("graphics");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeGraphicsSummaryPage(Graphics graphics) {
		String pageLevelKey = "graphicsSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Graphicss", "showGraphicsSummaryPage()");
		String url = getGraphicsSummaryPage();
		selectionContext.setCurrentArea("graphics");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeGraphicsRecordPage() {
		Graphics graphics = selectionContext.getSelection("graphics");
		String graphicsName = graphics.getName();
		
		String pageLevelKey = "graphicsRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Graphicss", "showGraphicsManagementPage()");
		addBreadcrumb(pageLevelKey, graphicsName, "showGraphicsRecordPage()");
		String url = getGraphicsRecordPage();
		selectionContext.setCurrentArea("graphics");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeGraphicsCreationPage(Graphics graphics) {
		setPageTitle("New "+getGraphicsLabel(graphics));
		setPageIcon("/icons/nam/NewGraphics16.gif");
		setSectionTitle("Graphics Identification");
		graphicsWizard.setNewMode(true);
		
		String pageLevelKey = "graphics";
		String wizardLevelKey = "graphicsWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Graphicss", "showGraphicsManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Graphics", "showGraphicsWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showGraphicsWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showGraphicsWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showGraphicsWizardPage('Documentation')");
		
		graphicsIdentificationSection.setOwner("graphicsWizard");
		graphicsConfigurationSection.setOwner("graphicsWizard");
		graphicsDocumentationSection.setOwner("graphicsWizard");
		
		sections.clear();
		sections.add(graphicsIdentificationSection);
		sections.add(graphicsConfigurationSection);
		sections.add(graphicsDocumentationSection);
		
		String url = getGraphicsWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("graphics");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeGraphicsUpdatePage(Graphics graphics) {
		setPageTitle(getGraphicsLabel(graphics));
		setPageIcon("/icons/nam/Graphics16.gif");
		setSectionTitle("Graphics Overview");
		String graphicsName = graphics.getName();
		graphicsWizard.setNewMode(false);
		
		String pageLevelKey = "graphics";
		String wizardLevelKey = "graphicsWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Graphicss", "showGraphicsManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(graphicsName, "showGraphicsWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showGraphicsWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showGraphicsWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showGraphicsWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showGraphicsWizardPage('Documentation')");
		
		graphicsOverviewSection.setOwner("graphicsWizard");
		graphicsIdentificationSection.setOwner("graphicsWizard");
		graphicsConfigurationSection.setOwner("graphicsWizard");
		graphicsDocumentationSection.setOwner("graphicsWizard");
		
		sections.clear();
		sections.add(graphicsOverviewSection);
		sections.add(graphicsIdentificationSection);
		sections.add(graphicsConfigurationSection);
		sections.add(graphicsDocumentationSection);
		
		String url = getGraphicsWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("graphics");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeGraphicsManagementPage() {
		setPageTitle("Graphicses");
		setPageIcon("/icons/nam/Graphics16.gif");
		String pageLevelKey = "graphicsManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Graphicses", "showGraphicsManagementPage()");
		String url = getGraphicsManagementPage();
		selectionContext.setCurrentArea("graphics");
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
		setSectionType("graphics");
		setSectionName("Overview");
		setSectionTitle("Overview of Graphicses");
		setSectionIcon("/icons/nam/Graphics16.gif");
	}
	
	public String initializeGraphicsSummaryView(Graphics graphics) {
		//String viewTitle = getGraphicsLabel(graphics);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("graphics");
		setSectionName("Summary");
		setSectionTitle("Summary of Graphics");
		setSectionIcon("/icons/nam/Graphics16.gif");
		String viewLevelKey = "graphicsSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Graphicss", "showGraphicsManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getGraphicsLabel(Graphics graphics) {
		String label = "Graphics";
		String name = GraphicsUtil.getLabel(graphics);
		if (name == null && graphics.getName() != null)
			name = NameUtil.capName(graphics.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Graphics> page = graphicsWizard.getPage();
		if (page != null)
			setSectionTitle("Graphics " + page.getName());
	}
	
	protected void updateState(Graphics graphics) {
		String graphicsName = NameUtil.capName(graphics.getName());
		setSectionTitle(graphicsName + " Graphics");
	}
	
}
