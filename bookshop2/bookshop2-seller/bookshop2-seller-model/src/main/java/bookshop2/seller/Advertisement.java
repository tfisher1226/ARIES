package bookshop2.seller;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;
import org.aries.adapter.DateAdapter;
import org.aries.adapter.DateTimeAdapter;
import org.aries.common.EmailAddress;
import org.aries.common.PhoneNumber;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Advertisement", namespace = "http://bookshop2/seller", propOrder = {
	"id",
	"enabled",
	"onSale",
	"productId",
	"salesPrice",
	"saleExpiration",
	"firstName",
	"lastName",
	"emailAddress",
	"homePhone",
	"cellPhone",
	"creationDate",
	"lastUpdate"
})
@XmlRootElement(name = "advertisement", namespace = "http://bookshop2/seller")
public class Advertisement implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "id", namespace = "http://bookshop2/seller")
	private Long id;

	@XmlElement(name = "enabled", namespace = "http://bookshop2/seller", type = String.class)
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean enabled;

	@XmlElement(name = "onSale", namespace = "http://bookshop2/seller", type = String.class)
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean onSale;

	@XmlElement(name = "productId", namespace = "http://bookshop2/seller", required = true)
	private String productId;

	@XmlElement(name = "salesPrice", namespace = "http://bookshop2/seller", required = true)
	private Double salesPrice;

	@XmlElement(name = "saleExpiration", namespace = "http://bookshop2/seller", type = String.class)
	@XmlJavaTypeAdapter(DateAdapter.class)
	@XmlSchemaType(name = "date")
	private Date saleExpiration;

	@XmlElement(name = "firstName", namespace = "http://bookshop2/seller", required = true)
	private String firstName;

	@XmlElement(name = "lastName", namespace = "http://bookshop2/seller", required = true)
	private String lastName;

	@XmlElement(name = "emailAddress", namespace = "http://bookshop2/seller", required = true)
	private EmailAddress emailAddress;

	@XmlElement(name = "homePhone", namespace = "http://bookshop2/seller")
	private PhoneNumber homePhone;

	@XmlElement(name = "cellPhone", namespace = "http://bookshop2/seller")
	private PhoneNumber cellPhone;

	@XmlElement(name = "creationDate", namespace = "http://bookshop2/seller", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date creationDate;

	@XmlElement(name = "lastUpdate", namespace = "http://bookshop2/seller", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date lastUpdate;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean isOnSale() {
		return onSale;
	}

	public Boolean getOnSale() {
		return onSale;
	}

	public void setOnSale(Boolean onSale) {
		this.onSale = onSale;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Date getSaleExpiration() {
		return saleExpiration;
	}

	public void setSaleExpiration(Date saleExpiration) {
		this.saleExpiration = saleExpiration;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}

	public PhoneNumber getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(PhoneNumber homePhone) {
		this.homePhone = homePhone;
	}

	public PhoneNumber getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(PhoneNumber cellPhone) {
		this.cellPhone = cellPhone;
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

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Advertisement other = (Advertisement) object;
		if (this.getId() == null || other.getId() == null)
			return this == other;
		if (this.getId().equals(other.getId()))
			return true;
		return this == object;
	}

	@Override
	public int hashCode() {
		if (getId() != null)
			return getId().hashCode();
		return super.hashCode();
	}

}
