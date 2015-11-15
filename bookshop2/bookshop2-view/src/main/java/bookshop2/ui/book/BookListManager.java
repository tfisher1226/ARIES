package bookshop2.ui.book;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;

import bookshop2.Book;
import bookshop2.util.BookUtil;


@SessionScoped
@Named("bookListManager")
public class BookListManager extends AbstractTabListManager<Book, BookListObject> implements Serializable {
	
	@Override
	public String getModule() {
		return "BookList";
	}

	@Override
	public String getTitle() {
		return "Book List";
	}

	@Override
	public Object getRecordId(Book book) {
		return book.getId();
	}

	@Override
	public String getRecordName(Book book) {
		return BookUtil.toString(book);
	}

	@Override
	protected Class<Book> getRecordClass() {
		return Book.class;
	}

	@Override
	protected Book getRecord(BookListObject rowObject) {
		return rowObject.getBook();
	}
	
	@Override
	protected BookListObject createRowObject(Book book) {
		return new BookListObject(book);
	}

	protected BookHelper getBookHelper() {
		return BeanContext.getFromSession("bookHelper");
	}
	
	protected BookInfoManager getBookInfoManager() {
		return BeanContext.getFromSession("bookInfoManager");
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
	}
	
	@Override
	public void refreshModel() {
		refreshModel(recordList);
	}
	
	public void editBook() {
		editBook(selectedRecordId);
	}
	
	public void editBook(String recordId) {
		editBook(Long.parseLong(recordId));
	}
	
	public void editBook(Long recordId) {
		Book book = recordByIdMap.get(recordId);
		editBook(book);
	}
	
	public void editBook(Book book) {
		BookInfoManager bookInfoManager = BeanContext.getFromSession("bookInfoManager");
		bookInfoManager.editBook(book);
	}
	
	//SEAM @Observer("org.aries.removeBook")
	public void removeBook() {
		removeBook(selectedRecordId);
	}
	
	public void removeBook(String recordId) {
		removeBook(Long.parseLong(recordId));
	}
	
	public void removeBook(Long recordId) {
		Book book = recordByIdMap.get(recordId);
		removeBook(book);
	}
	
	public void removeBook(Book book) {
			try {
				clearSelection();
				refresh();
			
			} catch (Exception e) {
			handleException(e);
		}
	}
	
	//SEAM @Observer("org.aries.cancelBook")
	public void cancelBook() {
		if (selectedRecord == null)
			return;
		try {
			BeanContext.removeFromSession("book");
			Long id = selectedRecord.getId();
			initialize(recordByIdMap.values());
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public boolean validateBook(Collection<Book> bookList) {
		return BookUtil.validate(bookList);
	}
	
}
