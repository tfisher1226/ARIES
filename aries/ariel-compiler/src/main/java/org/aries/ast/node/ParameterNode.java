package org.aries.ast.node;

import java.util.Stack;


/**
 * <p>Parameter node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String type</pre>
 *  
 */
public class ParameterNode extends AbstractNode {
	
	private TypeNode typeNode;

	private String key;
	
	private Stack<CommandNode> commandStack;


	//this is for a "fabricated" ParameterNode
	public ParameterNode() {
		//do nothing
	}
	
	public ParameterNode(Object node) {
		super(node);
	}

	public void setName(String name) {
		super.setName(name);
		if (name.equals("exception"))
			System.out.println();
	}
	
	public TypeNode getTypeNode() {
		return typeNode;
	}

	public void setTypeNode(TypeNode typeNode) {
		this.typeNode = typeNode;
	}

	public String getType() {
		return typeNode.getType();
	}

	public void setType(String type) {
		throw new UnsupportedOperationException();
	}
	
	public String getConstruct() {
		return typeNode.getConstruct();
	}

	public void setConstruct(String construct) {
		this.typeNode.setConstruct(construct);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Stack<CommandNode> getCommandStack() {
		return commandStack;
	}

	public void setCommandStack(Stack<CommandNode> commandStack) {
		this.commandStack = commandStack;
	}

	public String toString() {
		String type = super.toString();
		if (typeNode != null) {
			String construct = typeNode.getConstruct();
			if (construct.equalsIgnoreCase("list"))
				return "List<"+type+">";
			else if (construct.equalsIgnoreCase("set"))
				return "Set<"+type+">";
			else if (construct.equalsIgnoreCase("map"))
				return "Map<"+key+", "+type+">";
		}
		return super.toString();
	}

}
