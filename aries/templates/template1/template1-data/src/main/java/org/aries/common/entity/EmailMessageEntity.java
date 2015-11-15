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
import org.aries.common.entity.AttachmentEntity;
import org.aries.common.entity.EmailAddressEntity;
import org.aries.common.entity.EmailAddressListEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "EmailMessage")
@Table(name = "email_message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmailMessageEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "content")
	private String content;

	@Column(name = "subject")
	private String subject;

	@Column(name = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@Id
	@GeneratedValue
	@Column(name = "source_id")
	private String sourceId;

	@Column(name = "smtp_host")
	private String smtpHost;

	@Column(name = "smtp_port")
	private String smtpPort;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private EmailAddressEntity fromAddress;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<EmailAddressListEntity> toAddressList;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<EmailAddressListEntity> bccAddressList;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<EmailAddressListEntity> ccAddressList;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<EmailAddressListEntity> replytoAddressList;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<EmailAddressListEntity> adminAddressList;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

	public void setFromAddress(EmailAddressEntity fromAddress) {
		this.fromAddress = fromAddress;
	}

	public List<EmailAddressListEntity> getToAddressList() {
		if (toAddressList == null)
			toAddressList = new ArrayList<EmailAddressListEntity>();
		return toAddressList;
	}

	public void setToAddressList(List<EmailAddressListEntity> toAddressList) {
		this.toAddressList = toAddressList;
	}

	public List<EmailAddressListEntity> getBccAddressList() {
		if (bccAddressList == null)
			bccAddressList = new ArrayList<EmailAddressListEntity>();
		return bccAddressList;
	}

	public void setBccAddressList(List<EmailAddressListEntity> bccAddressList) {
		this.bccAddressList = bccAddressList;
	}

	public List<EmailAddressListEntity> getCcAddressList() {
		if (ccAddressList == null)
			ccAddressList = new ArrayList<EmailAddressListEntity>();
		return ccAddressList;
	}

	public void setCcAddressList(List<EmailAddressListEntity> ccAddressList) {
		this.ccAddressList = ccAddressList;
	}

	public List<EmailAddressListEntity> getReplytoAddressList() {
		if (replytoAddressList == null)
			replytoAddressList = new ArrayList<EmailAddressListEntity>();
		return replytoAddressList;
	}

	public void setReplytoAddressList(List<EmailAddressListEntity> replytoAddressList) {
		this.replytoAddressList = replytoAddressList;
	}

	public List<EmailAddressListEntity> getAdminAddressList() {
		if (adminAddressList == null)
			adminAddressList = new ArrayList<EmailAddressListEntity>();
		return adminAddressList;
	}

	public void setAdminAddressList(List<EmailAddressListEntity> adminAddressList) {
		this.adminAddressList = adminAddressList;
	}

	public List<AttachmentEntity> getAttachments() {
		if (attachments == null)
			attachments = new ArrayList<AttachmentEntity>();
		return attachments;
	}

	public void setAttachments(List<AttachmentEntity> attachments) {
		this.attachments = attachments;
	}

	public Boolean isSendAsHtml() {
		return sendAsHtml;
	}

	public void setSendAsHtml(Boolean sendAsHtml) {
		this.sendAsHtml = sendAsHtml;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		EmailMessageEntity other = (EmailMessageEntity) object;
		if (this.getSourceId() == null || other.getSourceId() == null || 
			this.getId() == null || other.getId() == null)
			return this == other;
		if (this.getSourceId().equals(other.getSourceId()) && 
			this.getId().equals(other.getId()))
				return true;
		return false;
	}

	@Override
	public int hashCode() {
		if (getSourceId() != null)
			return getSourceId().hashCode();
		return 0;
	}

}