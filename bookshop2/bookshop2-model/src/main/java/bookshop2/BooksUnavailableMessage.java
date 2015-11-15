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
@XmlType(name = "BooksUnavailableMessage", namespace = "http://bookshop2", propOrder = {
	"books"
})
@XmlRootElement(name = "booksUnavailableMessage", namespace = "http://bookshop2")
public class BooksUnavailableMessage extends AbstractMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "books", namespace = "http://bookshop2", required = true)
	private Set<Book> books;
	
	
	public BooksUnavailableMessage() {
		books = new HashSet<Book>();
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
}
