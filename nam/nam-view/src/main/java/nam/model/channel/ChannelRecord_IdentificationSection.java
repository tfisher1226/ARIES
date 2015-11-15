package nam.model.channel;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Channel;
import nam.model.util.ChannelUtil;


@SessionScoped
@Named("channelIdentificationSection")
public class ChannelRecord_IdentificationSection extends AbstractWizardPage<Channel> implements Serializable {

	private Channel channel;


	public ChannelRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setChannel(channel);
	}
	
	@Override
	public void validate() {
		if (channel == null) {
			validator.missing("Channel");
		} else {
			if (StringUtils.isEmpty(channel.getName()))
				validator.missing("Name");
		}
	}
	
}
