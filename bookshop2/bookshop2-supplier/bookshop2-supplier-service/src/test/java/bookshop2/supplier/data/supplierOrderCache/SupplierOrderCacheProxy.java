package bookshop2.supplier.data.supplierOrderCache;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bookshop2.Book;
import bookshop2.BookCriteria;
import bookshop2.BookKey;

import common.jmx.client.JmxProxy;


//@Stateful
public class SupplierOrderCacheProxy extends JmxProxy implements SupplierOrderCache {

	private static final Log log = LogFactory.getLog(SupplierOrderCacheProxy.class);
	
	private String mbean = SupplierOrderCacheManagerMBean.MBEAN_NAME;

	
	public List<Book> getAllBooksInStock() {
		return getList(mbean, "getAllBooksInStock");
	}

	public Book getFromBooksInStock(BookKey bookKey) {
		return getObject(mbean, "getFromBooksInStock", new String[] { "bookshop2.BookKey" }, new Object[] { bookKey });
	}

	public void addToBooksInStock(BookKey bookKey, Book book) {
		Map<BookKey, Book> bookMap = new HashMap<BookKey, Book>();
		bookMap.put(bookKey, book);
		addToBooksInStock(bookMap);
	}

	public void addToBooksInStock(Map<BookKey, Book> bookMap) {
		call(mbean, "addToBooksInStock", new String[] { "java.util.Map" }, new Object[] { bookMap });
	}

	public void clearSupplierOrderCache() {
		removeAllBooksInStock();
	}
	
	public void removeAllBooksInStock() {
		call(mbean, "removeAllBooksInStock");
	}

	public void removeFromBooksInStock(BookKey bookKey) {
		call(mbean, "removeFromBooksInStock", new String[] { "bookshop2.BookKey" }, new Object[] { bookKey });
	}

	@Override
	public Map<BookKey, Book> getAllBooksInStockAsMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book getFromBooksInStock(Long bookId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getFromBooksInStock(Collection<Long> bookIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<BookKey, Book> getFromBooksInStockAsMap(
			Collection<BookKey> bookKeys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getFromBooksInStock(BookCriteria bookCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getMatchingBooksInStock(Collection<Book> bookList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<BookKey, Book> getMatchingBooksInStockAsMap(
			Collection<Book> bookList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBooksInStock(Map<BookKey, Book> bookMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToBooksInStock(Book book) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToBooksInStock(Collection<Book> bookList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFromBooksInStock(Book book) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFromBooksInStock(Long bookId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFromBooksInStock(Collection<Book> bookList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFromBooksInStock(BookCriteria bookCriteria) {
		// TODO Auto-generated method stub
		
	}

}
