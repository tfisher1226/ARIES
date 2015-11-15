package nam.model.messaging;

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

import nam.model.Messaging;
import nam.model.util.MessagingUtil;


@SessionScoped
@Named("messagingSelectManager")
public class MessagingSelectManager extends AbstractSelectManager<Messaging, MessagingListObject> implements Serializable {
	
	@Inject
	private MessagingDataManager messagingDataManager;
	
	@Inject
	private MessagingHelper messagingHelper;
	
	
	@Override
	public String getClientId() {
		return "messagingSelect";
	}
	
	@Override
	public String getTitle() {
		return "Messaging Selection";
	}
	
	@Override
	protected Class<Messaging> getRecordClass() {
		return Messaging.class;
	}
	
	@Override
	public boolean isEmpty(Messaging messaging) {
		return messagingHelper.isEmpty(messaging);
	}
	
	@Override
	public String toString(Messaging messaging) {
		return messagingHelper.toString(messaging);
	}
	
	protected MessagingHelper getMessagingHelper() {
		return BeanContext.getFromSession("messagingHelper");
	}
	
	protected MessagingListManager getMessagingListManager() {
		return BeanContext.getFromSession("messagingListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshMessagingList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Messaging> recordList) {
		MessagingListManager messagingListManager = getMessagingListManager();
		DataModel<MessagingListObject> dataModel = messagingListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshMessagingList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Messaging> refreshRecords() {
		try {
			Collection<Messaging> records = messagingDataManager.getMessagingList();
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
	public void sortRecords(List<Messaging> messagingList) {
		Collections.sort(messagingList, new Comparator<Messaging>() {
			public int compare(Messaging messaging1, Messaging messaging2) {
				String text1 = MessagingUtil.toString(messaging1);
				String text2 = MessagingUtil.toString(messaging2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
