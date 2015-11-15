package bookshop2.supplier;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bookshop2.supplier.dao.ReservedBooksDaoIT;
import bookshop2.supplier.entity.ReservedBooksEntityIT;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	ReservedBooksDaoIT.class,
	ReservedBooksEntityIT.class
})
public class BookInventoryITSuite {
}