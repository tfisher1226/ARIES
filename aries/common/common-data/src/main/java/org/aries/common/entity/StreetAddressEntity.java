package org.aries.common.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.aries.common.Country;
import org.aries.common.State;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.ForeignKey;


@Entity(name = "StreetAddress")
@Table(name = "street_address")
@Cache(usage = READ_WRITE)
public class StreetAddressEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "street", nullable = false)
	private String street;
	
	@Column(name = "city", nullable = false)
	private String city;
	
	@Column(name = "state", nullable = false)
	@Enumerated(EnumType.STRING)
	private State state;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "zip_code_id", nullable = false)
	@ForeignKey(name = "street_address_zip_code_fk", inverseName = "street_address_zip_code_inverse_fk")
	@Cache(usage = READ_WRITE)
	private ZipCodeEntity zipCode;
	
	@Column(name = "country", nullable = false)
	@Enumerated(EnumType.STRING)
	private Country country;
	
	@Column(name = "latitude")
	private Double latitude;
	
	@Column(name = "longitude")
	private Double longitude;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public ZipCodeEntity getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(ZipCodeEntity zipCode) {
		this.zipCode = zipCode;
	}
	
	public Country getCountry() {
		return country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		if (getId() != null)
			return getClass().getSimpleName()+"["+getId()+"]";
		return super.toString();
	}
	
}
