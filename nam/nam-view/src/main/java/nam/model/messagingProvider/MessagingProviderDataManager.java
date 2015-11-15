package nam.model.messagingProvider;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.MessagingProvider;
import nam.model.util.MessagingProviderUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("messagingProviderDataManager")
public class MessagingProviderDataManager implements Serializable {
	
	@Inject
	private MessagingProviderEventManager messagingProviderEventManager;
	
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
	public Collection<MessagingProvider> getMessagingProviderList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<MessagingProvider> getDefaultList() {
		return null;
	}
	
	public void saveMessagingProvider(MessagingProvider messagingProvider) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeMessagingProvider(MessagingProvider messagingProvider) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
