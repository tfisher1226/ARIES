package bookshop2.client.invoiceService;

import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.Invoice;


@WebService(name = "invoiceService", targetNamespace = "http://bookshop2")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface InvoiceService {
	
	public String ID = "bookshop2.invoiceService";
	
	public List<Invoice> getAllInvoiceRecords();
	
	public Invoice getInvoiceRecordById(Long id);
	
	public List<Invoice> getInvoiceRecordsByPage(int pageIndex, int pageSize);
	
	public Long addInvoiceRecord(Invoice invoice);
	
	public void saveInvoiceRecord(Invoice invoice);
	
	public void removeAllInvoiceRecords();
	
	public void removeInvoiceRecord(Invoice invoice);
	
	public void importInvoiceRecords();
	
}
