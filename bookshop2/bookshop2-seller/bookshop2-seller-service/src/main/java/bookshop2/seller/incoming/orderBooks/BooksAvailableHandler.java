package bookshop2.seller.incoming.orderBooks;

import org.aries.tx.Transactional;

import bookshop2.BooksAvailableMessage;


public interface BooksAvailableHandler extends Transactional {
	
	public void booksAvailable(BooksAvailableMessage booksAvailableMessage);
	
}
