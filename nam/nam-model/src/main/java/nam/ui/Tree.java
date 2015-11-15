package nam.ui;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nam.model.Component;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Tree", namespace = "http://nam/ui", propOrder = {
    "treeNodes"
})
@XmlRootElement(name = "tree", namespace = "http://nam/ui")
public class Tree extends Component implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "name")
	private String name;
	
	@XmlElement(name = "treeNodes", namespace = "http://nam/ui")
	private TreeNodes treeNodes;
	
	
	public Tree() {
		//nothing for now
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
    public TreeNodes getTreeNodes() {
        return treeNodes;
    }

	public void setTreeNodes(TreeNodes treeNodes) {
		this.treeNodes = treeNodes;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(Object object) {
		if (object.getClass().isAssignableFrom(this.getClass())) {
			Tree other = (Tree) object;
			int status = compare(name, other.name);
			if (status != 0)
				return status;
		}
		int status = super.compareTo(object);
		return status;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Tree other = (Tree) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (name != null)
			hashCode += name.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Tree: name="+name;
    }

}
