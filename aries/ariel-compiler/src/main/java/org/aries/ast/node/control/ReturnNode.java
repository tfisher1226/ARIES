package org.aries.ast.node.control;

import org.aries.ast.node.ExpressionNode;
import org.aries.ast.node.StatementNode;


/**
 * <p>ReturnNode class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String text</pre>
 * 
 */
public class ReturnNode extends StatementNode {

	private ExpressionNode expressionNode;


//	public ReturnNode() {
//	}
	
	public ReturnNode(Object node) {
		super(node);
	}

	public ExpressionNode getExpressionNode() {
		return expressionNode;
	}

	public void setCommandNodes(ExpressionNode expressionNode) {
		this.expressionNode = expressionNode;
	}

}
