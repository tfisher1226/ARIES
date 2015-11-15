package bookshop2.client.invoiceService;

import java.util.List;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.Invoice;


@SuppressWarnings("serial")
public class InvoiceServiceInterceptor extends MessageInterceptor<InvoiceService> implements InvoiceService {
	
	@Override
	public List<Invoice> getAllInvoiceRecords() {
		try {
			log.info("#### [admin]: getAllInvoiceRecords() sending...");
			Message request = createMessage("getAllInvoiceRecords");
			Message response = getProxy().invoke(request);
		List<Invoice> result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Invoice getInvoiceRecordById(Long id) {
		try {
			log.info("#### [admin]: getInvoiceRecordById() sending...");
			Message request = createMessage("getInvoiceRecordById");
			request.addPart("id", id);
			Message response = getProxy().invoke(request);
		Invoice result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Invoice> getInvoiceRecordsByPage(int pageIndex, int pageSize) {
		try {
			log.info("#### [admin]: getInvoiceRecordsByPage() sending...");
			Message request = createMessage("getInvoiceRecordsByPage");
			request.addPart("pageIndex", pageIndex);
			request.addPart("pageSize", pageSize);
			Message response = getProxy().invoke(request);
		List<Invoice> result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addInvoiceRecord(Invoice invoice) {
		try {
			log.info("#### [admin]: addInvoiceRecord() sending...");
			Message request = createMessage("addInvoiceRecord");
			request.addPart("invoice", invoice);
			Message response = getProxy().invoke(request);
		Long result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveInvoiceRecord(Invoice invoice) {
		try {
			log.info("#### [admin]: saveInvoiceRecord() sending...");
			Message request = createMessage("saveInvoiceRecord");
			request.addPart("invoice", invoice);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllInvoiceRecords() {
		try {
			log.info("#### [admin]: removeAllInvoiceRecords() sending...");
			Message request = createMessage("removeAllInvoiceRecords");
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeInvoiceRecord(Invoice invoice) {
		try {
			log.info("#### [admin]: removeInvoiceRecord() sending...");
			Message request = createMessage("removeInvoiceRecord");
			request.addPart("invoice", invoice);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void importInvoiceRecords() {
		try {
			log.info("#### [admin]: importInvoiceRecords() sending...");
			Message request = createMessage("importInvoiceRecords");
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
