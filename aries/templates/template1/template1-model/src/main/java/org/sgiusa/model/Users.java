package org.sgiusa.model;

import java.io.Serializable;
import java.lang.Long;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.sgiusa.model.User;

/**
 * Generated by Nam.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Users", propOrder = {
	"id",
	"record"
})
@XmlRootElement(name = "users")
public class Users implements Serializable {

	public static final long serialVersionUID = 1;

	@XmlAttribute(name = "id")
	private Long id;

	@XmlElement(name = "record")
	private List<User> record;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<User> getRecord() {
		return record;
	}

	public void setRecord(List<User> record) {
		this.record = record;
	}

	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Users other = (Users) object;
		if (this.getId() == null || other.getId() == null)
			return this == other;
		if (this.getId().equals(other.getId()))
				return false;
		return true;
	}

	public int hashCode() {
		if (getId() != null)
			return getId().hashCode();
		return 0;
	}

}