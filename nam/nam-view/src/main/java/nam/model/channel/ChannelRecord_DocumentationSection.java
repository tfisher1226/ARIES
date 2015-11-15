package nam.model.channel;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Channel;
import nam.model.util.ChannelUtil;


@SessionScoped
@Named("channelDocumentationSection")
public class ChannelRecord_DocumentationSection extends AbstractWizardPage<Channel> implements Serializable {
	
	private Channel channel;

	
	public ChannelRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
	}

	
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public void initialize(Channel channel) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setChannel(channel);
	}
	
	@Override
	public void validate() {
		if (channel == null) {
			validator.missing("Channel");
		} else {
		}
	}

}
