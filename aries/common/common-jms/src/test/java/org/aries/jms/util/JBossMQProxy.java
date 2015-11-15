package org.aries.jms.util;

import java.util.List;

import javax.jms.Message;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;


public class JBossMQProxy extends AbstractJBossServerProxy {

	public JBossMQProxy(String host, int port) {
		super(host, port);
	}

	public void removeMessages() throws Exception {
		removeMessages("name=queueA,service=Queue");
	}
	
	public void start(String name) throws Exception {
		client.invoke(getDestinationName(name), "start");
		log.info("Started: name="+name);
	}

	public void stop(String name) throws Exception {
		client.invoke(getDestinationName(name), "stop");
		log.info("Stopped: name="+name);
	}

	public long countMessages(String name) throws Exception {
		Object[] parameters = {""};
		String[] signature = {"java.lang.String"};
		ObjectName mbeanName = getDestinationName(name);
		Long count = (Long) client.invoke(mbeanName, "countMessages", parameters, signature);
		log.info("Count of "+count+" messages for destination: "+mbeanName);
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> listMessagesForQueue(String name) throws Exception {
		List<Message> list = (List<Message>) client.invoke(getDestinationName(name), "listMessages");
		log.info("Found "+list.size()+" messages from destination: "+name);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Message> listMessagesForTopic(String name) throws Exception {
		Object[] parameters = {""};
		String[] signature = {"java.lang.String"};
		List<Message> list = (List<Message>) client.invoke(getDestinationName(name), "listMessages", parameters, signature);
		log.info("Found "+list.size()+" messages from destination: "+name);
		return list;
	}
	
	public int expireMessages(String name) throws Exception {
		Object[] parameters = {""};
		String[] signature = {"java.lang.String"};
		ObjectName mbeanName = getDestinationName(name);
		Integer count = (Integer) client.invoke(mbeanName, "expireMessages", parameters, signature);
		log.info("Expired "+count+" messages from destination: "+mbeanName);
		return count;
	}

	public int removeMessages(String name) throws Exception {
		Object[] parameters = {""};
		String[] signature = {"java.lang.String"};
		ObjectName mbeanName = getDestinationName(name);
		Integer count = (Integer) client.invoke(mbeanName, "removeMessages", parameters, signature);
		log.info("Removed "+count+" messages from destination: "+mbeanName);
		return count;
	}

	protected ObjectName getDestinationName(String name) throws MalformedObjectNameException {
		//return new ObjectName("jboss.mq.destination:"+name);
		return new ObjectName("org.hornetq:module=JMS,"+name);
	}
	
}
