package admin.bean;

import java.util.List;

import admin.Role;
import admin.RoleType;


public interface RoleManager {
	
	public void initialize();
	
	public void clearContext();
	
	public List<Role> getAllRoleRecords();
	
	public Role getRoleRecordById(Long id);
	
	public Role getRoleRecordByName(String name);
	
	public List<Role> getRoleRecordsByRoleType(RoleType roleType);
	
	public List<Role> getRoleRecordsByPage(int pageIndex, int pageSize);
	
	public Long addRoleRecord(Role role);
	
	public void saveRoleRecord(Role role);
	
	public void removeAllRoleRecords();
	
	public void removeRoleRecord(Role role);
	
	public void removeRoleRecord(Long id);
	
	public void importRoleRecords();
	
}
