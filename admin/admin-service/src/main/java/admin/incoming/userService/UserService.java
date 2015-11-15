package admin.incoming.userService;

import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import admin.User;


@WebService(name = "userService", targetNamespace = "http://admin")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface UserService {
	
	public String ID = "admin.userService";
	
	public List<User> getUserList();
	
	public User getUserById(Long id);
	
	public User getUserByUserName(String userName);
	
	public Long addUser(User user);
	
	public void saveUser(User user);
	
	public void deleteUser(User user);
	
}
