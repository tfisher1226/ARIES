package org.aries.ui.tree;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;


public class AbstractTreeNode extends TreeNodeImpl implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String label;

	private String area;

	private String type;

	private Object data;

	private boolean expanded;

	private boolean selected;

	private TreeNode parent;

	protected Map<Object, TreeNode> childrenMap = new LinkedHashMap<Object, TreeNode>();
	
	
	public AbstractTreeNode() {
		this(false);
	}

	public AbstractTreeNode(boolean leaf) {
		super(leaf);
		//expanded = !leaf;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	protected Map<Object, TreeNode> getChildrenMap() {
		return childrenMap;
	}

//	public Iterator<Map.Entry<Object, TreeNode>> getChildren() {
//		return childrenMap.entrySet().iterator();
//	}

	public boolean isLeaf() {
		//return childrenMap.isEmpty();
		return super.isLeaf();
	}
	
	public TreeNode getChild(Object identifier) {
		synchronized (childrenMap) {
			TreeNode treeNode = childrenMap.get(identifier);
			if (treeNode != null) { 
				return treeNode;
			}
			Collection<TreeNode> nodes = childrenMap.values();
			Iterator<TreeNode> iterator = nodes.iterator();
			while (iterator.hasNext()) {
				TreeNode subNode = iterator.next();
				treeNode = subNode.getChild(identifier);
				if (treeNode != null) { 
					return treeNode;
				}
			}
			return null;
		}
	}

	public void addChild(Object identifier, TreeNode child) {
		synchronized (childrenMap) {
			super.addChild(identifier, child);
			childrenMap.put(identifier, child);
			if (child instanceof AbstractTreeNode) {
				AbstractTreeNode childNode = (AbstractTreeNode) child;
				childNode.setParent(this);
			}
		}
	}

	public void removeChildren() {
		synchronized (childrenMap) {
			childrenMap.clear();
		}
	}
	
	public void removeChild(Object identifier) {
		synchronized (childrenMap) {
			childrenMap.remove(identifier);
			super.removeChild(identifier);
			//if (treeNode != null)
			//	treeNode.setParent(null);
			//return treeNode;
		}
	}

}
