package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Command node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String text</pre>
 * 
 */
public class CommandNode extends AbstractNode {

	private ResultNode resultNode;

	private List<ParameterNode> parameterNodes = new ArrayList<ParameterNode>();

	private List<VariableNode> variableNodes = new ArrayList<VariableNode>();

	
	public CommandNode() {
	}
	
	public CommandNode(Object node) {
		super(node);
	}

	public ResultNode getResultNode() {
		return resultNode;
	}

	public void setResultNode(ResultNode resultNode) {
		this.resultNode = resultNode;
	}

	public List<ParameterNode> getParameterNodes() {
		return parameterNodes;
	}

	public void setParameterNodes(List<ParameterNode> parameterNodes) {
		this.parameterNodes = parameterNodes;
	}

	public void addParameterNode(ParameterNode parameterNode) {
		parameterNodes.add(parameterNode);
	}

	public void addParameterNodes(List<ParameterNode> parameterNode) {
		this.parameterNodes.addAll(parameterNode);
	}
	
	public List<VariableNode> getVariableNodes() {
		return variableNodes;
	}

	public void addVariableNode(VariableNode variableNode) {
		variableNodes.add(variableNode);
	}

}
