package nam.model.namespace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.model.Application;
import nam.model.Configuration;
import nam.model.Element;
import nam.model.Execution;
import nam.model.Input;
import nam.model.Invocation;
import nam.model.Module;
import nam.model.Output;
import nam.model.Project;
import nam.model.Repository;
import nam.model.Resource;
import nam.model.Service;
import nam.model.util.ApplicationUtil;
import nam.model.util.ExecutionUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.ui.design.AbstractTreeBuilder;
import nam.ui.tree.ModelTreeNode;


@SuppressWarnings("serial")
public class ApplicationNamespaceTreeBuilder extends AbstractTreeBuilder implements Serializable {

	protected void populateMap(Element value) {
		correlationId++;
	}
	
	protected Collection<ModelTreeNode> createTree(Project project) {
		List<ModelTreeNode> nodes = new ArrayList<ModelTreeNode>();
		ModelTreeNode node = createDomainTree(project);
		nodes.add(node);
		return nodes;
	}
	
	protected ModelTreeNode createDomainTree(Project project) {
		ModelTreeNode rootNode = treeNodeFactory.createProjectNode(project, correlationId);
		createGroupNodesFromConfigurations(rootNode, ProjectUtil.getConfigurations(project));
		createGroupNodesFromApplications(rootNode, ProjectUtil.getApplications(project));
		createNodesFromConfigurations(ProjectUtil.getConfigurations(project));
		createNodesFromApplications(ProjectUtil.getApplications(project));
		createNodesFromModules(ProjectUtil.getApplicationModules(project));
		return rootNode;
	}
	
	protected ModelTreeNode createConfigurationTree(Project project) {
		ModelTreeNode rootNode = treeNodeFactory.createProjectNode(project, correlationId);
		createGroupNodesFromConfigurations(rootNode, ProjectUtil.getConfigurations(project));
		createNodesFromConfigurations(ProjectUtil.getConfigurations(project));
		return rootNode;
	}

//	protected DomainTreeNode createGroupNodes(Workspace workspace) {
//		DomainTreeNode rootNode = DomainTreeNode.createNode(workspace, workspace.getName());
//		createGroupNodesFromConfigurations(rootNode, workspace.getConfigurations());
//		createGroupNodesFromApplications(rootNode, workspace.getApplications());
//		return rootNode;
//	}

	protected void createGroupNodesFromConfigurations(ModelTreeNode parentNode, List<Configuration> configurations) {
		Iterator<Configuration> iterator = configurations.iterator();
		while (iterator.hasNext()) {
			Configuration configuration = iterator.next();
			String groupId = configuration.getGroupId();
			assureGroupNodeExists(parentNode, groupId);
		}
	}
	
	protected void createGroupNodesFromApplications(ModelTreeNode parentNode, Collection<Application> applications) {
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			String groupId = application.getGroupId();
			//String artifactId = application.getArtifactId();
			//DomainTreeNode node = DomainTreeNode.createNode(application, artifactId);
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
	
	protected void createNodesFromApplications(Collection<Application> applications) {
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = (Application) iterator.next();
			createNodesFromApplication(application);
		}
	}

	protected void createNodesFromApplication(Application application) {
		ModelTreeNode parentNode = groupNodes.get(application.getGroupId());
		createNodesFromConfiguration(application.getConfiguration());
		treeNodeFactory.createApplicationNode(parentNode, application, correlationId);
		createNodesFromModules(ApplicationUtil.getModules(application));
	}
	
	protected void createNodesFromModules(Collection<Module> modules) {
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = (Module) iterator.next();
			createNodesFromModule(module);
		}
	}

	protected void createNodesFromModule(Module module) {
		ModelTreeNode parentNode = groupNodes.get(module.getGroupId());
		ModelTreeNode treeNode = treeNodeFactory.createModuleNode(parentNode, module, correlationId);

		switch (module.getType()) {
		case POM: createNodesFromPomModule(treeNode, module); break;
		case DATA: createNodesFromDataModule(treeNode, module); break;
		case MODEL: createNodesFromModelModule(treeNode, module); break;
		case CLIENT: createNodesFromClientModule(treeNode, module); break;
		case SERVICE: createNodesFromServiceModule(treeNode, module); break;
		}
	}

	protected void createNodesFromPomModule(ModelTreeNode parentNode, Module module) {
		//Configuration configuration = module.getConfiguration();
		//configuration.getProfiles();
	}
	
	protected void createNodesFromDataModule(ModelTreeNode parentNode, Module module) {
		createNodesFromResources(parentNode, module);
		createNodesFromRepositories(parentNode, module);
	}

	protected void createNodesFromResources(ModelTreeNode parentNode, Module module) {
		List<Resource> resources = ModuleUtil.getResources(module);
		Iterator<Resource> iterator = resources.iterator();
		while (iterator.hasNext()) {
			Resource resource = (Resource) iterator.next();
			treeNodeFactory.createResourceNode(parentNode, resource, correlationId);
		}
	}

	protected void createNodesFromRepositories(ModelTreeNode parentNode, Module module) {
		List<Repository> repositories = ModuleUtil.getRepositories(module);
		Iterator<Repository> iterator = repositories.iterator();
		while (iterator.hasNext()) {
			Repository repository = (Repository) iterator.next();
			createNodesFromRepository(parentNode, repository);
		}
	}

	protected void createNodesFromRepository(ModelTreeNode parentNode, Repository repository) {
		treeNodeFactory.createNodesFromRepository(parentNode, repository, correlationId);
	}

	/*
	 * Model module
	 */

	protected void createNodesFromModelModule(ModelTreeNode moduleNode, Module module) {
		createResourceNodes(moduleNode, module);
	}

	protected void createResourceNodes(ModelTreeNode parentNode, Module module) {
		List<Resource> resources = ModuleUtil.getResources(module);
		Iterator<Resource> iterator = resources.iterator();
		while (iterator.hasNext()) {
			Resource resource = iterator.next();
			createResourceNodes(parentNode, resource);
		}
	}

	protected void createResourceNodes(ModelTreeNode parentNode, Resource resource) {
		treeNodeFactory.createResourceNode(parentNode, resource, correlationId);
	}
	
	/*
	 * Client module
	 */

	protected void createNodesFromClientModule(ModelTreeNode moduleNode, Module module) {
	}

	
	/*
	 * Service module
	 */
	
	protected void createNodesFromServiceModule(ModelTreeNode moduleNode, Module module) {
		createResourceNodes(moduleNode, module);
		//createServiceNodes(moduleNode, module);
	}
	
	protected void createServiceNodes(ModelTreeNode parentNode, Module module) {
		List<Service> services = ModuleUtil.getServices(module);
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			createServiceNodes(parentNode, service);
		}
	}
	
	protected void createServiceNodes(ModelTreeNode parentNode, Service service) {
		ModelTreeNode treeNode = treeNodeFactory.createServiceNode(parentNode, service, correlationId);
		createExecutionNodes(treeNode, service);
	}

	protected void createExecutionNodes(ModelTreeNode parentNode, Service service) {
		List<Execution> executions = ServiceUtil.getExecutions(service);
		Iterator<Execution> iterator = executions.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Execution execution = iterator.next();
			createExecutionNodes(parentNode, execution, i);
		}
	}

	protected void createExecutionNodes(ModelTreeNode parentNode, Execution execution, int count) {
		ModelTreeNode treeNode = treeNodeFactory.createExecutionNode(parentNode, execution, correlationId, count);
		createNodesFromExecutionInputs(treeNode, execution);
		createNodesFromExecutionOutputs(treeNode, execution);
		createNodesFromExecutionActions(treeNode, execution);
	}

	protected void createNodesFromExecutionInputs(ModelTreeNode parentNode, Execution execution) {
		List<Input> inputs = ExecutionUtil.getInputs(execution);
		Iterator<Input> iterator = inputs.iterator();
		while (iterator.hasNext()) {
			Input input = iterator.next();
			treeNodeFactory.createInputNode(parentNode, input, correlationId);
		}
	}

	protected void createNodesFromExecutionOutputs(ModelTreeNode parentNode, Execution execution) {
		List<Output> outputs = ExecutionUtil.getOutputs(execution);
		Iterator<Output> iterator = outputs.iterator();
		while (iterator.hasNext()) {
			Output output = iterator.next();
			treeNodeFactory.createOutputNode(parentNode, output, correlationId);
		}
	}

	protected void createNodesFromExecutionActions(ModelTreeNode parentNode, Execution execution) {
		List<Invocation> actions = ExecutionUtil.getActions(execution);
		Iterator<Invocation> iterator = actions.iterator();
		while (iterator.hasNext()) {
			Invocation action = iterator.next();
			treeNodeFactory.createActionNode(parentNode, action, correlationId);
		}
	}
	
}
