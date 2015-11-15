package bookshop2.supplier;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bookshop2.supplier.data.bookInventory.BookInventoryManagerIT;
import bookshop2.supplier.data.bookOrderLog.BookOrderLogManagerIT;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheManagerIT;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	BookInventoryManagerIT.class,
	BookOrderLogManagerIT.class,
	SupplierOrderCacheManagerIT.class
})
public class SupplierServiceITSuite {
}