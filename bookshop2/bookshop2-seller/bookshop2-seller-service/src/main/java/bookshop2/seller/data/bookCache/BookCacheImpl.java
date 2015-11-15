package bookshop2.seller.data.bookCache;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;

import org.aries.Assert;
import org.aries.cache.AbstractCachePeer;

import admin.User;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;
import bookshop2.util.Bookshop2Fixture;
import bookshop2.util.Bookshop2Helper;


@Singleton
@Local(BookCache.class)
@ConcurrencyManagement(BEAN)
@TransactionAttribute(REQUIRED)
@TransactionManagement(CONTAINER)
public class BookCacheImpl extends AbstractCachePeer implements BookCache {

	private static final String CONFIGURATION_FILE_NAME = "bookshop2-seller-ehcache-jgroups.xml";
	
	private static final String CACHE_PEER_NAME = "SellerCacheManager";
	
	private static final String CACHE_NAME = "BookCache";
	
	
	public BookCacheImpl() {
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
	public User getUser() {
		return getElement("User");
	}

	@Override
	public void setUser(User user) {
		putElement("User", user);
	}
	
	@Override
	public List<Book> getAllBookOrders() {
		return getElements("BookOrders");
	}
	
	@Override
	public Book getFromBookOrders(Long bookId) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
		List<Book> bookList = getFromBookOrders(bookCriteria);
		Assert.isTrue(bookList.size() <= 1, "Multiple records found for Book ID: "+bookId);
		if (bookList.size() == 1)
			return bookList.get(0);
		return null;
	}
	
	@Override
	public List<Book> getFromBookOrders(Collection<Long> bookIds) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromIds(bookIds);
		List<Book> bookList = getFromBookOrders(bookCriteria);
		return bookList;
	}
	
	@Override
	public List<Book> getFromBookOrders(BookCriteria bookCriteria) {
		List<Book> bookList = new ArrayList<Book>();
		List<Book> elements = getElements("BookOrders");
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
	public List<Book> getMatchingBookOrders(Collection<Book> bookList) {
		List<Book> allBookList = getAllBookOrders();
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
	public void setBookOrders(Collection<Book> bookList) {
		replaceElements("BookOrders", Bookshop2Helper.createBookKeys(bookList), bookList);
	}
	
	@Override
	public void addToBookOrders(Book book) {
		putElement("BookOrders", Bookshop2Helper.createBookKey(book), book);
	}
	
	@Override
	public void addToBookOrders(Collection<Book> bookList) {
		putElements("BookOrders", Bookshop2Helper.createBookKeys(bookList), bookList);
	}

	@Override
	public void removeAllBookOrders() {
		removeAllElements("BookOrders");
	}
	
	@Override
	public void removeFromBookOrders(Book book) {
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		removeElementByKey("BookOrders", bookKey);
	}
	
	@Override
	public void removeFromBookOrders(Long bookId) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
		removeFromBookOrders(bookCriteria);
	}
	
	@Override
	public void removeFromBookOrders(Collection<Book> bookList) {
		removeElementsByKey("BookOrders", Bookshop2Helper.createBookKeys(bookList));
	}
	
	@Override
	public void removeFromBookOrders(BookCriteria bookCriteria) {
		List<Book> bookList = getFromBookOrders(bookCriteria);
		removeFromBookOrders(bookList);
	}

	@Override
	public Set<Book> getAllAvailableBooks() {
		return getElementsAsSet("AvailableBooks");
	}

	@Override
	public Book getFromAvailableBooks(Long bookId) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
		Set<Book> bookSet = getFromAvailableBooks(bookCriteria);
		Assert.isTrue(bookSet.size() <= 1, "Multiple records found for Book ID: "+bookId);
		if (bookSet.size() == 1)
			return bookSet.iterator().next();
		return null;
	}
	
	@Override
	public Set<Book> getFromAvailableBooks(Collection<Long> bookIds) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromIds(bookIds);
		Set<Book> bookSet = getFromAvailableBooks(bookCriteria);
		return bookSet;
	}
	
	@Override
	public Set<Book> getFromAvailableBooks(BookCriteria bookCriteria) {
		Set<Book> bookSet = new HashSet<Book>();
		List<Book> elements = getElements("AvailableBooks");
		Iterator<Book> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			if (Bookshop2Helper.isMatch(bookCriteria, book)) {
				bookSet.add(book);
			}
		}
		return bookSet;
	}
	
	@Override
	public Set<Book> getMatchingAvailableBooks(Collection<Book> bookSet) {
		Set<Book> allBookSet = getAllAvailableBooks();
		Set<Book> matchingBookSet = new HashSet<Book>(bookSet.size());
		Iterator<Book> iterator = bookSet.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			if (Bookshop2Fixture.containsBook(allBookSet, book)) {
				matchingBookSet.add(book);
		}
		}
		return matchingBookSet;
	}
	
	@Override
	public void setAvailableBooks(Collection<Book> bookSet) {
		replaceElements("AvailableBooks", Bookshop2Helper.createBookKeys(bookSet), bookSet);
	}
	
	@Override
	public void addToAvailableBooks(Book book) {
		putElement("AvailableBooks", Bookshop2Helper.createBookKey(book), book);
	}

	@Override
	public void addToAvailableBooks(Collection<Book> bookSet) {
		putElements("AvailableBooks", Bookshop2Helper.createBookKeys(bookSet), bookSet);
	}

	@Override
	public void removeAllAvailableBooks() {
		removeAllElements("AvailableBooks");
	}
	
	@Override
	public void removeFromAvailableBooks(Book book) {
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		removeElementByKey("AvailableBooks", bookKey);
	}
	
	@Override
	public void removeFromAvailableBooks(Long bookId) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
		removeFromAvailableBooks(bookCriteria);
	}
	
	@Override
	public void removeFromAvailableBooks(Collection<Book> bookSet) {
		removeElementsByKey("AvailableBooks", Bookshop2Helper.createBookKeys(bookSet));
	}

	@Override
	public void removeFromAvailableBooks(BookCriteria bookCriteria) {
		Set<Book> bookSet = getFromAvailableBooks(bookCriteria);
		removeFromAvailableBooks(bookSet);
	}
	
	@Override
	public Set<Book> getAllUnavailableBooks() {
		return getElementsAsSet("UnavailableBooks");
	}

	@Override
	public Book getFromUnavailableBooks(Long bookId) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
		Set<Book> bookSet = getFromUnavailableBooks(bookCriteria);
		Assert.isTrue(bookSet.size() <= 1, "Multiple records found for Book ID: "+bookId);
		if (bookSet.size() == 1)
			return bookSet.iterator().next();
		return null;
	}
	
	@Override
	public Set<Book> getFromUnavailableBooks(Collection<Long> bookIds) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromIds(bookIds);
		Set<Book> bookSet = getFromUnavailableBooks(bookCriteria);
		return bookSet;
	}
	
	@Override
	public Set<Book> getFromUnavailableBooks(BookCriteria bookCriteria) {
		Set<Book> bookSet = new HashSet<Book>();
		List<Book> elements = getElements("UnavailableBooks");
		Iterator<Book> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			if (Bookshop2Helper.isMatch(bookCriteria, book)) {
				bookSet.add(book);
			}
		}
		return bookSet;
	}
	
	@Override
	public Set<Book> getMatchingUnavailableBooks(Collection<Book> bookSet) {
		Set<Book> allBookSet = getAllUnavailableBooks();
		Set<Book> matchingBookSet = new HashSet<Book>(bookSet.size());
		Iterator<Book> iterator = bookSet.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			if (Bookshop2Fixture.containsBook(allBookSet, book)) {
				matchingBookSet.add(book);
		}
		}
		return matchingBookSet;
	}
	
	@Override
	public void setUnavailableBooks(Collection<Book> bookSet) {
		replaceElements("UnavailableBooks", Bookshop2Helper.createBookKeys(bookSet), bookSet);
	}
	
	@Override
	public void addToUnavailableBooks(Book book) {
		putElement("UnavailableBooks", Bookshop2Helper.createBookKey(book), book);
	}

	@Override
	public void addToUnavailableBooks(Collection<Book> bookSet) {
		putElements("UnavailableBooks", Bookshop2Helper.createBookKeys(bookSet), bookSet);
	}

	@Override
	public void removeAllUnavailableBooks() {
		removeAllElements("UnavailableBooks");
	}
	
	@Override
	public void removeFromUnavailableBooks(Book book) {
		BookKey bookKey = Bookshop2Helper.createBookKey(book);
		removeElementByKey("UnavailableBooks", bookKey);
	}
	
	@Override
	public void removeFromUnavailableBooks(Long bookId) {
		BookCriteria bookCriteria = Bookshop2Helper.createBookCriteriaFromId(bookId);
		removeFromUnavailableBooks(bookCriteria);
	}

	@Override
	public void removeFromUnavailableBooks(Collection<Book> bookSet) {
		removeElementsByKey("UnavailableBooks", Bookshop2Helper.createBookKeys(bookSet));
	}
	
	@Override
	public void removeFromUnavailableBooks(BookCriteria bookCriteria) {
		Set<Book> bookSet = getFromUnavailableBooks(bookCriteria);
		removeFromUnavailableBooks(bookSet);
	}

}
