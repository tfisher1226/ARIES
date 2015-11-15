package bookshop2.seller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	SellerServiceCITSuite.class,
	OrderBooksJMSListenerCIT.class,
	PurchaseBooksJMSListenerCIT.class
})
public class SellerCITSuite {
}