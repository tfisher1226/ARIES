package org.aries.common.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.aries.common.Country;
import org.aries.common.PhoneLocation;
import org.hibernate.annotations.Cache;


@Entity(name = "PhoneNumber")
@Table(name = "phone_number")
@Cache(usage = READ_WRITE)
public class PhoneNumberEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "area", nullable = false)
	private String area;
	
	@Column(name = "number", nullable = false)
	private String number;
	
	@Column(name = "extension")
	private String extension;
	
	@Column(name = "country", nullable = false)
	@Enumerated(EnumType.STRING)
	private Country country;
	
	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private PhoneLocation type;
	
	@Column(name = "value")
	private String value;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public Country getCountry() {
		return country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}
	
	public PhoneLocation getType() {
		return type;
	}
	
	public void setType(PhoneLocation type) {
		this.type = type;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		if (getId() != null)
			return getClass().getSimpleName()+"["+getId()+"]";
		return super.toString();
	}
	
}
