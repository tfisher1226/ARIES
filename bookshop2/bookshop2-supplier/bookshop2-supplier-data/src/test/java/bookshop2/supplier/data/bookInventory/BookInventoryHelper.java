package bookshop2.supplier.data.bookInventory;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.aries.Assert;
import org.aries.common.dao.Dao;
import org.aries.tx.DataLayerTestControl;
import org.aries.tx.DataModuleTestControl;
import org.aries.tx.TransactionTestControl;
import org.aries.util.FieldUtil;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.Order;
import bookshop2.supplier.dao.BookDao;
import bookshop2.supplier.dao.BookDaoImpl;
import bookshop2.supplier.entity.BookOrdersEntity;
import bookshop2.supplier.entity.ReservedBooksEntity;
import bookshop2.supplier.map.ReservedBooksMapper;
import bookshop2.supplier.util.SupplierEntityFixture;
import bookshop2.supplier.util.SupplierMapperFixture;
import bookshop2.util.Bookshop2Fixture;
import common.jmx.client.JmxManager;
import common.jmx.client.JmxProxy;


@Stateful
@ConcurrencyManagement(BEAN)
@TransactionAttribute(REQUIRES_NEW)
public class BookInventoryHelper {

	private static EntityManagerFactory entityManagerFactory;
	
	private TransactionTestControl transactionControl;
	
	private DataLayerTestControl bookInventoryControl;
	
	private BookDao<ReservedBooksEntity> reservedBooksDao;
	
	private EntityManager entityManager;
	
	private boolean runningInContainer;

	private JmxProxy jmxProxy;
	
	
	public BookInventoryHelper() {
		jmxProxy = new JmxProxy();
	}

	
	public void setJmxProxy(JmxProxy jmxProxy) {
		this.jmxProxy = jmxProxy;
	}
	
	public void setJmxManager(JmxManager jmxManager) {
		jmxProxy.setJmxManager(jmxManager);
	}
	
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
		createDaoInstances();
	}
	
	public void initialize(DataLayerTestControl bookInventoryControl) throws Exception {
		this.transactionControl = bookInventoryControl.getTransactionTestControl();
		this.bookInventoryControl = bookInventoryControl;
		assureEntityManager();
		createDaoInstances();
	}
	
	public void initialize(DataModuleTestControl bookInventoryControl) throws Exception {
		this.transactionControl = bookInventoryControl.getTransactionTestControl();
		this.bookInventoryControl = bookInventoryControl;
		runningInContainer = true;
		createDaoInstances();
	}
	
	public void initializeAsClient(DataLayerTestControl bookInventoryControl) throws Exception {
		this.transactionControl = bookInventoryControl.getTransactionTestControl();
		this.bookInventoryControl = bookInventoryControl;
		entityManager = bookInventoryControl.setupEntityManager();
		createDaoInstances();
	}
	
	public void assureEntityManagerFactory() throws Exception {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("bookInventory");
		}
	}
	
	public void resetEntityManagerFactory() throws Exception {
		entityManagerFactory = null;
		assureEntityManagerFactory();
	}
	
	public void assureEntityManager() throws Exception {
		if (entityManager == null) {
			if (!runningInContainer) {
				entityManager = bookInventoryControl.createEntityManager();
			} else {
				entityManager = entityManagerFactory.createEntityManager();
			}
		}
	}
	
	protected void createDaoInstances() throws Exception {
		reservedBooksDao = createReservedBooksDao();
	}
	
	public BookDao<ReservedBooksEntity> createReservedBooksDao() {
		BookDao<ReservedBooksEntity> dao = new BookDaoImpl<ReservedBooksEntity>();
		dao.setEntityClass(ReservedBooksEntity.class);
		dao.setEntityManager(entityManager);
		dao.initialize("ReservedBooks");
		return dao;
	}
	
	public ReservedBooksMapper getReservedBooksMapper() {
		return SupplierMapperFixture.getReservedBooksMapper();
	}
	
	public ReservedBooksEntity createReservedBooksEntity() {
		return SupplierEntityFixture.create_ReservedBooksEntity();
	}
	
	public ReservedBooksEntity createEmptyReservedBooksEntity() {
		return SupplierEntityFixture.createEmpty_ReservedBooksEntity();
	}
	
	public List<ReservedBooksEntity> createReservedBooksEntityList() {
		return SupplierEntityFixture.createList_ReservedBooksEntity();
	}
	
	/*
	 * BookOrders verification
	 */
	
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
	
	
	protected BookDao<ReservedBooksEntity> getReservedBooksDao() throws Exception {
		return reservedBooksDao;
	}
	
	public void setReservedBooksDao(BookDao<ReservedBooksEntity> reservedBooksDao) throws Exception {
		this.reservedBooksDao = reservedBooksDao;
	}

	public void setEntityManager(Dao dao, EntityManager entityManager) {
		FieldUtil.setFieldValue(dao, "entityManager", entityManager);
	}

	public void closeEntityManager() throws Exception { 
		if (!runningInContainer) {
			bookInventoryControl.closeEntityManager(entityManager);
		} else {
			entityManager.close();
			entityManager = null;
		}
	}
	
	/*
	 * ReservedBooks management
	 */
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<Book> getAllReservedBooks() throws Exception {
		preProcessExecution();
		List<Book> bookList = getAllReservedBooksIC();
		postProcessExecution();
		return bookList;
	}
	
	@TransactionAttribute(REQUIRED)
	public List<Book> getAllReservedBooksIC() throws Exception {
		List<ReservedBooksEntity> entityList = reservedBooksDao.getAllBookRecords();
		List<Book> bookList = getReservedBooksMapper().toModelList(entityList);
		return bookList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<ReservedBooksEntity> getAllReservedBooksRecords() throws Exception {
		preProcessExecution();
		List<ReservedBooksEntity> entityList = reservedBooksDao.getAllBookRecords();
		postProcessExecution();
		return entityList;
	}
	
	@TransactionAttribute(REQUIRED)
	public List<ReservedBooksEntity> getAllReservedBooksRecordsIC() throws Exception {
		List<ReservedBooksEntity> entityList = reservedBooksDao.getAllBookRecords();
		return entityList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Book getReservedBooksById(Long id) throws Exception {
		preProcessExecution();
		ReservedBooksEntity entity = reservedBooksDao.getBookRecordById(id);
		Book book = getReservedBooksMapper().toModel(entity);
		postProcessExecution();
		return book;
	}
	
	@TransactionAttribute(REQUIRED)
	public Book getReservedBooksByIdIC(Long id) throws Exception {
		ReservedBooksEntity entity = reservedBooksDao.getBookRecordById(id);
		Book book = getReservedBooksMapper().toModel(entity);
		return book;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public ReservedBooksEntity getReservedBooksRecordById(Long id) throws Exception {
		preProcessExecution();
		ReservedBooksEntity entity = reservedBooksDao.getBookRecordById(id);
		refreshContext(entity);
		postProcessExecution();
		return entity;
	}
	
	@TransactionAttribute(REQUIRED)
	public ReservedBooksEntity getReservedBooksRecordByIdIC(Long id) throws Exception {
		ReservedBooksEntity entity = reservedBooksDao.getBookRecordById(id);
		refreshContext(entity);
		return entity;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<Book> getReservedBooksByIds(List<Long> ids) throws Exception {
		preProcessExecution();
		BookCriteria bookCriteria = new BookCriteria();
		bookCriteria.setIdList(ids);
		List<ReservedBooksEntity> entityList = reservedBooksDao.getBookRecordsByCriteria(bookCriteria);
		List<Book> bookList = getReservedBooksMapper().toModelList(entityList);
		postProcessExecution();
		return bookList;
	}
	
	@TransactionAttribute(REQUIRED)
	public List<Book> getReservedBooksByIdsIC(List<Long> ids) throws Exception {
		BookCriteria bookCriteria = new BookCriteria();
		bookCriteria.setIdList(ids);
		List<ReservedBooksEntity> entityList = reservedBooksDao.getBookRecordsByCriteria(bookCriteria);
		List<Book> bookList = getReservedBooksMapper().toModelList(entityList);
		return bookList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<ReservedBooksEntity> getReservedBooksRecordsByIds(List<Long> ids) throws Exception {
		preProcessExecution();
		BookCriteria bookCriteria = new BookCriteria();
		bookCriteria.setIdList(ids);
		List<ReservedBooksEntity> entityList = reservedBooksDao.getBookRecordsByCriteria(bookCriteria);
		refreshContext(entityList);
		postProcessExecution();
		return entityList;
	}
	
	@TransactionAttribute(REQUIRED)
	public List<ReservedBooksEntity> getReservedBooksRecordsByIdsIC(List<Long> ids) throws Exception {
		BookCriteria bookCriteria = new BookCriteria();
		bookCriteria.setIdList(ids);
		List<ReservedBooksEntity> entityList = reservedBooksDao.getBookRecordsByCriteria(bookCriteria);
		refreshContext(entityList);
		return entityList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Book getReservedBooksByBarCode(String barCode) throws Exception {
		preProcessExecution();
		BookCriteria bookCriteria = new BookCriteria();
		bookCriteria.addToFieldMap("barCode",  barCode);
		ReservedBooksEntity entity = reservedBooksDao.getBookRecordByCriteria(bookCriteria);
		Book book = getReservedBooksMapper().toModel(entity);
		postProcessExecution();
		return book;
	}
	
	@TransactionAttribute(REQUIRED)
	public Book getReservedBooksByBarCodeIC(String barCode) throws Exception {
		BookCriteria bookCriteria = new BookCriteria();
		bookCriteria.addToFieldMap("barCode",  barCode);
		ReservedBooksEntity entity = reservedBooksDao.getBookRecordByCriteria(bookCriteria);
		Book book = getReservedBooksMapper().toModel(entity);
		return book;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Long addReservedBooks() throws Exception {
		preProcessExecution();
		Book book = Bookshop2Fixture.create_Book();
		ReservedBooksEntity entity = getReservedBooksMapper().toEntity(book);
		Long id = reservedBooksDao.addBookRecord(entity);
		postProcessExecution();
		return id;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Long addReservedBooks(Book book) throws Exception {
		preProcessExecution();
		ReservedBooksEntity entity = getReservedBooksMapper().toEntity(book);
		Long id = reservedBooksDao.addBookRecord(entity);
		postProcessExecution();
		return id;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Long addReservedBooksRecord() throws Exception {
		preProcessExecution();
		ReservedBooksEntity entity = createReservedBooksEntity();
		Long id = reservedBooksDao.addBookRecord(entity);
		postProcessExecution();
		return id;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public Long addReservedBooksRecord(ReservedBooksEntity entity) throws Exception {
		preProcessExecution();
		Long id = reservedBooksDao.addBookRecord(entity);
		postProcessExecution();
		return id;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<Long> addReservedBooks(int count) throws Exception {
		preProcessExecution();
		List<Book> bookList = Bookshop2Fixture.createList_Book(count);
		List<ReservedBooksEntity> entityList = getReservedBooksMapper().toEntityList(bookList);
		List<Long> idList = reservedBooksDao.addBookRecords(entityList);
		postProcessExecution();
		return idList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<Long> addReservedBooks(List<Book> bookList) throws Exception {
		preProcessExecution();
		List<ReservedBooksEntity> entityList = getReservedBooksMapper().toEntityList(bookList);
		List<Long> idList = reservedBooksDao.addBookRecords(entityList);
		postProcessExecution();
		return idList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<Long> addReservedBooksRecords() throws Exception {
		preProcessExecution();
		List<ReservedBooksEntity> entityList = createReservedBooksEntityList();
		List<Long> idList = reservedBooksDao.addBookRecords(entityList);
		postProcessExecution();
		return idList;
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public List<Long> addReservedBooksRecords(List<ReservedBooksEntity> entityList) throws Exception {
		preProcessExecution();
		List<Long> idList = reservedBooksDao.addBookRecords(entityList);
		postProcessExecution();
		return idList;
	}
	
	public void assureRemoveAll() throws Exception {
		preProcessExecution();
		reservedBooksDao.removeAllBookRecords();
		postProcessExecution();
	}

	public void assureDeleteReservedBooks(Long id) throws Exception {
		ReservedBooksEntity bookOrders = reservedBooksDao.getBookRecordById(id);
		assureDeleteReservedBooks(bookOrders);
	}

	public void assureDeleteReservedBooks(ReservedBooksEntity bookOrders) throws Exception {
		preProcessExecution();
		reservedBooksDao.removeBookRecord(bookOrders);
		postProcessExecution();
		verifyReservedBooksNotExists(bookOrders);
		//closeEntityManager();
	}

	@TransactionAttribute(REQUIRES_NEW)
	public void removeAllReservedBooks() throws Exception {
		preProcessExecution();
		reservedBooksDao.removeAllBookRecords();
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeReservedBooks(Book book) throws Exception {
		preProcessExecution();
		ReservedBooksEntity entity = getReservedBooksMapper().toEntity(book);
		reservedBooksDao.removeBookRecord(entity);
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeReservedBooksRecord(ReservedBooksEntity entity) throws Exception {
		preProcessExecution();
		reservedBooksDao.removeBookRecord(entity);
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeReservedBooks(List<Book> bookList) throws Exception {
		preProcessExecution();
		List<ReservedBooksEntity> entityList = getReservedBooksMapper().toEntityList(bookList);
		reservedBooksDao.removeBookRecords(entityList);
		postProcessExecution();
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void removeReservedBooksRecord(List<ReservedBooksEntity> entityList) throws Exception {
		preProcessExecution();
		reservedBooksDao.removeBookRecords(entityList);
		postProcessExecution();
	}
	
	public void verifyReservedBooksCount(int expectedCount) throws Exception {
		//openEntityManager();
		List<ReservedBooksEntity> reservedBooks = reservedBooksDao.getAllBookRecords();
		verifyReservedBooksCount(reservedBooks, expectedCount);
		//closeEntityManager();
	}

	public void verifyReservedBooksCount(Collection<ReservedBooksEntity> reservedBooks, int expectedCount) throws Exception {
		Assert.notNull(reservedBooks, "Result not found");
		if (expectedCount > 0)
			Assert.notNull(reservedBooks, "ReservedBooks records should exist");
		Assert.equals(expectedCount, reservedBooks.size(), "ReservedBooks record count not correct");
	}

	public void verifyReservedBooksExists(ReservedBooksEntity reservedBooks) throws Exception {
		verifyReservedBooksExists(reservedBooks.getId());
	}

	public void verifyReservedBooksExists(Long id) throws Exception {
		//openEntityManager();
		ReservedBooksEntity bookOrders = reservedBooksDao.getBookRecordById(id);
		Assert.notNull(bookOrders, "ReservedBooks record should exist");
		Assert.equals(bookOrders.getId(), id, "Ids should be same");
		//closeEntityManager();
	}

	public void verifyReservedBooksExists(List<Long> ids) throws Exception {
		Iterator<Long> iterator = ids.iterator();
		while (iterator.hasNext()) {
			Long id = iterator.next();
			verifyReservedBooksExists(id);
		}
	}
	
	public void verifyReservedBooksNotExists(ReservedBooksEntity reservedBooks) throws Exception {
		verifyReservedBooksNotExists(reservedBooks.getId());
	}

	public void verifyReservedBooksNotExists(Long id) throws Exception {
		//openEntityManager();
		ReservedBooksEntity reservedBooks = reservedBooksDao.getBookRecordById(id);
		Assert.isNull(reservedBooks, "ReservedBooks should not exist");
		//closeEntityManager();
	}

	
	
	public void verifyNoReservedBooksRecordsExist() throws Exception {
		List<Book> allBookList = getAllReservedBooks();
		verifyNoReservedBooksRecordsExist(allBookList);
	}

	public void verifyNoReservedBooksRecordsExistIC() throws Exception {
		List<Book> allBookList = getAllReservedBooksIC();
		verifyNoReservedBooksRecordsExist(allBookList);
	}

	public void verifyNoReservedBooksRecordsExist(List<Book> bookList) throws Exception {
		Assert.isEmpty(bookList, "ReservedBooks should be empty");
	}

	public void verifyReservedBooksRecordExists(Book book) throws Exception {
		List<Book> allBookList = getAllReservedBooks();
		verifyReservedBooksRecordExists(allBookList, book);
	}
	
	public void verifyReservedBooksRecordExistsIC(Book book) throws Exception {
		List<Book> allBookList = getAllReservedBooksIC();
		verifyReservedBooksRecordExists(allBookList, book);
	}
	
	public void verifyReservedBooksRecordExists(Collection<Book> bookList, Book book) throws Exception {
		Assert.isTrue(!bookList.isEmpty(), "No ReservedBooks records exist");
		//Assert.isTrue(bookList.contains(book), "ReservedBooks record not found: "+book);
		Assert.isTrue(bookList.size() == 1, "Only 1 ReservedBooks record should exist");
		Bookshop2Fixture.assertSameBook(book, bookList.iterator().next());
	}

	public void verifyReservedBooksRecordsExist(Collection<Book> bookList) throws Exception {
		List<Book> allBookList = getAllReservedBooks();
		verifyReservedBooksRecordsExist(allBookList, bookList);
	}
	
	public void verifyReservedBooksRecordsExistIC(Collection<Book> bookList) throws Exception {
		List<Book> allBookList = getAllReservedBooksIC();
		verifyReservedBooksRecordsExist(allBookList, bookList);
	}

	public void verifyReservedBooksRecordsExist(Collection<Book> bookList, Collection<Book> bookList2) throws Exception {
		Assert.isTrue(!bookList.isEmpty(), "No ReservedBooks records exist");
		Iterator<Book> iterator = bookList2.iterator();
//		while (iterator.hasNext()) {
//			Book book = iterator.next();
//			Assert.isTrue(bookList.contains(book), "ReservedBooks record not found: "+book);
//		}
		Assert.equals(bookList.size(), bookList2.size(), "Unexpected ReservedBooks record counts");
		Bookshop2Fixture.assertSameBook(bookList, bookList2);
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

}
