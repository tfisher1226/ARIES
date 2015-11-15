package org.aries.jms;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.AbstractExecutable;


public class JmsMessageReceiver extends AbstractExecutable {

	private static final Log log = LogFactory.getLog(JmsMessageReceiver.class);
	
	private static final String MBEAN_NAME = "Messaging:name=MessageReceiver";

	private static final int DEFAULT_THREAD_POOL_SIZE = 1;
	
	private static final long DEFAULT_TIMEOUT = 10000;

	protected JmsMessageConsumer messageConsumer;
	
	protected MessageListener listener;

	protected ExecutorService executor;

	//private int _poolSize;

	private long timeout;


	public JmsMessageReceiver() {
		construct();
	}

	public JmsMessageReceiver(JmsMessageConsumer subscriber) {
		this.messageConsumer = subscriber;
		construct();
	}
	
	private void construct() {
		timeout = DEFAULT_TIMEOUT;
		executor = createExecutor();
	}

	public String getMBeanName() {
		return MBEAN_NAME;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long value) {
		timeout = value;
	}

	public void setConsumer(JmsMessageConsumer value) {
		messageConsumer = value;
	}
	
	public void setMessageListener(MessageListener value) {
		listener = value;
	}
	

	public void initialize() throws Exception {
        //log.info("Initialized");
		//nothing for now
    }
	
    protected ExecutorService createExecutor() {
        return createExecutor(DEFAULT_THREAD_POOL_SIZE);
    }
    
    protected ExecutorService createExecutor(int poolSize) {
    	SynchronousQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, poolSize, 1, TimeUnit.MINUTES, workQueue);
		executor.prestartAllCoreThreads();
		return executor;
    }
    
    
//	public void start() throws Exception {
//		synchronized(_lock) {
//			stop();
//			log.trace("Starting");
//			_thread = new Thread(createRunner());
//			_thread.start();
//			_stopped.set(false);
//			_started.set(true);
//			log.trace("Started");
//		}
//	}

//	public void stop() throws Exception {
//		synchronized(_lock) {
//			if (_thread != null) {
//				log.trace("Stopped");
//				_thread.interrupt();
//				_thread.join();
//				_stopped.set(true);
//				_started.set(false);
//				log.trace("Stopped");
//			}
//		}
//	}

	protected void execute() {
		try {
			Message message = receive();
			Thread thread = Thread.currentThread();
			if (thread.isInterrupted())
				return;
			if (message != null)
				dispatch(message);
		} catch (Exception e) {
			log.error(e);
		}
	}

	public Message receive() {
		return receive(getTimeout());
	}
	
	public Message receive(long timeout) {
		try {
			Message message = messageConsumer.receive(timeout);
			return message;
		} catch (Exception e) {
			//TODO handle this
			log.error(e);
			return null;
		}
	}
	
	protected void dispatch(Message message) {
		executor.execute(createHandler(message));
	}
	
	protected Runnable createHandler(final Message message) {
		return new Runnable() {
			public void run() {
				handle(message);
			}
		};
	}

	protected void handle(Message message) {
		listener.onMessage(message);
	}

	public void close() throws Exception {
		if (executor != null)
			executor.shutdown();
		super.close();
	}

}
