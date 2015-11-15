package bookshop2.seller.incoming.orderBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.BooksUnavailableMessage;


@WebService(name = "booksUnavailable", targetNamespace = "http://bookshop2/seller")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface BooksUnavailable {
	
	public String ID = "bookshop2.seller.booksUnavailable";
	
	public void booksUnavailable(BooksUnavailableMessage booksUnavailableMessage);
	
}
