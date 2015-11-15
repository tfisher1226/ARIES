package bookshop2.supplier;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bookshop2.supplier.dao.BookOrdersDaoIT;
import bookshop2.supplier.entity.BookOrdersEntityIT;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	BookOrdersDaoIT.class,
	BookOrdersEntityIT.class
	//BookOrdersBookEntityIT.class
})
public class BookOrderLogITSuite {
}