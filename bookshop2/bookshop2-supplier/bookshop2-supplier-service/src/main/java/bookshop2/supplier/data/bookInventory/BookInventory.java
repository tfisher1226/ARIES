package bookshop2.supplier.data.bookInventory;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.aries.Assert;
import org.aries.data.AbstractDataUnit;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;
import bookshop2.supplier.BookInventoryRepository;
import bookshop2.util.Bookshop2Helper;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionAttribute(REQUIRED)
@TransactionManagement(CONTAINER)
public class BookInventory extends AbstractDataUnit {
	
	private static final String UNIT_NAME = "BookInventory";
	
	@Inject
	protected BookInventoryRepository bookInventoryRepository;
	
	private Object mutex = new Object();
	
	
	public BookInventory() {
		//nothing for now
	}
	
	
	@Override
	public String getUnitName() {
		return UNIT_NAME;
	}
	
	@PostConstruct
	protected void initialize() {
		//nothing for now
	}
	
	@Override
	public void clearContext() {
		bookInventoryRepository.clearContext();
	}
	
	public List<Book> getAllReservedBooks() {
		return bookInventoryRepository.getAllReservedBooksRecords();
	}
	
	public Map<BookKey, Book> getAllReservedBooksAsMap() {
		List<Book> bookList = bookInventoryRepository.getAllReservedBooksRecords();
		Map<BookKey, Book> bookMap = Bookshop2Helper.createBookMap(bookList);
		return bookMap;
	}
	
	public Book getFromReservedBooks(Long bookId) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
		List<Book> bookList = bookInventoryRepository.getReservedBooksRecords(bookCriteria);
		Assert.isTrue(bookList.size() <= 1, "Unexpected number of results");
		if (bookList.size() == 1)
			return bookList.get(0);
		return null;
	}
	
	public Book getFromReservedBooks(BookKey bookKey) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromKey(bookKey);
		List<Book> bookList = bookInventoryRepository.getReservedBooksRecords(bookCriteria);
		Assert.isTrue(bookList.size() <= 1, "Unexpected number of results");
		if (bookList.size() == 1)
			return bookList.get(0);
		return null;
	}

	public List<Book> getFromReservedBooks(Collection<Long> bookIds) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromIds(bookIds);
		return bookInventoryRepository.getReservedBooksRecords(bookCriteria);
	}
	
	public Map<BookKey, Book> getFromReservedBooksAsMap(Collection<BookKey> bookKeys) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromKeys(bookKeys);
		List<Book> bookList = bookInventoryRepository.getReservedBooksRecords(bookCriteria);
		Map<BookKey, Book> bookMap = Bookshop2Helper.createBookMap(bookList);
		return bookMap;
	}
	
	public List<Book> getFromReservedBooks(BookCriteria bookCriteria) {
		return bookInventoryRepository.getReservedBooksRecords(bookCriteria);
	}
	
	public List<Book> getMatchingReservedBooks(Collection<Book> bookList) {
		Collection<BookKey> bookKeys = Bookshop2Helper.createBookKeys(bookList);
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromKeys(bookKeys);
		List<Book> matchingBookList = bookInventoryRepository.getReservedBooksRecords(bookCriteria);
		return matchingBookList;
	}
	
	public Map<BookKey, Book> getMatchingReservedBooksAsMap(Collection<Book> bookList) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteria(bookList);
		Collection<Book> resultList = bookInventoryRepository.getReservedBooksRecords(bookCriteria);
		Map<BookKey, Book> bookMap = Bookshop2Helper.createBookMap(resultList);
		return bookMap;
	}
	
	public Long addToReservedBooks(Book book) {
		return bookInventoryRepository.addReservedBooksRecord(book);
	}
	
	public List<Long> addToReservedBooks(Collection<Book> bookList) {
		return bookInventoryRepository.addReservedBooksRecords(bookList);
	}
	
	public List<Long> addToReservedBooks(Map<BookKey, Book> bookMap) {
		return bookInventoryRepository.addReservedBooksRecords(bookMap.values());
	}
	
	public void removeAllReservedBooks() {
		bookInventoryRepository.removeAllReservedBooksRecords();
	}
	
	public void removeFromReservedBooks(Book book) {
		bookInventoryRepository.removeReservedBooksRecord(book);
	}
	
	public void removeFromReservedBooks(Long bookId) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
		bookInventoryRepository.removeReservedBooksRecords(bookCriteria);
	}
	
	public void removeFromReservedBooks(BookKey bookKey) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromKey(bookKey);
		bookInventoryRepository.removeReservedBooksRecords(bookCriteria);
	}
	
	public void removeFromReservedBooks(Collection<Book> bookList) {
		bookInventoryRepository.removeReservedBooksRecords(bookList);
	}
	
	public void removeFromReservedBooks(BookCriteria bookCriteria) {
		bookInventoryRepository.removeReservedBooksRecords(bookCriteria);
	}
	
}
