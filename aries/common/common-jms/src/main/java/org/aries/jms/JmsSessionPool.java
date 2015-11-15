package org.aries.jms;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JmsSessionPool {

	private static final Log log = LogFactory.getLog(JmsSessionPool.class);

//	private static final int SESSION_POOL_SIZE = 3;

	public static JmsSessionPool instance = new JmsSessionPool();

	public static JmsSessionPool getInstance() {
		return instance;
	}

	
	private JmsConnectionAdapter connectionAdapter;
	
	private ConcurrentLinkedQueue<JmsSessionAdapter> sessionPool;

	
	//public JmsSessionAdapterPool(JmsConnectionAdapter connectionAdapter) {
	public JmsSessionPool() {
		sessionPool = new ConcurrentLinkedQueue<JmsSessionAdapter>();
	}

	public int size() {
		return sessionPool.size();
	}

//	public int capacity() {
//		return SESSION_POOL_SIZE;
//	}
	
	@SuppressWarnings("unchecked")
	public <T extends JmsSessionAdapter> T get() {
		T adapter = (T) sessionPool.remove();
		log.info("SESSION POOL: ALLOCATION: "+adapter);
		return adapter;
	}

	public void add(JmsSessionAdapter adapter) {
		sessionPool.add(adapter);
	}

	public void release(JmsSessionAdapter adapter) {
		log.info("SESSION POOL: RELEASE: "+adapter);
		sessionPool.add(adapter);
	}


	public void initialize() throws Exception {
    	synchronized (sessionPool) {
        	Iterator<JmsSessionAdapter> iterator = sessionPool.iterator();
        	while (iterator.hasNext()) {
				JmsSessionAdapter sessionAdapter = iterator.next();
	    		sessionAdapter.initialize();
	    	}
	    }
	}

	public void close() throws Exception {
		//nothing for now
	}

}
