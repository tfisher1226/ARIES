package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>TypeNode class for Ariel Abstract Syntax Tree.
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
public class TypeNode extends AbstractNode {

	private String key;

	private String identifier;

	private String construct;
	
	private List<TypeNode> typeArguments = new ArrayList<TypeNode>();
	
	
	//this is for a "fabricated" TypeNode
	public TypeNode() {
		//do nothing
	}

	public TypeNode(Object node) {
		super(node);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getConstruct() {
		return construct;
	}

	public void setConstruct(String construct) {
		this.construct = construct;
	}
	
	public List<TypeNode> getTypeArguments() {
		return typeArguments;
	}

	public void setTypeArguments(List<TypeNode> typeArguments) {
		this.typeArguments = typeArguments;
	}

	public void addTypeArgument(TypeNode typeArgument) {
		typeArguments.add(typeArgument);
	}

}
