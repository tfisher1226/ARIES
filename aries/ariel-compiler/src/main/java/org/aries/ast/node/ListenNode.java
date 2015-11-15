package org.aries.ast.node;


/**
 * <p>Listen node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>RoleNode role</pre>
 * 
 * <pre>ChannelNode channel</pre>
 *
 * <pre>EventNode event</pre>
 *  
 * <pre>List<PropertyNode> properties</pre>
 *  
 * <pre>List<VariableNode> variables</pre>
 *  
 * <pre>List<ExceptionNode> exceptions</pre>
 *  
 * <pre>List<TimeoutNode> timeout</pre>
 *  
 */
public class ListenNode extends EndpointNode {

	private EventNode eventNode;

	private String messageName;

	
	public ListenNode(Object node) {
		super(node);
	}

	public EventNode getEventNode() {
		return eventNode;
	}

	public void addEventNode(EventNode eventNode) {
		this.eventNode = eventNode;
	}
	
	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}
	
}
