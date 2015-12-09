package nam.model.element;

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

import nam.model.Element;
import nam.model.enumeration.EnumerationPageManager;
import nam.model.fault.FaultPageManager;
import nam.model.namespace.NamespacePageManager;
import nam.model.util.ElementUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("elementPageManager")
public class ElementPageManager extends AbstractPageManager<Element> implements Serializable {
	
	@Inject
	private ElementWizard elementWizard;

	@Inject
	private ElementDataManager elementDataManager;

	@Inject
	private ElementInfoManager elementInfoManager;
	
	@Inject
	private ElementListManager elementListManager;
	
	@Inject
	private NamespacePageManager namespacePageManager;
	
	@Inject
	private EnumerationPageManager enumerationPageManager;
	
	@Inject
	private FaultPageManager faultPageManager;
	
	@Inject
	private ElementRecord_OverviewSection elementOverviewSection;
	
	@Inject
	private ElementRecord_IdentificationSection elementIdentificationSection;
	
	@Inject
	private ElementRecord_ConfigurationSection elementConfigurationSection;
	
	@Inject
	private ElementRecord_DocumentationSection elementDocumentationSection;
	
	@Inject
	private ElementRecord_NamespacesSection elementNamespacesSection;
	
	@Inject
	private ElementRecord_FieldsSection elementFieldsSection;
	
	@Inject
	private SelectionContext selectionContext;


	public ElementPageManager() {
		initializeSections();
	}
	
	
	public void refresh() {
		refresh("element");
	}
	
	public void refreshLocal() {
		refreshLocal("element");
	}
	
	public void refreshMembers() {
		refreshMembers("element");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		//refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		elementDataManager.setScope(scope);
		elementListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		namespacePageManager.refreshLocal(scope);
		enumerationPageManager.refreshLocal(scope);
		faultPageManager.refreshLocal(scope);
	}
	
	public String getElementListPage() {
		return "/nam/model/element/elementListPage.xhtml";
	}

	public String getElementTreePage() {
		return "/nam/model/element/elementTreePage.xhtml";
	}
	
	public String getElementSummaryPage() {
		return "/nam/model/element/elementSummaryPage.xhtml";
	}

	public String getElementRecordPage() {
		return "/nam/model/element/elementRecordPage.xhtml";
	}
	
	public String getElementWizardPage() {
		return "/nam/model/element/elementWizardPage.xhtml";
	}
	
	public String getElementManagementPage() {
		return "/nam/model/element/elementManagementPage.xhtml";
	}
	
	public void handleElementSelected(@Observes @Selected Element element) {
		selectionContext.setSelection("element",  element);
		elementInfoManager.setRecord(element);
	}
	
	public void handleElementUnselected(@Observes @Unselected Element element) {
		selectionContext.unsetSelection("element",  element);
		elementInfoManager.unsetRecord(element);
	}
	
	public void handleElementChecked() {
		String scope = "elementSelection";
		ElementListObject listObject = elementListManager.getSelection();
		Element element = selectionContext.getSelection("element");
		boolean checked = elementListManager.getCheckedState();
		listObject.setChecked(checked);
		if (checked) {
			elementInfoManager.setRecord(element);
			selectionContext.setSelection(scope,  element);
		} else {
			elementInfoManager.unsetRecord(element);
			selectionContext.unsetSelection(scope,  element);
		}
		String target = selectionContext.getCurrentTarget();
		if (target.equals("namespace"))
			namespacePageManager.refreshLocal(scope);
		if (target.equals("enumeration"))
			enumerationPageManager.refreshLocal(scope);
		if (target.equals("fault"))
			faultPageManager.refreshLocal(scope);
		refreshLocal(scope);
	}
	
	public String initializeElementListPage() {
		String pageLevelKey = "elementList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Elements", "showElementManagementPage()");
		String url = getElementListPage();
		selectionContext.setCurrentArea("element");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeElementTreePage() {
		String pageLevelKey = "elementTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Elements", "showElementTreePage()");
		String url = getElementTreePage();
		selectionContext.setCurrentArea("element");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeElementSummaryPage(Element element) {
		String pageLevelKey = "elementSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Elements", "showElementSummaryPage()");
		String url = getElementSummaryPage();
		selectionContext.setCurrentArea("element");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeElementRecordPage() {
		Element element = selectionContext.getSelection("element");
		String elementName = ElementUtil.getLabel(element);
		
		String pageLevelKey = "elementRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Elements", "showElementManagementPage()");
		addBreadcrumb(pageLevelKey, elementName, "showElementRecordPage()");
		String url = getElementRecordPage();
		selectionContext.setCurrentArea("element");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeElementCreationPage(Element element) {
		setPageTitle("New "+getElementLabel(element));
		setPageIcon("/icons/nam/NewElement16.gif");
		setSectionTitle("Element Identification");
		elementWizard.setNewMode(true);
		
		String pageLevelKey = "element";
		String wizardLevelKey = "elementWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Elements", "showElementManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Element", "showElementWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Namespaces", "showElementWizardPage('Namespaces')");
		addBreadcrumb(wizardLevelKey, "Enumerations", "showElementWizardPage('Enumerations')");
		addBreadcrumb(wizardLevelKey, "Faults", "showElementWizardPage('Faults')");

		elementIdentificationSection.setOwner("elementWizard");
		elementConfigurationSection.setOwner("elementWizard");
		elementDocumentationSection.setOwner("elementWizard");
		elementNamespacesSection.setOwner("elementWizard");
//		elementEnumerationsSection.setOwner("elementWizard");
//		elementFaultsSection.setOwner("elementWizard");

		sections.clear();
		sections.add(elementIdentificationSection);
		sections.add(elementConfigurationSection);
		sections.add(elementDocumentationSection);
		sections.add(elementNamespacesSection);
//		sections.add(elementEnumerationsSection);
//		sections.add(elementFaultsSection);
		
		String url = getElementWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("element");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializeElementUpdatePage(Element element) {
		setPageTitle(getElementLabel(element));
		setPageIcon("/icons/nam/Element16.gif");
		setSectionTitle("Element Overview");
		String elementName = ElementUtil.getLabel(element);
		elementWizard.setNewMode(false);
		
		String pageLevelKey = "element";
		String wizardLevelKey = "elementWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Elements", "showElementManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(elementName, "showElementWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Namespaces", "showElementWizardPage('Namespaces')");
		addBreadcrumb(wizardLevelKey, "Enumerations", "showElementWizardPage('Enumerations')");
		addBreadcrumb(wizardLevelKey, "Faults", "showElementWizardPage('Faults')");

		elementOverviewSection.setOwner("elementWizard");
		elementIdentificationSection.setOwner("elementWizard");
		elementConfigurationSection.setOwner("elementWizard");
		elementDocumentationSection.setOwner("elementWizard");
		elementNamespacesSection.setOwner("elementWizard");
//		elementEnumerationsSection.setOwner("elementWizard");
//		elementFaultsSection.setOwner("elementWizard");

		sections.clear();
		sections.add(elementOverviewSection);
		sections.add(elementIdentificationSection);
		sections.add(elementConfigurationSection);
		sections.add(elementDocumentationSection);
		sections.add(elementNamespacesSection);
//		sections.add(elementEnumerationsSection);
//		sections.add(elementFaultsSection);
		
		String url = getElementWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("element");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializeElementManagementPage() {
		setPageTitle("Elements");
		setPageIcon("/icons/nam/Element16.gif");
		String pageLevelKey = "elementManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Elements", "showElementManagementPage()");
		String url = getElementManagementPage();
		selectionContext.setCurrentArea("element");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;	
	}
	
	public void initializeDefaultView() {
		setPageTitle("Elements");
		setPageIcon("/icons/nam/Element16.gif");
		setSectionType("element");
		setSectionName("Overview");
		setSectionTitle("Overview of Elements");
		setSectionIcon("/icons/nam/Overview16.gif");
		String viewLevelKey = "elementOverview";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Elements", "showElementManagementPage()");
		String scope = "projectList";
		refreshLocal(scope);
		sections.clear();
	}
	
	public String initializeElementNamespacesView() {
		setSectionType("element"); 
		setSectionName("Namespaces"); 
		setSectionTitle("Namespaces");
		setSectionIcon("/icons/nam/Namespace16.gif"); 
		selectionContext.setMessageDomain("elementNamespaces");
		namespacePageManager.refreshLocal("projectList");
		refreshLocal("namespaceSelection");
		sections.clear();
		return null;
	}
	
	public String initializeElementElementsView() {
		setSectionType("element"); 
		setSectionName("Elements"); 
		setSectionTitle("Elements");
		setSectionIcon("/icons/nam/Element16.gif"); 
		selectionContext.setMessageDomain("elementElements");
		//elementPageManager.refreshLocal("elementSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}

	public String initializeElementEnumerationsView() {
		setSectionType("element"); 
		setSectionName("Enumerations"); 
		setSectionTitle("Enumerations");
		setSectionIcon("/icons/nam/Enumeration16.gif"); 
		selectionContext.setMessageDomain("elementEnumerations");
		enumerationPageManager.refreshLocal("elementSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}

	public String initializeElementFaultsView() {
		setSectionType("element"); 
		setSectionName("Faults");
		setSectionTitle("Faults");
		setSectionIcon("/icons/nam/Fault16.gif");
		selectionContext.setMessageDomain("elementFaults");
		faultPageManager.refreshLocal("elementSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeElementSummaryView(Element element) {
		//String viewTitle = getElementLabel(element);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("element");
		setSectionName("Summary");
		setSectionTitle("Summary of Element Records");
		setSectionIcon("/icons/nam/Element16.gif");
		String viewLevelKey = "elementSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Elements", "showElementManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getElementLabel(Element element) {
		String label = "Element";
		String name = ElementUtil.getLabel(element);
		if (name == null && element.getName() != null)
			name = ElementUtil.getLabel(element);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Element> page = elementWizard.getPage();
		if (page != null)
			setSectionTitle("Element " + page.getName());
	}
	
	protected void updateState(Element element) {
		String elementName = ElementUtil.getLabel(element);
		setSectionTitle(elementName + " Element");
	}

}
