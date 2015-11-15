package nam.ui.tree;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;


@SuppressWarnings("serial")
public class ModelTreeNode extends AbstractTreeNode<ModelTreeObject<?>> implements Serializable {

	public ModelTreeNode(ModelTreeObject<?> data) {
		setData(data);
		setId(data.getId());
		setType(data.getType());
		setLabel(data.getLabel());
		Object object = data.getObject();
		if (object != null)
			setArea(object.getClass().getSimpleName());
	}
	
	public void addNodes(Collection<ModelTreeNode> nodes) {
		Iterator<ModelTreeNode> iterator = nodes.iterator();
		while (iterator.hasNext()) {
			ModelTreeNode node = iterator.next();
			addNode(node);
		}
	}
	
	public void addNode(ModelTreeNode node) {
		String childLabel = node.getData().getLabel();
		addChild(childLabel, node);
	}

//	public Iterator<Map.Entry<Object, TreeNode>> getChildren() {
//		//Map<Object, TreeNode> childrenMap2 = super.getChildrenMap();
//		Iterator<Entry<Object, TreeNode>> children = super.getChildrenKeysIterator();
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
