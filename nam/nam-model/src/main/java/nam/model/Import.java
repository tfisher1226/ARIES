package nam.model;

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


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Import", namespace = "http://nam/model", propOrder = {
	"object",
	"imports"
})
@XmlRootElement(name = "import", namespace = "http://nam/model")
public class Import implements Comparable<Object>, Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "dir")
	private String dir;

	@XmlAttribute(name = "file")
	private String file;
	
	@XmlAttribute(name = "type")
	private String type;
	
	@XmlAttribute(name = "prefix")
	private String prefix;
	
	@XmlAttribute(name = "namespace")
	private String namespace;
	
	@XmlAttribute(name = "include")
	private boolean include;
	
	@XmlElement(name = "object", namespace = "http://nam/model")
	private Object object;
	
	@XmlElement(name = "imports", namespace = "http://nam/model")
	private List<Import> imports;
	
	@XmlAttribute(name = "ref")
	private String ref;
	
	
	public Import() {
		imports = new ArrayList<Import>();
    }

	
    public String getDir() {
        return dir;
    }

	public void setDir(String dir) {
		this.dir = dir;
	}
	
    public String getFile() {
        return file;
    }

	public void setFile(String file) {
		this.file = file;
	}
	
    public String getType() {
        return type;
    }

	public void setType(String type) {
		this.type = type;
	}
	
    public String getPrefix() {
        return prefix;
    }

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
    public String getNamespace() {
        return namespace;
    }

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

    public boolean getInclude() {
        return include;
    }

	public void setInclude(boolean include) {
		this.include = include;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
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
	public int compareTo(Object object) {
		return 0;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Import other = (Import) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Import: dir="+dir+", file="+file+", type="+type+", prefix="+prefix+", namespace="+namespace;
    }


}
