package nam.model.messaging;

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

import nam.model.Messaging;
import nam.model.util.MessagingUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("messagingListManager")
public class MessagingListManager extends AbstractDomainListManager<Messaging, MessagingListObject> implements Serializable {
	
	@Inject
	private MessagingDataManager messagingDataManager;
	
	@Inject
	private MessagingEventManager messagingEventManager;
	
	@Inject
	private MessagingInfoManager messagingInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "messagingList";
	}
	
	@Override
	public String getTitle() {
		return "Messaging List";
	}
	
	@Override
	public Object getRecordKey(Messaging messaging) {
		return MessagingUtil.getKey(messaging);
	}
	
	@Override
	public String getRecordName(Messaging messaging) {
		return MessagingUtil.getLabel(messaging);
	}
	
	@Override
	protected Class<Messaging> getRecordClass() {
		return Messaging.class;
	}
	
	@Override
	protected Messaging getRecord(MessagingListObject rowObject) {
		return rowObject.getMessaging();
	}
	
	@Override
	public Messaging getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? MessagingUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Messaging messaging) {
		super.setSelectedRecord(messaging);
		fireSelectedEvent(messaging);
	}
	
	protected void fireSelectedEvent(Messaging messaging) {
		messagingEventManager.fireSelectedEvent(messaging);
	}
	
	public boolean isSelected(Messaging messaging) {
		Messaging selection = selectionContext.getSelection("messaging");
		boolean selected = selection != null && selection.equals(messaging);
		return selected;
	}
	
	@Override
	protected MessagingListObject createRowObject(Messaging messaging) {
		MessagingListObject listObject = new MessagingListObject(messaging);
		listObject.setSelected(isSelected(messaging));
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
	protected Collection<Messaging> createRecordList() {
		try {
			Collection<Messaging> messagingList = messagingDataManager.getMessagingList();
			if (messagingList != null)
				return messagingList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewMessaging() {
		return viewMessaging(selectedRecordKey);
	}
	
	public String viewMessaging(Object recordKey) {
		Messaging messaging = recordByKeyMap.get(recordKey);
		return viewMessaging(messaging);
	}
	
	public String viewMessaging(Messaging messaging) {
		String url = messagingInfoManager.viewMessaging(messaging);
		return url;
	}
	
	public String editMessaging() {
		return editMessaging(selectedRecordKey);
	}
	
	public String editMessaging(Object recordKey) {
		Messaging messaging = recordByKeyMap.get(recordKey);
		return editMessaging(messaging);
	}
	
	public String editMessaging(Messaging messaging) {
		String url = messagingInfoManager.editMessaging(messaging);
		return url;
	}
	
	public void removeMessaging() {
		removeMessaging(selectedRecordKey);
	}
	
	public void removeMessaging(Object recordKey) {
		Messaging messaging = recordByKeyMap.get(recordKey);
		removeMessaging(messaging);
	}
	
	public void removeMessaging(Messaging messaging) {
		try {
			if (messagingDataManager.removeMessaging(messaging))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelMessaging(@Observes @Cancelled Messaging messaging) {
		try {
			//Object key = MessagingUtil.getKey(messaging);
			//recordByKeyMap.put(key, messaging);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("messaging");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateMessaging(Collection<Messaging> messagingList) {
		return MessagingUtil.validate(messagingList);
	}
	
	public void exportMessagingList(@Observes @Export String tableId) {
		//String tableId = "pageForm:messagingListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
