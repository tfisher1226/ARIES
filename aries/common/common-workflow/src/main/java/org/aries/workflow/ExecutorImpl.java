package org.aries.workflow;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Action;
import org.aries.message.Message;
import org.aries.nam.model.old.ActionDescripter;
import org.aries.nam.model.old.ParameterDescripter;
import org.aries.nam.model.old.ProcessDescripter;
import org.aries.nam.model.old.ResultDescripter;
import org.aries.nam.model.old.ServiceDescripter;
import org.aries.runtime.BeanContext;
import org.aries.service.ServiceException;
import org.aries.util.ClassUtil;


public class ExecutorImpl implements Executor {

	protected static Log log = LogFactory.getLog(ExecutorImpl.class);
		
	private ExecutionContext executionContext;
	
	private ServiceDescripter serviceDescripter;
	
	private String executionName;
    
	private ClassLoader classLoader;

	private Message message;


	public ExecutorImpl(ServiceDescripter serviceDescripter, WebServiceContext webServiceContext) {
		executionContext = new ExecutionContext(serviceDescripter, webServiceContext);
		this.serviceDescripter = serviceDescripter;
		this.message = new Message();
	}

	public ExecutorImpl(ServiceDescripter serviceDescripter, WebServiceContext webServiceContext, String executionName) {
		this(serviceDescripter, webServiceContext);
		this.executionName = executionName;
	}

	public Object getServiceName() {
		return serviceDescripter.getServiceName();
	}

	public ProcessDescripter getProcessDescripter() {
		return serviceDescripter.getProcessDescripters().get(0);
	}
    
	protected ClassLoader getClassLoader() {
		return classLoader;
	}

	protected void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getPart(String key) {
		return (T) message.getPart(key);
	}

	@Override
	public <T> void addPart(String key, T value) {
		message.addPart(key, value);
	}

	public void initialize() throws Exception {
		//do nothing by default
	}

	public void execute() throws Exception {
		execute(message);
	}

	public Message execute(Message request) throws Exception {
		initialize();
		preProcess(request);
		Message response = process(request);
		return response;
	}
	
	protected void preProcess(Message message) {
		//do nothing by default
	}

	//assuming process exists for this service
	public Message process(Message message) throws Exception {
		//beginContext();
		preProcess(message);

		try {
			setClassLoader(Thread.currentThread().getContextClassLoader());
			log.debug("Processing started...");
	
			ProcessDescripter processDescripter = getProcessDescripter();
			Iterator<ActionDescripter> iterator = processDescripter.getActionDescripters().iterator();
			
			while (iterator.hasNext()) {
				ActionDescripter Descripter = iterator.next();
				String name = Descripter.getActionName();
				
				log.info("Finding action: "+name+", "+this);
				log.info("Class-loader: "+classLoader);
				Action instance = BeanContext.get(name);
				if (instance == null) {
					String text = "Action not found: "+name;
					throw new ServiceException(text);
				} 
	
				log.debug("Found action: "+name);
				message = invokeAction(Descripter, instance, message);
			}
	
			log.debug("Processing complete");
			return message;

		} finally {
			postProcess(message);
			//endContext();
		}
	}

	protected Message invokeAction(ActionDescripter Descripter, Action instance, Message message) throws Exception {
		List<ParameterDescripter> parameters = Descripter.getParameterDescripters();
		Iterator<ParameterDescripter> parameterIterator = parameters.iterator();
		String[] parameterTypes = new String[parameters.size()];
		Object[] parameterValues = new Object[parameters.size()];
		for (int i=0; parameterIterator.hasNext(); i++) {
			ParameterDescripter parameter = parameterIterator.next();
			String name = parameter.getName();
			String type = parameter.getType();
			Object value = message.getPart(name);
			parameterTypes[i] = ClassUtil.convertTypeToJavaClass(type);
			parameterValues[i] = value;
		}
		
		//String className = Descripter.getClassName();
		ResultDescripter result = Descripter.getResultDescripter();
		Method method = null;
		
//		try {
//			String resultType = "void";
//			if (result != null)
//				resultType = ClassUtil.convertTypeToJavaClass(result.getType());
//			Assert.notNull(getClassLoader(), "Class-loader must be set");
//			method = ReflectionUtil.findMethod(instance.getClass(), parameterTypes, resultType, getClassLoader());
//		} catch (Throwable e) {
//			log.error(e);
//			//fall-thru
//		}

//		if (method == null) {
//			message = instance.process(message);
//		} else {
//			Assert.notNull(method, "Method not found");
//			Object resultValue = ReflectionUtil.invokeMethod(instance, method, parameterValues);
//			if (resultValue != null)
//				message.addPart("result", resultValue);
//		}

		message = instance.process(message);
		return message;
	}

	protected void postProcess(Message message) {
		//do nothing by default
	}

	public void close() throws Exception {
		//do nothing by default
	}

}
