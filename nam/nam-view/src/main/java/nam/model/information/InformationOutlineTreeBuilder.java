package nam.model.information;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Field;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.model.util.ElementUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractTreeBuilder;
import nam.ui.tree.ModelTreeNode;


@SuppressWarnings("serial")
public class InformationOutlineTreeBuilder extends AbstractTreeBuilder implements Serializable {

	//@Out(required = false, value="nam.informationTreeMap")
	//private Map<Integer, Element> informationTreeMap;

	
	protected void populateMap(Element value) {
		//informationTreeMap.put(correlationId, value);
		correlationId++;
	}
	
	protected Collection<ModelTreeNode> createTree(Project project) {
		List<ModelTreeNode> nodes = new ArrayList<ModelTreeNode>();
		ModelTreeNode node = treeNodeFactory.createROOTNode();
		createNamespaceNodes(node, project);
		nodes.add(node);
		return nodes;
	}
	
	protected void createNamespaceNodes(ModelTreeNode parentNode, Project project) {
		List<Namespace> namespaces = new ArrayList<Namespace>();
		namespaces.addAll(ProjectUtil.getNamespaces(project));
		namespaces.addAll(ApplicationUtil.getNamespaces(project.getApplications()));
		Iterator<Namespace> iterator = namespaces.iterator();
		ModelTreeNode folderNode = null;
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			if (folderNode == null)
				folderNode = treeNodeFactory.createFOLDERNode("Namespaces");
			createNamespaceNode(folderNode, namespace);
		}
		if (folderNode != null)
			parentNode.addNode(folderNode);
	}
	
	protected ModelTreeNode createNamespaceNode(ModelTreeNode parentNode, Namespace namespace) {
		ModelTreeNode treeNode = treeNodeFactory.createNamespaceNode(parentNode, namespace, correlationId);
		createElementNodes(treeNode, namespace);
		//populateMap(namespace);
		return treeNode;
	}

	protected void createElementNodes(ModelTreeNode parentNode, Namespace namespace) {
		List<Element> elements = NamespaceUtil.getElements(namespace);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			createElementNode(parentNode, element);
		}
	}

	protected ModelTreeNode createElementNode(ModelTreeNode parentNode, Element element) {
		ModelTreeNode treeNode = treeNodeFactory.createElementNode(parentNode, element, correlationId);
		createFieldNodes(treeNode, element);
		populateMap(element);
		return treeNode;
	}
	
	protected void createFieldNodes(ModelTreeNode parentNode, Element element) {
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			createFieldNode(parentNode, field);
		}
	}
	
	protected ModelTreeNode createFieldNode(ModelTreeNode parentNode, Field field) {
		ModelTreeNode treeNode = treeNodeFactory.createFieldNode(parentNode, field, correlationId);
		//populateMap(field);
		return treeNode;
	}
	
}
