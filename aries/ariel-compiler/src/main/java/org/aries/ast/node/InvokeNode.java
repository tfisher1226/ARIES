package org.aries.ast.node;


/**
 * <p>Invoke node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String destination</pre>
 * 
 * <pre>RoleNode role</pre>
 * 
 * <pre>List<ChannelNode> channel</pre>
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
public class InvokeNode extends OutgoingNode {

	public InvokeNode(Object node) {
		super(node);
	}

}
