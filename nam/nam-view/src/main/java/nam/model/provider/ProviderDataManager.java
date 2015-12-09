package nam.model.provider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Application;
import nam.model.Messaging;
import nam.model.Module;
import nam.model.Persistence;
import nam.model.Project;
import nam.model.Provider;
import nam.model.util.ApplicationUtil;
import nam.model.util.MessagingUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("providerDataManager")
public class ProviderDataManager implements Serializable {
	
	@Inject
	private ProviderEventManager providerEventManager;
	
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
	
	public Collection<Provider> getProviderList() {
		if (scope == null)
			return null;
		
		if (scope.equals("projectSelection")) {
			return getProviderList_ForProjectSelection();
		}
		
		if (scope.equals("applicationSelection")) {
			return getProviderList_ForApplicationSelection();
		}
		
		if (scope.equals("moduleSelection")) {
			return getProviderList_ForModuleSelection();
		}
		
		if (scope.equals("messagingSelection")) {
			return getProviderList_ForMessagingSelection();
		}
		
		if (scope.equals("persistenceSelection")) {
			return getProviderList_ForPersistenceSelection();
		}
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("messaging")) {
			return getProviderList_ForMessaging((Messaging) owner);
		
		} else if (scope.equals("persistence")) {
			return getProviderList_ForPersistence((Persistence) owner);
		
		} else {
			return getDefaultList();
		}
	}
	
	protected Collection<Provider> getProviderList_ForProjectSelection() {
		Collection<Provider> providerList = new ArrayList<Provider>();
		Collection<Project> projectList = selectionContext.getSelection("projectList");
		providerList.addAll(MessagingUtil.getProviders(ProjectUtil.getMessagingBlocks(projectList)));
		providerList.addAll(PersistenceUtil.getProviders(ProjectUtil.getPersistenceBlocks(projectList)));
		return providerList;
	}

	protected Collection<Provider> getProviderList_ForApplicationSelection() {
		Collection<Project> projectList = selectionContext.getSelection("projectList");
		Collection<Application> applicationSelection = selectionContext.getSelection("applicationSelection");
		Collection<Provider> providerList = ApplicationUtil.getProviders(projectList, applicationSelection);
		return providerList;
	}

	protected Collection<Provider> getProviderList_ForModuleSelection() {
		Collection<Project> projectList = selectionContext.getSelection("projectList");
		Collection<Module> moduleSelection = selectionContext.getSelection("moduleSelection");
		Collection<Provider> providerList = ModuleUtil.getProviders(projectList, moduleSelection);
		return providerList;
	}

	protected Collection<Provider> getProviderList_ForMessaging(Messaging messaging) {
		return MessagingUtil.getProviders(messaging);
	}
	
	protected Collection<Provider> getProviderList_ForMessagingSelection() {
		Collection<Messaging> messagingSelection = selectionContext.getSelection("messagingSelection");
		Collection<Provider> providerList = MessagingUtil.getProviders(messagingSelection);
		return providerList;
	}
	
	protected Collection<Provider> getProviderList_ForPersistence(Persistence persistence) {
		return PersistenceUtil.getProviders(persistence);
	}
	
	protected Collection<Provider> getProviderList_ForPersistenceSelection() {
		Collection<Persistence> persistenceSelection = selectionContext.getSelection("persistenceSelection");
		Collection<Provider> providerList = PersistenceUtil.getProviders(persistenceSelection);
		return providerList;
	}
	
	protected Collection<Provider> getDefaultList() {
		Collection<Provider> providerList = new HashSet<Provider>();
		List<Project> projectList = selectionContext.getSelection("projectList");
		Iterator<Project> iterator = projectList.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			providerList.addAll(getProviderList(project));
		}
		return providerList;
	}

	protected Collection<Provider> getProviderList(Project project) {
		return ProjectUtil.getProviders(project);
	}

	public void saveProvider(Provider provider) {
		if (scope != null) {
			Object owner = getOwner();
		
			if (scope.equals("messaging")) {
				MessagingUtil.addProvider((Messaging) owner, provider);
		
			} else if (scope.equals("persistence")) {
				PersistenceUtil.addProvider((Persistence) owner, provider);
			}
		}
	}
	
	public boolean removeProvider(Provider provider) {
		if (scope != null) {
			Object owner = getOwner();
		
			if (scope.equals("messaging")) {
				return MessagingUtil.removeProvider((Messaging) owner, provider);
		
			} else if (scope.equals("persistence")) {
				return PersistenceUtil.removeProvider((Persistence) owner, provider);
			}
		}
		return false;
	}
	
}
