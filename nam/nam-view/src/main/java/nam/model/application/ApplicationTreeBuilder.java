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
import nam.model.Element;
import nam.model.Module;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ApplicationUtil;
import nam.model.util.ElementUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.ui.design.AbstractTreeBuilder;
import nam.ui.tree.ModelTreeNode;


public class ApplicationTreeBuilder extends AbstractTreeBuilder implements Serializable {

	public void createTree(ModelTreeNode rootNode, Project project) {
		ModelTreeNode projectNode = treeNodeFactory.createProjectNode(project, correlationId);
		treeNodeFactory.addNode(rootNode, projectNode);
		create_ModuleDomains_node(projectNode, ProjectUtil.getModules(project));
		create_ApplicationDomains_node(projectNode, ProjectUtil.getApplications(project));
	}
	
	protected void create_ApplicationDomains_node(ModelTreeNode parentNode, Collection<Application> applicationCollection) {
		if (applicationCollection.size() > 0) {
			ModelTreeNode applicationsNode = treeNodeFactory.createFolderNode("Applications");
			treeNodeFactory.addNode(parentNode, applicationsNode);
			create_ApplicationDomain_nodes(applicationsNode, applicationCollection);
			applicationsNode.setExpanded(true);
		}
	}
		
	protected void create_Services_node(ModelTreeNode parentNode, Collection<Service> serviceCollection) {
		if (serviceCollection.size() > 0) {
			ModelTreeNode servicesNode = treeNodeFactory.createFolderNode("Services");
			treeNodeFactory.addNode(parentNode, servicesNode);
			create_Service_nodes(servicesNode, serviceCollection);
		}
	}
	
	protected void create_Modules_node(ModelTreeNode parentNode, Collection<Module> moduleCollection) {
		if (moduleCollection.size() > 0) {
			ModelTreeNode modulesNode = treeNodeFactory.createFolderNode("Modules");
			treeNodeFactory.addNode(parentNode, modulesNode);
			create_Module_nodes(modulesNode, moduleCollection);
		}
	}
	
	protected void create_Elements_node(ModelTreeNode parentNode, Collection<Element> elementCollection) {
		if (elementCollection.size() > 0) {
			ModelTreeNode elementsNode = treeNodeFactory.createFolderNode("Elements");
			treeNodeFactory.addNode(parentNode, elementsNode);
			//create_Element_nodes(elementsNode, elementCollection);
		}
	}
	
	protected void create_ModuleDomains_node(ModelTreeNode parentNode, Collection<Module> moduleCollection) {
		if (moduleCollection.size() > 0) {
			ModelTreeNode modulesNode = treeNodeFactory.createFolderNode("Modules");
			treeNodeFactory.addNode(parentNode, modulesNode);
			create_ModuleDomain_nodes(modulesNode, moduleCollection);
			modulesNode.setExpanded(true);
		}
	}
	
	protected void create_ApplicationDomain_nodes(ModelTreeNode parentNode, Collection<Application> applicationCollection) {
		Map<String, List<Application>> map = create_ApplicationByGroupId_map(applicationCollection);
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
	
	protected Map<String, List<Application>> create_ApplicationByGroupId_map(Collection<Application> applicationCollection) {
		Map<String, List<Application>> map = new HashMap<String, List<Application>>();
		Iterator<Application> iterator = applicationCollection.iterator();
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
	
	protected void create_ModuleDomain_nodes(ModelTreeNode parentNode, Collection<Module> moduleCollection) {
		Map<String, List<Module>> map = create_ModuleByGroupId_map(moduleCollection);
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
	
	protected Map<String, List<Module>> create_ModuleByGroupId_map(Collection<Module> moduleCollection) {
		Map<String, List<Module>> map = new HashMap<String, List<Module>>();
		Iterator<Module> iterator = moduleCollection.iterator();
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
	
	protected void create_Service_nodes(ModelTreeNode parentNode, Collection<Service> serviceCollection) {
		Iterator<Service> iterator = serviceCollection.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			create_Service_node(parentNode, service);
		}
	}
	
	protected void create_Service_node(ModelTreeNode parentNode, Service service) {
		ModelTreeNode serviceNode = treeNodeFactory.createServiceNode(parentNode, service, correlationId);
	}
	
	protected void create_Application_nodes(ModelTreeNode parentNode, Collection<Application> applicationCollection) {
		Iterator<Application> iterator = applicationCollection.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			create_Application_node(parentNode, application);
		}
	}
	
	protected void create_Application_node(ModelTreeNode parentNode, Application application) {
		ModelTreeNode applicationNode = treeNodeFactory.createApplicationNode(parentNode, application, correlationId);
		create_Modules_node(applicationNode, ApplicationUtil.getModules(application));
		create_Elements_node(applicationNode, ApplicationUtil.getElements(application));
		create_Services_node(applicationNode, ApplicationUtil.getServices(application));
	}
	
	protected void create_Module_nodes(ModelTreeNode parentNode, Collection<Module> moduleCollection) {
		Iterator<Module> iterator = moduleCollection.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			create_Module_node(parentNode, module);
		}
	}
	
	protected void create_Module_node(ModelTreeNode parentNode, Module module) {
		ModelTreeNode moduleNode = treeNodeFactory.createModuleNode(parentNode, module, correlationId);
		create_Elements_node(moduleNode, ModuleUtil.getElements(module));
		create_Services_node(moduleNode, ModuleUtil.getServices(module));
	}
	
	protected void create_Element_nodes(ModelTreeNode parentNode, Collection<Element> elementCollection) {
		Iterator<Element> iterator = elementCollection.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			create_Element_node(parentNode, element);
		}
	}
	
	protected void create_Element_node(ModelTreeNode parentNode, Element element) {
		ModelTreeNode elementNode = treeNodeFactory.createElementNode(parentNode, element, correlationId);
	}
	
}
