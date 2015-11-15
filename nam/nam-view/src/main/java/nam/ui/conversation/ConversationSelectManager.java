package nam.ui.conversation;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.Conversation;
import nam.ui.util.ConversationUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;


@SessionScoped
@Named("conversationSelectManager")
public class ConversationSelectManager extends AbstractSelectManager<Conversation, ConversationListObject> implements Serializable {
	
	@Inject
	private ConversationDataManager conversationDataManager;
	
	@Inject
	private ConversationHelper conversationHelper;
	
	
	@Override
	public String getClientId() {
		return "conversationSelect";
	}
	
	@Override
	public String getTitle() {
		return "Conversation Selection";
	}
	
	@Override
	protected Class<Conversation> getRecordClass() {
		return Conversation.class;
	}
	
	@Override
	public boolean isEmpty(Conversation conversation) {
		return conversationHelper.isEmpty(conversation);
	}
	
	@Override
	public String toString(Conversation conversation) {
		return conversationHelper.toString(conversation);
	}

	protected ConversationListManager getConversationListManager() {
		return BeanContext.getFromSession("conversationListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshConversationList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Conversation> recordList) {
		ConversationListManager conversationListManager = getConversationListManager();
		DataModel<ConversationListObject> dataModel = conversationListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshConversationList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Conversation> refreshRecords() {
		try {
			Collection<Conversation> records = conversationDataManager.getConversationList();
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
	public void sortRecords(List<Conversation> conversationList) {
		Collections.sort(conversationList, new Comparator<Conversation>() {
			public int compare(Conversation conversation1, Conversation conversation2) {
				String text1 = ConversationUtil.toString(conversation1);
				String text2 = ConversationUtil.toString(conversation2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
