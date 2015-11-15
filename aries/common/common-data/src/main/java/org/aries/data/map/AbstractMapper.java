package org.aries.data.map;

import java.util.Date;

import org.aries.util.ExceptionUtil;


public abstract class AbstractMapper<M, E> {

	protected Class<?> modelClass;

	protected Class<?> entityClass;

	
	public void setModelClass(Class<?> modelClass) {
		this.modelClass = modelClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	protected M createModel() {
		try {
			@SuppressWarnings("unchecked") M model = (M) modelClass.newInstance();
			return model;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}

	protected E createEntity() {
		try {
			@SuppressWarnings("unchecked") E entity = (E) entityClass.newInstance();
			return entity;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}

	protected Date createDate(Date date) {
		if (date == null)
			return null;
		Date newDate = new Date(date.getTime());
		return newDate;
	}

}
