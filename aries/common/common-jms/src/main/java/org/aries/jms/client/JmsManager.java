package org.aries.jms.client;

import java.util.List;

import javax.jms.Message;
import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.hornetq.jms.client.HornetQQueue;
import org.hornetq.jms.client.HornetQTopic;

import common.jmx.client.JmxManager;


public class JmsManager {

	private static Log log = LogFactory.getLog(JmsManager.class);
	
	private JmxManager jmxManager;
	
	
	public JmsManager(JmxManager jmxManager) {
		this.jmxManager = jmxManager;
	}

	public void initialize() throws Exception {
		//nothing for now
	}

	public ObjectName getObjectNameForQueue(String jndiName) throws Exception {
		return getObjectNameForQueue(null, jndiName);
	}
	
	public ObjectName getObjectNameForQueue(String deploymentArchive, String jndiName) throws Exception {
		if (!jndiName.startsWith("jms/queue/"))
			jndiName = "jms/queue/" + jndiName;
		//System.out.println(">>>>>>>>>>>>>>>>>>>>> "+jndiName);
		HornetQQueue hornetQQueue = jmxManager.lookupObject(jndiName);
		if (hornetQQueue == null)
			System.out.println();
		ObjectName objectName = createObjectNameForQueue(deploymentArchive, hornetQQueue.getName());
		//deployment=bookshop2-buyer.ear,
		return objectName;
	}
	
	public ObjectName getObjectNameForTopic(String jndiName) throws Exception {
		return getObjectNameForTopic(null, jndiName);
	}
	
	public ObjectName getObjectNameForTopic(String deploymentArchive, String jndiName) throws Exception {
		if (!jndiName.startsWith("jms/topic/"))
			jndiName = "jms/topic/" + jndiName;
		HornetQTopic hornetQTopic = jmxManager.lookupObject(jndiName);
		ObjectName objectName = createObjectNameForTopic(deploymentArchive, hornetQTopic.getName());
		//deployment=bookshop2-buyer.ear,
		return objectName;
	}
	
	public ObjectName createObjectNameForQueue(String deploymentArchive, String queueName) throws Exception {
		//ObjectName objectName = new ObjectName("jboss.as:subsystem=messaging,hornetq-server=default,jms-queue="+queueName);
		//ObjectName objectName = new ObjectName("jboss.as:hornetq-server=default,jms-queue="+queueName+",subsystem=messaging");
		//ObjectName objectName = new ObjectName("jboss.as:subsystem=messaging,hornetq-server=default,core-address=jms.queue."+queueName);
		StringBuffer buf = new StringBuffer("jboss.as:subsystem=messaging,hornetq-server=default");
		if (deploymentArchive != null)
			buf.append(",deployment=" + deploymentArchive);
		buf.append(",jms-queue=" + queueName);
		ObjectName objectName = new ObjectName(buf.toString());
		return objectName;
	}

	public ObjectName createObjectNameForTopic(String deploymentArchive, String topicName) throws Exception {
		//ObjectName objectName = new ObjectName("jboss.as:subsystem=messaging,hornetq-server=default,jms-topic="+topicName);
		//ObjectName objectName = new ObjectName("jboss.as:hornetq-server=default,jms-topic="+topicName+",subsystem=messaging");
		//ObjectName objectName = new ObjectName("jboss.as:subsystem=messaging,hornetq-server=default,core-address=jms.topic."+topicName);
		StringBuffer buf = new StringBuffer("jboss.as:subsystem=messaging,hornetq-server=default");
		if (deploymentArchive != null)
			buf.append(",deployment=" + deploymentArchive);
		buf.append(",jms-topic=" + topicName);
		ObjectName objectName = new ObjectName(buf.toString());
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
		return countMessagesForQueue(null, queueName);
	}

	public Long countMessagesForQueue(String deploymentArchive, String queueName) throws Exception {
		return countMessages(getObjectNameForQueue(deploymentArchive, queueName));
	}

	public Long countMessagesForTopic(String topicName) throws Exception {
		return countMessagesForTopic(null, topicName);
	}

	public Long countMessagesForTopic(String deploymentArchive, String topicName) throws Exception {
		return countMessages(getObjectNameForTopic(deploymentArchive, topicName));
	}

	public Long countMessages(ObjectName objectName) throws Exception {
		Long count = (Long) jmxManager.invoke(objectName, "countMessages");
		Assert.notNull(count, "countMessages() returned null");
		log.info("Count of "+count+" messages for: "+objectName);
		return count;
	}

	
	public List<Message> listMessagesForQueue(String queueName) throws Exception {
		return listMessagesForQueue(null, queueName);
	}

	public List<Message> listMessagesForQueue(String deploymentArchive, String queueName) throws Exception {
		return listMessages(getObjectNameForQueue(deploymentArchive, queueName));
	}

	public List<Message> listMessagesForTopic(String topicName) throws Exception {
		return listMessagesForTopic(null, topicName);
	}

	public List<Message> listMessagesForTopic(String deploymentArchive, String topicName) throws Exception {
		return listMessages(getObjectNameForTopic(deploymentArchive, topicName));
	}

	@SuppressWarnings("unchecked")
	public List<Message> listMessages(ObjectName objectName) throws Exception {
		List<Message> list = (List<Message>) jmxManager.invoke(objectName, "listMessages");
		log.info("Found "+list.size()+" messages from: "+objectName);
		return list;
	}

//	public List<Message> listMessagesForQueue2(String queueName) throws Exception {
//		return listMessages2(getObjectNameForQueue(queueName));
//	}
//
//	public List<Message> listMessagesForTopic2(String topicName) throws Exception {
//		return listMessages2(getObjectNameForTopic(topicName));
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<Message> listMessages2(ObjectName objectName) throws Exception {
//		Object[] parameters = {""};
//		String[] signature = {"java.lang.String"};
//		List<Message> list = (List<Message>) jmxManager.invoke(objectName, "listMessages", parameters, signature);
//		log.info("Found "+list.size()+" messages from: "+objectName);
//		return list;
//	}

	
	public Integer expireMessagesForQueue(String queueName) throws Exception {
		return expireMessagesForQueue(null, queueName);
	}

	public Integer expireMessagesForQueue(String deploymentArchive, String queueName) throws Exception {
		return expireMessages(getObjectNameForQueue(deploymentArchive, queueName));
	}

	public Integer expireMessagesForTopic(String topicName) throws Exception {
		return expireMessagesForTopic(null, topicName);
	}

	public Integer expireMessagesForTopic(String deploymentArchive, String topicName) throws Exception {
		return expireMessages(getObjectNameForTopic(deploymentArchive, topicName));
	}

	public Integer expireMessages(ObjectName objectName) throws Exception {
		Object[] parameters = {""};
		String[] signature = {"java.lang.String"};
		Integer count = (Integer) jmxManager.invoke(objectName, "expireMessages", parameters, signature);
		log.info("Expired "+count+" messages from queue: "+objectName);
		return count;
	}

	
	public Integer removeMessagesFromQueue(String queueName) throws Exception {
		return removeMessages(getObjectNameForQueue(null, queueName));
	}

	public Integer removeMessagesFromQueue(String deploymentArchive, String queueName) throws Exception {
		return removeMessages(getObjectNameForQueue(deploymentArchive, queueName));
	}

	public Integer removeMessagesFromTopic(String topicName) throws Exception {
		return removeMessages(getObjectNameForTopic(null, topicName));
	}

	public Integer removeMessagesFromTopic(String deploymentArchive, String topicName) throws Exception {
		return removeMessages(getObjectNameForTopic(deploymentArchive, topicName));
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
