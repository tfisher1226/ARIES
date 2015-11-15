package nam.model.channel;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Channel;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("channelEventManager")
public class ChannelEventManager extends AbstractEventManager<Channel> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Channel getInstance() {
		return selectionContext.getSelection("channel");
	}
	
	public void removeChannel() {
		Channel channel = getInstance();
		fireRemoveEvent(channel);
	}
	
}
