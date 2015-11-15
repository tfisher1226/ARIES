package org.aries.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;
import org.aries.adapter.DateTimeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmailMessage", namespace = "http://www.aries.org/common", propOrder = {
	"id",
	"content",
	"subject",
	"timestamp",
	"sourceId",
	"smtpHost",
	"smtpPort",
	"fromAddress",
	"toAddresses",
	"bccAddresses",
	"ccAddresses",
	"replytoAddresses",
	"adminAddresses",
	"attachments",
	"sendAsHtml"
})
@XmlRootElement(name = "emailMessage", namespace = "http://www.aries.org/common")
public class EmailMessage implements Comparable<EmailMessage>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://www.aries.org/common")
	private Long id;
	
	@XmlElement(name = "content", namespace = "http://www.aries.org/common")
	private String content;
	
	@XmlElement(name = "subject", namespace = "http://www.aries.org/common")
	private String subject;
	
	@XmlElement(name = "timestamp", namespace = "http://www.aries.org/common", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date timestamp;
	
	@XmlElement(name = "sourceId", namespace = "http://www.aries.org/common")
	private String sourceId;
	
	@XmlElement(name = "smtpHost", namespace = "http://www.aries.org/common")
	private String smtpHost;
	
	@XmlElement(name = "smtpPort", namespace = "http://www.aries.org/common")
	private String smtpPort;
	
	@XmlElement(name = "fromAddress", namespace = "http://www.aries.org/common", required = true, nillable = true)
	private EmailAddress fromAddress;
	
	@XmlElement(name = "toAddresses", namespace = "http://www.aries.org/common", nillable = true)
	private List<EmailAddressList> toAddresses;
	
	@XmlElement(name = "bccAddresses", namespace = "http://www.aries.org/common", nillable = true)
	private List<EmailAddressList> bccAddresses;
	
	@XmlElement(name = "ccAddresses", namespace = "http://www.aries.org/common", nillable = true)
	private List<EmailAddressList> ccAddresses;
	
	@XmlElement(name = "replytoAddresses", namespace = "http://www.aries.org/common", nillable = true)
	private List<EmailAddressList> replytoAddresses;
	
	@XmlElement(name = "adminAddresses", namespace = "http://www.aries.org/common", nillable = true)
	private List<EmailAddressList> adminAddresses;
	
	@XmlElement(name = "attachments", namespace = "http://www.aries.org/common", nillable = true)
	private List<Attachment> attachments;
	
	@XmlElement(name = "sendAsHtml", namespace = "http://www.aries.org/common", type = String.class)
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean sendAsHtml;
	
	
	public EmailMessage() {
		toAddresses = new ArrayList<EmailAddressList>();
		bccAddresses = new ArrayList<EmailAddressList>();
		ccAddresses = new ArrayList<EmailAddressList>();
		replytoAddresses = new ArrayList<EmailAddressList>();
		adminAddresses = new ArrayList<EmailAddressList>();
		attachments = new ArrayList<Attachment>();
	}
	
	
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
	
	public EmailAddress getFromAddress() {
		return fromAddress;
	}
	
	public void setFromAddress(EmailAddress emailAddress) {
		this.fromAddress = emailAddress;
	}
	
	public List<EmailAddressList> getToAddresses() {
		synchronized (toAddresses) {
			return toAddresses;
		}
	}
	
	public void setToAddresses(Collection<EmailAddressList> emailAddressListList) {
		if (emailAddressListList == null) {
			this.toAddresses = null;
		} else {
		synchronized (this.toAddresses) {
			this.toAddresses = new ArrayList<EmailAddressList>();
				addToToAddresses(emailAddressListList);
			}
		}
	}

	public void addToToAddresses(EmailAddressList emailAddressList) {
		if (emailAddressList != null ) {
			synchronized (this.toAddresses) {
				this.toAddresses.add(emailAddressList);
			}
		}
	}

	public void addToToAddresses(Collection<EmailAddressList> emailAddressListCollection) {
		if (emailAddressListCollection != null && !emailAddressListCollection.isEmpty()) {
			synchronized (this.toAddresses) {
				this.toAddresses.addAll(emailAddressListCollection);
			}
		}
	}

	public void removeFromToAddresses(EmailAddressList emailAddressList) {
		if (emailAddressList != null ) {
			synchronized (this.toAddresses) {
				this.toAddresses.remove(emailAddressList);
			}
		}
	}

	public void removeFromToAddresses(Collection<EmailAddressList> emailAddressListCollection) {
		if (emailAddressListCollection != null ) {
			synchronized (this.toAddresses) {
				this.toAddresses.removeAll(emailAddressListCollection);
			}
		}
	}

	public void clearToAddresses() {
		synchronized (toAddresses) {
			toAddresses.clear();
		}
	}
	
	public List<EmailAddressList> getBccAddresses() {
		synchronized (bccAddresses) {
			return bccAddresses;
		}
	}
	
	public void setBccAddresses(Collection<EmailAddressList> emailAddressListList) {
		if (emailAddressListList == null) {
			this.bccAddresses = null;
		} else {
		synchronized (this.bccAddresses) {
			this.bccAddresses = new ArrayList<EmailAddressList>();
				addToBccAddresses(emailAddressListList);
			}
		}
	}

	public void addToBccAddresses(EmailAddressList emailAddressList) {
		if (emailAddressList != null ) {
			synchronized (this.bccAddresses) {
				this.bccAddresses.add(emailAddressList);
			}
		}
	}

	public void addToBccAddresses(Collection<EmailAddressList> emailAddressListCollection) {
		if (emailAddressListCollection != null && !emailAddressListCollection.isEmpty()) {
			synchronized (this.bccAddresses) {
				this.bccAddresses.addAll(emailAddressListCollection);
			}
		}
	}

	public void removeFromBccAddresses(EmailAddressList emailAddressList) {
		if (emailAddressList != null ) {
			synchronized (this.bccAddresses) {
				this.bccAddresses.remove(emailAddressList);
			}
		}
	}

	public void removeFromBccAddresses(Collection<EmailAddressList> emailAddressListCollection) {
		if (emailAddressListCollection != null ) {
			synchronized (this.bccAddresses) {
				this.bccAddresses.removeAll(emailAddressListCollection);
			}
		}
	}

	public void clearBccAddresses() {
		synchronized (bccAddresses) {
			bccAddresses.clear();
		}
	}
	
	public List<EmailAddressList> getCcAddresses() {
		synchronized (ccAddresses) {
			return ccAddresses;
		}
	}
	
	public void setCcAddresses(Collection<EmailAddressList> emailAddressListList) {
		if (emailAddressListList == null) {
			this.ccAddresses = null;
		} else {
		synchronized (this.ccAddresses) {
			this.ccAddresses = new ArrayList<EmailAddressList>();
				addToCcAddresses(emailAddressListList);
			}
		}
	}

	public void addToCcAddresses(EmailAddressList emailAddressList) {
		if (emailAddressList != null ) {
			synchronized (this.ccAddresses) {
				this.ccAddresses.add(emailAddressList);
			}
		}
	}

	public void addToCcAddresses(Collection<EmailAddressList> emailAddressListCollection) {
		if (emailAddressListCollection != null && !emailAddressListCollection.isEmpty()) {
			synchronized (this.ccAddresses) {
				this.ccAddresses.addAll(emailAddressListCollection);
			}
		}
	}

	public void removeFromCcAddresses(EmailAddressList emailAddressList) {
		if (emailAddressList != null ) {
			synchronized (this.ccAddresses) {
				this.ccAddresses.remove(emailAddressList);
			}
		}
	}

	public void removeFromCcAddresses(Collection<EmailAddressList> emailAddressListCollection) {
		if (emailAddressListCollection != null ) {
			synchronized (this.ccAddresses) {
				this.ccAddresses.removeAll(emailAddressListCollection);
			}
		}
	}

	public void clearCcAddresses() {
		synchronized (ccAddresses) {
			ccAddresses.clear();
		}
	}
	
	public List<EmailAddressList> getReplytoAddresses() {
		synchronized (replytoAddresses) {
			return replytoAddresses;
		}
	}
	
	public void setReplytoAddresses(Collection<EmailAddressList> emailAddressListList) {
		if (emailAddressListList == null) {
			this.replytoAddresses = null;
		} else {
		synchronized (this.replytoAddresses) {
			this.replytoAddresses = new ArrayList<EmailAddressList>();
				addToReplytoAddresses(emailAddressListList);
			}
		}
	}

	public void addToReplytoAddresses(EmailAddressList emailAddressList) {
		if (emailAddressList != null ) {
			synchronized (this.replytoAddresses) {
				this.replytoAddresses.add(emailAddressList);
			}
		}
	}

	public void addToReplytoAddresses(Collection<EmailAddressList> emailAddressListCollection) {
		if (emailAddressListCollection != null && !emailAddressListCollection.isEmpty()) {
			synchronized (this.replytoAddresses) {
				this.replytoAddresses.addAll(emailAddressListCollection);
			}
		}
	}

	public void removeFromReplytoAddresses(EmailAddressList emailAddressList) {
		if (emailAddressList != null ) {
			synchronized (this.replytoAddresses) {
				this.replytoAddresses.remove(emailAddressList);
			}
		}
	}

	public void removeFromReplytoAddresses(Collection<EmailAddressList> emailAddressListCollection) {
		if (emailAddressListCollection != null ) {
			synchronized (this.replytoAddresses) {
				this.replytoAddresses.removeAll(emailAddressListCollection);
			}
		}
	}

	public void clearReplytoAddresses() {
		synchronized (replytoAddresses) {
			replytoAddresses.clear();
		}
	}
	
	public List<EmailAddressList> getAdminAddresses() {
		synchronized (adminAddresses) {
			return adminAddresses;
		}
	}
	
	public void setAdminAddresses(Collection<EmailAddressList> emailAddressListList) {
		if (emailAddressListList == null) {
			this.adminAddresses = null;
		} else {
		synchronized (this.adminAddresses) {
			this.adminAddresses = new ArrayList<EmailAddressList>();
				addToAdminAddresses(emailAddressListList);
			}
		}
	}

	public void addToAdminAddresses(EmailAddressList emailAddressList) {
		if (emailAddressList != null ) {
			synchronized (this.adminAddresses) {
				this.adminAddresses.add(emailAddressList);
			}
		}
	}

	public void addToAdminAddresses(Collection<EmailAddressList> emailAddressListCollection) {
		if (emailAddressListCollection != null && !emailAddressListCollection.isEmpty()) {
			synchronized (this.adminAddresses) {
				this.adminAddresses.addAll(emailAddressListCollection);
			}
		}
	}

	public void removeFromAdminAddresses(EmailAddressList emailAddressList) {
		if (emailAddressList != null ) {
			synchronized (this.adminAddresses) {
				this.adminAddresses.remove(emailAddressList);
			}
		}
	}

	public void removeFromAdminAddresses(Collection<EmailAddressList> emailAddressListCollection) {
		if (emailAddressListCollection != null ) {
			synchronized (this.adminAddresses) {
				this.adminAddresses.removeAll(emailAddressListCollection);
			}
		}
	}

	public void clearAdminAddresses() {
		synchronized (adminAddresses) {
			adminAddresses.clear();
		}
	}
	
	public List<Attachment> getAttachments() {
		synchronized (attachments) {
			return attachments;
		}
	}
	
	public void setAttachments(Collection<Attachment> attachmentList) {
		if (attachmentList == null) {
			this.attachments = null;
		} else {
		synchronized (this.attachments) {
			this.attachments = new ArrayList<Attachment>();
				addToAttachments(attachmentList);
			}
		}
	}

	public void addToAttachments(Attachment attachment) {
		if (attachment != null ) {
			synchronized (this.attachments) {
				this.attachments.add(attachment);
			}
		}
	}

	public void addToAttachments(Collection<Attachment> attachmentCollection) {
		if (attachmentCollection != null && !attachmentCollection.isEmpty()) {
			synchronized (this.attachments) {
				this.attachments.addAll(attachmentCollection);
			}
		}
	}

	public void removeFromAttachments(Attachment attachment) {
		if (attachment != null ) {
			synchronized (this.attachments) {
				this.attachments.remove(attachment);
			}
		}
	}

	public void removeFromAttachments(Collection<Attachment> attachmentCollection) {
		if (attachmentCollection != null ) {
			synchronized (this.attachments) {
				this.attachments.removeAll(attachmentCollection);
			}
		}
	}

	public void clearAttachments() {
		synchronized (attachments) {
			attachments.clear();
		}
	}
	
	public Boolean isSendAsHtml() {
		return sendAsHtml != null && sendAsHtml;
	}
	
	public Boolean getSendAsHtml() {
		return sendAsHtml != null && sendAsHtml;
	}
	
	public void setSendAsHtml(Boolean sendAsHtml) {
		this.sendAsHtml = sendAsHtml;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(EmailMessage other) {
		int status = compare(timestamp, other.timestamp);
		if (status != 0)
			return status;
		status = compare(sourceId, other.sourceId);
		if (status != 0)
			return status;
		return 0;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		EmailMessage other = (EmailMessage) object;
		if (id != null)
			return id.equals(other.id);
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		if (id != null)
			return id.hashCode();
		int hashCode = 0;
		if (timestamp != null)
			hashCode += timestamp.hashCode();
		if (sourceId != null)
			hashCode += sourceId.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "EmailMessage: timestamp="+timestamp+", sourceId="+sourceId;
	}
	
}
