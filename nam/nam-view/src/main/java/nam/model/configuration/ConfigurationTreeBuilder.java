package nam.model.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Configuration;
import nam.model.Element;
import nam.model.Project;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractTreeBuilder;
import nam.ui.tree.ModelTreeNode;


@SuppressWarnings("serial")
public class ConfigurationTreeBuilder extends AbstractTreeBuilder implements Serializable {

	protected void populateMap(Element value) {
		correlationId++;
	}
	
	protected Collection<ModelTreeNode> createTree(Project project) {
		List<ModelTreeNode> nodes = new ArrayList<ModelTreeNode>();
		ModelTreeNode node = treeNodeFactory.createProjectNode(project, correlationId);
		createGroupNodesFromConfigurations(node, ProjectUtil.getConfigurations(project));
		createNodesFromConfigurations(ProjectUtil.getConfigurations(project));
		nodes.add(node);
		return nodes;
	}

	protected void createGroupNodesFromConfigurations(ModelTreeNode parentNode, List<Configuration> configurations) {
		Iterator<Configuration> iterator = configurations.iterator();
		while (iterator.hasNext()) {
			Configuration configuration = iterator.next();
			String groupId = configuration.getGroupId();
			assureGroupNodeExists(parentNode, groupId);
		}
	}
	
	protected void assureGroupNodeExists(ModelTreeNode parentNode, String groupId) {
		ModelTreeNode groupNode = groupNodes.get(groupId);
		if (groupNode == null) {
			groupNode = treeNodeFactory.createGROUPNode(groupId);
			groupNodes.put(groupId, groupNode);
			parentNode.addNode(groupNode);
		}
	}

	protected void createNodesFromConfigurations(List<Configuration> configurations) {
		Iterator<Configuration> iterator = configurations.iterator();
		while (iterator.hasNext()) {
			Configuration configuration = iterator.next();
			createNodesFromConfiguration(configuration);
		}
	}

	protected void createNodesFromConfiguration(Configuration configuration) {
		if (configuration != null) {
			ModelTreeNode parentNode = groupNodes.get(configuration.getGroupId());
			treeNodeFactory.createConfigurationNode(parentNode, configuration, correlationId);
		}
	}
	
}
