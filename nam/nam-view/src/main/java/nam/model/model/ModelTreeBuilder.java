package nam.model.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Import;
import nam.model.Model;
import nam.model.Project;
import nam.model.util.ModelUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractTreeBuilder;
import nam.ui.tree.ModelTreeNode;


public class ModelTreeBuilder extends AbstractTreeBuilder implements Serializable {
	
	public void createTree(ModelTreeNode rootNode, Project project) {
		ModelTreeNode projectNode = treeNodeFactory.createProjectNode(project, correlationId);
		treeNodeFactory.addNode(rootNode, projectNode);
		Collection<Import> imports = ProjectUtil.getImports(project);
		Collection<Model> models = createModels(imports);
		create_ModelDomains_node(projectNode, models);
	}
	
	public Collection<Model> createModels(Collection<Import> imports) {
		Collection<Model> models = new ArrayList<Model>();
		Iterator<Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import _import = iterator.next();
			String fileName = _import.getDir() +"/" + _import.getFile();
			Model model = new Model();
			model.setName(fileName);
			models.add(model);
		}
		return models;
	}

	protected void create_ModelDomains_node(ModelTreeNode parentNode, Collection<Model> modelCollection) {
		if (modelCollection.size() > 0) {
			ModelTreeNode modelsNode = treeNodeFactory.createFolderNode("Models");
			treeNodeFactory.addNode(parentNode, modelsNode);
			create_ModelDomain_nodes(modelsNode, modelCollection);
			modelsNode.setExpanded(true);
		}
	}
	
	protected void create_Models_node(ModelTreeNode parentNode, Collection<Model> modelCollection) {
		if (modelCollection.size() > 0) {
			ModelTreeNode modelsNode = treeNodeFactory.createFolderNode("Models");
			treeNodeFactory.addNode(parentNode, modelsNode);
			create_Model_nodes(modelsNode, modelCollection);
		}
	}
	
	protected void create_ModelDomain_nodes(ModelTreeNode parentNode, Collection<Model> modelCollection) {
		Map<String, List<Model>> map = create_ModelByPackage_map(modelCollection);
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String _package = iterator.next();
			ModelTreeNode domainNode = treeNodeFactory.createDomainNode(_package);
			treeNodeFactory.addNode(parentNode, domainNode);
			List<Model> list = map.get(_package);
			create_Model_nodes(domainNode, list);
		}
	}
	
	protected Map<String, List<Model>> create_ModelByPackage_map(Collection<Model> modelCollection) {
		Map<String, List<Model>> map = new HashMap<String, List<Model>>();
		Iterator<Model> iterator = modelCollection.iterator();
		while (iterator.hasNext()) {
			Model model = iterator.next();
			String _package = model.getPackage();
			List<Model> list = map.get(_package);
			if (list == null) {
				list = new ArrayList<Model>();
				map.put(_package, list);
			}
			list.add(model);
		}
		return map;
	}
	
	protected void create_Model_nodes(ModelTreeNode parentNode, Collection<Model> modelCollection) {
		Iterator<Model> iterator = modelCollection.iterator();
		while (iterator.hasNext()) {
			Model model = iterator.next();
			create_Model_node(parentNode, model);
		}
	}
	
	protected void create_Model_node(ModelTreeNode parentNode, Model model) {
		ModelTreeNode modelNode = treeNodeFactory.createModelNode(parentNode, model, correlationId);
	}
	
}
