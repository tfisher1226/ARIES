package nam.model.messaging;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.ui.tree.ModelTreeNode;

import org.richfaces.model.TreeNode;


@SessionScoped
@Named("messagingNamespaceTreeManager")
@SuppressWarnings("serial")
public class MessagingNamespaceTreeManager extends AbstractMessagingTreeManager implements Serializable {

	private ModelTreeNode messagingNamespaceTreeRootNode;

	private ModelTreeNode messagingNamespaceTreeSelectedNode;

	protected MessagingNamespaceTreeBuilder messagingNamespaceTreeBuilder;


	public MessagingNamespaceTreeManager() {
		messagingNamespaceTreeBuilder = new MessagingNamespaceTreeBuilder();
	}
	
	@Override
	public String getTreeId() {
		return "messagingNamespaceTree";
	}

	public String getTitle() {
		return "Messaging View";
	}
	
	public String getSubTitle() {
		if (messagingNamespaceTreeSelectedNode != null)
			return messagingNamespaceTreeSelectedNode.getData().getLabel();
		return "Artifact List";
	}
	
	@Override
	public ModelTreeNode getRootNode() {
		return messagingNamespaceTreeRootNode;
	}
	
	@Override
	public TreeNode getNodeById(String nodeId) {
		return messagingNamespaceTreeBuilder.getNodeById(nodeId);
	}

	@Override
	public void refreshModel() {
		//TODO messagingNamespaceTreeRootNode = messagingNamespaceTreeBuilder.createTree(projects);
	}

}

