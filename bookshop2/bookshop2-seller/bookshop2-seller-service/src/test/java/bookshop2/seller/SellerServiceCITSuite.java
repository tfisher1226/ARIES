package bookshop2.seller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bookshop2.seller.data.bookCache.BookCacheManagerCIT;
import bookshop2.seller.incoming.orderBooks.OrderBooksListenerForJMSCIT;
import bookshop2.seller.incoming.purchaseBooks.PurchaseBooksListenerForJMSCIT;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	BookCacheManagerCIT.class,
	OrderBooksListenerForJMSCIT.class,
	PurchaseBooksListenerForJMSCIT.class
})
public class SellerServiceCITSuite {
}