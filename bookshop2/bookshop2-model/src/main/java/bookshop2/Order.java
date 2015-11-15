package bookshop2;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.DateTimeAdapter;
import org.aries.common.PersonName;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Order", namespace = "http://bookshop2", propOrder = {
	"id",
	"trackingNumber",
	"personName",
	"count",
	"books",
	"dateTime"
})
@XmlRootElement(name = "order", namespace = "http://bookshop2")
public class Order implements Comparable<Order>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://bookshop2")
	private Long id;
	
	@XmlElement(name = "trackingNumber", namespace = "http://bookshop2", required = true)
	private String trackingNumber;
	
	@XmlElement(name = "personName", namespace = "http://bookshop2", required = true)
	private PersonName personName;
	
	@XmlElement(name = "count", namespace = "http://bookshop2", required = true)
	private Integer count;
	
	@XmlElement(name = "books", namespace = "http://bookshop2", required = true)
	private Set<Book> books;
	
	@XmlElement(name = "dateTime", namespace = "http://bookshop2", type = String.class, required = true)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date dateTime;
	
	
	public Order() {
		books = new HashSet<Book>();
	}
	
	
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
	
	public PersonName getPersonName() {
		return personName;
	}
	
	public void setPersonName(PersonName personName) {
		this.personName = personName;
	}
	
	public Integer getCount() {
		return count;
	}
	
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public Set<Book> getBooks() {
		synchronized (books) {
			return books;
		}
	}
	
	public void setBooks(Collection<Book> bookSet) {
		if (bookSet == null) {
			this.books = null;
		} else {
			synchronized (this.books) {
				this.books = new HashSet<Book>();
				addToBooks(bookSet);
			}
		}
	}

	public void addToBooks(Book book) {
		if (book != null ) {
			synchronized (this.books) {
				this.books.add(book);
			}
		}
	}

	public void addToBooks(Collection<Book> bookCollection) {
		if (bookCollection != null && !bookCollection.isEmpty()) {
			synchronized (this.books) {
				this.books.addAll(bookCollection);
			}
		}
	}

	public void removeFromBooks(Book book) {
		if (book != null ) {
			synchronized (this.books) {
				this.books.remove(book);
			}
		}
	}

	public void removeFromBooks(Collection<Book> bookCollection) {
		if (bookCollection != null ) {
			synchronized (this.books) {
				this.books.removeAll(bookCollection);
			}
		}
	}

	public void clearBooks() {
		synchronized (books) {
			books.clear();
		}
	}
	
	public Date getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(Order other) {
		int status = compare(personName, other.personName);
		if (status != 0)
			return status;
		status = compare(dateTime, other.dateTime);
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
		Order other = (Order) object;
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
		if (trackingNumber != null)
			hashCode += trackingNumber.hashCode();
		if (dateTime != null)
			hashCode += dateTime.hashCode();
		if (personName != null)
			hashCode += personName.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Order: trackingNumber="+trackingNumber+", personName="+personName+", dateTime="+dateTime;
	}
	
}
