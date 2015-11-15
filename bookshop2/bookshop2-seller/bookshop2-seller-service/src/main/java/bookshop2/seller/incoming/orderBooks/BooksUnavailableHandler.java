package bookshop2.seller.incoming.orderBooks;

import org.aries.tx.Transactional;

import bookshop2.BooksUnavailableMessage;


public interface BooksUnavailableHandler extends Transactional {
	
	public void booksUnavailable(BooksUnavailableMessage booksUnavailableMessage);
	
}
