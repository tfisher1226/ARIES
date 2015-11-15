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
@XmlType(name = "Relations", namespace = "http://nam/ui", propOrder = {
	"relations"
})
@XmlRootElement(name = "relations", namespace = "http://nam/ui")
public class Relations implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "relation", namespace = "http://nam/ui")
	private List<Relation> relations;
	
	
	public Relations() {
		relations = new ArrayList<Relation>();
	}
	
	
	public List<Relation> getRelations() {
		synchronized (relations) {
			return relations;
		}
	}
	
	public void setRelations(Collection<Relation> relations) {
		if (relations == null) {
			this.relations = null;
		} else {
		synchronized (this.relations) {
				this.relations = new ArrayList<Relation>();
				addToRelations(relations);
			}
		}
	}

	public void addToRelations(Relation relation) {
		if (relation != null ) {
			synchronized (this.relations) {
				this.relations.add(relation);
			}
		}
	}

	public void addToRelations(Collection<Relation> relationCollection) {
		if (relationCollection != null && !relationCollection.isEmpty()) {
			synchronized (this.relations) {
				this.relations.addAll(relationCollection);
			}
		}
	}

	public void removeFromRelations(Relation relation) {
		if (relation != null ) {
			synchronized (this.relations) {
				this.relations.remove(relation);
			}
		}
	}

	public void removeFromRelations(Collection<Relation> relationCollection) {
		if (relationCollection != null ) {
			synchronized (this.relations) {
				this.relations.removeAll(relationCollection);
			}
		}
	}

	public void clearRelations() {
		synchronized (relations) {
			relations.clear();
		}
	}
}
