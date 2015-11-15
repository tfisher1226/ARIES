package bookshop2.supplier;

import bookshop2.supplier.dao.BookDao;
import bookshop2.supplier.dao.BookDaoImpl;
import bookshop2.supplier.dao.OrderDao;
import bookshop2.supplier.dao.OrderDaoImpl;
import bookshop2.supplier.entity.BookOrdersBookEntity;
import bookshop2.supplier.entity.BookOrdersEntity;
import bookshop2.supplier.entity.ReservedBooksEntity;
import bookshop2.supplier.map.BookOrdersBookMapper;
import bookshop2.supplier.map.BookOrdersMapper;
import bookshop2.supplier.map.ReservedBooksMapper;


public class SupplierDataTestFixture {

	
	private static OrderDao<BookOrdersEntity> bookOrdersDao;

	private static BookDao<BookOrdersBookEntity> bookOrdersBookDao;

	private static BookDao<ReservedBooksEntity> reservedBooksDao;

	private static BookOrdersMapper bookOrdersMapper;

	private static BookOrdersBookMapper bookOrdersBookMapper;

	private static ReservedBooksMapper reservedBooksMapper;


	public static OrderDao<BookOrdersEntity> getBookOrdersDao() {
		if (bookOrdersDao == null)
			bookOrdersDao = createBookOrdersDao();
		return bookOrdersDao;
	}
	
	public static OrderDao<BookOrdersEntity> createBookOrdersDao() {
		OrderDaoImpl<BookOrdersEntity> bookOrdersDao = new OrderDaoImpl<BookOrdersEntity>();
		bookOrdersDao.initialize("BookOrders");
		return bookOrdersDao;
	}

	public static BookDao<BookOrdersBookEntity> getBookOrdersBookDao() {
		if (bookOrdersBookDao == null)
			bookOrdersBookDao = createBookOrdersBookDao();
		return bookOrdersBookDao;
	}
	
	public static BookDao<BookOrdersBookEntity> createBookOrdersBookDao() {
		BookDaoImpl<BookOrdersBookEntity> bookOrdersBookDao = new BookDaoImpl<BookOrdersBookEntity>();
		bookOrdersBookDao.initialize("BookOrdersBook");
		return bookOrdersBookDao;
	}

	public static BookDao<ReservedBooksEntity> getReservedBooksDao() {
		if (reservedBooksDao == null)
			reservedBooksDao = createReservedBooksDao();
		return reservedBooksDao;
	}
	
	public static BookDao<ReservedBooksEntity> createReservedBooksDao() {
		BookDaoImpl<ReservedBooksEntity> reservedBooksDao = new BookDaoImpl<ReservedBooksEntity>();
		reservedBooksDao.initialize("ReservedBooks");
		return reservedBooksDao;
	}
	
	public static BookOrdersMapper getBookOrdersMapper() {
		if (bookOrdersMapper == null)
			bookOrdersMapper = createBookOrdersMapper();
		return bookOrdersMapper;
	}

	public static BookOrdersMapper createBookOrdersMapper() {
		BookOrdersMapper mapper = new BookOrdersMapper();
		return mapper;
	}

	public static BookOrdersBookMapper getBooksMapper() {
		if (bookOrdersBookMapper == null)
			bookOrdersBookMapper = createBookMapper();
		return bookOrdersBookMapper;
	}

	public static BookOrdersBookMapper createBookMapper() {
		BookOrdersBookMapper mapper = new BookOrdersBookMapper();
		return mapper;	
	}
	
	public static ReservedBooksMapper getReservedBooksMapper() {
		if (reservedBooksMapper == null)
			reservedBooksMapper = createReservedBooksMapper();
		return reservedBooksMapper;
	}

	public static ReservedBooksMapper createReservedBooksMapper() {
		ReservedBooksMapper mapper = new ReservedBooksMapper();
		return mapper;
	}
	

}
