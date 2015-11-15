package bookshop2.ui.book;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import bookshop2.Book;
import bookshop2.util.BookUtil;


public class BookListObject extends AbstractListObject<Book> implements Comparable<BookListObject>, Serializable {
	
	private Book book;
	
	
	public BookListObject(Book book) {
		this.book = book;
	}

	
	public Book getBook() {
		return book;
	}
	
	@Override
	public String getLabel() {
		return toString(book);
	}
	
	@Override
	public String toString() {
		return toString(book);
	}
	
	@Override
	public String toString(Book book) {
		return BookUtil.toString(book);
	}
	
	@Override
	public int compareTo(BookListObject other) {
		String thisText = toString(this.book);
		String otherText = toString(other.book);
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		BookListObject other = (BookListObject) object;
		Long thisId = this.book.getId();
		Long otherId = other.book.getId();
		if (thisId == null)
			return false;
		if (otherId == null)
			return false;
		return thisId.equals(otherId);
	}

}
