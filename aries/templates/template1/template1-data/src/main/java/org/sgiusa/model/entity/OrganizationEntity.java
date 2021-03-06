package org.sgiusa.model.entity;

import java.lang.String;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;
import org.sgiusa.model.OrganizationLevel;
import org.sgiusa.model.entity.OrganizationEntity;
import org.sgiusa.model.entity.UserEntity;

/**
 * Generated by Nam.
 *
 */
@Entity(name = "Organization")
@Table(name = "organization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrganizationEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "organization_id")
	private String organizationId;

	@Column(name = "type")
	private String type;

	@Column(name = "level")
	private OrganizationLevel level;

	@Column(name = "name")
	private String name;

	@Column(name = "abbrv")
	private String abbrv;

	@Column(name = "zip_codes")
	@ElementCollection(targetClass = String.class)
	@CollectionTable(name = "organization_zip_codes")
	private List<String> zipCodes;

	@Column(name = "creation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column(name = "last_update")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

	@JoinColumn(name = "parent", referencedColumnName = "id")
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey(name = "organization_entity_parent_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private OrganizationEntity parent;

	@JoinColumn(name = "id")
	@OneToMany(cascade = CascadeType.ALL, targetEntity = OrganizationEntity.class)
	@ForeignKey(name = "organization_entity_children_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<OrganizationEntity> children;

	@JoinColumn(name = "creator", referencedColumnName = "id")
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey(name = "organization_entity_creator_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private UserEntity creator;

	@JoinColumn(name = "provider", referencedColumnName = "id")
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey(name = "organization_entity_provider_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private UserEntity provider;

	@JoinColumn(name = "id")
	@OneToMany(cascade = CascadeType.ALL, targetEntity = UserEntity.class)
	@ForeignKey(name = "organization_entity_accessors_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<UserEntity> accessors;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public OrganizationLevel getLevel() {
		return level;
	}

	public void setLevel(OrganizationLevel level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbrv() {
		return abbrv;
	}

	public void setAbbrv(String abbrv) {
		this.abbrv = abbrv;
	}

	public List<String> getZipCodes() {
		return zipCodes;
	}

	public void setZipCodes(List<String> zipCodes) {
		this.zipCodes = zipCodes;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public OrganizationEntity getParent() {
		return parent;
	}

	public void setParent(OrganizationEntity parent) {
		this.parent = parent;
	}

	public List<OrganizationEntity> getChildren() {
		return children;
	}

	public void setChildren(List<OrganizationEntity> children) {
		this.children = children;
	}

	public UserEntity getCreator() {
		return creator;
	}

	public void setCreator(UserEntity creator) {
		this.creator = creator;
	}

	public UserEntity getProvider() {
		return provider;
	}

	public void setProvider(UserEntity provider) {
		this.provider = provider;
	}

	public List<UserEntity> getAccessors() {
		return accessors;
	}

	public void setAccessors(List<UserEntity> accessors) {
		this.accessors = accessors;
	}

}