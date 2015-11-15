package admin.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.dao.Dao;

import admin.entity.PermissionEntity;


public interface PermissionDao<T extends PermissionEntity> extends Dao<T> {
	
	public T getPermissionRecordById(Long id);
	
	public List<T> getAllPermissionRecords();
	
	public List<T> getPermissionRecordsByPage(int pageIndex, int pageSize);
	
	public Long addPermissionRecord(T permissionRecord);
	
	public List<Long> addPermissionRecords(Collection<T> permissionRecords);
	
	public void savePermissionRecord(T permissionRecord);
	
	public void savePermissionRecords(Collection<T> permissionRecords);
	
	public void removeAllPermissionRecords();
	
	public void removePermissionRecord(T permissionRecord);
	
	public void removePermissionRecords(Collection<T> permissionRecords);
	
}
