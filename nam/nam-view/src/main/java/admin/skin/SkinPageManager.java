package admin.skin;

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

import admin.Skin;
import admin.util.SkinUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("skinPageManager")
public class SkinPageManager extends AbstractPageManager<Skin> implements Serializable {
	
	@Inject
	private SkinWizard skinWizard;
	
	@Inject
	private SkinDataManager skinDataManager;
	
	@Inject
	private SkinListManager skinListManager;
	
	@Inject
	private SkinRecord_OverviewSection skinOverviewSection;
	
	@Inject
	private SkinRecord_IdentificationSection skinIdentificationSection;
	
	@Inject
	private SkinRecord_ConfigurationSection skinConfigurationSection;
	
	@Inject
	private SkinRecord_DocumentationSection skinDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public SkinPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("skin");
	}
	
	public void refreshLocal() {
		refreshLocal("skin");
	}
	
	public void refreshMembers() {
		refreshMembers("skin");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		skinDataManager.setScope(scope);
		skinListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		skinListManager.refresh();
	}
	
	public String getSkinListPage() {
		return "/admin/skin/skinListPage.xhtml";
	}
	
	public String getSkinTreePage() {
		return "/admin/skin/skinTreePage.xhtml";
	}
	
	public String getSkinSummaryPage() {
		return "/admin/skin/skinSummaryPage.xhtml";
	}
	
	public String getSkinRecordPage() {
		return "/admin/skin/skinRecordPage.xhtml";
	}
	
	public String getSkinWizardPage() {
		return "/admin/skin/skinWizardPage.xhtml";
	}
	
	public String getSkinManagementPage() {
		return "/admin/skin/skinManagementPage.xhtml";
	}
	
	public String initializeSkinListPage() {
		String pageLevelKey = "skinList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Skins", "showSkinManagementPage()");
		String url = getSkinListPage();
		selectionContext.setCurrentArea("skin");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeSkinTreePage() {
		String pageLevelKey = "skinTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Skins", "showSkinTreePage()");
		String url = getSkinTreePage();
		selectionContext.setCurrentArea("skin");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeSkinSummaryPage(Skin skin) {
		String pageLevelKey = "skinSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Skins", "showSkinSummaryPage()");
		String url = getSkinSummaryPage();
		selectionContext.setCurrentArea("skin");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeSkinRecordPage() {
		Skin skin = selectionContext.getSelection("skin");
		String skinName = skin.getName();
		
		String pageLevelKey = "skinRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Skins", "showSkinManagementPage()");
		addBreadcrumb(pageLevelKey, skinName, "showSkinRecordPage()");
		String url = getSkinRecordPage();
		selectionContext.setCurrentArea("skin");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeSkinCreationPage(Skin skin) {
		setPageTitle("New "+getSkinLabel(skin));
		setPageIcon("/icons/nam/NewSkin16.gif");
		setSectionTitle("Skin Identification");
		skinWizard.setNewMode(true);
		
		String pageLevelKey = "skin";
		String wizardLevelKey = "skinWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Skins", "showSkinManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Skin", "showSkinWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showSkinWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showSkinWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showSkinWizardPage('Documentation')");
		
		skinIdentificationSection.setOwner("skinWizard");
		skinConfigurationSection.setOwner("skinWizard");
		skinDocumentationSection.setOwner("skinWizard");
		
		sections.clear();
		sections.add(skinIdentificationSection);
		sections.add(skinConfigurationSection);
		sections.add(skinDocumentationSection);
		
		String url = getSkinWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("skin");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeSkinUpdatePage(Skin skin) {
		setPageTitle(getSkinLabel(skin));
		setPageIcon("/icons/nam/Skin16.gif");
		setSectionTitle("Skin Overview");
		String skinName = skin.getName();
		skinWizard.setNewMode(false);
		
		String pageLevelKey = "skin";
		String wizardLevelKey = "skinWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Skins", "showSkinManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(skinName, "showSkinWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showSkinWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showSkinWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showSkinWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showSkinWizardPage('Documentation')");
		
		skinOverviewSection.setOwner("skinWizard");
		skinIdentificationSection.setOwner("skinWizard");
		skinConfigurationSection.setOwner("skinWizard");
		skinDocumentationSection.setOwner("skinWizard");
		
		sections.clear();
		sections.add(skinOverviewSection);
		sections.add(skinIdentificationSection);
		sections.add(skinConfigurationSection);
		sections.add(skinDocumentationSection);
		
		String url = getSkinWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("skin");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeSkinManagementPage() {
		setPageTitle("Skins");
		setPageIcon("/icons/nam/Skin16.gif");
		String pageLevelKey = "skinManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Skins", "showSkinManagementPage()");
		String url = getSkinManagementPage();
		selectionContext.setCurrentArea("skin");
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
		setSectionType("skin");
		setSectionName("Overview");
		setSectionTitle("Overview of Skins");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeSkinSummaryView(Skin skin) {
		//String viewTitle = getSkinLabel(skin);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("skin");
		setSectionName("Summary");
		setSectionTitle("Summary of Skin Records");
		setSectionIcon("/icons/nam/Skin16.gif");
		String viewLevelKey = "skinSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Skins", "showSkinManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getSkinLabel(Skin skin) {
		String label = "Skin";
		String name = SkinUtil.getLabel(skin);
		if (name == null && skin.getName() != null)
			name = NameUtil.capName(skin.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Skin> page = skinWizard.getPage();
		if (page != null)
			setSectionTitle("Skin " + page.getName());
	}
	
	protected void updateState(Skin skin) {
		String skinName = NameUtil.capName(skin.getName());
		setSectionTitle(skinName + " Skin");
	}
	
}