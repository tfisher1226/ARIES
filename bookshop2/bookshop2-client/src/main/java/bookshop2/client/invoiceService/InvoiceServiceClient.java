package bookshop2.client.invoiceService;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.Invoice;


public class InvoiceServiceClient extends AbstractDelegate implements InvoiceService {
	
	private static final Log log = LogFactory.getLog(InvoiceServiceClient.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2";
	}
	
	@Override
	public String getServiceId() {
		return InvoiceService.ID;
	}
	
	@SuppressWarnings("unchecked")
	public InvoiceService getProxy() throws Exception {
		return getProxy(InvoiceService.ID);
	}
	
	@Override
	public List<Invoice> getAllInvoiceRecords() {
		try {
			List<Invoice> result = getProxy().getAllInvoiceRecords();
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Invoice getInvoiceRecordById(Long id) {
		try {
			Invoice result = getProxy().getInvoiceRecordById(id);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Invoice> getInvoiceRecordsByPage(int pageIndex, int pageSize) {
		try {
			List<Invoice> result = getProxy().getInvoiceRecordsByPage(pageIndex, pageSize);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addInvoiceRecord(Invoice invoice) {
		try {
			Long result = getProxy().addInvoiceRecord(invoice);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveInvoiceRecord(Invoice invoice) {
		try {
			getProxy().saveInvoiceRecord(invoice);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllInvoiceRecords() {
		try {
			getProxy().removeAllInvoiceRecords();
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeInvoiceRecord(Invoice invoice) {
		try {
			getProxy().removeInvoiceRecord(invoice);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void importInvoiceRecords() {
		try {
			getProxy().importInvoiceRecords();
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
