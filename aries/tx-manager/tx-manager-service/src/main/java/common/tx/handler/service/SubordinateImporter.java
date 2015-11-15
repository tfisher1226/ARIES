package common.tx.handler.service;

import java.util.HashMap;

import org.aries.tx.TransactionContext;
import org.aries.tx.TransactionContextImpl;
import org.aries.tx.service.subordinate.SubordinateATCoordinator;

import common.tx.CoordinationConstants;
import common.tx.exception.SystemException;
import common.tx.model.context.CoordinationContext;
import common.tx.model.context.CoordinationContextType;
import common.tx.service.coordinator.ContextFactoryImple;


/**
 * class to manage association of incoming AT transactions with subordinate AT transactions
 * coordinated by the local coordination servcie
 */
public class SubordinateImporter {

	private static HashMap<String, TransactionContext> subordinateContextMap = new HashMap<String, TransactionContext>();

	private static ContextFactoryImple atContextFactory = (ContextFactoryImple) ContextFactoryMapper.getMapper().getContextFactory(CoordinationConstants.WSAT_PROTOCOL);


	public static TransactionContext importContext(CoordinationContextType coordinationContext) {
		// get the subordinate transaction manager to install any existing
		// subordinate tx for this one or create and install a new one.
		final String identifier = coordinationContext.getIdentifier().getValue();
		TransactionContext subordinateTransactionContext = subordinateContextMap.get(identifier);
		if (subordinateTransactionContext == null) {
			// create a context for a local coordinator
			CoordinationContext context = null;
			try {
				context = atContextFactory.create(CoordinationConstants.WSAT_PROTOCOL, 0L, coordinationContext, false);
			} catch (SystemException e) {
				// should not happen
			}
			subordinateTransactionContext = new TransactionContextImpl(context);
			subordinateContextMap.put(identifier, subordinateTransactionContext);

			// register a cleanup callback with the subordinate transactionso that the entry gets removed
			// when the transcation commits or rolls back

			String subordinateId = context.getIdentifier().getValue().substring(4); // remove "urn:" prefix
			SubordinateATCoordinator.SubordinateCallback callback = new SubordinateATCoordinator.SubordinateCallback() {
				public String parentId = identifier;
				
				public void run() {
					subordinateContextMap.remove(parentId);
				}
			};
			SubordinateATCoordinator.addCallback(subordinateId, callback);
		}

		return subordinateTransactionContext;
	}
}
