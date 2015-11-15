package bookshop2.buyer;

import org.jboss.shrinkwrap.descriptor.spi.node.Node;
import org.jboss.shrinkwrap.descriptor.spi.node.NodeDescriptorImplBase;


public class JMSDescriptorImpl extends NodeDescriptorImplBase implements JMSDescriptor
{
	// -------------------------------------------------------------------------------------||
	// Instance Members -------------------------------------------------------------------||
	// -------------------------------------------------------------------------------------||

	private final Node configuration;

	// -------------------------------------------------------------------------------------||
	// Constructor ------------------------------------------------------------------------||
	// -------------------------------------------------------------------------------------||

	public JMSDescriptorImpl(String descriptorName)
	{
		this(descriptorName, new Node("configuration")
		.attribute("xmlns", "urn:hornetq")
		.attribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance")
		.attribute("xsi:schemaLocation", "urn:hornetq ../schemas/hornetq-jms.xsd"));
	}

	public JMSDescriptorImpl(String descriptorName, Node configuration)
	{
		super(descriptorName);
		this.configuration = configuration;
	}

	/* (non-Javadoc)
	 * @see com.acme.jms.descriptor.JMSDescriptor#qeueu(java.lang.String, java.lang.String)
	 */
	@Override
	public JMSDescriptor queue(String name, String jndi)
	{
		configuration.getOrCreate("queue@name=" + name)
		.attribute("name", name)
		.getOrCreate("entry").attribute("name", jndi);
		return this;
	}

	/* (non-Javadoc)
	 * @see com.acme.jms.descriptor.JMSDescriptor#topic(java.lang.String, java.lang.String)
	 */
	@Override
	public JMSDescriptor topic(String name, String jndi)
	{
		configuration.getOrCreate("topic@name=" + name)
		.attribute("name", name)
		.getOrCreate("entry").attribute("name", jndi);

		return this;
	}
	// -------------------------------------------------------------------------------------||
	// Required Impl - NodeProvider --------------------------------------------------------||
	// -------------------------------------------------------------------------------------||

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jboss.shrinkwrap.descriptor.spi.NodeProvider#getRootNode()
	 */
	@Override
	public Node getRootNode()
	{
		return configuration;
	}

//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see org.jboss.shrinkwrap.descriptor.impl.base.NodeProviderImplBase#getExporter()
//	 */
//	@Override
//	protected DescriptorExporter getExporter()
//	{
//		return new XMLExporter();
//	}

}
