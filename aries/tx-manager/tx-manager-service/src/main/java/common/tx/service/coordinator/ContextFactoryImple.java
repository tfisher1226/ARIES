package common.tx.service.coordinator;

import javax.xml.namespace.QName;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.Durable2PCParticipant;
import org.aries.tx.TransactionContextFactory;
import org.aries.tx.Volatile2PCParticipant;
import org.aries.tx.participant.ParticipantEngineImpl;
import org.aries.tx.participant.ParticipantProcessor;
import org.aries.tx.registration.RegistrationClient;
import org.aries.tx.service.coordinator.ATCoordinator;
import org.aries.tx.service.coordinator.CoordinatorManager;
import org.aries.tx.service.subordinate.SubordinateATCoordinator;
import org.aries.tx.service.subordinate.SubordinateDurable2PCStub;
import org.aries.tx.service.subordinate.SubordinateVolatile2PCStub;

import tx.manager.registry.ServiceRegistry;

import com.arjuna.ats.arjuna.coordinator.ActionStatus;
import common.tx.CoordinationConstants;
import common.tx.InstanceIdentifier;
import common.tx.exception.NoActivityException;
import common.tx.exception.SystemException;
import common.tx.exception.WrongStateException;
import common.tx.handler.service.ContextFactoryMapper;
import common.tx.model.context.CoordinationContext;
import common.tx.model.context.CoordinationContextType;
import common.tx.model.context.Expires;
import common.tx.service.registration.RegistrarImple;


//@ContextProvider(coordinationType = ArjunaContextImple.coordinationType, serviceType = ArjunaContextImple.serviceType, contextImplementation = ArjunaContextImple.class)
public class ContextFactoryImple implements ContextFactory {

	private static Log log = LogFactory.getLog(ContextFactory.class);
	
	private CoordinatorManager coordinatorManager;

	private RegistrarImple registrar;


	public ContextFactoryImple() {
		try {
			coordinatorManager = new CoordinatorManager();
			registrar = new RegistrarImple();
			registrar.setCoordinator(coordinatorManager);
			// install the factory for the mapper to locate
			ContextFactoryMapper.getMapper().addContextFactory(CoordinationConstants.WSAT_PROTOCOL, this);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public CoordinatorManager getCoordinatorManager() {
		return coordinatorManager;
	}

	public RegistrarImple getRegistrar() {
		return registrar;
	}

	/**
	 * Called when a context factory is added to a context factory mapper. This
	 * method will be called multiple times if the context factory is added to
	 * multiple context factory mappers or to the same context mapper with
	 * different protocol identifiers.
	 *
	 * @param coordinationTypeURI
	 *            the coordination type uri
	 */
	public void install(String coordinationTypeURI)
	{
	}

	/**
	 * Creates a coordination context.
	 *
	 * @param coordinationTypeURI
	 *            the coordination type uri
	 * @param expires
	 *            the expire date/time for the returned context, can be null
	 * @param currentContext
	 *            the current context, can be null
	 *
	 * @return the created coordination context
	 *
	 * @throws com.arjuna.wsc.InvalidCreateParametersException
	 *             if a parameter passed is invalid this activity identifier.
	 *
	 */
	public CoordinationContext create(String coordinationTypeURI, Long expires, CoordinationContextType currentContext, boolean isSecure) throws SystemException {
		if (coordinationTypeURI.equals(CoordinationConstants.WSAT_PROTOCOL)) {
			try {
				if (currentContext == null) {
					// make sure no transaction is currently associated

					//TODO _coordManager.suspend();

					int timeout;
					if (expires == null) {
						timeout = 0;
					} else {
						long timeoutVal = expires.longValue();
						timeout = (timeoutVal > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) timeoutVal);
					}

					//_coordManager.begin(ArjunaContextImple.serviceType, timeout);
					String transactionId = coordinatorManager.begin();

					//ArjunaContextImple arjunaContext = ArjunaContextImple.getContext();
					ServiceRegistry serviceRegistry = ServiceRegistry.getInstance();
					String registrationCoordinatorURI = serviceRegistry.getServiceURI(CoordinationConstants.REGISTRATION_SERVICE_NAME);

					CoordinationContext coordinationContext = new CoordinationContext();
					coordinationContext.setCoordinationType(coordinationTypeURI);
					CoordinationContextType.Identifier identifier = new CoordinationContextType.Identifier();

					//TODO make transactionId come from transaction context 
					identifier.setValue("urn:" + transactionId);
					coordinationContext.setIdentifier(identifier);
					
					//TODO make transactionExpires come from transaction context 
					int transactionExpires = 0; 
					if (transactionExpires > 0) {
						Expires expiresInstance = new Expires();
						expiresInstance.setValue(transactionExpires);
						coordinationContext.setExpires(expiresInstance);
					}
					
					//TODO make transactionId come from transaction context 
					W3CEndpointReference registrationCoordinator = getRegistrationCoordinator(registrationCoordinatorURI, transactionId);
					coordinationContext.setRegistrationService(registrationCoordinator);

					/*
					 * Now add the registrar for this specific coordinator to the
					 * mapper.
					 */
					CleanupSynchronization synchronization = new CleanupSynchronization(transactionId, registrar);
					coordinatorManager.enlistSynchronization(transactionId, synchronization);

					/*
					 * TODO Uughh! This does a suspend for us! Left over from original
					 * WS-AS stuff.
					 *
					 * TODO
					 * REFACTOR, REFACTOR, REFACTOR.
					 */

					registrar.associate();
					return coordinationContext;
					
				} else {
					// we need to create a subordinate transaction and register it as both a durable and volatile
					// participant with the registration service defined in the current context

					SubordinateATCoordinator subTx = (SubordinateATCoordinator) createSubordinate();
					// hmm, need to create wrappers here as the subTx is in WSCF which only knows
					// about WSAS and WS-C and the participant is in WS-T
					String vtppid = subTx.getVolatile2PhaseId();
					String dtppid = subTx.getDurable2PhaseId();
					Volatile2PCParticipant vtpp = new SubordinateVolatile2PCStub(subTx);
					Durable2PCParticipant dtpp = new SubordinateDurable2PCStub(subTx);
					
					//String messageId = MessageId.getMessageId();
					W3CEndpointReference participant;
					W3CEndpointReference coordinator;

					participant = getParticipant(vtppid, isSecure);
					coordinator = RegistrationClient.getInstance().register(currentContext, participant, CoordinationConstants.WSAT_SUB_PROTOCOL_VOLATILE_2PC, null);//TODO
					ParticipantProcessor.getProcessor().activateParticipant(new ParticipantEngineImpl(vtpp, vtppid, coordinator), vtppid);
					
					participant = getParticipant(dtppid, isSecure);
					coordinator = RegistrationClient.getInstance().register(currentContext, participant, CoordinationConstants.WSAT_SUB_PROTOCOL_DURABLE_2PC, null);//TODO
					ParticipantProcessor.getProcessor().activateParticipant(new ParticipantEngineImpl(dtpp, dtppid, coordinator), dtppid);

					// ok now create the context
					ServiceRegistry serviceRegistry = ServiceRegistry.getInstance();
					String registrationCoordinatorURI = serviceRegistry.getServiceURI(CoordinationConstants.REGISTRATION_SERVICE_NAME);

					CoordinationContext coordinationContext = new CoordinationContext();
					coordinationContext.setCoordinationType(coordinationTypeURI);
					CoordinationContextType.Identifier identifier = new CoordinationContextType.Identifier();

					//TODO make transactionId come from transaction context 
					String transactionId = subTx.get_uid().stringForm();
					identifier.setValue("urn:"+transactionId);
					coordinationContext.setIdentifier(identifier);

					Expires expiresInstance = currentContext.getExpires();
					long transactionExpires = (expiresInstance != null ? expiresInstance.getValue() : 0);
					if (transactionExpires > 0) {
						expiresInstance = new Expires();
						expiresInstance.setValue(transactionExpires);
						coordinationContext.setExpires(expiresInstance);
					}
					W3CEndpointReference registrationCoordinator = getRegistrationCoordinator(registrationCoordinatorURI, transactionId);
					coordinationContext.setRegistrationService(registrationCoordinator);

					// now associate the tx id with the sub transaction

					registrar.associate(subTx);
					return coordinationContext;
				}
				
			} catch (SystemException e) {
				log.error("Error", e);
				throw e;
				
			} catch (NoActivityException e) {
				log.error("No activity", e);
				throw new SystemException(e);
				
			} catch (WrongStateException e) {
				log.error("Wrong state", e);
				throw new SystemException(e);
				
			} catch (Exception e) {
				// TODO handle properly
				throw new SystemException(e);
			}
			
		} else {
			String message = "Invalid type URI: "+" <"+CoordinationConstants.WSAT_PROTOCOL+", "+coordinationTypeURI+">";
			log.warn(message);
			throw new SystemException(message);
		}
	}

	/**
	 * class used to return data required to manage a bridged to subordinate transaction
	 */
	public class BridgeTxData {
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
	public BridgeTxData createBridgedTransaction (String subordinateType, Long expires, boolean isSecure) {
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

		try {
			registrar.associate(subTransaction);
		} catch (Exception e) {
			// will not happen
		}
		
		BridgeTxData bridgeTxData = new BridgeTxData();
		bridgeTxData.context = coordinationContext;
		bridgeTxData.coordinator = subTransaction;
		bridgeTxData.identifier = transactionId;
		return bridgeTxData;
	}

	private W3CEndpointReference getParticipant(String id, boolean isSecure) {
		QName serviceName = CoordinationConstants.PARTICIPANT_SERVICE_QNAME;
		QName endpointName = CoordinationConstants.PARTICIPANT_ENDPOINT_QNAME;
		String address = ServiceRegistry.getInstance().getServiceURI(CoordinationConstants.PARTICIPANT_SERVICE_NAME);
		W3CEndpointReferenceBuilder builder = new W3CEndpointReferenceBuilder();
		builder.serviceName(serviceName);
		builder.endpointName(endpointName);
		builder.address(address);
		InstanceIdentifier.setEndpointInstanceIdentifier(builder, id);
		return builder.build();
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

	/**
	 * Called when a context factory is removed from a context factory mapper.
	 * This method will be called multiple times if the context factory is
	 * removed from multiple context factory mappers or from the same context
	 * factory mapper with different coordination type uris.
	 *
	 * @param coordinationTypeURI
	 *            the coordination type uri
	 */
	public void uninstall (String coordinationTypeURI) {
		// we don't use this as one implementation is registered per type
	}

	public Object createSubordinate() throws NoActivityException, SystemException {
		return createSubordinate(SubordinateATCoordinator.SUBORDINATE_TX_TYPE_AT_AT);
	}

	public ATCoordinator createSubordinate(String subordinateType) throws NoActivityException, SystemException {
		try {
			ATCoordinator subordinateCoordinator = createSubordinateCoordinator(subordinateType);
			subordinateCoordinator.enlistSynchronization(new CleanupSynchronization(subordinateCoordinator.get_uid().stringForm(), registrar));
			registrar.associate(subordinateCoordinator);
			return subordinateCoordinator;
		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		}
	}

	public ATCoordinator createSubordinateCoordinator(String subordinateType) throws SystemException {	
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

}
