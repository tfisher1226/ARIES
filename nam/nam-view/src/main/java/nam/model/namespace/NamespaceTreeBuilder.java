package nam.model.namespace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Namespace;
import nam.model.Project;
import nam.model.util.NamespaceUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractTreeBuilder;
import nam.ui.tree.ModelTreeNode;


public class NamespaceTreeBuilder extends AbstractTreeBuilder implements Serializable {
	
	public void createTree(ModelTreeNode rootNode, Project project) {
		ModelTreeNode projectNode = treeNodeFactory.createProjectNode(project, correlationId);
		treeNodeFactory.addNode(rootNode, projectNode);
		create_NamespaceDomains_node(projectNode, ProjectUtil.getNamespaces(project));
	}
	
	protected void create_NamespaceDomains_node(ModelTreeNode parentNode, Collection<Namespace> namespaceCollection) {
		if (namespaceCollection.size() > 0) {
			ModelTreeNode namespacesNode = treeNodeFactory.createFolderNode("Namespaces");
			treeNodeFactory.addNode(parentNode, namespacesNode);
			create_NamespaceDomain_nodes(namespacesNode, namespaceCollection);
			namespacesNode.setExpanded(true);
		}
	}
	
	protected void create_NamespaceDomain_nodes(ModelTreeNode parentNode, Collection<Namespace> namespaceCollection) {
		Map<String, List<Namespace>> map = create_NamespaceByUri_map(namespaceCollection);
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String groupId = iterator.next();
			ModelTreeNode domainNode = treeNodeFactory.createDomainNode(groupId);
			treeNodeFactory.addNode(parentNode, domainNode);
			List<Namespace> list = map.get(groupId);
			create_Namespace_nodes(domainNode, list);
		}
	}
	
	protected Map<String, List<Namespace>> create_NamespaceByUri_map(Collection<Namespace> namespaceCollection) {
		Map<String, List<Namespace>> map = new HashMap<String, List<Namespace>>();
		Iterator<Namespace> iterator = namespaceCollection.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			String uri = namespace.getUri();
			List<Namespace> list = map.get(uri);
			if (list == null) {
				list = new ArrayList<Namespace>();
				map.put(uri, list);
			}
			list.add(namespace);
		}
		return map;
	}
	
	protected void create_Namespace_nodes(ModelTreeNode parentNode, Collection<Namespace> namespaceCollection) {
		Iterator<Namespace> iterator = namespaceCollection.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			create_Namespace_node(parentNode, namespace);
		}
	}
	
	protected void create_Namespace_node(ModelTreeNode parentNode, Namespace namespace) {
		ModelTreeNode namespaceNode = treeNodeFactory.createNamespaceNode(parentNode, namespace, correlationId);
	}
	
}
