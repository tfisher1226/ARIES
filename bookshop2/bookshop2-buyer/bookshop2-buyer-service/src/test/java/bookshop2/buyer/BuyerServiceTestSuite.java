package bookshop2.buyer;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	BuyerServiceTestSuite_IT.class,
	BuyerServiceTestSuite_CIT.class,
})
public class BuyerServiceTestSuite {
}