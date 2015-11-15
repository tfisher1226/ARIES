package bookshop2.supplier.incoming.queryBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.QueryRequestMessage;


@WebService(name = "queryBooks", targetNamespace = "http://bookshop2/supplier")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface QueryBooks {
	
	public String ID = "bookshop2.supplier.queryBooks";
	
	public void queryBooks(QueryRequestMessage queryRequestMessage);
	
}
