package nam.model.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Adapter;
import nam.model.Element;
import nam.model.Persistence;
import nam.model.Project;
import nam.model.Provider;
import nam.model.Repository;
import nam.model.Source;
import nam.model.Unit;
import nam.model.util.PersistenceUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.RepositoryUtil;
import nam.ui.design.AbstractTreeBuilder;
import nam.ui.tree.ModelTreeNode;


@SuppressWarnings("serial")
public class PersistenceOutlineTreeBuilder extends AbstractTreeBuilder implements Serializable {

	protected void populateMap(Element value) {
		correlationId++;
	}
	
	protected Collection<ModelTreeNode> createTree(Project project) {
		List<ModelTreeNode> nodes = new ArrayList<ModelTreeNode>();
		ModelTreeNode node = treeNodeFactory.createROOTNode();
		createPersistenceTree(node, project);
		nodes.add(node);
		return nodes;
	}

	protected ModelTreeNode createPersistenceTree(ModelTreeNode parentNode, Project project) {
		List<Persistence> persistenceBlocks = ProjectUtil.getPersistenceBlocks(project);
		Persistence persistence = persistenceBlocks.get(0);
		if (persistence != null) {
			createProviderNodes(parentNode, persistence);
			createAdapterNodes(parentNode, persistence);
			createSourceNodes(parentNode, persistence);
			createUnitNodes(parentNode, persistence);
			createRepositoryNodes(parentNode, persistence);
			//createPropertyNodes(parentNode, persistence);
			return parentNode;
		}
		return null;
	}

	protected void createProviderNodes(ModelTreeNode parentNode, Persistence persistence) {
		List<Provider> providers = PersistenceUtil.getProviders(persistence);
		Iterator<Provider> iterator = providers.iterator();
		ModelTreeNode folderNode = null;
		while (iterator.hasNext()) {
			Provider provider = iterator.next();
			if (folderNode == null)
				folderNode = treeNodeFactory.createFOLDERNode("Providers");
			createProviderNode(folderNode, provider);
		}
		if (folderNode != null)
			parentNode.addNode(folderNode);
	}

	protected ModelTreeNode createProviderNode(ModelTreeNode parentNode, Provider provider) {
		ModelTreeNode treeNode = treeNodeFactory.createPersistenceProviderNode(parentNode, provider, correlationId);
		return treeNode;
	}

	protected void createAdapterNodes(ModelTreeNode parentNode, Persistence persistence) {
		List<Adapter> adapters = PersistenceUtil.getAdapters(persistence);
		Iterator<Adapter> iterator = adapters.iterator();
		ModelTreeNode folderNode = null;
		while (iterator.hasNext()) {
			Adapter adapter = iterator.next();
			if (folderNode == null)
				folderNode = treeNodeFactory.createFOLDERNode("Adapters");
			createAdapterNode(folderNode, adapter);
		}
		if (folderNode != null)
			parentNode.addNode(folderNode);
	}

	protected ModelTreeNode createAdapterNode(ModelTreeNode parentNode, Adapter adapter) {
		ModelTreeNode treeNode = treeNodeFactory.createPersistenceAdapterNode(parentNode, adapter, correlationId);
		return treeNode;
	}

	protected void createRepositoryNodes(ModelTreeNode parentNode, Persistence persistence) {
		List<Repository> repositories = PersistenceUtil.getRepositories(persistence);
		Iterator<Repository> iterator = repositories.iterator();
		ModelTreeNode folderNode = null;
		while (iterator.hasNext()) {
			Repository repository = iterator.next();
			if (folderNode == null)
				folderNode = treeNodeFactory.createFOLDERNode("Repositories");
			createRepositoryNode(folderNode, repository);
		}
		if (folderNode != null)
			parentNode.addNode(folderNode);
	}

	protected ModelTreeNode createRepositoryNode(ModelTreeNode parentNode, Repository repository) {
		ModelTreeNode treeNode = treeNodeFactory.createNodesFromRepository(parentNode, repository, correlationId);
		Collection<Unit> units = RepositoryUtil.getUnits(repository);
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			createUnitNode(treeNode, unit);
		}
		return treeNode;
	}
	
	protected void createUnitNodes(ModelTreeNode parentNode, Persistence persistence) {
		List<Unit> persistenceUnits = PersistenceUtil.getUnits(persistence);
		Iterator<Unit> iterator = persistenceUnits.iterator();
		ModelTreeNode folderNode = null;
		while (iterator.hasNext()) {
			Unit persistenceUnit = iterator.next();
			if (folderNode == null)
				folderNode = treeNodeFactory.createFOLDERNode("Persistence-Units");
			createUnitNode(folderNode, persistenceUnit);
		}
		if (folderNode != null)
			parentNode.addNode(folderNode);
	}

	protected ModelTreeNode createUnitNode(ModelTreeNode parentNode, Unit persistenceUnit) {
		ModelTreeNode treeNode = treeNodeFactory.createUnitNode(parentNode, persistenceUnit, correlationId);
		Source dataSource = persistenceUnit.getSource();
		if (dataSource != null)
			createSourceNode(treeNode, dataSource);
		return treeNode;
	}

	protected void createSourceNodes(ModelTreeNode parentNode, Persistence persistence) {
		List<Source> dataSources = PersistenceUtil.getSources(persistence);
		Iterator<Source> iterator = dataSources.iterator();
		ModelTreeNode folderNode = null;
		while (iterator.hasNext()) {
			Source dataSource = iterator.next();
			if (folderNode == null)
				folderNode = treeNodeFactory.createFOLDERNode("Data-Sources");
			createSourceNode(folderNode, dataSource);
		}
		if (folderNode != null)
			parentNode.addNode(folderNode);
	}

	protected ModelTreeNode createSourceNode(ModelTreeNode parentNode, Source dataSource) {
		ModelTreeNode treeNode = treeNodeFactory.createSourceNode(parentNode, dataSource, correlationId);
		return treeNode;
	}
	

}
