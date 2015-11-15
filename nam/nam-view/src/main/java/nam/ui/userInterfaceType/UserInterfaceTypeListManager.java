package nam.ui.userInterfaceType;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.UserInterfaceType;
import nam.ui.design.SelectionContext;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Refresh;


@SessionScoped
@Named("userInterfaceTypeListManager")
public class UserInterfaceTypeListManager extends AbstractDomainListManager<UserInterfaceType, UserInterfaceTypeListObject> implements Serializable {
	
	@Inject
	private UserInterfaceTypeDataManager userInterfaceTypeDataManager;
	
//	@Inject
//	private UserInterfaceTypeEventManager userInterfaceTypeEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "userInterfaceTypeList";
	}
	
	@Override
	public String getTitle() {
		return "UserInterfaceType List";
	}
	
//	@Override
//	public Object getRecordId(UserInterfaceType userInterfaceType) {
//		return userInterfaceType.ordinal();
//	}
	
//	@Override
//	public Object getRecordKey(UserInterfaceType userInterfaceType) {
//		return UserInterfaceTypeUtil.getKey(userInterfaceType);
//	}
	
	@Override
	public String getRecordName(UserInterfaceType userInterfaceType) {
		return userInterfaceType.name();
	}
	
	@Override
	protected Class<UserInterfaceType> getRecordClass() {
		return UserInterfaceType.class;
	}
	
	@Override
	protected UserInterfaceType getRecord(UserInterfaceTypeListObject rowObject) {
		return rowObject.getUserInterfaceType();
	}
	
	@Override
	public UserInterfaceType getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? selectedRecord.name() : null;
	}
	
	@Override
	public void setSelectedRecord(UserInterfaceType userInterfaceType) {
		super.setSelectedRecord(userInterfaceType);
//		fireSelectedEvent(userInterfaceType);
	}
	
//	protected void fireSelectedEvent(UserInterfaceType userInterfaceType) {
//		userInterfaceTypeEventManager.fireSelectedEvent(userInterfaceType);
//	}
	
	@Override
	protected UserInterfaceTypeListObject createRowObject(UserInterfaceType userInterfaceType) {
		return new UserInterfaceTypeListObject(userInterfaceType);
	}
	
	protected UserInterfaceTypeHelper getUserInterfaceTypeHelper() {
		return BeanContext.getFromSession("userInterfaceTypeHelper");
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
	}
	
	public void handleRefresh(@Observes @Refresh Object object) {
		//refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<UserInterfaceType> createRecordList() {
		try {
			Collection<UserInterfaceType> userInterfaceTypeList = Arrays.asList(UserInterfaceType.values());
			return userInterfaceTypeList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
}
