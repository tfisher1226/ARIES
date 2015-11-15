package org.aries.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StreetAddress", namespace = "http://www.aries.org/common", propOrder = {
	"id",
	"street",
	"city",
	"state",
	"zipCode",
	"country",
	"latitude",
	"longitude"
})
@XmlRootElement(name = "streetAddress", namespace = "http://www.aries.org/common")
public class StreetAddress implements Comparable<StreetAddress>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://www.aries.org/common")
	private Long id;
	
	@XmlElement(name = "street", namespace = "http://www.aries.org/common", required = true)
	private String street;
	
	@XmlElement(name = "city", namespace = "http://www.aries.org/common", required = true)
	private String city;
	
	@XmlElement(name = "state", namespace = "http://www.aries.org/common", required = true)
	private State state;
	
	@XmlElement(name = "zipCode", namespace = "http://www.aries.org/common", required = true)
	private ZipCode zipCode;
	
	@XmlElement(name = "country", namespace = "http://www.aries.org/common", required = true)
	private Country country;
	
	@XmlElement(name = "latitude", namespace = "http://www.aries.org/common")
	private Double latitude;
	
	@XmlElement(name = "longitude", namespace = "http://www.aries.org/common")
	private Double longitude;
	
	
	public StreetAddress() {
		//nothing for now
	}
	
	
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
	
	public ZipCode getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(ZipCode zipCode) {
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
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(StreetAddress other) {
		int status = compare(street, other.street);
		if (status != 0)
			return status;
		status = compare(city, other.city);
		if (status != 0)
			return status;
		status = compare(state, other.state);
		if (status != 0)
			return status;
		status = compare(zipCode, other.zipCode);
		if (status != 0)
			return status;
		status = compare(country, other.country);
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
		StreetAddress other = (StreetAddress) object;
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
		if (street != null)
			hashCode += street.hashCode();
		if (city != null)
			hashCode += city.hashCode();
		if (state != null)
			hashCode += state.hashCode();
		if (country != null)
			hashCode += country.hashCode();
		if (zipCode != null)
			hashCode += zipCode.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "StreetAddress: street="+street+", city="+city+", state="+state+", zipCode="+zipCode+", country="+country;
	}
	
}
