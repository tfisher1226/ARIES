package org.aries.bank;

import java.io.Serializable;
import java.lang.String;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Generated by Nam.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAccount", propOrder = {
	"balance",
	"duration",
	"holderName",
	"holderSSN",
	"id"
})
@XmlRootElement(name = "bankAccount")
public class BankAccount implements Serializable {

	public static final long serialVersionUID = 1;

	@XmlAttribute(name = "balance")
	private Double balance;

	@XmlAttribute(name = "duration")
	private Integer duration;

	@XmlAttribute(name = "holder-name")
	private String holderName;

	@XmlAttribute(name = "holder-s-s-n")
	private String holderSSN;

	@XmlAttribute(name = "id")
	private Long id;

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getHolderSSN() {
		return holderSSN;
	}

	public void setHolderSSN(String holderSSN) {
		this.holderSSN = holderSSN;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		BankAccount other = (BankAccount) object;
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