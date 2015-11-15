package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>ItemNode class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String type</pre>
 *
 * <pre>List<PrincipleNode> credentials</pre>
 *  
 */
public class ItemNode extends AbstractNode {

	private TypeNode typeNode;

	private String key;

	private List<String> indexes = new ArrayList<String>();
	
	
	public ItemNode(Object node) {
		super(node);
	}

	public TypeNode getTypeNode() {
		return typeNode;
	}

	public void setTypeNode(TypeNode typeNode) {
		this.typeNode = typeNode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getConstruct() {
		return typeNode.getConstruct();
	}

	public void setConstruct(String construct) {
		typeNode.setConstruct(construct);
	}
	
	public void setIndexes(List<String> indexes) {
		this.indexes = indexes;
	}

	public List<String> getIndexes() {
		return indexes;
	}

	public void addIndex(String index) {
		indexes.add(index);
	}

}
