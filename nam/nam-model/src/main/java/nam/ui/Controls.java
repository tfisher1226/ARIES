package nam.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Controls", namespace = "http://nam/ui", propOrder = {
	"tables",
    "trees"
})
@XmlRootElement(name = "controls", namespace = "http://nam/ui")
public class Controls implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "table", namespace = "http://nam/ui")
	private List<Table> tables;
	
	@XmlElement(name = "tree", namespace = "http://nam/ui")
	private List<Tree> trees;
	
	
	public Controls() {
		tables = new ArrayList<Table>();
		trees = new ArrayList<Tree>();
	}
	
	
	public List<Table> getTables() {
		synchronized (tables) {
			return tables;
		}
	}
	
	public void setTables(Collection<Table> tables) {
		if (tables == null) {
			this.tables = null;
		} else {
		synchronized (this.tables) {
				this.tables = new ArrayList<Table>();
				addToTables(tables);
			}
		}
	}

	public void addToTables(Table table) {
		if (table != null ) {
			synchronized (this.tables) {
				this.tables.add(table);
			}
		}
	}

	public void addToTables(Collection<Table> tableCollection) {
		if (tableCollection != null && !tableCollection.isEmpty()) {
			synchronized (this.tables) {
				this.tables.addAll(tableCollection);
			}
		}
	}

	public void removeFromTables(Table table) {
		if (table != null ) {
			synchronized (this.tables) {
				this.tables.remove(table);
			}
		}
	}

	public void removeFromTables(Collection<Table> tableCollection) {
		if (tableCollection != null ) {
			synchronized (this.tables) {
				this.tables.removeAll(tableCollection);
			}
		}
	}

	public void clearTables() {
		synchronized (tables) {
			tables.clear();
		}
	}
	
    public List<Tree> getTrees() {
		synchronized (trees) {
			return trees;
		}
	}
	
	public void setTrees(Collection<Tree> trees) {
        if (trees == null) {
			this.trees = null;
		} else {
		synchronized (this.trees) {
				this.trees = new ArrayList<Tree>();
				addToTrees(trees);
			}
		}
	}

	public void addToTrees(Tree tree) {
		if (tree != null ) {
			synchronized (this.trees) {
				this.trees.add(tree);
			}
        }
    }

	public void addToTrees(Collection<Tree> treeCollection) {
		if (treeCollection != null && !treeCollection.isEmpty()) {
			synchronized (this.trees) {
				this.trees.addAll(treeCollection);
			}
		}
	}

	public void removeFromTrees(Tree tree) {
		if (tree != null ) {
			synchronized (this.trees) {
				this.trees.remove(tree);
			}
		}
	}

	public void removeFromTrees(Collection<Tree> treeCollection) {
		if (treeCollection != null ) {
			synchronized (this.trees) {
				this.trees.removeAll(treeCollection);
			}
		}
	}

	public void clearTrees() {
		synchronized (trees) {
			trees.clear();
		}
	}
}
