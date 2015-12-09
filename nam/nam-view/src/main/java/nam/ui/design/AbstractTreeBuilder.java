package nam.ui.design;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.model.Project;
import nam.ui.tree.ModelTreeNode;
import nam.ui.tree.TreeNodeFactory;

import org.aries.runtime.BeanContext;
import org.richfaces.model.TreeNode;


public abstract class AbstractTreeBuilder {

	protected Integer correlationId;
	
	protected Map<String, ModelTreeNode> groupNodes;

	protected Map<String, ModelTreeNode> treeNodes;

	protected TreeNodeFactory treeNodeFactory;
	

	public TreeNode getNodeById(String nodeId) {
		return treeNodes.get(nodeId);
	}

	protected void initializeTreeBuilder() {
		correlationId = new Integer(0);
		groupNodes = new HashMap<String, ModelTreeNode>();
		treeNodes = new HashMap<String, ModelTreeNode>();
		treeNodeFactory = BeanContext.getFromSession("treeNodeFactory");
	}
	
	public ModelTreeNode createTree(Collection<Project> projects) {
		initializeTreeBuilder();
		ModelTreeNode rootNode = treeNodeFactory.createROOTNode();
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			if (project != null)
				createTree(rootNode, project);
		}
		populateTreeNodeMap(rootNode);
		return rootNode;
	}

	protected void createTree(ModelTreeNode rootNode, Project project) {
	}

	protected Collection<ModelTreeNode> createTree(Project project) {
		return new ArrayList<ModelTreeNode>();
	}

	protected void populateTreeNodeMap(ModelTreeNode treeNode) {
		//String nodeKey = treeNodeFactory.getNodeKey(treeNode);
		//String nodeId = treeNodeFactory.getNodeId(treeNode);
		//treeNode.setId(nodeId);
		String nodeId = treeNode.getId();
		treeNodes.put(nodeId, treeNode);
		Iterator<Object> iterator = treeNode.getChildrenKeysIterator();
		while (iterator.hasNext()) {
			Object key = (Object) iterator.next();
			ModelTreeNode childNode = (ModelTreeNode) treeNode.getChild(key);
			populateTreeNodeMap(childNode);
		}
	}
	
}
