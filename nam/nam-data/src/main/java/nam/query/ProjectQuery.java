package nam.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import nam.entity.ProjectEntity;

import org.aries.common.query.AbstractQuery;


public class ProjectQuery<T extends ProjectEntity> extends AbstractQuery<T> {

	public ProjectQuery(EntityManager entityManager) {
		super(entityManager);
	}

	public void integrateCriteria(ProjectCriteria projectCriteria) {
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
