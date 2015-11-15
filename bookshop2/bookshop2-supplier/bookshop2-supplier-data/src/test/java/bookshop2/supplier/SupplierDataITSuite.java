package bookshop2.supplier;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	BookInventoryITSuite.class,
	BookOrderLogITSuite.class
})
public class SupplierDataITSuite {
}