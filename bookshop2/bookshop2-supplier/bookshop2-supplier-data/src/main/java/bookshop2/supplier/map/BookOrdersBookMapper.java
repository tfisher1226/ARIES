package bookshop2.supplier.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.aries.data.map.AbstractMapper;

import bookshop2.Book;
import bookshop2.supplier.entity.BookOrdersBookEntity;


@Stateless
@Local(BookOrdersBookMapper.class)
public class BookOrdersBookMapper extends AbstractMapper<Book, BookOrdersBookEntity> {
	
	public BookOrdersBookMapper() {
		setModelClass(Book.class);
		setEntityClass(BookOrdersBookEntity.class);
	}
	
	
	public Book toModel(BookOrdersBookEntity bookOrdersBookEntity) {
		if (bookOrdersBookEntity == null)
			return null;
		Book bookModel = createModel();
		bookModel.setId(bookOrdersBookEntity.getId());
		bookModel.setBarCode(bookOrdersBookEntity.getBarCode());
		bookModel.setAuthor(bookOrdersBookEntity.getAuthor());
		bookModel.setTitle(bookOrdersBookEntity.getTitle());
		bookModel.setPrice(bookOrdersBookEntity.getPrice());
		bookModel.setQuantity(bookOrdersBookEntity.getQuantity());
		return bookModel;
	}
	
	public List<Book> toModelList(Collection<BookOrdersBookEntity> bookOrdersBookEntityList) {
		if (bookOrdersBookEntityList == null)
			return null;
		List<Book> bookModelList = new ArrayList<Book>();
		for (BookOrdersBookEntity bookOrdersBookEntity : bookOrdersBookEntityList) {
			Book bookModel = toModel(bookOrdersBookEntity);
			bookModelList.add(bookModel);
		}
		return bookModelList;
	}
	
	public BookOrdersBookEntity toEntity(Book bookModel) {
		if (bookModel == null)
			return null;
		BookOrdersBookEntity bookOrdersBookEntity = createEntity();
		toEntity(bookOrdersBookEntity, bookModel);
		return bookOrdersBookEntity;
	}
	
	public void toEntity(BookOrdersBookEntity bookOrdersBookEntity, Book bookModel) {
		if (bookOrdersBookEntity != null && bookModel != null) {
			bookOrdersBookEntity.setId(bookModel.getId());
			bookOrdersBookEntity.setBarCode(bookModel.getBarCode());
			bookOrdersBookEntity.setAuthor(bookModel.getAuthor());
			bookOrdersBookEntity.setTitle(bookModel.getTitle());
			bookOrdersBookEntity.setPrice(bookModel.getPrice());
			bookOrdersBookEntity.setQuantity(bookModel.getQuantity());
		}
	}
	
	public List<BookOrdersBookEntity> toEntityList(Collection<Book> bookModelList) {
		if (bookModelList == null)
			return null;
		List<BookOrdersBookEntity> bookOrdersBookEntityList = new ArrayList<BookOrdersBookEntity>();
		for (Book bookModel : bookModelList) {
			BookOrdersBookEntity bookOrdersBookEntity = toEntity(bookModel);
			bookOrdersBookEntityList.add(bookOrdersBookEntity);
		}
		return bookOrdersBookEntityList;
	}
	
}
