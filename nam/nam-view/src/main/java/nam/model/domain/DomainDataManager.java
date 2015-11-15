package nam.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import nam.model.Application;
import nam.model.Domain;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ApplicationUtil;
import nam.model.util.DomainUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.ui.design.SelectionContext;

import org.aries.RuntimeContext;
import org.aries.runtime.BeanContext;
import org.aries.util.NameUtil;

import aries.generation.engine.GenerationContext;


@SessionScoped
@Named("domainDataManager")
public class DomainDataManager implements Serializable {
	
	@Inject
	private DomainEventManager domainEventManager;
	
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
	
	protected GenerationContext getContext() {
		ServletContext servletContext = RuntimeContext.getInstance().getServletContext();
		String contextId = (String) servletContext.getAttribute("contextId");
		GenerationContext context = BeanContext.getFromSession(contextId + ".context");
		return context;
	}
	
	public Collection<Domain> getDomainList() {
		if (scope != null) {
			if (scope.equals("module")) {
				return getModuleDomains();
			} else if (scope.equals("service")) {
				return getServiceDomains();
			} else if (scope.startsWith("application")) {
				return getApplicationDomains();
			} else if (scope.equals("projectList")) {
				return getProjectDomains();	
			}
		}
		return null;
	}

	public Collection<Domain> getModuleDomains() {
		Collection<Project> projectList = selectionContext.getSelection("projectList");
		Collection<Module> moduleList = ProjectUtil.getModules(projectList);
		Map<String, List<Module>> map = createModuleByGroupIdMap(moduleList);
		List<Domain> domainList = new ArrayList<Domain>();
		Iterator<String> iterator = map.keySet().iterator();
		GenerationContext context = getContext();
		while (iterator.hasNext()) {
			String key = iterator.next();
			List<Module> list = map.get(key);
			Module module = list.iterator().next();
			Domain domain = DomainUtil.create();
			domain.setName(key);
			domain.setLabel(NameUtil.capName(key));
			Namespace namespace = context.getNamespaceByUri(module.getNamespace());
			domain.setNamespace(namespace);
			domainList.add(domain);
		}
		
		Collections.sort(domainList);
		return domainList;
	}

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
	
	public Collection<Domain> getServiceDomains() {
		List<Domain> domainList = new ArrayList<Domain>();
		Collection<Project> projectList = selectionContext.getSelection("projectList");
		Collection<Application> applicationList = ProjectUtil.getApplications(projectList);
		Collection<Module> modules = ApplicationUtil.getModules(applicationList);
		Iterator<Module> iterator = modules.iterator();
		GenerationContext context = getContext();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			ModuleType type = module.getType();
			if (type == ModuleType.SERVICE) {
				Domain domain = DomainUtil.create();
				String groupId = module.getGroupId();
				domain.setName(groupId);
				domain.setLabel(NameUtil.capName(groupId));
				Namespace namespace = context.getNamespaceByUri(module.getNamespace());
				domain.setNamespace(namespace);
				domainList.add(domain);
			}
		}
		Collections.sort(domainList);
		return domainList;
	}

	public Collection<Domain> getApplicationDomains() {
		List<Domain> domainList = new ArrayList<Domain>();
		Collection<Project> projectList = selectionContext.getSelection("projectList");
		Collection<Application> applicationList = ProjectUtil.getApplications(projectList);
		Map<String, List<Application>> map = createApplicationByGroupIdMap(applicationList);
		Iterator<String> iterator = map.keySet().iterator();
		GenerationContext context = getContext();
		while (iterator.hasNext()) {
			String key = iterator.next();
			List<Application> list = map.get(key);
			Application application = list.iterator().next();
			Domain domain = DomainUtil.create();
			domain.setName(key);
			domain.setLabel(NameUtil.capName(key));
			Namespace namespace = context.getNamespaceByUri(application.getNamespace());
			domain.setNamespace(namespace);
			domainList.add(domain);
		}
		
		Collections.sort(domainList);
		return domainList;
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
	

	public Collection<Domain> getProjectDomains() {
		Collection<Project> projectList = selectionContext.getSelection("projectList");
		//GenerationContext context = selectionContext.getSelection("context");
		
		Collection<Application> applicationList = ProjectUtil.getApplications(projectList);
		Map<String, List<Application>> map = createApplicationByGroupIdMap(applicationList);
		List<Domain> domainList = new ArrayList<Domain>();
		Iterator<String> iterator = map.keySet().iterator();
		GenerationContext context = getContext();
		while (iterator.hasNext()) {
			String key = iterator.next();
			List<Application> list = map.get(key);
			Application application = list.iterator().next();
			Domain domain = DomainUtil.create();
			domain.setName(key);
			domain.setLabel(NameUtil.capName(key));
			Namespace namespace = context.getNamespaceByUri(application.getNamespace());
			domain.setNamespace(namespace);
			domainList.add(domain);
		}
		
		Collections.sort(domainList);
		return domainList;
	}

	public void saveDomain(Domain domain) {
		if (scope != null) {
			if (scope.equals("module")) {
				ModuleUtil.addDomain((Module) getOwner(), domain);
			}
		}
	}
	
	public boolean removeDomain(Domain domain) {
		if (scope != null) {
			if (scope.equals("module"))
				return ModuleUtil.removeDomain((Module) getOwner(), domain);
		}
		return false;
	}
	
}
