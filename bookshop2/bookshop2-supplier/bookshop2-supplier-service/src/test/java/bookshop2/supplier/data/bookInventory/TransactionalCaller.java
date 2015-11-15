package bookshop2.supplier.data.bookInventory;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;


@Stateful
@TransactionAttribute(REQUIRED)
public class TransactionalCaller {
	
	public void runAction(Runnable runnable) throws Exception {
		runnable.run();
	}

}