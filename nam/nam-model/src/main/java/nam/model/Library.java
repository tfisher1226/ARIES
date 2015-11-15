package nam.model;

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
@XmlType(name = "Library", namespace = "http://nam/model", propOrder = {
	"exclusions",
	"dependencies"
})
@XmlRootElement(name = "library", namespace = "http://nam/model")
public class Library extends Dependency implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "exclusions", namespace = "http://nam/model")
	private List<Dependency> exclusions;
	
	@XmlElement(name = "dependencies", namespace = "http://nam/model")
	private List<Dependency> dependencies;
	
	
	public Library() {
		exclusions = new ArrayList<Dependency>();
		dependencies = new ArrayList<Dependency>();
	}
	
	
	public List<Dependency> getExclusions() {
		synchronized (exclusions) {
			return exclusions;
		}
	}
	
	public void setExclusions(Collection<Dependency> exclusions) {
		if (exclusions == null) {
			this.exclusions = null;
		} else {
		synchronized (this.exclusions) {
				this.exclusions = new ArrayList<Dependency>();
				addToExclusions(exclusions);
			}
		}
	}

	public void addToExclusions(Dependency dependency) {
		if (dependency != null ) {
			synchronized (this.exclusions) {
				this.exclusions.add(dependency);
			}
		}
	}

	public void addToExclusions(Collection<Dependency> dependencyCollection) {
		if (dependencyCollection != null && !dependencyCollection.isEmpty()) {
			synchronized (this.exclusions) {
				this.exclusions.addAll(dependencyCollection);
			}
		}
	}

	public void removeFromExclusions(Dependency dependency) {
		if (dependency != null ) {
			synchronized (this.exclusions) {
				this.exclusions.remove(dependency);
			}
		}
	}

	public void removeFromExclusions(Collection<Dependency> dependencyCollection) {
		if (dependencyCollection != null ) {
			synchronized (this.exclusions) {
				this.exclusions.removeAll(dependencyCollection);
			}
		}
	}

	public void clearExclusions() {
		synchronized (exclusions) {
			exclusions.clear();
		}
	}
	
	public List<Dependency> getDependencies() {
		synchronized (dependencies) {
			return dependencies;
		}
	}
	
	public void setDependencies(Collection<Dependency> dependencies) {
		if (dependencies == null) {
			this.dependencies = null;
		} else {
		synchronized (this.dependencies) {
				this.dependencies = new ArrayList<Dependency>();
				addToDependencies(dependencies);
			}
		}
	}

	public void addToDependencies(Dependency dependency) {
		if (dependency != null ) {
			synchronized (this.dependencies) {
				this.dependencies.add(dependency);
			}
		}
	}

	public void addToDependencies(Collection<Dependency> dependencyCollection) {
		if (dependencyCollection != null && !dependencyCollection.isEmpty()) {
			synchronized (this.dependencies) {
				this.dependencies.addAll(dependencyCollection);
			}
		}
	}

	public void removeFromDependencies(Dependency dependency) {
		if (dependency != null ) {
			synchronized (this.dependencies) {
				this.dependencies.remove(dependency);
			}
		}
	}

	public void removeFromDependencies(Collection<Dependency> dependencyCollection) {
		if (dependencyCollection != null ) {
			synchronized (this.dependencies) {
				this.dependencies.removeAll(dependencyCollection);
			}
		}
	}

	public void clearDependencies() {
		synchronized (dependencies) {
			dependencies.clear();
		}
	}
}
