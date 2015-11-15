package bookshop2.client.paymentService;

import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.Payment;


@WebService(name = "paymentService", targetNamespace = "http://bookshop2")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface PaymentService {
	
	public String ID = "bookshop2.paymentService";
	
	public List<Payment> getAllPaymentRecords();
	
	public Payment getPaymentRecordById(Long id);
	
	public List<Payment> getPaymentRecordsByPage(int pageIndex, int pageSize);
	
	public Long addPaymentRecord(Payment payment);
	
	public void savePaymentRecord(Payment payment);
	
	public void removeAllPaymentRecords();
	
	public void removePaymentRecord(Payment payment);
	
	public void importPaymentRecords();
	
}
