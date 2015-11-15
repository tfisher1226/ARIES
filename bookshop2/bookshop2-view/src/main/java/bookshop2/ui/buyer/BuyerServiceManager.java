package bookshop2.ui.buyer;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.MessageListener;

import org.aries.Assert;
import org.aries.Handler;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractInvocationManager;
import org.aries.ui.InvocationValues;
import org.aries.ui.JmxEventManager;

import bookshop2.OrderAcceptedMessage;
import bookshop2.OrderRejectedMessage;
import bookshop2.OrderRequestMessage;
import bookshop2.buyer.client.orderBooks.OrderBooks;
import bookshop2.buyer.client.orderBooks.OrderBooksClient;
import bookshop2.util.Bookshop2Fixture;


@SessionScoped
@Named("buyerServiceManager")
public class BuyerServiceManager extends AbstractInvocationManager implements Serializable {

	@Inject
	private JmxEventManager jmxEventManager;
	
	
	public BuyerServiceManager() throws Exception {
		initializeOrderBooks();
		initializePurchaseBooks();
	}
	
//	//SEAM @Observer("org.aries.reset")
//	public void handleReset(@Observes @Updated PersonName personName) {
//	}
	
	protected BuyerInfoManager getBuyerInfoManager() {
		return BeanContext.getFromSession("buyerInfoManager");
	}
	
	protected OrderBooksClient getOrderBooksClient() {
		OrderBooksClient orderBooks = BeanContext.getFromSession(OrderBooks.ID);
		orderBooks.setTransportType(getTransportType());
		return orderBooks;
	}
	
	public void initializeOrderBooks() {
		InvocationValues invocationValues = getInvocationValues("orderBooks");
		invocationValues.addParameterPlaceholder("OrderRequestMessage", "orderRequestMessage");
	}
	
	public void initializePurchaseBooks() {
		InvocationValues invocationValues = getInvocationValues("purchaseBooks");
		invocationValues.addParameterPlaceholder("PurchaseRequestMessage", "purchaseRequestMessage");
	}
	
	//SEAM @Observer("orderRequestMessageEntered")
	public void handleOrderRequestMessageEntered() {
		BuyerInfoManager manager = getBuyerInfoManager();
		OrderRequestMessage orderRequestMessage = manager.getOrderRequestMessage();
		setParameterValue("orderBooks", "orderRequestMessage", orderRequestMessage);
	}
	
	
//	public Map<PhoneLocation, PhoneNumber> getPhoneNumbers(OrderRequestMessage orderRequestMessage) {
//		if (user == null)
//			return null;
//		Map<PhoneLocation, PhoneNumber> map = new HashMap<PhoneLocation, PhoneNumber>();
//		PhoneNumber cellPhone = user.getCellPhone();
//		PhoneNumber homePhone = user.getHomePhone();
//		if (cellPhone != null)
//			map.put(PhoneLocation.CELL, cellPhone);
//		if (homePhone != null)
//			map.put(PhoneLocation.HOME, homePhone);
//		return map;
//	}

	
	
//	public String get_local_Buyer_OrderAccepted_destination() {
//		return "test_message_domain_test_destination_queue_a";
//	}
//	
//	public String get_local_Buyer_OrderRejected_destination() {
//		return "test_message_domain_test_destination_queue_b";
//	}
//	
//	protected JmsClient start_local_Supplier_BooksAvailable_handler() throws Exception {
//		JmsClient client = createClient(getJNDINameForQueue(get_local_Buyer_OrderAccepted_destination()));
//		client.setMessageListener(create_local_Supplier_BooksAvailable_response_listener());
//		client.initialize();
//		return client;
//	}
//	
//	protected MessageListener create_local_Supplier_BooksAvailable_response_listener() {
//		return new MessageListener() {
//			public void onMessage(Message message) {
//				try {
//					String jmsMessageID = message.getJMSMessageID();
//					log.info("#### [test]: BooksAvailable: received: "+jmsMessageID);
//					Object object = MessageUtil.getPart(message, "booksAvailableMessage");
//					Assert.isTrue(object instanceof BooksAvailableMessage, "Payload type not correct");
//					BooksAvailableMessage booksAvailableMessage = (BooksAvailableMessage) object;
//					validateMessage(booksAvailableMessage);
//				} catch (Throwable e) {
//					errorMessage = ExceptionUtils.getRootCauseMessage(e);
//					e.printStackTrace();
//				} finally {
//					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.supplier.BooksAvailable"))
//						expectedMessageResult.set(true);
//					booksAvailableReceived = true;
//				}
//			}
//		};
//	}
//
//	protected String getJNDINameForQueue(String destinationName) {
//		return getJNDINameForDestination(destinationName, "queue");
//	}
//
//	protected String getJNDINameForTopic(String destinationName) {
//		return getJNDINameForDestination(destinationName, "topic");
//	}
//
//	protected String getJNDINameForDestination(String destinationName, String typeName) {
//		if (!destinationName.startsWith("jms/" + typeName))
//			return "jms/" + typeName + "/" + destinationName;
//		return destinationName;
//	}
//
//	
//	protected JmsClient createClient(String destination) throws Exception {
//		JmsClient client = new JmsClient();
//		configureClient(client, destination);
//		//client.initialize();
//		return client;
//	}
//	
//	protected void configureClient(JmsClient client, String destination) throws Exception {
//		configureClient(client);
//		client.setDestinationName(destination);
//	}
//	
//	protected void configureClient(JmsClient client) throws Exception {
//		client.setUserName("testuser");
//		client.setPassword("password");
//		client.setInitialContextProperties(configuration.getInitialContextProperties());
//		client.setConnectionFactoryName("/ConnectionFactory");
//	}
//	





	private Map<String, Handler<?>> handlers;
	
	private MessageListener responseListener;
	
	public void executeOrderBooks() {
		String correlationId = "sampleCorrelationId";
		String transactionId = "sampleTransactionId";

		try {
			InvocationValues invocationValues = getInvocationValues();
			OrderRequestMessage orderRequestMessage = Bookshop2Fixture.create_OrderRequestMessage();
			//OrderRequestMessage orderRequestMessage = invocationValues.getParameterValue("orderRequestMessage");
			Assert.notNull(orderRequestMessage, "OrderRequestMessage parameter must be specified");
//			orderRequestMessage.setAddress(streetAddress);
//			orderRequestMessage.setPayment(payment);
//			orderRequestMessage.setName(personName);
//			orderRequestMessage.setCancelRequest(cancelRequest);
//			orderRequestMessage.setDateTime(dateTime);
//			orderRequestMessage.setUndoRequest(undoRequest);
			orderRequestMessage.setCorrelationId(correlationId);
			orderRequestMessage.setTransactionId(transactionId);
			
			OrderBooksClient orderBooksClient = getOrderBooksClient();
			//orderBooksClient.setCorrelationId("sampleCorrelationId");
			//orderBooksClient.setTransactionId("sampleTransactionId");
			orderBooksClient.addResponseHandler("OrderAcceptedMessage", createOrderAcceptedHandler());
			orderBooksClient.addResponseHandler("OrderRejectedMessage", createOrderRejectedHandler());
			
			//JmxEventHandler jmxEventHandler = new JmxEventHandler();
			jmxEventManager = BeanContext.getFromSession("jmxEventManager");
			jmxEventManager.initialize();
			
			orderBooksClient.orderBooks(orderRequestMessage);
		} catch (Exception e) {
			handleException(e);
		}
	}

//	public <T extends Serializable> void addResponseHandler(String messageClass, Handler<T> handler) {
//		if (handlers == null)
//			handlers = new HashMap<String, Handler<?>>();
//		handlers.put(messageClass, handler);
//		if (responseListener == null)
//			responseListener = createResponseListener();
//	}
	
	protected Handler<OrderAcceptedMessage> createOrderAcceptedHandler() {
		return new Handler<OrderAcceptedMessage>() {
			public void handle(OrderAcceptedMessage orderAcceptedMessage) {
				jmxEventManager = BeanContext.getFromSession("jmxEventManager");
				jmxEventManager.setRunning(false);
			}
		};
	}
	
	protected Handler<OrderRejectedMessage> createOrderRejectedHandler() {
		return new Handler<OrderRejectedMessage>() {
			public void handle(OrderRejectedMessage orderRejectedMessage) {
				
				//TODO org.aries.ui.JmxEventListManager cannot be cast to org.aries.ui.JmxEventManager
				
				jmxEventManager = BeanContext.getFromSession("jmxEventManager");
				jmxEventManager.setRunning(false);
			}
		};
	}
	
//	protected MessageListener createResponseListener() {
//		return new MessageListener() {
//			public void onMessage(Message message) {
//				try {
//					String jmsMessageID = message.getJMSMessageID();
//					Object response = MessageUtil.getPart(message, "response");
//					Assert.notNull(response, "Response not found for: "+jmsMessageID);
//					Class<?> classObject = response.getClass();
//					String key = classObject.getName();
//					Handler handler = handlers.get(key);
//					Assert.notNull(handler, "Handler not found: "+key);
//					handler.handle(classObject.cast(response));
//					
//				} catch (JMSException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		};
//	}

	

//	private JmxProxy jmxProxy;
//	
//	private JmxManager jmxManager;
//	
//	private NotificationListener notificationListener;
//	
//	private Map<Object, FutureResult<Object>> notificationListenerMap;
//	
//	private Map<String, Map<String, Notification>> notificationStore;
	
//	protected ExecutorService executor;
//	
//	
//    protected ExecutorService createExecutor() {
//        return createExecutor(1);
//    }
//    
//    protected ExecutorService createExecutor(int poolSize) {
//    	SynchronousQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();
//		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, poolSize, 1, TimeUnit.MINUTES, workQueue);
//		executor.prestartAllCoreThreads();
//		return executor;
//    }

	
//	protected void registerNotificationListeners() throws Exception {
//		addRequestNotificationListeners("Buyer_OrderBooks");
//		addResponseNotificationListeners("Buyer_OrderAccepted");
//		addResponseNotificationListeners("Buyer_OrderRejected");
//		addRequestNotificationListeners("Seller_OrderBooks");
//		addResponseNotificationListeners("Seller_OrderAccepted");
//		addResponseNotificationListeners("Seller_OrderRejected");
//		addRequestNotificationListeners("Seller_PurchaseBooks");
//		addResponseNotificationListeners("Seller_PurchaseAccepted");
//		addResponseNotificationListeners("Seller_PurchaseRejected");
//		//addRequestNotificationListeners("Seller_OrderBooks");
//		//addResponseNotificationListeners("Seller_OrderAccepted");
//		//addResponseNotificationListeners("Seller_OrderRejected");
//		//addRequestNotificationListeners("Supplier_QueryBooks");
//		//addResponseNotificationListeners("Supplier_BooksAvailable");
//		//addResponseNotificationListeners("Supplier_BooksUnavailable");
//		//addRequestNotificationListeners("Supplier_ReserveBooks");
//		registerNotificationListeners2();
//	}
	
//	protected void registerNotificationListeners2() throws Exception {
//		if (notificationListener != null)
//			throw new Exception("NotificationListener already initialized");
//		ObjectName objectName = new ObjectName(ServiceEventDispatcher.MBEAN_NAME);
//		notificationListener = createNotificationListener();
//		jmxManager.addNotificationListener(objectName, notificationListener);
//		System.out.println("JMX Listener["+this.hashCode()+"]: registered");
//	}
	
	
//	protected void addMessageNotificationListeners(String serviceId) throws Exception {
//		addNotificationListener(serviceId + "_Message_Sent");
//		addNotificationListener(serviceId + "_Message_Received");
//		addNotificationListener(serviceId + "_Message_Dispatch_Done");
//		addNotificationListener(serviceId + "_Message_Dispatch_Aborted");
//	}
//	
//	protected void addRequestNotificationListeners(String serviceId) throws Exception {
//		addNotificationListener(serviceId + "_Request_Sent");
//		addNotificationListener(serviceId + "_Request_Received");
//		addNotificationListener(serviceId + "_Request_Handled");
//		addNotificationListener(serviceId + "_Request_Done");
//		addNotificationListener(serviceId + "_Request_Cancel_Done");
//		addNotificationListener(serviceId + "_Request_Undo_Done");
//		addNotificationListener(serviceId + "_Incoming_Request_Aborted");
//		addNotificationListener(serviceId + "_Outgoing_Request_Aborted");
//	}
//	
//	protected void addResponseNotificationListeners(String callbackId) throws Exception {
//		addNotificationListener(callbackId + "_Response_Sent");
//		addNotificationListener(callbackId + "_Response_Handled");
//		addNotificationListener(callbackId + "_Response_Received");
//		addNotificationListener(callbackId + "_Response_Done");
//		addNotificationListener(callbackId + "_Incoming_Response_Aborted");
//		addNotificationListener(callbackId + "_Outgoing_Response_Aborted");
//	}
//	
//	protected void addProcessNotificationListeners(String eventId) throws Exception {
//		addNotificationListener(eventId + "_Process_Complete");
//		addNotificationListener(eventId + "_Process_Aborted");
//	}
//	
//	protected void addNotificationListener(String notificationId) throws Exception {
//		addNotificationListener(notificationId, null);
//	}
//	
//	protected void addNotificationListener(Object notificationId, FutureResult<Object> futureResult) throws Exception {
//		if (notificationListenerMap == null)
//			notificationListenerMap = new HashMap<Object, FutureResult<Object>>();
//		notificationListenerMap.put(notificationId, futureResult);
//	}


//	protected NotificationListener createNotificationListener() {
//		return new NotificationListener() {
//			public void handleNotification(Notification notification, Object handback) {
//				//Object userData = notification.getUserData();
//				if (notification instanceof ServiceEventNotification) {
//					ServiceEventNotification serviceEventNotification = (ServiceEventNotification) notification;
//					String notificationId = serviceEventNotification.getEventId();
//					String correlationId = serviceEventNotification.getCorrelationId();
//					System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$  JMX Listener["+this.hashCode()+"] type["+notificationId+"]: received");
//					recordNotification(correlationId, notificationId, notification);
//					FutureResult<Object> futureResult = notificationListenerMap.get(notificationId);
//					if (futureResult != null)
//						futureResult.set(notification);
//					System.out.println("JMX Listener["+this.hashCode()+"] type["+notificationId+"]: dispatched");
//					
//					JmxEvent jmxEvent = new JmxEvent();
//					jmxEvent.setSequenceNumber(notification.getSequenceNumber());
//					jmxEvent.setTimeStamp(new Date(notification.getTimeStamp()));
//					jmxEvent.setEventId(notificationId);
//					jmxEvent.setCorrelationId(correlationId);
//					jmxEvent.setMessage((String) notification.getUserData());
//					jmxEvent.setData((Serializable) notification.getUserData());
//					
////					ObjectName objectName = MBeanUtil.makeObjectName(JmxEventHandlerMBean.MBEAN_NAME);
////					JmxEventHandlerMBean fooProxy = JMX.newMBeanProxy(jmxManager.getMBeanServerConnection(), objectName, JmxEventHandlerMBean.class);
////					fooProxy.dispatch(jmxEvent);
//
//					String[] signature = new String[] {"java.lang.String", "org.aries.ui.JmxEvent"};
//					Object[] parameters = new Object[] {correlationId, jmxEvent};
//					jmxProxy.call(JmxEventHandlerMBean.MBEAN_NAME, "dispatch", signature, parameters);
//				}
//			}
//		};
//	}

	
//	protected Runnable createHandler(final JmxEvent jmxEvent) {
//		return new Runnable() {
//			public void run() {
//				JmxEventListManager jmxEventListManager = BeanContext.getBeanByName("jmxEventListManager");
//				jmxEventListManager.addJmxEvent(jmxEvent);
//			}
//		};
//	}
	
//	protected boolean isNotificationExist(String correlationId, String serviceId) {
//		if (notificationStore == null)
//			notificationStore = new HashMap<String, Map<String, Notification>>();
//		Map<String, Notification> map = notificationStore.get(correlationId);
//		if (map != null)
//			return map.containsKey(serviceId);
//		return false;
//	}
//	
//	protected void recordNotification(String correlationId, String serviceId, Notification notification) {
//		if (notificationStore == null)
//			notificationStore = new HashMap<String, Map<String, Notification>>();
//		Map<String, Notification> map = notificationStore.get(correlationId);
//		if (map == null) {
//			map = new HashMap<String, Notification>();
//			notificationStore.put(correlationId, map);
//		}
//		map.put(serviceId, notification);
//	}
	
}
