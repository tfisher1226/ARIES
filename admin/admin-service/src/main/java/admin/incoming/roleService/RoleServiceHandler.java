package admin.incoming.roleService;

import java.util.List;

import org.aries.tx.Transactional;

import admin.Role;
import admin.RoleType;


public interface RoleServiceHandler extends Transactional {
	
	public List<Role> getRoleList();
	
	public Role getRoleById(Long id);
	
	public Role getRoleByName(String name);
	
	public List<Role> getRoleListByRoleType(RoleType roleType);
	
	public Long addRole(Role role);
	
	public void saveRole(Role role);
	
	public void deleteRole(Role role);
	
}
