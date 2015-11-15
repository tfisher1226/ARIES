package admin.incoming.roleService;

import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import admin.Role;
import admin.RoleType;


@WebService(name = "roleService", targetNamespace = "http://admin")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface RoleService {
	
	public String ID = "admin.roleService";
	
	public List<Role> getRoleList();
	
	public Role getRoleById(Long id);
	
	public Role getRoleByName(String name);
	
	public List<Role> getRoleListByRoleType(RoleType roleType);
	
	public Long addRole(Role role);
	
	public void saveRole(Role role);
	
	public void deleteRole(Role role);
	
}
