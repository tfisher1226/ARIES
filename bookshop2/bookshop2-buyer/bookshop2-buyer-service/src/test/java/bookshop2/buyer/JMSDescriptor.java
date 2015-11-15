package bookshop2.buyer;

import org.jboss.shrinkwrap.descriptor.api.Descriptor;


public interface JMSDescriptor extends Descriptor {

	public JMSDescriptor queue(String queueName, String jndiName);
	
	public JMSDescriptor topic(String topicName, String jndiName);

}

