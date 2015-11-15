package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Endpoint node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
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
public class EndpointNode extends BlockNode {

	private List<MessageNode> messageNodes = new ArrayList<MessageNode>();
	
		
	public EndpointNode(Object node) {
		super(node);
	}

	public List<MessageNode> getMessageNodes() {
		return messageNodes;
	}

	public void addMessageNode(MessageNode messageNode) {
		messageNodes.add(messageNode);
	}

}
