package org.aries.common.dao;

import java.util.List;

import org.aries.common.EmailAddress;
import org.aries.common.entity.AbstractUserEntity;


public interface AbstractUserDao<T extends AbstractUserEntity> extends Dao<T> {

	public List<T> getAllAbstractUsers();

	public List<T> getAbstractUsersByPage(int pageIndex, int pageSize);

	public T getAbstractUserById(Long id);

	public T getAbstractUserByUserId(String userId);

	public T getAbstractUserByEmailAddressId(EmailAddress emailAddress);

	public Long saveAbstractUser(T abstractUser);

	public void deleteAbstractUser(T abstractUser);

}
