package template1.service;

import java.io.Serializable;
import java.util.List;

import org.aries.message.Message;
import org.aries.nam.model.TransportType;
import org.aries.registry.ServiceLocator;
import org.aries.runtime.BeanContext;
import org.aries.service.ServiceProxy;

import template1.model.User;
import template1.model.Users;


/**
 * Provides client interface to <code>user</code> service.
 * 
 * @author tfisher
 */
@SuppressWarnings("serial")
public class UserServiceProxy implements UserService, Serializable {

	/**
	 * Gets all <em>User</em>s.
	 * @return The list of <em>User</em>s.
	 */
	@Override
	public List<User> getUsers() {
    	Message request = new Message();
		ServiceLocator serviceLocator = BeanContext.get("org.aries.serviceLocator");
		ServiceProxy serviceProxy = serviceLocator.lookup("/infosgi-service", "0.0.1-SNAPSHOT", "org.sgiusa.getUsers", TransportType.RMI);
    	Message response = serviceProxy.invoke(request);
    	Users users = response.getPart(USERS);
		List<User> result = users.getRecords();
    	return result;
	}
	
	/**
	 * Gets the <em>User</em> associated with <em>id</em>.
	 * @return The <em>User</em> for <em>id</em>.
	 */
	@Override
	public User getUserById(Long id) {
    	Message request = new Message();
    	request.addPart(USER_ID, id);
		ServiceLocator serviceLocator = BeanContext.get("org.aries.serviceLocator");
		ServiceProxy serviceProxy = serviceLocator.lookup("/infosgi-service", "0.0.1-SNAPSHOT", "org.sgiusa.getUser", TransportType.RMI);
    	Message response = serviceProxy.invoke(request);
    	User result = response.getPart(USER);
    	return result;
	}

	/**
	 * Gets the <em>User</em> associated with <em>userName</em>.
	 * @return The <em>User</em> for <em>userName</em>.
	 */
	@Override
	public User getUserByName(String userName) {
    	Message request = new Message();
    	request.addPart(USER_NAME, userName);
		ServiceLocator serviceLocator = BeanContext.get("org.aries.serviceLocator");
		ServiceProxy serviceProxy = serviceLocator.lookup("/infosgi-service", "0.0.1-SNAPSHOT", "org.sgiusa.getUser", TransportType.RMI);
    	Message response = serviceProxy.invoke(request);
    	User result = response.getPart(USER);
    	return result;
	}

	/**
	 * Saves specified <em>User</em> to database.
	 * @param user The record to save in database.
	 * @return The <em>id</em> for <em>User</em>.
	 */
	@Override
	public Long saveUser(User user) {
    	Message request = new Message();
    	request.addPart(USER, user);
		ServiceLocator serviceLocator = BeanContext.get("org.aries.serviceLocator");
		ServiceProxy serviceProxy = serviceLocator.lookup("/infosgi-service", "0.0.1-SNAPSHOT", "org.sgiusa.saveUser", TransportType.RMI);
    	Message response = serviceProxy.invoke(request);
    	Long id = response.getPart(USER_ID);
    	return id;
	}

}
