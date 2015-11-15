package nam.model.transacted;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Transacted;
import nam.model.util.TransactedUtil;
import nam.ui.design.SelectionContext;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractPageManager;
import org.aries.ui.AbstractWizardPage;
import org.aries.ui.Breadcrumb;
import org.aries.ui.PageManager;
import org.aries.util.NameUtil;


@SessionScoped
@Named("transactedPageManager")
public class TransactedPageManager extends AbstractPageManager<Transacted> implements Serializable {
	
	@Inject
	private TransactedWizard transactedWizard;
	
//	@Inject
//	private TransactedRecord_IdentificationSection transactedIdentificationSection;
//	
//	@Inject
//	private TransactedRecord_ConfigurationSection transactedConfigurationSection;
//	
//	@Inject
//	private TransactedRecord_DocumentationSection transactedDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public TransactedPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public String getTransactedListPage() {
		return "/nam/model/transacted/transactedListPage.xhtml";
	}
	
	public String getTransactedTreePage() {
		return "/nam/model/transacted/transactedTreePage.xhtml";
	}
	
	public String getTransactedSummaryPage() {
		return "/nam/model/transacted/transactedSummaryPage.xhtml";
	}
	
	public String getTransactedRecordPage() {
		return "/nam/model/transacted/transactedRecordPage.xhtml";
	}
	
	public String getTransactedWizardPage() {
		return "/nam/model/transacted/transactedWizardPage.xhtml";
	}
	
	public String getTransactedManagementPage() {
		return "/nam/model/transacted/transactedManagementPage.xhtml";
	}
	
	public String initializeTransactedListPage() {
		String pageLevelKey = "transactedList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Transacteds", "showTransactedManagementPage()");
		String url = getTransactedListPage();
		selectionContext.setCurrentArea("transacted");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeTransactedTreePage() {
		String pageLevelKey = "transactedTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Transacteds", "showTransactedTreePage()");
		String url = getTransactedTreePage();
		selectionContext.setCurrentArea("transacted");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeTransactedSummaryPage(Transacted transacted) {
		String pageLevelKey = "transactedSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Transacteds", "showTransactedSummaryPage()");
		String url = getTransactedSummaryPage();
		selectionContext.setCurrentArea("transacted");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeTransactedRecordPage() {
		Transacted transacted = selectionContext.getSelection("transacted");
		String transactedName = transacted.toString();
		
		String pageLevelKey = "transactedRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Transacteds", "showTransactedManagementPage()");
		addBreadcrumb(pageLevelKey, transactedName, "showTransactedRecordPage()");
		String url = getTransactedRecordPage();
		selectionContext.setCurrentArea("transacted");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeTransactedCreationPage(Transacted transacted) {
		setPageTitle("New "+getTransactedLabel(transacted));
		setSectionTitle("Transacted Identification");
		transactedWizard.setNewMode(true);
		
		String pageLevelKey = "transacted";
		String wizardLevelKey = "transactedWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Transacteds", "showTransactedManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Transacted", "showTransactedWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showTransactedWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showTransactedWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showTransactedWizardPage('Documentation')");
		
//		transactedIdentificationSection.setOwner("transactedWizard");
//		transactedConfigurationSection.setOwner("transactedWizard");
//		transactedDocumentationSection.setOwner("transactedWizard");
		
		sections.clear();
//		sections.add(transactedIdentificationSection);
//		sections.add(transactedConfigurationSection);
//		sections.add(transactedDocumentationSection);
		
		String url = getTransactedWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("transacted");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		return url;
	}
	
	public String initializeTransactedUpdatePage(Transacted transacted) {
		setPageTitle(getTransactedLabel(transacted));
		setSectionTitle("Transacted Identification");
		String transactedName = transacted.toString();
		transactedWizard.setNewMode(false);
		
		String pageLevelKey = "transacted";
		String wizardLevelKey = "transactedWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Transacteds", "showTransactedManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(transactedName, "showTransactedWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showTransactedWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showTransactedWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showTransactedWizardPage('Documentation')");
		
//		transactedIdentificationSection.setOwner("transactedWizard");
//		transactedConfigurationSection.setOwner("transactedWizard");
//		transactedDocumentationSection.setOwner("transactedWizard");
		
		sections.clear();
//		sections.add(transactedIdentificationSection);
//		sections.add(transactedConfigurationSection);
//		sections.add(transactedDocumentationSection);
		
		String url = getTransactedWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("transacted");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		return url;
	}
	
	public String initializeTransactedManagementPage() {
		String pageLevelKey = "transactedManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Transacteds", "showTransactedManagementPage()");
		String url = getTransactedManagementPage();
		selectionContext.setCurrentArea("transacted");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public void initializeDefaultView() {
		initializeView(this, "Transacteds", "Overview") ;
	}
	
	public void initializeView(String elementType, String viewTitle, String sectionName) {
		String pageManagerName = NameUtil.uncapName(elementType) + "PageManager";
		PageManager<?> pageManager = BeanContext.getFromSession(pageManagerName);
		initializeView(pageManager, viewTitle, sectionName);
	}
	
	public void initializeView(PageManager<?> pageManager, String viewTitle, String sectionName) {
		pageManager.setSectionName(sectionName);
		pageManager.setSectionTitle(viewTitle);
		pageManager.setSectionType("transacted");
		pageManager.setSectionIcon("/icons/nam/Transacted16.gif");
	}
	
	public String initializeTransactedSummaryView(Transacted transacted) {
		String viewTitle = getTransactedLabel(transacted);
		String currentArea = selectionContext.getCurrentArea();
		initializeView(currentArea, viewTitle, "Summary");
		String viewLevelKey = "transactedSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Transacteds", "showTransactedSummaryPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getTransactedLabel(Transacted transacted) {
		String label = "Transacted";
		String name = TransactedUtil.getLabel(transacted);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}

	protected void updateState() {
		AbstractWizardPage<Transacted> page = transactedWizard.getPage();
		if (page != null)
			setSectionTitle("Transacted " + page.getName());
	}

	protected void updateState(Transacted transacted) {
		String transactedName = NameUtil.capName(transacted.toString());
		setSectionTitle(transactedName + " Transacted");
	}
	
}
