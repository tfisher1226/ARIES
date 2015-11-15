package org.aries.common.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.aries.common.entity.UserEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "Note")
@Table(name = "note")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NoteEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "creation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column(name = "text")
	private String text;

	@Column(name = "last_update")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private UserEntity author;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public UserEntity getAuthor() {
		return author;
	}

	public void setAuthor(UserEntity author) {
		this.author = author;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		NoteEntity other = (NoteEntity) object;
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