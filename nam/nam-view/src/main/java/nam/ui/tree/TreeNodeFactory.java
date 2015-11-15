package nam.ui.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Adapter;
import nam.model.Application;
import nam.model.Channel;
import nam.model.Configuration;
import nam.model.Container;
import nam.model.Element;
import nam.model.Entity;
import nam.model.Execution;
import nam.model.Field;
import nam.model.Import;
import nam.model.Information;
import nam.model.Input;
import nam.model.Interactor;
import nam.model.Invocation;
import nam.model.Listener;
import nam.model.Master;
import nam.model.Minion;
import nam.model.Model;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Network;
import nam.model.Output;
import nam.model.Persistence;
import nam.model.Pod;
import nam.model.Project;
import nam.model.Provider;
import nam.model.Repository;
import nam.model.Resource;
import nam.model.Router;
import nam.model.Service;
import nam.model.Source;
import nam.model.Unit;
import nam.model.Volume;
import nam.model.Workspace;
import nam.model.util.ApplicationUtil;
import nam.model.util.ElementUtil;
import nam.model.util.ExecutionUtil;
import nam.model.util.InformationUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.util.NameUtil;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.core.Events;
import org.richfaces.json.JSONObject;
import org.richfaces.model.TreeNode;


@Startup
@AutoCreate
@BypassInterceptors
@Name("treeNodeFactory")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class TreeNodeFactory implements Serializable {

	//@In(required = true)
	//private Events events;
	
	//@In(required=true, value="nam.projectRegistry")
	//private ProjectRegistry projectRegistry;

	private boolean showArchiveTypeInLabels;

	private boolean showComponentTypeInLabels;

	
	public boolean getShowArchiveTypeInLabels() {
		return showArchiveTypeInLabels;
	}

	public void toggleShowArchiveTypeInLabels() {
		setShowArchiveTypeInLabels(!getShowArchiveTypeInLabels());
		raiseComponentLabelsChangedEvent();
	}
	
	public void setShowArchiveTypeInLabels(boolean showArchiveTypeInLabels) {
		this.showArchiveTypeInLabels = showArchiveTypeInLabels;
	}

	public boolean getShowComponentTypeInLabels() {
		return showComponentTypeInLabels;
	}

	public void toggleShowComponentTypeInLabels() {
		setShowComponentTypeInLabels(!getShowComponentTypeInLabels());
		raiseComponentLabelsChangedEvent();
	}
	
	public void setShowComponentTypeInLabels(boolean showComponentTypeInLabels) {
		this.showComponentTypeInLabels = showComponentTypeInLabels;
	}

	protected void raiseComponentLabelsChangedEvent() {
		Events events = BeanContext.getFromSession("org.jboss.seam.core.events");
		events.raiseEvent("nam.componentLabelsChanged");
	}

	
	/*
	 * Model File related
	 */
	
	public ModelTreeNode createWorkspaceNode(Workspace workspace, Integer id) {
		ModelTreeNode node = createNode(workspace, "Workspace", workspace.getName());
		node.setExpanded(true);
		return node;
	}
	
	public ModelTreeNode createModelFolderNode(Workspace workspace, Integer id) {
		ModelTreeNode node = createNode(workspace, "ModelFolder", workspace.getName());
		node.setExpanded(true);
		return node;
	}
	
	public ModelTreeNode createModelFileNode(Workspace workspace, Integer id) {
		ModelTreeNode node = createNode(workspace, "ModelFolder", workspace.getName());
		node.setExpanded(true);
		return node;
	}
	
	
	/*
	 * Project-related
	 */
	
	public ModelTreeNode createProjectNode(Project project, Integer id) {
		String label = project.getName();
		if (showComponentTypeInLabels) label += " (application)";
		ModelTreeNode node = createNode(project, "Project", label);
		node.setExpanded(true);
		return node;
	}

	
	/*
	 * Application-related
	 */
	
	public ModelTreeNode createApplicationNode(ModelTreeNode parentNode, Application application, Integer id) {
		String label = application.getName();
		if (showComponentTypeInLabels) label += " (application)";
		ModelTreeNode node = createNode(application, "Application", label);
		//node.setExpanded(true);
		addNode(parentNode, node);
		return node;
	}
	
	
	/*
	 * Module-related
	 */
	
	public ModelTreeNode createModuleNode(ModelTreeNode parentNode, Module module, Integer id) {
		String type = module.getType().name()+"_MODULE";
		String label = module.getArtifactId();
		if (showArchiveTypeInLabels) label = "(jar) "+label;
		if (showComponentTypeInLabels) label += " (module)";
		ModelTreeNode treeNode = createNode(module, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}
	
	public ModelTreeNode createModuleEARNode(ModelTreeNode parentNode, Module module, Integer id) {
		String type = module.getType().name()+"_MODULE";
		String label = module.getArtifactId();
		if (showArchiveTypeInLabels) label = "(ear) "+label;
		if (showComponentTypeInLabels) label += " (module)";
		ModelTreeNode treeNode = createNode(module, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}
	
	
	/*
	 * Import-related
	 */

	public ModelTreeNode createImportNode(ModelTreeNode parentNode, Import _import, Integer id) {
		String dir = _import.getDir();
		String file = _import.getFile();
		String label = dir + "/" + file;
		String type = _import.getClass().getSimpleName();
		ModelTreeNode treeNode = createNode(_import, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}
	
	
	/*
	 * Information-related
	 */
	
	public void createNodesFromModelModule(ModelTreeNode moduleNode, Module module, Integer id) {
		createNamespaceNodes(moduleNode, module, id);
		createResourceNodes(moduleNode, module, id);
	}

	public void createNamespaceNodes(ModelTreeNode parentNode, Project project, Integer id) {
		List<Namespace> namespaces = new ArrayList<Namespace>();
		namespaces.addAll(ProjectUtil.getNamespaces(project));
		namespaces.addAll(ApplicationUtil.getNamespaces(project.getApplications()));
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			createNamespaceNode(parentNode, namespace, id);
		}
	}
	
	public void createNamespaceNodes(ModelTreeNode parentNode, Module module, Integer id) {
		List<Namespace> namespaces = new ArrayList<Namespace>();
		Information informationBlock = ModuleUtil.getInformationBlock(module);
		namespaces.addAll(InformationUtil.getNamespaces(informationBlock));
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			createNamespaceNode(parentNode, namespace, id);
		}
	}
	
	public ModelTreeNode createNamespaceNode(ModelTreeNode parentNode, Namespace namespace, Integer id) {
		//String label = namespace.getUri();
		String label = namespace.getName();
		String type = namespace.getClass().getSimpleName();
		//if (showArchiveTypeInLabels) label = "(pom) "+label;
		//if (showComponentTypeInLabels) label += " (configuration)";
		ModelTreeNode treeNode = createNode(namespace, type, label);
		createElementNodes(treeNode, namespace, id);
		addNode(parentNode, treeNode);
		return treeNode;
	}
	
	public void createElementNodes(ModelTreeNode parentNode, Namespace namespace, Integer id) {
		List<Element> elements = NamespaceUtil.getElements(namespace);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			createElementNode(parentNode, element, id);
		}
	}

	public ModelTreeNode createElementNode(ModelTreeNode parentNode, Element element, Integer id) {
		String label = element.getName();
		String type = element.getClass().getSimpleName();
		//if (type != null)
		//	label += " -> "+type;
		//if (showArchiveTypeInLabels) label = "(pom) "+label;
		//if (showComponentTypeInLabels) label += " (configuration)";
		//JSONObject jsonObject = toJSON(element);
		//String json = jsonObject.toString();
		//String json = JSONUtil.toJson(element);
		ModelTreeNode treeNode = createNode(element, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}
	
	public void createFieldNodes(ModelTreeNode parentNode, Element element, Integer id) {
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			createFieldNode(parentNode, field, id);
		}
	}
	
	public ModelTreeNode createFieldNode(ModelTreeNode parentNode, Field field, Integer id) {
		String label = field.getName() + " : " + field.getType();
		String type = field.getClass().getSimpleName();
		//if (showArchiveTypeInLabels) label = "(pom) "+label;
		//if (showComponentTypeInLabels) label += " (configuration)";
		ModelTreeNode treeNode = createNode(field, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}


	
	/*
	 * Service-related
	 */
	
	public void createNodesFromServiceModule(ModelTreeNode moduleNode, Module module, Integer id) {
		createResourceNodes(moduleNode, module, id);
		createNodesFromService(moduleNode, module, id);
	}
	
	public void createNodesFromClientModule(ModelTreeNode moduleNode, Module module, Integer id) {
		createNodesFromResources(moduleNode, module, id);
		createNodesFromService(moduleNode, module, id);
	}

	public void createNodesFromService(ModelTreeNode parentNode, Module module, Integer id) {
		List<Service> services = ModuleUtil.getServices(module);
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			createNodesFromService(parentNode, service, id);
		}
	}
	
	public void createNodesFromService(ModelTreeNode parentNode, Service service, Integer id) {
		ModelTreeNode treeNode = createServiceNode(parentNode, service, id);
		createExecutionNodes(treeNode, service, id);
	}

	public void createExecutionNodes(ModelTreeNode parentNode, Service service, Integer id) {
		List<Execution> executions = ServiceUtil.getExecutions(service);
		Iterator<Execution> iterator = executions.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Execution execution = iterator.next();
			createExecutionNodes(parentNode, execution, i, id);
		}
	}

	public void createExecutionNodes(ModelTreeNode parentNode, Execution execution, int count, Integer id) {
		ModelTreeNode treeNode = createExecutionNode(parentNode, execution, id, count);
		createNodesFromExecutionInputs(treeNode, execution, id);
		createNodesFromExecutionOutputs(treeNode, execution, id);
		createNodesFromExecutionActions(treeNode, execution, id);
	}

	public void createNodesFromExecutionInputs(ModelTreeNode parentNode, Execution execution, Integer id) {
		List<Input> inputs = ExecutionUtil.getInputs(execution);
		Iterator<Input> iterator = inputs.iterator();
		while (iterator.hasNext()) {
			Input input = iterator.next();
			createInputNode(parentNode, input, id);
		}
	}

	public void createNodesFromExecutionOutputs(ModelTreeNode parentNode, Execution execution, Integer id) {
		List<Output> outputs = ExecutionUtil.getOutputs(execution);
		Iterator<Output> iterator = outputs.iterator();
		while (iterator.hasNext()) {
			Output output = iterator.next();
			createOutputNode(parentNode, output, id);
		}
	}

	public void createNodesFromExecutionActions(ModelTreeNode parentNode, Execution execution, Integer id) {
		List<Invocation> actions = ExecutionUtil.getActions(execution);
		Iterator<Invocation> iterator = actions.iterator();
		while (iterator.hasNext()) {
			Invocation action = iterator.next();
			createActionNode(parentNode, action, id);
		}
	}
	
	public ModelTreeNode createServiceNode(ModelTreeNode parentNode, Service service, Integer id) {
		String type = service.getClass().getSimpleName();
		String label = service.getName();
		//String label = "ID=\""+service.getNamespace()+"."+service.getName()+"\"";
		if (showArchiveTypeInLabels) label = "(ejb) "+label;
		if (showComponentTypeInLabels) label += " (service)";
		ModelTreeNode treeNode = createNode(service, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public ModelTreeNode createExecutionNode(ModelTreeNode parentNode, Execution execution, Integer id, int count) {
		String type = execution.getClass().getSimpleName();
		String label = "ID=\""+count+"\"";
		if (showComponentTypeInLabels) label += " (execution)";
		ModelTreeNode treeNode = createNode(execution, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public ModelTreeNode createActionNode(ModelTreeNode parentNode, Invocation action, Integer id) {
		String type = action.getClass().getSimpleName();
		String label = "ID=\""+action.getName()+"\"";
		if (showComponentTypeInLabels) label += " (action)";
		ModelTreeNode treeNode = createNode(action, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public ModelTreeNode createInputNode(ModelTreeNode parentNode, Input input, Integer id) {
		String type = input.getClass().getSimpleName();
		String label = "type=\""+input.getType()+"\" name=\""+input.getName()+"\"";
		if (showComponentTypeInLabels) label += " (input)";
		ModelTreeNode treeNode = createNode(input, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public ModelTreeNode createOutputNode(ModelTreeNode parentNode, Output output, Integer id) {
		String type = output.getClass().getSimpleName();
		String label = "type=\""+output.getType()+"\" name=\""+output.getName()+"\"";
		if (showComponentTypeInLabels) label += " (output)";
		ModelTreeNode treeNode = createNode(output, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public void createResourceNodes(ModelTreeNode parentNode, Module module, Integer id) {
		List<Resource> resources = ModuleUtil.getResources(module);
		Iterator<Resource> iterator = resources.iterator();
		while (iterator.hasNext()) {
			Resource resource = iterator.next();
			createResourceNode(parentNode, resource, id);
		}
	}

	public ModelTreeNode createResourceNode(ModelTreeNode parentNode, Resource resource, Integer id) {
		String label = resource.getPath();
		if (label == null) label = resource.getUrl();
		String type = resource.getType().toUpperCase();
		if (showComponentTypeInLabels) label += " ("+type+")";
		ModelTreeNode treeNode = createNode(resource, label, type+"_RESOURCE");
		addNode(parentNode, treeNode);
		return treeNode;
	}

	
	/*
	 * Persistence-related
	 */
	
	public ModelTreeNode createPersistenceNode(ModelTreeNode parentNode, Persistence persistence, Integer id) {
		String type = "Persistence";
		String label = persistence.getName();
		if (showComponentTypeInLabels) label += " (persistence)";
		ModelTreeNode treeNode = createNode(persistence, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public void createNodesFromDataModule(ModelTreeNode parentNode, Module module, Integer id) {
		createNodesFromResources(parentNode, module, id);
		createNodesFromRepositories(parentNode, module, id);
	}

	public void createNodesFromResources(ModelTreeNode parentNode, Module module, Integer id) {
		List<Resource> resources = ModuleUtil.getResources(module);
		Iterator<Resource> iterator = resources.iterator();
		while (iterator.hasNext()) {
			Resource resource = (Resource) iterator.next();
			createResourceNode(parentNode, resource, id);
		}
	}

	public ModelTreeNode createPersistenceProviderNode(ModelTreeNode parentNode, Provider provider, Integer id) {
		String type = "PersistenceProvider";
		String label = provider.getName();
		if (showComponentTypeInLabels) label += " (provider)";
		ModelTreeNode treeNode = createNode(provider, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}
	
	public ModelTreeNode createPersistenceAdapterNode(ModelTreeNode parentNode, Adapter adapter, Integer id) {
		String type = "PersistenceAdapter";
		String label = adapter.getName();
		if (showComponentTypeInLabels) label += " (adapter)";
		ModelTreeNode treeNode = createNode(adapter, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public void createNodesFromRepositories(ModelTreeNode parentNode, Module module, Integer id) {
		List<Repository> repositories = ModuleUtil.getRepositories(module);
		Iterator<Repository> iterator = repositories.iterator();
		while (iterator.hasNext()) {
			Repository repository = (Repository) iterator.next();
			createNodesFromRepository(parentNode, repository, id);
		}
	}

	public ModelTreeNode createNodesFromRepository(ModelTreeNode parentNode, Repository repository, Integer id) {
		String type = "PersistenceAdapter";
		String label = repository.getName();
		if (showComponentTypeInLabels) label += " (repository)";
		ModelTreeNode treeNode = createNode(repository, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}
	
	public ModelTreeNode createUnitNode(ModelTreeNode parentNode, Unit persistenceUnit, Integer id) {
		String type = persistenceUnit.getClass().getSimpleName();
		String label = persistenceUnit.getName();
		if (showComponentTypeInLabels) label += " (persistence-unit)";
		ModelTreeNode treeNode = createNode(persistenceUnit, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public ModelTreeNode createEntityBeanNode(ModelTreeNode parentNode, Entity entityBean, Integer id) {
		String label = entityBean.getType();
		if (showComponentTypeInLabels) label += " (entity-bean)";
		ModelTreeNode treeNode = createNode(entityBean, label, "EntityBean");
		addNode(parentNode, treeNode);
		return treeNode;
	}

//	public ModelTreeNode createSourceNode(ModelTreeNode parentNode, Unit persistenceUnit) {
//		String label = persistenceUnit.getSource();
//		if (showComponentTypeInLabels) label += " (data-source)";
//		ModelTreeNode treeNode = createNode(persistenceUnit, label, "Source");
//		addNode(parentNode, treeNode);
//		return treeNode;
//	}

	public ModelTreeNode createSourceNode(ModelTreeNode parentNode, Source dataSource, Integer id) {
		String label = dataSource.getName();
		if (label == null)
			label = dataSource.getJndiName();
		if (showComponentTypeInLabels) label += " (data-source)";
		ModelTreeNode treeNode = createNode(dataSource, label, "Source");
		addNode(parentNode, treeNode);
		return treeNode;
	}
	
	
	/*
	 * Messaging-related
	 */
	
	public ModelTreeNode createMessagingProviderNode(ModelTreeNode parentNode, Provider provider, Integer id) {
		String type = "MessagingProvider";
		String label = provider.getName();
		if (showComponentTypeInLabels) label += " (provider)";
		ModelTreeNode treeNode = createNode(provider, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}
	
	public ModelTreeNode createMessagingAdapterNode(ModelTreeNode parentNode, Adapter adapter, Integer id) {
		String type = "MessagingAdapter";
		String label = adapter.getName();
		if (showComponentTypeInLabels) label += " (adapter)";
		ModelTreeNode treeNode = createNode(adapter, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public ModelTreeNode createChannelNode(ModelTreeNode parentNode, Channel channel, Integer id) {
		String type = channel.getClass().getSimpleName();
		String label = channel.getName();
		if (showComponentTypeInLabels) label += " (channel)";
		ModelTreeNode treeNode = createNode(channel, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public ModelTreeNode createListenerNode(ModelTreeNode parentNode, Listener listener, Integer id) {
		String type = listener.getClass().getSimpleName();
		String label = listener.getLink();
		if (showComponentTypeInLabels) label += " (listener)";
		ModelTreeNode treeNode = createNode(listener, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public ModelTreeNode createRouterNode(ModelTreeNode parentNode, Router router, Integer id) {
		String type = router.getClass().getSimpleName();
		String label = router.getName();
		if (showComponentTypeInLabels) label += " (router)";
		ModelTreeNode treeNode = createNode(router, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public ModelTreeNode createEndpointNode(ModelTreeNode parentNode, Interactor endpoint, Integer id) {
		String type = endpoint.getClass().getSimpleName();
		String label = endpoint.getLink();
		if (showComponentTypeInLabels) label += " (endpoint)";
		ModelTreeNode treeNode = createNode(endpoint, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}
	
	
	/*
	 * Container-related
	 */
	
	public ModelTreeNode createConfigurationNode(ModelTreeNode parentNode, Configuration configuration, Integer id) {
		String type = configuration.getClass().getSimpleName();
		String label = configuration.getArtifactId();
		if (showArchiveTypeInLabels) label = "(pom) "+label;
		if (showComponentTypeInLabels) label += " (configuration)";
		ModelTreeNode treeNode = createNode(configuration, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}
	
	
//	@SuppressWarnings("rawtypes") 
//	public ModelTreeNode createNode(String label) {
//		ModelTreeObject<?> object = new ModelTreeObject(label);
//		return createNode(object);
//	}

//	@SuppressWarnings({ "rawtypes", "unchecked" }) 
//	public <T> ModelTreeNode createNode(T object, String label) {
//		ModelTreeObject<T> treeObject = new ModelTreeObject(object, label);
//		return createNode(treeObject);
//	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelTreeNode createROOTNode() {
		ModelTreeObject<?> object = new ModelTreeObject(null, "TEMP", "ROOT");
		ModelTreeNode node = createNode(object);
		node.setExpanded(true);
		return node;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelTreeNode createFolderNode(String label) {
		ModelTreeObject<?> object = new ModelTreeObject(null, "FOLDER", label);
		return createNode(object);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelTreeNode createFOLDERNode(String label) {
		ModelTreeObject<?> object = new ModelTreeObject(null, "FOLDER", label);
		return createNode(object);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelTreeNode createDomainNode(String label) {
		ModelTreeObject<?> object = new ModelTreeObject(null, "GROUP", label);
		return createNode(object);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelTreeNode createGROUPNode(String label) {
		ModelTreeObject<?> object = new ModelTreeObject(null, "GROUP", label);
		return createNode(object);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelTreeNode createTEMPNode(String label) {
		ModelTreeObject<?> object = new ModelTreeObject(null, "TEMP", label);
		return createNode(object);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" }) 
	public <T> ModelTreeNode createNode(T object, String type, String label) {
		ModelTreeObject<T> treeObject = new ModelTreeObject(object, type, label);
		ModelTreeNode node = createNode(treeObject);
		return node;
	}

	public ModelTreeNode createNode(ModelTreeObject<?> object) {
		ModelTreeNode node = new ModelTreeNode(object);
		//String id = object.getId();
		//String type = object.getType();
		//String label = object.getLabel();
		//node.setId(id);
		//String nodeId = type + ":" + label;
		//long currentTimeMillis = System.currentTimeMillis();
		//String nodeId = nodeDescripter + "[" + currentTimeMillis  + "]";
		//String nodeId = getNodeId(node);
		//object.setId(nodeId);
		//node.setId(nodeId);
		if (StringUtils.isEmpty(node.getId()))
			System.out.println();
		//System.out.println(">>>"+node.getId());
		node.setExpanded(false);
		return node;
	}

	
	public void addNode(ModelTreeNode parentNode, ModelTreeNode childNode) {
		parentNode.addNode(childNode);
		//String nodeId = getNodeId(childNode);
		//childNode.setId(nodeId);
	}
	
	public void addNodes(ModelTreeNode parentNode, Collection<ModelTreeNode> childNodes) {
		Iterator<ModelTreeNode> iterator = childNodes.iterator();
		while (iterator.hasNext()) {
			ModelTreeNode childNode = iterator.next();
			addNode(parentNode, childNode);
		}
	}


//	public String getNodeId(ModelTreeObject<?> object) {
//		long currentTimeMillis = System.currentTimeMillis();
//		String nodeDescripter = getNodeDescripter(object);
//		String id = nodeDescripter + "[" + currentTimeMillis  + "]";
//		return id;
//	}
//	
//	public String getNodeDescripter(ModelTreeObject<?> object) {
//		String type = object.getType();
//		String label = object.getLabel();
//		String nodeDescripter = type + ":" + label;
//		return nodeDescripter;
//	}
	
	public String getNodeKey(TreeNode treeNode) {
		return getNodeKey((ModelTreeNode) treeNode);
	}
	
	public String getNodeKey(ModelTreeNode treeNode) {
		String nodeKey = treeNode.getLabel();
		return nodeKey;
	}

	public String getNodeId(ModelTreeNode treeNode) {
		ModelTreeNode parentNode = (ModelTreeNode) treeNode.getParent();
		return getNodeId(parentNode, treeNode);
	}
	
	public String getNodeId(ModelTreeNode parentNode, ModelTreeNode treeNode) {
		String nodeId = "";
		while (parentNode != null) {
			String tmp = treeNode.getLabel();
			if (nodeId != null)
				nodeId = tmp + "/" + nodeId;
			else nodeId = tmp;
			ModelTreeNode newParentNode = (ModelTreeNode) parentNode.getParent();
			treeNode = parentNode;
			parentNode = newParentNode;
		}
		return nodeId;
	}
	
	public static JSONObject toJSON(Element element) {
		return new JSONObject(element, new String[] {
				"name",
				"type",
				"abstract"
		});
	}

	public ModelTreeNode createModelFileNode(Import importedFile) {
		ModelTreeObject<Import> object = createModelFileObject(importedFile);
		ModelTreeNode node = createNode(object);
		node.setExpanded(true);
		return node;
	}
	
	public ModelTreeObject<Import> createModelFileObject(Import importedFile) {
		String fileName = importedFile.getFile();
		fileName = NameUtil.getBaseNameFromPath(fileName);
		ModelTreeObject<Import> object = new ModelTreeObject<Import>(importedFile, "ModelFile", fileName);
		return object;
	}

	public ModelTreeNode createPodNode(ModelTreeNode parentNode, Pod pod, Integer correlationId) {
		String type = "Pod";
		String label = pod.getName();
		if (showComponentTypeInLabels) label += " (pod)";
		ModelTreeNode treeNode = createNode(pod, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public ModelTreeNode createMinionNode(ModelTreeNode parentNode, Minion minion, Integer correlationId) {
		String type = "Minion";
		String label = minion.getName();
		if (showComponentTypeInLabels) label += " (minion)";
		ModelTreeNode treeNode = createNode(minion, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public ModelTreeNode createNetworkNode(ModelTreeNode parentNode, Network network, Integer correlationId) {
		String type = "Network";
		String label = network.getName();
		if (showComponentTypeInLabels) label += " (network)";
		ModelTreeNode treeNode = createNode(network, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public ModelTreeNode createVolumeNode(ModelTreeNode parentNode, Volume volume, Integer correlationId) {
		String type = "Volume";
		String label = volume.getName();
		if (showComponentTypeInLabels) label += " (volume)";
		ModelTreeNode treeNode = createNode(volume, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public ModelTreeNode createMasterNode(ModelTreeNode parentNode, Master master, Integer correlationId) {
		String type = "Master";
		String label = master.getName();
		if (showComponentTypeInLabels) label += " (master)";
		ModelTreeNode treeNode = createNode(master, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public ModelTreeNode createContainerNode(ModelTreeNode parentNode, Container container, Integer correlationId) {
		String type = "Container";
		String label = container.getName();
		if (showComponentTypeInLabels) label += " (container)";
		ModelTreeNode treeNode = createNode(container, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

	public ModelTreeNode createModelNode(ModelTreeNode parentNode, Model model, Integer correlationId) {
		String type = "Model";
		String label = model.getName();
		if (showComponentTypeInLabels) label += " (model)";
		ModelTreeNode treeNode = createNode(model, type, label);
		addNode(parentNode, treeNode);
		return treeNode;
	}

}
