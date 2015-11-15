package admin.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.aries.common.query.AbstractQuery;

import admin.PermissionCriteria;
import admin.entity.PermissionEntity;


public class PermissionQuery<T extends PermissionEntity> extends AbstractQuery<T> {

	public PermissionQuery(EntityManager entityManager) {
		super(entityManager);
	}


	public void integrateCriteria(PermissionCriteria permissionCriteria) {
		query = createCriteriaQuery(permissionCriteria);
	}

	public List<T> getResultList() {
		return executeCriteriaQuery(query);
	}

	protected CriteriaQuery<T> createCriteriaQuery(PermissionCriteria permissionCriteria) {
		CriteriaQuery<PermissionEntity> criteriaQuery = criteriaBuilder.createQuery(PermissionEntity.class);
		criteriaQuery.distinct(false);
		root = criteriaQuery.from(entityClass);
		@SuppressWarnings("unchecked") CriteriaQuery<T> select = (CriteriaQuery<T>) criteriaQuery.select(root);
		List<Order> orderByList = new ArrayList<Order>();
		orderByList.add(criteriaBuilder.asc(root.get("timestamp")));
		select = select.orderBy(orderByList);
		Predicate predicate = buildPredicates(permissionCriteria);
		select.where(predicate);
		return select;
	}

	protected Predicate buildPredicates(PermissionCriteria permissionCriteria) {
		Predicate predicate = criteriaBuilder.conjunction();
		String roleName = permissionCriteria.getRole();
		if (roleName != null)
			integrateRoleNamePredicate(predicate, roleName);
		return predicate;
	}

	protected void integrateRoleNamePredicate(Predicate predicate, String roleName) {
		Path<Object> path = root.get("roleName");
		Predicate roleNamePredicate = path.in(roleName);
		predicate.getExpressions().add(roleNamePredicate);
	}

	protected List<T> executeCriteriaQuery(CriteriaQuery<T> criteriaQuery) {
		TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
		List<T> results = new ArrayList<T>();
		results.addAll(typedQuery.getResultList());
		return results;
	}


}
