package admin.incoming.userService;

import java.util.List;

import org.aries.tx.Transactional;

import admin.User;


public interface UserServiceHandler extends Transactional {
	
	public List<User> getUserList();
	
	public User getUserById(Long id);
	
	public User getUserByUserName(String userName);
	
	public Long addUser(User user);
	
	public void saveUser(User user);
	
	public void deleteUser(User user);
	
}
