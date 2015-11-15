package org.aries.common.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.entity.AttachmentEntity;


public interface AttachmentDao<T extends AttachmentEntity> extends Dao<T> {
	
	public T getAttachmentRecordById(Long id);
	
	public List<T> getAllAttachmentRecords();
	
	public List<T> getAttachmentRecordsByPage(int pageIndex, int pageSize);
	
	public Long addAttachmentRecord(T attachmentRecord);
	
	public List<Long> addAttachmentRecords(Collection<T> attachmentRecords);
	
	public void saveAttachmentRecord(T attachmentRecord);
	
	public void saveAttachmentRecords(Collection<T> attachmentRecords);
	
	public void removeAllAttachmentRecords();
	
	public void removeAttachmentRecord(T attachmentRecord);
	
	public void removeAttachmentRecords(Collection<T> attachmentRecords);
	
}
