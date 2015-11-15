package nam.model._import;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Import;
import nam.model.Project;
import nam.model.util.ImportUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractTreeBuilder;
import nam.ui.tree.ModelTreeNode;


public class ImportTreeBuilder extends AbstractTreeBuilder implements Serializable {
	
	public void createTree(ModelTreeNode rootNode, Project project) {
		ModelTreeNode projectNode = treeNodeFactory.createProjectNode(project, correlationId);
		treeNodeFactory.addNode(rootNode, projectNode);
		create_ImportDomains_node(projectNode, ProjectUtil.getImports(project));
	}
	
	protected void create_ImportDomains_node(ModelTreeNode parentNode, Collection<Import> importCollection) {
		if (importCollection.size() > 0) {
			ModelTreeNode importsNode = treeNodeFactory.createFolderNode("Imports");
			treeNodeFactory.addNode(parentNode, importsNode);
			create_ImportDomain_nodes(importsNode, importCollection);
			importsNode.setExpanded(true);
		}
	}
	
	protected void create_ImportDomain_nodes(ModelTreeNode parentNode, Collection<Import> importCollection) {
		Map<String, List<Import>> map = create_ImportByUri_map(importCollection);
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String groupId = iterator.next();
			ModelTreeNode domainNode = treeNodeFactory.createDomainNode(groupId);
			treeNodeFactory.addNode(parentNode, domainNode);
			List<Import> list = map.get(groupId);
			create_Import_nodes(domainNode, list);
		}
	}
	
	protected Map<String, List<Import>> create_ImportByUri_map(Collection<Import> importCollection) {
		Map<String, List<Import>> map = new HashMap<String, List<Import>>();
		Iterator<Import> iterator = importCollection.iterator();
		while (iterator.hasNext()) {
			Import _import = iterator.next();
			String file = _import.getFile();
			List<Import> list = map.get(file);
			if (list == null) {
				list = new ArrayList<Import>();
				map.put(file, list);
			}
			list.add(_import);
		}
		return map;
	}
	
	protected void create_Import_nodes(ModelTreeNode parentNode, Collection<Import> importCollection) {
		Iterator<Import> iterator = importCollection.iterator();
		while (iterator.hasNext()) {
			Import _import = iterator.next();
			create_Import_node(parentNode, _import);
		}
	}
	
	protected void create_Import_node(ModelTreeNode parentNode, Import _import) {
		ModelTreeNode importNode = treeNodeFactory.createImportNode(parentNode, _import, correlationId);
	}
	
}
