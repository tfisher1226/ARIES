package org.aries.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.aries.common.entity.UserEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "Event")
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EventEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private UserEntity user;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		EventEntity other = (EventEntity) object;
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