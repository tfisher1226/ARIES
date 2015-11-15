package nam.model.channel;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Channel;
import nam.model.Project;
import nam.model.util.ChannelUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("channelWizard")
@SuppressWarnings("serial")
public class ChannelWizard extends AbstractDomainElementWizard<Channel> implements Serializable {
	
	@Inject
	private ChannelDataManager channelDataManager;
	
	@Inject
	private ChannelPageManager channelPageManager;
	
	@Inject
	private ChannelEventManager channelEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Channel";
	}
	
	@Override
	public String getUrlContext() {
		return channelPageManager.getChannelWizardPage();
	}
	
	@Override
	public void initialize(Channel channel) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(channelPageManager.getSections());
		super.initialize(channel);
	}
	
	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}
	
	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}
	
	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		channelPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		channelPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		channelPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		channelPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Channel channel = getInstance();
		channelDataManager.saveChannel(channel);
		channelEventManager.fireSavedEvent(channel);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Channel channel = getInstance();
		//TODO take this out soon
		if (channel == null)
			channel = new Channel();
		channelEventManager.fireCancelledEvent(channel);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Channel channel = selectionContext.getSelection("channel");
		String name = channel.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("channelWizard");
			display.error("Channel name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
