package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>ExpressionNode class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String value</pre>
 * 
 */
public class ExpressionNode extends StatementNode {

	private String objectName;
	
	private List<String> selectorChain = new ArrayList<String>();

	private ExpressionNode expressionNode;
	
	
	public ExpressionNode(Object node) {
		super(node);
	}
	
	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public List<String> getSelectorChain() {
		return selectorChain;
	}

	public void setSelectorChain(List<String> selectorChain) {
		this.selectorChain = selectorChain;
	}
	
	public void addSelector(String selector) {
		this.selectorChain.add(selector);
	}

	public ExpressionNode getExpressionNode() {
		return expressionNode;
	}

	public void setExpressionNode(ExpressionNode expressionNode) {
		this.expressionNode = expressionNode;
	}
	
}
