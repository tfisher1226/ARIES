package nam.model.messaging;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.render.ResponseStateManager;

import nam.model.Namespace;
import nam.model.namespace.NamespaceManager;
import nam.ui.design.AbstractDomainTreeManager;
import nam.ui.tree.ModelTreeNode;
import nam.ui.tree.ModelTreeObject;

import org.jboss.seam.annotations.In;
import org.richfaces.component.UITree;


@SuppressWarnings("serial")
public abstract class AbstractMessagingTreeManager extends AbstractDomainTreeManager implements Serializable {

	@In(required=true)
	private NamespaceManager namespaceManager;

	private int expansionState = 1;
	
	
//	public Boolean adviseNodeOpenedOLD(UITree tree) {
//		ModelTreeNode treeNode = getCurrentTreeNode(tree);
//		ModelTreeObject<?> data = treeNode.getData();
//		switch (expansionState) {
//		case 0: return Boolean.FALSE;
//		case 1: return data.getType().equals("Namespace");
//		default: return Boolean.TRUE;
//		}
//	}
	
	public Boolean adviseNodeOpened(UITree tree) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResponseStateManager stateManager = facesContext.getRenderKit().getResponseStateManager();
		if (!stateManager.isPostback(facesContext))
			return Boolean.FALSE;

//		ModelTreeNode treeNode = getCurrentTreeNode(tree);
//		ModelTreeObject<?> data = treeNode.getData();
		
//		TreeRowKey<ModelTreeNode> currentRowKey = getCurrentRowKey(tree);
//		boolean expanded = componentState.isExpanded(currentRowKey);
//		return expanded;
		return Boolean.TRUE;
		
//		switch (expansionState) {
//		case 0: return Boolean.FALSE;
//		case 1: return Boolean.TRUE;
//		case 2: return data.getType().equals("Namespace");
//		default:
//			TreeRowKey<ModelTreeNode> currentRowKey = getCurrentRowKey(tree);
//			boolean expanded = componentState.isExpanded(currentRowKey);
//			return expanded;
//		}
	}

	public Boolean adviseNodeSelected(UITree tree) {
//		@SuppressWarnings("unchecked") TreeRowKey<ModelTreeNode> rowKey = componentState.getSelectedNode();
//		if (rowKey == null)
//			return Boolean.FALSE;
//		try {
//			ModelTreeNode selectedNode = getSelectedTreeNode(tree, rowKey);
//			ModelTreeNode currentNode = getCurrentTreeNode(tree);
//			if (selectedNode == null || !selectedNode.equals(currentNode))
//				return Boolean.FALSE;
//			processSelection(currentNode);
//			return Boolean.TRUE;
//		} catch (Exception e) {
//			return Boolean.TRUE;
//		}
		return Boolean.FALSE;
	}
	
//	public void nodeSelected(NodeSelectedEvent event) {
//		super.processTreeSelectionChange(event);
//		processSelection();
//	}

	protected void processSelection() {
		processSelection((ModelTreeNode) getSelectedNode());
	}
	
	protected void processSelection(ModelTreeNode currentNode) {
		ModelTreeObject<?> data = currentNode.getData();
		Object object = data.getObject();
		if (object instanceof Namespace)
			namespaceManager.selectNamespace((Namespace) object);
//		if (object instanceof Element) {
//			TreeNode<ModelTreeObject<?>> parent = getSelectedNode().getParent();
//			namespaceManager.selectNamespace((Namespace) parent.getData().getObject());
//			elementManager.selectElement((Element) object);
//		}
//		if (object instanceof Field) {
//			TreeNode<ModelTreeObject<?>> parent = getSelectedNode().getParent();
//			elementManager.selectElement((Element) parent.getData().getObject());
//			fieldManager.selectField((Field) object);
//		}
	}
	
}
