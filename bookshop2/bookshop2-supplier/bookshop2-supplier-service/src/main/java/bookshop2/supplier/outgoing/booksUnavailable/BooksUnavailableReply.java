package bookshop2.supplier.outgoing.booksUnavailable;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.BooksUnavailableMessage;


@WebService(name = "booksUnavailableReply", targetNamespace = "http://bookshop2/supplier")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface BooksUnavailableReply {
	
	public String ID = "bookshop2.supplier.booksUnavailableReply";

	public void booksUnavailable(BooksUnavailableMessage booksUnavailableMessage);

}
