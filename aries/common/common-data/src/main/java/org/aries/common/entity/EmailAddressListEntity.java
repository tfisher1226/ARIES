package org.aries.common.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.ForeignKey;


@Entity(name = "EmailAddressList")
@Table(name = "email_address_list")
@Cache(usage = READ_WRITE)
public class EmailAddressListEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "email_address_list_addresse", joinColumns = @JoinColumn(name = "email_address_list_id"), inverseJoinColumns = @JoinColumn(name = "addresse_id"))
	@ForeignKey(name = "email_address_list_addresse_fk", inverseName = "email_address_list_addresse_inverse_fk")
	@Cache(usage = READ_WRITE)
	private List<EmailAddressEntity> addresses;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<EmailAddressEntity> getAddresses() {
		if (addresses == null)
			addresses = new ArrayList<EmailAddressEntity>();
		return addresses;
	}
	
	public void setAddresses(Collection<EmailAddressEntity> emailAddressList) {
		if (emailAddressList == null) {
			this.addresses = null;
		} else {
			this.addresses = new ArrayList<EmailAddressEntity>();
			this.addresses.addAll(emailAddressList);
	}
	}
	
	@Override
	public String toString() {
		if (getId() != null)
			return getClass().getSimpleName()+"["+getId()+"]";
		return super.toString();
	}
	
}
