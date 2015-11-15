package nam.model.annotation;

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

import nam.model.Annotation;
import nam.model.util.AnnotationUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("annotationPageManager")
public class AnnotationPageManager extends AbstractPageManager<Annotation> implements Serializable {
	
	@Inject
	private AnnotationWizard annotationWizard;
	
	@Inject
	private AnnotationDataManager annotationDataManager;
	
	@Inject
	private AnnotationListManager annotationListManager;
	
	@Inject
	private AnnotationRecord_OverviewSection annotationOverviewSection;
	
	@Inject
	private AnnotationRecord_IdentificationSection annotationIdentificationSection;
	
	@Inject
	private AnnotationRecord_ConfigurationSection annotationConfigurationSection;
	
	@Inject
	private AnnotationRecord_DocumentationSection annotationDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public AnnotationPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("annotation");
	}
	
	public void refreshLocal() {
		refreshLocal("annotation");
	}
	
	public void refreshMembers() {
		refreshMembers("annotation");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		annotationDataManager.setScope(scope);
		annotationListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		annotationListManager.refresh();
	}
	
	public String getAnnotationListPage() {
		return "/nam/model/annotation/annotationListPage.xhtml";
	}
	
	public String getAnnotationTreePage() {
		return "/nam/model/annotation/annotationTreePage.xhtml";
	}
	
	public String getAnnotationSummaryPage() {
		return "/nam/model/annotation/annotationSummaryPage.xhtml";
	}
	
	public String getAnnotationRecordPage() {
		return "/nam/model/annotation/annotationRecordPage.xhtml";
	}
	
	public String getAnnotationWizardPage() {
		return "/nam/model/annotation/annotationWizardPage.xhtml";
	}
	
	public String getAnnotationManagementPage() {
		return "/nam/model/annotation/annotationManagementPage.xhtml";
	}
	
	public String initializeAnnotationListPage() {
		String pageLevelKey = "annotationList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Annotations", "showAnnotationManagementPage()");
		String url = getAnnotationListPage();
		selectionContext.setCurrentArea("annotation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeAnnotationTreePage() {
		String pageLevelKey = "annotationTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Annotations", "showAnnotationTreePage()");
		String url = getAnnotationTreePage();
		selectionContext.setCurrentArea("annotation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeAnnotationSummaryPage(Annotation annotation) {
		String pageLevelKey = "annotationSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Annotations", "showAnnotationSummaryPage()");
		String url = getAnnotationSummaryPage();
		selectionContext.setCurrentArea("annotation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeAnnotationRecordPage() {
		Annotation annotation = selectionContext.getSelection("annotation");
		String annotationName = AnnotationUtil.getLabel(annotation);
		
		String pageLevelKey = "annotationRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Annotations", "showAnnotationManagementPage()");
		addBreadcrumb(pageLevelKey, annotationName, "showAnnotationRecordPage()");
		String url = getAnnotationRecordPage();
		selectionContext.setCurrentArea("annotation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeAnnotationCreationPage(Annotation annotation) {
		setPageTitle("New "+getAnnotationLabel(annotation));
		setPageIcon("/icons/nam/NewAnnotation16.gif");
		setSectionTitle("Annotation Identification");
		annotationWizard.setNewMode(true);
		
		String pageLevelKey = "annotation";
		String wizardLevelKey = "annotationWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Annotations", "showAnnotationManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Annotation", "showAnnotationWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showAnnotationWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showAnnotationWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showAnnotationWizardPage('Documentation')");
		
		annotationIdentificationSection.setOwner("annotationWizard");
		annotationConfigurationSection.setOwner("annotationWizard");
		annotationDocumentationSection.setOwner("annotationWizard");
		
		sections.clear();
		sections.add(annotationIdentificationSection);
		sections.add(annotationConfigurationSection);
		sections.add(annotationDocumentationSection);
		
		String url = getAnnotationWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("annotation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeAnnotationUpdatePage(Annotation annotation) {
		setPageTitle(getAnnotationLabel(annotation));
		setPageIcon("/icons/nam/Annotation16.gif");
		setSectionTitle("Annotation Overview");
		String annotationName = AnnotationUtil.getLabel(annotation);
		annotationWizard.setNewMode(false);
		
		String pageLevelKey = "annotation";
		String wizardLevelKey = "annotationWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Annotations", "showAnnotationManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(annotationName, "showAnnotationWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showAnnotationWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showAnnotationWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showAnnotationWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showAnnotationWizardPage('Documentation')");
		
		annotationOverviewSection.setOwner("annotationWizard");
		annotationIdentificationSection.setOwner("annotationWizard");
		annotationConfigurationSection.setOwner("annotationWizard");
		annotationDocumentationSection.setOwner("annotationWizard");
		
		sections.clear();
		sections.add(annotationOverviewSection);
		sections.add(annotationIdentificationSection);
		sections.add(annotationConfigurationSection);
		sections.add(annotationDocumentationSection);
		
		String url = getAnnotationWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("annotation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeAnnotationManagementPage() {
		setPageTitle("Annotations");
		setPageIcon("/icons/nam/Annotation16.gif");
		String pageLevelKey = "annotationManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Annotations", "showAnnotationManagementPage()");
		String url = getAnnotationManagementPage();
		selectionContext.setCurrentArea("annotation");
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
		setSectionType("annotation");
		setSectionName("Overview");
		setSectionTitle("Overview of Annotations");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeAnnotationSummaryView(Annotation annotation) {
		//String viewTitle = getAnnotationLabel(annotation);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("annotation");
		setSectionName("Summary");
		setSectionTitle("Summary of Annotation Records");
		setSectionIcon("/icons/nam/Annotation16.gif");
		String viewLevelKey = "annotationSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Annotations", "showAnnotationManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getAnnotationLabel(Annotation annotation) {
		String label = "Annotation";
		String name = AnnotationUtil.getLabel(annotation);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Annotation> page = annotationWizard.getPage();
		if (page != null)
			setSectionTitle("Annotation " + page.getName());
	}
	
	protected void updateState(Annotation annotation) {
		String annotationName = AnnotationUtil.getLabel(annotation);
		setSectionTitle(annotationName + " Annotation");
	}
	
}
