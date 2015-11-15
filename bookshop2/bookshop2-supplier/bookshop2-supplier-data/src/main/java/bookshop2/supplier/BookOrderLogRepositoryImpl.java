package bookshop2.supplier;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.inject.Inject;

import org.aries.Assert;
import org.aries.data.AbstractRepository;
import org.aries.util.ExceptionUtil;

import bookshop2.Order;
import bookshop2.OrderCriteria;
import bookshop2.supplier.bean.BookOrdersManager;


@Stateful
@Local(BookOrderLogRepository.class)
public class BookOrderLogRepositoryImpl extends AbstractRepository implements BookOrderLogRepository {
	
	@Inject
	protected BookOrdersManager bookOrdersManager;
	
	
	public BookOrdersManager getBookOrdersManager() {
		return bookOrdersManager;
	}
	
	public void setBookOrdersManager(BookOrdersManager bookOrdersManager) {
		this.bookOrdersManager = bookOrdersManager;
	}
	
	@Override
	public void clearContext() {
		try {
			bookOrdersManager.clearContext();
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Order> getAllBookOrdersRecords() {
		try {
			List<Order> records = bookOrdersManager.getAllBookOrdersRecords();
			Assert.notNull(records, "BookOrders record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Order getBookOrdersRecord(Long id) {
		try {
			Assert.notNull(id, "Id must be specified");
			Order record = bookOrdersManager.getBookOrdersRecord(id);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Order getBookOrdersRecordByTrackingNumber(String trackingNumber) {
		try {
			Assert.notNull(trackingNumber, "TrackingNumber must be specified");
			Order record = bookOrdersManager.getBookOrdersRecordByTrackingNumber(trackingNumber);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Order> getBookOrdersRecords(int pageIndex, int pageSize) {
		try {
			Assert.notNull(pageIndex, "Page-index must be specified");
			Assert.notNull(pageSize, "Page-size must be specified");
			List<Order> records = bookOrdersManager.getBookOrdersRecords(pageIndex, pageSize);
			Assert.notNull(records, "BookOrders record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Order> getBookOrdersRecords(OrderCriteria orderCriteria) {
		try {
			Assert.notNull(orderCriteria, "Order record criteria must be specified");
			List<Order> records = bookOrdersManager.getBookOrdersRecords(orderCriteria);
			Assert.notNull(records, "BookOrders record list null");
			return records;
			
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Long addBookOrdersRecord(Order order) {
		try {
			Assert.notNull(order, "BookOrders record must be specified");
			Long id = bookOrdersManager.addBookOrdersRecord(order);
			Assert.notNull(id, "Id must exist");
			return id;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Long> addBookOrdersRecords(Collection<Order> orderList) {
		try {
			Assert.notNull(orderList, "BookOrders record list must be specified");
			List<Long> idList = bookOrdersManager.addBookOrdersRecords(orderList);
			Assert.notNull(idList, "Id list must exist");
			Assert.isTrue(idList.size() == orderList.size(), "Id count must be correct");
			return idList;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void saveBookOrdersRecord(Order order) {
		try {
			Assert.notNull(order, "BookOrders record must be specified");
			bookOrdersManager.saveBookOrdersRecord(order);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void saveBookOrdersRecords(Collection<Order> orderList) {
		try {
			Assert.notNull(orderList, "BookOrders record list must be specified");
			bookOrdersManager.saveBookOrdersRecords(orderList);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeAllBookOrdersRecords() {
		try {
			bookOrdersManager.removeAllBookOrdersRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeBookOrdersRecord(Order order) {
		try {
			Assert.notNull(order, "BookOrders record must be specified");
			bookOrdersManager.removeBookOrdersRecord(order);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeBookOrdersRecord(Long id) {
		try {
			Assert.notNull(id, "BookOrders record ID must be specified");
			bookOrdersManager.removeBookOrdersRecord(id);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeBookOrdersRecords(Collection<Order> orderList) {
		try {
			Assert.notNull(orderList, "BookOrders record list must be specified");
			bookOrdersManager.removeBookOrdersRecords(orderList);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeBookOrdersRecords(OrderCriteria orderCriteria) {
		try {
			Assert.notNull(orderCriteria, "Order record criteria must be specified");
			bookOrdersManager.removeBookOrdersRecords(orderCriteria);
			
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void importBookOrdersRecords() {
		try {
			bookOrdersManager.importBookOrdersRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
}
