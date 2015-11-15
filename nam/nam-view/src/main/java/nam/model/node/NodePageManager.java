package nam.model.node;

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

import nam.model.Node;
import nam.model.util.NodeUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("nodePageManager")
public class NodePageManager extends AbstractPageManager<Node> implements Serializable {
	
	@Inject
	private NodeWizard nodeWizard;
	
	@Inject
	private NodeDataManager nodeDataManager;
	
	@Inject
	private NodeListManager nodeListManager;
	
	@Inject
	private NodeRecord_OverviewSection nodeOverviewSection;
	
	@Inject
	private NodeRecord_IdentificationSection nodeIdentificationSection;
	
	@Inject
	private NodeRecord_ConfigurationSection nodeConfigurationSection;
	
	@Inject
	private NodeRecord_DocumentationSection nodeDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public NodePageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("node");
	}
	
	public void refreshLocal() {
		refreshLocal("node");
	}
	
	public void refreshMembers() {
		refreshMembers("node");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		nodeDataManager.setScope(scope);
		nodeListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		nodeListManager.refresh();
	}
	
	public String getNodeListPage() {
		return "/nam/model/node/nodeListPage.xhtml";
	}
	
	public String getNodeTreePage() {
		return "/nam/model/node/nodeTreePage.xhtml";
	}
	
	public String getNodeSummaryPage() {
		return "/nam/model/node/nodeSummaryPage.xhtml";
	}
	
	public String getNodeRecordPage() {
		return "/nam/model/node/nodeRecordPage.xhtml";
	}
	
	public String getNodeWizardPage() {
		return "/nam/model/node/nodeWizardPage.xhtml";
	}
	
	public String getNodeManagementPage() {
		return "/nam/model/node/nodeManagementPage.xhtml";
	}
	
	public String initializeNodeListPage() {
		String pageLevelKey = "nodeList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Nodes", "showNodeManagementPage()");
		String url = getNodeListPage();
		selectionContext.setCurrentArea("node");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeNodeTreePage() {
		String pageLevelKey = "nodeTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Nodes", "showNodeTreePage()");
		String url = getNodeTreePage();
		selectionContext.setCurrentArea("node");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeNodeSummaryPage(Node node) {
		String pageLevelKey = "nodeSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Nodes", "showNodeSummaryPage()");
		String url = getNodeSummaryPage();
		selectionContext.setCurrentArea("node");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeNodeRecordPage() {
		Node node = selectionContext.getSelection("node");
		String nodeName = node.getName();
		
		String pageLevelKey = "nodeRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Nodes", "showNodeManagementPage()");
		addBreadcrumb(pageLevelKey, nodeName, "showNodeRecordPage()");
		String url = getNodeRecordPage();
		selectionContext.setCurrentArea("node");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeNodeCreationPage(Node node) {
		setPageTitle("New "+getNodeLabel(node));
		setPageIcon("/icons/nam/NewNode16.gif");
		setSectionTitle("Node Identification");
		nodeWizard.setNewMode(true);
		
		String pageLevelKey = "node";
		String wizardLevelKey = "nodeWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Nodes", "showNodeManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Node", "showNodeWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showNodeWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showNodeWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showNodeWizardPage('Documentation')");
		
		nodeIdentificationSection.setOwner("nodeWizard");
		nodeConfigurationSection.setOwner("nodeWizard");
		nodeDocumentationSection.setOwner("nodeWizard");
		
		sections.clear();
		sections.add(nodeIdentificationSection);
		sections.add(nodeConfigurationSection);
		sections.add(nodeDocumentationSection);
		
		String url = getNodeWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("node");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeNodeUpdatePage(Node node) {
		setPageTitle(getNodeLabel(node));
		setPageIcon("/icons/nam/Node16.gif");
		setSectionTitle("Node Overview");
		String nodeName = node.getName();
		nodeWizard.setNewMode(false);
		
		String pageLevelKey = "node";
		String wizardLevelKey = "nodeWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Nodes", "showNodeManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(nodeName, "showNodeWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showNodeWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showNodeWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showNodeWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showNodeWizardPage('Documentation')");
		
		nodeOverviewSection.setOwner("nodeWizard");
		nodeIdentificationSection.setOwner("nodeWizard");
		nodeConfigurationSection.setOwner("nodeWizard");
		nodeDocumentationSection.setOwner("nodeWizard");
		
		sections.clear();
		sections.add(nodeOverviewSection);
		sections.add(nodeIdentificationSection);
		sections.add(nodeConfigurationSection);
		sections.add(nodeDocumentationSection);
		
		String url = getNodeWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("node");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeNodeManagementPage() {
		setPageTitle("Nodes");
		setPageIcon("/icons/nam/Node16.gif");
		String pageLevelKey = "nodeManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Nodes", "showNodeManagementPage()");
		String url = getNodeManagementPage();
		selectionContext.setCurrentArea("node");
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
		setSectionType("node");
		setSectionName("Overview");
		setSectionTitle("Overview of Nodes");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeNodeSummaryView(Node node) {
		//String viewTitle = getNodeLabel(node);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("node");
		setSectionName("Summary");
		setSectionTitle("Summary of Node Records");
		setSectionIcon("/icons/nam/Node16.gif");
		String viewLevelKey = "nodeSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Nodes", "showNodeManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getNodeLabel(Node node) {
		String label = "Node";
		String name = NodeUtil.getLabel(node);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Node> page = nodeWizard.getPage();
		if (page != null)
			setSectionTitle("Node " + page.getName());
	}
	
	protected void updateState(Node node) {
		String nodeName = NameUtil.capName(node.getName());
		setSectionTitle(nodeName + " Node");
	}
	
}
