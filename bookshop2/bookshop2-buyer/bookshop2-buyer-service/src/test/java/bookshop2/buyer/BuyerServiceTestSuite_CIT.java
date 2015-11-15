package bookshop2.buyer;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bookshop2.buyer.incoming.orderBooks.OrderBooksJMSListenerCIT;
import bookshop2.buyer.incoming.purchaseBooks.PurchaseBooksJMSListenerCIT;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	OrderBooksJMSListenerCIT.class,
	PurchaseBooksJMSListenerCIT.class
})
public class BuyerServiceTestSuite_CIT {
}