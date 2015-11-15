package org.aries.jms.management;

import java.util.List;

import javax.jms.Message;
import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.hornetq.jms.client.HornetQQueue;
import org.hornetq.jms.client.HornetQTopic;

import common.jmx.client.JmxManager;


public class JmxManagementServiceImpl implements JmxManagementService {

	private static Log log = LogFactory.getLog(JmxManagementServiceImpl.class);
	
	private JmxManager jmxManager;
	

	public ObjectName getObjectNameForQueue(String jndiName) throws Exception {
		HornetQQueue hornetQQueue = jmxManager.lookupObject(jndiName);
		
		ObjectName objectName = createObjectNameForQueue(hornetQQueue.getName());
		return objectName;
	}
	
	public ObjectName getObjectNameForTopic(String jndiName) throws Exception {
		HornetQTopic hornetQTopic = jmxManager.lookupObject(jndiName);
		ObjectName objectName = createObjectNameForTopic(hornetQTopic.getName());
		return objectName;
	}
	
	public ObjectName createObjectNameForQueue(String queueName) throws Exception {
		//ObjectName objectName = new ObjectName("jboss.as:hornetq-server=default,jms-queue="+queueName+",subsystem=messaging");
		//ObjectName objectName = new ObjectName("jboss.as:subsystem=messaging,hornetq-server=default,core-address=jms.queue."+queueName);
		ObjectName objectName = new ObjectName("jboss.as:subsystem=messaging,hornetq-server=default,jms-queue="+queueName);
		return objectName;
	}
	
	public ObjectName createObjectNameForTopic(String topicName) throws Exception {
		//ObjectName objectName = new ObjectName("jboss.as:hornetq-server=default,jms-topic="+topicName+",subsystem=messaging");
		//ObjectName objectName = new ObjectName("jboss.as:subsystem=messaging,hornetq-server=default,core-address=jms.topic."+topicName);
		ObjectName objectName = new ObjectName("jboss.as:subsystem=messaging,hornetq-server=default,jms-topic="+topicName);
		return objectName;
	}
	
	
	public void startQueue(String queueName) throws Exception {
		start(getObjectNameForQueue(queueName));
	}

	public void startTopic(String topicName) throws Exception {
		start(getObjectNameForTopic(topicName));
	}

	public void start(ObjectName objectName) throws Exception {
		jmxManager.invoke(objectName, "start");
		log.info("Started: name="+objectName);
	}

	
	public void stopQueue(String queueName) throws Exception {
		stop(getObjectNameForQueue(queueName));
	}

	public void stopTopic(String topicName) throws Exception {
		stop(getObjectNameForTopic(topicName));
	}

	public void stop(ObjectName objectName) throws Exception {
		jmxManager.invoke(objectName, "stop");
		log.info("Stopped: name="+objectName);
	}
	

	public Long countMessagesForQueue(String queueName) throws Exception {
		ObjectName objectName = getObjectNameForQueue(queueName);
		//MBeanInfo mBeanInfo = client.getMBeanInfo(objectName);
		return countMessages(objectName);
	}

	public Long countMessagesForTopic(String topicName) throws Exception {
		return countMessages(getObjectNameForTopic(topicName));
	}

	public Long countMessages(ObjectName objectName) throws Exception {
		Long count = (Long) jmxManager.invoke(objectName, "countMessages");
		Assert.notNull(count, "countMessages() returned null");
		log.info("Count of "+count+" messages for: "+objectName);
		return count;
	}

	
	public List<Message> listMessagesForQueue(String queueName) throws Exception {
		return listMessages(getObjectNameForQueue(queueName));
	}

	public List<Message> listMessagesForTopic(String topicName) throws Exception {
		return listMessages(getObjectNameForTopic(topicName));
	}

	@SuppressWarnings("unchecked")
	public List<Message> listMessages(ObjectName objectName) throws Exception {
		List<Message> list = (List<Message>) jmxManager.invoke(objectName, "listMessages");
		log.info("Found "+list.size()+" messages from: "+objectName);
		return list;
	}

	public List<Message> listMessagesForQueue2(String queueName) throws Exception {
		return listMessages2(getObjectNameForQueue(queueName));
	}

	public List<Message> listMessagesForTopic2(String topicName) throws Exception {
		return listMessages2(getObjectNameForTopic(topicName));
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> listMessages2(ObjectName objectName) throws Exception {
		Object[] parameters = {""};
		String[] signature = {"java.lang.String"};
		List<Message> list = (List<Message>) jmxManager.invoke(objectName, "listMessages", parameters, signature);
		log.info("Found "+list.size()+" messages from: "+objectName);
		return list;
	}

	
	public Integer expireMessagesForQueue(String queueName) throws Exception {
		return expireMessages(getObjectNameForQueue(queueName));
	}

	public Integer expireMessagesForTopic(String topicName) throws Exception {
		return expireMessages(getObjectNameForTopic(topicName));
	}

	public Integer expireMessages(ObjectName objectName) throws Exception {
		Object[] parameters = {""};
		String[] signature = {"java.lang.String"};
		Integer count = (Integer) jmxManager.invoke(objectName, "expireMessages", parameters, signature);
		log.info("Expired "+count+" messages from queue: "+objectName);
		return count;
	}
	
	
	public Integer removeMessagesFromQueue(String queueName) throws Exception {
		return removeMessages(getObjectNameForQueue(queueName));
	}

	public Integer removeMessagesFromTopic(String topicName) throws Exception {
		return removeMessages(getObjectNameForTopic(topicName));
	}

	public Integer removeMessages(ObjectName objectName) throws Exception {
		log.info("removeMessages: invoked: "+objectName);
		Object[] parameters = {""};
		String[] signature = {"java.lang.String"};
		//Integer count = (Integer) client.invoke(objectName, "removeMessages", parameters, signature);
		Integer count = (Integer) jmxManager.invoke(objectName, "removeMessages");
		log.info("Removed "+count+" messages from queue: "+objectName);
		log.info("removeMessages: returns: "+count);
		return count;
	}
	
}
