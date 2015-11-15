package template1.service;

import java.util.List;

import template1.model.User;


/**
 * Provides public interface into the <code>sample1</code> service module.
 * 
 * @author Administrator
 */
public interface UserService {

	/** The identifying (the context) portion of the service URL. */
	public String CONTEXT = "/infosgi-service";

	/* 
	 * Below is the list of operations that are provided by this service.
	 * The name of each operation includes two portions, 1) the service ID,
	 * and 2) the operation ID.
	 */

	/* 
	 * The operations provided by this service: 
	 */

	public String GET_USER = "org.sgiusa.getUser";
	public String SAVE_USER = "org.sgiusa.saveUser";

	/*
	 * Below is the list of data items transferred to/from this service.
	 * The list below represents the data model exported by this service.
	 * The data model is basically the list of items below, the set of 
	 * potential data item types.
	 */
	public final String USER = "user";
	public final String USERS = "users";
	public final String USER_ID = "userId";
	public final String USER_NAME = "userName";
	public final String PASS_CODE = "passCode";


	/**
	 * Gets all <em>User</em>s.
	 * @return The list of <em>User</em>s.
	 */
	public List<User> getUsers();

	/**
	 * Gets the <em>User</em> associated with specified <em>id</em>.
	 * @return The <em>User</em> for <em>id</em>.
	 */
	public User getUserById(Long id);

	/**
	 * Gets the <em>User</em> associated with specified <em>userName</em>.
	 * @return The <em>User</em> for <em>userName</em>.
	 */
	public User getUserByName(String userName);

	/**
	 * Saves specified <em>User</em> to database.
	 * @param user The record to save in database.
	 * @return The <em>id</em> for <em>User</em>.
	 */
	public Long saveUser(User user);

}
