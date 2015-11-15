package nam.model.channel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Channel;
import nam.model.Listener;
import nam.model.Messaging;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.MessagingUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.ui.design.SelectionContext;

import org.aries.Assert;


@SessionScoped
@Named("channelDataManager")
public class ChannelDataManager implements Serializable {
	
	@Inject
	private ChannelEventManager channelEventManager;
	
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
	
	public Collection<Channel> getChannelList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;

		if (scope.equals("service")) {
			return getChannelList((Service) getOwner());
		} else if (scope.equals("projectList")) {
			return getChannelList((Collection<Project>) getOwner());
		} else {
			return getDefaultList();
		}
	}
	
	public Collection<Channel> getChannelList(Service service) {
		List<Channel> channelList = ServiceUtil.getChannels(service);
		return channelList;
	}

	public Collection<Channel> getChannelList(Collection<Project> projectList) {
		List<Messaging> messagingBlocks = ProjectUtil.getMessagingBlocks(projectList);
		List<Channel> channelList = MessagingUtil.getChannels(messagingBlocks);
		return channelList;
	}

	public Collection<Channel> getDefaultList() {
		return null;
	}

	public void saveChannel(Channel channel) {
		if (scope != null) {
			if (scope.equals("service")) {
				ServiceUtil.addChannel((Service) getOwner(), channel);
			}
		}
	}
	
	public boolean removeChannel(Channel channel) {
		if (scope != null) {
			if (scope.equals("service"))
				return ServiceUtil.removeChannel((Service) getOwner(), channel);
		}
		return false;
	}
	
}
