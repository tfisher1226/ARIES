package org.aries.client.jms;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Handler;
import org.aries.message.Message;
import org.aries.service.AbstractServiceProxy;
import org.aries.service.ServiceProxy;


public class JMSServiceProxy extends AbstractServiceProxy implements ServiceProxy {

	private static Log log = LogFactory.getLog(JMSServiceProxy.class);

	private JMSEndpointContext endpointContext;


	public JMSServiceProxy(JMSEndpointContext endpointContext) {
		this.endpointContext = endpointContext;
	}

	public void initialize() throws JMSException {
    	endpointContext.initialize();
	}
	

	public void dispatch(Message message) {
		try {
			javax.jms.Message jmsMessage = endpointContext.toJMSMessage(message);
			endpointContext.getProducer().send(jmsMessage);
			
		} catch (JMSException e) {
            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
            log.debug("", e);
			//TODO drill down and get the actual cause
			//TODO throw instead a UserDefined fault 
			throw new RuntimeException(e);

		} catch (RuntimeException e) {
            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
            log.debug("", e);
			//assuming for now e is acceptable for propagation 
			//(i.e. not like an SQLException etc...)
			//TODO drill down and get the actual cause
			//TODO throw instead a UserDefined fault 
			throw e;

		} catch (Throwable e) {
            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
			//assuming for now e is acceptable for propagation 
			//(i.e. not like an SQLException etc...)
			//TODO drill down and get the actual cause
			//TODO throw instead a UserDefined fault 
            log.debug("", e);
			throw new RuntimeException(e);
		}
	}

	public Future<Message> invoke(final Message request, Handler<Message> handler) {
		ExecutorService executor = createExecutor("aries.jms", "JMSServiceProxy", 1);
		Callable<Message> callable = new Callable<Message>() {
			public Message call() {
				try {
					javax.jms.Message jmsRequest = endpointContext.toJMSMessage(request);
					endpointContext.getProducer().send(jmsRequest);
					
					javax.jms.Message jmsResponse = endpointContext.getConsumer().receive(2000);
					Message response = endpointContext.fromJMSMessage(jmsResponse);
					return response;
					
				} catch (Exception e) {
		            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
					log.debug("", e);
					return null;
				}
			}
		};
		
//		AsyncHandler<Message> asyncHandler = new AsyncHandler<Message>() {
//			public void handleResponse(Response<Message> response) {
//				Message message = response.get();
//				BankLoanQuoteResponse bankLoanQuoteResponse = message.getPart("response");
//				boolean accepted = getLoanQuoteHandler.accept(bankLoanQuoteResponse);
//				if (accepted) {
//					
//				}
//			}
//		};
		
		FutureTask<Message> future = new AsyncResponse<Message>(callable, handler);
		executor.execute(future);
		return future;
	}


	public Message invoke(Message request) {
		//request.addPart("ARIESServiceName", service);
		//request.addPart("ARIESMethodName", method);

		try {
			javax.jms.Message jmsRequest = endpointContext.toJMSMessage(request);
			javax.jms.Message jmsResponse = endpointContext.getInvoker().invoke(jmsRequest);
			Message response = endpointContext.fromJMSMessage(jmsResponse);
			return response;

		} catch (JMSException e) {
            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
            log.debug("", e);
			//TODO drill down and get the actual cause
			//TODO throw instead a UserDefined fault 
			throw new RuntimeException(e);

		} catch (RuntimeException e) {
            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
            log.debug("", e);
			//assuming for now e is acceptable for propagation 
			//(i.e. not like an SQLException etc...)
			//TODO drill down and get the actual cause
			//TODO throw instead a UserDefined fault 
			throw e;

		} catch (Throwable e) {
            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
			//assuming for now e is acceptable for propagation 
			//(i.e. not like an SQLException etc...)
			//TODO drill down and get the actual cause
			//TODO throw instead a UserDefined fault 
            log.debug("", e);
			throw new RuntimeException(e);
		}
	}

	
	public Message receive(long timeout) {
		//Assert.notNull(incomingDestination, "JMS incoming-destination must be specified");

		try {
			//attempt to receive message
			javax.jms.Message jmsMessage = endpointContext.getConsumer().receive(timeout);
			if (jmsMessage == null)
				return null;
			
			//process received message
			Message response = endpointContext.fromJMSMessage(jmsMessage);
			return response;

		} catch (JMSException e) {
            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
            log.debug("", e);
			//TODO drill down and get the actual cause
			//TODO throw instead a UserDefined fault 
			throw new RuntimeException(e);

		} catch (RuntimeException e) {
            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
            log.debug("", e);
			//assuming for now e is acceptable for propagation 
			//(i.e. not like an SQLException etc...)
			//TODO drill down and get the actual cause
			//TODO throw instead a UserDefined fault 
			throw e;

		} catch (Throwable e) {
            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
			//assuming for now e is acceptable for propagation 
			//(i.e. not like an SQLException etc...)
			//TODO drill down and get the actual cause
			//TODO throw instead a UserDefined fault 
            log.debug("", e);
			throw new RuntimeException(e);
		}
	}
	
	
	
	protected ExecutorService createExecutor() {
		return createExecutor("aries.jms", "JMSServiceProxy", 1);
	}

	
	//TODO use keep alive var.
	protected ExecutorService createExecutor(String group, String name, int poolSize) {
		SynchronousQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();
		ThreadPoolExecutor executor = createThreadPoolExecutor(workQueue, group, name, poolSize);
		executor.prestartAllCoreThreads();
		return executor;
	}

	
	protected ThreadPoolExecutor createThreadPoolExecutor(BlockingQueue<Runnable> workQueue, final String group, final String name, int poolSize) {
		final ThreadGroup threadGroup = new ThreadGroup(group);
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, poolSize, 1, TimeUnit.MINUTES, workQueue, new ThreadFactory() {
			private int counter = 0;
			public Thread newThread(Runnable runnable) {
				Thread thread = new Thread(threadGroup, runnable);
				thread.setName(group+name+"."+counter);
				//TODO check do we need this?
				//thread.setDaemon(true);
				counter++;
				return thread;
			}
		});
		return executor;
	}
	
}
