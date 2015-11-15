package org.aries.nam.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import nam.model.Adapter;
import nam.model.Application;
import nam.model.Channel;
import nam.model.Listener;
import nam.model.Messaging;
import nam.model.Module;
import nam.model.Persistence;
import nam.model.Project;
import nam.model.Provider;
import nam.model.Repository;
import nam.model.Unit;
import nam.model.util.AdapterUtil;
import nam.model.util.ChannelUtil;
import nam.model.util.ListenerUtil;
import nam.model.util.MessagingUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ProviderUtil;
import nam.model.util.RepositoryUtil;
import nam.model.util.UnitUtil;


//@AutoCreate
//@Scope(ScopeType.SESSION)
//@Name("nam.projectRegistry")
public class ProjectRegistry {

	private Map<String, Provider> providers = new HashMap<String, Provider>();
	private Map<String, Adapter> adapters = new HashMap<String, Adapter>();
	private Map<String, Channel> channels = new HashMap<String, Channel>();
	private Map<String, Listener> listeners = new HashMap<String, Listener>();
	private Map<String, Repository> repositories = new HashMap<String, Repository>();
	private Map<String, Unit> persistenceUnits = new HashMap<String, Unit>();

	
	public Repository getActualRepository(Repository repository) {
		String ref = repository.getRef();
		return ref == null ? repository : repositories.get(ref);
	}

	public Unit getUnit(Unit persistenceUnit) {
		String ref = persistenceUnit.getRef();
		return ref == null ? persistenceUnit : persistenceUnits.get(ref);
	}
	

	public void readProject(Project project) {
		readFromProject(project);
		//updateProject(project);
	}

//	protected void updateModel(Project project) {
//		updateModules(project.getModules());
//	}
//
//	protected void updateModules(Modules modules) {
//		if (modules != null) {
//			List<Module> list = modules.getModules();
//			Iterator<Module> iterator = list.iterator();
//			while (iterator.hasNext()) {
//				Module module = (Module) iterator.next();
//				List<Repository> repositories = ModuleUtil.getRepositories(module);
//				
//			}
//		}
//	}
//
//	protected void updateModule(Module module) {
//		List<Repository> repositories = ModuleUtil.getRepositories(module);
//		Iterator<Repository> iterator = repositories.iterator();
//		while (iterator.hasNext()) {
//			Repository repository = (Repository) iterator.next();
//			
//		}
//	}

	protected void readFromProject(Project project) {
		//readFromMessaging(project.getMessaging());
		//readFromPersistence(project.getPersistence());
		readFromApplications(ProjectUtil.getApplications(project));
		readFromModules(ProjectUtil.getApplicationModules(project));
		//model.getApplications();
	}

	protected void readFromMessaging(Messaging messaging) {
		if (messaging != null) {
			providers.putAll(ProviderUtil.createProviderMap(MessagingUtil.getProviders(messaging)));
			adapters.putAll(AdapterUtil.createAdapterMap(MessagingUtil.getAdapters(messaging)));
			channels.putAll(ChannelUtil.createChannelMap(MessagingUtil.getChannels(messaging)));
			listeners.putAll(ListenerUtil.createListenerMap(MessagingUtil.getListeners(messaging)));
		}
	}

	protected void readFromPersistence(Persistence persistence) {
		if (persistence != null) {
			repositories.putAll(RepositoryUtil.createRepositoryMap(PersistenceUtil.getRepositories(persistence)));
			persistenceUnits.putAll(UnitUtil.createUnitMap(PersistenceUtil.getUnits(persistence)));
		}
	}

	protected void readFromApplications(Collection<Application> applications) {
		Iterator<Application> iterator = applications.iterator(); 		
		while (iterator.hasNext()) {
			Application application = (Application) iterator.next();
			readFromApplication(application);
		}
	}

	protected void readFromApplication(Application application) {
		//application.get
	}
	

	protected void readFromModules(Set<Module> modules) {
		Iterator<Module> iterator = modules.iterator(); 		
		while (iterator.hasNext()) {
			Module module = (Module) iterator.next();
			readFromModule(module);
		}
	}

	protected void readFromModule(Module module) {
		//module.getRepositories();
	}

}
