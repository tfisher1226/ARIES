package nam.model.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Persistence;
import nam.model.Project;
import nam.model.util.PersistenceUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractTreeBuilder;
import nam.ui.tree.ModelTreeNode;


public class PersistenceTreeBuilder extends AbstractTreeBuilder implements Serializable {
	
	public void createTree(ModelTreeNode rootNode, Project project) {
		ModelTreeNode projectNode = treeNodeFactory.createProjectNode(project, correlationId);
		treeNodeFactory.addNode(rootNode, projectNode);
		List<Persistence> persistenceBlocks = ProjectUtil.getPersistenceBlocks(project);
		create_PersistenceDomains_node(projectNode, persistenceBlocks);
	}
	
	protected void create_PersistenceDomains_node(ModelTreeNode parentNode, Collection<Persistence> persistenceCollection) {
		if (persistenceCollection.size() > 0) {
			ModelTreeNode persistencesNode = treeNodeFactory.createFolderNode("Persistences");
			treeNodeFactory.addNode(parentNode, persistencesNode);
			create_PersistenceDomain_nodes(persistencesNode, persistenceCollection);
			persistencesNode.setExpanded(true);
		}
	}
	
	protected void create_PersistenceDomain_nodes(ModelTreeNode parentNode, Collection<Persistence> persistenceCollection) {
		Map<String, List<Persistence>> map = create_PersistenceByDomain_map(persistenceCollection);
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String groupId = iterator.next();
			ModelTreeNode domainNode = treeNodeFactory.createDomainNode(groupId);
			treeNodeFactory.addNode(parentNode, domainNode);
			List<Persistence> list = map.get(groupId);
			create_Persistence_nodes(domainNode, list);
		}
	}
	
	protected Map<String, List<Persistence>> create_PersistenceByDomain_map(Collection<Persistence> persistenceCollection) {
		Map<String, List<Persistence>> map = new HashMap<String, List<Persistence>>();
		Iterator<Persistence> iterator = persistenceCollection.iterator();
		while (iterator.hasNext()) {
			Persistence persistence = iterator.next();
			String domain = persistence.getDomain();
			List<Persistence> list = map.get(domain);
			if (list == null) {
				list = new ArrayList<Persistence>();
				map.put(domain, list);
			}
			list.add(persistence);
		}
		return map;
	}
	
	protected void create_Persistence_nodes(ModelTreeNode parentNode, Collection<Persistence> persistenceCollection) {
		Iterator<Persistence> iterator = persistenceCollection.iterator();
		while (iterator.hasNext()) {
			Persistence persistence = iterator.next();
			create_Persistence_node(parentNode, persistence);
		}
	}
	
	protected void create_Persistence_node(ModelTreeNode parentNode, Persistence persistence) {
		ModelTreeNode persistenceNode = treeNodeFactory.createPersistenceNode(parentNode, persistence, correlationId);
	}
	
}
