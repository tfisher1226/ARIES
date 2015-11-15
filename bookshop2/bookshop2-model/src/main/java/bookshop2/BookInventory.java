package bookshop2;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookInventory", namespace = "http://bookshop2", propOrder = {
	"id",
	"books"
})
@XmlRootElement(name = "bookInventory", namespace = "http://bookshop2")
public class BookInventory implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://bookshop2")
	private Long id;
	
	@XmlElement(name = "books", namespace = "http://bookshop2")
	private Set<Book> books;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Set<Book> getBooks() {
		if (books == null)
			books = new HashSet<Book>();
		return books;
	}
	
	public void setBooks(Set<Book> books) {
		this.books = books;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		BookInventory other = (BookInventory) object;
		if (this.getId() == null || other.getId() == null)
			return this == other;
		if (this.getId().equals(other.getId()))
			return true;
		return this == object;
	}
	
}
