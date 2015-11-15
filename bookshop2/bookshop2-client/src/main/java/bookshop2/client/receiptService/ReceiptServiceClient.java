package bookshop2.client.receiptService;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.Receipt;


public class ReceiptServiceClient extends AbstractDelegate implements ReceiptService {
	
	private static final Log log = LogFactory.getLog(ReceiptServiceClient.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2";
	}
	
	@Override
	public String getServiceId() {
		return ReceiptService.ID;
	}
	
	@SuppressWarnings("unchecked")
	public ReceiptService getProxy() throws Exception {
		return getProxy(ReceiptService.ID);
	}
	
	@Override
	public List<Receipt> getAllReceiptRecords() {
		try {
			List<Receipt> result = getProxy().getAllReceiptRecords();
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Receipt getReceiptRecordById(Long id) {
		try {
			Receipt result = getProxy().getReceiptRecordById(id);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Receipt> getReceiptRecordsByPage(int pageIndex, int pageSize) {
		try {
			List<Receipt> result = getProxy().getReceiptRecordsByPage(pageIndex, pageSize);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addReceiptRecord(Receipt receipt) {
		try {
			Long result = getProxy().addReceiptRecord(receipt);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveReceiptRecord(Receipt receipt) {
		try {
			getProxy().saveReceiptRecord(receipt);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllReceiptRecords() {
		try {
			getProxy().removeAllReceiptRecords();
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeReceiptRecord(Receipt receipt) {
		try {
			getProxy().removeReceiptRecord(receipt);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void importReceiptRecords() {
		try {
			getProxy().importReceiptRecords();
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
