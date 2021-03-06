package org.sgiusa.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.sgiusa.model.GohonzonInfo;

/**
 * Generated by Nam.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MembershipInfo", propOrder = {
	"id",
	"friendOfSgi",
	"receivedCertificate",
	"notActivated",
	"notLocatable",
	"lastUpdate",
	"gohonzons"
})
@XmlRootElement(name = "membershipInfo")
public class MembershipInfo implements Serializable {

	public static final long serialVersionUID = 1;

	@XmlAttribute(name = "id")
	private Long id;

	@XmlAttribute(name = "friend-of-sgi")
	private Boolean friendOfSgi;

	@XmlAttribute(name = "received-certificate")
	private Boolean receivedCertificate;

	@XmlAttribute(name = "not-activated")
	private Boolean notActivated;

	@XmlAttribute(name = "not-locatable")
	private Boolean notLocatable;

	@XmlAttribute(name = "last-update")
	private Date lastUpdate;

	@XmlElement(name = "gohonzons")
	private List<GohonzonInfo> gohonzons;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getFriendOfSgi() {
		return friendOfSgi;
	}

	public void setFriendOfSgi(Boolean friendOfSgi) {
		this.friendOfSgi = friendOfSgi;
	}

	public Boolean getReceivedCertificate() {
		return receivedCertificate;
	}

	public void setReceivedCertificate(Boolean receivedCertificate) {
		this.receivedCertificate = receivedCertificate;
	}

	public Boolean getNotActivated() {
		return notActivated;
	}

	public void setNotActivated(Boolean notActivated) {
		this.notActivated = notActivated;
	}

	public Boolean getNotLocatable() {
		return notLocatable;
	}

	public void setNotLocatable(Boolean notLocatable) {
		this.notLocatable = notLocatable;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public List<GohonzonInfo> getGohonzons() {
		return gohonzons;
	}

	public void setGohonzons(List<GohonzonInfo> gohonzons) {
		this.gohonzons = gohonzons;
	}

	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		MembershipInfo other = (MembershipInfo) object;
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