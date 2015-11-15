package nam.model.property;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Property;
import nam.model.util.PropertyUtil;
import nam.ui.design.SelectionContext;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractPageManager;
import org.aries.ui.AbstractWizardPage;
import org.aries.ui.Breadcrumb;
import org.aries.ui.PageManager;
import org.aries.util.NameUtil;


@SessionScoped
@Named("propertyPageManager")
public class PropertyPageManager extends AbstractPageManager<Property> implements Serializable {
	
	@Inject
	private PropertyWizard propertyWizard;
	
	@Inject
	private PropertyDataManager propertyDataManager;
	
	@Inject
	private PropertyListManager propertyListManager;
	
	@Inject
	private PropertyRecord_OverviewSection propertyOverviewSection;
	
//	@Inject
//	private PropertyRecord_IdentificationSection propertyIdentificationSection;
//	
//	@Inject
//	private PropertyRecord_ConfigurationSection propertyConfigurationSection;
//	
//	@Inject
//	private PropertyRecord_DocumentationSection propertyDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public PropertyPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("property");
	}
	
	public void refreshLocal() {
		refreshLocal("property");
	}
	
	public void refreshMembers() {
		refreshMembers("property");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		propertyDataManager.setScope(scope);
		propertyListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		propertyListManager.refresh();
	}
	
	public String getPropertyListPage() {
		return "/nam/model/property/propertyListPage.xhtml";
	}
	
	public String getPropertyTreePage() {
		return "/nam/model/property/propertyTreePage.xhtml";
	}
	
	public String getPropertySummaryPage() {
		return "/nam/model/property/propertySummaryPage.xhtml";
	}
	
	public String getPropertyRecordPage() {
		return "/nam/model/property/propertyRecordPage.xhtml";
	}
	
	public String getPropertyWizardPage() {
		return "/nam/model/property/propertyWizardPage.xhtml";
	}
	
	public String getPropertyManagementPage() {
		return "/nam/model/property/propertyManagementPage.xhtml";
	}
	
	public String initializePropertyListPage() {
		String pageLevelKey = "propertyList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Propertys", "showPropertyManagementPage()");
		String url = getPropertyListPage();
		selectionContext.setCurrentArea("property");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePropertyTreePage() {
		String pageLevelKey = "propertyTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Propertys", "showPropertyTreePage()");
		String url = getPropertyTreePage();
		selectionContext.setCurrentArea("property");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePropertySummaryPage(Property property) {
		String pageLevelKey = "propertySummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Propertys", "showPropertySummaryPage()");
		String url = getPropertySummaryPage();
		selectionContext.setCurrentArea("property");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePropertyRecordPage() {
		Property property = selectionContext.getSelection("property");
		String propertyName = PropertyUtil.getLabel(property);
		
		String pageLevelKey = "propertyRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Propertys", "showPropertyManagementPage()");
		addBreadcrumb(pageLevelKey, propertyName, "showPropertyRecordPage()");
		String url = getPropertyRecordPage();
		selectionContext.setCurrentArea("property");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializePropertyCreationPage(Property property) {
		setPageTitle("New "+getPropertyLabel(property));
		setPageIcon("/icons/nam/NewProperty16.gif");
		setSectionTitle("Property Identification");
		propertyWizard.setNewMode(true);
		
		String pageLevelKey = "property";
		String wizardLevelKey = "propertyWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Propertys", "showPropertyManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Property", "showPropertyWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showPropertyWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showPropertyWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showPropertyWizardPage('Documentation')");
		
//		propertyIdentificationSection.setOwner("propertyWizard");
//		propertyConfigurationSection.setOwner("propertyWizard");
//		propertyDocumentationSection.setOwner("propertyWizard");
		
		sections.clear();
//		sections.add(propertyIdentificationSection);
//		sections.add(propertyConfigurationSection);
//		sections.add(propertyDocumentationSection);
		
		String url = getPropertyWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("property");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializePropertyUpdatePage(Property property) {
		setPageTitle(getPropertyLabel(property));
		setPageIcon("/icons/nam/Property16.gif");
		setSectionTitle("Property Overview");
		String propertyName = PropertyUtil.getLabel(property);
		propertyWizard.setNewMode(false);
		
		String pageLevelKey = "property";
		String wizardLevelKey = "propertyWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Propertys", "showPropertyManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(propertyName, "showPropertyWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showPropertyWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showPropertyWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showPropertyWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showPropertyWizardPage('Documentation')");
		
		propertyOverviewSection.setOwner("propertyWizard");
//		propertyIdentificationSection.setOwner("propertyWizard");
//		propertyConfigurationSection.setOwner("propertyWizard");
//		propertyDocumentationSection.setOwner("propertyWizard");
		
		sections.clear();
		sections.add(propertyOverviewSection);
//		sections.add(propertyIdentificationSection);
//		sections.add(propertyConfigurationSection);
//		sections.add(propertyDocumentationSection);
		
		String url = getPropertyWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("property");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializePropertyManagementPage() {
		setPageTitle("Propertys");
		setPageIcon("/icons/nam/Property16.gif");
		String pageLevelKey = "propertyManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Propertys", "showPropertyManagementPage()");
		String url = getPropertyManagementPage();
		selectionContext.setCurrentArea("property");
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
		setSectionType("property");
		setSectionName("Overview");
		setSectionTitle("Overview of Propertys");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializePropertySummaryView(Property property) {
		//String viewTitle = getPropertyLabel(property);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("property");
		setSectionName("Summary");
		setSectionTitle("Summary of Property Records");
		setSectionIcon("/icons/nam/Property16.gif");
		String viewLevelKey = "propertySummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Propertys", "showPropertyManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getPropertyLabel(Property property) {
		String label = "Property";
		String name = PropertyUtil.getLabel(property);
		if (name == null && property.getName() != null)
			name = PropertyUtil.getLabel(property);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}

	protected void updateState() {
		AbstractWizardPage<Property> page = propertyWizard.getPage();
		if (page != null)
			setSectionTitle("Property " + page.getName());
	}
	
	protected void updateState(Property property) {
		String propertyName = PropertyUtil.getLabel(property);
		setSectionTitle(propertyName + " Property");
	}
	
}
