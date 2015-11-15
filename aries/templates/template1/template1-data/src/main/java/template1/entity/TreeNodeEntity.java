package template1.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

/**
 *
 */
@Entity(name = "TreeNode")
@Table(name = "treeNode")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TreeNodeEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

	@Column(name = "node_level")
	private TreeNodeLevel nodeLevel;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "parent", referencedColumnName = "id")
	@ForeignKey(name = "tree_node_parent_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private TreeNodeEntity parent;

	@OneToMany(cascade = CascadeType.ALL, targetEntity = TreeNodeEntity.class)
	@JoinColumn(name = "children", referencedColumnName = "id")
	@ForeignKey(name = "tree_node_children_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<TreeNodeEntity> children;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public TreeNodeLevel getNodeLevel() {
		return nodeLevel;
	}

	public void setNodeLevel(TreeNodeLevel nodeLevel) {
		this.nodeLevel = nodeLevel;
	}

	public TreeNodeEntity getParent() {
		return parent;
	}

	public void setParent(TreeNodeEntity parent) {
		this.parent = parent;
	}

	public List<TreeNodeEntity> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNodeEntity> children) {
		this.children = children;
	}

}