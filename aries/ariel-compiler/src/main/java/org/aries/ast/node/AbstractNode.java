package org.aries.ast.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aries.codegen.util.CodeUtil;


public class AbstractNode {

	private Object node;

	private String type;

	private String name;

	private String domain;

	private String value;

	private String text;

	private String openBracket;

	private String closeBracket;

	private List<String> tokens = new ArrayList<String>();

	private List<AbstractNode> childNodes = new ArrayList<AbstractNode>();

	private Map<String, List<PropertyNode>> propertyNodes = new HashMap<String, List<PropertyNode>>();

	
	public AbstractNode() {
		setText("auto-generated");
	}
	
	public AbstractNode(Object node) {
		setNode(node);
		setText(node.toString());
	}

	public Object getNode() {
		return node;
	}

	public void setNode(Object node) {
		this.node = node;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void appendText(String text) {
		this.text += text;
	}

	public List<String> getTokens() {
		return tokens;
	}

	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}

	public void addToken(String token) {
		tokens.add(token);
	}
	
	public String getOpenBracket() {
		return openBracket;
	}

	public void setOpenBracket(String openBracket) {
		this.openBracket = openBracket;
	}

	public String getCloseBracket() {
		return closeBracket;
	}

	public void setCloseBracket(String closeBracket) {
		this.closeBracket = closeBracket;
	}

	public Set<PropertyNode> getPropertyNodes() {
		synchronized (propertyNodes) {
			Set<PropertyNode> set = new HashSet<PropertyNode>();
			Set<String> keySet = propertyNodes.keySet();
			Iterator<String> iterator = keySet.iterator();
			while (iterator.hasNext()) {
				String propertyName = iterator.next();
				List<PropertyNode> list = propertyNodes.get(propertyName);
				if (list != null)
					set.addAll(list);
			}
			return set;
		}
	}

	//this just "gets" the "first" one in the list
	public PropertyNode getPropertyNode(String name) {
		synchronized (propertyNodes) {
			List<PropertyNode> list = getPropertyNodes(name);
			if (!list.isEmpty())
				return list.get(0);
			return null;
		}
	}
	
	public List<PropertyNode> getPropertyNodes(String name) {
		synchronized (propertyNodes) {
			List<PropertyNode> list = propertyNodes.get(name);
			if (list == null) {
				list = new ArrayList<PropertyNode>();
				propertyNodes.put(name, list);
			}
			return list;
		}
	}
	
//	public void setPropertyNodes(String propertyName, PropertyNode propertyNode) {
//		synchronized (propertyNodes) {
//			this.propertyNodes.put(propertyName, propertyNode);
//		}
//	}

//	public void setPropertyNodes(String propertyName, List<PropertyNode> propertyNodes) {
//		synchronized (propertyNodes) {
//			this.propertyListNodes.put(propertyName, propertyNodes);
//		}
//	}
	
	public void addPropertyNode(String propertyName, PropertyNode propertyNode) {
		synchronized (propertyNodes) {
			List<PropertyNode> list = this.propertyNodes.get(propertyName);
			list.add(propertyNode);
			addChildNode(propertyNode);
		}
	}

	public List<AbstractNode> getChildNodes() {
		return childNodes;
	}

	public void addChildNode(AbstractNode childNode) {
		childNodes.add(childNode);
	}

	public String toString() {
		if (tokens != null)
			return CodeUtil.getSourceFromTokens(tokens);
		if (text != null)
			return text + " " + super.toString();
		if (type != null && name != null)
			return type + ":" + name + " " + super.toString();
		if (name != null)
			return name + " " + super.toString();
		return super.toString();
	}

}
