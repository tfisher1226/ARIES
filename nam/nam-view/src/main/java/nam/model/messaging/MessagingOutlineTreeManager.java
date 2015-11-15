package nam.model.messaging;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.ui.tree.ModelTreeNode;

import org.richfaces.model.TreeNode;


@SessionScoped
@Named("messagingOutlineTreeManager")
@SuppressWarnings("serial")
public class MessagingOutlineTreeManager extends AbstractMessagingTreeManager implements Serializable {

	private ModelTreeNode messagingOutlineTreeRootNode;

	private ModelTreeNode messagingOutlineTreeSelectedNode;

	protected MessagingOutlineTreeBuilder messagingOutlineTreeBuilder;

	
	public MessagingOutlineTreeManager() {
		messagingOutlineTreeBuilder = new MessagingOutlineTreeBuilder();
	}
	
	@Override
	public String getTreeId() {
		return "messagingOutlineTree";
	}

	public String getTitle() {
		return "Messaging View";
	}
	
	public String getSubTitle() {
		if (messagingOutlineTreeSelectedNode != null)
			return messagingOutlineTreeSelectedNode.getData().getLabel();
		return "Artifact List";
	}
	
	@Override
	public ModelTreeNode getRootNode() {
		return messagingOutlineTreeRootNode;
	}
	
	@Override
	public TreeNode getNodeById(String nodeId) {
		return messagingOutlineTreeBuilder.getNodeById(nodeId);
	}

	@Override
	public void refreshModel() {
		//TODO messagingOutlineTreeRootNode = messagingOutlineTreeBuilder.createTree(projects);
	}

}

