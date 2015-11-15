package nam.model.messaging;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import nam.model.Import;
import nam.model.Messaging;
import nam.model._import.ImportListManager;
import nam.model._import.ImportListObject;
import nam.model.util.MessagingUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;


@SessionScoped
@Named("messagingHelper")
public class MessagingHelper extends AbstractElementHelper<Messaging> implements Serializable {
	
	@Override
	public boolean isEmpty(Messaging messaging) {
		return MessagingUtil.isEmpty(messaging);
	}
	
	@Override
	public String toString(Messaging messaging) {
		return MessagingUtil.toString(messaging);
	}
	
	@Override
	public String toString(Collection<Messaging> messagingList) {
		return MessagingUtil.toString(messagingList);
	}
	
	@Override
	public boolean validate(Messaging messaging) {
		return MessagingUtil.validate(messaging);
	}
	
	@Override
	public boolean validate(Collection<Messaging> messagingList) {
		return MessagingUtil.validate(messagingList);
	}
	
	public DataModel<ImportListObject> getImports(Messaging messaging) {
		if (messaging == null)
			return null;
		return getImports(messaging.getImports());
	}
	
	public DataModel<ImportListObject> getImports(Collection<Import> importsList) {
		ImportListManager importListManager = BeanContext.getFromSession("importListManager");
		DataModel<ImportListObject> dataModel = importListManager.getDataModel(importsList);
		return dataModel;
	}
	
	public DataModel<Serializable> getMembers(Messaging messaging) {
		if (messaging == null)
			return null;
		return getMembers(messaging.getMembers());
	}
	
	public DataModel<Serializable> getMembers(Collection<Serializable> membersList) {
		//SerializableListManager serializableListManager = BeanContext.getFromSession("serializableListManager");
		DataModel<Serializable> dataModel = null; //serializableListManager.getDataModel(membersList);
		return dataModel;
	}
	
}
