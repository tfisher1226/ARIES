package bookshop2.client.receiptService;

import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.Receipt;


@WebService(name = "receiptService", targetNamespace = "http://bookshop2")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ReceiptService {
	
	public String ID = "bookshop2.receiptService";
	
	public List<Receipt> getAllReceiptRecords();
	
	public Receipt getReceiptRecordById(Long id);
	
	public List<Receipt> getReceiptRecordsByPage(int pageIndex, int pageSize);
	
	public Long addReceiptRecord(Receipt receipt);
	
	public void saveReceiptRecord(Receipt receipt);
	
	public void removeAllReceiptRecords();
	
	public void removeReceiptRecord(Receipt receipt);
	
	public void importReceiptRecords();
	
}
