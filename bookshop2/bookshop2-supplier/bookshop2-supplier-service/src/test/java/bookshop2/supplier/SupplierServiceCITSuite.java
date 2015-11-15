package bookshop2.supplier;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bookshop2.supplier.data.bookInventory.BookInventoryManagerCIT;
import bookshop2.supplier.data.bookOrderLog.BookOrderLogManagerCIT;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheManagerCIT;
import bookshop2.supplier.incoming.queryBooks.QueryBooksHandlerCIT;
import bookshop2.supplier.incoming.queryBooks.QueryBooksListenerForJMSCIT;
import bookshop2.supplier.incoming.reserveBooks.ReserveBooksHandlerCIT;
import bookshop2.supplier.incoming.reserveBooks.ReserveBooksListenerForJMSCIT;
import bookshop2.supplier.incoming.shipBooks.ShipBooksListenerForJMSCIT;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	SupplierProcessCIT.class,
	BookInventoryManagerCIT.class,
	BookOrderLogManagerCIT.class,
	SupplierOrderCacheManagerCIT.class,
	QueryBooksHandlerCIT.class,
	QueryBooksListenerForJMSCIT.class,
	ReserveBooksHandlerCIT.class,
	ReserveBooksListenerForJMSCIT.class,
	//ShipBooksListenerForJMSCIT.class
})
public class SupplierServiceCITSuite {
}