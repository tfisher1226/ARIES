package nam.model.messaging;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Messaging;
import nam.model.Project;
import nam.model.util.ExtensionsUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("messagingDataManager")
public class MessagingDataManager implements Serializable {
	
	@Inject
	private MessagingEventManager messagingEventManager;
	
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
	
	public Collection<Messaging> getMessagingList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("project")) {
			return getMessagingList((Project) owner);
		
		} else {
		return getDefaultList();
	}
	}
	
	protected Collection<Messaging> getMessagingList(Project project) {
		return ExtensionsUtil.getMessagingBlocks(project);
	}
	
	public Collection<Messaging> getDefaultList() {
		Collection<Project> projectList = selectionContext.getSelection("projectList");
		return ExtensionsUtil.getMessagingBlocks(projectList);
	}
	
	public void saveMessaging(Messaging messaging) {
		if (scope != null) {
			Object owner = getOwner();
		
			if (scope.equals("project")) {
				ExtensionsUtil.addExtension((Project) owner, messaging);
			}
		}
	}
	
	public boolean removeMessaging(Messaging messaging) {
		if (scope != null) {
			Object owner = getOwner();
		
			if (scope.equals("project")) {
				Project project = (Project) owner;
				//ExtensionsUtil.remExtension((Project) owner, messaging);
				//return ProjectUtil.removeMessaging((Project) owner, messaging);
				return project.getExtensions().getInformationsAndMessagingsAndPersistences().remove(messaging);
			}
		}
		return false;
	}
	
}
