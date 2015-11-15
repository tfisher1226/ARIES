package org.aries.common.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.ForeignKey;


@Entity(name = "EmailMessage")
@Table(name = "email_message")
@Cache(usage = READ_WRITE)
public class EmailMessageEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "subject")
	private String subject;
	
	@Column(name = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@Column(name = "source_id")
	private String sourceId;
	
	@Column(name = "smtp_host")
	private String smtpHost;
	
	@Column(name = "smtp_port")
	private String smtpPort;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "from_address_id", nullable = false)
	@ForeignKey(name = "email_message_from_address_fk", inverseName = "email_message_from_address_inverse_fk")
	@Cache(usage = READ_WRITE)
	private EmailAddressEntity fromAddress;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "email_message_to_addresse", joinColumns = @JoinColumn(name = "email_message_id"), inverseJoinColumns = @JoinColumn(name = "to_addresse_id"))
	@ForeignKey(name = "email_message_to_addresse_fk", inverseName = "email_message_to_addresse_inverse_fk")
	@Cache(usage = READ_WRITE)
	private List<EmailAddressListEntity> toAddresses;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "email_message_bcc_addresse", joinColumns = @JoinColumn(name = "email_message_id"), inverseJoinColumns = @JoinColumn(name = "bcc_addresse_id"))
	@ForeignKey(name = "email_message_bcc_addresse_fk", inverseName = "email_message_bcc_addresse_inverse_fk")
	@Cache(usage = READ_WRITE)
	private List<EmailAddressListEntity> bccAddresses;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "email_message_cc_addresse", joinColumns = @JoinColumn(name = "email_message_id"), inverseJoinColumns = @JoinColumn(name = "cc_addresse_id"))
	@ForeignKey(name = "email_message_cc_addresse_fk", inverseName = "email_message_cc_addresse_inverse_fk")
	@Cache(usage = READ_WRITE)
	private List<EmailAddressListEntity> ccAddresses;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "email_message_replyto_addresse", joinColumns = @JoinColumn(name = "email_message_id"), inverseJoinColumns = @JoinColumn(name = "replyto_addresse_id"))
	@ForeignKey(name = "email_message_replyto_addresse_fk", inverseName = "email_message_replyto_addresse_inverse_fk")
	@Cache(usage = READ_WRITE)
	private List<EmailAddressListEntity> replytoAddresses;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "email_message_admin_addresse", joinColumns = @JoinColumn(name = "email_message_id"), inverseJoinColumns = @JoinColumn(name = "admin_addresse_id"))
	@ForeignKey(name = "email_message_admin_addresse_fk", inverseName = "email_message_admin_addresse_inverse_fk")
	@Cache(usage = READ_WRITE)
	private List<EmailAddressListEntity> adminAddresses;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "email_message_attachment", joinColumns = @JoinColumn(name = "email_message_id"), inverseJoinColumns = @JoinColumn(name = "attachment_id"))
	@ForeignKey(name = "email_message_attachment_fk", inverseName = "email_message_attachment_inverse_fk")
	@Cache(usage = READ_WRITE)
	private List<AttachmentEntity> attachments;
	
	@Column(name = "send_as_html")
	private Boolean sendAsHtml;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getSourceId() {
		return sourceId;
	}
	
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	public String getSmtpHost() {
		return smtpHost;
	}
	
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	
	public String getSmtpPort() {
		return smtpPort;
	}
	
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}
	
	public EmailAddressEntity getFromAddress() {
		return fromAddress;
	}
	
	public void setFromAddress(EmailAddressEntity emailAddress) {
		this.fromAddress = emailAddress;
	}
	
	public List<EmailAddressListEntity> getToAddresses() {
		if (toAddresses == null)
			toAddresses = new ArrayList<EmailAddressListEntity>();
		return toAddresses;
	}
	
	public void setToAddresses(Collection<EmailAddressListEntity> emailAddressListList) {
		if (emailAddressListList == null) {
			this.toAddresses = null;
		} else {
			this.toAddresses = new ArrayList<EmailAddressListEntity>();
			this.toAddresses.addAll(emailAddressListList);
		}
	}
	
	public List<EmailAddressListEntity> getBccAddresses() {
		if (bccAddresses == null)
			bccAddresses = new ArrayList<EmailAddressListEntity>();
		return bccAddresses;
	}
	
	public void setBccAddresses(Collection<EmailAddressListEntity> emailAddressListList) {
		if (emailAddressListList == null) {
			this.bccAddresses = null;
		} else {
			this.bccAddresses = new ArrayList<EmailAddressListEntity>();
			this.bccAddresses.addAll(emailAddressListList);
		}
	}
	
	public List<EmailAddressListEntity> getCcAddresses() {
		if (ccAddresses == null)
			ccAddresses = new ArrayList<EmailAddressListEntity>();
		return ccAddresses;
	}
	
	public void setCcAddresses(Collection<EmailAddressListEntity> emailAddressListList) {
		if (emailAddressListList == null) {
			this.ccAddresses = null;
		} else {
			this.ccAddresses = new ArrayList<EmailAddressListEntity>();
			this.ccAddresses.addAll(emailAddressListList);
		}
	}
	
	public List<EmailAddressListEntity> getReplytoAddresses() {
		if (replytoAddresses == null)
			replytoAddresses = new ArrayList<EmailAddressListEntity>();
		return replytoAddresses;
	}
	
	public void setReplytoAddresses(Collection<EmailAddressListEntity> emailAddressListList) {
		if (emailAddressListList == null) {
			this.replytoAddresses = null;
		} else {
			this.replytoAddresses = new ArrayList<EmailAddressListEntity>();
			this.replytoAddresses.addAll(emailAddressListList);
		}
	}
	
	public List<EmailAddressListEntity> getAdminAddresses() {
		if (adminAddresses == null)
			adminAddresses = new ArrayList<EmailAddressListEntity>();
		return adminAddresses;
	}
	
	public void setAdminAddresses(Collection<EmailAddressListEntity> emailAddressListList) {
		if (emailAddressListList == null) {
			this.adminAddresses = null;
		} else {
			this.adminAddresses = new ArrayList<EmailAddressListEntity>();
			this.adminAddresses.addAll(emailAddressListList);
		}
	}
	
	public List<AttachmentEntity> getAttachments() {
		if (attachments == null)
			attachments = new ArrayList<AttachmentEntity>();
		return attachments;
	}
	
	public void setAttachments(Collection<AttachmentEntity> attachmentList) {
		if (attachmentList == null) {
			this.attachments = null;
		} else {
			this.attachments = new ArrayList<AttachmentEntity>();
			this.attachments.addAll(attachmentList);
		}
	}
	
	public Boolean isSendAsHtml() {
		return sendAsHtml;
	}
	
	public Boolean getSendAsHtml() {
		return sendAsHtml;
	}
	
	public void setSendAsHtml(Boolean sendAsHtml) {
		this.sendAsHtml = sendAsHtml;
	}
	
	@Override
	public String toString() {
		if (getId() != null)
			return getClass().getSimpleName()+"["+getId()+"]";
		return super.toString();
	}
	
}
