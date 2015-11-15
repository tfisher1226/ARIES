package nam.model.channel;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.Channel;
import nam.model.Project;
import nam.model.util.ChannelUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("channelInfoManager")
public class ChannelInfoManager extends AbstractNamRecordManager<Channel> implements Serializable {
	
	@Inject
	private ChannelWizard channelWizard;
	
	@Inject
	private ChannelDataManager channelDataManager;
	
	@Inject
	private ChannelPageManager channelPageManager;
	
	@Inject
	private ChannelEventManager channelEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ChannelHelper channelHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ChannelInfoManager() {
		setInstanceName("channel");
	}
	
	
	public Channel getChannel() {
		return getRecord();
	}
	
	public Channel getSelectedChannel() {
		return selectionContext.getSelection("channel");
	}
	
	@Override
	public Class<Channel> getRecordClass() {
		return Channel.class;
	}
	
	@Override
	public boolean isEmpty(Channel channel) {
		return channelHelper.isEmpty(channel);
	}
	
	@Override
	public String toString(Channel channel) {
		return channelHelper.toString(channel);
	}
	
	@Override
	public void initialize() {
		Channel channel = selectionContext.getSelection("channel");
		if (channel != null)
			initialize(channel);
	}
	
	protected void initialize(Channel channel) {
		ChannelUtil.initialize(channel);
		channelWizard.initialize(channel);
		setContext("channel", channel);
	}
	
	public void handleChannelSelected(@Observes @Selected Channel channel) {
		selectionContext.setSelection("channel",  channel);
		channelPageManager.updateState(channel);
		channelPageManager.refreshMembers();
		setRecord(channel);
	}
	
	@Override
	public String newRecord() {
		return newChannel();
	}
	
	public String newChannel() {
		try {
			Channel channel = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("channel",  channel);
			String url = channelPageManager.initializeChannelCreationPage(channel);
			channelPageManager.pushContext(channelWizard);
			initialize(channel);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Channel create() {
		Channel channel = ChannelUtil.create();
		return channel;
	}
	
	@Override
	public Channel clone(Channel channel) {
		channel = ChannelUtil.clone(channel);
		return channel;
	}
	
	@Override
	public String viewRecord() {
		return viewChannel();
	}
	
	public String viewChannel() {
		Channel channel = selectionContext.getSelection("channel");
		String url = viewChannel(channel);
		return url;
	}
	
	public String viewChannel(Channel channel) {
		try {
			String url = channelPageManager.initializeChannelSummaryView(channel);
			channelPageManager.pushContext(channelWizard);
			initialize(channel);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editChannel();
	}
	
	public String editChannel() {
		Channel channel = selectionContext.getSelection("channel");
		String url = editChannel(channel);
		return url;
	}
	
	public String editChannel(Channel channel) {
		try {
			//channel = clone(channel);
			selectionContext.resetOrigin();
			selectionContext.setSelection("channel",  channel);
			String url = channelPageManager.initializeChannelUpdatePage(channel);
			channelPageManager.pushContext(channelWizard);
			initialize(channel);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveChannel() {
		Channel channel = getChannel();
		if (validateChannel(channel)) {
			if (isImmediate())
				persistChannel(channel);
			outject("channel", channel);
		}
	}
	
	public void persistChannel(Channel channel) {
		saveChannel(channel);
	}
	
	public void saveChannel(Channel channel) {
		try {
			saveChannelToSystem(channel);
			channelEventManager.fireAddedEvent(channel);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveChannelToSystem(Channel channel) {
		channelDataManager.saveChannel(channel);
	}
	
	public void handleSaveChannel(@Observes @Add Channel channel) {
		saveChannel(channel);
	}
	
	public void addChannel(Channel channel) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichChannel(Channel channel) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Channel channel) {
		return validateChannel(channel);
	}
	
	public boolean validateChannel(Channel channel) {
		Validator validator = getValidator();
		boolean isValid = ChannelUtil.validate(channel);
		Display display = getFromSession("display");
		display.setModule("channelInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveChannel() {
		display = getFromSession("display");
		display.setModule("channelInfo");
		Channel channel = selectionContext.getSelection("channel");
		if (channel == null) {
			display.error("Channel record must be selected.");
		}
	}
	
	public String handleRemoveChannel(@Observes @Remove Channel channel) {
		display = getFromSession("display");
		display.setModule("channelInfo");
		try {
			display.info("Removing Channel "+ChannelUtil.getLabel(channel)+" from the system.");
			removeChannelFromSystem(channel);
			selectionContext.clearSelection("channel");
			channelEventManager.fireClearSelectionEvent();
			channelEventManager.fireRemovedEvent(channel);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeChannelFromSystem(Channel channel) {
		if (channelDataManager.removeChannel(channel))
			setRecord(null);
	}
	
	public void cancelChannel() {
		BeanContext.removeFromSession("channel");
		channelPageManager.removeContext(channelWizard);
	}
	
}
