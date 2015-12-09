package nam.model.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.data.DataLayerHelper;
import nam.model.Application;
import nam.model.Cache;
import nam.model.Component;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.Service;
import nam.model.Transacted;
import nam.model.Unit;
import nam.model.util.ApplicationUtil;
import nam.model.util.CacheUtil;
import nam.model.util.ComponentUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("componentDataManager")
public class ComponentDataManager implements Serializable {
	
	@Inject
	private ComponentEventManager componentEventManager;
	
	@Inject
	private SelectionContext selectionContext;

	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	public void initialize(String scope) {
		this.scope = scope;
	}
	
	public Collection<Component> getComponentList() {
		if (scope == null)
			return null;
		
		if (scope.equals("projectSelection")) {
			return getComponentList_ForProjectSelection();
		}
		
		if (scope.equals("applicationSelection")) {
			return getComponentList_ForApplicationSelection();
		}
		
		if (scope.equals("moduleSelection")) {
			return getComponentList_ForApplicationSelection();
		}
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("service")) {
			return getComponentList((Service) owner);
		} else {
			return getDefaultList();
		}
	}

	public Collection<Component> getComponentList(String componentType) {
		Collection<Component> components = new ArrayList<Component>();
		
		//CACHE
		if (componentType.equals("cache")) {
			Collection<Application> applicationSelection = selectionContext.getSelection("applicationSelection");
			if (applicationSelection == null) {
				Collection<Project> projectList = selectionContext.getSelection("projectList");
				applicationSelection = ProjectUtil.getApplications(projectList);
			}
			Collection<Service> services = ApplicationUtil.getServices(applicationSelection);
			Collection<Cache> references = ServiceUtil.getCacheUnitReferences(services);
			Iterator<Cache> iterator = references.iterator();
			while (iterator.hasNext()) {
				Cache cache = iterator.next();
				String cacheName2 = CacheUtil.getCacheName(cache);
				String className2 = CacheUtil.getClassName(cache);
				String packageName2 = CacheUtil.getPackageName(cache);
				String key = CacheUtil.getKey(cache);
				String qualifiedName2 = CacheUtil.getQualifiedName(cache);
				String type2 = CacheUtil.getType(cache);
				String rootName = CacheUtil.getRootName(cache);
				String namespace = cache.getNamespace();
				if (namespace == null)
					namespace = TypeUtil.getNamespace(type2);
				
				String cacheName = DataLayerHelper.getCacheUnitNameUncapped(cache);
				String cacheNameCapped = DataLayerHelper.getCacheUnitNameCapped(cache);
				String className = DataLayerHelper.getCacheUnitClassName(cache);
				String interfaceName = DataLayerHelper.getCacheUnitInterfaceName(cache);
				String packageName = DataLayerHelper.getCacheUnitPackageName(namespace, cache);
				String qualifiedName = DataLayerHelper.getCacheUnitQualifiedName(namespace, cache);
				//String type = DataLayerHelper.getCacheUnitTypeName(namespace, cache);
				String typeName = DataLayerHelper.getCacheUnitTypeName(namespace, cache);
				//String key = UnitUtil.getKey(unit);

				Component component = new Component();
//				component.setBaseType(baseType);
//				component.setElement(element);
				component.setName(cacheName);
				component.setNamespace(namespace);
				component.setDescription("");
				component.setInterfaceName(interfaceName);
				component.setClassName(className);
				component.setPackageName(packageName);
//				component.setOperations(operations);
//				component.setDescription(description);
				component.setType(typeName);
				component.setVersion("0.0.1-SNAPSHOT");
//				component.setFields(fields);
				Transacted transacted = new Transacted();
				//transacted.setScope();
				component.setTransacted(transacted);
				component.setCached(false);
				component.setPublished(false);
				components.add(component);
			}
			ComponentUtil.sortRecords(components);
			return components;
			
		//PERSISTENCE
		} else if (componentType.equals("persistence")) {
			Collection<Application> applicationSelection = selectionContext.getSelection("applicationSelection");
			if (applicationSelection == null) {
				Collection<Project> projectList = selectionContext.getSelection("projectList");
				applicationSelection = ProjectUtil.getApplications(projectList);
			}
			Collection<Service> services = ApplicationUtil.getServices(applicationSelection);
			Collection<Unit> references = ServiceUtil.getDataUnitReferences(services);
			Iterator<Unit> iterator = references.iterator();
			while (iterator.hasNext()) {
				Unit unit = iterator.next();
				Namespace namespace = unit.getNamespace();

				String unitName = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
				String unitNameCapped = DataLayerHelper.getPersistenceUnitNameCapped(unit);
				String qualifiedName = DataLayerHelper.getPersistenceUnitQualifiedName(namespace, unit);
				String interfaceName = DataLayerHelper.getPersistenceUnitInterfaceName(unit);
				String className = DataLayerHelper.getPersistenceUnitClassName(unit);
				String packageName = UnitUtil.getPackageName(unit);

				String type = DataLayerHelper.getPersistenceUnitType(unit);
				String typeName = DataLayerHelper.getPersistenceUnitTypeName(namespace, unit);
				String key = UnitUtil.getKey(unit);
				
				Component component = new Component();
//				component.setBaseType(baseType);
//				component.setElement(element);
				component.setName(unitName);
				component.setNamespace(namespace.getUri());
				component.setDescription("");
				component.setInterfaceName(interfaceName);
				component.setClassName(className);
				component.setPackageName(packageName);
//				component.setOperations(operations);
//				component.setDescription(description);
				component.setType(typeName);
				component.setVersion("0.0.1-SNAPSHOT");
//				component.setFields(fields);
				Transacted transacted = new Transacted();
				//transacted.setScope();
				component.setTransacted(transacted);
				component.setCached(false);
				component.setPublished(false);
				components.add(component);
			}

		} else if (componentType.equals("handler")) {
		} else if (componentType.equals("scheduler")) {
		} else if (componentType.equals("processor")) {
			
		}
		return components;
	}
	
	public Collection<Component> getComponentList_ForProjectSelection() {
		Collection<Project> projectSelection = selectionContext.getSelection("projectSelection");
		Collection<Application> applicationList = ProjectUtil.getApplications(projectSelection);
		Collection<Component> components = ApplicationUtil.getComponents(applicationList);
		ComponentUtil.sortRecords(components);
		return components;
	}

	public Collection<Component> getComponentList_ForApplicationSelection() {
		Collection<Application> applicationSelection = selectionContext.getSelection("applicationSelection");
		Collection<Component> components = ApplicationUtil.getComponents(applicationSelection);
		ComponentUtil.sortRecords(components);
		return components;
	}
	
	public Collection<Component> getComponentList_ForModuleSelection() {
		Collection<Module> moduleSelection = selectionContext.getSelection("moduleSelection");
		Collection<Component> components = ModuleUtil.getComponents(moduleSelection);
		ComponentUtil.sortRecords(components);
		return components;
	}
	
	public Collection<Component> getComponentList(Service service) {
		Collection<Component> componentList = ServiceUtil.getComponents(service);
		if (service.getProcess() != null)
			componentList.add(service.getProcess());
		return componentList;
	}

	public Collection<Component> getDefaultList() {
		return null;
	}

	public void saveComponent(Component component) {
		if (scope != null) {
			Object owner = getOwner();
			if (scope.equals("service")) {
				ServiceUtil.addComponent((Service) owner, component);
			}
		}
	}
	
	public boolean removeComponent(Component component) {
		if (scope != null) {
			Object owner = getOwner();
			if (scope.equals("service")) {
				return ServiceUtil.removeComponent((Service) owner, component);
			}
		}
		return false;
	}
	
}
