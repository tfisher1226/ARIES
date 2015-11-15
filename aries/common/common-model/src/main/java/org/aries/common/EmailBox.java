package org.aries.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.DateTimeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmailBox", namespace = "http://www.aries.org/common", propOrder = {
	"id",
	"type",
	"name",
	"parentBox",
	"messages",
	"creationDate",
	"lastUpdate"
})
@XmlRootElement(name = "emailBox", namespace = "http://www.aries.org/common")
public class EmailBox implements Comparable<EmailBox>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://www.aries.org/common")
	private Long id;
	
	@XmlElement(name = "type", namespace = "http://www.aries.org/common")
	private String type;
	
	@XmlElement(name = "name", namespace = "http://www.aries.org/common")
	private String name;
	
	@XmlTransient
	private EmailAccount emailAccount;
	
	@XmlElement(name = "parentBox", namespace = "http://www.aries.org/common")
	private EmailBox parentBox;
	
	@XmlElement(name = "messages", namespace = "http://www.aries.org/common", nillable = true)
	private List<EmailMessage> messages;
	
	@XmlElement(name = "creationDate", namespace = "http://www.aries.org/common", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date creationDate;
	
	@XmlElement(name = "lastUpdate", namespace = "http://www.aries.org/common", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date lastUpdate;
	
	
	public EmailBox() {
		messages = new ArrayList<EmailMessage>();
	}
	
	
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
	
	public EmailAccount getEmailAccount() {
		return emailAccount;
	}
	
	public void setEmailAccount(EmailAccount emailAccount) {
		this.emailAccount = emailAccount;
	}
	
	public EmailBox getParentBox() {
		return parentBox;
	}
	
	public void setParentBox(EmailBox emailBox) {
		this.parentBox = emailBox;
	}
	
	public List<EmailMessage> getMessages() {
		synchronized (messages) {
			return messages;
		}
	}
	
	public void setMessages(Collection<EmailMessage> emailMessageList) {
		if (emailMessageList == null) {
			this.messages = null;
		} else {
			synchronized (this.messages) {
				this.messages = new ArrayList<EmailMessage>();
				addToMessages(emailMessageList);
			}
		}
	}

	public void addToMessages(EmailMessage emailMessage) {
		if (emailMessage != null ) {
			synchronized (this.messages) {
				this.messages.add(emailMessage);
			}
		}
	}

	public void addToMessages(Collection<EmailMessage> emailMessageCollection) {
		if (emailMessageCollection != null && !emailMessageCollection.isEmpty()) {
			synchronized (this.messages) {
				this.messages.addAll(emailMessageCollection);
			}
		}
	}

	public void removeFromMessages(EmailMessage emailMessage) {
		if (emailMessage != null ) {
			synchronized (this.messages) {
				this.messages.remove(emailMessage);
			}
		}
	}

	public void removeFromMessages(Collection<EmailMessage> emailMessageCollection) {
		if (emailMessageCollection != null ) {
			synchronized (this.messages) {
				this.messages.removeAll(emailMessageCollection);
			}
		}
	}

	public void clearMessages() {
		synchronized (messages) {
			messages.clear();
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
	
	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		if (parent instanceof EmailAccount) {
			this.emailAccount = (EmailAccount) parent;
		}
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(EmailBox other) {
		int status = compare(type, other.type);
		if (status != 0)
			return status;
		status = compare(name, other.name);
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
		EmailBox other = (EmailBox) object;
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
		if (type != null)
			hashCode += type.hashCode();
		if (name != null)
			hashCode += name.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "EmailBox: type="+type+", name="+name;
	}
	
}
