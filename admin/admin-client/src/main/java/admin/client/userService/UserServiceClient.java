package admin.client.userService;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import admin.User;


public class UserServiceClient extends AbstractDelegate implements UserService {
	
	private static final Log log = LogFactory.getLog(UserServiceClient.class);
	

	@Override
	public String getDomain() {
		return "admin";
	}
	
	@Override
	public String getServiceId() {
		return UserService.ID;
	}
	
	@SuppressWarnings("unchecked")
	public UserService getProxy() throws Exception {
		return getProxy(UserService.ID);
	}
	
	@Override
	public List<User> getUserList() {
		try {
			List<User> userList = getProxy().getUserList();
			return userList;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}

	@Override
	public User getUserById(Long id) {
		try {
			User user = getProxy().getUserById(id);
			return user;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public User getUserByUserName(String userName) {
		try {
			User user = getProxy().getUserByUserName(userName);
			return user;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addUser(User user) {
		try {
			Long id = getProxy().addUser(user);
			return id;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveUser(User user) {
		try {
			getProxy().saveUser(user);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void deleteUser(User user) {
		try {
			getProxy().deleteUser(user);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
