package admin.ui.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;
import org.aries.ui.event.ResetEvent;

import admin.User;
import admin.client.userService.UserService;
import admin.util.UserUtil;


@SessionScoped
@Named("userListManager")
public class UserListManager extends AbstractTabListManager<User, UserListObject> implements Serializable {

	@Override
	public String getModule() {
		return "UserList";
	}
	
	@Override
	public String getTitle() {
		return "User List";
	}
	
	@Override
	public Object getRecordId(User user) {
		return user.getId();
	}

	@Override
	public String getRecordName(User user) {
		return UserUtil.toString(user);
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
	protected UserListObject createRowObject(User user) {
		return new UserListObject(user);
	}
	
	protected UserHelper getUserHelper() {
		return BeanContext.getFromSession("userHelper");
	}
	
	protected UserService getUserService() {
		return BeanContext.getFromSession(UserService.ID);
	}
	
	protected UserInfoManager getUserInfoManager() {
		return BeanContext.getFromSession("userInfoManager");
	}
	
//	//SEAM @Observer("org.aries.ui.reset")
//	public void reset(@Observes ResetEvent event) {
//		reset();
//	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
	}
	
	@Override
	//SEAM @Observer("org.aries.refreshUserList")
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected List<User> createRecordList() {
		try {
			List<User> userList = getUserService().getUserList();
			return userList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void editUser() {
		editUser(selectedRecordId);
	}
	
	public void editUser(String recordId) {
		editUser(Long.parseLong(recordId));
	}
	
	public void editUser(Long recordId) {
		User user = recordByIdMap.get(recordId);
		editUser(user);
	}
	
	public void editUser(User user) {
		UserInfoManager userInfoManager = BeanContext.getFromSession("userInfoManager");
		userInfoManager.editUser(user);
	}
	
	//SEAM @Observer("org.aries.removeUser")
	public void removeUser() {
		removeUser(selectedRecordId);
	}
	
	public void removeUser(String recordId) {
		removeUser(Long.parseLong(recordId));
	}
	
	public void removeUser(Long recordId) {
		User user = recordByIdMap.get(recordId);
		removeUser(user);
	}
	
	public void removeUser(User user) {
		try {
			getUserService().deleteUser(selectedRecord);
			clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
		
	//SEAM @Observer("org.aries.cancelUser")
	public void cancelUser() {
		if (selectedRecord == null)
			return;
		try {
			BeanContext.removeFromSession("user");
			Long id = selectedRecord.getId();
			User user = getUserService().getUserById(id);
			recordByIdMap.put(id, user);
			initialize(recordByIdMap.values());
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateUser(Collection<User> userList) {
		return UserUtil.validate(userList);
	}
	
//	//SEAM @Observer("org.aries.exportUserList")
//	public void exportUserList() {
//		String tableId = "pageForm:userListTable";
//		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
//		exportManager.exportToXLS(tableId);
//	}

}
