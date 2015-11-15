package bookshop2.supplier.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.aries.common.entity.PersonNameEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.ForeignKey;


@MappedSuperclass
public class AbstractOrderEntity<T extends AbstractBookEntity> implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "tracking_number", nullable = false, unique = true)
	private String trackingNumber;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "person_name_id", nullable = false)
	@ForeignKey(name = "order_person_name_fk", inverseName = "order_person_name_inverse_fk")
	@Cache(usage = READ_WRITE)
	private PersonNameEntity personName;
	
	@Column(name = "count", nullable = false)
	private Integer count;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "order_book", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
	@ForeignKey(name = "order_book_fk", inverseName = "order_book_inverse_fk")
	@Cache(usage = READ_WRITE)
	private Set<T> books;
	
	@Column(name = "date_time", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTrackingNumber() {
		return trackingNumber;
	}
	
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	
	public PersonNameEntity getPersonName() {
		return personName;
	}
	
	public void setPersonName(PersonNameEntity personName) {
		this.personName = personName;
	}
	
	public Integer getCount() {
		return count;
	}
	
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public Set<T> getBooks() {
		if (books == null)
			books = new HashSet<T>();
		return books;
	}
	
	public void setBooks(Collection<T> bookSet) {
		if (bookSet == null) {
			this.books = null;
		} else {
			this.books = new HashSet<T>();
			this.books.addAll(bookSet);
		}
	}
	
	public Date getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	@Override
	public String toString() {
		if (getId() != null)
			return getClass().getSimpleName()+"["+getId()+"] trackingNumber="+getTrackingNumber();
		return "getClass().getSimpleName(): trackingNumber="+getTrackingNumber();
	}
	
}
