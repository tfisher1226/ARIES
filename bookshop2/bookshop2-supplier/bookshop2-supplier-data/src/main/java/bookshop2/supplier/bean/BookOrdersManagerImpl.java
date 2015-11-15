package bookshop2.supplier.bean;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.persistence.PersistenceContextType.EXTENDED;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionSynchronizationRegistry;

import org.aries.Assert;

import bookshop2.Order;
import bookshop2.OrderCriteria;
import bookshop2.supplier.dao.OrderDao;
import bookshop2.supplier.dao.OrderDaoImpl;
import bookshop2.supplier.entity.BookOrdersEntity;
import bookshop2.supplier.map.BookOrdersMapper;
import bookshop2.supplier.process.OrderImporter;


@Stateful
@Local(BookOrdersManager.class)
@ConcurrencyManagement(BEAN)
public class BookOrdersManagerImpl implements BookOrdersManager {
	
	@PersistenceContext(unitName = "bookOrderLog", type = EXTENDED)
	protected EntityManager entityManager;
	
	@Inject
	protected OrderImporter bookOrdersImporter;
	
	@Inject
	protected BookOrdersMapper bookOrdersMapper;
	
	@Inject
	@New(OrderDaoImpl.class)
	protected OrderDao<BookOrdersEntity> bookOrdersDao;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public void clearContext() {
		entityManager.clear();
	}
	
	@PostConstruct
	public void initialize() {
		bookOrdersDao.initialize("BookOrders");
		bookOrdersDao.setEntityManager(entityManager);
		bookOrdersDao.setEntityClass(BookOrdersEntity.class);
	}
	
	@Override
	public List<Order> getAllBookOrdersRecords() {
		List<BookOrdersEntity> entityList = bookOrdersDao.getAllOrderRecords();
		List<Order> modelList = bookOrdersMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public Order getBookOrdersRecord(Long id) {
		Assert.notNull(id, "ID must be specified");
		BookOrdersEntity entity = bookOrdersDao.getOrderRecordById(id);
		Order model = bookOrdersMapper.toModel(entity);
		return model;
	}
	
	@Override
	public Order getBookOrdersRecordByTrackingNumber(String trackingNumber) {
		BookOrdersEntity entity = bookOrdersDao.getOrderRecordByTrackingNumber(trackingNumber);
		Order model = bookOrdersMapper.toModel(entity);
		return model;
	}
	
	@Override
	public List<Order> getBookOrdersRecords(int pageIndex, int pageSize) {
		Assert.notNull(pageIndex, "Page-index must be specified");
		Assert.notNull(pageSize, "Page-size must be specified");
		List<BookOrdersEntity> entityList = bookOrdersDao.getOrderRecordsByPage(pageIndex, pageSize);
		List<Order> modelList = bookOrdersMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public List<Order> getBookOrdersRecords(OrderCriteria orderCriteria) {
		Assert.notNull(orderCriteria, "Order record criteria must be specified");
		List<BookOrdersEntity> entityList = bookOrdersDao.getOrderRecordsByCriteria(orderCriteria);
		List<Order> modelList = bookOrdersMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public Long addBookOrdersRecord(Order order) {
		Assert.notNull(order, "Order record must be specified");
		BookOrdersEntity entity = bookOrdersMapper.toEntity(order);
		Long id = bookOrdersDao.addOrderRecord(entity);
		Assert.notNull(id, "Id must exist");
		return id;
	}
	
	@Override
	public List<Long> addBookOrdersRecords(Collection<Order> orderList) {
		Assert.notNull(orderList, "Order record list must be specified");
		List<BookOrdersEntity> entityList = bookOrdersMapper.toEntityList(orderList);
		List<Long> idList = bookOrdersDao.addOrderRecords(entityList);
		Assert.notNull(idList, "IdList must exist");
		Assert.equals(idList.size(), orderList.size(), "Id count not correct");
		return idList;
	}
	
	@Override
	public void saveBookOrdersRecord(Order order) {
		Assert.notNull(order, "Order record must be specified");
		BookOrdersEntity entity = bookOrdersMapper.toEntity(order);
		bookOrdersDao.saveOrderRecord(entity);
	}
	
	@Override
	public void saveBookOrdersRecords(Collection<Order> orderList) {
		Assert.notNull(orderList, "Order record list must be specified");
		List<BookOrdersEntity> entityList = bookOrdersMapper.toEntityList(orderList);
		bookOrdersDao.saveOrderRecords(entityList);
	}
	
	@Override
	public void removeAllBookOrdersRecords() {
		bookOrdersDao.removeAllOrderRecords();
	}
	
	@Override
	public void removeBookOrdersRecord(Order order) {
		Assert.notNull(order, "Order record must be specified");
		BookOrdersEntity entity = bookOrdersMapper.toEntity(order);
		bookOrdersDao.removeOrderRecord(entity);
	}
	
	@Override
	public void removeBookOrdersRecord(Long id) {
		Assert.notNull(id, "Order record ID must be specified");
		BookOrdersEntity entity = bookOrdersDao.getOrderRecordById(id);
		bookOrdersDao.removeOrderRecord(entity);
	}
	
	@Override
	public void removeBookOrdersRecords(Collection<Order> orderList) {
		Assert.notNull(orderList, "Order record list must be specified");
		List<BookOrdersEntity> entityList = bookOrdersMapper.toEntityList(orderList);
		bookOrdersDao.removeOrderRecords(entityList);
	}
	
	@Override
	public void removeBookOrdersRecords(OrderCriteria orderCriteria) {
		Assert.notNull(orderCriteria, "Order record criteria must be specified");
		bookOrdersDao.removeOrderRecords(orderCriteria);
	}
	
	@Override
	public void importBookOrdersRecords() {
		bookOrdersImporter.importOrderRecords();
	}
	
}
