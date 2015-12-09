package nam.model.type;

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

import nam.model.Type;
import nam.model.util.TypeUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("typePageManager")
public class TypePageManager extends AbstractPageManager<Type> implements Serializable {
	
	@Inject
	private TypeWizard typeWizard;
	
	@Inject
	private TypeDataManager typeDataManager;
	
	@Inject
	private TypeInfoManager typeInfoManager;
	
	@Inject
	private TypeListManager typeListManager;
	
	@Inject
	private TypeRecord_OverviewSection typeOverviewSection;
	
	@Inject
	private TypeRecord_IdentificationSection typeIdentificationSection;
	
	@Inject
	private TypeRecord_ConfigurationSection typeConfigurationSection;
	
	@Inject
	private TypeRecord_DocumentationSection typeDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public TypePageManager() {
		initializeSections();
	}
	
	
	public void refresh() {
		refresh("type");
	}
	
	public void refreshLocal() {
		refreshLocal("type");
	}
	
	public void refreshMembers() {
		refreshMembers("type");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		//refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		typeDataManager.setScope(scope);
		typeListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		//nothing for now
	}
	
	public String getTypeListPage() {
		return "/nam/model/type/typeListPage.xhtml";
	}
	
	public String getTypeTreePage() {
		return "/nam/model/type/typeTreePage.xhtml";
	}
	
	public String getTypeSummaryPage() {
		return "/nam/model/type/typeSummaryPage.xhtml";
	}
	
	public String getTypeRecordPage() {
		return "/nam/model/type/typeRecordPage.xhtml";
	}
	
	public String getTypeWizardPage() {
		return "/nam/model/type/typeWizardPage.xhtml";
	}
	
	public String getTypeManagementPage() {
		return "/nam/model/type/typeManagementPage.xhtml";
	}
	
	public void handleTypeSelected(@Observes @Selected Type type) {
		selectionContext.setSelection("type",  type);
		typeInfoManager.setRecord(type);
	}
	
	public void handleTypeUnselected(@Observes @Unselected Type type) {
		selectionContext.unsetSelection("type",  type);
		typeInfoManager.unsetRecord(type);
	}
	
	public void handleTypeChecked() {
		String scope = "typeSelection";
		TypeListObject listObject = typeListManager.getSelection();
		Type type = selectionContext.getSelection("type");
		boolean checked = typeListManager.getCheckedState();
		listObject.setChecked(checked);
		if (checked) {
			typeInfoManager.setRecord(type);
			selectionContext.setSelection(scope,  type);
		} else {
			typeInfoManager.unsetRecord(type);
			selectionContext.unsetSelection(scope,  type);
		}
		refreshLocal(scope);
	}
	
	public String initializeTypeListPage() {
		String pageLevelKey = "typeList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Types", "showTypeManagementPage()");
		String url = getTypeListPage();
		selectionContext.setCurrentArea("type");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeTypeTreePage() {
		String pageLevelKey = "typeTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Types", "showTypeTreePage()");
		String url = getTypeTreePage();
		selectionContext.setCurrentArea("type");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeTypeSummaryPage(Type type) {
		String pageLevelKey = "typeSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Types", "showTypeSummaryPage()");
		String url = getTypeSummaryPage();
		selectionContext.setCurrentArea("type");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeTypeRecordPage() {
		Type type = selectionContext.getSelection("type");
		String typeName = TypeUtil.getLabel(type);
		
		String pageLevelKey = "typeRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Types", "showTypeManagementPage()");
		addBreadcrumb(pageLevelKey, typeName, "showTypeRecordPage()");
		String url = getTypeRecordPage();
		selectionContext.setCurrentArea("type");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeTypeCreationPage(Type type) {
		setPageTitle("New "+getTypeLabel(type));
		setPageIcon("/icons/nam/NewType16.gif");
		setSectionTitle("Type Identification");
		typeWizard.setNewMode(true);
		
		String pageLevelKey = "type";
		String wizardLevelKey = "typeWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Types", "showTypeManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Type", "showTypeWizardPage()"));
		
		
		typeIdentificationSection.setOwner("typeWizard");
		typeConfigurationSection.setOwner("typeWizard");
		typeDocumentationSection.setOwner("typeWizard");
		
		sections.clear();
		sections.add(typeIdentificationSection);
		sections.add(typeConfigurationSection);
		sections.add(typeDocumentationSection);
		
		String url = getTypeWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("type");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializeTypeUpdatePage(Type type) {
		setPageTitle(getTypeLabel(type));
		setPageIcon("/icons/nam/Type16.gif");
		setSectionTitle("Type Overview");
		String typeName = TypeUtil.getLabel(type);
		typeWizard.setNewMode(false);
		
		String pageLevelKey = "type";
		String wizardLevelKey = "typeWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Types", "showTypeManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(typeName, "showTypeWizardPage()"));
		
		
		typeOverviewSection.setOwner("typeWizard");
		typeIdentificationSection.setOwner("typeWizard");
		typeConfigurationSection.setOwner("typeWizard");
		typeDocumentationSection.setOwner("typeWizard");
		
		sections.clear();
		sections.add(typeOverviewSection);
		sections.add(typeIdentificationSection);
		sections.add(typeConfigurationSection);
		sections.add(typeDocumentationSection);
		
		String url = getTypeWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("type");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializeTypeManagementPage() {
		setPageTitle("Types");
		setPageIcon("/icons/nam/Type16.gif");
		String pageLevelKey = "typeManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Types", "showTypeManagementPage()");
		String url = getTypeManagementPage();
		selectionContext.setCurrentArea("type");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public void initializeDefaultView() {
		setPageTitle("Types");
		setPageIcon("/icons/nam/Type16.gif");
		setSectionType("type");
		setSectionName("Overview");
		setSectionTitle("Overview of Types");
		setSectionIcon("/icons/nam/Overview16.gif");
		String viewLevelKey = "typeOverview";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Types", "showTypeManagementPage()");
		String scope = "projectList";
		refreshLocal(scope);
		sections.clear();
	}
	
	public String initializeTypeSummaryView(Type type) {
		//String viewTitle = getTypeLabel(type);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("type");
		setSectionName("Summary");
		setSectionTitle("Summary of Type Records");
		setSectionIcon("/icons/nam/Type16.gif");
		String viewLevelKey = "typeSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Types", "showTypeManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getTypeLabel(Type type) {
		String label = "Type";
		String name = TypeUtil.getLabel(type);
		if (name == null && type.getName() != null)
			name = TypeUtil.getLabel(type);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Type> page = typeWizard.getPage();
		if (page != null)
			setSectionTitle("Type " + page.getName());
	}
	
	protected void updateState(Type type) {
		String typeName = TypeUtil.getLabel(type);
		setSectionTitle(typeName + " Type");
	}
	
}
