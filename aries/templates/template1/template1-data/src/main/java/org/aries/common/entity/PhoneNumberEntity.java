package org.aries.common.entity;

import java.lang.String;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.aries.common.Country;
import org.aries.common.PhoneLocation;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Generated by Nam.
 *
 */
@Entity(name = "PhoneNumber")
@Table(name = "phone_number")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PhoneNumberEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "type")
	private PhoneLocation type;

	@Column(name = "area")
	private String area;

	@Column(name = "country")
	private Country country;

	@Column(name = "extension")
	private String extension;

	@Column(name = "number")
	private String number;

	@Column(name = "value")
	private String value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PhoneLocation getType() {
		return type;
	}

	public void setType(PhoneLocation type) {
		this.type = type;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}