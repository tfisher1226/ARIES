package bookshop2.ui.order;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.common.util.PersonNameUtil;
import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import bookshop2.Book;
import bookshop2.Order;
import bookshop2.ui.book.BookListManager;
import bookshop2.ui.book.BookListObject;
import bookshop2.util.OrderUtil;


@SessionScoped
@Named("orderHelper")
public class OrderHelper extends AbstractElementHelper<Order> implements Serializable {

	@Override
	public boolean isEmpty(Order order) {
		return OrderUtil.isEmpty(order);
	}
	
	@Override
	public String toString(Order order) {
		return PersonNameUtil.toPersonNameString(order.getPersonName());
	}
	
	@Override
	public String toString(Collection<Order> orderList) {
		return OrderUtil.toString(orderList);
	}
	
	@Override
	public boolean validate(Order order) {
		return OrderUtil.validate(order);
	}

	@Override
	public boolean validate(Collection<Order> orderList) {
		return OrderUtil.validate(orderList);
	}
	
	public DataModel<BookListObject> getBooks(Order order) {
		if (order == null)
			return null;
		return getBooks(order.getBooks());
	}

	public DataModel<BookListObject> getBooks(Collection<Book> booksList) {
		BookListManager bookListManager = BeanContext.getFromSession("bookListManager");
		DataModel<BookListObject> dataModel = bookListManager.getDataModel(booksList);
		return dataModel;
	}

}
