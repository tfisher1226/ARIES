package bookshop2.supplier.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.aries.data.map.AbstractMapper;

import bookshop2.Book;
import bookshop2.supplier.entity.ReservedBooksEntity;


@Stateless
@Local(ReservedBooksMapper.class)
public class ReservedBooksMapper extends AbstractMapper<Book, ReservedBooksEntity> {
	
	public ReservedBooksMapper() {
		setModelClass(Book.class);
		setEntityClass(ReservedBooksEntity.class);
	}
	
	
	public Book toModel(ReservedBooksEntity reservedBooksEntity) {
		if (reservedBooksEntity == null)
			return null;
		Book bookModel = createModel();
		bookModel.setId(reservedBooksEntity.getId());
		bookModel.setBarCode(reservedBooksEntity.getBarCode());
		bookModel.setAuthor(reservedBooksEntity.getAuthor());
		bookModel.setTitle(reservedBooksEntity.getTitle());
		bookModel.setPrice(reservedBooksEntity.getPrice());
		bookModel.setQuantity(reservedBooksEntity.getQuantity());
		return bookModel;
	}
	
	public List<Book> toModelList(Collection<ReservedBooksEntity> reservedBooksEntityList) {
		if (reservedBooksEntityList == null)
			return null;
		List<Book> bookModelList = new ArrayList<Book>();
		for (ReservedBooksEntity reservedBooksEntity : reservedBooksEntityList) {
			Book bookModel = toModel(reservedBooksEntity);
			bookModelList.add(bookModel);
		}
		return bookModelList;
	}
	
	public ReservedBooksEntity toEntity(Book bookModel) {
		if (bookModel == null)
			return null;
		ReservedBooksEntity reservedBooksEntity = createEntity();
		toEntity(reservedBooksEntity, bookModel);
		return reservedBooksEntity;
	}
	
	public void toEntity(ReservedBooksEntity reservedBooksEntity, Book bookModel) {
		if (reservedBooksEntity != null && bookModel != null) {
			reservedBooksEntity.setId(bookModel.getId());
			reservedBooksEntity.setBarCode(bookModel.getBarCode());
			reservedBooksEntity.setAuthor(bookModel.getAuthor());
			reservedBooksEntity.setTitle(bookModel.getTitle());
			reservedBooksEntity.setPrice(bookModel.getPrice());
			reservedBooksEntity.setQuantity(bookModel.getQuantity());
		}
	}
	
	public List<ReservedBooksEntity> toEntityList(Collection<Book> bookModelList) {
		if (bookModelList == null)
			return null;
		List<ReservedBooksEntity> reservedBooksEntityList = new ArrayList<ReservedBooksEntity>();
		for (Book bookModel : bookModelList) {
			ReservedBooksEntity reservedBooksEntity = toEntity(bookModel);
			reservedBooksEntityList.add(reservedBooksEntity);
		}
		return reservedBooksEntityList;
	}
	
}
