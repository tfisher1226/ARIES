package bookshop2.supplier.data.bookInventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateful;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bookshop2.Book;
import bookshop2.BookKey;

import common.jmx.client.JmxProxy;


@Stateful
public class BookInventoryProxy extends JmxProxy {

	private static final Log log = LogFactory.getLog(BookInventoryProxy.class);
	
	private String mbean = BookInventoryManagerMBean.MBEAN_NAME;

	
	public List<Book> getAllReservedBooks() {
		return getList(mbean, "getAllReservedBooks");
	}

	public Book getFromReservedBooks(BookKey bookKey) {
		return getObject(mbean, "getFromReservedBooks", new String[] { "bookshop2.BookKey" }, new Object[] { bookKey });
	}

	public void addToReservedBooks(BookKey bookKey, Book book) {
		Map<BookKey, Book> bookMap = new HashMap<BookKey, Book>();
		bookMap.put(bookKey, book);
		addToReservedBooks(bookMap);
	}

	public void addToReservedBooks(Map<BookKey, Book> bookMap) {
		call(mbean, "addToReservedBooks", new String[] { "java.util.Map" }, new Object[] { bookMap });
	}

	public void clearBookInventory() {
		call(mbean, "removeAllReservedBooks");
	}
	
	public void removeAllReservedBooks() {
		call(mbean, "removeAllReservedBooks");
	}

	public void removeFromReservedBooks(BookKey bookKey) {
		call(mbean, "removeFromReservedBooks", new String[] { "bookshop2.BookKey" }, new Object[] { bookKey });
	}

}
