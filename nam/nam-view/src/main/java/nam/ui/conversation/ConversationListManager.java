package nam.ui.conversation;

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

import nam.ui.Conversation;
import nam.ui.design.SelectionContext;
import nam.ui.util.ConversationUtil;


@SessionScoped
@Named("conversationListManager")
public class ConversationListManager extends AbstractDomainListManager<Conversation, ConversationListObject> implements Serializable {
	
	@Inject
	private ConversationDataManager conversationDataManager;
	
	@Inject
	private ConversationEventManager conversationEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "conversationList";
	}
	
	@Override
	public String getTitle() {
		return "Conversation List";
	}
	
	@Override
	public Object getRecordKey(Conversation conversation) {
		return ConversationUtil.getKey(conversation);
	}
	
	@Override
	public String getRecordName(Conversation conversation) {
		return ConversationUtil.toString(conversation);
	}
	
	@Override
	protected Class<Conversation> getRecordClass() {
		return Conversation.class;
	}
	
	@Override
	protected Conversation getRecord(ConversationListObject rowObject) {
		return rowObject.getConversation();
	}
	
	@Override
	public Conversation getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ConversationUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Conversation conversation) {
		super.setSelectedRecord(conversation);
		fireSelectedEvent(conversation);
	}
	
	protected void fireSelectedEvent(Conversation conversation) {
		conversationEventManager.fireSelectedEvent(conversation);
	}
	
	public boolean isSelected(Conversation conversation) {
		Conversation selection = selectionContext.getSelection("conversation");
		boolean selected = selection != null && selection.equals(conversation);
		return selected;
	}
	
	@Override
	protected ConversationListObject createRowObject(Conversation conversation) {
		ConversationListObject listObject = new ConversationListObject(conversation);
		listObject.setSelected(isSelected(conversation));
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
	protected Collection<Conversation> createRecordList() {
		try {
			Collection<Conversation> conversationList = conversationDataManager.getConversationList();
			if (conversationList != null)
				return conversationList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewConversation() {
		return viewConversation(selectedRecordKey);
	}
	
	public String viewConversation(Object recordKey) {
		Conversation conversation = recordByKeyMap.get(recordKey);
		return viewConversation(conversation);
	}
	
	public String viewConversation(Conversation conversation) {
		ConversationInfoManager conversationInfoManager = BeanContext.getFromSession("conversationInfoManager");
		String url = conversationInfoManager.viewConversation(conversation);
		return url;
	}
	
	public String editConversation() {
		return editConversation(selectedRecordKey);
	}
	
	public String editConversation(Object recordKey) {
		Conversation conversation = recordByKeyMap.get(recordKey);
		return editConversation(conversation);
	}
	
	public String editConversation(Conversation conversation) {
		ConversationInfoManager conversationInfoManager = BeanContext.getFromSession("conversationInfoManager");
		String url = conversationInfoManager.editConversation(conversation);
		return url;
	}
	
	public void removeConversation() {
		removeConversation(selectedRecordKey);
	}
	
	public void removeConversation(Object recordKey) {
		Conversation conversation = recordByKeyMap.get(recordKey);
		removeConversation(conversation);
	}
	
	public void removeConversation(Conversation conversation) {
		try {
			if (conversationDataManager.removeConversation(conversation))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelConversation(@Observes @Cancelled Conversation conversation) {
		try {
			//Object key = ConversationUtil.getKey(conversation);
			//recordByKeyMap.put(key, conversation);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("conversation");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateConversation(Collection<Conversation> conversationList) {
		return ConversationUtil.validate(conversationList);
	}
	
	public void exportConversationList(@Observes @Export String tableId) {
		//String tableId = "pageForm:conversationListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
