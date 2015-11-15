package bookshop2.supplier.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.aries.common.map.PersonNameMapper;
import org.aries.common.util.CommonMapperFixture;
import org.aries.data.map.AbstractMapper;

import bookshop2.Book;
import bookshop2.Order;
import bookshop2.supplier.entity.BookOrdersBookEntity;
import bookshop2.supplier.entity.BookOrdersEntity;


@Stateless
@Local(BookOrdersMapper.class)
public class BookOrdersMapper extends AbstractMapper<Order, BookOrdersEntity> {
	
	@Inject
	protected PersonNameMapper personNameMapper;

	@Inject
	protected BookOrdersBookMapper bookOrdersBookMapper;
	
	
	public BookOrdersMapper() {
		setModelClass(Order.class);
		setEntityClass(BookOrdersEntity.class);
		if (personNameMapper == null)
			personNameMapper = CommonMapperFixture.getPersonNameMapper();
		if (bookOrdersBookMapper == null)
			bookOrdersBookMapper = new BookOrdersBookMapper();
	}
	
	
	public Order toModel(BookOrdersEntity bookOrdersEntity) {
		if (bookOrdersEntity == null)
			return null;
		Order orderModel = createModel();
		orderModel.setId(bookOrdersEntity.getId());
		orderModel.setTrackingNumber(bookOrdersEntity.getTrackingNumber());
		orderModel.setPersonName(personNameMapper.toModel(bookOrdersEntity.getPersonName()));
		orderModel.setCount(bookOrdersEntity.getCount());
		orderModel.setBooks(new HashSet<Book>(bookOrdersBookMapper.toModelList(bookOrdersEntity.getBooks())));
		orderModel.setDateTime(bookOrdersEntity.getDateTime());
		return orderModel;
	}
	
	public List<Order> toModelList(Collection<BookOrdersEntity> bookOrdersEntityList) {
		if (bookOrdersEntityList == null)
			return null;
		List<Order> orderModelList = new ArrayList<Order>();
		for (BookOrdersEntity bookOrdersEntity : bookOrdersEntityList) {
			Order orderModel = toModel(bookOrdersEntity);
			orderModelList.add(orderModel);
		}
		return orderModelList;
	}
	
	public BookOrdersEntity toEntity(Order orderModel) {
		if (orderModel == null)
			return null;
		BookOrdersEntity bookOrdersEntity = createEntity();
		toEntity(bookOrdersEntity, orderModel);
		return bookOrdersEntity;
	}
	
	public void toEntity(BookOrdersEntity bookOrdersEntity, Order orderModel) {
		if (bookOrdersEntity != null && orderModel != null) {
			bookOrdersEntity.setId(orderModel.getId());
			bookOrdersEntity.setTrackingNumber(orderModel.getTrackingNumber());
			bookOrdersEntity.setPersonName(personNameMapper.toEntity(orderModel.getPersonName()));
			bookOrdersEntity.setCount(orderModel.getCount());
			Collection<BookOrdersBookEntity> bookOrdersEntityList = bookOrdersBookMapper.toEntityList(orderModel.getBooks());
			bookOrdersEntity.setBooks(new ArrayList<BookOrdersBookEntity>(bookOrdersEntityList));
			bookOrdersEntity.setDateTime(orderModel.getDateTime());
		}
	}
	
	public List<BookOrdersEntity> toEntityList(Collection<Order> orderModelList) {
		if (orderModelList == null)
			return null;
		List<BookOrdersEntity> bookOrdersEntityList = new ArrayList<BookOrdersEntity>();
		for (Order orderModel : orderModelList) {
			BookOrdersEntity bookOrdersEntity = toEntity(orderModel);
			bookOrdersEntityList.add(bookOrdersEntity);
		}
		return bookOrdersEntityList;
	}
	
}
