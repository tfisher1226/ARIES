package bookshop2.supplier.data.supplierOrderCache;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;

import org.aries.Assert;
import org.aries.cache.AbstractCachePeer;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;
import bookshop2.util.Bookshop2Fixture;
import bookshop2.util.Bookshop2Helper;


@Singleton
@Local(SupplierOrderCache.class)
@ConcurrencyManagement(BEAN)
@TransactionAttribute(REQUIRED)
@TransactionManagement(CONTAINER)
public class SupplierOrderCacheImpl extends AbstractCachePeer implements SupplierOrderCache {
	
	private static final String CONFIGURATION_FILE_NAME = "bookshop2-supplier-ehcache-jgroups.xml";
	
	private static final String CACHE_PEER_NAME = "SupplierCacheManager";
	
	private static final String CACHE_NAME = "SupplierOrderCache";
	
	
	public SupplierOrderCacheImpl() {
		//nothing for now
	}
	
	
	@Override
	protected String getCachePeerName() {
		return CACHE_PEER_NAME;
	}
	
	@Override
	protected String getCacheName() {
		return CACHE_NAME;
	}
	
	@PostConstruct
	public void initialize() {
		initialize(CONFIGURATION_FILE_NAME);
	}
	
	@Override
	public List<Book> getAllBooksInStock() {
		return getElements("BooksInStock");
	}
	
	@Override
	public Map<BookKey, Book> getAllBooksInStockAsMap() {
		List<Book> allElements = getAllElements("BooksInStock");
		Map<BookKey, Book> map = Bookshop2Helper.createBookMap(allElements);
		return map;
	}
	
	@Override
	public Book getFromBooksInStock(Long bookId) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
		List<Book> bookList = getFromBooksInStock(bookCriteria);
		Assert.isTrue(bookList.size() <= 1, "Multiple records found for Book ID: "+bookId);
		if (bookList.size() == 1)
			return bookList.get(0);
		return null;
	}
	
	@Override
	public Book getFromBooksInStock(BookKey bookKey) {
		return getElement("BooksInStock", bookKey);
	}
	
	@Override
	public List<Book> getFromBooksInStock(Collection<Long> bookIds) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromIds(bookIds);
		List<Book> bookList = getFromBooksInStock(bookCriteria);
		return bookList;
	}
	
	@Override
	public Map<BookKey, Book> getFromBooksInStockAsMap(Collection<BookKey> bookKeys) {
		List<Book> elements = getElements("BooksInStock", bookKeys);
		Map<BookKey, Book> map = Bookshop2Helper.createBookMap(elements);
		return map;
	}
	
	@Override
	public List<Book> getFromBooksInStock(BookCriteria bookCriteria) {
		List<Book> bookList = new ArrayList<Book>();
		List<Book> elements = getElements("BooksInStock");
		Iterator<Book> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			if (Bookshop2Helper.isMatch(bookCriteria, book)) {
				bookList.add(book);
			}
		}
		return bookList;
	}
	
	@Override
	public List<Book> getMatchingBooksInStock(Collection<Book> bookList) {
		List<Book> allBookList = getAllBooksInStock();
		List<Book> matchingBookList = new ArrayList<Book>(bookList.size());
		Iterator<Book> iterator = bookList.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			if (Bookshop2Fixture.containsBook(allBookList, book)) {
				matchingBookList.add(book);
			}
		}
		return matchingBookList;
	}
	
	@Override
	public Map<BookKey, Book> getMatchingBooksInStockAsMap(Collection<Book> bookList) {
		Collection<BookKey> bookKeys = Bookshop2Helper.createBookKeys(bookList);
		return getFromBooksInStockAsMap(bookKeys);
	}
	
	@Override
	public void setBooksInStock(Map<BookKey, Book> bookMap) {
		replaceElements("BooksInStock", bookMap);
	}
	
	@Override
	public void addToBooksInStock(Book book) {
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		putElement("BooksInStock", bookKey, book);
	}
	
	@Override
	public void addToBooksInStock(Collection<Book> bookList) {
		Map<BookKey, Book> bookMap = Bookshop2Helper.createBookMap(bookList);
		putElements("BooksInStock", bookMap);
	}
	
	@Override
	public void addToBooksInStock(Map<BookKey, Book> bookMap) {
		putElements("BooksInStock", bookMap);
	}
	
	@Override
	public void removeAllBooksInStock() {
		removeAllElements("BooksInStock");
	}
	
	@Override
	public void removeFromBooksInStock(Book book) {
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		removeElementByKey("BooksInStock", bookKey);
	}
	
	@Override
	public void removeFromBooksInStock(Long bookId) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
		removeFromBooksInStock(bookCriteria);
	}
	
	@Override
	public void removeFromBooksInStock(BookKey bookKey) {
		removeElementByKey("BooksInStock", bookKey);
	}
	
	@Override
	public void removeFromBooksInStock(Collection<Book> bookList) {
		Collection<BookKey> bookKeys = Bookshop2Helper.createBookKeys(bookList);
		removeElementsByKey("BooksInStock", bookKeys);
	}
	
	@Override
	public void removeFromBooksInStock(BookCriteria bookCriteria) {
		List<Book> bookList = getFromBooksInStock(bookCriteria);
		removeFromBooksInStock(bookList);
	}

}
