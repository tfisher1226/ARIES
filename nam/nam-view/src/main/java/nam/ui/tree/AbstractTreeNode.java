package nam.ui.tree;

import org.richfaces.model.TreeNode;


@SuppressWarnings("serial")
public class AbstractTreeNode<T> extends org.aries.ui.tree.AbstractTreeNode implements TreeNode {

	private T data;
	
	
	public AbstractTreeNode() {
		this(false);
	}

	public AbstractTreeNode(boolean leaf) {
		super(leaf);
	}
	
	public T getData() {
		return data;
	}

	@SuppressWarnings("unchecked")
	public void setData(Object data) {
		this.data = (T) data;
	}

}
