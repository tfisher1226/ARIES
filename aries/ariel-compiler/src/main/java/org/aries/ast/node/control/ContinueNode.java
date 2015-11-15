package org.aries.ast.node.control;

import org.aries.ast.node.StatementNode;


/**
 * <p>ContinueNode class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String text</pre>
 * 
 */
public class ContinueNode extends StatementNode {

	private String identifier;


//	public ContinueNode() {
//	}
	
	public ContinueNode(Object node) {
		super(node);
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}
