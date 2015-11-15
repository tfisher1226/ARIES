package org.aries.ast.node;


/**
 * <p>Outgoing node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String destination</pre>
 * 
 * <pre>RoleNode role</pre>
 * 
 * <pre>ChannelNode channel</pre>
 *
 * <pre>List<PropertyNode> properties</pre>
 *  
 * <pre>List<VariableNode> variables</pre>
 *  
 * <pre>List<MessageNode> messages</pre>
 *  
 * <pre>List<ExceptionNode> exceptions</pre>
 *  
 * <pre>List<TimeoutNode> timeout</pre>
 *  
 */
public class OutgoingNode extends EndpointNode {

	private String destination;
	
	public OutgoingNode(Object node) {
		super(node);
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
}
