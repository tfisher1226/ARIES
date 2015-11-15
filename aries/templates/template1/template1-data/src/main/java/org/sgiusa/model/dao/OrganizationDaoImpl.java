package org.sgiusa.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.aries.Assert;
import org.aries.common.dao.DaoImpl;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.sgiusa.model.entity.OrganizationEntity;


@AutoCreate
@Scope(ScopeType.STATELESS)
@Name("org.sgiusa.organizationDao")
public class OrganizationDaoImpl extends DaoImpl implements OrganizationDao {

	@In(required = true, value="org.sgiusa.entityManager")
	protected EntityManager entityManager; 
	
	
	@Override
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Query createQuery(String sql) {
		return getEntityManager().createQuery(sql);
	}

	@Override
	public OrganizationEntity getOrganization() {
		List<OrganizationEntity> result = getOrganizations();
		if (result.size() > 0)
			return result.get(0);
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked") 
	public List<OrganizationEntity> getOrganizations() {
		Query query = getEntityManager().createNamedQuery("getOrganizations");
		List<OrganizationEntity> result = query.getResultList();
		return result;
	}

	@Override
	public OrganizationEntity getOrganization(Long id) {
		Query query = getEntityManager().createNamedQuery("getOrganizationById");
		query.setParameter("id", id);
		Object object = query.getSingleResult();
		OrganizationEntity result = (OrganizationEntity) object;
		return result;
	}

	@Override
	public OrganizationEntity getOrganization(String organizationId) {
		Query query = getEntityManager().createNamedQuery("getOrganizationByCode");
		query.setParameter("organizationId", organizationId);
		Object object = query.getSingleResult();
		OrganizationEntity result = (OrganizationEntity) object;
		return result;
	}

	@Override
	public Long saveOrganization(OrganizationEntity entity) {
		entity = getEntityManager().merge(entity);
		getEntityManager().persist(entity);
		Long id = entity.getId();
		return id;
	}

//	@Override
//	public void deleteOrganization(OrganizationEntity entity) {
//		OrganizationEntity parent = entity.getParent();
//		Assert.notNull(parent, "Parent organization must exist");
//		parent = getOrganization(parent.getId());
//		parent.getChildren().remove(entity);
//		getEntityManager().persist(parent);
//		entity = getEntityManager().merge(entity);
//		getEntityManager().remove(entity);
//	}

//	public void saveUser() {
//		UserEntity userEntity = new UserEntity();
//		userEntity.setFirstName("tom");
//		userEntity.setLastName("fisher");
//		userEntity.setEnabled(true);
//		userEntity.setLoginId("tfisher");
//		userEntity.setPassword("xxx");
//		persist(userEntity);
//	}

//	public void saveMember() {
//		MemberEntity memberEntity = new MemberEntity();
//		memberEntity.setFirstName("tom");
//		memberEntity.setLastName("fisher");
//		persist(memberEntity);
//	}

}
