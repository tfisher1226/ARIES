package nam.model.channel;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Channel;
import nam.model.listener.ListenerDataManager;
import nam.model.listener.ListenerListManager;
import nam.model.util.ChannelUtil;
import nam.ui.design.SelectionContext;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractPageManager;
import org.aries.ui.AbstractWizardPage;
import org.aries.ui.Breadcrumb;
import org.aries.ui.PageManager;
import org.aries.util.NameUtil;


@SessionScoped
@Named("channelPageManager")
public class ChannelPageManager extends AbstractPageManager<Channel> implements Serializable {
	
	@Inject
	private ChannelWizard channelWizard;
	
	@Inject
	private ChannelDataManager channelDataManager;

	@Inject
	private ChannelListManager channelListManager;
	
	@Inject
	private ChannelRecord_OverviewSection channelOverviewSection;
	
	@Inject
	private ChannelRecord_IdentificationSection channelIdentificationSection;
	
	@Inject
	private ChannelRecord_ConfigurationSection channelConfigurationSection;
	
	@Inject
	private ChannelRecord_DocumentationSection channelDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ChannelPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("channel");
	}
	
	public void refreshLocal() {
		refreshLocal("channel");
	}
	
	public void refreshMembers() {
		refreshMembers("channel");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		channelDataManager.setScope(scope);
		channelListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		channelListManager.refresh();
	}
	
	public String getChannelListPage() {
		return "/nam/model/channel/channelListPage.xhtml";
	}
	
	public String getChannelTreePage() {
		return "/nam/model/channel/channelTreePage.xhtml";
	}
	
	public String getChannelSummaryPage() {
		return "/nam/model/channel/channelSummaryPage.xhtml";
	}
	
	public String getChannelRecordPage() {
		return "/nam/model/channel/channelRecordPage.xhtml";
	}
	
	public String getChannelWizardPage() {
		return "/nam/model/channel/channelWizardPage.xhtml";
	}
	
	public String getChannelManagementPage() {
		return "/nam/model/channel/channelManagementPage.xhtml";
	}
	
	public String initializeChannelListPage() {
		String pageLevelKey = "channelList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Channels", "showChannelManagementPage()");
		String url = getChannelListPage();
		selectionContext.setCurrentArea("channel");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeChannelTreePage() {
		String pageLevelKey = "channelTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Channels", "showChannelTreePage()");
		String url = getChannelTreePage();
		selectionContext.setCurrentArea("channel");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeChannelSummaryPage(Channel channel) {
		String pageLevelKey = "channelSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Channels", "showChannelSummaryPage()");
		String url = getChannelSummaryPage();
		selectionContext.setCurrentArea("channel");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeChannelRecordPage() {
		Channel channel = selectionContext.getSelection("channel");
		String channelName = channel.getName();
		
		String pageLevelKey = "channelRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Channels", "showChannelManagementPage()");
		addBreadcrumb(pageLevelKey, channelName, "showChannelRecordPage()");
		String url = getChannelRecordPage();
		selectionContext.setCurrentArea("channel");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeChannelCreationPage(Channel channel) {
		setPageTitle("New "+getChannelLabel(channel));
		setPageIcon("/icons/nam/NewChannel16.gif");
		setSectionTitle("Channel Identification");
		channelWizard.setNewMode(true);
		
		String pageLevelKey = "channel";
		String wizardLevelKey = "channelWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Channels", "showChannelManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Channel", "showChannelWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showChannelWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showChannelWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showChannelWizardPage('Documentation')");
		
		channelIdentificationSection.setOwner("channelWizard");
		channelConfigurationSection.setOwner("channelWizard");
		channelDocumentationSection.setOwner("channelWizard");
		
		sections.clear();
		sections.add(channelIdentificationSection);
		sections.add(channelConfigurationSection);
		sections.add(channelDocumentationSection);
		
		String url = getChannelWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("channel");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeChannelUpdatePage(Channel channel) {
		setPageTitle(getChannelLabel(channel));
		setPageIcon("/icons/nam/Channel16.gif");
		setSectionTitle("Channel Overview");
		String channelName = channel.getName();
		channelWizard.setNewMode(false);
		
		String pageLevelKey = "channel";
		String wizardLevelKey = "channelWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Channels", "showChannelManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(channelName, "showChannelWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showChannelWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showChannelWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showChannelWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showChannelWizardPage('Documentation')");
		
		channelOverviewSection.setOwner("channelWizard");
		channelIdentificationSection.setOwner("channelWizard");
		channelConfigurationSection.setOwner("channelWizard");
		channelDocumentationSection.setOwner("channelWizard");
		
		sections.clear();
		sections.add(channelOverviewSection);
		sections.add(channelIdentificationSection);
		sections.add(channelConfigurationSection);
		sections.add(channelDocumentationSection);
		
		String url = getChannelWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("channel");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeChannelManagementPage() {
		setPageTitle("Channels");
		setPageIcon("/icons/nam/Channel16.gif");
		String pageLevelKey = "channelManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Channels", "showChannelManagementPage()");
		String url = getChannelManagementPage();
		selectionContext.setCurrentArea("channel");
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
		setSectionType("channel");
		setSectionName("Overview");
		setSectionTitle("Overview of Channels");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeChannelSummaryView(Channel channel) {
		//String viewTitle = getChannelLabel(channel);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("channel");
		setSectionName("Summary");
		setSectionTitle("Summary of Channel Records");
		setSectionIcon("/icons/nam/Channel16.gif");
		String viewLevelKey = "channelSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Channels", "showChannelManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getChannelLabel(Channel channel) {
		String label = "Channel";
		String name = ChannelUtil.getLabel(channel);
		if (name == null && channel.getName() != null)
			name = NameUtil.capName(channel.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Channel> page = channelWizard.getPage();
		if (page != null)
			setSectionTitle("Channel " + page.getName());
	}

	protected void updateState(Channel channel) {
		String channelName = NameUtil.capName(channel.getName());
		setSectionTitle(channelName + " Channel");
	}
	
}
