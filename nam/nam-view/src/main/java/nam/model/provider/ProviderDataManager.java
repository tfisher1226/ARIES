package nam.model.provider;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.CacheProvider;
import nam.model.Messaging;
import nam.model.MessagingProvider;
import nam.model.Persistence;
import nam.model.PersistenceProvider;
import nam.model.Project;
import nam.model.Provider;
import nam.model.util.CacheProviderUtil;
import nam.model.util.MessagingProviderUtil;
import nam.model.util.MessagingUtil;
import nam.model.util.PersistenceProviderUtil;
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
	
	@SuppressWarnings("unchecked")
	public Collection<Provider> getProviderList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("messaging")) {
			return getProviderList((Messaging) owner);
		} else if (scope.equals("persistence")) {
			return getProviderList((Persistence) owner);
		} else {
			return getDefaultList();
		}
	}
	
	protected Collection<Provider> getProviderList(Messaging messaging) {
		return MessagingUtil.getProviders(messaging);
	}

	protected Collection<Provider> getProviderList(Persistence persistence) {
		return PersistenceUtil.getProviders(persistence);
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
