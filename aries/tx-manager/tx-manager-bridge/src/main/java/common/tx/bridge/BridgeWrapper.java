package common.tx.bridge;

import javax.xml.ws.wsaddressing.W3CEndpointReference;
import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;

import org.aries.tx.TransactionContext;
import org.aries.tx.TransactionContextFactory;
import org.aries.tx.TransactionContextImpl;
import org.aries.tx.recovery.participant.XTSATRecoveryManager;
import org.aries.tx.service.coordinator.ATCoordinator;
import org.aries.tx.service.subordinate.SubordinateATCoordinator;

import tx.manager.registry.ServiceRegistry;

import com.arjuna.ats.arjuna.coordinator.ActionStatus;
import common.tx.CoordinationConstants;
import common.tx.InstanceIdentifier;
import common.tx.exception.NoActivityException;
import common.tx.exception.SystemException;
import common.tx.exception.UnknownTransactionException;
import common.tx.model.context.CoordinationContext;


/**
 * An API class for use by the JTA ==> AT bridge manager providing a wrapper around a subordinate transaction.
 * Static methods are provided to create and register a WS-AT subordinate transaction or to locate a recovered
 * transaction. The returned wrapper allows a client to drive the coordinator through prepare, (phase 2) commit
 * and/or rollback, to access the transaction id under which the coordinator is registered and, if the transaction
 * has not been recovered, to obtain a resumable tx context for the transaction.
 *
 * n.b. this class only supports bridging to WS-AT 1.1 transactions.
 */
public class BridgeWrapper {

	/**
	 * cached reference to the WS-AT 1.1. context factory - we only support bridging to WS-AT 1.1 subordinate
	 * transactions
	 */
//	private static ContextFactoryImple contextFactory = (ContextFactoryImple) 
//	ContextFactoryMapper.getMapper().getContextFactory(CoordinationConstants.WSAT_PROTOCOL);

	private final static BridgeWrapper[] EMPTY_SCAN = new BridgeWrapper[0];

	/**
	 * the standard type string used to identify AT AT subordinate transactions. bridge clients
	 * must ensure that they do not employ this type for their subordinates.
	 */

	public static final String SUBORDINATE_TX_TYPE_AT_AT = SubordinateATCoordinator.SUBORDINATE_TX_TYPE_AT_AT;


	/**
	 * create an AT 1.1 subordinate transaction, associate it with the AT 1.1. registry then return a
	 * BridgedTransaction wrapper allowing the transaction to be driven through prepare, commit
	 * and/or rollback and providing access to the transaction id and a context which can be used to
	 * resume the transaction.
	 * @param subordinateType a unique string which groups subordinates for the benefit of their parent
	 * tx/app and allows them to be identified and retrieved as a group during recovery. this must differ
	 * from the string {@link SUBORDINATE_TX_TYPE_AT_AT}
	 * @param expires the timeout for the bridged-to transaction or 0 if no timeout is required
	 * @param isSecure true if AT 1.1. protocol messages for the bridged-to transaction should employ
	 * secure communications, otherwise false
	 * @return a wrapper for the bridged-to transaction
	 * @throws SystemException
	 */
	public static BridgeWrapper create(String subordinateType, long expires, boolean isSecure) {
		// the AT 1.1 context factory provides us with a means to create the required data.

		BridgeTxData bridgeTxData = createBridgedTransaction(subordinateType, expires, isSecure);
		if (bridgeTxData != null) {
			BridgeWrapper bridgeWrapper = new BridgeWrapper();
			bridgeWrapper.context = new TransactionContextImpl(bridgeTxData.context);
			bridgeWrapper.coordinator = bridgeTxData.coordinator;
			bridgeWrapper.id = bridgeTxData.identifier;
			bridgeWrapper.subordinateType = subordinateType;
			return bridgeWrapper;
		} else {
			return null;
		}
	}
	
	/**
	 * class used to return data required to manage a bridged to subordinate transaction
	 */
	public static class BridgeTxData {
		public CoordinationContext context;
		public SubordinateATCoordinator coordinator;
		public String identifier;
	}

	/**
	 * create a bridged to subordinate WS-AT 1.1 transaction, associate it with the registrar and create and return
	 * a coordination context for it. n.b. this is a private, behind-the-scenes method for use by the JTA-AT
	 * transaction bridge code.
	 * @param subordinateType a unique string which groups subordinates for the benefit of their parent tx/app and
	 * allows them to be identified and retrieved as a group during recovery
	 * @param expires the timeout for the bridged to AT transaction
	 * @param isSecure true if the registration cooridnator URL should use a secure address, otherwise false.
	 * @return a coordination context for the bridged to transaction
	 */
	public static BridgeTxData createBridgedTransaction (String subordinateType, Long expires, boolean isSecure) {
		// we must have a type and it must not be the AT-AT subordinate type
		if (subordinateType == null || SubordinateATCoordinator.SUBORDINATE_TX_TYPE_AT_AT.equals(subordinateType)) {
			return null;
		}
		// we need to create a subordinate transaction and register it as both a durable and volatile
		// participant with the registration service defined in the current context

		SubordinateATCoordinator subTransaction = null;
		try {
			subTransaction = (SubordinateATCoordinator) createSubordinate(subordinateType);
		} catch (NoActivityException e) {
			// will not happen
			return null;
		} catch (SystemException e) {
			// may happen
			return null;
		}

		// ok now create the context

		ServiceRegistry serviceRegistry = ServiceRegistry.getInstance();
		String registrationCoordinatorURI = serviceRegistry.getServiceURI(CoordinationConstants.REGISTRATION_SERVICE_NAME);

		String transactionId = subTransaction.get_uid().stringForm();
		CoordinationContext coordinationContext = TransactionContextFactory.createCoordinationContext(transactionId, expires);
		
		W3CEndpointReference registrationCoordinator = getRegistrationCoordinator(registrationCoordinatorURI, transactionId);
		coordinationContext.setRegistrationService(registrationCoordinator);

		// now associate the tx id with the sub transaction

//		try {
//			registrar.associate(subTransaction);
//		} catch (Exception e) {
//			// will not happen
//		}
		
		BridgeTxData bridgeTxData = new BridgeTxData();
		bridgeTxData.context = coordinationContext;
		bridgeTxData.coordinator = subTransaction;
		bridgeTxData.identifier = transactionId;
		return bridgeTxData;
	}
	
	private static W3CEndpointReference getRegistrationCoordinator(String registrationCoordinatorURI, String identifier) {
		W3CEndpointReferenceBuilder builder = new W3CEndpointReferenceBuilder();
		builder.serviceName(CoordinationConstants.REGISTRATION_SERVICE_QNAME);
		builder.endpointName(CoordinationConstants.REGISTRATION_ENDPOINT_QNAME);
		// strictly we shouldn't need to set the address because we are in the same web app as the
		// coordinator but we have to as the W3CEndpointReference implementation is incomplete
		builder.address(registrationCoordinatorURI);
		InstanceIdentifier.setEndpointInstanceIdentifier(builder, identifier);
		W3CEndpointReference registrationCoordinator = builder.build();
		return registrationCoordinator;
	}
	
	public static Object createSubordinate() throws NoActivityException, SystemException {
		return createSubordinate(SubordinateATCoordinator.SUBORDINATE_TX_TYPE_AT_AT);
	}

	public static ATCoordinator createSubordinate(String subordinateType) throws NoActivityException, SystemException {
		try {
			ATCoordinator subordinateCoordinator = createSubordinateCoordinator(subordinateType);
			//subordinateCoordinator.enlistSynchronization(new CleanupSynchronization(subordinateCoordinator.get_uid().stringForm(), registrar));
			//registrar.associate(subordinateCoordinator);
			return subordinateCoordinator;
		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		}
	}

	public static ATCoordinator createSubordinateCoordinator(String subordinateType) throws SystemException {	
		try {
			SubordinateATCoordinator coord = new SubordinateATCoordinator(subordinateType);
			int status = coord.start(null);
			if (status != ActionStatus.RUNNING)
				throw new SystemException("Unexpected status: "+ActionStatus.stringForm(status));
			return coord;
		} catch (SystemException e) {
			throw e;
		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		}
	}
	
	
	/**
	 * recreate a wrapper for a bridged-to WS-AT 1.1 transaction recovered from the log
	 * @param identifier the identifier of a previously created bridged-to transaction
	 * @return a wrapper for the bridged-to transaction or null if it may still be awaiting recovery
	 * @throws UnknownTransactionException if recovery has been performed and no transaction with the
	 * given identifier has been found in the log
	 */
	public static BridgeWrapper recover(String identifier) throws UnknownTransactionException {
		SubordinateATCoordinator coordinator = SubordinateATCoordinator.getRecoveredCoordinator(identifier);
		if (coordinator != null) {
			BridgeWrapper bridgeWrapper = new BridgeWrapper();
			bridgeWrapper.context = null;
			bridgeWrapper.coordinator =coordinator;
			bridgeWrapper.id = identifier;
			bridgeWrapper.subordinateType = coordinator.getSubordinateType();
			return bridgeWrapper;
		} else {
			XTSATRecoveryManager recoveryManager = XTSATRecoveryManager.getRecoveryManager();
			if (recoveryManager != null && recoveryManager.isCoordinatorRecoveryStarted())
				throw new UnknownTransactionException();
			return null;
		}
	}

	/**
	 * return a list of bridge wrappers for all recovered subordinate transactions with a given
	 * subordinate type
	 * @param subordinateType the subordinate type supplied in the original bridge wrapper create call
	 * which created the subordinate transaction.
	 * @return a possibly zero-length array of bridge wrappers for all recovered subordinate AT transactions
	 * with the given subordinate type or null if a subordinate coordinator recovery scan has not yet occurred
	 */
	public static BridgeWrapper[] scan(String subordinateType) {
		// return null if not yet ready

		XTSATRecoveryManager recoveryManager = XTSATRecoveryManager.getRecoveryManager();
		if (!recoveryManager.isCoordinatorRecoveryStarted()) {
			return null;
		}

		// refuse to expose the internal AT - AT subordinates

		if (subordinateType == null || subordinateType.equals(SUBORDINATE_TX_TYPE_AT_AT)) {
			return EMPTY_SCAN;
		}

		SubordinateATCoordinator[] coordinators = SubordinateATCoordinator.listRecoveredCoordinators();
		int count = 0;

		for (int i = 0; i < coordinators.length; i++) {
			if (coordinators[i].getSubordinateType().equals(subordinateType)) {
				count++;
			}
		}

		if (count == 0) {
			return EMPTY_SCAN;
		}

		BridgeWrapper[] result = new BridgeWrapper[count];
		count = 0;

		for (int i = 0; i < coordinators.length; i++) {
			SubordinateATCoordinator coordinator = coordinators[i];
			if (coordinator.getSubordinateType().equals(subordinateType)) {
				BridgeWrapper bridgeWrapper = new BridgeWrapper();
				bridgeWrapper.context = null;
				bridgeWrapper.coordinator = coordinator;
				bridgeWrapper.id = coordinator.get_uid().stringForm();
				bridgeWrapper.subordinateType = coordinator.getSubordinateType();
				result[count++] = bridgeWrapper;
			}
		}

		return result;
	}



	private SubordinateATCoordinator coordinator;

	private TransactionContext context;

	private String id;

	private String subordinateType;


	/**
	 * this class handles all creation of bridged transactions 
	 */
	private BridgeWrapper() {
	}

	/**
	 * obtain the identifier for the bridged-to transaction
	 * @return the identifier for the bridged-to transaction
	 */
	public String getIdentifier() {
		return id;
	}

	/**
	 * obtain a resumable transaction context for the bridged-to transaction
	 * @return a resumable transaction context
	 * @throws UnknownTransactionException if this transaction has been recovered from the log and hence
	 * has no associated transaction context.
	 */
	public TransactionContext getContext() throws UnknownTransactionException {
		if (context == null)
			throw new UnknownTransactionException();
		return context;
	}

	/**
	 * obtain the subordinate type for the bridged-to transaction
	 * @return the subordinate type for the bridged-to transaction
	 */
	public String getSubordinateType() {
		return subordinateType;
	}

	/**
	 * initiate synchronization beforeCompletion processing for the bridged-to transaction
	 * @return true if the beforeCompletion succeeds otherwise false.
	 */
	public boolean prepareVolatile() {
		return coordinator.prepareVolatile();
	}

	/**
	 * prepare the bridged-to transaction
	 * @return the result of preparing the transaction
	 */
	public int prepare() {
		return coordinator.prepare();
	}


	/**
	 * initiate synchronization afterCompletion processing for the bridged-to transaction following a
	 * successful commit
	 */
	public void commitVolatile() {
		coordinator.commitVolatile();
	}

	/**
	 * perform a phase 2 commit for the bridged-to transaction
	 */
	public void commit() {
		coordinator.commit();
	}

	/**
	 * initiate synchronization afterCompletion processing for the bridged-to transaction following a
	 * rollback
	 */
	public void rollbackVolatile() {
		coordinator.rollbackVolatile();
	}

	/**
	 * rollback the bridged-to transaction
	 */
	public void rollback() {
		coordinator.rollback();
	}

}
