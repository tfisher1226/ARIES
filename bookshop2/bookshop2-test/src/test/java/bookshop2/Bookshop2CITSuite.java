package bookshop2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bookshop2.buyer.BuyerCITSuite;
import bookshop2.seller.SellerCITSuite;
import bookshop2.shipper.ShipperCITSuite;
import bookshop2.supplier.SupplierCITSuite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	BuyerCITSuite.class,
	SellerCITSuite.class,
	SupplierCITSuite.class,
	ShipperCITSuite.class
})
public class Bookshop2CITSuite {
}