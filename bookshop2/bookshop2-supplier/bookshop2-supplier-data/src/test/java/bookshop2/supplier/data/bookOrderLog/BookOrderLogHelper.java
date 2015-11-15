package bookshop2.supplier.data.bookOrderLog;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transaction;

import org.aries.Assert;
import org.aries.common.PersonName;
import org.aries.common.dao.PersonNameDao;
import org.aries.common.dao.PersonNameDaoImpl;
import org.aries.common.entity.PersonNameEntity;
import org.aries.common.map.PersonNameMapper;
import org.aries.common.util.CommonEntityFixture;
import org.aries.common.util.CommonFixture;
import org.aries.common.util.CommonMapperFixture;
import org.aries.tx.DataLayerTestControl;
import org.aries.tx.DataModuleTestControl;
import org.aries.tx.TransactionTestControl;

import com.arjuna.ats.arjuna.coordinator.BasicAction;
import com.arjuna.ats.internal.arjuna.thread.ThreadActionData;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.Order;
import bookshop2.OrderCriteria;
import bookshop2.supplier.dao.BookDao;
import bookshop2.supplier.dao.BookDaoImpl;
import bookshop2.supplier.dao.OrderDao;
import bookshop2.supplier.dao.OrderDaoImpl;
import bookshop2.supplier.entity.BookOrdersBookEntity;
import bookshop2.supplier.entity.BookOrdersEntity;
import bookshop2.supplier.map.BookOrdersBookMapper;
import bookshop2.supplier.map.BookOrdersMapper;
import bookshop2.supplier.util.SupplierEntityFixture;
import bookshop2.supplier.util.SupplierMapperFixture;
import bookshop2.util.Bookshop2Fixture;
import common.jmx.client.JmxManager;
import common.jmx.client.JmxProxy;


@Stateful
@ConcurrencyManagement(BEAN)
@TransactionAttribute(REQUIRES_NEW)
public class BookOrderLogHelper {

	private static EntityManagerFactory entityManagerFactory;
	
	private TransactionTestControl transactionControl;
	
	private DataLayerTestControl bookOrderLogControl;
	
	@Inject
	private OrderDao<BookOrdersEntity> bookOrdersDao;
	
	private PersonNameDao<PersonNameEntity> personNameDao;
	
	private BookDao<BookOrdersBookEntity> bookOrdersBookDao;
	
	private EntityManager entityManager;
	
	private boolean runningInContainer;

	private JmxProxy jmxProxy;
	
	
	public BookOrderLogHelper() {
		jmxProxy = new JmxProxy();
	}

	
	public void setJmxProxy(JmxProxy jmxProxy) {
		this.jmxProxy = jmxProxy;
	}
	
	public void setJmxManager(JmxManager jmxManager) {
		jmxProxy.setJmxManager(jmxManager);
	}
	
//	protected void clearContext() throws Exception {
//		clearContext(BookOrderLogManager.MBEAN_NAME);
//	}
	
	public void clearContext(String mbeanName) throws Exception {
		jmxProxy.call(mbeanName, "clearContext");
	}

	public <T> void refreshContext(T entity) throws Exception {
		entityManager.refresh(entity);
	}
	
	public <T> void refreshContext(List<T> entityList) throws Exception {
		Iterator<T> iterator = entityList.iterator();
		while (iterator.hasNext()) {
			T entity = iterator.next();
			entityManager.refresh(entity);
		}
	}
	
	public void initialize(TransactionTestControl transactionControl) throws Exception {
		this.transactionControl = transactionControl;
		assureEntityManagerFactory();
		runningInContainer = true;
		assureEntityManager();
		//createDaoInstances();
		bookOrdersDao.initialize("BookOrders");
		bookOrdersDao.setEntityManager(entityManager);
		bookOrdersDao.setEntityClass(BookOrdersEntity.class);
	}
	
	public void initialize(DataLayerTestControl bookOrderLogControl) throws Exception {
		this.transactionControl = bookOrderLogControl.getTransactionTestControl();
		this.bookOrderLogControl = bookOrderLogControl;
		assureEntityManager();
		createDaoInstances();
	}
	
	public void initialize(DataModuleTestControl bookOrderLogControl) throws Exception {
		this.transactionControl = bookOrderLogControl.getTransactionTestControl();
		this.bookOrderLogControl = bookOrderLogControl;
		runningInContainer = true;
		createDaoInstances();
	}
	
	public void initializeAsClient(DataLayerTestControl bookOrderLogControl) throws Exception {
		this.transactionControl = bookOrderLogControl.getTransactionTestControl();
		this.bookOrderLogControl = bookOrderLogControl;
		entityManager = bookOrderLogControl.setupEntityManager();
		createDaoInstances();
	}
	
	public void assureEntityManagerFactory() throws Exception {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("bookOrderLog");
		}
	}
	
	public void resetEntityManagerFactory() throws Exception {
		entityManagerFactory = null;
		assureEntityManagerFactory();
	}
	
	public void assureEntityManager() throws Exception {
		if (entityManager == null) {
			if (runningInContainer) {
				entityManager = entityManagerFactory.createEntityManager();
			} else {
				entityManager = bookOrderLogControl.createEntityManager();
			}
		}
	}
	
	protected void createDaoInstances() throws Exception {
		bookOrdersDao = createBookOrdersDao();
		personNameDao = createPersonNameDao();
		bookOrdersBookDao = createBookOrdersBookDao();
	}
	
	public OrderDao<BookOrdersEntity> createBookOrdersDao() {
		OrderDao<BookOrdersEntity> dao = new OrderDaoImpl<BookOrdersEntity>();
		dao.setEntityClass(BookOrdersEntity.class);
		dao.setEntityManager(entityManager);
		dao.initialize("BookOrders");
		return dao;
	}
	
	public PersonNameDao<PersonNameEntity> createPersonNameDao() {
		PersonNameDao<PersonNameEntity> dao = new PersonNameDaoImpl<PersonNameEntity>();
		dao.setEntityClass(PersonNameEntity.class);
		dao.setEntityManager(entityManager);
		dao.initialize("PersonName");
		return dao;
	}
	
	public BookDao<BookOrdersBookEntity> createBookOrdersBookDao() {
		BookDao<BookOrdersBookEntity> dao = new BookDaoImpl<BookOrdersBookEntity>();
		dao.setEntityClass(BookOrdersBookEntity.class);
		dao.setEntityManager(entityManager);
		dao.initialize("BookOrdersBook");
		return dao;
	}
	
	public BookOrdersMapper getBookOrdersMapper() {
		return SupplierMapperFixture.getBookOrdersMapper();
	}
	
	public BookOrdersEntity createBookOrdersEntity() {
		return SupplierEntityFixture.create_BookOrdersEntity();
	}
	
	public BookOrdersEntity createEmptyBookOrdersEntity() {
		return SupplierEntityFixture.createEmpty_BookOrdersEntity();
	}
	
	public List<BookOrdersEntity> createBookOrdersEntityList() {
		return SupplierEntityFixture.createList_BookOrdersEntity();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<Order> getAllBookOrders() throws Exception {
		preProcessExecution();
		List<BookOrdersEntity> entityList = bookOrdersDao.getAllOrderRecords();
		List<Order> orderList = getBookOrdersMapper().toModelList(entityList);
		postProcessExecution();
		return orderList;
	}
	
	@TransactionAttribute(REQUIRED)
	public List<Order> getAllBookOrdersIC() throws Exception {
		List<BookOrdersEntity> entityList = bookOrdersDao.getAllOrderRecords();
		List<Order> orderList = getBookOrdersMapper().toModelList(entityList);
		return orderList;
	}

	@TransactionAttribute(REQUIRES_NEW)
	public List<BookOrdersEntity> getAllBookOrdersRecords() throws Exception {
		preProcessExecution();
		List<BookOrdersEntity> entityList = bookOrdersDao.getAllOrderRecords();
		postProcessExecution();
		return entityList;
	}
	
	@TransactionAttribute(REQUIRED)
	public List<BookOrdersEntity> getAllBookOrdersRecordsIC() throws Exception {
		List<BookOrdersEntity> entityList = bookOrdersDao.getAllOrderRecords();
		return entityList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Order getBookOrdersById(Long id) throws Exception {
		preProcessExecution();
		BookOrdersEntity entity = bookOrdersDao.getOrderRecordById(id);
		Order order = getBookOrdersMapper().toModel(entity);
		postProcessExecution();
		return order;
	}
	
	@TransactionAttribute(REQUIRED)
	public Order getBookOrdersByIdIC(Long id) throws Exception {
		BookOrdersEntity entity = bookOrdersDao.getOrderRecordById(id);
		Order order = getBookOrdersMapper().toModel(entity);
		return order;
	}

	@TransactionAttribute(REQUIRES_NEW)
	public BookOrdersEntity getBookOrdersRecordById(Long id) throws Exception {
		preProcessExecution();
		BookOrdersEntity entity = bookOrdersDao.getOrderRecordById(id);
		refreshContext(entity);
		postProcessExecution();
		return entity;
	}
	
	@TransactionAttribute(REQUIRED)
	public BookOrdersEntity getBookOrdersRecordByIdIC(Long id) throws Exception {
		BookOrdersEntity entity = bookOrdersDao.getOrderRecordById(id);
		refreshContext(entity);
		return entity;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<Order> getBookOrdersByIds(List<Long> ids) throws Exception {
		preProcessExecution();
		OrderCriteria orderCriteria = new OrderCriteria();
		orderCriteria.setIdList(ids);
		List<BookOrdersEntity> entityList = bookOrdersDao.getOrderRecordsByCriteria(orderCriteria);
		List<Order> orderList = getBookOrdersMapper().toModelList(entityList);
		postProcessExecution();
		return orderList;
	}
	
	@TransactionAttribute(REQUIRED)
	public List<Order> getBookOrdersByIdsIC(List<Long> ids) throws Exception {
		OrderCriteria orderCriteria = new OrderCriteria();
		orderCriteria.setIdList(ids);
		List<BookOrdersEntity> entityList = bookOrdersDao.getOrderRecordsByCriteria(orderCriteria);
		List<Order> orderList = getBookOrdersMapper().toModelList(entityList);
		return orderList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<BookOrdersEntity> getBookOrdersRecordsByIds(List<Long> ids) throws Exception {
		preProcessExecution();
		OrderCriteria orderCriteria = new OrderCriteria();
		orderCriteria.setIdList(ids);
		List<BookOrdersEntity> entityList = bookOrdersDao.getOrderRecordsByCriteria(orderCriteria);
		refreshContext(entityList);
		postProcessExecution();
		return entityList;
	}
	
	@TransactionAttribute(REQUIRED)
	public List<BookOrdersEntity> getBookOrdersRecordsByIdsIC(List<Long> ids) throws Exception {
		OrderCriteria orderCriteria = new OrderCriteria();
		orderCriteria.setIdList(ids);
		List<BookOrdersEntity> entityList = bookOrdersDao.getOrderRecordsByCriteria(orderCriteria);
		refreshContext(entityList);
		return entityList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Order getBookOrdersByTrackingNumber(String trackingNumber) throws Exception {
		preProcessExecution();
		OrderCriteria orderCriteria = new OrderCriteria();
		orderCriteria.addToFieldMap("trackingNumber",  trackingNumber);
		BookOrdersEntity entity = bookOrdersDao.getOrderRecordByCriteria(orderCriteria);
		Order order = getBookOrdersMapper().toModel(entity);
		postProcessExecution();
		return order;
	}
	
	@TransactionAttribute(REQUIRED)
	public Order getBookOrdersByTrackingNumberIC(String trackingNumber) throws Exception {
		OrderCriteria orderCriteria = new OrderCriteria();
		orderCriteria.addToFieldMap("trackingNumber",  trackingNumber);
		BookOrdersEntity entity = bookOrdersDao.getOrderRecordByCriteria(orderCriteria);
		Order order = getBookOrdersMapper().toModel(entity);
		return order;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public BookOrdersEntity getBookOrdersRecordByTrackingNumber(String trackingNumber, Long id) throws Exception {
		preProcessExecution();
		OrderCriteria orderCriteria = new OrderCriteria();
		orderCriteria.addToFieldMap("trackingNumber",  trackingNumber);
		BookOrdersEntity entity = bookOrdersDao.getOrderRecordByCriteria(orderCriteria);
		postProcessExecution();
		return entity;
	}
	
	@TransactionAttribute(REQUIRED)
	public BookOrdersEntity getBookOrdersRecordByTrackingNumberIC(String trackingNumber, Long id) throws Exception {
		OrderCriteria orderCriteria = new OrderCriteria();
		orderCriteria.addToFieldMap("trackingNumber",  trackingNumber);
		BookOrdersEntity entity = bookOrdersDao.getOrderRecordByCriteria(orderCriteria);
		return entity;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Long addBookOrders() throws Exception {
		preProcessExecution();
		Order order = Bookshop2Fixture.create_Order();
		BookOrdersEntity entity = getBookOrdersMapper().toEntity(order);
		Transaction transaction = transactionControl.getTransaction();
		BasicAction currentAction = ThreadActionData.currentAction();
		Long id = bookOrdersDao.addOrderRecord(entity);
		transaction = transactionControl.getTransaction();
		currentAction = ThreadActionData.currentAction();
		postProcessExecution();
		return id;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Long addBookOrders(Order order) throws Exception {
		preProcessExecution();
		BookOrdersEntity entity = getBookOrdersMapper().toEntity(order);
		Long id = bookOrdersDao.addOrderRecord(entity);
		postProcessExecution();
		return id;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Long addBookOrdersRecord(BookOrdersEntity entity) throws Exception {
		preProcessExecution();
		Long id = bookOrdersDao.addOrderRecord(entity);
		postProcessExecution();
		return id;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<Long> addBookOrders(int count) throws Exception {
		preProcessExecution();
		List<Order> orderList = Bookshop2Fixture.createList_Order(count);
		List<BookOrdersEntity> entityList = getBookOrdersMapper().toEntityList(orderList);
		List<Long> idList = bookOrdersDao.addOrderRecords(entityList);
		postProcessExecution();
		return idList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<Long> addBookOrders(List<Order> orderList) throws Exception {
		preProcessExecution();
		List<BookOrdersEntity> entityList = getBookOrdersMapper().toEntityList(orderList);
		List<Long> idList = bookOrdersDao.addOrderRecords(entityList);
		postProcessExecution();
		return idList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<Long> addBookOrdersRecords(List<BookOrdersEntity> entityList) throws Exception {
		preProcessExecution();
		List<Long> idList = bookOrdersDao.addOrderRecords(entityList);
		postProcessExecution();
		return idList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeAllBookOrders() throws Exception {
		preProcessExecution();
		bookOrdersDao.removeAllOrderRecords();
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeBookOrders(Order order) throws Exception {
		preProcessExecution();
		BookOrdersEntity entity = getBookOrdersMapper().toEntity(order);
		bookOrdersDao.removeOrderRecord(entity);
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeBookOrdersRecord(BookOrdersEntity entity) throws Exception {
		preProcessExecution();
		bookOrdersDao.removeOrderRecord(entity);
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeBookOrders(List<Order> orderList) throws Exception {
		preProcessExecution();
		List<BookOrdersEntity> entityList = getBookOrdersMapper().toEntityList(orderList);
		bookOrdersDao.removeOrderRecords(entityList);
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeBookOrdersRecord(List<BookOrdersEntity> entityList) throws Exception {
		preProcessExecution();
		bookOrdersDao.removeOrderRecords(entityList);
		postProcessExecution();
	}
	
	public void verifyBookOrdersCount(int expectedCount) throws Exception {
		List<BookOrdersEntity> entityList = getAllBookOrdersRecords();
		Assert.notNull(entityList, "BookOrders results should not be null");
		Assert.equals(entityList.size(), expectedCount, "BookOrders record count not correct");
	}
	
	public void verifyBookOrdersDoNotExist() throws Exception {
		verifyBookOrdersCount(0);
	}
	
	public void verifyBookOrdersDoNotExist(BookOrdersEntity entity) throws Exception {
		verifyBookOrdersDoNotExist(entity.getId());
	}
	
	public void verifyBookOrdersDoNotExist(Long id) throws Exception {
		BookOrdersEntity entity = getBookOrdersRecordById(id);
		Assert.isNull(entity, "BookOrders record should not exist");
	}
	
	public void verifyBookOrdersExist(BookOrdersEntity entity) throws Exception {
		BookOrdersEntity currentEntity = getBookOrdersRecordById(entity.getId());
		Assert.notNull(currentEntity, "BookOrders record should exist");
		SupplierEntityFixture.assertSameBookOrdersEntity(currentEntity, entity, true);
	}
	
	public void verifyBookOrdersExist(Long id) throws Exception {
		BookOrdersEntity entity = getBookOrdersRecordById(id);
		Assert.notNull(entity, "BookOrders record should exist");
	}
	
	public void verifyBookOrdersExist(List<Long> ids) throws Exception {
		Collection<BookOrdersEntity> entityList = getBookOrdersRecordsByIds(ids);
		Assert.notNull(entityList, "BookOrders results should not be null");
		Assert.notEmpty(entityList, "BookOrders records should exist");
		Assert.equals(entityList.size(), ids.size(), "BookOrders record count should be correct");
	}
	
	public void verifyBookOrdersDoNotExist(List<Long> ids) throws Exception {
		Collection<BookOrdersEntity> entityList = getBookOrdersRecordsByIds(ids);
		Assert.notNull(entityList, "BookOrders results should not be null");
		Assert.isEmpty(entityList, "BookOrders records should not exist");
	}
	
	public PersonNameMapper getPersonNameMapper() {
		return CommonMapperFixture.getPersonNameMapper();
	}
	
	public PersonNameEntity createPersonNameEntity() {
		return CommonEntityFixture.create_PersonNameEntity();
	}
	
	public PersonNameEntity createEmptyPersonNameEntity() {
		return CommonEntityFixture.createEmpty_PersonNameEntity();
	}
	
	public List<PersonNameEntity> createPersonNameEntityList() {
		return CommonEntityFixture.createList_PersonNameEntity();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<PersonName> getAllPersonName() throws Exception {
		preProcessExecution();
		List<PersonNameEntity> entityList = personNameDao.getAllPersonNameRecords();
		List<PersonName> personNameList = getPersonNameMapper().toModelList(entityList);
		postProcessExecution();
		return personNameList;
	}
	
	@TransactionAttribute(REQUIRED)
	public List<PersonName> getAllPersonNameIC() throws Exception {
		List<PersonNameEntity> entityList = personNameDao.getAllPersonNameRecords();
		List<PersonName> personNameList = getPersonNameMapper().toModelList(entityList);
		return personNameList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<PersonNameEntity> getAllPersonNameRecords() throws Exception {
		preProcessExecution();
		List<PersonNameEntity> entityList = personNameDao.getAllPersonNameRecords();
		postProcessExecution();
		return entityList;
	}
	
	@TransactionAttribute(REQUIRED)
	public List<PersonNameEntity> getAllPersonNameRecordsIC() throws Exception {
		List<PersonNameEntity> entityList = personNameDao.getAllPersonNameRecords();
		return entityList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public PersonName getPersonNameById(Long id) throws Exception {
		preProcessExecution();
		PersonNameEntity entity = personNameDao.getPersonNameRecordById(id);
		PersonName personName =  getPersonNameMapper().toModel(entity);
		postProcessExecution();
		return personName;
	}
	
	@TransactionAttribute(REQUIRED)
	public PersonName getPersonNameByIdIC(Long id) throws Exception {
		PersonNameEntity entity = personNameDao.getPersonNameRecordById(id);
		PersonName personName = getPersonNameMapper().toModel(entity);
		return personName;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public PersonNameEntity getPersonNameRecordById(Long id) throws Exception {
		preProcessExecution();
		PersonNameEntity entity = personNameDao.getPersonNameRecordById(id);
		refreshContext(entity);
		postProcessExecution();
		return entity;
	}
	
	@TransactionAttribute(REQUIRED)
	public PersonNameEntity getPersonNameRecordByIdIC(Long id) throws Exception {
		PersonNameEntity entity = personNameDao.getPersonNameRecordById(id);
		refreshContext(entity);
		return entity;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Long addPersonName() throws Exception {
		preProcessExecution();
		PersonName personName = CommonFixture.create_PersonName();
		PersonNameEntity entity = getPersonNameMapper().toEntity(personName);
		Long id = personNameDao.addPersonNameRecord(entity);
		postProcessExecution();
		return id;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Long addPersonName(PersonName personName) throws Exception {
		preProcessExecution();
		PersonNameEntity entity = getPersonNameMapper().toEntity(personName);
		Long id = personNameDao.addPersonNameRecord(entity);
		postProcessExecution();
		return id;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Long addPersonNameRecord(PersonNameEntity entity) throws Exception {
		preProcessExecution();
		Long id = personNameDao.addPersonNameRecord(entity);
		postProcessExecution();
		return id;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<Long> addPersonName(List<PersonName> personNameList) throws Exception {
		preProcessExecution();
		List<PersonNameEntity> entityList = getPersonNameMapper().toEntityList(personNameList);
		List<Long> idList = personNameDao.addPersonNameRecords(entityList);
		postProcessExecution();
		return idList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<Long> addPersonNameRecords(List<PersonNameEntity> entityList) throws Exception {
		preProcessExecution();
		List<Long> idList = personNameDao.addPersonNameRecords(entityList);
		postProcessExecution();
		return idList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removePersonName(PersonName personName) throws Exception {
		preProcessExecution();
		PersonNameEntity entity = getPersonNameMapper().toEntity(personName);
		personNameDao.removePersonNameRecord(entity);
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removePersonNameRecord(PersonNameEntity entity) throws Exception {
		preProcessExecution();
		personNameDao.removePersonNameRecord(entity);
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removePersonName(List<PersonName> personNameList) throws Exception {
		preProcessExecution();
		List<PersonNameEntity> entityList = getPersonNameMapper().toEntityList(personNameList);
		personNameDao.removePersonNameRecords(entityList);
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removePersonNameRecord(List<PersonNameEntity> entityList) throws Exception {
		preProcessExecution();
		personNameDao.removePersonNameRecords(entityList);
		postProcessExecution();
	}
	
	public void verifyPersonNameCount(int expectedCount) throws Exception {
		List<PersonNameEntity> entityList = getAllPersonNameRecords();
		Assert.notNull(entityList, "PersonName results should not be null");
		Assert.equals(entityList.size(), expectedCount, "PersonName record count not correct");
	}
	
	public void verifyPersonNameDoNotExist() throws Exception {
		verifyPersonNameCount(0);
	}
	
	public void verifyPersonNameDoNotExist(PersonNameEntity entity) throws Exception {
		verifyPersonNameDoNotExist(entity.getId());
	}
	
	public void verifyPersonNameDoNotExist(Long id) throws Exception {
		PersonNameEntity entity = getPersonNameRecordById(id);
		Assert.isNull(entity, "PersonName record should not exist");
	}

	public void verifyPersonNameExist(PersonNameEntity entity) throws Exception {
		PersonNameEntity currentEntity = getPersonNameRecordById(entity.getId());
		Assert.notNull(currentEntity, "PersonName record should exist");
		CommonEntityFixture.assertSamePersonNameEntity(currentEntity, entity, true);
	}
	
	public void verifyPersonNameExist(Long id) throws Exception {
		PersonNameEntity entity = getPersonNameRecordById(id);
		Assert.notNull(entity, "PersonName record should exist");
	}

	public BookOrdersBookMapper getBookOrdersBookMapper() {
		return SupplierMapperFixture.getBookOrdersBookMapper();
	}
	
	public BookOrdersBookEntity createBookOrdersBookEntity(BookOrdersEntity bookOrdersEntity) {
		return SupplierEntityFixture.create_BookOrdersBookEntity(bookOrdersEntity);
	}
	
	public BookOrdersBookEntity createEmptyBookOrdersBookEntity() {
		return SupplierEntityFixture.createEmpty_BookOrdersBookEntity();
	}
	
	public List<BookOrdersBookEntity> createBookOrdersBookEntityList(BookOrdersEntity bookOrdersEntity) {
		return SupplierEntityFixture.createList_BookOrdersBookEntity(bookOrdersEntity);
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<Book> getAllBookOrdersBook() throws Exception {
		preProcessExecution();
		List<BookOrdersBookEntity> entityList = bookOrdersBookDao.getAllBookRecords();
		List<Book> bookList = getBookOrdersBookMapper().toModelList(entityList);
		postProcessExecution();
		return bookList;
	}
	
	@TransactionAttribute(REQUIRED)
	public List<Book> getAllBookOrdersBookIC() throws Exception {
		List<BookOrdersBookEntity> entityList = bookOrdersBookDao.getAllBookRecords();
		List<Book> bookList = getBookOrdersBookMapper().toModelList(entityList);
		return bookList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<BookOrdersBookEntity> getAllBookOrdersBookRecords() throws Exception {
		preProcessExecution();
		List<BookOrdersBookEntity> entityList = bookOrdersBookDao.getAllBookRecords();
		postProcessExecution();
		return entityList;
	}
	
	@TransactionAttribute(REQUIRED)
	public List<BookOrdersBookEntity> getAllBookOrdersBookRecordsIC() throws Exception {
		List<BookOrdersBookEntity> entityList = bookOrdersBookDao.getAllBookRecords();
		return entityList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Book getBookOrdersBookById(Long id) throws Exception {
		preProcessExecution();
		BookOrdersBookEntity entity = bookOrdersBookDao.getBookRecordById(id);
		Book book = getBookOrdersBookMapper().toModel(entity);
		postProcessExecution();
		return book;
	}
	
	@TransactionAttribute(REQUIRED)
	public Book getBookOrdersBookByIdIC(Long id) throws Exception {
		BookOrdersBookEntity entity = bookOrdersBookDao.getBookRecordById(id);
		Book book = getBookOrdersBookMapper().toModel(entity);
		return book;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public BookOrdersBookEntity getBookOrdersBookRecordById(Long id) throws Exception {
		preProcessExecution();
		BookOrdersBookEntity entity = bookOrdersBookDao.getBookRecordById(id);
		refreshContext(entity);
		postProcessExecution();
		return entity;
	}
	
	@TransactionAttribute(REQUIRED)
	public BookOrdersBookEntity getBookOrdersBookRecordByIdIC(Long id) throws Exception {
		BookOrdersBookEntity entity = bookOrdersBookDao.getBookRecordById(id);
		refreshContext(entity);
		return entity;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<Book> getBookOrdersBookByIds(List<Long> ids) throws Exception {
		preProcessExecution();
		BookCriteria bookCriteria = new BookCriteria();
		bookCriteria.setIdList(ids);
		List<BookOrdersBookEntity> entityList = bookOrdersBookDao.getBookRecordsByCriteria(bookCriteria);
		List<Book> bookList = getBookOrdersBookMapper().toModelList(entityList);
		postProcessExecution();
		return bookList;
	}
	
	@TransactionAttribute(REQUIRED)
	public List<Book> getBookOrdersBookByIdsIC(List<Long> ids) throws Exception {
		BookCriteria bookCriteria = new BookCriteria();
		bookCriteria.setIdList(ids);
		List<BookOrdersBookEntity> entityList = bookOrdersBookDao.getBookRecordsByCriteria(bookCriteria);
		List<Book> bookList = getBookOrdersBookMapper().toModelList(entityList);
		return bookList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<BookOrdersBookEntity> getBookOrdersBookRecordsByIds(List<Long> ids) throws Exception {
		preProcessExecution();
		BookCriteria bookCriteria = new BookCriteria();
		bookCriteria.setIdList(ids);
		List<BookOrdersBookEntity> entityList = bookOrdersBookDao.getBookRecordsByCriteria(bookCriteria);
		refreshContext(entityList);
		postProcessExecution();
		return entityList;
	}
	
	@TransactionAttribute(REQUIRED)
	public List<BookOrdersBookEntity> getBookOrdersBookRecordsByIdsIC(List<Long> ids) throws Exception {
		BookCriteria bookCriteria = new BookCriteria();
		bookCriteria.setIdList(ids);
		List<BookOrdersBookEntity> entityList = bookOrdersBookDao.getBookRecordsByCriteria(bookCriteria);
		refreshContext(entityList);
		return entityList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Long addBookOrdersBook() throws Exception {
		preProcessExecution();
		Book book = Bookshop2Fixture.create_Book();
		BookOrdersBookEntity entity = getBookOrdersBookMapper().toEntity(book);
		Long id = bookOrdersBookDao.addBookRecord(entity);
		postProcessExecution();
		return id;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Long addBookOrdersBook(Book book) throws Exception {
		preProcessExecution();
		BookOrdersBookEntity entity = getBookOrdersBookMapper().toEntity(book);
		Long id = bookOrdersBookDao.addBookRecord(entity);
		postProcessExecution();
		return id;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Long addBookOrdersBookRecord(BookOrdersBookEntity entity) throws Exception {
		preProcessExecution();
		Long id = bookOrdersBookDao.addBookRecord(entity);
		postProcessExecution();
		return id;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<Long> addBookOrdersBook(List<Book> bookList) throws Exception {
		preProcessExecution();
		List<BookOrdersBookEntity> entityList = getBookOrdersBookMapper().toEntityList(bookList);
		List<Long> idList = bookOrdersBookDao.addBookRecords(entityList);
		postProcessExecution();
		return idList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<Long> addBookOrdersBookRecords(List<BookOrdersBookEntity> entityList) throws Exception {
		preProcessExecution();
		List<Long> idList = bookOrdersBookDao.addBookRecords(entityList);
		postProcessExecution();
		return idList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeBookOrdersBook(Book book) throws Exception {
		preProcessExecution();
		BookOrdersBookEntity entity = getBookOrdersBookMapper().toEntity(book);
		bookOrdersBookDao.removeBookRecord(entity);
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeBookOrdersBookRecord(BookOrdersBookEntity entity) throws Exception {
		preProcessExecution();
		bookOrdersBookDao.removeBookRecord(entity);
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeBookOrdersBook(List<Book> bookList) throws Exception {
		preProcessExecution();
		List<BookOrdersBookEntity> entityList = getBookOrdersBookMapper().toEntityList(bookList);
		bookOrdersBookDao.removeBookRecords(entityList);
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeBookOrdersBookRecord(List<BookOrdersBookEntity> entityList) throws Exception {
		preProcessExecution();
		bookOrdersBookDao.removeBookRecords(entityList);
		postProcessExecution();
	}
	
	public void verifyBookOrdersBookCount(int expectedCount) throws Exception {
		List<BookOrdersBookEntity> entityList = getAllBookOrdersBookRecords();
		Assert.notNull(entityList, "BookOrdersBook results should not be null");
		Assert.equals(entityList.size(), expectedCount, "BookOrdersBook record count not correct");
	}
	
	public void verifyBookOrdersBookDoNotExist() throws Exception {
		verifyBookOrdersBookCount(0);
	}
	
	public void verifyBookOrdersBookDoNotExist(BookOrdersBookEntity entity) throws Exception {
		verifyBookOrdersBookDoNotExist(entity.getId());
	}
	
	public void verifyBookOrdersBookDoNotExist(Long id) throws Exception {
		BookOrdersBookEntity entity = getBookOrdersBookRecordById(id);
		Assert.isNull(entity, "BookOrdersBook record should not exist");
	}
	
	public void verifyBookOrdersBookExist(BookOrdersBookEntity entity) throws Exception {
		BookOrdersBookEntity currentEntity = getBookOrdersBookRecordById(entity.getId());
		Assert.notNull(currentEntity, "BookOrdersBook record should exist");
		SupplierEntityFixture.assertSameBookOrdersBookEntity(currentEntity, entity, true);
	}
	
	public void verifyBookOrdersBookExist(Long id) throws Exception {
		BookOrdersBookEntity entity = getBookOrdersBookRecordById(id);
		Assert.notNull(entity, "BookOrdersBook record should exist");
	}
	
	public void verifyBookOrdersBookExist(List<Long> ids) throws Exception {
		Collection<BookOrdersBookEntity> entityList = getBookOrdersBookRecordsByIds(ids);
		Assert.notNull(entityList, "BookOrdersBook results should not be null");
		Assert.notEmpty(entityList, "BookOrdersBook records should exist");
		Assert.equals(entityList.size(), ids.size(), "BookOrdersBook record count should be correct");
	}
	
	public void verifyBookOrdersBookDoNotExist(List<Long> ids) throws Exception {
		Collection<BookOrdersBookEntity> entityList = getBookOrdersBookRecordsByIds(ids);
		Assert.notNull(entityList, "BookOrdersBook results should not be null");
		Assert.isEmpty(entityList, "BookOrdersBook records should not exist");
	}
	
//	public OrderCriteria createOrderCriteria(List<Long> idList) {
//		OrderCriteria orderCriteria = new OrderCriteria();
//		orderCriteria.setIdList(idList);
//		return orderCriteria;
//	}


	public void assureRemoveAll() throws Exception {
		preProcessExecution();
		bookOrdersDao.removeAllOrderRecords();
		postProcessExecution();
	}
	
	public void assureDeleteBookOrders(Long id) throws Exception {
		BookOrdersEntity bookOrders = bookOrdersDao.getOrderRecordById(id);
		assureDeleteBookOrders(bookOrders);
	}

	public void assureDeleteBookOrders(BookOrdersEntity bookOrders) throws Exception {
		preProcessExecution();
		bookOrdersDao.removeOrderRecord(bookOrders);
		postProcessExecution();
		verifyBookOrdersNotExists(bookOrders);
		//closeEntityManager();
	}
	

	public void verifyBookOrdersExists(List<Long> idList) throws Exception {
		Iterator<Long> iterator = idList.iterator();
		while (iterator.hasNext()) {
			Long id = iterator.next();
			verifyBookOrdersExist(id);
		}
	}
	
	public void verifyBookOrdersNotExists(BookOrdersEntity bookOrders) throws Exception {
		verifyBookOrdersNotExists(bookOrders.getId());
	}

	public void verifyBookOrdersNotExists(Long id) throws Exception {
		//openEntityManager();
		BookOrdersEntity bookOrders = bookOrdersDao.getOrderRecordById(id);
		Assert.isNull(bookOrders, "BookOrders should not exist");
		//closeEntityManager();
	}

	/*
	 * OrderBook verification
	 */

	public void verifyOrderBookCount(int expectedCount) throws Exception {
		Query query = entityManager.createNativeQuery("select * from order_book");
		List<?> resultList = query.getResultList();
		verifyResultListCount("order_book", resultList, expectedCount);
	}

	public void verifyOrderBookCount(Long id, int expectedCount) throws Exception {
		Query query = entityManager.createNativeQuery("select * from order_book where order_id = :id");
		query.setParameter("id", id);
		List<?> resultList = query.getResultList();
		verifyResultListCount("order_book", resultList, expectedCount);
	}

	public <T> void verifyResultListCount(String tableName, Collection<T> resultList, int expectedCount) throws Exception {
		Assert.notNull(resultList, tableName+" results should not be null");
		Assert.equals(expectedCount, resultList.size(), tableName+" record count not correct");
	}

	
	
	protected void preProcessExecution() throws Exception {
		if (!runningInContainer) {
			if (transactionControl != null) {
				transactionControl.beginTransaction();
			}
		}
	}
	
	protected void postProcessExecution() throws Exception {
		postProcessExecution(true);
	}
	
	protected void postProcessExecution(boolean commitAndClose) throws Exception {
		if (!runningInContainer) {
			if (transactionControl != null && commitAndClose) {
				transactionControl.commitTransaction();
				transactionControl.clearTransactions();
			}
		}
	}


	
//	protected void verifyBookOrdersCount(int expectedCount) throws Exception {
//		List<BookOrdersEntity> records = BookOrdersDao.getAllBooksList();
//		verifyBookOrdersCount(records, expectedCount);
//	}
//
//	protected void verifyBookOrdersCount(List<BookOrdersEntity> records, int expectedCount) throws Exception {
//		Assert.notNull(records, "Result not found");
//		if (expectedCount > 0)
//			Assert.notNull(records, "BookOrders records should exist");
//		Assert.equals(records.size(), expectedCount, "BookOrders record count not correct");
//	}
//
//	protected void verifyBookOrdersExists(BookEntity BookOrders) throws Exception {
//		verifyBookOrdersExists(BookOrders.getId());
//	}
//
//	protected void verifyBookOrdersExists(Long id) throws Exception {
//		BookOrdersEntity BookOrders = BookOrdersDao.getBookById(id);
//		Assert.notNull(BookOrders, "BookOrders should exist");
//		Assert.equals(BookOrders.getId(), id, "Ids should be same");
//	}
//
//	protected void verifyBookOrdersNotExists(BookEntity BookOrders) throws Exception {
//		verifyBookOrdersNotExists(BookOrders.getId());
//	}
//
//	protected void verifyBookOrdersNotExists(Long id) throws Exception {
//		BookOrdersEntity BookOrders = BookOrdersDao.getBookById(id);
//		Assert.isNull(BookOrders, "BookOrders should not exist");
//	}
	
	
}
