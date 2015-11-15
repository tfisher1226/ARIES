package nam.model.unit;

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

import nam.model.Unit;
import nam.model.util.UnitUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("unitPageManager")
public class UnitPageManager extends AbstractPageManager<Unit> implements Serializable {
	
	@Inject
	private UnitWizard unitWizard;
	
	@Inject
	private UnitDataManager unitDataManager;
	
	@Inject
	private UnitListManager unitListManager;
	
	@Inject
	private UnitRecord_OverviewSection unitOverviewSection;
	
	@Inject
	private UnitRecord_IdentificationSection unitIdentificationSection;
	
	@Inject
	private UnitRecord_ConfigurationSection unitConfigurationSection;
	
	@Inject
	private UnitRecord_DocumentationSection unitDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public UnitPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("unit");
	}
	
	public void refreshLocal() {
		refreshLocal("unit");
	}
	
	public void refreshMembers() {
		refreshMembers("unit");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		unitDataManager.setScope(scope);
		unitListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		unitListManager.refresh();
	}
	
	public String getUnitListPage() {
		return "/nam/model/unit/unitListPage.xhtml";
	}
	
	public String getUnitTreePage() {
		return "/nam/model/unit/unitTreePage.xhtml";
	}
	
	public String getUnitSummaryPage() {
		return "/nam/model/unit/unitSummaryPage.xhtml";
	}
	
	public String getUnitRecordPage() {
		return "/nam/model/unit/unitRecordPage.xhtml";
	}
	
	public String getUnitWizardPage() {
		return "/nam/model/unit/unitWizardPage.xhtml";
	}
	
	public String getUnitManagementPage() {
		return "/nam/model/unit/unitManagementPage.xhtml";
	}
	
	public String initializeUnitListPage() {
		String pageLevelKey = "unitList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Units", "showUnitManagementPage()");
		String url = getUnitListPage();
		selectionContext.setCurrentArea("unit");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeUnitTreePage() {
		String pageLevelKey = "unitTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Units", "showUnitTreePage()");
		String url = getUnitTreePage();
		selectionContext.setCurrentArea("unit");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeUnitSummaryPage(Unit unit) {
		String pageLevelKey = "unitSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Units", "showUnitSummaryPage()");
		String url = getUnitSummaryPage();
		selectionContext.setCurrentArea("unit");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeUnitRecordPage() {
		Unit unit = selectionContext.getSelection("unit");
		String unitName = UnitUtil.getLabel(unit);
		
		String pageLevelKey = "unitRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Units", "showUnitManagementPage()");
		addBreadcrumb(pageLevelKey, unitName, "showUnitRecordPage()");
		String url = getUnitRecordPage();
		selectionContext.setCurrentArea("unit");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeUnitCreationPage(Unit unit) {
		setPageTitle("New "+getUnitLabel(unit));
		setPageIcon("/icons/nam/NewUnit16.gif");
		setSectionTitle("Unit Identification");
		unitWizard.setNewMode(true);
		
		String pageLevelKey = "unit";
		String wizardLevelKey = "unitWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Units", "showUnitManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Unit", "showUnitWizardPage()"));
		
		
		unitIdentificationSection.setOwner("unitWizard");
		unitConfigurationSection.setOwner("unitWizard");
		unitDocumentationSection.setOwner("unitWizard");
		
		sections.clear();
		sections.add(unitIdentificationSection);
		sections.add(unitConfigurationSection);
		sections.add(unitDocumentationSection);
		
		String url = getUnitWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("unit");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeUnitUpdatePage(Unit unit) {
		setPageTitle(getUnitLabel(unit));
		setPageIcon("/icons/nam/Unit16.gif");
		setSectionTitle("Unit Overview");
		String unitName = UnitUtil.getLabel(unit);
		unitWizard.setNewMode(false);
		
		String pageLevelKey = "unit";
		String wizardLevelKey = "unitWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Units", "showUnitManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(unitName, "showUnitWizardPage()"));
		
		
		unitOverviewSection.setOwner("unitWizard");
		unitIdentificationSection.setOwner("unitWizard");
		unitConfigurationSection.setOwner("unitWizard");
		unitDocumentationSection.setOwner("unitWizard");
		
		sections.clear();
		sections.add(unitOverviewSection);
		sections.add(unitIdentificationSection);
		sections.add(unitConfigurationSection);
		sections.add(unitDocumentationSection);
		
		String url = getUnitWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("unit");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeUnitManagementPage() {
		setPageTitle("Units");
		setPageIcon("/icons/nam/Unit16.gif");
		String pageLevelKey = "unitManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Units", "showUnitManagementPage()");
		String url = getUnitManagementPage();
		selectionContext.setCurrentArea("unit");
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
		setSectionType("unit");
		setSectionName("Overview");
		setSectionTitle("Overview of Units");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeUnitSummaryView(Unit unit) {
		//String viewTitle = getUnitLabel(unit);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("unit");
		setSectionName("Summary");
		setSectionTitle("Summary of Unit Records");
		setSectionIcon("/icons/nam/Unit16.gif");
		String viewLevelKey = "unitSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Units", "showUnitManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getUnitLabel(Unit unit) {
		String label = "Unit";
		String name = UnitUtil.getLabel(unit);
		if (name == null && unit.getName() != null)
			name = UnitUtil.getLabel(unit);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Unit> page = unitWizard.getPage();
		if (page != null)
			setSectionTitle("Unit " + page.getName());
	}
	
	protected void updateState(Unit unit) {
		String unitName = UnitUtil.getLabel(unit);
		setSectionTitle(unitName + " Unit");
	}
	
}
