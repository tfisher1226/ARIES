package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Receive node class for Ariel Abstract Syntax Tree.
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
public class ReceiveNode extends EndpointNode {

	private List<CommandNode> commandNodes = new ArrayList<CommandNode>();

	private List<OptionNode> optionNodes = new ArrayList<OptionNode>();

	private ThrowsNode throwsNode;
	

	public ReceiveNode(Object node) {
		super(node);
	}

	public List<CommandNode> getCommandNodes() {
		return commandNodes;
	}

	public void addCommandNode(CommandNode commandNode) {
		commandNodes.add(commandNode);
	}

	public List<OptionNode> getOptionNodes() {
		return optionNodes;
	}

	public void addOptionNode(OptionNode optionNode) {
		optionNodes.add(optionNode);
	}

	public ThrowsNode getThrowsNode() {
		return throwsNode;
	}

	public void addThrowsNode(ThrowsNode throwsNode) {
		this.throwsNode = throwsNode;
	}

}
