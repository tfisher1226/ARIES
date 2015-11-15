package org.aries.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "Property")
@Table(name = "property")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PropertyEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "value")
	private String value;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		PropertyEntity other = (PropertyEntity) object;
		if (this.getId() == null || other.getId() == null)
			return this == other;
		if (this.getId().equals(other.getId()))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		if (getId() != null)
			return getId().hashCode();
		return 0;
	}

}