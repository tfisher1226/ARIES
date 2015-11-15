package nam.model.node;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Node;
import nam.model.util.NodeUtil;


public class NodeListObject extends AbstractListObject<Node> implements Comparable<NodeListObject>, Serializable {
	
	private Node node;
	
	
	public NodeListObject(Node node) {
		this.node = node;
	}
	
	
	public Node getNode() {
		return node;
	}
	
	@Override
	public Object getKey() {
		return getKey(node);
	}
	
	public Object getKey(Node node) {
		return NodeUtil.getKey(node);
	}
	
	@Override
	public String getLabel() {
		return getLabel(node);
	}
	
	public String getLabel(Node node) {
		return NodeUtil.getLabel(node);
	}
	
	@Override
	public String toString() {
		return toString(node);
	}
	
	@Override
	public String toString(Node node) {
		return NodeUtil.toString(node);
	}
	
	@Override
	public int compareTo(NodeListObject other) {
		Object thisKey = getKey(this.node);
		Object otherKey = getKey(other.node);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		NodeListObject other = (NodeListObject) object;
		Object thisKey = NodeUtil.getKey(this.node);
		Object otherKey = NodeUtil.getKey(other.node);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
