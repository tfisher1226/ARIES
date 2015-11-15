package bookshop2;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.aries.common.AbstractMessage;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderResponseMessage", namespace = "http://bookshop2", propOrder = {
	"order",
	"availableBooks",
	"unavailableBooks"
})
@XmlRootElement(name = "orderResponseMessage", namespace = "http://bookshop2")
public class OrderResponseMessage extends AbstractMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "order", namespace = "http://bookshop2", required = true)
	private Order order;
	
	@XmlElement(name = "availableBooks", namespace = "http://bookshop2")
	private Set<Book> availableBooks;
	
	@XmlElement(name = "unavailableBooks", namespace = "http://bookshop2")
	private Set<Book> unavailableBooks;
	
	
	public OrderResponseMessage() {
		availableBooks = new HashSet<Book>();
		unavailableBooks = new HashSet<Book>();
	}
	
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Set<Book> getAvailableBooks() {
		synchronized (availableBooks) {
			return availableBooks;
		}
	}
	
	public void setAvailableBooks(Collection<Book> bookSet) {
		if (bookSet == null) {
			this.availableBooks = null;
		} else {
			synchronized (this.availableBooks) {
				this.availableBooks = new HashSet<Book>();
				addToAvailableBooks(bookSet);
			}
		}
	}

	public void addToAvailableBooks(Book book) {
		if (book != null ) {
			synchronized (this.availableBooks) {
				this.availableBooks.add(book);
			}
		}
	}

	public void addToAvailableBooks(Collection<Book> bookCollection) {
		if (bookCollection != null && !bookCollection.isEmpty()) {
			synchronized (this.availableBooks) {
				this.availableBooks.addAll(bookCollection);
			}
		}
	}

	public void removeFromAvailableBooks(Book book) {
		if (book != null ) {
			synchronized (this.availableBooks) {
				this.availableBooks.remove(book);
			}
		}
	}

	public void removeFromAvailableBooks(Collection<Book> bookCollection) {
		if (bookCollection != null ) {
			synchronized (this.availableBooks) {
				this.availableBooks.removeAll(bookCollection);
			}
		}
	}

	public void clearAvailableBooks() {
		synchronized (availableBooks) {
			availableBooks.clear();
		}
	}
	
	public Set<Book> getUnavailableBooks() {
		synchronized (unavailableBooks) {
			return unavailableBooks;
		}
	}
	
	public void setUnavailableBooks(Collection<Book> bookSet) {
		if (bookSet == null) {
			this.unavailableBooks = null;
		} else {
			synchronized (this.unavailableBooks) {
				this.unavailableBooks = new HashSet<Book>();
				addToUnavailableBooks(bookSet);
			}
		}
	}

	public void addToUnavailableBooks(Book book) {
		if (book != null ) {
			synchronized (this.unavailableBooks) {
				this.unavailableBooks.add(book);
			}
		}
	}

	public void addToUnavailableBooks(Collection<Book> bookCollection) {
		if (bookCollection != null && !bookCollection.isEmpty()) {
			synchronized (this.unavailableBooks) {
				this.unavailableBooks.addAll(bookCollection);
			}
		}
	}

	public void removeFromUnavailableBooks(Book book) {
		if (book != null ) {
			synchronized (this.unavailableBooks) {
				this.unavailableBooks.remove(book);
			}
		}
	}

	public void removeFromUnavailableBooks(Collection<Book> bookCollection) {
		if (bookCollection != null ) {
			synchronized (this.unavailableBooks) {
				this.unavailableBooks.removeAll(bookCollection);
			}
		}
	}

	public void clearUnavailableBooks() {
		synchronized (unavailableBooks) {
			unavailableBooks.clear();
		}
	}
}
