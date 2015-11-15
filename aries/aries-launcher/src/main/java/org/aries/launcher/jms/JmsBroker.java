package org.aries.launcher.jms;


//import org.apache.activemq.broker.BrokerService;
//import org.apache.activemq.store.memory.MemoryPersistenceAdapter;


public final class JmsBroker {

	private JMSEmbeddedBroker jmsBrokerThread;
    
	private String jmsBrokerUrl = "tcp://localhost:51616";
    
	private String activeMQStorageDir;
    
	
	public JmsBroker() {
    }

    public JmsBroker(String url) {
        jmsBrokerUrl = url;
    }
    
    public void start() throws Exception {
        jmsBrokerThread = new JMSEmbeddedBroker(jmsBrokerUrl);
        jmsBrokerThread.startBroker();
    }
    
    public void stop() throws Exception {
        synchronized (this) {
            jmsBrokerThread.shutdownBroker = true;
        }
        if (jmsBrokerThread != null) {
            jmsBrokerThread.join();
        }
        
        jmsBrokerThread = null;
    }
    
    class JMSEmbeddedBroker extends Thread {
        boolean shutdownBroker;
        final String brokerUrl;
        Exception exception;
        
        
        public JMSEmbeddedBroker(String url) {
            brokerUrl = url;
        }
        
        public void startBroker() throws Exception {
            synchronized (this) {
                super.start();
                try {
                    wait();
                    if (exception != null) {
                        throw exception;
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        public void run() {
        	/*
            try {  
                BrokerService broker = new BrokerService();
                synchronized (this) {                                     
                    broker.setPersistenceAdapter(new MemoryPersistenceAdapter());                    
                    broker.setTmpDataDirectory(new File("./target"));
                    broker.setSchedulerSupport(false);
                    broker.addConnector(brokerUrl);
                    broker.start();
                    Thread.sleep(200);
                    notifyAll();
                }
                synchronized (this) {
                    while (!shutdownBroker) {
                        wait(1000);
                    }
                }                
                broker.stop();              
                broker = null;                
            } catch (Exception e) {
                exception = e;
                e.printStackTrace();
            }
            */
        }
    }
}

