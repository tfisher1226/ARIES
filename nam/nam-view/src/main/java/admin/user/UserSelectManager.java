package admin.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import admin.User;
import admin.client.userService.UserService;
import admin.util.UserUtil;


@SessionScoped
@Named("userSelectManager")
public class UserSelectManager extends AbstractSelectManager<User, UserListObject> implements Serializable {

	@Inject
	private UserDataManager userDataManager;
	
	@Inject
	private UserHelper userHelper;
	
	
	@Override
	public String getClientId() {
		return "userSelect";
	}

	@Override
	public String getTitle() {
		return "User Selection";
	}

	@Override
	protected Class<User> getRecordClass() {
		return User.class;
	}

	@Override
	public boolean isEmpty(User user) {
		return userHelper.isEmpty(user);
	}

	@Override
	public String toString(User user) {
		return userHelper.toString(user);
	}

	protected UserHelper getUserHelper() {
		return BeanContext.getFromSession("userHelper");
	}

	protected UserService getUserService() {
		return BeanContext.getFromSession(UserService.ID);
	}

	protected UserListManager getUserListManager() {
		return BeanContext.getFromSession("userListManager");
	}

	@Override
	public void initialize() {
		initializeContext();
		refreshUserList();
		populate(selectedRecords);
	}

	@Override
	public void populateItems(Collection<User> recordList) {
		UserListManager userListManager = getUserListManager();
		DataModel<UserListObject> dataModel = userListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}

	public void refreshUserList() {
		masterList = refreshRecords();
	}

	@Override
	protected Collection<User> refreshRecords() {
		try {
			Collection<User> records = userDataManager.getUserList();
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
	public void sortRecords(List<User> userList) {
		sortRecordsByUserName(userList);
	}

	public void sortRecordsByPersonName(List<User> userList) {
		UserUtil.sortRecordsByPersonName(userList);
	}

	public void sortRecordsByUserName(List<User> userList) {
		UserUtil.sortRecordsByUserName(userList);
	}

}
