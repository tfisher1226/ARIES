package org.aries.common;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.DateTimeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractNote", namespace = "http://www.aries.org/common", propOrder = {
	"id",
	"text",
	"author",
	"creationDate",
	"lastUpdate"
})
@XmlRootElement(name = "abstractNote", namespace = "http://www.aries.org/common")
public abstract class AbstractNote implements Comparable<AbstractNote>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://www.aries.org/common")
	private Long id;
	
	@XmlElement(name = "text", namespace = "http://www.aries.org/common")
	private String text;
	
	@XmlElement(name = "author", namespace = "http://www.aries.org/common", required = true)
	private AbstractUser author;
	
	@XmlElement(name = "creationDate", namespace = "http://www.aries.org/common", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date creationDate;
	
	@XmlElement(name = "lastUpdate", namespace = "http://www.aries.org/common", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date lastUpdate;
	
	
	public AbstractNote() {
		//nothing for now
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public AbstractUser getAuthor() {
		return author;
	}
	
	public void setAuthor(AbstractUser user) {
		this.author = user;
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
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(AbstractNote other) {
		int status = compare(author, other.author);
		if (status != 0)
			return status;
		status = compare(creationDate, other.creationDate);
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
		AbstractNote other = (AbstractNote) object;
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
		if (creationDate != null)
			hashCode += creationDate.hashCode();
		if (author != null)
			hashCode += author.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(getClass().getName()+": ");
		buf.append("hashCode="+hashCode());
		if (author != null)
			buf.append(", author="+author);
		if (creationDate != null)
			buf.append(", creationDate="+creationDate);
		return buf.toString();
	}
	
}
