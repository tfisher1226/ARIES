package admin.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.dao.Dao;

import admin.User;
import admin.entity.RegistrationEntity;


public interface RegistrationDao<T extends RegistrationEntity> extends Dao<T> {
	
	public T getRegistrationRecordById(Long id);
	
	public T getRegistrationRecordByUser(User user);
	
	public List<T> getAllRegistrationRecords();
	
	public List<T> getRegistrationRecordsByPage(int pageIndex, int pageSize);
	
	public Long addRegistrationRecord(T registrationRecord);
	
	public List<Long> addRegistrationRecords(Collection<T> registrationRecords);
	
	public void saveRegistrationRecord(T registrationRecord);
	
	public void saveRegistrationRecords(Collection<T> registrationRecords);
	
	public void removeAllRegistrationRecords();
	
	public void removeRegistrationRecord(T registrationRecord);
	
	public void removeRegistrationRecords(Collection<T> registrationRecords);
	
}
