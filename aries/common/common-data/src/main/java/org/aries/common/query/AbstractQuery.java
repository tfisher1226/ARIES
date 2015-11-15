package org.aries.common.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


public abstract class AbstractQuery<T> {

	protected EntityManager entityManager;
	
	protected CriteriaBuilder criteriaBuilder;
	
	protected CriteriaQuery<T> query;
	
	protected Class<T> entityClass;
	
	protected Root<T> root;
	
	public AbstractQuery(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.criteriaBuilder = entityManager.getCriteriaBuilder();
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public void executeUpdate() {
		TypedQuery<T> typedQuery = entityManager.createQuery(query);
		typedQuery.executeUpdate();
	}

	public T getSingleResult() {
		TypedQuery<T> typedQuery = entityManager.createQuery(query);
		T result = typedQuery.getSingleResult();
		return result;
	}

	public List<T> getResultList() {
		TypedQuery<T> typedQuery = entityManager.createQuery(query);
		List<T> results = new ArrayList<T>();
		results.addAll(typedQuery.getResultList());
		return results;
	}
	
}
