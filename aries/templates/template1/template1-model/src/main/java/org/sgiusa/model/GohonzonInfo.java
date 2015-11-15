package org.sgiusa.model;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.sgiusa.model.GohonzonType;

/**
 * Generated by Nam.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GohonzonInfo", propOrder = {
	"id",
	"gohonzonType",
	"receiveDate",
	"returned",
	"returnDate",
	"lastUpdate"
})
@XmlRootElement(name = "gohonzonInfo")
public class GohonzonInfo implements Serializable {

	public static final long serialVersionUID = 1;

	@XmlAttribute(name = "id")
	private Long id;

	@XmlAttribute(name = "gohonzon-type")
	private GohonzonType gohonzonType;

	@XmlAttribute(name = "receive-date")
	private Date receiveDate;

	@XmlAttribute(name = "returned")
	private Boolean returned;

	@XmlAttribute(name = "return-date")
	private Date returnDate;

	@XmlAttribute(name = "last-update")
	private Date lastUpdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GohonzonType getGohonzonType() {
		return gohonzonType;
	}

	public void setGohonzonType(GohonzonType gohonzonType) {
		this.gohonzonType = gohonzonType;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Boolean getReturned() {
		return returned;
	}

	public void setReturned(Boolean returned) {
		this.returned = returned;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		GohonzonInfo other = (GohonzonInfo) object;
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