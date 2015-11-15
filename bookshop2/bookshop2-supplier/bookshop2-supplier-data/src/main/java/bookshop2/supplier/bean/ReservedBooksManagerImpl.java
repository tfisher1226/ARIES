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

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;
import bookshop2.supplier.dao.BookDao;
import bookshop2.supplier.dao.BookDaoImpl;
import bookshop2.supplier.entity.ReservedBooksEntity;
import bookshop2.supplier.map.ReservedBooksMapper;
import bookshop2.supplier.process.BookImporter;
import bookshop2.util.Bookshop2Helper;


@Stateful
@Local(ReservedBooksManager.class)
@ConcurrencyManagement(BEAN)
public class ReservedBooksManagerImpl implements ReservedBooksManager {
	
	@PersistenceContext(unitName = "bookInventory", type = EXTENDED)
	protected EntityManager entityManager;
	
	@Inject
	protected BookImporter reservedBooksImporter;
	
	@Inject
	protected ReservedBooksMapper reservedBooksMapper;
	
	@Inject
	@New(BookDaoImpl.class)
	protected BookDao<ReservedBooksEntity> reservedBooksDao;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public void clearContext() {
		entityManager.clear();
	}
	
	@PostConstruct
	public void initialize() {
		reservedBooksDao.initialize("ReservedBooks");
		reservedBooksDao.setEntityManager(entityManager);
		reservedBooksDao.setEntityClass(ReservedBooksEntity.class);
	}
	
	@Override
	public List<Book> getAllReservedBooksRecords() {
		List<ReservedBooksEntity> entityList = reservedBooksDao.getAllBookRecords();
		List<Book> modelList = reservedBooksMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public Book getReservedBooksRecord(Long id) {
		Assert.notNull(id, "ID must be specified");
		ReservedBooksEntity entity = reservedBooksDao.getBookRecordById(id);
		Book model = reservedBooksMapper.toModel(entity);
		return model;
	}
	
	@Override
	public Book getReservedBooksRecord(BookKey key) {
		Assert.notNull(key, "Key must exist");
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromKey(key);
		ReservedBooksEntity entity = reservedBooksDao.getBookRecordByCriteria(bookCriteria);
		Book model = reservedBooksMapper.toModel(entity);
		return model;
	}
	
	@Override
	public List<Book> getReservedBooksRecords(int pageIndex, int pageSize) {
		Assert.notNull(pageIndex, "Page-index must be specified");
		Assert.notNull(pageSize, "Page-size must be specified");
		List<ReservedBooksEntity> entityList = reservedBooksDao.getBookRecordsByPage(pageIndex, pageSize);
		List<Book> modelList = reservedBooksMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public List<Book> getReservedBooksRecords(BookCriteria bookCriteria) {
		Assert.notNull(bookCriteria, "Book record criteria must be specified");
		List<ReservedBooksEntity> entityList = reservedBooksDao.getBookRecordsByCriteria(bookCriteria);
		List<Book> modelList = reservedBooksMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public Long addReservedBooksRecord(Book book) {
		Assert.notNull(book, "Book record must be specified");
		ReservedBooksEntity entity = reservedBooksMapper.toEntity(book);
		Long id = reservedBooksDao.addBookRecord(entity);
		Assert.notNull(id, "Id must exist");
		return id;
	}
	
	@Override
	public List<Long> addReservedBooksRecords(Collection<Book> bookList) {
		Assert.notNull(bookList, "Book record list must be specified");
		List<ReservedBooksEntity> entityList = reservedBooksMapper.toEntityList(bookList);
		List<Long> idList = reservedBooksDao.addBookRecords(entityList);
		Assert.notNull(idList, "IdList must exist");
		Assert.equals(idList.size(), bookList.size(), "Id count not correct");
		return idList;
	}
	
	@Override
	public void saveReservedBooksRecord(Book book) {
		Assert.notNull(book, "Book record must be specified");
		ReservedBooksEntity entity = reservedBooksMapper.toEntity(book);
		reservedBooksDao.saveBookRecord(entity);
	}
	
	@Override
	public void saveReservedBooksRecords(Collection<Book> bookList) {
		Assert.notNull(bookList, "Book record list must be specified");
		List<ReservedBooksEntity> entityList = reservedBooksMapper.toEntityList(bookList);
		reservedBooksDao.saveBookRecords(entityList);
	}
	
	@Override
	public void removeAllReservedBooksRecords() {
		reservedBooksDao.removeAllBookRecords();
	}
	
	@Override
	public void removeReservedBooksRecord(Book book) {
		Assert.notNull(book, "Book record must be specified");
		ReservedBooksEntity entity = reservedBooksMapper.toEntity(book);
		reservedBooksDao.removeBookRecord(entity);
	}
	
	@Override
	public void removeReservedBooksRecord(Long id) {
		Assert.notNull(id, "Book record ID must be specified");
		ReservedBooksEntity entity = reservedBooksDao.getBookRecordById(id);
		reservedBooksDao.removeBookRecord(entity);
	}
	
	@Override
	public void removeReservedBooksRecord(BookKey key) {
		Assert.notNull(key, "Book record key must be specified");
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromKey(key);
		reservedBooksDao.removeBookRecords(bookCriteria);
	}
	
	@Override
	public void removeReservedBooksRecords(Collection<Book> bookList) {
		Assert.notNull(bookList, "Book record list must be specified");
		List<ReservedBooksEntity> entityList = reservedBooksMapper.toEntityList(bookList);
		reservedBooksDao.removeBookRecords(entityList);
	}
	
	@Override
	public void removeReservedBooksRecords(BookCriteria bookCriteria) {
		Assert.notNull(bookCriteria, "Book record criteria must be specified");
		reservedBooksDao.removeBookRecords(bookCriteria);
	}
	
	@Override
	public void importReservedBooksRecords() {
		reservedBooksImporter.importBookRecords();
	}
	
}
