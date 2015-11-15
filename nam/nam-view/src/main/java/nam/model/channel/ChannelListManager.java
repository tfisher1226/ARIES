package nam.model.channel;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import nam.model.Channel;
import nam.model.util.ChannelUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("channelListManager")
public class ChannelListManager extends AbstractDomainListManager<Channel, ChannelListObject> implements Serializable {
	
	@Inject
	private ChannelDataManager channelDataManager;
	
	@Inject
	private ChannelEventManager channelEventManager;
	
	@Inject
	private ChannelInfoManager channelInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "channelList";
	}
	
	@Override
	public String getTitle() {
		return "Channel List";
	}
	
	@Override
	public Object getRecordKey(Channel channel) {
		return ChannelUtil.getKey(channel);
	}
	
	@Override
	public String getRecordName(Channel channel) {
		return ChannelUtil.getLabel(channel);
	}
	
	@Override
	protected Class<Channel> getRecordClass() {
		return Channel.class;
	}
	
	@Override
	protected Channel getRecord(ChannelListObject rowObject) {
		return rowObject.getChannel();
	}
	
	@Override
	public Channel getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ChannelUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Channel channel) {
		super.setSelectedRecord(channel);
		fireSelectedEvent(channel);
	}
	
	protected void fireSelectedEvent(Channel channel) {
		channelEventManager.fireSelectedEvent(channel);
	}
	
	public boolean isSelected(Channel channel) {
		Channel selection = selectionContext.getSelection("channel");
		boolean selected = selection != null && selection.equals(channel);
		return selected;
	}
	
	@Override
	protected ChannelListObject createRowObject(Channel channel) {
		ChannelListObject listObject = new ChannelListObject(channel);
		listObject.setSelected(isSelected(channel));
		return listObject;
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
		else refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Channel> createRecordList() {
		try {
		Collection<Channel> channelList = channelDataManager.getChannelList();
			if (channelList != null)
		return channelList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewChannel() {
		return viewChannel(selectedRecordKey);
	}
	
	public String viewChannel(Object recordKey) {
		Channel channel = recordByKeyMap.get(recordKey);
		return viewChannel(channel);
	}
	
	public String viewChannel(Channel channel) {
		String url = channelInfoManager.viewChannel(channel);
		return url;
	}
	
	public String editChannel() {
		return editChannel(selectedRecordKey);
	}
	
	public String editChannel(Object recordKey) {
		Channel channel = recordByKeyMap.get(recordKey);
		return editChannel(channel);
	}
	
	public String editChannel(Channel channel) {
		String url = channelInfoManager.editChannel(channel);
		return url;
	}
	
	public void removeChannel() {
		removeChannel(selectedRecordKey);
	}
	
	public void removeChannel(Object recordKey) {
		Channel channel = recordByKeyMap.get(recordKey);
		removeChannel(channel);
	}
	
	public void removeChannel(Channel channel) {
		try {
			if (channelDataManager.removeChannel(channel))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelChannel(@Observes @Cancelled Channel channel) {
		try {
			//Object key = ChannelUtil.getKey(channel);
			//recordByKeyMap.put(key, channel);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("channel");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateChannel(Collection<Channel> channelList) {
		return ChannelUtil.validate(channelList);
	}
	
	public void exportChannelList(@Observes @Export String tableId) {
		//String tableId = "pageForm:channelListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
