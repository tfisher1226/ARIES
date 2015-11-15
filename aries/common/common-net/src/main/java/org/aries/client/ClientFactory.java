package org.aries.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.aries.notification.NotificationDispatcher;
import org.aries.runtime.BeanContext;
import org.aries.util.ClassUtil;


public class ClientFactory {

	public static Client createClient(String className) throws ClassNotFoundException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Class<?> clientInterface = ClassUtil.loadClass(className, classLoader);
		Class<?>[] interfacesArray = new Class<?>[] { clientInterface, Client.class };

		Handler handler = new Handler();
		//Class<?> proxyClass = Proxy.getProxyClass(classLoader, interfacesArray);
		Object newInstance = Proxy.newProxyInstance(classLoader, interfacesArray , handler);

		//Constructor<?> constructor = proxyClass.getConstructor(new Class[] { InvocationHandler.class });
		//Client client = (Client) constructor.newInstance(new Object[] { handler });

		Client client = (Client) newInstance;
		return client;
	}


	protected static class Handler implements InvocationHandler {

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//			Operation operation = new Operation();
//			operation.setName(method.getName());
//			operation.getParameters().addAll(createParameters(method, args));
//			operation.setResult(createResult(method.getReturnType()));
			if (method.getName().equals("setCorrelationId")) {
				fireClientInvokedNotification(null);
			}
			return null;
		}
		
		protected void fireClientInvokedNotification(Object userData) throws Exception {
			NotificationDispatcher dispatcher = BeanContext.get("bookshop.buyer.notificationDispatcher");
			dispatcher.fireClientInvokedNotification(this, userData);
		}
		
//		private Collection<Parameter> createParameters(Method method, Object[] args) {
//			Collection<Parameter> parameters = new ArrayList<Parameter>();
//			Class<?>[] parameterTypes = method.getParameterTypes();
//			for (int i = 0; i < args.length; i++) {
//				Object parameterValue = args[i];
//				String className = parameterTypes[i].getCanonicalName();
//				//Class<?> loadClass = ClassUtil.loadClass(className);
//				Parameter parameter = new Parameter();
//				parameter.setName(parameterValue.toString());
//				parameter.setType(className);
//				//parameter.set
//				parameters.add(parameter);
//			}
//			return parameters;
//		}

//		private Result createResult(Class<?> returnType) {
//			Result result = new Result();
//			result.setType(returnType.getName());
//			result.setName("result");
//			return result;
//		}
	} 

//	protected static class NotificationDispatcher extends NotificationBroadcasterSupport implements NotificationEmitter {
//		
//	    @Override
//	    public MBeanNotificationInfo[] getNotificationInfo() {
//	        String[] notificationTypes = new String[] { ClientInvokedNotification.NOTIFICATION_ID };
//	        String className = AttributeChangeNotification.class.getName();
//	        String description = ClientInvokedNotification.DESCRIPTION;
//	        MBeanNotificationInfo notificationInfo = new MBeanNotificationInfo(notificationTypes, className, description);
//	        MBeanNotificationInfo[] notificationInfoArray = new MBeanNotificationInfo[] { notificationInfo };
//			return notificationInfoArray;
//	    }
//	    
//	}

//	public void sendClientInvokedNotification(Map<String, Object> userData) {
//		try {
//			ClientInvokedNotification notification = new ClientInvokedNotification();
//			notification.setUserData(userData);
//			sendNotification(notification, 0);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
