package bookshop2.supplier;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	SupplierServiceCITSuite.class,
	QueryBooksJMSListenerCIT.class,
	ReserveBooksJMSListenerCIT.class,
	ShipBooksJMSListenerCIT.class
})
public class SupplierCITSuite {
}