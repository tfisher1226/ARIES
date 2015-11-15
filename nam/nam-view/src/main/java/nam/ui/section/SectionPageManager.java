package nam.ui.section;

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

import nam.ui.Section;
import nam.ui.design.SelectionContext;
import nam.ui.util.SectionUtil;


@SessionScoped
@Named("sectionPageManager")
public class SectionPageManager extends AbstractPageManager<Section> implements Serializable {
	
	@Inject
	private SectionWizard sectionWizard;
	
	@Inject
	private SectionDataManager sectionDataManager;
	
	@Inject
	private SectionListManager sectionListManager;
	
	@Inject
	private SectionRecord_OverviewSection sectionOverviewSection;
	
	@Inject
	private SectionRecord_IdentificationSection sectionIdentificationSection;
	
	@Inject
	private SectionRecord_ConfigurationSection sectionConfigurationSection;
	
	@Inject
	private SectionRecord_DocumentationSection sectionDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public SectionPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("section");
	}
	
	public void refreshLocal() {
		refreshLocal("section");
	}
	
	public void refreshMembers() {
		refreshMembers("section");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		sectionDataManager.setScope(scope);
		sectionListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		sectionListManager.refresh();
	}
	
	public String getSectionListPage() {
		return "/nam/ui/section/sectionListPage.xhtml";
	}
	
	public String getSectionTreePage() {
		return "/nam/ui/section/sectionTreePage.xhtml";
	}
	
	public String getSectionSummaryPage() {
		return "/nam/ui/section/sectionSummaryPage.xhtml";
	}
	
	public String getSectionRecordPage() {
		return "/nam/ui/section/sectionRecordPage.xhtml";
	}
	
	public String getSectionWizardPage() {
		return "/nam/ui/section/sectionWizardPage.xhtml";
	}
	
	public String getSectionManagementPage() {
		return "/nam/ui/section/sectionManagementPage.xhtml";
	}
	
	public String initializeSectionListPage() {
		String pageLevelKey = "sectionList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Sections", "showSectionManagementPage()");
		String url = getSectionListPage();
		selectionContext.setCurrentArea("section");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeSectionTreePage() {
		String pageLevelKey = "sectionTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Sections", "showSectionTreePage()");
		String url = getSectionTreePage();
		selectionContext.setCurrentArea("section");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeSectionSummaryPage(Section section) {
		String pageLevelKey = "sectionSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Sections", "showSectionSummaryPage()");
		String url = getSectionSummaryPage();
		selectionContext.setCurrentArea("section");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeSectionRecordPage() {
		Section section = selectionContext.getSelection("section");
		String sectionName = section.getName();
		
		String pageLevelKey = "sectionRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Sections", "showSectionManagementPage()");
		addBreadcrumb(pageLevelKey, sectionName, "showSectionRecordPage()");
		String url = getSectionRecordPage();
		selectionContext.setCurrentArea("section");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeSectionCreationPage(Section section) {
		setPageTitle("New "+getSectionLabel(section));
		setPageIcon("/icons/nam/NewSection16.gif");
		setSectionTitle("Section Identification");
		sectionWizard.setNewMode(true);
		
		String pageLevelKey = "section";
		String wizardLevelKey = "sectionWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Sections", "showSectionManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Section", "showSectionWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showSectionWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showSectionWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showSectionWizardPage('Documentation')");
		
		sectionIdentificationSection.setOwner("sectionWizard");
		sectionConfigurationSection.setOwner("sectionWizard");
		sectionDocumentationSection.setOwner("sectionWizard");
		
		sections.clear();
		sections.add(sectionIdentificationSection);
		sections.add(sectionConfigurationSection);
		sections.add(sectionDocumentationSection);
		
		String url = getSectionWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("section");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeSectionUpdatePage(Section section) {
		setPageTitle(getSectionLabel(section));
		setPageIcon("/icons/nam/Section16.gif");
		setSectionTitle("Section Overview");
		String sectionName = section.getName();
		sectionWizard.setNewMode(false);
		
		String pageLevelKey = "section";
		String wizardLevelKey = "sectionWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Sections", "showSectionManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(sectionName, "showSectionWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showSectionWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showSectionWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showSectionWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showSectionWizardPage('Documentation')");
		
		sectionOverviewSection.setOwner("sectionWizard");
		sectionIdentificationSection.setOwner("sectionWizard");
		sectionConfigurationSection.setOwner("sectionWizard");
		sectionDocumentationSection.setOwner("sectionWizard");
		
		sections.clear();
		sections.add(sectionOverviewSection);
		sections.add(sectionIdentificationSection);
		sections.add(sectionConfigurationSection);
		sections.add(sectionDocumentationSection);
		
		String url = getSectionWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("section");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeSectionManagementPage() {
		setPageTitle("Sections");
		setPageIcon("/icons/nam/Section16.gif");
		String pageLevelKey = "sectionManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Sections", "showSectionManagementPage()");
		String url = getSectionManagementPage();
		selectionContext.setCurrentArea("section");
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
		setSectionType("section");
		setSectionName("Overview");
		setSectionTitle("Overview of Sections");
		setSectionIcon("/icons/nam/Section16.gif");
	}
	
	public String initializeSectionSummaryView(Section section) {
		//String viewTitle = getSectionLabel(section);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("section");
		setSectionName("Summary");
		setSectionTitle("Summary of Section Records");
		setSectionIcon("/icons/nam/Section16.gif");
		String viewLevelKey = "sectionSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Sections", "showSectionManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getSectionLabel(Section section) {
		String label = "Section";
		String name = SectionUtil.getLabel(section);
		if (name == null && section.getName() != null)
			name = NameUtil.capName(section.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Section> page = sectionWizard.getPage();
		if (page != null)
			setSectionTitle("Section " + page.getName());
	}
	
	protected void updateState(Section section) {
		String sectionName = NameUtil.capName(section.getName());
		setSectionTitle(sectionName + " Section");
	}
	
}
