package org.aries.ast.node;


public class PrimaryNode extends AbstractNode {

	private TypeNode typeNode;
	
	
	public PrimaryNode(Object node) {
		super(node);
	}
	
	public TypeNode getTypeNode() {
		return typeNode;
	}

	public void setTypeNode(TypeNode typeNode) {
		this.typeNode = typeNode;
	}

}
