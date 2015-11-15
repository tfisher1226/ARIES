package nam.model.attribute;

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

import nam.model.Attribute;
import nam.model.util.AttributeUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("attributePageManager")
public class AttributePageManager extends AbstractPageManager<Attribute> implements Serializable {

	@Inject
	private AttributeWizard attributeWizard;
	
	@Inject
	private AttributeDataManager attributeDataManager;
	
	@Inject
	private AttributeListManager attributeListManager;
	
	@Inject
	private AttributeRecord_OverviewSection attributeOverviewSection;
	
	@Inject
	private AttributeRecord_IdentificationSection attributeIdentificationSection;
	
	@Inject
	private AttributeRecord_ConfigurationSection attributeConfigurationSection;
	
	@Inject
	private AttributeRecord_DocumentationSection attributeDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public AttributePageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("attribute");
	}
	
	public void refreshLocal() {
		refreshLocal("attribute");
	}
	
	public void refreshMembers() {
		refreshMembers("attribute");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		attributeDataManager.setScope(scope);
		attributeListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		attributeListManager.refresh();
	}
	
	public String getAttributeListPage() {
		return "/nam/model/attribute/attributeListPage.xhtml";
	}
	
	public String getAttributeTreePage() {
		return "/nam/model/attribute/attributeTreePage.xhtml";
	}

	public String getAttributeSummaryPage() {
		return "/nam/model/attribute/attributeSummaryPage.xhtml";
	}

	public String getAttributeRecordPage() {
		return "/nam/model/attribute/attributeRecordPage.xhtml";
	}
	
	public String getAttributeWizardPage() {
		return "/nam/model/attribute/attributeWizardPage.xhtml";
	}

	public String getAttributeManagementPage() {
		return "/nam/model/attribute/attributeManagementPage.xhtml";
	}
	
	public String initializeAttributeListPage() {
		String pageLevelKey = "attributeList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Attributes", "showAttributeManagementPage()");
		String url = getAttributeListPage();
		selectionContext.setCurrentArea("attribute");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeAttributeTreePage() {
		String pageLevelKey = "attributeTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Attributes", "showAttributeTreePage()");
		String url = getAttributeTreePage();
		selectionContext.setCurrentArea("attribute");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeAttributeSummaryPage(Attribute attribute) {
		String pageLevelKey = "attributeSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Attributes", "showAttributeSummaryPage()");
		String url = getAttributeSummaryPage();
		selectionContext.setCurrentArea("attribute");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeAttributeRecordPage() {
		Attribute attribute = selectionContext.getSelection("attribute");
		String attributeName = attribute.getName();
		
		String pageLevelKey = "attributeRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Attributes", "showAttributeManagementPage()");
		addBreadcrumb(pageLevelKey, attributeName, "showAttributeRecordPage()");
		String url = getAttributeRecordPage();
		selectionContext.setCurrentArea("attribute");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeAttributeCreationPage(Attribute attribute) {
		setPageTitle("New "+getAttributeLabel(attribute));
		setPageIcon("/icons/nam/NewAttribute16.gif");
		setSectionTitle("Attribute Identification");
		attributeWizard.setNewMode(true);
		
		String pageLevelKey = "attribute";
		String wizardLevelKey = "attributeWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Attributes", "showAttributeManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Attribute", "showAttributeWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showAttributeWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showAttributeWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showAttributeWizardPage('Documentation')");
		
		attributeIdentificationSection.setOwner("attributeWizard");
		attributeConfigurationSection.setOwner("attributeWizard");
		attributeDocumentationSection.setOwner("attributeWizard");
		
		sections.clear();
		sections.add(attributeIdentificationSection);
		sections.add(attributeConfigurationSection);
		sections.add(attributeDocumentationSection);
		
		String url = getAttributeWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("attribute");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeAttributeUpdatePage(Attribute attribute) {
		setPageTitle(getAttributeLabel(attribute));
		setPageIcon("/icons/nam/Attribute16.gif");
		setSectionTitle("Attribute Overview");
		String attributeName = attribute.getName();
		attributeWizard.setNewMode(false);
		
		String pageLevelKey = "attribute";
		String wizardLevelKey = "attributeWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Attributes", "showAttributeManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(attributeName, "showAttributeWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showAttributeWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showAttributeWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showAttributeWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showAttributeWizardPage('Documentation')");
		
		attributeOverviewSection.setOwner("attributeWizard");
		attributeIdentificationSection.setOwner("attributeWizard");
		attributeConfigurationSection.setOwner("attributeWizard");
		attributeDocumentationSection.setOwner("attributeWizard");
		
		sections.clear();
		sections.add(attributeOverviewSection);
		sections.add(attributeIdentificationSection);
		sections.add(attributeConfigurationSection);
		sections.add(attributeDocumentationSection);
		
		String url = getAttributeWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("attribute");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
		
	public String initializeAttributeManagementPage() {
		setPageTitle("Attributes");
		setPageIcon("/icons/nam/Attribute16.gif");
		String pageLevelKey = "attributeManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Attributes", "showAttributeManagementPage()");
		String url = getAttributeManagementPage();
		selectionContext.setCurrentArea("attribute");
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
		setSectionType("attribute");
		setSectionName("Overview");
		setSectionTitle("Overview of Attributes");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeAttributeSummaryView(Attribute attribute) {
		//String viewTitle = getAttributeLabel(attribute);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("attribute");
		setSectionName("Summary");
		setSectionTitle("Summary of Attribute Records");
		setSectionIcon("/icons/nam/Attribute16.gif");
		String viewLevelKey = "attributeSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Attributes", "showAttributeManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}

	protected String getAttributeLabel(Attribute attribute) {
		String label = "Attribute";
		String name = AttributeUtil.getLabel(attribute);
		if (name == null && attribute.getName() != null)
			name = NameUtil.capName(attribute.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}

	protected void updateState() {
		AbstractWizardPage<Attribute> page = attributeWizard.getPage();
		if (page != null)
			setSectionTitle("Attribute " + page.getName());
	}
	
	protected void updateState(Attribute attribute) {
		String attributeName = NameUtil.capName(attribute.getName());
		setSectionTitle(attributeName + " Attribute");
	}

}
