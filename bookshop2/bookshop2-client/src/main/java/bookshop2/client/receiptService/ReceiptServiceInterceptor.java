package bookshop2.client.receiptService;

import java.util.List;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.Receipt;


@SuppressWarnings("serial")
public class ReceiptServiceInterceptor extends MessageInterceptor<ReceiptService> implements ReceiptService {
	
	@Override
	public List<Receipt> getAllReceiptRecords() {
		try {
			log.info("#### [admin]: getAllReceiptRecords() sending...");
			Message request = createMessage("getAllReceiptRecords");
			Message response = getProxy().invoke(request);
		List<Receipt> result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Receipt getReceiptRecordById(Long id) {
		try {
			log.info("#### [admin]: getReceiptRecordById() sending...");
			Message request = createMessage("getReceiptRecordById");
			request.addPart("id", id);
			Message response = getProxy().invoke(request);
		Receipt result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Receipt> getReceiptRecordsByPage(int pageIndex, int pageSize) {
		try {
			log.info("#### [admin]: getReceiptRecordsByPage() sending...");
			Message request = createMessage("getReceiptRecordsByPage");
			request.addPart("pageIndex", pageIndex);
			request.addPart("pageSize", pageSize);
			Message response = getProxy().invoke(request);
		List<Receipt> result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addReceiptRecord(Receipt receipt) {
		try {
			log.info("#### [admin]: addReceiptRecord() sending...");
			Message request = createMessage("addReceiptRecord");
			request.addPart("receipt", receipt);
			Message response = getProxy().invoke(request);
		Long result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveReceiptRecord(Receipt receipt) {
		try {
			log.info("#### [admin]: saveReceiptRecord() sending...");
			Message request = createMessage("saveReceiptRecord");
			request.addPart("receipt", receipt);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllReceiptRecords() {
		try {
			log.info("#### [admin]: removeAllReceiptRecords() sending...");
			Message request = createMessage("removeAllReceiptRecords");
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeReceiptRecord(Receipt receipt) {
		try {
			log.info("#### [admin]: removeReceiptRecord() sending...");
			Message request = createMessage("removeReceiptRecord");
			request.addPart("receipt", receipt);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void importReceiptRecords() {
		try {
			log.info("#### [admin]: importReceiptRecords() sending...");
			Message request = createMessage("importReceiptRecords");
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
