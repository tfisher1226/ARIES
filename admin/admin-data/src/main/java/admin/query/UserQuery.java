package admin.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.aries.common.EmailAddress;
import org.aries.common.PersonName;
import org.aries.common.StreetAddress;
import org.aries.common.query.AbstractQuery;

import admin.Role;
import admin.UserCriteria;
import admin.entity.UserEntity;


public class UserQuery<T extends UserEntity> extends AbstractQuery<T> {

	public UserQuery(EntityManager entityManager) {
		super(entityManager);
	}

	
	public void integrateCriteria(UserCriteria userCriteria) {
		query = createCriteriaQuery(userCriteria);
	}

	public List<T> getResultList() {
		return executeCriteriaQuery(query);
	}
	
	protected CriteriaQuery<T> createCriteriaQuery(UserCriteria userCriteria) {
		CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
		criteriaQuery.distinct(false);
		root = criteriaQuery.from(entityClass);
		@SuppressWarnings("unchecked") CriteriaQuery<T> select = (CriteriaQuery<T>) criteriaQuery.select(root);
		List<Order> orderByList = new ArrayList<Order>();
		select = select.orderBy(orderByList);
		Predicate predicate = buildPredicates(userCriteria);
		select.where(predicate);
		return select;
	}
	
	protected Predicate buildPredicates(UserCriteria userCriteria) {
		Predicate predicate = criteriaBuilder.conjunction();
		integrateEnabledPredicate(predicate, userCriteria.getEnabled());
		integrateUserNamePredicate(predicate, userCriteria.getUserName());
		integratePersonNamePredicate(predicate, userCriteria.getPersonName());
		integrateEmailAddressPredicate(predicate, userCriteria.getEmailAddress());
		integrateStreetAddressPredicate(predicate, userCriteria.getStreetAddress());
		integrateRolesPredicate(predicate, userCriteria.getRoles());
		return predicate;
	}
	
	protected void integrateEnabledPredicate(Predicate predicate, Boolean enabled) {
		Path<Object> path = root.get("enabled");
		Predicate enabledPredicate = path.in(enabled);
		predicate.getExpressions().add(enabledPredicate);
	}
	
	protected void integrateUserNamePredicate(Predicate predicate, String userName) {
		Path<Object> path = root.get("userName");
		Predicate userNamePredicate = path.in(userName);
		predicate.getExpressions().add(userNamePredicate);
	}
	
	protected void integratePersonNamePredicate(Predicate predicate, PersonName personName) {
		Path<Object> path = root.get("personName");
		Predicate personNamePredicate = path.in(personName);
		predicate.getExpressions().add(personNamePredicate);
	}
	
	protected void integrateEmailAddressPredicate(Predicate predicate, EmailAddress emailAddress) {
		Path<Object> path = root.get("emailAddress");
		Predicate emailAddressPredicate = path.in(emailAddress);
		predicate.getExpressions().add(emailAddressPredicate);
	}
	
	protected void integrateStreetAddressPredicate(Predicate predicate, StreetAddress streetAddress) {
		Path<Object> path = root.get("streetAddress");
		Predicate streetAddressPredicate = path.in(streetAddress);
		predicate.getExpressions().add(streetAddressPredicate);
	}
	
	protected void integrateRolesPredicate(Predicate predicate, Role roles) {
		Path<Object> path = root.get("roles");
		Predicate rolesPredicate = path.in(roles);
		predicate.getExpressions().add(rolesPredicate);
	}
	
	protected List<T> executeCriteriaQuery(CriteriaQuery<T> criteriaQuery) {
		TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
		List<T> results = new ArrayList<T>();
		results.addAll(typedQuery.getResultList());
		return results;
	}
	
}
