package org.aries.jms.util;

import java.util.List;

import javax.jms.Message;


public class JBossMQManager extends AbstractJBossServerManager {

	private JBossMQProxy _proxy;

	
	public JBossMQManager(String host, int port) {
		super(host, port);
	}
	
	public JBossMQProxy getProxy() {
		return _proxy;
	}
	
	public void initialize() throws Exception {
		_proxy = new JBossMQProxy(_host, _port);
		_proxy.initialize();
	}
	
	public void start(String name) throws Exception {
		_proxy.start(name);
	}

	public void stop(String name) throws Exception {
		_proxy.stop(name);
	}

	public void removeTestMessages() throws Exception {
		_proxy.removeMessages("name=queueA,service=Queue");
		_proxy.removeMessages("name=queueB,service=Queue");
		_proxy.removeMessages("name=queueC,service=Queue");
		_proxy.removeMessages("name=testTopic,service=Topic");
		_proxy.removeMessages("name=testDurableTopic,service=Topic");
	}

	public List<Message> listMessages() throws Exception {
		List<Message> messages = _proxy.listMessagesForTopic("name=testTopic,service=Topic");
		return messages;
	}
	
}
