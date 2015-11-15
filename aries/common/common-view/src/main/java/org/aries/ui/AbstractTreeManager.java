package org.aries.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.aries.ui.tree.AbstractTreeNode;
import org.aries.util.NameUtil;
import org.richfaces.component.UITree;
import org.richfaces.event.TreeSelectionChangeEvent;
import org.richfaces.event.TreeSelectionChangeListener;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;


public abstract class AbstractTreeManager<T extends TreeNodeImpl> extends AbstractViewManager implements TreeSelectionChangeListener {

	public abstract String getFormId();

	public abstract String getTreeId();

	public abstract TreeNode getRootNode();

	public abstract TreeNode getNodeById(String nodeId);

	//public abstract TreeNode getSelectedNode();

	//protected abstract void setSelectedNode(TreeNode node);

	protected abstract void refreshModel();


	protected boolean visible;

	protected TreeNode selectedNode;

	protected String selectedNodeId;

	protected String selectedRowKey;

	protected Collection<Object> selectedNodes = new HashSet<Object>();
	
	protected Object selectionLock = new Object();
	

	public String refresh() {
		refreshModel();
		return null;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public void setSelectedNode(String selectedNodeId) {
		TreeNode treeNode = getNodeById(selectedNodeId);
		setSelectedNode(treeNode);
	}
	
	public void setSelectedNode(AbstractTreeNode treeNode) {
		if (treeNode == null)
			return;
		synchronized (selectionLock) {
			AbstractTreeNode node = (AbstractTreeNode) getSelectedNode();
			if (node != null)
				node.setSelected(false);
			//setSelectedNode((TreeNode) treeNode);
			this.selectedNode = treeNode;
			treeNode.setSelected(true);
		}
	}

	public Collection<Object> getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(Collection<Object> selectedNodes) {
		this.selectedNodes = selectedNodes;
	}
	
	
	public String getSelectedNodeId() {
		return selectedNodeId;
	}

	public void setSelectedNodeId(String selectedNodeId) {
		synchronized (selectionLock) {
			this.selectedNodeId = selectedNodeId;
			setSelectedNode(selectedNodeId);
			resetTreeData();
		}
	}
	
	public String getSelectedRowKey() {
		return selectedRowKey;
	}

	public void setSelectedRowKey(String selectedRowKey) {
		if (selectedRowKey == null)
			return;
		synchronized (selectionLock ) {
			this.selectedRowKey = selectedRowKey;
			
			//TODO for multiple selection
			//sequenceRowKey = organizationTreeBuilder.createSequenceRowKey(selectedRowKey);
			//selectedNodes.clear();
			//selectedNodes.add(sequenceRowKey);
			//resetSelection();
			//resetRowKey();
		}
	}

	public void clearSelection() {
		synchronized (selectionLock) {
			selectedNode = null;
			selectedNodes.clear();
			selectedNodeId = null;
			selectedRowKey = null;
			resetSelection();
		}
	}

	
	/*
	 * UITree related
	 */
	
	public UITree getTreeComponent() {
		FacesContext fc = FacesContext.getCurrentInstance();
		UIViewRoot view = fc.getViewRoot();
		UIComponent component = (UIComponent) view.findComponent(getFormId() + ":" + getTreeId());
		UITree tree = (UITree) component;
		return tree;
	}

	public void resetTreeData() {
		UITree tree = getTreeComponent();
		if (tree != null) {
			tree.setData(getSelectedNode());
		}
	}

	public void resetRowKey(Object rowKey) {
		UITree tree = getTreeComponent();
		if (tree != null) {
			tree.setRowKey(rowKey);
		}
	}

	public void resetSelection() {
		UITree tree = getTreeComponent();
		if (tree != null) {
			tree.setSelection(selectedNodes);
		}
	}
	
	public Boolean adviseNodeOpened(UITree tree) {
		return Boolean.TRUE;
	}

	public Boolean adviseNodeSelected(UITree tree) {
		return Boolean.TRUE;
	}

	public void processTreeSelectionChange(TreeSelectionChangeEvent event) {
		List<Object> selection = new ArrayList<Object>(event.getNewSelection());
		Object currentSelectionKey = selection.get(0);
		UITree tree = (UITree) event.getSource();

		Object storedKey = tree.getRowKey();
		tree.setRowKey(currentSelectionKey);
		@SuppressWarnings("unchecked") T currentNode = (T) tree.getRowData();
		tree.setRowKey(storedKey);
		setSelectedNode(currentNode);

		//richfaces 3
		//HtmlTree tree = (HtmlTree) event.getComponent();
		//@SuppressWarnings("unchecked") T currentNode = (T) tree.getModelTreeNode(tree.getRowKey());
		//setSelectedNode(currentNode);
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
