package nam.model._import;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Import;
import nam.model.util.ImportUtil;


public class ImportListObject extends AbstractListObject<Import> implements Comparable<ImportListObject>, Serializable {
	
	private Import _import;
	
	
	public ImportListObject(Import _import) {
		this._import = _import;
	}
	
	
	public Import getImport() {
		return _import;
	}
	
	@Override
	public Object getKey() {
		return getKey(_import);
	}
	
	public Object getKey(Import _import) {
		return ImportUtil.getKey(_import);
	}
	
	@Override
	public String getLabel() {
		return getLabel(_import);
	}
	
	public String getLabel(Import _import) {
		return ImportUtil.getLabel(_import);
	}
	
	@Override
	public String toString() {
		return toString(_import);
	}
	
	@Override
	public String toString(Import _import) {
		return ImportUtil.toString(_import);
	}
	
	@Override
	public int compareTo(ImportListObject other) {
		Object thisKey = getKey(this._import);
		Object otherKey = getKey(other._import);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ImportListObject other = (ImportListObject) object;
		Object thisKey = ImportUtil.getKey(this._import);
		Object otherKey = ImportUtil.getKey(other._import);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
