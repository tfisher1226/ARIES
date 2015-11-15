package org.aries.common.dao;

import java.util.List;

import org.aries.common.entity.AbstractNoteEntity;


public interface AbstractNoteDao<T extends AbstractNoteEntity> extends Dao<T> {

	public List<T> getAllAbstractNotes();

	public List<T> getAbstractNotesByAuthorId(Long abstractUserId);

	public List<T> getAbstractNotesByPage(int pageIndex, int pageSize);

	public T getAbstractNoteById(Long id);

	public Long saveAbstractNote(T abstractNote);

	public void deleteAbstractNote(T abstractNote);

}
