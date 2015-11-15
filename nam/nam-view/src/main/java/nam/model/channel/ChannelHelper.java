package nam.model.channel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;
import org.aries.ui.model.ListTableModel;

import nam.model.Channel;
import nam.model.util.ChannelUtil;


@SessionScoped
@Named("channelHelper")
public class ChannelHelper extends AbstractElementHelper<Channel> implements Serializable {
	
	@Override
	public boolean isEmpty(Channel channel) {
		return ChannelUtil.isEmpty(channel);
	}
	
	@Override
	public String toString(Channel channel) {
		return ChannelUtil.toString(channel);
	}
	
	@Override
	public String toString(Collection<Channel> channelList) {
		return ChannelUtil.toString(channelList);
	}
	
	@Override
	public boolean validate(Channel channel) {
		return ChannelUtil.validate(channel);
	}
	
	@Override
	public boolean validate(Collection<Channel> channelList) {
		return ChannelUtil.validate(channelList);
	}
	
	public DataModel<String> getClients(Channel channel) {
		if (channel == null)
			return null;
		return getClients(channel.getClients());
	}
	
	public DataModel<String> getClients(Collection<String> clientsList) {
		List<String> values = new ArrayList<String>(clientsList);
		@SuppressWarnings("unchecked") DataModel<String> dataModel = new ListTableModel<String>(values);
		return dataModel;
	}
	
	public DataModel<String> getManagers(Channel channel) {
		if (channel == null)
			return null;
		return getManagers(channel.getManagers());
	}
	
	public DataModel<String> getManagers(Collection<String> managersList) {
		List<String> values = new ArrayList<String>(managersList);
		@SuppressWarnings("unchecked") DataModel<String> dataModel = new ListTableModel<String>(values);
		return dataModel;
	}
	
	public DataModel<String> getServices(Channel channel) {
		if (channel == null)
			return null;
		return getServices(channel.getServices());
	}
	
	public DataModel<String> getServices(Collection<String> servicesList) {
		List<String> values = new ArrayList<String>(servicesList);
		@SuppressWarnings("unchecked") DataModel<String> dataModel = new ListTableModel<String>(values);
		return dataModel;
	}
	
}
