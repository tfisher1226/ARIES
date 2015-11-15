package bookshop2.ui.book;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordManager;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import admin.ui.action.ActionListManager;
import admin.ui.action.ActionSelectManager;
import bookshop2.Book;
import bookshop2.OrderRequestMessage;
import bookshop2.util.BookUtil;
import bookshop2.util.Bookshop2Fixture;


@SessionScoped
@Named("bookInfoManager")
public class BookInfoManager extends AbstractRecordManager<Book> implements Serializable {
	
	private ActionSelectManager actionSelectManager;

	private ActionListManager actionListManager;
	
	@Inject
	@Updated
	private Event<Book> updatedEvent;
	
	
	public BookInfoManager() {
		setInstanceName("book");
	}


	public Book getBook() {
		return getRecord();
	}

	@Override
	public Class<Book> getRecordClass() {
		return Book.class;
	}
	
	@Override
	public boolean isEmpty(Book book) {
		return getBookHelper().isEmpty(book);
	}
	
	@Override
	public String toString(Book book) {
		return getBookHelper().toString(book);
	}
	
	protected BookHelper getBookHelper() {
		return BeanContext.getFromSession("bookHelper");
	}
	
	protected void initialize(Book book) {
		BookUtil.initialize(book);
		initializeOutjectedState(book);
		setContext("book", book);
	}
	
	protected void initializeOutjectedState(Book book) {
		outject(instanceName, book);
	}
	
	public void populate() {
		try {
			Book book = Bookshop2Fixture.create_Book();
			initialize(book);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void activate() {
		initializeContext();
		Book book = BeanContext.getFromSession(getInstanceId());
		if (book == null)
			newBook();
		else editBook(book);
	}
	
	//SEAM @Begin(join = true)
	public void newBook() {
		try {
			Book book = create();
			initialize(book);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	@Override
	public Book create() {
		Book book = BookUtil.create();
		return book;
	}
	
	@Override
	public Book clone(Book book) {
		book = BookUtil.clone(book);
		return book;
	}
	
	//SEAM @Begin(join = true)
	public void editBook(Book book) {
		try {
			//book = clone(book);
			initialize(book);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}

	//SEAM @Observer("org.aries.saveBook")
	public void saveBook() {
		setModule("BookDialog");
		Book book = getBook();
		enrichBook(book);
		if (validate(book)) {
			saveBook(book);
			outject("book", book);
			//raiseEvent(actionEvent);
			updatedEvent.fire(book);
		}
	}

	public void processBook(Book book) {
		Long id = book.getId();
		if (id != null) {
			saveBook(book);
		}
	}
	
	public void saveBook(Book book) {
		try {
			raiseEvent("org.aries.refreshBookList");
			raiseEvent(actionEvent);
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichBook(Book book) {
		//nothing for now
	}
	
	public boolean validate(Book book) {
		Validator validator = getValidator();
		boolean isValid = BookUtil.validate(book);
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}

}
