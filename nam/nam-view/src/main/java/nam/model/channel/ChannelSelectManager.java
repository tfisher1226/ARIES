package nam.model.channel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Channel;
import nam.model.util.ChannelUtil;


@SessionScoped
@Named("channelSelectManager")
public class ChannelSelectManager extends AbstractSelectManager<Channel, ChannelListObject> implements Serializable {
	
	@Inject
	private ChannelDataManager channelDataManager;
	
	@Inject
	private ChannelHelper channelHelper;
	
	
	@Override
	public String getClientId() {
		return "channelSelect";
	}
	
	@Override
	public String getTitle() {
		return "Channel Selection";
	}
	
	@Override
	protected Class<Channel> getRecordClass() {
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
	
	protected ChannelHelper getChannelHelper() {
		return BeanContext.getFromSession("channelHelper");
	}
	
	protected ChannelListManager getChannelListManager() {
		return BeanContext.getFromSession("channelListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshChannelList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Channel> recordList) {
		ChannelListManager channelListManager = getChannelListManager();
		DataModel<ChannelListObject> dataModel = channelListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshChannelList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Channel> refreshRecords() {
		try {
			Collection<Channel> records = channelDataManager.getChannelList();
			return records;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<Channel> channelList) {
		Collections.sort(channelList, new Comparator<Channel>() {
			public int compare(Channel channel1, Channel channel2) {
				String text1 = ChannelUtil.toString(channel1);
				String text2 = ChannelUtil.toString(channel2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
