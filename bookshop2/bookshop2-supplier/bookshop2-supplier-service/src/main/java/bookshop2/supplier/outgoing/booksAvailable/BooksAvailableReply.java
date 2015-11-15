package bookshop2.supplier.outgoing.booksAvailable;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.BooksAvailableMessage;


@WebService(name = "booksAvailableReply", targetNamespace = "http://bookshop2/supplier")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface BooksAvailableReply {
	
	public String ID = "bookshop2.supplier.booksAvailableReply";

	public void booksAvailable(BooksAvailableMessage booksAvailableMessage);

}
