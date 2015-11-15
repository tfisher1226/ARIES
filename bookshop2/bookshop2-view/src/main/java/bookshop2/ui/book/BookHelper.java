package bookshop2.ui.book;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import bookshop2.Book;
import bookshop2.util.BookUtil;


@SessionScoped
@Named("bookHelper")
public class BookHelper extends AbstractElementHelper<Book> implements Serializable {

	@Override
	public boolean isEmpty(Book book) {
		return BookUtil.isEmpty(book);
	}
	
	@Override
	public String toString(Book book) {
		return book.getTitle()+" "+book.getAuthor();
	}
	
	@Override
	public String toString(Collection<Book> bookList) {
		return BookUtil.toString(bookList);
	}
	
	@Override
	public boolean validate(Book book) {
		return BookUtil.validate(book);
	}

	@Override
	public boolean validate(Collection<Book> bookList) {
		return BookUtil.validate(bookList);
	}

}
