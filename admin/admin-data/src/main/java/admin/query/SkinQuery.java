package admin.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.aries.common.query.AbstractQuery;

import admin.SkinCriteria;
import admin.entity.SkinEntity;


public class SkinQuery<T extends SkinEntity> extends AbstractQuery<T> {

	public SkinQuery(EntityManager entityManager) {
		super(entityManager);
	}

	public void integrateCriteria(SkinCriteria skinCriteria) {
	}

	public List<T> getResultList() {
		return executeCriteriaQuery(query);
	}

	protected List<T> executeCriteriaQuery(CriteriaQuery<T> criteriaQuery) {
		TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
		List<T> results = new ArrayList<T>();
		results.addAll(typedQuery.getResultList());
		return results;
	}
	
}
