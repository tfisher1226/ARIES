package admin.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.aries.common.query.AbstractQuery;

import admin.PreferencesCriteria;
import admin.entity.PreferencesEntity;


public class PreferencesQuery<T extends PreferencesEntity> extends AbstractQuery<T> {

	public PreferencesQuery(EntityManager entityManager) {
		super(entityManager);
	}

	public void integrateCriteria(PreferencesCriteria skinCriteria) {
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
