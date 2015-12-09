package admin.user;

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

import admin.User;
import admin.util.UserUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("userListManager")
public class UserListManager extends AbstractDomainListManager<User, UserListObject> implements Serializable {

	@Inject
	private UserDataManager userDataManager;
	
	@Inject
	private UserEventManager userEventManager;
	
	@Inject
	private UserInfoManager userInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "userList";
	}
	
	@Override
	public String getTitle() {
		return "User List";
	}

	public Object getRecordId(User user) {
		return user.getId();
	}
	
	@Override
	public Object getRecordKey(User user) {
		return UserUtil.getKey(user);
	}

	@Override
	public String getRecordName(User user) {
		return UserUtil.getLabel(user);
	}
	
	@Override
	protected Class<User> getRecordClass() {
		return User.class;
	}
	
	@Override
	protected User getRecord(UserListObject rowObject) {
		return rowObject.getUser();
	}
	
	@Override
	public User getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? UserUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(User user) {
		super.setSelectedRecord(user);
		fireSelectedEvent(user);
	}
	
	protected void fireSelectedEvent(User user) {
		userEventManager.fireSelectedEvent(user);
	}
	
	public boolean isSelected(User user) {
		User selection = selectionContext.getSelection("user");
		boolean selected = selection != null && selection.equals(user);
		return selected;
	}
	
	public boolean isChecked(User user) {
		Collection<User> selection = selectionContext.getSelection("userSelection");
		boolean checked = selection != null && selection.contains(user);
		return checked;
	}
	
	@Override
	protected UserListObject createRowObject(User user) {
		UserListObject listObject = new UserListObject(user);
		listObject.setSelected(isSelected(user));
		listObject.setChecked(isChecked(user));
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
	protected Collection<User> createRecordList() {
		try {
			Collection<User> userList = userDataManager.getUserList();
			if (userList != null)
			return userList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewUser() {
		return viewUser(selectedRecordKey);
	}
	
	public String viewUser(Object recordKey) {
		User user = recordByKeyMap.get(recordKey);
		return viewUser(user);
	}
	
	public String viewUser(User user) {
		String url = userInfoManager.viewUser(user);
		return url;
	}

	public String editUser() {
		return editUser(selectedRecordKey);
	}
	
	public String editUser(Object recordKey) {
		User user = recordByKeyMap.get(recordKey);
		return editUser(user);
	}
	
	public String editUser(User user) {
		String url = userInfoManager.editUser(user);
		return url;
	}

	public void removeUser() {
		removeUser(selectedRecordKey);
	}
	
	public void removeUser(Object recordKey) {
		User user = recordByKeyMap.get(recordKey);
		removeUser(user);
	}
	
	public void removeUser(User user) {
		try {
			if (userDataManager.removeUser(user))
			clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void cancelUser(@Observes @Cancelled User user) {
		try {
			//Object key = UserUtil.getKey(user);
			//recordByKeyMap.put(key, user);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("user");			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateUser(Collection<User> userList) {
		return UserUtil.validate(userList);
	}
	
	public void exportUserList(@Observes @Export String tableId) {
		//String tableId = "pageForm:userListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}

}
