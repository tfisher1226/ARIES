package nam.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nam.model.Import;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "View", namespace = "http://nam/ui", propOrder = {
    "name",
	"domain",
	"imports",
	"relations",
	"controls"
})
@XmlRootElement(name = "view", namespace = "http://nam/ui")
public class View implements Comparable<View>, Serializable {

	private static final long serialVersionUID = 1L;
    
	@XmlElement(name = "name", namespace = "http://nam/ui", required = true)
	private String name;
    
	@XmlElement(name = "domain", namespace = "http://nam/ui", required = true)
	private String domain;
    
	@XmlElement(name = "import", namespace = "http://nam/ui")
	private List<Import> imports;
    
	@XmlElement(name = "relations", namespace = "http://nam/ui")
	private Relations relations;
	
	@XmlElement(name = "controls", namespace = "http://nam/ui")
	private Controls controls;
    
    @XmlAttribute(name = "ref")
	private String ref;

	
	public View() {
		imports = new ArrayList<Import>();
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
    }

    public String getDomain() {
        return domain;
    }

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public List<Import> getImports() {
		synchronized (imports) {
			return imports;
		}
	}
	
	public void setImports(Collection<Import> imports) {
		if (imports == null) {
			this.imports = null;
		} else {
		synchronized (this.imports) {
				this.imports = new ArrayList<Import>();
				addToImports(imports);
			}
		}
	}

	public void addToImports(Import _import) {
		if (_import != null ) {
			synchronized (this.imports) {
				this.imports.add(_import);
			}
		}
	}

	public void addToImports(Collection<Import> importCollection) {
		if (importCollection != null && !importCollection.isEmpty()) {
			synchronized (this.imports) {
				this.imports.addAll(importCollection);
			}
		}
	}

	public void removeFromImports(Import _import) {
		if (_import != null ) {
			synchronized (this.imports) {
				this.imports.remove(_import);
			}
		}
	}

	public void removeFromImports(Collection<Import> importCollection) {
		if (importCollection != null ) {
			synchronized (this.imports) {
				this.imports.removeAll(importCollection);
			}
		}
	}

	public void clearImports() {
		synchronized (imports) {
			imports.clear();
		}
	}
	
	public Relations getRelations() {
		return relations;
	}
	
	public void setRelations(Relations relations) {
		this.relations = relations;
    }

    public Controls getControls() {
        return controls;
    }

	public void setControls(Controls controls) {
		this.controls = controls;
	}
	
    public String getRef() {
        return ref;
    }

	public void setRef(String ref) {
		this.ref = ref;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(View other) {
		int status = compare(name, other.name);
		if (status != 0)
			return status;
		status = compare(domain, other.domain);
		if (status != 0)
			return status;
		return 0;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		View other = (View) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (name != null)
			hashCode += name.hashCode();
		if (domain != null)
			hashCode += domain.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "View: name="+name+", domain="+domain;
    }

}
