package nam.model.messagingProvider;

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

import nam.model.MessagingProvider;
import nam.model.util.MessagingProviderUtil;


@SessionScoped
@Named("messagingProviderSelectManager")
public class MessagingProviderSelectManager extends AbstractSelectManager<MessagingProvider, MessagingProviderListObject> implements Serializable {
	
	@Inject
	private MessagingProviderDataManager messagingProviderDataManager;
	
	@Inject
	private MessagingProviderHelper messagingProviderHelper;
	
	
	@Override
	public String getClientId() {
		return "messagingProviderSelect";
	}
	
	@Override
	public String getTitle() {
		return "MessagingProvider Selection";
	}
	
	@Override
	protected Class<MessagingProvider> getRecordClass() {
		return MessagingProvider.class;
	}
	
	@Override
	public boolean isEmpty(MessagingProvider messagingProvider) {
		return messagingProviderHelper.isEmpty(messagingProvider);
	}
	
	@Override
	public String toString(MessagingProvider messagingProvider) {
		return messagingProviderHelper.toString(messagingProvider);
	}
	
	protected MessagingProviderHelper getMessagingProviderHelper() {
		return BeanContext.getFromSession("messagingProviderHelper");
	}
	
	protected MessagingProviderListManager getMessagingProviderListManager() {
		return BeanContext.getFromSession("messagingProviderListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshMessagingProviderList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<MessagingProvider> recordList) {
		MessagingProviderListManager messagingProviderListManager = getMessagingProviderListManager();
		DataModel<MessagingProviderListObject> dataModel = messagingProviderListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshMessagingProviderList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<MessagingProvider> refreshRecords() {
		try {
			Collection<MessagingProvider> records = messagingProviderDataManager.getMessagingProviderList();
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
	public void sortRecords(List<MessagingProvider> messagingProviderList) {
		Collections.sort(messagingProviderList, new Comparator<MessagingProvider>() {
			public int compare(MessagingProvider messagingProvider1, MessagingProvider messagingProvider2) {
				String text1 = MessagingProviderUtil.toString(messagingProvider1);
				String text2 = MessagingProviderUtil.toString(messagingProvider2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
