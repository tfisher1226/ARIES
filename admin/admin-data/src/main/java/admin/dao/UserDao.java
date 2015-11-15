package admin.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.PersonName;
import org.aries.common.dao.Dao;

import admin.UserCriteria;
import admin.entity.UserEntity;


public interface UserDao<T extends UserEntity> extends Dao<T> {
	
	public T getUserRecordById(Long id);
	
	public T getUserRecordByUserName(String userName);
	
	public T getUserRecordByCriteria(UserCriteria userCriteria);
	
	public List<T> getAllUserRecords();
	
	//public List<T> getUserRecordsByName(PersonName name);
	
	public List<T> getUserRecordsByPersonName(PersonName personName);
	
	public List<T> getUserRecordsByPage(int pageIndex, int pageSize);
	
	public List<T> getUserRecordsByCriteria(UserCriteria userCriteria);
	
	public Long addUserRecord(T userRecord);
	
	public List<Long> addUserRecords(Collection<T> userRecords);
	
	public void saveUserRecord(T userRecord);
	
	public void saveUserRecords(Collection<T> userRecords);
	
	public void removeAllUserRecords();
	
	public void removeUserRecord(T userRecord);
	
	public void removeUserRecords(Collection<T> userRecords);
	
	public void removeUserRecords(UserCriteria userCriteria);
	
}
