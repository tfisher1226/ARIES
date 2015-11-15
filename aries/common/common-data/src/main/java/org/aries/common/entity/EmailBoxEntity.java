package org.aries.common.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;


@Entity(name = "EmailBox")
@Table(name = "email_box")
@Cache(usage = READ_WRITE)
public class EmailBoxEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "email_account_id")
	@ForeignKey(name = "email_box_email_account_fk")
	@OnDelete(action = CASCADE)
	@Cache(usage = READ_WRITE)
	private EmailAccountEntity emailAccount;
	
	@OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
	@JoinColumn(name = "parent_box_id")
	@ForeignKey(name = "email_box_parent_box_fk", inverseName = "email_box_parent_box_inverse_fk")
	@Cache(usage = READ_WRITE)
	private EmailBoxEntity parentBox;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "email_box_message", joinColumns = @JoinColumn(name = "email_box_id"), inverseJoinColumns = @JoinColumn(name = "message_id"))
	@ForeignKey(name = "email_box_message_fk", inverseName = "email_box_message_inverse_fk")
	@Cache(usage = READ_WRITE)
	private List<EmailMessageEntity> messages;
	
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
	
	public void setParentBox(EmailBoxEntity emailBox) {
		this.parentBox = emailBox;
	}
	
	public List<EmailMessageEntity> getMessages() {
		if (messages == null)
			messages = new ArrayList<EmailMessageEntity>();
		return messages;
	}
	
	public void setMessages(Collection<EmailMessageEntity> emailMessageList) {
		if (emailMessageList == null) {
			this.messages = null;
		} else {
			this.messages = new ArrayList<EmailMessageEntity>();
			this.messages.addAll(emailMessageList);
		}
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
	public String toString() {
		if (getId() != null)
			return getClass().getSimpleName()+"["+getId()+"]";
		return super.toString();
	}
	
}
