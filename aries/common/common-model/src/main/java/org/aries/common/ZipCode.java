package org.aries.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZipCode", namespace = "http://www.aries.org/common", propOrder = {
	"id",
	"number",
	"extension",
	"country"
})
@XmlRootElement(name = "zipCode", namespace = "http://www.aries.org/common")
public class ZipCode implements Comparable<ZipCode>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://www.aries.org/common")
	private Long id;
	
	@XmlElement(name = "number", namespace = "http://www.aries.org/common", required = true)
	private String number;
	
	@XmlElement(name = "extension", namespace = "http://www.aries.org/common")
	private String extension;
	
	@XmlElement(name = "country", namespace = "http://www.aries.org/common", required = true)
	private Country country;
	
	
	public ZipCode() {
		//nothing for now
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(ZipCode other) {
		int status = compare(number, other.number);
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
		ZipCode other = (ZipCode) object;
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
		if (number != null)
			hashCode += number.hashCode();
		if (country != null)
			hashCode += country.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "ZipCode: number="+number+", country="+country;
	}
	
}
