package nam.model.network;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Network;
import nam.model.container.ContainerPageManager;
import nam.model.domain.DomainPageManager;
import nam.model.master.MasterPageManager;
import nam.model.minion.MinionPageManager;
import nam.model.pod.PodPageManager;
import nam.model.service.ServicePageManager;
import nam.model.util.NetworkUtil;
import nam.model.volume.VolumePageManager;
import nam.ui.design.SelectionContext;

import org.aries.ui.AbstractPageManager;
import org.aries.ui.AbstractWizardPage;
import org.aries.ui.Breadcrumb;
import org.aries.util.NameUtil;


@SessionScoped
@Named("networkPageManager")
public class NetworkPageManager extends AbstractPageManager<Network> implements Serializable {
	
	@Inject
	private NetworkWizard networkWizard;
	
	@Inject
	private NetworkDataManager networkDataManager;
	
	@Inject
	private NetworkListManager networkListManager;
	
	@Inject
	private DomainPageManager domainPageManager;
	
	@Inject
	private MasterPageManager masterPageManager;
	
	@Inject
	private MinionPageManager minionPageManager;
	
	@Inject
	private PodPageManager podPageManager;
	
	@Inject
	private ContainerPageManager containerPageManager;
	
	@Inject
	private ServicePageManager servicePageManager;
	
	@Inject
	private VolumePageManager volumePageManager;
	
	@Inject
	private NetworkRecord_OverviewSection networkOverviewSection;
	
	@Inject
	private NetworkRecord_IdentificationSection networkIdentificationSection;
	
	@Inject
	private NetworkRecord_ConfigurationSection networkConfigurationSection;
	
	@Inject
	private NetworkRecord_DocumentationSection networkDocumentationSection;
	
	@Inject
	private NetworkRecord_DomainsSection networkDomainsSection;
	
	@Inject
	private NetworkRecord_MastersSection networkMastersSection;
	
	@Inject
	private NetworkRecord_MinionsSection networkMinionsSection;
	
	@Inject
	private NetworkRecord_PodsSection networkPodsSection;
	
	@Inject
	private NetworkRecord_ContainersSection networkContainersSection;
	
	@Inject
	private NetworkRecord_ServicesSection networkServicesSection;
	
	@Inject
	private NetworkRecord_VolumesSection networkVolumesSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public NetworkPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("network");
	}
	
	public void refreshLocal() {
		refreshLocal("network");
	}
	
	public void refreshMembers() {
		refreshMembers("network");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		networkDataManager.setScope(scope);
		networkListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		domainPageManager.refresh(scope);
		masterPageManager.refresh(scope);
		minionPageManager.refresh(scope);
		podPageManager.refresh(scope);
		containerPageManager.refresh(scope);
		servicePageManager.refresh(scope);
		volumePageManager.refresh(scope);
		networkListManager.refresh();
	}
	
	public String getNetworkListPage() {
		return "/nam/model/network/networkListPage.xhtml";
	}
	
	public String getNetworkTreePage() {
		return "/nam/model/network/networkTreePage.xhtml";
	}
	
	public String getNetworkSummaryPage() {
		return "/nam/model/network/networkSummaryPage.xhtml";
	}
	
	public String getNetworkRecordPage() {
		return "/nam/model/network/networkRecordPage.xhtml";
	}
	
	public String getNetworkWizardPage() {
		return "/nam/model/network/networkWizardPage.xhtml";
	}
	
	public String getNetworkManagementPage() {
		return "/nam/model/network/networkManagementPage.xhtml";
	}
	
	public String initializeNetworkListPage() {
		String pageLevelKey = "networkList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Networks", "showNetworkManagementPage()");
		String url = getNetworkListPage();
		selectionContext.setCurrentArea("network");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeNetworkTreePage() {
		String pageLevelKey = "networkTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Networks", "showNetworkTreePage()");
		String url = getNetworkTreePage();
		selectionContext.setCurrentArea("network");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeNetworkSummaryPage(Network network) {
		String pageLevelKey = "networkSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Networks", "showNetworkSummaryPage()");
		String url = getNetworkSummaryPage();
		selectionContext.setCurrentArea("network");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeNetworkRecordPage() {
		Network network = selectionContext.getSelection("network");
		String networkName = network.getName();
		
		String pageLevelKey = "networkRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Networks", "showNetworkManagementPage()");
		addBreadcrumb(pageLevelKey, networkName, "showNetworkRecordPage()");
		String url = getNetworkRecordPage();
		selectionContext.setCurrentArea("network");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeNetworkCreationPage(Network network) {
		setPageTitle("New "+getNetworkLabel(network));
		setPageIcon("/icons/nam/NewNetwork16.gif");
		setSectionTitle("Network Identification");
		networkWizard.setNewMode(true);
		
		String pageLevelKey = "network";
		String wizardLevelKey = "networkWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Networks", "showNetworkManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Network", "showNetworkWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showNetworkWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showNetworkWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showNetworkWizardPage('Documentation')");
		addBreadcrumb(wizardLevelKey, "Domains", "showNetworkWizardPage('Domains')");
		addBreadcrumb(wizardLevelKey, "Masters", "showNetworkWizardPage('Masters')");
		addBreadcrumb(wizardLevelKey, "Minions", "showNetworkWizardPage('Minions')");
		addBreadcrumb(wizardLevelKey, "Pods", "showNetworkWizardPage('Pods')");
		addBreadcrumb(wizardLevelKey, "Containers", "showNetworkWizardPage('Containers')");
		addBreadcrumb(wizardLevelKey, "Services", "showNetworkWizardPage('Services')");
		addBreadcrumb(wizardLevelKey, "Volumes", "showNetworkWizardPage('Volumes')");
		
		networkIdentificationSection.setOwner("networkWizard");
		networkConfigurationSection.setOwner("networkWizard");
		networkDocumentationSection.setOwner("networkWizard");
		networkDomainsSection.setOwner("networkWizard");
		networkMastersSection.setOwner("networkWizard");
		networkMinionsSection.setOwner("networkWizard");
		networkPodsSection.setOwner("networkWizard");
		networkContainersSection.setOwner("networkWizard");
		networkServicesSection.setOwner("networkWizard");
		networkVolumesSection.setOwner("networkWizard");
		
		sections.clear();
		sections.add(networkIdentificationSection);
		sections.add(networkConfigurationSection);
		sections.add(networkDocumentationSection);
		sections.add(networkDomainsSection);
		sections.add(networkMastersSection);
		sections.add(networkMinionsSection);
		sections.add(networkPodsSection);
		sections.add(networkContainersSection);
		sections.add(networkServicesSection);
		sections.add(networkVolumesSection);
		
		String url = getNetworkWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("network");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeNetworkUpdatePage(Network network) {
		setPageTitle(getNetworkLabel(network));
		setPageIcon("/icons/nam/Network16.gif");
		setSectionTitle("Network Overview");
		String networkName = network.getName();
		networkWizard.setNewMode(false);
		
		String pageLevelKey = "network";
		String wizardLevelKey = "networkWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Networks", "showNetworkManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(networkName, "showNetworkWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showNetworkWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showNetworkWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showNetworkWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showNetworkWizardPage('Documentation')");
		addBreadcrumb(wizardLevelKey, "Domains", "showNetworkWizardPage('Domains')");
		addBreadcrumb(wizardLevelKey, "Masters", "showNetworkWizardPage('Masters')");
		addBreadcrumb(wizardLevelKey, "Minions", "showNetworkWizardPage('Minions')");
		addBreadcrumb(wizardLevelKey, "Pods", "showNetworkWizardPage('Pods')");
		addBreadcrumb(wizardLevelKey, "Containers", "showNetworkWizardPage('Containers')");
		addBreadcrumb(wizardLevelKey, "Services", "showNetworkWizardPage('Services')");
		addBreadcrumb(wizardLevelKey, "Volumes", "showNetworkWizardPage('Volumes')");
		
		networkOverviewSection.setOwner("networkWizard");
		networkIdentificationSection.setOwner("networkWizard");
		networkConfigurationSection.setOwner("networkWizard");
		networkDocumentationSection.setOwner("networkWizard");
		networkDomainsSection.setOwner("networkWizard");
		networkMastersSection.setOwner("networkWizard");
		networkMinionsSection.setOwner("networkWizard");
		networkPodsSection.setOwner("networkWizard");
		networkContainersSection.setOwner("networkWizard");
		networkServicesSection.setOwner("networkWizard");
		networkVolumesSection.setOwner("networkWizard");
		
		sections.clear();
		sections.add(networkOverviewSection);
		sections.add(networkIdentificationSection);
		sections.add(networkConfigurationSection);
		sections.add(networkDocumentationSection);
		sections.add(networkDomainsSection);
		sections.add(networkMastersSection);
		sections.add(networkMinionsSection);
		sections.add(networkPodsSection);
		sections.add(networkContainersSection);
		sections.add(networkServicesSection);
		sections.add(networkVolumesSection);
		
		String url = getNetworkWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("network");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeNetworkManagementPage() {
		setPageTitle("Networks");
		setPageIcon("/icons/nam/Network16.gif");
		String pageLevelKey = "networkManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Networks", "showNetworkManagementPage()");
		String url = getNetworkManagementPage();
		selectionContext.setCurrentArea("network");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		refreshLocal("projectList");
		return url;
	}
	
	public void initializeDefaultView() {
		setSectionType("network");
		setSectionName("Overview");
		setSectionTitle("Overview of Networks");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeNetworkDomainsView() {
		setSectionType("network");
		setSectionName("Domains");
		setSectionTitle("Domains");
		setSectionIcon("/icons/nam/Domain16.gif");
		String viewLevelKey = "networkDomains";
		selectionContext.setMessageDomain(viewLevelKey);
		domainPageManager.refresh("network");
		networkListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeNetworkMastersView() {
		setSectionType("network");
		setSectionName("Masters");
		setSectionTitle("Master Nodes");
		setSectionIcon("/icons/nam/Master16.gif");
		String viewLevelKey = "networkMasters";
		selectionContext.setMessageDomain(viewLevelKey);
		masterPageManager.refresh("network");
		networkListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeNetworkMinionsView() {
		setSectionType("network");
		setSectionName("Minions");
		setSectionTitle("Minion Nodes");
		setSectionIcon("/icons/nam/Minion16.gif");
		String viewLevelKey = "networkMinions";
		selectionContext.setMessageDomain(viewLevelKey);
		minionPageManager.refresh("network");
		networkListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeNetworkPodsView() {
		setSectionType("network");
		setSectionName("Pods");
		setSectionTitle("Pods");
		setSectionIcon("/icons/nam/Pod16.gif");
		String viewLevelKey = "networkPods";
		selectionContext.setMessageDomain(viewLevelKey);
		podPageManager.refresh("network");
		networkListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeNetworkContainersView() {
		setSectionType("network");
		setSectionName("Containers");
		setSectionTitle("Containers");
		setSectionIcon("/icons/nam/Container16.gif");
		String viewLevelKey = "networkContainers";
		selectionContext.setMessageDomain(viewLevelKey);
		containerPageManager.refresh("network");
		networkListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeNetworkServicesView() {
		setSectionType("network");
		setSectionName("Services");
		setSectionTitle("Services");
		setSectionIcon("/icons/nam/Service16.gif");
		String viewLevelKey = "networkServices";
		selectionContext.setMessageDomain(viewLevelKey);
		servicePageManager.refresh("network");
		networkListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeNetworkVolumesView() {
		setSectionType("network");
		setSectionName("Volumes");
		setSectionTitle("Volumes");
		setSectionIcon("/icons/nam/Volume16.gif");
		String viewLevelKey = "networkVolumes";
		selectionContext.setMessageDomain(viewLevelKey);
		volumePageManager.refresh("network");
		networkListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeNetworkSummaryView(Network network) {
		//String viewTitle = getNetworkLabel(network);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("network");
		setSectionName("Summary");
		setSectionTitle("Summary of Network Records");
		setSectionIcon("/icons/nam/Network16.gif");
		String viewLevelKey = "networkSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Networks", "showNetworkManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getNetworkLabel(Network network) {
		String label = "Network";
		String name = NetworkUtil.getLabel(network);
		if (name == null && network.getName() != null)
			name = NameUtil.capName(network.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Network> page = networkWizard.getPage();
		if (page != null)
			setSectionTitle("Network " + page.getName());
	}
	
	protected void updateState(Network network) {
		String networkName = NameUtil.capName(network.getName());
		setSectionTitle(networkName + " Network");
	}
	
}
