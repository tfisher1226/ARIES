package nam.model.volume;

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

import nam.model.Volume;
import nam.model.util.VolumeUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("volumePageManager")
public class VolumePageManager extends AbstractPageManager<Volume> implements Serializable {
	
	@Inject
	private VolumeWizard volumeWizard;
	
	@Inject
	private VolumeDataManager volumeDataManager;
	
	@Inject
	private VolumeListManager volumeListManager;
	
	@Inject
	private VolumeRecord_OverviewSection volumeOverviewSection;
	
	@Inject
	private VolumeRecord_IdentificationSection volumeIdentificationSection;
	
	@Inject
	private VolumeRecord_ConfigurationSection volumeConfigurationSection;
	
	@Inject
	private VolumeRecord_DocumentationSection volumeDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public VolumePageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("volume");
	}
	
	public void refreshLocal() {
		refreshLocal("volume");
	}
	
	public void refreshMembers() {
		refreshMembers("volume");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		volumeDataManager.setScope(scope);
		volumeListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		volumeListManager.refresh();
	}
	
	public String getVolumeListPage() {
		return "/nam/model/volume/volumeListPage.xhtml";
	}
	
	public String getVolumeTreePage() {
		return "/nam/model/volume/volumeTreePage.xhtml";
	}
	
	public String getVolumeSummaryPage() {
		return "/nam/model/volume/volumeSummaryPage.xhtml";
	}
	
	public String getVolumeRecordPage() {
		return "/nam/model/volume/volumeRecordPage.xhtml";
	}
	
	public String getVolumeWizardPage() {
		return "/nam/model/volume/volumeWizardPage.xhtml";
	}
	
	public String getVolumeManagementPage() {
		return "/nam/model/volume/volumeManagementPage.xhtml";
	}
	
	public String initializeVolumeListPage() {
		String pageLevelKey = "volumeList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Volumes", "showVolumeManagementPage()");
		String url = getVolumeListPage();
		selectionContext.setCurrentArea("volume");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeVolumeTreePage() {
		String pageLevelKey = "volumeTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Volumes", "showVolumeTreePage()");
		String url = getVolumeTreePage();
		selectionContext.setCurrentArea("volume");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeVolumeSummaryPage(Volume volume) {
		String pageLevelKey = "volumeSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Volumes", "showVolumeSummaryPage()");
		String url = getVolumeSummaryPage();
		selectionContext.setCurrentArea("volume");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeVolumeRecordPage() {
		Volume volume = selectionContext.getSelection("volume");
		String volumeName = VolumeUtil.getLabel(volume);
		
		String pageLevelKey = "volumeRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Volumes", "showVolumeManagementPage()");
		addBreadcrumb(pageLevelKey, volumeName, "showVolumeRecordPage()");
		String url = getVolumeRecordPage();
		selectionContext.setCurrentArea("volume");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeVolumeCreationPage(Volume volume) {
		setPageTitle("New "+getVolumeLabel(volume));
		setPageIcon("/icons/nam/NewVolume16.gif");
		setSectionTitle("Volume Identification");
		volumeWizard.setNewMode(true);
		
		String pageLevelKey = "volume";
		String wizardLevelKey = "volumeWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Volumes", "showVolumeManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Volume", "showVolumeWizardPage()"));
		
		
		volumeIdentificationSection.setOwner("volumeWizard");
		volumeConfigurationSection.setOwner("volumeWizard");
		volumeDocumentationSection.setOwner("volumeWizard");
		
		sections.clear();
		sections.add(volumeIdentificationSection);
		sections.add(volumeConfigurationSection);
		sections.add(volumeDocumentationSection);
		
		String url = getVolumeWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("volume");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeVolumeUpdatePage(Volume volume) {
		setPageTitle(getVolumeLabel(volume));
		setPageIcon("/icons/nam/Volume16.gif");
		setSectionTitle("Volume Overview");
		String volumeName = VolumeUtil.getLabel(volume);
		volumeWizard.setNewMode(false);
		
		String pageLevelKey = "volume";
		String wizardLevelKey = "volumeWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Volumes", "showVolumeManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(volumeName, "showVolumeWizardPage()"));
		
		
		volumeOverviewSection.setOwner("volumeWizard");
		volumeIdentificationSection.setOwner("volumeWizard");
		volumeConfigurationSection.setOwner("volumeWizard");
		volumeDocumentationSection.setOwner("volumeWizard");
		
		sections.clear();
		sections.add(volumeOverviewSection);
		sections.add(volumeIdentificationSection);
		sections.add(volumeConfigurationSection);
		sections.add(volumeDocumentationSection);
		
		String url = getVolumeWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("volume");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeVolumeManagementPage() {
		setPageTitle("Volumes");
		setPageIcon("/icons/nam/Volume16.gif");
		String pageLevelKey = "volumeManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Volumes", "showVolumeManagementPage()");
		String url = getVolumeManagementPage();
		selectionContext.setCurrentArea("volume");
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
		setSectionType("volume");
		setSectionName("Overview");
		setSectionTitle("Overview of Volumes");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeVolumeSummaryView(Volume volume) {
		//String viewTitle = getVolumeLabel(volume);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("volume");
		setSectionName("Summary");
		setSectionTitle("Summary of Volume Records");
		setSectionIcon("/icons/nam/Volume16.gif");
		String viewLevelKey = "volumeSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Volumes", "showVolumeManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getVolumeLabel(Volume volume) {
		String label = "Volume";
		String name = VolumeUtil.getLabel(volume);
		if (name == null && volume.getName() != null)
			name = VolumeUtil.getLabel(volume);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Volume> page = volumeWizard.getPage();
		if (page != null)
			setSectionTitle("Volume " + page.getName());
	}
	
	protected void updateState(Volume volume) {
		String volumeName = VolumeUtil.getLabel(volume);
		setSectionTitle(volumeName + " Volume");
	}
	
}
