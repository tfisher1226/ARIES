package bookshop2.shipper;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	ShipperServiceITSuite.class,
	ShipperServiceCITSuite.class,
})
public class ShipperServiceTestSuite {
}