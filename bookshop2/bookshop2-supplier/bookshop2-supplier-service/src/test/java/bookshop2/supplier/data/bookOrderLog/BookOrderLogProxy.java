package bookshop2.supplier.data.bookOrderLog;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bookshop2.Book;
import bookshop2.BookKey;

import common.jmx.client.JmxProxy;


@Stateful
public class BookOrderLogProxy extends JmxProxy {

	private static final Log log = LogFactory.getLog(BookOrderLogProxy.class);
	
	private String mbean = BookOrderLogManagerMBean.MBEAN_NAME;

	
	public List<Book> getAllBookOrders() {
		return getList(mbean, "getAllBookOrders");
	}

	public Book getFromBookOrders(Long bookId) {
		return getObject(mbean, "getFromBookOrders", new String[] { "java.lang.Long" }, new Object[] { bookId });
	}

	public void addToBookOrders(Book book) {
		List<Book> bookList = new ArrayList<Book>();
		bookList.add(book);
		addToBookOrders(bookList);
	}

	public void addToBookOrders(List<Book> bookList) {
		call(mbean, "addToBookOrders", new String[] { "java.util.List" }, new Object[] { bookList });
	}

	public void removeAllBookOrders() {
		call(mbean, "removeAllBookOrders");
	}

	public void removeFromBookOrders(Book book) {
		call(mbean, "removeFromBookOrders", new String[] { "bookshop2.Book" }, new Object[] { book });
	}

}
