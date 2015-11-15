package nam.model.application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Application;
import nam.model.Applications;
import nam.model.Element;
import nam.model.Module;
import nam.model.Modules;
import nam.model.Project;
import nam.model.Service;
import nam.model.Services;
import nam.model.util.ApplicationUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServicesUtil;
import nam.ui.design.AbstractTreeBuilder;
import nam.ui.tree.ModelTreeNode;


public class ApplicationTreeBuilder2 extends AbstractTreeBuilder implements Serializable {

	protected Map<String, List<Module>> createModuleByGroupIdMap(Collection<Module> moduleList) {
		Map<String, List<Module>> map = new HashMap<String, List<Module>>();
		Iterator<Module> iterator = moduleList.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			String groupId = module.getGroupId();
			List<Module> list = map.get(groupId);
			if (list == null) {
				list = new ArrayList<Module>();
				map.put(groupId, list);
			}
			list.add(module);
		}
		return map;
	}
	
	protected Map<String, List<Application>> createApplicationByGroupIdMap(Collection<Application> applicationList) {
		Map<String, List<Application>> map = new HashMap<String, List<Application>>();
		Iterator<Application> iterator = applicationList.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			String groupId = application.getGroupId();
			List<Application> list = map.get(groupId);
			if (list == null) {
				list = new ArrayList<Application>();
				map.put(groupId, list);
			}
			list.add(application);
		}
		return map;
	}
	
	public void createTree(ModelTreeNode rootNode, Project project) {
		ModelTreeNode projectNode = treeNodeFactory.createProjectNode(project, correlationId);
		treeNodeFactory.addNode(rootNode, projectNode);
		create_Modules_node(projectNode, project.getModules());
		create_Applications_node(projectNode, project.getApplications());
	}
	
	protected void create_Services_node(ModelTreeNode parentNode, Services services) {
		ModelTreeNode servicesNode = treeNodeFactory.createFolderNode("Services");
		treeNodeFactory.addNode(parentNode, servicesNode);
		List<Service> list = ServicesUtil.getServices(services);
		Iterator<Service> iterator = list.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			create_Service_node(servicesNode, service);
		}
		servicesNode.setExpanded(true);
	}
	
	protected void create_Modules_node(ModelTreeNode parentNode, Modules modules) {
		ModelTreeNode modulesNode = treeNodeFactory.createFolderNode("Modules");
		treeNodeFactory.addNode(parentNode, modulesNode);
		create_ModuleDomain_nodes(modulesNode, ModuleUtil.getModules(modules));
		modulesNode.setExpanded(true);
	}
	
	protected void create_Module_node(ModelTreeNode parentNode, Module module) {
		ModelTreeNode moduleNode = treeNodeFactory.createModuleNode(parentNode, module, correlationId);
		create_Elements_nodes(moduleNode, ModuleUtil.getElements(module));
		create_Services_nodes(moduleNode, ModuleUtil.getServices(module));
	}
	
	protected void create_Applications_node(ModelTreeNode parentNode, Applications applications) {
		ModelTreeNode applicationsNode = treeNodeFactory.createFolderNode("Applications");
		treeNodeFactory.addNode(parentNode, applicationsNode);
		Collection<Application> applicationList = ApplicationUtil.getApplications(applications);
		create_ApplicationDomain_nodes(applicationsNode, applicationList);
		applicationsNode.setExpanded(true);
	}
	
	protected void create_ApplicationDomain_nodes(ModelTreeNode parentNode, Collection<Application> applicationCollection) {
		Map<String, List<Application>> map = createApplicationByGroupIdMap(applicationCollection);
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String groupId = iterator.next();
			ModelTreeNode domainNode = treeNodeFactory.createDomainNode(groupId);
			treeNodeFactory.addNode(parentNode, domainNode);
			List<Application> list = map.get(groupId);
			create_Application_nodes(domainNode, list);
		}
	}

	protected void create_Application_nodes(ModelTreeNode parentNode, List<Application> applicationList) {
		Iterator<Application> iterator = applicationList.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			create_Application_node(parentNode, application);
		}
	}
	
	protected void create_Application_node(ModelTreeNode parentNode, Application application) {
		ModelTreeNode applicationNode = treeNodeFactory.createApplicationNode(parentNode, application, correlationId);
		create_Module_nodes(applicationNode, ApplicationUtil.getModules(application));
		//create_Elements_nodes(applicationNode, ApplicationUtil.getElements(application));
		create_Services_nodes(applicationNode, ApplicationUtil.getServices(application));
	}
	
	protected void create_ModuleDomain_nodes(ModelTreeNode parentNode, Collection<Module> moduleCollection) {
		Map<String, List<Module>> map = createModuleByGroupIdMap(moduleCollection);
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String groupId = iterator.next();
			ModelTreeNode domainNode = treeNodeFactory.createDomainNode(groupId);
			treeNodeFactory.addNode(parentNode, domainNode);
			List<Module> list = map.get(groupId);
			create_Module_nodes(domainNode, list);
		}
	}
	
	protected void create_Module_nodes(ModelTreeNode parentNode, Collection<Module> moduleList) {
		Iterator<Module> iterator = moduleList.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			create_Module_node(parentNode, module);
		}
	}


	
//	protected void create_ApplicationDomain_node(ModelTreeNode parentNode, Applications applications) {
//		ModelTreeNode applicationDomainNode = treeNodeFactory.createDomainNode("ApplicationDomain");
//		treeNodeFactory.addNode(parentNode, applicationDomainNode);
//		create_Application_node(applicationDomainNode, application);
//	}
//	
//	protected void create_ModuleDomain_node(ModelTreeNode parentNode, ModuleDomain moduleDomain) {
//		ModelTreeNode moduleDomainNode = treeNodeFactory.createDomainNode("ModuleDomain");
//		treeNodeFactory.addNode(parentNode, moduleDomainNode);
//		create_Module_node(moduleDomainNode, module);
//	}
	
//	protected void create_Elements_node(ModelTreeNode parentNode, Elements elements) {
//	ModelTreeNode elementsNode = treeNodeFactory.createFolderNode("Elements");
//	treeNodeFactory.addNode(parentNode, elementsNode);
//	List<Element> list = ElementUtil.getElements(elements);
//	Iterator<Element> iterator = list.iterator();
//	while (iterator.hasNext()) {
//		Element element = iterator.next();
//		create_Element_node(elementsNode, element);
//	}
//	elementsNode.setExpanded(true);
//}
	
	protected void create_Service_node(ModelTreeNode parentNode, Service service) {
		ModelTreeNode serviceNode = treeNodeFactory.createServiceNode(parentNode, service, correlationId);
		treeNodeFactory.addNode(parentNode, serviceNode);
	}
	
	protected void create_Elements_nodes(ModelTreeNode parentNode, List<Element> elements) {
		if (elements.size() > 0) {
			ModelTreeNode elementsNode = treeNodeFactory.createFolderNode("Elements");
			treeNodeFactory.addNode(parentNode, elementsNode);
			Iterator<Element> iterator = elements.iterator();
			while (iterator.hasNext()) {
				Element element = iterator.next();
				create_Element_node(elementsNode, element);
			}
		}
	}
	
	protected void create_Element_node(ModelTreeNode parentNode, Element element) {
		ModelTreeNode elementNode = treeNodeFactory.createElementNode(parentNode, element, correlationId);
		treeNodeFactory.addNode(parentNode, elementNode);
	}
	
	protected void create_Services_nodes(ModelTreeNode parentNode, Collection<Service> services) {
		if (services.size() > 0) {
			ModelTreeNode servicesNode = treeNodeFactory.createFolderNode("Services");
			treeNodeFactory.addNode(parentNode, servicesNode);
			Iterator<Service> iterator = services.iterator();
			while (iterator.hasNext()) {
				Service service = iterator.next();
				create_Service_node(servicesNode, service);
			}
		}
	}
	

	protected void create_Element_node(ModelTreeNode parentNode, Service service) {
		ModelTreeNode serviceNode = treeNodeFactory.createServiceNode(parentNode, service, correlationId);
		treeNodeFactory.addNode(parentNode, serviceNode);
	}
	

	
	
	protected void create_Modules_FolderNode(ModelTreeNode parentNode, Project project) {
		ModelTreeNode modulesNode = treeNodeFactory.createFolderNode("Modules");
		treeNodeFactory.addNode(parentNode, modulesNode);
		create_ModuleDomain_DomainNodes(modulesNode, ProjectUtil.getModules(project));
		modulesNode.setExpanded(true);
	}
	
	protected void create_ModuleDomain_DomainNodes(ModelTreeNode parentNode, Set<Module> modules) {
		Map<String, List<Module>> moduleDomainMap = createModuleByGroupIdMap(modules);
		Set<String> moduleDomains = moduleDomainMap.keySet();
		Iterator<String> iterator = moduleDomains.iterator();
		while (iterator.hasNext()) {
			String moduleDomain = iterator.next();
			ModelTreeNode moduleDomainNode = treeNodeFactory.createDomainNode(moduleDomain);
			treeNodeFactory.addNode(parentNode, moduleDomainNode);
			List<Module> modulesForDomain = moduleDomainMap.get(moduleDomain);
			create_Module_ElementNodes(moduleDomainNode, modulesForDomain);
		}
	}

	protected void create_Module_ElementNodes(ModelTreeNode parentNode, List<Module> modules) {
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			create_Module_ElementNode(parentNode, module);
		}
	}

	protected void create_Module_ElementNode(ModelTreeNode parentNode, Module module) {
		ModelTreeNode moduleNode = treeNodeFactory.createModuleNode(parentNode, module, correlationId);
		treeNodeFactory.addNode(parentNode, moduleNode);
		//create_Module_ElementNodes(moduleNode, applications);
	}

	protected void create_Applications_FolderNode(ModelTreeNode parentNode, Project project) {
		ModelTreeNode applicationsNode = treeNodeFactory.createFolderNode("Applications");
		treeNodeFactory.addNode(parentNode, applicationsNode);
		Collection<Application> applications = ProjectUtil.getApplications(project);
		createApplicationsFolder_ApplicationNodes(applicationsNode, applications);
		applicationsNode.setExpanded(true);
	}

	protected void createApplicationsFolder_ApplicationNodes(ModelTreeNode parentNode, Collection<Application> applications) {
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			createApplicationNode(parentNode, application);
		}
	}

	protected void createApplicationNode(ModelTreeNode parentNode, Application application) {
		ModelTreeNode applicationNode = treeNodeFactory.createApplicationNode(parentNode, application, correlationId);
		ModelTreeNode folderNode = treeNodeFactory.createFolderNode("Application");
		
		ModelTreeNode modulesNode = treeNodeFactory.createFOLDERNode("Modules");
		treeNodeFactory.addNode(applicationNode, modulesNode);
		if (application.getName().equals("admin"))
			System.out.println();
		createNodesFromModules(modulesNode, ApplicationUtil.getModules(application));
	}

	protected void createApplicationsFolder_ModuleNodes(ModelTreeNode parentNode, Set<Module> modules) {
		
	}

	protected void createTree2(ModelTreeNode rootNode, Project project) {
		//List<ModelTreeNode> nodes = new ArrayList<ModelTreeNode>();
		//Model model = WorkspaceUtil.getModel(workspace);
		ModelTreeNode projectNode = treeNodeFactory.createProjectNode(project, correlationId);
		treeNodeFactory.addNode(rootNode, projectNode);
		
		ModelTreeNode folderNode = treeNodeFactory.createFOLDERNode("Applications");
		treeNodeFactory.addNode(projectNode, folderNode);
		folderNode.setExpanded(true);
		
		createGroupNodesFromApplications(folderNode, ProjectUtil.getApplications(project));
		createNodesFromApplications(ProjectUtil.getApplications(project));
		//createNodesFromModules(ProjectUtil.getApplicationModules(project));
		//nodes.add(projectNode);
		//return nodes;
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
			//groupNode.setExpanded(true);
			groupNodes.put(groupId, groupNode);
			groupNode.setExpanded(false);
			treeNodeFactory.addNode(parentNode, groupNode);
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
		ModelTreeNode applicationNode = treeNodeFactory.createApplicationNode(parentNode, application, correlationId);
		ModelTreeNode modulesNode = treeNodeFactory.createFOLDERNode("Modules");
		treeNodeFactory.addNode(applicationNode, modulesNode);
		createNodesFromModules(modulesNode, ApplicationUtil.getModules(application));
	}
	
	protected void createNodesFromModules(ModelTreeNode parentNode, Collection<Module> modules) {
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = (Module) iterator.next();
			createNodesFromModule(parentNode, module);
		}
	}

	protected void createNodesFromModule(ModelTreeNode parentNode, Module module) {
		ModelTreeNode treeNode = treeNodeFactory.createModuleNode(parentNode, module, correlationId);

		switch (module.getType()) {
		case POM: createNodesFromPOMModule(treeNode, module); break;
		case EAR: createNodesFromEARModule(treeNode, module); break;
		case WAR: createNodesFromWARModule(treeNode, module); break;
		case DATA: treeNodeFactory.createNodesFromDataModule(treeNode, module, correlationId); break;
		case MODEL: treeNodeFactory.createNodesFromModelModule(treeNode, module, correlationId); break;
		case CLIENT: treeNodeFactory.createNodesFromClientModule(treeNode, module, correlationId); break;
		case SERVICE: treeNodeFactory.createNodesFromServiceModule(treeNode, module, correlationId); break;
		default: break;
		}
	}

	protected void createNodesFromPOMModule(ModelTreeNode parentNode, Module module) {
	}
	
	protected void createNodesFromEARModule(ModelTreeNode parentNode, Module module) {
	}
	
	protected void createNodesFromWARModule(ModelTreeNode parentNode, Module module) {
	}
	
}
