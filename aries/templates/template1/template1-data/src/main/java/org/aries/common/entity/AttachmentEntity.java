package org.aries.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "Attachment")
@Table(name = "attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AttachmentEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "size")
	private Long size;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "content_type")
	private String contentType;


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

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		AttachmentEntity other = (AttachmentEntity) object;
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