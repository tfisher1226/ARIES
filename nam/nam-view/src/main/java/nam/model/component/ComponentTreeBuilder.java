package nam.model.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Application;
import nam.model.Configuration;
import nam.model.Execution;
import nam.model.Input;
import nam.model.Invocation;
import nam.model.Module;
import nam.model.Output;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ExecutionUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.ui.design.AbstractTreeBuilder;
import nam.ui.tree.ModelTreeNode;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;


@Startup
@AutoCreate
@Name("componentTreeBuilder")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class ComponentTreeBuilder extends AbstractTreeBuilder implements Serializable {

	
	protected Collection<ModelTreeNode> createTree(Project project) {
		List<ModelTreeNode> nodes = new ArrayList<ModelTreeNode>();
		ModelTreeNode node = treeNodeFactory.createProjectNode(project, correlationId);
		//TODO
		nodes.add(node);
		return nodes;
	}

	public ModelTreeNode createTree(Object object) {
		initializeTreeBuilder();
		if (object instanceof Configuration)
			return createConfigurationTree((Configuration) object);
		if (object instanceof Application)
			return createApplicationTree((Application) object);
		if (object instanceof Module)
			return createModuleTree((Module) object);
		if (object instanceof Service)
			return createServiceTree((Service) object);
		return null;
	}

	protected ModelTreeNode createConfigurationTree(Configuration configuration) {
		ModelTreeNode parentNode = treeNodeFactory.createROOTNode();
		createConfigurationNodes(parentNode, configuration);
		return parentNode;
	}

	protected void createConfigurationNodes(ModelTreeNode parentNode, Configuration configuration) {
		ModelTreeNode treeNode = treeNodeFactory.createConfigurationNode(parentNode, configuration, correlationId);
	}

	protected ModelTreeNode createApplicationTree(Application application) {
		ModelTreeNode parentNode = treeNodeFactory.createROOTNode();
		createApplicationNodes(parentNode, application);
		return parentNode;
	}

	protected void createApplicationNodes(ModelTreeNode parentNode, Application application) {
		ModelTreeNode treeNode = treeNodeFactory.createApplicationNode(parentNode, application, correlationId);
	}

	protected ModelTreeNode createModuleTree(Module module) {
		ModelTreeNode parentNode = treeNodeFactory.createROOTNode();
		createModuleNodes(parentNode, module);
		return parentNode;
	}

	protected void createModuleNodes(ModelTreeNode parentNode, Module module) {
		ModelTreeNode treeNode = treeNodeFactory.createModuleNode(parentNode, module, correlationId);
		List<Service> services = ModuleUtil.getServices(module);
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			createServiceNodes(treeNode, service);
		}
	}

	protected ModelTreeNode createServiceTree(Service service) {
		ModelTreeNode parentNode = treeNodeFactory.createROOTNode();
		createServiceNodes(parentNode, service);
		return parentNode;
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
