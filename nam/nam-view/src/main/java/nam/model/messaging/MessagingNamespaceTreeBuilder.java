package nam.model.messaging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractTreeBuilder;
import nam.ui.tree.ModelTreeNode;


@SuppressWarnings("serial")
public class MessagingNamespaceTreeBuilder extends AbstractTreeBuilder implements Serializable {

	protected void populateMap(Element value) {
		correlationId++;
	}

	protected Collection<ModelTreeNode> createTree(Project project) {
		List<ModelTreeNode> nodes = new ArrayList<ModelTreeNode>();
		ModelTreeNode node = treeNodeFactory.createROOTNode();
		createMessagingTree(node, project);
		nodes.add(node);
		return nodes;
	}
	
	protected ModelTreeNode createMessagingTree(ModelTreeNode parentNode, Project project) {
		createNamespaceNodes(parentNode, project);
		//createCacheNodes(parentNode, project);
		return parentNode;
	}
	
	protected void createNamespaceNodes(ModelTreeNode parentNode, Project project) {
		List<Namespace> namespaces = new ArrayList<Namespace>();
		namespaces.addAll(ProjectUtil.getNamespaces(project));
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			createNamespaceNode(parentNode, namespace);
		}
	}
	
	protected ModelTreeNode createNamespaceNode(ModelTreeNode parentNode, Namespace namespace) {
		ModelTreeNode treeNode = treeNodeFactory.createNamespaceNode(parentNode, namespace, correlationId);
		//createElementNodes(treeNode, namespace);
		//populateMap(namespace);
		return treeNode;
	}

//	protected void createElementNodes(ModelTreeNode parentNode, Namespace namespace) {
//		List<Element> elements = NamespaceUtil.getElements(namespace);
//		Iterator<Element> iterator = elements.iterator();
//		while (iterator.hasNext()) {
//			Element element = iterator.next();
//			createElementNode(parentNode, element);
//		}
//	}

}
