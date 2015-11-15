package nam.model.messagingProvider;

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

import nam.model.MessagingProvider;
import nam.model.util.MessagingProviderUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("messagingProviderListManager")
public class MessagingProviderListManager extends AbstractDomainListManager<MessagingProvider, MessagingProviderListObject> implements Serializable {
	
	@Inject
	private MessagingProviderDataManager messagingProviderDataManager;
	
	@Inject
	private MessagingProviderEventManager messagingProviderEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "messagingProviderList";
	}
	
	@Override
	public String getTitle() {
		return "MessagingProvider List";
	}
	
	@Override
	public Object getRecordKey(MessagingProvider messagingProvider) {
		return MessagingProviderUtil.getKey(messagingProvider);
	}
	
	@Override
	public String getRecordName(MessagingProvider messagingProvider) {
		return MessagingProviderUtil.toString(messagingProvider);
	}
	
	@Override
	protected Class<MessagingProvider> getRecordClass() {
		return MessagingProvider.class;
	}
	
	@Override
	protected MessagingProvider getRecord(MessagingProviderListObject rowObject) {
		return rowObject.getMessagingProvider();
	}
	
	@Override
	public MessagingProvider getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? MessagingProviderUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(MessagingProvider messagingProvider) {
		super.setSelectedRecord(messagingProvider);
		fireSelectedEvent(messagingProvider);
	}
	
	protected void fireSelectedEvent(MessagingProvider messagingProvider) {
		messagingProviderEventManager.fireSelectedEvent(messagingProvider);
	}
	
	public boolean isSelected(MessagingProvider messagingProvider) {
		MessagingProvider selection = selectionContext.getSelection("messagingProvider");
		boolean selected = selection != null && selection.equals(messagingProvider);
		return selected;
	}
	
	@Override
	protected MessagingProviderListObject createRowObject(MessagingProvider messagingProvider) {
		MessagingProviderListObject listObject = new MessagingProviderListObject(messagingProvider);
		listObject.setSelected(isSelected(messagingProvider));
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
	
	public void handleRefresh(@Observes @Refresh Object object) {
		//refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<MessagingProvider> createRecordList() {
		try {
			Collection<MessagingProvider> messagingProviderList = messagingProviderDataManager.getMessagingProviderList();
			if (messagingProviderList != null)
				return messagingProviderList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewMessagingProvider() {
		return viewMessagingProvider(selectedRecordKey);
	}
	
	public String viewMessagingProvider(Object recordKey) {
		MessagingProvider messagingProvider = recordByKeyMap.get(recordKey);
		return viewMessagingProvider(messagingProvider);
	}
	
	public String viewMessagingProvider(MessagingProvider messagingProvider) {
		MessagingProviderInfoManager messagingProviderInfoManager = BeanContext.getFromSession("messagingProviderInfoManager");
		String url = messagingProviderInfoManager.viewMessagingProvider(messagingProvider);
		return url;
	}
	
	public String editMessagingProvider() {
		return editMessagingProvider(selectedRecordKey);
	}
	
	public String editMessagingProvider(Object recordKey) {
		MessagingProvider messagingProvider = recordByKeyMap.get(recordKey);
		return editMessagingProvider(messagingProvider);
	}
	
	public String editMessagingProvider(MessagingProvider messagingProvider) {
		MessagingProviderInfoManager messagingProviderInfoManager = BeanContext.getFromSession("messagingProviderInfoManager");
		String url = messagingProviderInfoManager.editMessagingProvider(messagingProvider);
		return url;
	}
	
	public void removeMessagingProvider() {
		removeMessagingProvider(selectedRecordKey);
	}
	
	public void removeMessagingProvider(Object recordKey) {
		MessagingProvider messagingProvider = recordByKeyMap.get(recordKey);
		removeMessagingProvider(messagingProvider);
	}
	
	public void removeMessagingProvider(MessagingProvider messagingProvider) {
		try {
			if (messagingProviderDataManager.removeMessagingProvider(messagingProvider))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelMessagingProvider(@Observes @Cancelled MessagingProvider messagingProvider) {
		try {
			//Object key = MessagingProviderUtil.getKey(messagingProvider);
			//recordByKeyMap.put(key, messagingProvider);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("messagingProvider");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateMessagingProvider(Collection<MessagingProvider> messagingProviderList) {
		return MessagingProviderUtil.validate(messagingProviderList);
	}
	
	public void exportMessagingProviderList(@Observes @Export String tableId) {
		//String tableId = "pageForm:messagingProviderListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
