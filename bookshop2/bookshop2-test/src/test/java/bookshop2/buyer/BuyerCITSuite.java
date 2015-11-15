package bookshop2.buyer;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	BuyerServiceTestSuite_CIT.class,
	OrderBooksJMSListenerCIT.class,
	PurchaseBooksJMSListenerCIT.class
})
public class BuyerCITSuite {
}