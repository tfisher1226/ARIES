package admin.bean;

import java.util.List;

import admin.Permission;


public interface PermissionManager {
	
	public void initialize();
	
	public void clearContext();
	
	public List<Permission> getAllPermissionRecords();
	
	public Permission getPermissionRecordById(Long id);
	
	public List<Permission> getPermissionRecordsByPage(int pageIndex, int pageSize);
	
	public void importPermissionRecords();
	
}
