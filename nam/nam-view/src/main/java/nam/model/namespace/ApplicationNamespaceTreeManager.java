package nam.model.namespace;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Configuration;
import nam.model.application.AbstractApplicationTreeManager;
import nam.ui.tree.ModelTreeNode;
import nam.ui.tree.ModelTreeObject;

import org.aries.Assert;
import org.richfaces.model.TreeNode;


@SessionScoped
@Named("applicationNamespaceTreeManager")
@SuppressWarnings("serial")
public class ApplicationNamespaceTreeManager extends AbstractApplicationTreeManager implements Serializable {

	private ModelTreeNode applicationNamespaceTreeRootNode;

	private ModelTreeNode applicationNamespaceTreeSelectedNode;

	protected ApplicationNamespaceTreeBuilder applicationNamespaceTreeBuilder;


	public ApplicationNamespaceTreeManager() {
		applicationNamespaceTreeBuilder = new ApplicationNamespaceTreeBuilder();
	}
	
	@Override
	public String getTreeId() {
		return "applicationNamespaceTree";
	}

	public String getTitle() {
		return "Application View";
	}
	
	public String getSubTitle() {
		if (applicationNamespaceTreeSelectedNode != null)
			return applicationNamespaceTreeSelectedNode.getData().getLabel();
		return "Artifact List";
	}
	
	@Override
	public ModelTreeNode getRootNode() {
		return applicationNamespaceTreeRootNode;
	}

	@Override
	public TreeNode getNodeById(String nodeId) {
		return applicationNamespaceTreeBuilder.getNodeById(nodeId);
	}

//	public boolean getShowModuleTypeInLabels() {
//		return showModuleTypeInLabels;
//	}
//
//	public void setShowModuleTypeInLabels(boolean value) {
//		showModuleTypeInLabels = value;
//	}
//
//	public void toggleShowModuleTypeInLabels() {
//		setShowModuleTypeInLabels(!getShowModuleTypeInLabels());
//	}
	
	public String refresh() {
		super.refresh();
		return null;
	}
	
	@Override
	public void refreshModel() {
		//TODO applicationNamespaceTreeRootNode = applicationNamespaceTreeBuilder.createTree(projects);
	}

	
	public String saveSelection() {
		ModelTreeNode selectedNode = (ModelTreeNode) getSelectedNode();
		if (selectedNode != null) {
			Assert.notNull(selectedNode, "An configuration must be selected");
			ModelTreeObject<?> data = selectedNode.getData();
			Configuration configuration = (Configuration) data.getObject();
			selectionListener.handle(configuration);
		}
		return null;
	}
	
}

