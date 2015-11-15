package bookshop2.seller.incoming.orderBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.BooksAvailableMessage;


@WebService(name = "booksAvailable", targetNamespace = "http://bookshop2/seller")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface BooksAvailable {
	
	public String ID = "bookshop2.seller.booksAvailable";
	
	public void booksAvailable(BooksAvailableMessage booksAvailableMessage);
	
}
