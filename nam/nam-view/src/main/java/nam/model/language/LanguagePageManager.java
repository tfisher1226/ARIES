package nam.model.language;

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

import nam.model.Language;
import nam.model.util.LanguageUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("languagePageManager")
public class LanguagePageManager extends AbstractPageManager<Language> implements Serializable {
	
	@Inject
	private LanguageWizard languageWizard;
	
	@Inject
	private LanguageDataManager languageDataManager;
	
	@Inject
	private LanguageListManager languageListManager;
	
	@Inject
	private LanguageRecord_OverviewSection languageOverviewSection;
	
	@Inject
	private LanguageRecord_IdentificationSection languageIdentificationSection;
	
	@Inject
	private LanguageRecord_ConfigurationSection languageConfigurationSection;
	
	@Inject
	private LanguageRecord_DocumentationSection languageDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public LanguagePageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("language");
	}
	
	public void refreshLocal() {
		refreshLocal("language");
	}
	
	public void refreshMembers() {
		refreshMembers("language");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		languageDataManager.setScope(scope);
		languageListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		languageListManager.refresh();
	}
	
	public String getLanguageListPage() {
		return "/nam/model/language/languageListPage.xhtml";
	}
	
	public String getLanguageTreePage() {
		return "/nam/model/language/languageTreePage.xhtml";
	}
	
	public String getLanguageSummaryPage() {
		return "/nam/model/language/languageSummaryPage.xhtml";
	}
	
	public String getLanguageRecordPage() {
		return "/nam/model/language/languageRecordPage.xhtml";
	}
	
	public String getLanguageWizardPage() {
		return "/nam/model/language/languageWizardPage.xhtml";
	}
	
	public String getLanguageManagementPage() {
		return "/nam/model/language/languageManagementPage.xhtml";
	}
	
	public String initializeLanguageListPage() {
		String pageLevelKey = "languageList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Languages", "showLanguageManagementPage()");
		String url = getLanguageListPage();
		selectionContext.setCurrentArea("language");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeLanguageTreePage() {
		String pageLevelKey = "languageTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Languages", "showLanguageTreePage()");
		String url = getLanguageTreePage();
		selectionContext.setCurrentArea("language");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeLanguageSummaryPage(Language language) {
		String pageLevelKey = "languageSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Languages", "showLanguageSummaryPage()");
		String url = getLanguageSummaryPage();
		selectionContext.setCurrentArea("language");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeLanguageRecordPage() {
		Language language = selectionContext.getSelection("language");
		String languageName = LanguageUtil.getLabel(language);
		
		String pageLevelKey = "languageRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Languages", "showLanguageManagementPage()");
		addBreadcrumb(pageLevelKey, languageName, "showLanguageRecordPage()");
		String url = getLanguageRecordPage();
		selectionContext.setCurrentArea("language");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeLanguageCreationPage(Language language) {
		setPageTitle("New "+getLanguageLabel(language));
		setPageIcon("/icons/nam/NewLanguage16.gif");
		setSectionTitle("Language Identification");
		languageWizard.setNewMode(true);
		
		String pageLevelKey = "language";
		String wizardLevelKey = "languageWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Languages", "showLanguageManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Language", "showLanguageWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showLanguageWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showLanguageWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showLanguageWizardPage('Documentation')");
		
		languageIdentificationSection.setOwner("languageWizard");
		languageConfigurationSection.setOwner("languageWizard");
		languageDocumentationSection.setOwner("languageWizard");
		
		sections.clear();
		sections.add(languageIdentificationSection);
		sections.add(languageConfigurationSection);
		sections.add(languageDocumentationSection);
		
		String url = getLanguageWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("language");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeLanguageUpdatePage(Language language) {
		setPageTitle(getLanguageLabel(language));
		setPageIcon("/icons/nam/Language16.gif");
		setSectionTitle("Language Overview");
		String languageName = LanguageUtil.getLabel(language);
		languageWizard.setNewMode(false);
		
		String pageLevelKey = "language";
		String wizardLevelKey = "languageWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Languages", "showLanguageManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(languageName, "showLanguageWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showLanguageWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showLanguageWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showLanguageWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showLanguageWizardPage('Documentation')");
		
		languageOverviewSection.setOwner("languageWizard");
		languageIdentificationSection.setOwner("languageWizard");
		languageConfigurationSection.setOwner("languageWizard");
		languageDocumentationSection.setOwner("languageWizard");
		
		sections.clear();
		sections.add(languageOverviewSection);
		sections.add(languageIdentificationSection);
		sections.add(languageConfigurationSection);
		sections.add(languageDocumentationSection);
		
		String url = getLanguageWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("language");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeLanguageManagementPage() {
		setPageTitle("Languages");
		setPageIcon("/icons/nam/Language16.gif");
		String pageLevelKey = "languageManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Languages", "showLanguageManagementPage()");
		String url = getLanguageManagementPage();
		selectionContext.setCurrentArea("language");
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
		setSectionType("language");
		setSectionName("Overview");
		setSectionTitle("Overview of Languages");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeLanguageSummaryView(Language language) {
		//String viewTitle = getLanguageLabel(language);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("language");
		setSectionName("Summary");
		setSectionTitle("Summary of Language Records");
		setSectionIcon("/icons/nam/Language16.gif");
		String viewLevelKey = "languageSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Languages", "showLanguageManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getLanguageLabel(Language language) {
		String label = "Language";
		String name = LanguageUtil.getLabel(language);
		if (name == null && language.getName() != null)
			name = LanguageUtil.getLabel(language);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Language> page = languageWizard.getPage();
		if (page != null)
			setSectionTitle("Language " + page.getName());
	}
	
	protected void updateState(Language language) {
		String languageName = LanguageUtil.getLabel(language);
		setSectionTitle(languageName + " Language");
	}
	
}
