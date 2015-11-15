package nam.model.util;

import java.util.List;

import nam.ui.Tree;
import nam.ui.TreeNode;
import nam.ui.TreeNodes;


public class TreeUtil {

	public static TreeNode getRootNode(Tree tree) {
		TreeNodes treeNodes = tree.getTreeNodes();		
		return treeNodes.getRootNode();
	}

	public static List<TreeNode> getTreeNodes(Tree tree) {
		TreeNodes treeNodes = tree.getTreeNodes();		
		return treeNodes.getTreeNodes();
	}

}
