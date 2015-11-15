package bookshop2.supplier.util;

import org.aries.common.util.CommonMapperFixture;
import org.aries.util.FieldUtil;

import bookshop2.supplier.map.BookOrdersBookMapper;
import bookshop2.supplier.map.BookOrdersMapper;
import bookshop2.supplier.map.ReservedBooksMapper;


public class SupplierMapperFixture {
	
	private static ReservedBooksMapper reservedBooksMapper;
	
	private static BookOrdersMapper bookOrdersMapper;
	
	private static BookOrdersBookMapper bookOrdersBookMapper;
	
	
	public static void clear() {
		reservedBooksMapper = null;
		bookOrdersMapper = null;
		bookOrdersBookMapper = null;
	}

	public static ReservedBooksMapper getReservedBooksMapper() {
		if (reservedBooksMapper == null) {
			reservedBooksMapper = new ReservedBooksMapper();
		}
		return reservedBooksMapper;
	}
	
	public static BookOrdersMapper getBookOrdersMapper() {
		if (bookOrdersMapper == null) {
			bookOrdersMapper = new BookOrdersMapper();
			FieldUtil.setFieldValue(bookOrdersMapper, "personNameMapper", CommonMapperFixture.getPersonNameMapper());
			FieldUtil.setFieldValue(bookOrdersMapper, "bookOrdersBookMapper", getBookOrdersBookMapper());
		}
		return bookOrdersMapper;
	}
	
	public static BookOrdersBookMapper getBookOrdersBookMapper() {
		if (bookOrdersBookMapper == null) {
			bookOrdersBookMapper = new BookOrdersBookMapper();
	}
		return bookOrdersBookMapper;
	}
	
}
