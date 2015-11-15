package bookshop2.supplier.incoming.queryBooks;

import org.aries.tx.Transactional;

import bookshop2.QueryRequestMessage;


public interface QueryBooksHandler extends Transactional {
	
	public void queryBooks(QueryRequestMessage queryRequestMessage);
	
}
