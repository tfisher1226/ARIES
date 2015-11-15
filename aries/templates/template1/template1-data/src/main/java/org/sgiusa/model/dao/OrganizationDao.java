package org.sgiusa.model.dao;

import java.util.List;

import javax.persistence.Query;

import org.aries.common.dao.Dao;
import org.sgiusa.model.entity.OrganizationEntity;


public interface OrganizationDao extends Dao {

	Query createQuery(String sql);

	public OrganizationEntity getOrganization();

	public List<OrganizationEntity> getOrganizations();

	public OrganizationEntity getOrganization(Long id);

	public OrganizationEntity getOrganization(String organizationId);

	public Long saveOrganization(OrganizationEntity data);

//	public void deleteOrganization(OrganizationEntity entity);

}
