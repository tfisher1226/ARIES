package org.sgiusa.model.entity;

import java.lang.String;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;
import org.sgiusa.model.Division;
import org.sgiusa.model.SubDivision;
import org.sgiusa.model.entity.OrganizationEntity;

/**
 * Generated by Nam.
 *
 */
@Entity(name = "Event")
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EventEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "divisions")
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Division.class)
	@CollectionTable(name = "event_division")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<Division> divisions;

	@Column(name = "sub_divisions")
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = SubDivision.class)
	@CollectionTable(name = "event_sub_division")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<SubDivision> subDivisions;

	@JoinColumn(name = "organization", referencedColumnName = "id")
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey(name = "event_entity_organization_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private OrganizationEntity organization;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Division> getDivisions() {
		return divisions;
	}

	public void setDivisions(List<Division> divisions) {
		this.divisions = divisions;
	}

	public List<SubDivision> getSubDivisions() {
		return subDivisions;
	}

	public void setSubDivisions(List<SubDivision> subDivisions) {
		this.subDivisions = subDivisions;
	}

	public OrganizationEntity getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationEntity organization) {
		this.organization = organization;
	}

}