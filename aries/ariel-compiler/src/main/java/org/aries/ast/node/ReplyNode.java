package org.aries.ast.node;


/**
 * <p>Reply node class for Ariel Abstract Syntax Tree.
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
public class ReplyNode extends OutgoingNode {

	private String operationName;

	private String messageName;

	//private String processScope;
	
	
	public ReplyNode(Object node) {
		super(node);
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}
	
}
