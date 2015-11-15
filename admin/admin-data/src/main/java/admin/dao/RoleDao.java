package admin.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.dao.Dao;

import admin.RoleType;
import admin.entity.RoleEntity;


public interface RoleDao<T extends RoleEntity> extends Dao<T> {
	
	public T getRoleRecordById(Long id);
	
	public T getRoleRecordByName(String name);
	
	public List<T> getAllRoleRecords();
	
	public List<T> getRoleRecordsByRoleType(RoleType roleType);
	
	public List<T> getRoleRecordsByPage(int pageIndex, int pageSize);
	
	public Long addRoleRecord(T roleRecord);
	
	public List<Long> addRoleRecords(Collection<T> roleRecords);
	
	public void saveRoleRecord(T roleRecord);
	
	public void saveRoleRecords(Collection<T> roleRecords);
	
	public void removeAllRoleRecords();
	
	public void removeRoleRecord(T roleRecord);
	
	public void removeRoleRecords(Collection<T> roleRecords);
	
}
