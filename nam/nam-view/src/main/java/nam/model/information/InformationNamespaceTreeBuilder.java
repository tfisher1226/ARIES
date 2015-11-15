package nam.model.information;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nam.model.Element;
import nam.model.Project;
import nam.ui.design.AbstractTreeBuilder;
import nam.ui.tree.ModelTreeNode;


@SuppressWarnings("serial")
public class InformationNamespaceTreeBuilder extends AbstractTreeBuilder implements Serializable {

	protected void populateMap(Element value) {
		correlationId++;
	}

	protected Collection<ModelTreeNode> createTree(Project project) {
		List<ModelTreeNode> nodes = new ArrayList<ModelTreeNode>();
		ModelTreeNode node = treeNodeFactory.createProjectNode(project, correlationId);
		treeNodeFactory.createNamespaceNodes(node, project, correlationId);
		//createCacheNodes(parentNode, project);
		nodes.add(node);
		return nodes;
	}
	
}
