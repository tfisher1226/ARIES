package org.aries.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.aries.common.entity.EmailAccountEntity;
import org.aries.common.entity.EmailBoxEntity;
import org.aries.common.entity.EmailMessageEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "EmailBox")
@Table(name = "email_box")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmailBoxEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "type")
	private String type;

	@Column(name = "name")
	private String name;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private EmailAccountEntity emailAccount;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private EmailBoxEntity parentBox;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<EmailMessageEntity> messageList;

	@Column(name = "creation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column(name = "last_update")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EmailAccountEntity getEmailAccount() {
		return emailAccount;
	}

	public void setEmailAccount(EmailAccountEntity emailAccount) {
		this.emailAccount = emailAccount;
	}

	public EmailBoxEntity getParentBox() {
		return parentBox;
	}

	public void setParentBox(EmailBoxEntity parentBox) {
		this.parentBox = parentBox;
	}

	public List<EmailMessageEntity> getMessageList() {
		if (messageList == null)
			messageList = new ArrayList<EmailMessageEntity>();
		return messageList;
	}

	public void setMessageList(List<EmailMessageEntity> messageList) {
		this.messageList = messageList;
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

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		EmailBoxEntity other = (EmailBoxEntity) object;
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