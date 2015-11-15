package org.aries.ui.tree;

import java.io.Serializable;


@SuppressWarnings("serial")
public class ModelTreeNode extends AbstractTreeNode implements Serializable {

	public ModelTreeNode() {
		super(false);
	}

	public ModelTreeNode(ModelTreeObject<?> data) {
		super(false);
		setData(data);
	}

	protected void addNode(ModelTreeNode node) {
		addChild(node.getData().toString(), node);
	}

	@SuppressWarnings("rawtypes") 
	public static ModelTreeNode createNode(String label) {
		ModelTreeObject<?> object = new ModelTreeObject(label);
		return createNode(object);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" }) 
	public static <T> ModelTreeNode createNode(T object, String label) {
		ModelTreeObject<T> treeObject = new ModelTreeObject(object, label);
		return createNode(treeObject);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" }) 
	public static <T> ModelTreeNode createNode(T object, String label, String type) {
		ModelTreeObject<T> treeObject = new ModelTreeObject(object, label, type);
		return createNode(treeObject);
	}

	public static ModelTreeNode createNode(ModelTreeObject<?> object) {
		ModelTreeNode node = new ModelTreeNode(object);
		return node;
	}
	
//	public Iterator<Map.Entry<Object, TreeNode>> getChildren() {
//		Map<Object, TreeNode> childrenMap2 = super.getChildrenMap();
//		Iterator<Object> childrenKeysIterator = super.getChildrenKeysIterator();
//		Iterator<Entry<Object, TreeNode>> children = childrenKeysIterator;
//		return children;
//	}

	public String toString() {
		return getData().toString();
	}
	
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		ModelTreeNode other = (ModelTreeNode) object;
		return getData().equals(other.getData());
	}
	
	public int hashCode() {
		return getData().hashCode();
	}

}
