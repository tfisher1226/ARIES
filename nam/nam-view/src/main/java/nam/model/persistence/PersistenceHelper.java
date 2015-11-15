package nam.model.persistence;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import nam.model.Persistence;
import nam.model.util.PersistenceUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;


@SessionScoped
@Named("persistenceHelper")
public class PersistenceHelper extends AbstractElementHelper<Persistence> implements Serializable {
	
	@Override
	public boolean isEmpty(Persistence persistence) {
		return PersistenceUtil.isEmpty(persistence);
	}
	
	@Override
	public String toString(Persistence persistence) {
		return PersistenceUtil.toString(persistence);
	}
	
	@Override
	public String toString(Collection<Persistence> persistenceList) {
		return PersistenceUtil.toString(persistenceList);
	}
	
	@Override
	public boolean validate(Persistence persistence) {
		return PersistenceUtil.validate(persistence);
	}
	
	@Override
	public boolean validate(Collection<Persistence> persistenceList) {
		return PersistenceUtil.validate(persistenceList);
	}
	
	public DataModel<Serializable> getMembers(Persistence persistence) {
		if (persistence == null)
			return null;
		return getMembers(persistence.getMembers());
	}
	
	public DataModel<Serializable> getMembers(Collection<Serializable> membersList) {
		//TODO SerializableListManager serializableListManager = BeanContext.getFromSession("serializableListManager");
		DataModel<Serializable> dataModel = null; //serializableListManager.getDataModel(membersList);
		return dataModel;
	}
	
}
