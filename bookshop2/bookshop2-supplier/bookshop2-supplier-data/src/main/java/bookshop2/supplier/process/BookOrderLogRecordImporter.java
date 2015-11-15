package bookshop2.supplier.process;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.util.ExceptionUtil;

import bookshop2.supplier.dao.BookDao;
import bookshop2.supplier.dao.OrderDao;
import bookshop2.supplier.entity.BookOrdersBookEntity;
import bookshop2.supplier.entity.BookOrdersEntity;


@Stateless
@Local(BookOrderLogRecordImporter.class)
public class BookOrderLogRecordImporter {

	private static Log log = LogFactory.getLog(BookOrderLogRecordImporter.class);
	
	@Inject
	protected OrderDao<BookOrdersEntity> bookOrdersDao;

	@Inject
	protected BookDao<BookOrdersBookEntity> bookOrdersBookDao;

	//@PersistenceContext(unitName = "bookOrderLog", type = EXTENDED)
	protected EntityManager entityManager;
	
	
	@PostConstruct
	public void initializeBookOrdersBookDao() {
		bookOrdersBookDao.initialize("BookOrders");
		bookOrdersBookDao.setEntityManager(entityManager);
		bookOrdersBookDao.setEntityClass(BookOrdersBookEntity.class);
	}
	
	public void importBookOrdersRecords() {
		importAllBookOrdersRecords();
	}

	public void importAllBookOrdersRecords() {
		List<BookOrdersEntity> recordsToImport = getBookOrdersRecords();
		log.debug("*** Members found: "+recordsToImport.size());
		List<BookOrdersEntity> recordsImported = importBookOrdersRecords(recordsToImport);
		log.debug("*** Members imported: "+recordsImported.size());
		saveBookOrdersRecords(recordsImported);
	}
	
//	public void importBookOrdersRecords(BookOrdersCriteria bookOrdersCriteria) {
//	}

	//SEAM @Transactional
	@SuppressWarnings("unchecked") 
	protected List<BookOrdersEntity> getBookOrdersRecords() {
		try {
			List<BookOrdersEntity> result = bookOrdersDao.getAllOrderRecords();
			return result;
			
		} catch (Exception e) {
			Exception cause = ExceptionUtil.getRootCause(e);
			throw new RuntimeException(cause.getMessage());
		}
	}
	
	public void saveBookOrdersRecord(BookOrdersEntity bookOrder) {
		try {
			bookOrdersDao.saveOrderRecord(bookOrder);
			
		} catch (Exception e) {
			log.error(e);
			ExceptionUtil.rethrow(e);
		}
	}

	public void saveBookOrdersRecords(List<BookOrdersEntity> bookOrders) {
		try {
			bookOrdersDao.saveOrderRecords(bookOrders);
			
		} catch (Exception e) {
			log.error(e);
			ExceptionUtil.rethrow(e);
		}
	}

	protected List<BookOrdersEntity> importBookOrdersRecords(List<BookOrdersEntity> bookOrders) {
		return null;
	}

}
