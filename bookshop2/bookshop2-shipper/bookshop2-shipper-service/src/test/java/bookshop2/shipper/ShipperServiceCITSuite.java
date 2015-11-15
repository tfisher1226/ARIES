package bookshop2.shipper;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bookshop2.shipper.incoming.shipBooks.ShipBooksListenerForJMSCIT;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	ShipBooksListenerForJMSCIT.class
})
public class ShipperServiceCITSuite {
}