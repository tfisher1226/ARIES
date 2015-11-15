package admin.client.userService;

import java.util.List;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import admin.User;


@SuppressWarnings("serial")
public class UserServiceInterceptor extends MessageInterceptor<UserService> implements UserService {
	
	@Override
	public List<User> getUserList() {
		try {
			log.info("#### [admin]: getUserList() sending...");
			Message request = createMessage("getUserList");
			Message response = getProxy().invoke(request);
			List<User> userList = response.getPart("userList");
			return userList;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}

	@Override
	public User getUserById(Long id) {
		try {
			log.info("#### [admin]: getUserById() sending...");
			Message request = createMessage("getUserById");
			request.addPart("id", id);
			Message response = getProxy().invoke(request);
			User user = response.getPart("user");
			return user;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public User getUserByUserName(String userName) {
		try {
			log.info("#### [admin]: getUserByUserName() sending...");
			Message request = createMessage("getUserByUserName");
			request.addPart("userName", userName);
			Message response = getProxy().invoke(request);
			User user = response.getPart("user");
			return user;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addUser(User user) {
		try {
			log.info("#### [admin]: addUser() sending...");
			Message request = createMessage("addUser");
			request.addPart("user", user);
			Message response = getProxy().invoke(request);
			Long id = response.getPart("id");
			return id;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveUser(User user) {
		try {
			log.info("#### [admin]: saveUser() sending...");
			Message request = createMessage("saveUser");
			request.addPart("user", user);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void deleteUser(User user) {
		try {
			log.info("#### [admin]: deleteUser() sending...");
			Message request = createMessage("deleteUser");
			request.addPart("user", user);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
